package com.banks.erp.library.util.context;

import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.PhaseEvent;
import javax.faces.event.PhaseId;
import javax.faces.event.PhaseListener;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Rajib Kumer Ghosh
 *
 */

public class CacheControlPhaseListener implements PhaseListener {

	private static final long serialVersionUID = 1765819160012934855L;

	@Override
	public void afterPhase(PhaseEvent pe) {
	}

	@Override
	public void beforePhase(PhaseEvent pe) {
		FacesContext facesContext = pe.getFacesContext();
		ExternalContext externalContext = facesContext.getExternalContext();
		HttpServletResponse response = (HttpServletResponse) externalContext.getResponse();

		response.addHeader("Pragma", "no-cache");
		response.addHeader("Cache-Control", "no-cache");
		response.addHeader("Cache-Control", "no-store");
		response.addHeader("Cache-Control", "must-revalidate");
		response.addHeader("Expires", "0");
	}

	@Override
	public PhaseId getPhaseId() {
		return PhaseId.RENDER_RESPONSE;
	}

}
