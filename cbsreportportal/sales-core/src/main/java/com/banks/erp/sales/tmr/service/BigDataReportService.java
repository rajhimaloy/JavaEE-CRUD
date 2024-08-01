/**
 * 
 */
package com.banks.erp.sales.tmr.service;

import javax.enterprise.context.RequestScoped;
import javax.transaction.Transactional;
import javax.transaction.Transactional.TxType;

import com.banks.erp.sales.tmr.iservice.IBigDataReportService;

/**
 * @author Rajib_Ghosh
 *
 */
@RequestScoped
//@Transactional(TxType.SUPPORTS)
public class BigDataReportService implements IBigDataReportService {
	
	/*private StreamedContent file;

    public BigDataReportService() {
        file = DefaultStreamedContent.builder()
                .name("downloaded_boromir.jpg")
                .contentType("image/jpg")
                .stream(() -> FacesContext.getCurrentInstance().getExternalContext().getResourceAsStream("/resources/demo/images/boromir.jpg"))
                .build();
    }

    @Override
    public StreamedContent getFile() {
        return file;
    }*/

}
