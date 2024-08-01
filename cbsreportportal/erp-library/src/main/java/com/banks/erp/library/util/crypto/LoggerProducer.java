/**
 * 
 */
package com.banks.erp.library.util.crypto;

import javax.enterprise.inject.Produces;
import javax.enterprise.inject.spi.InjectionPoint;
import java.util.logging.Logger;

import javax.enterprise.context.Dependent;

/**
 * @author Rajib Kumer Ghosh
 *
 */
@Dependent
public class LoggerProducer {

	// ======================================
    // =              Producers             =
    // ======================================

    @Produces
    public Logger produceLogger(InjectionPoint injectionPoint) {
        return Logger.getLogger(injectionPoint.getMember().getDeclaringClass().getName());
    }
}
