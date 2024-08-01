/**
 * 
 */
package com.banks.erp.library.util.util;

import java.io.*;
import java.math.BigDecimal;
import java.sql.*;
import java.text.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.function.Consumer;

import javax.annotation.Resource;
import javax.enterprise.context.Dependent;
import javax.inject.Inject;
import javax.sql.DataSource;
import javax.transaction.Transactional;
import javax.transaction.Transactional.TxType;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.NumberToTextConverter;
import org.apache.poi.xssf.streaming.SXSSFCell;
import org.apache.poi.xssf.streaming.SXSSFRow;
import org.apache.poi.xssf.streaming.SXSSFSheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.*;

import com.monitorjbl.xlsx.StreamingReader;

/**
 * @author Rajib Kumer Ghosh
 *
 */
@Dependent
@Transactional(TxType.SUPPORTS)
public class ExcelUtil {

	@Resource(name = "rajibkg_ds_oracle_rpt")
	private DataSource ds;

	@Inject
	private ExcelParsingService excelParsingService;

	private String getFileName(String baseName) {
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss");
		String dateTimeInfo = dateFormat.format(new Date());
		return baseName.concat(String.format("_%s.xlsx", dateTimeInfo));
	}

	private void formatDateCell(XSSFWorkbook workbook, Cell cell) {
		CellStyle cellStyle = workbook.createCellStyle();
		CreationHelper creationHelper = workbook.getCreationHelper();
		cellStyle.setDataFormat(creationHelper.createDataFormat().getFormat("yyyy-MM-dd HH:mm:ss"));
		cell.setCellStyle(cellStyle);
	}

	private void writeHeaderLine(ResultSet result, XSSFSheet sheet) throws SQLException {
		// write header line containing column names
		ResultSetMetaData metaData = result.getMetaData();
		int numberOfColumns = metaData.getColumnCount();

		Row headerRow = sheet.createRow(0);

		// exclude the first column which is the ID field
		for (int i = 2; i <= numberOfColumns; i++) {
			String columnName = metaData.getColumnName(i);
			Cell headerCell = headerRow.createCell(i - 2);
			headerCell.setCellValue(columnName);
		}
	}

	private void writeDataLines(ResultSet result, XSSFWorkbook workbook, XSSFSheet sheet) throws SQLException {
		ResultSetMetaData metaData = result.getMetaData();
		int numberOfColumns = metaData.getColumnCount();

		int rowCount = 1;

		while (result.next()) {
			Row row = sheet.createRow(rowCount++);

			for (int i = 2; i <= numberOfColumns; i++) {
				Object valueObject = result.getObject(i);

				Cell cell = row.createCell(i - 2);

				if (valueObject instanceof Boolean) {
					cell.setCellValue((Boolean) valueObject);
				} else if (valueObject instanceof Float) {
					cell.setCellValue((float) valueObject);
				} else if (valueObject instanceof Double) {
					cell.setCellValue((double) valueObject);
				} else if (valueObject instanceof BigDecimal) {
					cell.setCellValue((double) ((BigDecimal) valueObject).doubleValue());
				} else if (valueObject instanceof Date) {
					cell.setCellValue((Date) valueObject);
					formatDateCell(workbook, cell);
				} else {
					cell.setCellValue((String) valueObject);
				}
			}
		}
	}

	public String exportToExcel(String sql, String fileName, String excelFilePathLocation) {

		// String excelFilePath = excelFilePathLocation +
		// getFileName(fileName.concat("_Export")); //With Date Time Stamp
		// String excelFilePath = excelFilePathLocation +
		// fileName.concat("_Export.csv"); //Without Date Time Stamp
		String excelFilePath = excelFilePathLocation + fileName.concat("_Export.xlsx"); // Without Date Time Stamp

		try {
			Connection conn = ds.getConnection();

			Statement statement = conn.createStatement();
			ResultSet result = statement.executeQuery(sql);

			XSSFWorkbook workbook = new XSSFWorkbook();
			// workbook.setCompressTempFiles(true);
			XSSFSheet sheet = workbook.createSheet(fileName);
			// sheet.setRandomAccessWindowSize(100);

			writeHeaderLine(result, sheet);

			writeDataLines(result, workbook, sheet);

			FileOutputStream outputStream = new FileOutputStream(excelFilePath);
			// BufferedOutputStream bufferedOutputStream = new
			// BufferedOutputStream(outputStream);
			workbook.write(outputStream);
			// workbook.dispose();
			workbook.close();
			outputStream.flush();
			outputStream.close();
			/*
			 * bufferedOutputStream.flush(); bufferedOutputStream.close();
			 */

			//result.close();
			//statement.close();
			//conn.close();

		} catch (SQLException e) {
			System.out.println("Datababse error:");
			e.printStackTrace();
		} catch (IOException e) {
			System.out.println("File IO error:");
			e.printStackTrace();
		}

		return excelFilePath;
	}

	private void formatDateCellforBigData(SXSSFWorkbook workbook, Cell cell) {
		CellStyle cellStyle = workbook.createCellStyle();
		CreationHelper creationHelper = workbook.getCreationHelper();
		cellStyle.setDataFormat(creationHelper.createDataFormat().getFormat("yyyy-MM-dd HH:mm:ss"));
		cell.setCellStyle(cellStyle);
	}

	private void writeBigDataHeaderLine(ResultSet result, Sheet sheet) throws SQLException {
		// write header line containing column names
		ResultSetMetaData metaData = result.getMetaData();
		int numberOfColumns = metaData.getColumnCount();

		Row headerRow = sheet.createRow(0);

		// exclude the first column which is the ID field
		for (int i = 2; i <= numberOfColumns; i++) {
			String columnName = metaData.getColumnName(i);
			Cell headerCell = headerRow.createCell(i - 2);
			headerCell.setCellValue(columnName);
		}
	}

	private void writeBigDataLines(ResultSet result, SXSSFWorkbook workbook, Sheet sheet) throws SQLException {
		ResultSetMetaData metaData = result.getMetaData();
		int numberOfColumns = metaData.getColumnCount();

		int rowCount = 1;

		while (result.next()) {
			Row row = sheet.createRow(rowCount++);

			for (int i = 2; i <= numberOfColumns; i++) {
				Object valueObject = result.getObject(i);

				Cell cell = row.createCell(i - 2);

				if (valueObject instanceof Boolean) {
					cell.setCellValue((Boolean) valueObject);
				} else if (valueObject instanceof Float) {
					cell.setCellValue((float) valueObject);
				} else if (valueObject instanceof Double) {
					cell.setCellValue((double) valueObject);
				} else if (valueObject instanceof BigDecimal) {
					cell.setCellValue((double) ((BigDecimal) valueObject).doubleValue());
				} else if (valueObject instanceof Date) {
					cell.setCellValue((Date) valueObject);
					formatDateCellforBigData(workbook, cell);
				} else {
					cell.setCellValue((String) valueObject);
				}
			}
		}
	}

	// Export to Big Data to Excel
	public String exportBigDataToExcel(String sql, String fileName, String excelFilePathLocation) {

		// String excelFilePath = excelFilePathLocation +
		// getFileName(fileName.concat("_Export")); //With Date Time Stamp
		String excelFilePath = excelFilePathLocation + fileName.concat("_Export.csv"); // Without Date Time Stamp
		// String excelFilePath = excelFilePathLocation +
		// fileName.concat("_Export.xlsx"); //Without Date Time Stamp

		try {
			Connection conn = ds.getConnection();

			Statement statement = conn.createStatement();
			ResultSet result = statement.executeQuery(sql);

			SXSSFWorkbook workbook = new SXSSFWorkbook(10);
			workbook.setCompressTempFiles(true);
			Sheet sheet = workbook.createSheet(fileName);
			// sheet.setRandomAccessWindowSize(10);

			writeBigDataHeaderLine(result, sheet);

			writeBigDataLines(result, workbook, sheet);

			FileOutputStream outputStream = new FileOutputStream(excelFilePath);
			// BufferedOutputStream bufferedOutputStream = new
			// BufferedOutputStream(outputStream);
			workbook.write(outputStream);
			// outputStream.flush();
			outputStream.close();
			workbook.dispose();
			// workbook.close();
			/*
			 * bufferedOutputStream.flush(); bufferedOutputStream.close();
			 */

			//result.close();
			//statement.close();
			//conn.close();

		} catch (SQLException e) {
			System.out.println("Datababse error:");
			e.printStackTrace();
		} catch (IOException e) {
			System.out.println("File IO error:");
			e.printStackTrace();
		}

		return excelFilePath;
	}
	
	//Big Data Import/Insert from Excel to DB (All columns in varchar2 format)
	public void excelToDBInserter(String sql, String excelFilePathLocation, String fileName, String fileExtension,
			String reportID) throws SQLException {

		String excelFilePath = excelFilePathLocation + fileName.concat(fileExtension);

		Integer bufferSize = 2048;
		Integer sheetAt = 0;
		Integer rowStart = 1;
		Integer rowEnd = -1;
		Integer batchSize = 1000;

		try (InputStream inputStream = new FileInputStream(new File(excelFilePath));
				Connection conn = ds.getConnection()) {

			PreparedStatement statement = conn.prepareStatement(sql);

			excelParsingService.readAsync(inputStream, bufferSize, sheetAt, rowStart, rowEnd, batchSize, (data) -> {
				data.forEach((key, row) -> {
					for (int i = 0; i < row.size(); i++) {
						try {
							statement.setString(i+1, row.get(i));
						} catch (SQLException e) {
							e.printStackTrace();
						}
					}
					
					try {
						statement.addBatch();
					} catch (SQLException e) {
						e.printStackTrace();
					}	
				});
				
				try {
					statement.executeBatch();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			});
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void excelToDBInserter1(String sql, String excelFilePathLocation, String fileName, String fileExtension,
			String reportID) {
		String excelFilePath = excelFilePathLocation + fileName.concat(fileExtension);

		int batchSize = 20;
		// String result = null;

		FileInputStream fileInputStream = null;
		InputStream inputStream = null;
		Scanner sc = null;

		try {
			// long start = System.currentTimeMillis();

			// OPCPackage pkg = OPCPackage.open(excelFilePath);
			// XSSFWorkbook workbook1 = new XSSFWorkbook(pkg);
			// pkg.close(); // gracefully closes the underlying zip file
			// work with the wb object

			// inputStream = new FileInputStream(excelFilePath);
			// XSSFWorkbook workbook1 = new XSSFWorkbook(inputStream);
			// SXSSFWorkbook workbook = new SXSSFWorkbook(workbook1, 10);
			// workbook.setCompressTempFiles(true);

			inputStream = new FileInputStream(new File(excelFilePath));
			// Workbook workbook = WorkbookFactory.create(inputStream);
			Workbook workbook = StreamingReader.builder().rowCacheSize(1000).bufferSize(2048).open(inputStream);

			Sheet firstSheet = workbook.getSheetAt(0);
			Iterator<Row> rowIterator = firstSheet.iterator();

			Connection conn = ds.getConnection();
			conn.setAutoCommit(false);

			// String sql = "INSERT INTO students (name, enrolled, progress) VALUES (?, ?,
			// ?)";
			PreparedStatement statement = conn.prepareStatement(sql);

			int count = 0;

			rowIterator.next(); // skip the header row

			while (rowIterator.hasNext()) {
				Row nextRow = rowIterator.next();
				Iterator<Cell> cellIterator = nextRow.cellIterator();

				while (cellIterator.hasNext()) {
					Cell nextCell = cellIterator.next();
					int columnIndex = nextCell.getColumnIndex();

					// getCellValueAndSetStatementForTXNDetail(columnIndex, nextCell, statement);
					getCellValueAndSetStatement(columnIndex, nextCell, statement, reportID);
				}

				statement.addBatch();

				if (count % batchSize == 0) {
					statement.executeBatch();
				}
			}

			workbook.close();

			// execute the remaining queries
			statement.executeBatch();

			conn.commit();
			//statement.close();
			//conn.close();

			// long end = System.currentTimeMillis();
			// System.out.printf("Import done in %d ms\n", (end - start));

			// result = "All data have been inserted Successfully!";

		} catch (IOException ex1) {
			System.out.println("Error reading file");
			ex1.printStackTrace();
		} catch (SQLException ex2) {
			System.out.println("Database error");
			ex2.printStackTrace();
		}

		// return result;
	}

	public void getCellValueAndSetStatement(int columnIndex, Cell nextCell, PreparedStatement statement,
			String reportID) {
		switch (reportID) {
		case "1399010301":
			getCellValueAndSetStatementForTXNDetail(columnIndex, nextCell, statement);
			break;
		case "1399010302":
			getCellValueAndSetStatementForSSNPayrollData(columnIndex, nextCell, statement);
			break;
		}
	}

	public void getCellValueAndSetStatementForTXNDetail(int columnIndex, Cell nextCell, PreparedStatement statement) {
		try {
			switch (columnIndex) {
			case 0:
				String mobileNo = nextCell.getStringCellValue();
				statement.setString(1, mobileNo);
				break;
			case 1:
				String txnType = nextCell.getStringCellValue();
				statement.setString(2, txnType);
				break;
			case 2:
				Date txnDate = nextCell.getDateCellValue();
				statement.setTimestamp(3, new Timestamp(txnDate.getTime()));
				break;
			case 3:
				/*
				 * int txnAmount = (int) nextCell.getNumericCellValue(); statement.setInt(4,
				 * txnAmount);
				 */
				Double txnAmount = (double) nextCell.getNumericCellValue();
				statement.setDouble(4, txnAmount);
			}
		} catch (SQLException ex2) {
			ex2.printStackTrace();
		}
	}

	public void getCellValueAndSetStatementForSSNPayrollData(int columnIndex, Cell nextCell,
			PreparedStatement statement) {
		try {
			switch (columnIndex) {
			case 0:
				int id = (int) nextCell.getNumericCellValue();
				statement.setInt(1, id);
				break;
			case 1:
				int bhataTypeId = (int) nextCell.getNumericCellValue();
				statement.setInt(2, bhataTypeId);
				break;
			case 2:
				String accountName = nextCell.getStringCellValue();
				statement.setString(3, accountName);
				break;
			case 3:
				int accountNumber = (int) nextCell.getNumericCellValue();
				statement.setInt(4, accountNumber);
				break;
			case 4:
				String accountType = nextCell.getStringCellValue();
				statement.setString(5, accountType);
				break;
			case 5:
				Double allowanceAmount = (double) nextCell.getNumericCellValue();
				statement.setDouble(6, allowanceAmount);
				break;
			case 6:
				String bankName = nextCell.getStringCellValue();
				statement.setString(7, bankName);
				break;
			case 7:
				String beneficiaryName = nextCell.getStringCellValue();
				statement.setString(8, beneficiaryName);
				break;
			case 8:
				String branchName = nextCell.getStringCellValue();
				statement.setString(9, branchName);
				break;
			case 9:
				int cycleId = (int) nextCell.getNumericCellValue();
				statement.setInt(10, cycleId);
				break;
			case 10:
				int district = (int) nextCell.getNumericCellValue();
				statement.setInt(11, district);
				break;
			case 11:
				int division = (int) nextCell.getNumericCellValue();
				statement.setInt(12, division);
				break;
			case 12:
				int eunion = (int) nextCell.getNumericCellValue();
				statement.setInt(13, eunion);
				break;
			case 13:
				String beneficiaryId = nextCell.getStringCellValue();
				statement.setString(14, beneficiaryId);
				break;
			case 14:
				int ministryCode = (int) nextCell.getNumericCellValue();
				statement.setInt(15, ministryCode);
				break;
			case 15:
				int mobile = (int) nextCell.getNumericCellValue();
				statement.setInt(16, mobile);
				break;
			case 16:
				int nid = (int) nextCell.getNumericCellValue();
				statement.setInt(17, nid);
				break;
			case 17:
				int paymentStatus = (int) nextCell.getNumericCellValue();
				statement.setInt(18, paymentStatus);
				break;
			case 18:
				String paymentType = nextCell.getStringCellValue();
				statement.setString(19, paymentType);
				break;
			case 19:
				int referenceNo = (int) nextCell.getNumericCellValue();
				statement.setInt(20, referenceNo);
				break;
			case 20:
				int routingNumber = (int) nextCell.getNumericCellValue();
				statement.setInt(21, routingNumber);
				break;
			case 21:
				int schemeCode = (int) nextCell.getNumericCellValue();
				statement.setInt(22, schemeCode);
				break;
			case 22:
				int status = (int) nextCell.getNumericCellValue();
				statement.setInt(23, status);
				break;
			case 23:
				int upazila = (int) nextCell.getNumericCellValue();
				statement.setInt(24, upazila);
				break;
			case 24:
				String bhataName = nextCell.getStringCellValue();
				statement.setString(25, bhataName);
				break;
			case 25:
				String cycleName = nextCell.getStringCellValue();
				statement.setString(26, cycleName);
				break;
			case 26:
				Date created_at = nextCell.getDateCellValue();
				statement.setTimestamp(27, new Timestamp(created_at.getTime()));
				break;
			case 27:
				Date insertDateTime = nextCell.getDateCellValue();
				statement.setTimestamp(28, new Timestamp(insertDateTime.getTime()));
				break;
			case 28:
				String divisionName = nextCell.getStringCellValue();
				statement.setString(29, divisionName);
				break;
			case 29:
				String districtName = nextCell.getStringCellValue();
				statement.setString(30, districtName);
				break;
			case 30:
				String upazilaName = nextCell.getStringCellValue();
				statement.setString(31, upazilaName);
			}
		} catch (SQLException ex2) {
			ex2.printStackTrace();
		}
	}	

	public void csvToDBInserter(String sql, String excelFilePathLocation, String fileName, String fileExtension,
			String reportID) {
		String csvFilePath = excelFilePathLocation + fileName.concat(fileExtension);
		int batchSize = 20;
		Connection conn = null;

		try {
			conn = ds.getConnection();
			conn.setAutoCommit(false);

			// String sql = "INSERT INTO students (name, enrolled, progress) VALUES (?, ?,
			// ?)";
			PreparedStatement statement = conn.prepareStatement(sql);

			BufferedReader lineReader = new BufferedReader(new FileReader(csvFilePath));
			String lineText = null;

			int count = 0;

			lineReader.readLine(); // skip header line

			while ((lineText = lineReader.readLine()) != null) {
				String[] data = lineText.split(",");

				getCsvCellValueAndSetStatement(data, statement, reportID);

				statement.addBatch();

				if (count % batchSize == 0) {
					statement.executeBatch();
				}
			}

			lineReader.close();

			// execute the remaining queries
			statement.executeBatch();

			conn.commit();
			//statement.close();
			//conn.close();

		} catch (IOException ex) {
			System.err.println(ex);
		} catch (SQLException ex) {
			ex.printStackTrace();

			try {
				conn.rollback();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	public void getCsvCellValueAndSetStatement(String[] data, PreparedStatement statement, String reportID) {
		try {
			switch (reportID) {
			case "1399010301":
				String mobileNo = data[0];
				String txnType = data[1];
				String txnDate = data[2];
				String txnAmount = data[3];
				// String comment = data.length == 5 ? data[4] : "";

				statement.setString(1, mobileNo);
				statement.setString(2, txnType);

				Timestamp sqlTimestamp = Timestamp.valueOf(txnDate);
				statement.setTimestamp(3, sqlTimestamp);

				/*
				 * Float fRating = Float.parseFloat(txnAmount); statement.setFloat(4, fRating);
				 */
				Double dTXNAmount = Double.parseDouble(txnAmount);
				statement.setDouble(4, dTXNAmount);

				// statement.setString(5, comment);

				break;
			}

		} catch (SQLException ex2) {
			ex2.printStackTrace();
		}
	}

}
