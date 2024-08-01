/**
 * 
 */
package com.banks.erp.library.util.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * @author Rajib_Ghosh
 * https://www.baeldung.com/java-compress-and-uncompress
 *
 */
public class ZipUtil {
	
	static void zip(File fileToZip, String zipExtension) throws IOException {
		
		zipExtension =  "." + ((zipExtension != null && !zipExtension.isEmpty()) ? zipExtension.replace(".", "") : "zip");

        if (!fileToZip.isFile()) {
            return;
        }

        String sourceFileName = fileToZip.getName();
        String extensionName = sourceFileName.substring(sourceFileName.lastIndexOf('.'));
        String sourceFileNameWithoutExtension = fileToZip.getName().replace(extensionName, "");
        String sourceFilePath = fileToZip.getAbsolutePath().replace(sourceFileName, "");
        FileOutputStream fos = new FileOutputStream( sourceFilePath + sourceFileNameWithoutExtension + zipExtension);
        ZipOutputStream zipOut = new ZipOutputStream(fos);
        FileInputStream fis = new FileInputStream(fileToZip);
        ZipEntry zipEntry = new ZipEntry(fileToZip.getName());
        zipOut.putNextEntry(zipEntry);

        byte[] bytes = new byte[1024];
        int length;

        while ((length = fis.read(bytes)) >= 0) {
            zipOut.write(bytes, 0, length);
        }

        zipOut.close();
        fis.close();
        fos.close();
    }

}
