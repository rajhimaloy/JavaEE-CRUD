/**
 * 
 */
package com.banks.erp.library.util.util;

import java.io.Serializable;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

import javax.enterprise.context.Dependent;
import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.transaction.Transactional.TxType;

/**
 * @author Rajib Kumer Ghosh
 *
 */
@Dependent
@Transactional(TxType.SUPPORTS)
public class ScheduledExecutorUtil implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private SendEmailUtil sendEmailUtil;

	@Inject
	private ExcelUtil excelUtil;

	@SuppressWarnings("unused")
	public void callScheduledExecutorTest() {
		ScheduledExecutorService ses = Executors.newScheduledThreadPool(1);

		Runnable task1 = () -> {
			// count++;
			// System.out.println("Running...task1 - count : " + count);
			sendEmailUtil.sendMailSSLTest();
		};

		// init Delay = 5 second, repeat the task every 10 second period
		// ScheduledFuture<?> scheduledFuture =
		// ses.scheduleAtFixedRate(sendEmailUtil.sendMailSSL(), 5, 10,
		// TimeUnit.SECONDS);
		ScheduledFuture<?> scheduledFuture = ses.scheduleAtFixedRate(task1, 5, 10, TimeUnit.SECONDS);

	}

	@SuppressWarnings("unused")
	public void callScheduledExecutor(String toMail, String mailSubject, String mailBody, long initialDelay, long period, TimeUnit unit) {
		ScheduledExecutorService ses = Executors.newScheduledThreadPool(1);

		Runnable task1 = () -> {
			sendEmailUtil.sendMailWithTextBody(toMail, mailSubject, mailBody);
		};

		ScheduledFuture<?> scheduledFuture = ses.scheduleAtFixedRate(task1, initialDelay, period, unit);
		// ses.shutdown();
		// scheduledFuture.cancel(true);

		try {
			ses.awaitTermination(1, TimeUnit.MINUTES);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@SuppressWarnings("unused")
	public void callScheduledExecutorWithAttachment(String toMail, String mailSubject, String mailBody, String attachment, long initialDelay, long period, TimeUnit unit) {
		ScheduledExecutorService ses = Executors.newScheduledThreadPool(1);

		Runnable task1 = () -> {
			sendEmailUtil.sendMailSSLWithAttachment(toMail, mailSubject, mailBody, attachment);
		};

		ScheduledFuture<?> scheduledFuture = ses.scheduleAtFixedRate(task1, initialDelay, period, unit);

		try {
			ses.awaitTermination(1, TimeUnit.MINUTES);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@SuppressWarnings("unused")
	public void callScheduledExecutorWithHtmlImageAttachment(String toMail, String ccMail, String mailSubject, String mailBody, String mailHTMLContent,
			String attachment, String attachmentImage, long initialDelay, long period, TimeUnit unit) {
		ScheduledExecutorService ses = Executors.newScheduledThreadPool(1);

		Runnable task1 = () -> {
			sendEmailUtil.sendMailSSLWithHtmlImageAttachment(toMail, ccMail, mailSubject,
					mailBody, mailHTMLContent, attachment, attachmentImage);
		};

		ScheduledFuture<?> scheduledFuture = ses.scheduleAtFixedRate(task1, initialDelay, period, unit);

		try {
			ses.awaitTermination(1, TimeUnit.MINUTES);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@SuppressWarnings("unused")
	public void callScheduledExecutorWithAutoExcelExportForHtmlImageAttachment(String sql, String fileName,
			String excelFilePathLocation, String toMail, String ccMail, String mailSubject, String mailBody, String mailHTMLContent, String attachmentImage,
			long initialDelay, long period, TimeUnit unit) {
		ScheduledExecutorService ses = Executors.newScheduledThreadPool(1);

		Runnable task1 = () -> {
			String attachment = excelUtil.exportToExcel(sql, fileName, excelFilePathLocation);
			sendEmailUtil.sendMailSSLWithHtmlImageAttachment(toMail, ccMail, mailSubject,
					mailBody, mailHTMLContent, attachment, attachmentImage);
		};

		ScheduledFuture<?> scheduledFuture = ses.scheduleAtFixedRate(task1, initialDelay, period, unit);

		try {
			ses.awaitTermination(1, TimeUnit.MINUTES);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	// RA, BI Portal
	@SuppressWarnings("unused")
	public void callScheduledExecutorWithAutoExcelExportForHtmlTableAndAttachment(String reportID, String sql, String fileName,
			String excelFilePathLocation, String mailSubject, String mailHTMLContent, long initialDelay, long period, TimeUnit unit) {
		ScheduledExecutorService ses = Executors.newScheduledThreadPool(1);

		Runnable task1 = () -> {
			String attachment = excelUtil.exportToExcel(sql, fileName, excelFilePathLocation);
			try {
				sendEmailUtil.sendMailWithHtmlTableAndAttachment(reportID, mailSubject,
						mailHTMLContent, attachment);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		};

		ScheduledFuture<?> scheduledFuture = ses.scheduleAtFixedRate(task1, initialDelay, period, unit);

		try {
			ses.awaitTermination(10, TimeUnit.SECONDS);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	// RA
	@SuppressWarnings("unused")
	public void callScheduledExecutorWithAutoExcelExportForHtmlTableAndMultiAttachment(String reportID, String sql1, String fileName1,
			String sql2, String fileName2, String excelFilePathLocation, String mailSubject, String mailHTMLContent,
			long initialDelay, long period, TimeUnit unit) {
		ScheduledExecutorService ses = Executors.newScheduledThreadPool(1);

		Runnable task1 = () -> {
			String attachment1 = excelUtil.exportToExcel(sql1, fileName1, excelFilePathLocation);
			String attachment2 = excelUtil.exportToExcel(sql2, fileName2, excelFilePathLocation);
			try {
				sendEmailUtil.sendMailWithHtmlTableAndMultiAttachment(reportID,
						mailSubject, mailHTMLContent, attachment1, attachment2);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		};

		ScheduledFuture<?> scheduledFuture = ses.scheduleAtFixedRate(task1, initialDelay, period, unit);

		try {
			ses.awaitTermination(1, TimeUnit.MINUTES);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	//Big Data, Prism Portal
	@SuppressWarnings("unused")
	public void callScheduledExecutorWithBigDataAutoExcelExportForHtmlTableAndAttachment(String reportID, String sql, String fileName,
			String excelFilePathLocation, String mailSubject, String mailHTMLContent, long initialDelay, long period, TimeUnit unit) {
		ScheduledExecutorService ses = Executors.newScheduledThreadPool(1);

		Runnable task1 = () -> {
			String attachment = excelUtil.exportBigDataToExcel(sql, fileName, excelFilePathLocation);
			try {
				sendEmailUtil.sendMailWithHtmlTableAndAttachment(reportID, mailSubject,
						mailHTMLContent, attachment);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		};

		ScheduledFuture<?> scheduledFuture = ses.scheduleAtFixedRate(task1, initialDelay, period, unit);

		try {
			ses.awaitTermination(10, TimeUnit.SECONDS);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public List<Runnable> getAndShutdownNotExecutedTasks() {
		ScheduledExecutorService ses = Executors.newScheduledThreadPool(100);
		List<Runnable> notExecutedTasks = ses.shutdownNow();
		return notExecutedTasks;
	}
}
