/**
 * 
 */
package com.banks.erp.library.util.crypto;

import java.security.MessageDigest;
import java.util.Base64;

import javax.enterprise.context.Dependent;

/**
 * @author Rajib Kumer Ghosh
 *
 */
@Dependent
public class PasswordUtil {

    // ======================================
    // =          Business methods          =
    // ======================================

    public static String digestPassword(String plainTextPassword) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            md.update(plainTextPassword.getBytes("UTF-8"));
            byte[] passwordDigest = md.digest();
            return new String(Base64.getEncoder().encode(passwordDigest));
        } catch (Exception e) {
            throw new RuntimeException("Exception encoding password", e);
        }
    }

}
