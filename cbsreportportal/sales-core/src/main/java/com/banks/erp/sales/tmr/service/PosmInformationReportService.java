/**
 * 
 */
package com.banks.erp.sales.tmr.service;

import java.io.IOException;
import java.util.Map;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.transaction.Transactional.TxType;

import com.banks.erp.library.util.util.ReportUtil;
import com.banks.erp.sales.tmr.iservice.IPosmInformationReportService;

import net.sf.jasperreports.engine.JRException;

/**
 * @author Rajib Kumer Ghosh
 *
 */

@RequestScoped
@Transactional(TxType.SUPPORTS)
public class PosmInformationReportService implements IPosmInformationReportService {
	
	@Inject
	private ReportUtil reportUtil;

	@Override
	public void exportReport(String reportSrcFilePath, Map<String, Object> parameters, String reportSrcFileName, Integer reportExportformat) throws JRException, IOException {
		try {
			reportUtil.generateReport(reportSrcFilePath, parameters, reportSrcFileName, reportExportformat);			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
