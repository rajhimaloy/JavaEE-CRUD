/**
 * 
 */
package com.banks.erp.library.util.util;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Map;

import javax.sql.DataSource;
import javax.transaction.Transactional;
import javax.transaction.Transactional.TxType;
import javax.annotation.Resource;
import javax.enterprise.context.Dependent;
import javax.faces.context.FacesContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.export.HtmlExporter;
import net.sf.jasperreports.engine.export.JRCsvExporter;
//import net.sf.jasperreports.engine.export.JRHtmlExporter;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.engine.export.JRRtfExporter;
import net.sf.jasperreports.engine.export.JRXlsExporter;
import net.sf.jasperreports.engine.export.JRXmlExporter;
import net.sf.jasperreports.engine.export.ooxml.JRXlsxExporter;
import net.sf.jasperreports.engine.query.JRQueryExecuterFactory;
//import net.sf.jasperreports.engine.util.JRProperties;
import net.sf.jasperreports.export.Exporter;
import net.sf.jasperreports.export.ExporterInput;
import net.sf.jasperreports.export.HtmlExporterOutput;
import net.sf.jasperreports.export.OutputStreamExporterOutput;
import net.sf.jasperreports.export.SimpleCsvExporterConfiguration;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleHtmlExporterConfiguration;
import net.sf.jasperreports.export.SimpleHtmlExporterOutput;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;
import net.sf.jasperreports.export.SimplePdfExporterConfiguration;
import net.sf.jasperreports.export.SimpleRtfExporterConfiguration;
import net.sf.jasperreports.export.SimpleXlsExporterConfiguration;
import net.sf.jasperreports.export.SimpleXlsxExporterConfiguration;
import net.sf.jasperreports.export.WriterExporterOutput;
import net.sf.jasperreports.export.XmlExporterOutput;

/**
 * @author Rajib Kumer Ghosh
 *
 */

@Dependent
@Transactional(TxType.SUPPORTS)
public class ReportUtil {

	//@Resource(name = "rajibkg_ds_mysql_rpt")
	@Resource(name = "rajibkg_ds_oracle_rpt")
	private DataSource ds;	

	public static String getReportExportFormat(Integer reportExportformat) {
		String reportFormat = null;

		if (reportExportformat == 1) {
			reportFormat = ".pdf";
		} else if (reportExportformat == 2) {
			reportFormat = ".xlsx";
		} else if (reportExportformat == 3) {
			reportFormat = ".html";
		} else if (reportExportformat == 4) {
			reportFormat = ".xls";
		} else if (reportExportformat == 5) {
			reportFormat = ".csv";
		} else if (reportExportformat == 6) {
			reportFormat = ".rtf";
		} else {
			reportFormat = ".xml";
		}

		return reportFormat;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public byte[] export(final JasperPrint print, Integer reportExportformat) throws JRException {
		final Exporter exporter;
		final ByteArrayOutputStream out = new ByteArrayOutputStream();
		boolean html = false;
		// Get report format
		String reportFormat = getReportExportFormat(reportExportformat);

		switch (reportFormat) {
		case ".html":
			exporter = new HtmlExporter();
			exporter.setExporterOutput(new SimpleHtmlExporterOutput(out));
			html = true;
			break;

		case ".csv":
			exporter = new JRCsvExporter();
			break;

		case ".xml":
			exporter = new JRXmlExporter();
			break;

		case ".xlsx":
			exporter = new JRXlsxExporter();
			break;

		case ".pdf":
			exporter = new JRPdfExporter();
			break;

		default:
			throw new JRException("Unknown report format: " + reportFormat.toString());
		}

		if (!html) {
			exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(out));
		}

		exporter.setExporterInput(new SimpleExporterInput(print));
		exporter.exportReport();

		return out.toByteArray();
	}

	@SuppressWarnings("unchecked")
	public void generateReport(String reportSrcFilePath, Map<String, Object> parameters, String reportName,
			Integer reportExportformat) {
		try {

			Connection conn = ds.getConnection();

			// Report Path
			String reportSrcFile = FacesContext.getCurrentInstance().getExternalContext()
					.getRealPath(reportSrcFilePath);

			// Compile from .jrxml to .jasper
			JasperReport jasperReport = JasperCompileManager.compileReport(reportSrcFile);
			
			//For Jasper Version 6.1.0
			//jasperReport.setProperty( "net.sf.jasperreports.query.executer.factory.plsql", "com.jaspersoft.jrx.query.PlSqlQueryExecuterFactory");

			// Fill the Report
			JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, conn);
			// logger.info("JasperPrint" + jasperPrint);

			// Sending Response to UI
			HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext()
					.getResponse();

			// Get report format
			String reportFormat = getReportExportFormat(reportExportformat);

			if (reportExportformat == 1) {
				// Export report to PDF format
				JRPdfExporter pdfExporter = new JRPdfExporter();
				pdfExporter.setExporterInput(new SimpleExporterInput(jasperPrint));
				ByteArrayOutputStream pdfReportStream = new ByteArrayOutputStream();
				pdfExporter.setExporterOutput(new SimpleOutputStreamExporterOutput(pdfReportStream));
				pdfExporter.exportReport();

				// Setting response header for PDF
				response.setContentType("application/pdf");
				response.setHeader("Content-Length", String.valueOf(pdfReportStream.size()));
				response.addHeader("Content-Disposition",
						"inline; filename=" + reportName + "-rajib.nagad" + reportFormat + ";");

				// Closing Stream of PDF
				OutputStream responseOutputStream = response.getOutputStream();
				responseOutputStream.write(pdfReportStream.toByteArray());
				responseOutputStream.close();
				pdfReportStream.close();
			} else if (reportExportformat == 2) {
				// Export report to XLSX format
				JRXlsxExporter xlsxExporter = new JRXlsxExporter();
				xlsxExporter.setExporterInput(new SimpleExporterInput(jasperPrint));
				ByteArrayOutputStream xlsxReportStream = new ByteArrayOutputStream();
				xlsxExporter.setExporterOutput(new SimpleOutputStreamExporterOutput(xlsxReportStream));
				xlsxExporter.exportReport();

				// Setting response header for XLSX
				response.setContentType("application/vnd.ms-excel");
				response.setHeader("Content-Length", String.valueOf(xlsxReportStream.size()));
				response.addHeader("Content-Disposition",
						"inline; filename=" + reportName + "-rajib.nagad" + reportFormat + ";");

				// Closing Stream of XLSX
				OutputStream responseOutputStream = response.getOutputStream();
				responseOutputStream.write(xlsxReportStream.toByteArray());
				responseOutputStream.close();
				xlsxReportStream.close();
			} else if (reportExportformat == 3) {
				// Export report to HTML format.
				Exporter htmlExporter = new HtmlExporter();

				// Exporter Input for All format
				htmlExporter.setExporterInput(new SimpleExporterInput(jasperPrint));
				ByteArrayOutputStream htmlReportStream = new ByteArrayOutputStream();
				htmlExporter.setExporterOutput(new SimpleHtmlExporterOutput(htmlReportStream));
				htmlExporter.exportReport();

				// Setting response header for HTML
				response.setContentType("text/html");
				response.setHeader("Content-Length", String.valueOf(htmlReportStream.size()));
				response.addHeader("Content-Disposition",
						"inline; filename=" + reportName + "-rajib.nagad" + reportFormat + ";");

				// Closing Stream of HTML
				OutputStream responseOutputStream = response.getOutputStream();
				responseOutputStream.write(htmlReportStream.toByteArray());
				responseOutputStream.close();
				htmlReportStream.close();
			} else if (reportExportformat == 4) {
				// Export report to XLS format
				JRXlsExporter xlsExporter = new JRXlsExporter();
				xlsExporter.setExporterInput(new SimpleExporterInput(jasperPrint));
				ByteArrayOutputStream xlsReportStream = new ByteArrayOutputStream();
				xlsExporter.setExporterOutput(new SimpleOutputStreamExporterOutput(xlsReportStream));
				xlsExporter.exportReport();

				// Setting response header for XLS
				response.setContentType("vnd.ms-excel");
				response.setHeader("Content-Length", String.valueOf(xlsReportStream.size()));
				response.addHeader("Content-Disposition",
						"inline; filename=" + reportName + "-rajib.nagad" + reportFormat + ";");

				// Closing Stream of XLS
				OutputStream responseOutputStream = response.getOutputStream();
				responseOutputStream.write(xlsReportStream.toByteArray());
				responseOutputStream.close();
				xlsReportStream.close();
			} else if (reportExportformat == 5) {
				// Export report to CSV format
				JRCsvExporter csvExporter = new JRCsvExporter();
				csvExporter.setExporterInput(new SimpleExporterInput(jasperPrint));
				ByteArrayOutputStream csvReportStream = new ByteArrayOutputStream();
				csvExporter.setExporterOutput(
						(WriterExporterOutput) new SimpleOutputStreamExporterOutput(csvReportStream));
				csvExporter.exportReport();

				// Setting response header for CSV
				response.setContentType("APPLICATION/OCTET-STREAM");
				response.setHeader("Content-Length", String.valueOf(csvReportStream.size()));
				response.addHeader("Content-Disposition",
						"inline; filename=" + reportName + "-rajib.nagad" + reportFormat + ";");

				// Closing Stream of CSV
				OutputStream responseOutputStream = response.getOutputStream();
				responseOutputStream.write(csvReportStream.toByteArray());
				responseOutputStream.close();
				csvReportStream.close();
			} else if (reportExportformat == 6) {
				// Export report to RTF format
				JRRtfExporter rtfExporter = new JRRtfExporter();
				rtfExporter.setExporterInput(new SimpleExporterInput(jasperPrint));
				ByteArrayOutputStream rtfReportStream = new ByteArrayOutputStream();
				rtfExporter.setExporterOutput(
						(WriterExporterOutput) new SimpleOutputStreamExporterOutput(rtfReportStream));
				rtfExporter.exportReport();

				// Setting response header for RTF
				response.setContentType("application/text");
				response.setHeader("Content-Length", String.valueOf(rtfReportStream.size()));
				response.addHeader("Content-Disposition",
						"inline; filename=" + reportName + "-rajib.nagad" + reportFormat + ";");

				// Closing Stream of RTF
				OutputStream responseOutputStream = response.getOutputStream();
				responseOutputStream.write(rtfReportStream.toByteArray());
				responseOutputStream.close();
				rtfReportStream.close();
			} else {
				// Export report to XML format
				JRXmlExporter xmlExporter = new JRXmlExporter();
				xmlExporter.setExporterInput(new SimpleExporterInput(jasperPrint));
				ByteArrayOutputStream xmlReportStream = new ByteArrayOutputStream();
				xmlExporter.setExporterOutput(
						(XmlExporterOutput) new SimpleOutputStreamExporterOutput(xmlReportStream));
				xmlExporter.exportReport();

				// Setting response header for XML
				response.setContentType("text/xml");
				response.setHeader("Content-Length", String.valueOf(xmlReportStream.size()));
				response.addHeader("Content-Disposition",
						"inline; filename=" + reportName + "-rajib.nagad" + reportFormat + ";");

				// Closing Stream of XML
				OutputStream responseOutputStream = response.getOutputStream();
				responseOutputStream.write(xmlReportStream.toByteArray());
				responseOutputStream.close();
				xmlReportStream.close();
			}

			// closing database connection
			//conn.close();

			// logger will log the error into the studio logs
			// logger.info("Completed Successfully: ");
		} catch (Exception e) {
			// logger.info("Error: ", e);
			e.printStackTrace();
		}

	}

	public void generateReportInFolder(String reportSrcFilePath, Map<String, Object> parameters, String reportName,
			Integer reportExportformat) throws JRException, ClassNotFoundException, SQLException {

		String reportOutputFolder = "E:/jasperoutput/";

		String reportSrcFile = FacesContext.getCurrentInstance().getExternalContext().getRealPath(reportSrcFilePath);

		// Compile the jrxml.
		JasperReport jasperReport = JasperCompileManager.compileReport(reportSrcFile);

		// Database connection for report
		/*
		 * ConnectionUtil connectionUtil = new ConnectionUtil(); Connection conn =
		 * connectionUtil.getMySQLConnection();
		 */
		Connection conn = ds.getConnection();

		// Fill the report
		JasperPrint print = JasperFillManager.fillReport(jasperReport, parameters, conn);

		// Make sure the output directory exists.
		File outDir = new File(reportOutputFolder);
		outDir.mkdirs();

		// Get report format
		String reportFormat = getReportExportFormat(reportExportformat);

		if (reportExportformat == 1) {
			// PDF Exporter.
			JRPdfExporter exporter = new JRPdfExporter();

			// Exporter Input for All format
			ExporterInput exporterInput = new SimpleExporterInput(print);
			exporter.setExporterInput(exporterInput);

			// Exporter Output for All format except HTML
			OutputStreamExporterOutput exporterOutput = new SimpleOutputStreamExporterOutput(
					reportOutputFolder + reportName + "-kumer.rajib" + reportFormat);
			// Output
			exporter.setExporterOutput(exporterOutput);

			// Export to PDF format
			SimplePdfExporterConfiguration configuration = new SimplePdfExporterConfiguration();
			exporter.setConfiguration(configuration);
			exporter.exportReport();
		} else if (reportExportformat == 2) {
			// XLSX Exporter.
			JRXlsxExporter exporter = new JRXlsxExporter();

			// Exporter Input for All format
			ExporterInput exporterInput = new SimpleExporterInput(print);
			exporter.setExporterInput(exporterInput);

			// Exporter Output for All format except HTML
			OutputStreamExporterOutput exporterOutput = new SimpleOutputStreamExporterOutput(
					reportOutputFolder + reportName + "-kumer.rajib" + reportFormat);
			// Output
			exporter.setExporterOutput(exporterOutput);

			// Export to XLSX format
			SimpleXlsxExporterConfiguration configuration = new SimpleXlsxExporterConfiguration();
			exporter.setConfiguration(configuration);
			exporter.exportReport();
		} else if (reportExportformat == 3) {
			// HTML Exporter.
			HtmlExporter exporter = new HtmlExporter();

			// Exporter Input for All format
			ExporterInput exporterInput = new SimpleExporterInput(print);
			exporter.setExporterInput(exporterInput);

			// Exporter Output for HTML
			SimpleHtmlExporterOutput exporterOutput = new SimpleHtmlExporterOutput(
					reportOutputFolder + reportName + "-kumer.rajib" + reportFormat);
			// Output
			exporter.setExporterOutput(exporterOutput);

			// Export to HTML format
			SimpleHtmlExporterConfiguration configuration = new SimpleHtmlExporterConfiguration();
			exporter.setConfiguration(configuration);
			exporter.exportReport();
		} else if (reportExportformat == 4) {
			// XLS Exporter.
			JRXlsExporter exporter = new JRXlsExporter();

			// Exporter Input for All format
			ExporterInput exporterInput = new SimpleExporterInput(print);
			exporter.setExporterInput(exporterInput);

			// Exporter Output for All format except HTML
			SimpleOutputStreamExporterOutput exporterOutput = new SimpleOutputStreamExporterOutput(
					reportOutputFolder + reportName + "-kumer.rajib" + reportFormat);
			// Output
			exporter.setExporterOutput(exporterOutput);

			// Export to XLS format
			SimpleXlsExporterConfiguration configuration = new SimpleXlsExporterConfiguration();
			exporter.setConfiguration(configuration);
			exporter.exportReport();
		} else if (reportExportformat == 5) {
			// CSV Exporter.
			Exporter exporter = new JRCsvExporter();

			// Exporter Input for All format
			ExporterInput exporterInput = new SimpleExporterInput(print);
			exporter.setExporterInput(exporterInput);

			// Exporter Output for All format except HTML

			final ByteArrayOutputStream out = new ByteArrayOutputStream();
			try {
				out.write((reportOutputFolder + reportName + "-kumer.rajib" + reportFormat).getBytes());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			SimpleOutputStreamExporterOutput exporterOutput = new SimpleOutputStreamExporterOutput(out);

			// Output
			exporter.setExporterOutput(exporterOutput);

			// Export to CSV format
			SimpleCsvExporterConfiguration configuration = new SimpleCsvExporterConfiguration();
			exporter.setConfiguration(configuration);
			exporter.exportReport();
		} else if (reportExportformat == 6) {
			// RTF Exporter.
			Exporter exporter = new JRRtfExporter();

			// Exporter Input for All format
			ExporterInput exporterInput = new SimpleExporterInput(print);
			exporter.setExporterInput(exporterInput);

			// Exporter Output for All format except HTML
			/*
			 * final ByteArrayOutputStream out = new ByteArrayOutputStream(); try {
			 * out.write((reportOutputFolder + reportName + "-kumer.rajib" +
			 * reportFormat).getBytes()); } catch (IOException e) { // TODO Auto-generated
			 * catch block e.printStackTrace(); }
			 */
			OutputStreamExporterOutput exporterOutput = new SimpleOutputStreamExporterOutput(
					reportOutputFolder + reportName + "-kumer.rajib" + reportFormat);

			// Output
			exporter.setExporterOutput(exporterOutput);

			// Export to RTF format
			SimpleRtfExporterConfiguration configuration = new SimpleRtfExporterConfiguration();
			exporter.setConfiguration(configuration);
			exporter.exportReport();
		} else {
			// XML Exporter.
			Exporter exporter = new JRCsvExporter();

			// Exporter Input for All format
			ExporterInput exporterInput = new SimpleExporterInput(print);
			exporter.setExporterInput(exporterInput);

			// Exporter Output for All format except HTML
			OutputStreamExporterOutput exporterOutput = new SimpleOutputStreamExporterOutput(
					reportOutputFolder + reportName + "-kumer.rajib" + reportFormat);

			// Output
			exporter.setExporterOutput(exporterOutput);

			// Export to XML format
			SimpleCsvExporterConfiguration configuration = new SimpleCsvExporterConfiguration();
			exporter.setConfiguration(configuration);
			exporter.exportReport();
		}
		
		//conn.close();

	}

	// ====================================================================================================

	public void generateReportWithoutCompile(String reportSrcFilePath, Map<String, Object> parameters,
			String reportName, Integer reportExportformat)
			throws JRException, ClassNotFoundException, SQLException, IOException {

		// Database connection for report
		/*
		 * ConnectionUtil connectionUtil = new ConnectionUtil(); Connection conn =
		 * connectionUtil.getMySQLConnection();
		 */
		Connection conn = ds.getConnection();

		/*
		 * // Fill the report JasperPrint print =
		 * JasperFillManager.fillReport(reportSrcFile, parameters, conn);
		 * 
		 * // Get report format String reportFormat =
		 * getReportExportFormat(reportExportformat);
		 * 
		 * if (reportExportformat == 1) { // PDF Exporter. JRPdfExporter exporter = new
		 * JRPdfExporter();
		 * 
		 * // Exporter Input for All format ExporterInput exporterInput = new
		 * SimpleExporterInput(print); exporter.setExporterInput(exporterInput);
		 * 
		 * // Exporter Output for All format except HTML OutputStreamExporterOutput
		 * exporterOutput = new SimpleOutputStreamExporterOutput( reportOutputFolder +
		 * reportName + "-kumer.rajib" + reportFormat); // Output
		 * exporter.setExporterOutput(exporterOutput);
		 * 
		 * // Export to PDF format SimplePdfExporterConfiguration configuration = new
		 * SimplePdfExporterConfiguration(); exporter.setConfiguration(configuration);
		 * exporter.exportReport(); }
		 */

		// ---------------------------------------------------------------------------------------------

		/*
		 * ResourceBundle bundle = JsfUtil.getBundle(); String name =
		 * bundle.getString("CustomerDetails"); String currency =
		 * bundle.getString("CustomerDetails");
		 */
		String name = "CustomerDetails";

		String reportPath = FacesContext.getCurrentInstance().getExternalContext()
				.getRealPath("/reports/CustomerDetails.jasper");

		/*
		 * Map<String, Object> params = new HashMap<>(); params.put("customerid", 2);
		 * params.put("contactid", "C002");
		 */
		// params.put("SUBREPORT_DIR",
		// FacesContext.getCurrentInstance().getExternalContext().getRealPath("/reports/")
		// + "/");

		// JasperPrint jasperPrint = JasperFillManager.fillReport(reportPath,
		// parameters, new JREmptyDataSource());
		JasperPrint jasperPrint = JasperFillManager.fillReport(reportPath, parameters, conn);
		// JasperPrint jasperPrint = JasperFillManager.fillReport(reportPath, new
		// HashMap<String,Object>(), new JRBeanArrayDataSource(new
		// SaleOrder[]{saleOrder}));
		HttpServletResponse httpServletResponse = (HttpServletResponse) FacesContext.getCurrentInstance()
				.getExternalContext().getResponse();
		httpServletResponse.addHeader("Content-disposition",
				"attachment; filename=" + name + "_" + "kumer.rajib" + ".pdf");
		ServletOutputStream servletOutputStream = httpServletResponse.getOutputStream();
		JasperExportManager.exportReportToPdfStream(jasperPrint, servletOutputStream);
		FacesContext.getCurrentInstance().responseComplete();
		//conn.close();

	}

}
