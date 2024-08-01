/**
 * 
 */
package com.banks.erp.sales.tmr.iservice;

import java.io.IOException;
import java.util.Map;

import net.sf.jasperreports.engine.JRException;

/**
 * @author Rajib_Ghosh
 *
 */
public interface IMixTmrInformationReportService {
	public void exportReport(String reportSrcFilePath, Map<String, Object> parameters, String reportSrcFileName, Integer reportExportformat) throws JRException, IOException;

}
