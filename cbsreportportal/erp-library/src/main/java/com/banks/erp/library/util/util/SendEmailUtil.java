/**
 * 
 */
package com.banks.erp.library.util.util;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.sql.DataSource;
import javax.transaction.Transactional;
import javax.transaction.Transactional.TxType;

import com.banks.erp.library.util.dto.MailConfigurationDTO;
import com.banks.erp.library.util.persistence.CollectionDao;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.annotation.Resource;
import javax.enterprise.context.Dependent;
import javax.inject.Inject;

/**
 * @author Rajib Kumer Ghosh
 *
 */
@Dependent
@Transactional(TxType.SUPPORTS)
public class SendEmailUtil implements Serializable {

	private static final long serialVersionUID = 1L;

	@Resource(name = "rajibkg_ds_oracle_rpt")
	private DataSource ds;

	@Inject
	private CollectionDao collectionDao;

	// private String smtpServer = "smtp.gmail.com";
	private String smtpServer = "smtp.office365.com";
	private String smtpTLSPort = "587";
	private String smtpSSLPort = "465";
	private String smtpAuth = "true";
	private String smtpTLSEnable = "true";
	private String smtpFactorySSLPort = "465";
	private String smtpFactorySSLClass = "javax.net.ssl.SSLSocketFactory";
	private String smtpType = "TLS"; //"SSL";

	private String getSendMailUserName() {
		// Sending the Mail through Scheduler Authentication Parameter.
		String reportID = "1399990201";
		MailConfigurationDTO mailConfigurationDTO = null;
		String userName = null;
		try {
			mailConfigurationDTO = getMailConfigurationDTO(reportID);
			userName = mailConfigurationDTO.getMailUserName();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		userName = "bi.reports@nagad.com.bd";// "rajib.ghosh@nagad.com.bd";
		return userName;
	}

	private String getSendMailPassword() {
		// Sending the Mail through Scheduler Authentication Parameter.
		String reportID = "1399990201";
		MailConfigurationDTO mailConfigurationDTO = null;
		String password = null;
		try {
			mailConfigurationDTO = getMailConfigurationDTO(reportID);
			password = mailConfigurationDTO.getMailPassword();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		password = "0*Q#bRIc1e&Lb4P!cR";// "Nagad@2014";
		return password;
	}

	public String getFromMail() {
		String reportID = "1399990201";
		MailConfigurationDTO mailConfigurationDTO = null;
		String fromMail = null;
		try {
			mailConfigurationDTO = getMailConfigurationDTO(reportID);
			fromMail = mailConfigurationDTO.getFromMail();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		fromMail = "bi.reports@nagad.com.bd";
		return fromMail;
	}
	
	public MailConfigurationDTO getMailConfigurationDTO(String reportID) throws Exception {	
		String sql = "SELECT M.REPORTID, R.RDOREPORTNAME, M.MAILGROUPID, M.MAILGROUPNAME, S.MAILUSERNAME, S.MAILPASSWORD, S.FROMMAIL, S.TOMAIL, S.CCMAIL \r\n" + 
				"FROM SYS_REPORTWISEMAILGROUPMAP M \r\n" + 
				"INNER JOIN SYS_MAILGROUPSETUP S ON S.MAILGROUPID = M.MAILGROUPID \r\n" + 
				"INNER JOIN SYS_REPORTDISPLAYOPTION R ON R.RDOREPORTID = M.REPORTID \r\n" + 
				"WHERE M.REPORTID = ? AND M.STATUSID = 'A' AND S.STATUSID = 'A' AND R.STATUSID = 'A' ";
		
		HashMap<Integer, Object> params = new HashMap<Integer, Object>();
		params.put(1, reportID);	//searched for first occurrence of ? in whole Query, hence used 1. For second occurrence of ? will be 2 and so on. 
		
		MailConfigurationDTO mailConfigurationDTO = new MailConfigurationDTO();
		ResultSet resultSet = null;		
		try {
			resultSet = collectionDao.executeQueryResultSetWithParam(sql, params);
			while(resultSet.next()) {
				
				mailConfigurationDTO.setReportID(resultSet.getString(1));
				mailConfigurationDTO.setReportName(resultSet.getString(2));
				mailConfigurationDTO.setMailGroupID(resultSet.getString(3));
				mailConfigurationDTO.setMailGroupName(resultSet.getString(4));
				mailConfigurationDTO.setMailUserName(resultSet.getString(5));
				mailConfigurationDTO.setMailPassword(resultSet.getString(6));
				mailConfigurationDTO.setFromMail(resultSet.getString(7));
				mailConfigurationDTO.setToMail(resultSet.getString(8));
				mailConfigurationDTO.setCcMail(resultSet.getString(9));
			}
			
			//resultSet.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return mailConfigurationDTO;		
	}

	public Properties getSendMailProperties(String sslTlsType) {
		Properties prop = new Properties();

		if (sslTlsType.equalsIgnoreCase("SSL")) {
			prop.put("mail.smtp.host", smtpServer);
			prop.put("mail.smtp.port", smtpSSLPort);
			prop.put("mail.smtp.auth", smtpAuth);
			prop.put("mail.smtp.socketFactory.port", smtpFactorySSLPort);
			prop.put("mail.smtp.socketFactory.class", smtpFactorySSLClass);
		} else {
			prop.put("mail.smtp.host", smtpServer);
			prop.put("mail.smtp.port", smtpTLSPort);
			prop.put("mail.smtp.auth", smtpAuth);
			prop.put("mail.smtp.starttls.enable", smtpTLSEnable);
		}

		return prop;
	}

	//R&D
	public void sendMailTLS() {
		// final String username = "eng.rajibkumerghosh@gmail.com";
		// final String password = "*******";
		final String username = "rajib.ghosh@nagad.com.bd";
		final String password = "Nagad@2014";

		// final String fromMail = "eng.rajibkumerghosh@gmail.com";
		final String fromMail = "rajib.ghosh@nagad.com.bd";
		// final String toMail = "rajib.ghosh@nagad.com.bd, mamunur.rashid@nagad.com.bd,
		// ziko.mandal@nagad.com.bd";
		final String toMail = "rajib.ghosh@nagad.com.bd";
		final String mailSubject = "Auto mail from Application Using Gmail SSL via Java Mail";
		final String mailBody = "Dear Hira Bhai,"
				+ "\n\n Please do not spam my email!. This is the testing auto mail from Application Using Gmail SSL via Java Mail";

		Properties prop = new Properties();
		prop.put("mail.smtp.host", smtpServer);
		prop.put("mail.smtp.port", smtpTLSPort);
		prop.put("mail.smtp.auth", smtpAuth);
		prop.put("mail.smtp.starttls.enable", smtpTLSEnable);

		Session session = Session.getInstance(getSendMailProperties("TLS"), new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(username, password);
			}
		});

		try {

			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress(fromMail));
			message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toMail));
			message.setSubject(mailSubject);
			message.setText(mailBody);

			Transport.send(message);

		} catch (MessagingException e) {
			e.printStackTrace();
		}

	}

	//R&D
	public void sendMailSSLTest() {

		// final String fromMail = "eng.rajibkumerghosh@gmail.com";
		final String fromMail = "rajib.ghosh@nagad.com.bd";
		// final String toMail = "rajib.ghosh@nagad.com.bd, mamunur.rashid@nagad.com.bd,
		// ziko.mandal@nagad.com.bd";
		final String toMail = "rajib.ghosh@nagad.com.bd";
		final String mailSubject = "Auto mail from Application Using Gmail SSL via Java Mail";
		final String mailBody = "Dear Hira Bhai,"
				+ "\n\n Please do not spam my email!. This is the testing auto mail from Application Using Gmail SSL via Java Mail";

		/*
		 * Properties prop = new Properties(); String path =
		 * System.getProperty("user.dir")+"/SendEmailUtil.properties"; try {
		 * prop.load(new FileInputStream(path)); } catch (FileNotFoundException e1) {
		 * e1.printStackTrace(); } catch (IOException e1) { e1.printStackTrace(); }
		 */

		Session session = Session.getInstance(getSendMailProperties("SSL"), new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(getSendMailUserName(), getSendMailPassword());
			}
		});

		try {

			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress(getFromMail()));
			message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toMail));

			message.setSubject(mailSubject);
			message.setText(mailBody);

			Transport.send(message);

		} catch (MessagingException e) {
			e.printStackTrace();
		}

		// Please allow/On "Less Secure App" On from
		// Gmail/(https://myaccount.google.com) -> security -> "Less Secure App" -> On
	}

	//Online Registration
	public void sendMailWithTextBody(String toMail, String mailSubject, String mailBody) {

		Session session = Session.getInstance(getSendMailProperties(smtpType), new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(getSendMailUserName(), getSendMailPassword());
			}
		});

		try {

			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress(getFromMail()));
			message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toMail));

			message.setSubject(mailSubject);
			message.setText(mailBody);

			Transport.send(message);

		} catch (MessagingException e) {
			e.printStackTrace();
		}
	}

	public void sendMailSSLWithAttachment(String toMail, String mailSubject, String mailBody, String attachment) {

		Session session = Session.getInstance(getSendMailProperties("SSL"), new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(getSendMailUserName(), getSendMailPassword());
			}
		});

		try {

			InternetAddress fromAddress = new InternetAddress(getFromMail());
			InternetAddress toAddress = new InternetAddress(toMail);

			// Create an Internet mail msg.
			MimeMessage message = new MimeMessage(session);
			message.setFrom(fromAddress);
			message.setRecipient(Message.RecipientType.TO, toAddress);
			message.setSubject(mailSubject);
			message.setSentDate(new Date());

			// Set the email msg text.
			MimeBodyPart messagePart = new MimeBodyPart();
			messagePart.setText(mailBody);

			// Set the email attachment Excel file
			FileDataSource fileDataSource = new FileDataSource(attachment);

			MimeBodyPart attachmentPart = new MimeBodyPart();
			attachmentPart.setDataHandler(new DataHandler(fileDataSource));
			attachmentPart.setFileName(fileDataSource.getName());

			// Create Multipart E-Mail.
			Multipart multipart = new MimeMultipart();
			multipart.addBodyPart(messagePart);
			multipart.addBodyPart(attachmentPart);

			message.setContent(multipart);

			Transport.send(message);

		} catch (MessagingException e) {
			e.printStackTrace();
		}
	}

	public void sendMailSSLWithHtmlImageAttachment(String toMail, String ccMail, String mailSubject, String mailBody, String mailHTMLContent, String attachment,
			String attachmentImage) {

		Session session = Session.getInstance(getSendMailProperties("SSL"), new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(getSendMailUserName(), getSendMailPassword());
			}
		});

		try {

			InternetAddress fromAddress = new InternetAddress(getFromMail());
			InternetAddress toAddress = new InternetAddress(toMail);

			// Create an Internet mail msg.
			MimeMessage message = new MimeMessage(session);
			message.setFrom(fromAddress);
			message.setRecipient(Message.RecipientType.TO, toAddress);
			if (ccMail != null && !ccMail.isEmpty()) {
				message.setRecipients(Message.RecipientType.CC, InternetAddress.parse(ccMail));
			}
			message.setSubject(mailSubject);
			message.setSentDate(new Date());

			// Set the email msg text.
			MimeBodyPart messagePart = new MimeBodyPart();
			messagePart.setText(mailBody);
			messagePart.setContent(mailHTMLContent, "text/html");

			// Set the email attachment Excel file
			FileDataSource fileDataSource = new FileDataSource(attachment);

			MimeBodyPart attachmentPart = new MimeBodyPart();
			attachmentPart.setDataHandler(new DataHandler(fileDataSource));
			attachmentPart.setFileName(fileDataSource.getName());
			// attachmentPart.setDisposition(MimeBodyPart.INLINE);
			// attachmentPart.setDisposition(MimeBodyPart.ATTACHMENT);

			// Set the email attachment Image file
			FileDataSource fileDataSourceImage = new FileDataSource(attachmentImage);

			MimeBodyPart attachmentPartImage = new MimeBodyPart();
			attachmentPartImage.setDataHandler(new DataHandler(fileDataSourceImage));
			attachmentPartImage.setFileName(fileDataSourceImage.getName());
			attachmentPartImage.setHeader("Content-ID", "<image>");

			// Create Multipart E-Mail.
			Multipart multipart = new MimeMultipart();
			multipart.addBodyPart(messagePart);
			multipart.addBodyPart(attachmentPart);
			multipart.addBodyPart(attachmentPartImage);

			message.setContent(multipart);

			Transport.send(message);

		} catch (MessagingException e) {
			e.printStackTrace();
		}
	}

	// RA, BI Portal, Prism Portal
	public void sendMailWithHtmlTableAndAttachment(String reportID, String mailSubject, String mailHTMLContent, String attachment) throws Exception {

		MailConfigurationDTO mailConfigurationDTO = getMailConfigurationDTO(reportID);
		
		Session session = Session.getInstance(getSendMailProperties(smtpType), new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(mailConfigurationDTO.getMailUserName(), mailConfigurationDTO.getMailPassword());
			}
		});

		try {

			InternetAddress fromAddress = new InternetAddress(mailConfigurationDTO.getFromMail());
			InternetAddress toAddress = new InternetAddress(mailConfigurationDTO.getToMail());

			// Create an Internet mail msg.
			MimeMessage message = new MimeMessage(session);
			message.setFrom(fromAddress);
			message.setRecipient(Message.RecipientType.TO, toAddress);
			if (mailConfigurationDTO.getCcMail() != null && !mailConfigurationDTO.getCcMail().isEmpty()) {
				message.setRecipients(Message.RecipientType.CC, InternetAddress.parse(mailConfigurationDTO.getCcMail()));
			}
			message.setSubject(mailSubject);
			message.setSentDate(new Date());

			// Set the email msg text.
			MimeBodyPart messagePart = new MimeBodyPart();
			messagePart.setContent(mailHTMLContent, "text/html");

			// Set the email attachment Excel file
			FileDataSource fileDataSource = new FileDataSource(attachment);
			MimeBodyPart attachmentPart = new MimeBodyPart();
			attachmentPart.setDataHandler(new DataHandler(fileDataSource));
			attachmentPart.setFileName(fileDataSource.getName());

			// Create Multipart E-Mail.
			Multipart multipart = new MimeMultipart();
			multipart.addBodyPart(messagePart);
			multipart.addBodyPart(attachmentPart);

			message.setContent(multipart);

			Transport.send(message);

		} catch (MessagingException e) {
			e.printStackTrace();
		}
	}

	// RA
	public void sendMailWithHtmlTableAndMultiAttachment(String reportID, String mailSubject, String mailHTMLContent, String attachment1,
			String attachment2) throws Exception {

		MailConfigurationDTO mailConfigurationDTO = getMailConfigurationDTO(reportID);
		
		Session session = Session.getInstance(getSendMailProperties(smtpType), new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(mailConfigurationDTO.getMailUserName(), mailConfigurationDTO.getMailPassword());
			}
		});

		try {

			InternetAddress fromAddress = new InternetAddress(mailConfigurationDTO.getFromMail());
			InternetAddress toAddress = new InternetAddress(mailConfigurationDTO.getToMail());

			// Create an Internet mail msg.
			MimeMessage message = new MimeMessage(session);
			message.setFrom(fromAddress);
			message.setRecipient(Message.RecipientType.TO, toAddress);
			if (mailConfigurationDTO.getCcMail() != null && !mailConfigurationDTO.getCcMail().isEmpty()) {
				message.setRecipients(Message.RecipientType.CC, InternetAddress.parse(mailConfigurationDTO.getCcMail()));
			}
			message.setSubject(mailSubject);
			message.setSentDate(new Date());

			// Set the email msg text.
			MimeBodyPart messagePart = new MimeBodyPart();
			messagePart.setContent(mailHTMLContent, "text/html");

			// Set the email attachment-1 Excel file
			FileDataSource fileDataSource1 = new FileDataSource(attachment1);
			MimeBodyPart attachmentPart1 = new MimeBodyPart();
			attachmentPart1.setDataHandler(new DataHandler(fileDataSource1));
			attachmentPart1.setFileName(fileDataSource1.getName());

			// Set the email attachment-2 Excel file
			FileDataSource fileDataSource2 = new FileDataSource(attachment2);
			MimeBodyPart attachmentPart2 = new MimeBodyPart();
			attachmentPart2.setDataHandler(new DataHandler(fileDataSource2));
			attachmentPart2.setFileName(fileDataSource2.getName());

			// Create Multipart E-Mail.
			Multipart multipart = new MimeMultipart();
			multipart.addBodyPart(messagePart);
			multipart.addBodyPart(attachmentPart1);
			multipart.addBodyPart(attachmentPart2);

			message.setContent(multipart);

			Transport.send(message);

		} catch (MessagingException e) {
			e.printStackTrace();
		}
	}

	// Failed Notify, BI Portal
	public void sendMailWithHtmlBody(String toMail, String ccMail, String mailSubject, String mailHTMLContent) {

		Session session = Session.getInstance(getSendMailProperties(smtpType), new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(getSendMailUserName(), getSendMailPassword());
			}
		});

		try {

			InternetAddress fromAddress = new InternetAddress(getFromMail());
			InternetAddress toAddress = new InternetAddress(toMail);

			// Create an Internet mail msg.
			MimeMessage message = new MimeMessage(session);
			message.setFrom(fromAddress);
			message.setRecipient(Message.RecipientType.TO, toAddress);
			if (ccMail != null && !ccMail.isEmpty()) {
				message.setRecipients(Message.RecipientType.CC, InternetAddress.parse(ccMail));
			}
			message.setSubject(mailSubject);
			message.setSentDate(new Date());

			// Set the email msg text.
			MimeBodyPart messagePart = new MimeBodyPart();
			messagePart.setContent(mailHTMLContent, "text/html");

			// Create Multipart E-Mail.
			Multipart multipart = new MimeMultipart();
			multipart.addBodyPart(messagePart);

			message.setContent(multipart);

			Transport.send(message);

		} catch (MessagingException e) {
			e.printStackTrace();
		}
	}

}
