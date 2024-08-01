/**
 * 
 */
package com.banks.erp.sales.tmr.iservice;

import java.io.IOException;
import java.util.Map;

import net.sf.jasperreports.engine.JRException;

/**
 * @author Rajib Kumer Ghosh
 *
 */
public interface IPosmInformationReportService {
	public void exportReport(String reportSrcFilePath, Map<String, Object> parameters, String reportSrcFileName, Integer reportExportformat) throws JRException, IOException;

}
