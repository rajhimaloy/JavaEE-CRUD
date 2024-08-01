/**
 * 
 */
package com.banks.erp.library.util.crypto;

import javax.crypto.spec.SecretKeySpec;
import java.security.Key;

import javax.enterprise.context.Dependent;

/**
 * @author Rajib Kumer Ghosh
 *
 */
@Dependent
public class SimpleKeyGenerator implements KeyGenerator {

	// ======================================
    // =          Business methods          =
    // ======================================

    @Override
    public Key generateKey() {
        String keyString = "simplekey";
        Key key = new SecretKeySpec(keyString.getBytes(), 0, keyString.getBytes().length, "DES");
        return key;
    }
}
