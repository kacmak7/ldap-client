package org.parafia.servlet;

import java.io.IOException;
import java.sql.BatchUpdateException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.parafia.model.Grave;
import org.parafia.service.GraveyardManager;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

public class DeleteGraves extends javax.servlet.http.HttpServlet implements javax.servlet.Servlet {
	private static final long serialVersionUID = 2826667749475119030L;

	private final Logger log = Logger.getLogger(getClass());
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		log.debug("DeleteGraves action begins");
		
		ApplicationContext context = WebApplicationContextUtils.getWebApplicationContext(getServletContext());
		GraveyardManager graveyardManager = (GraveyardManager)context.getBean("graveyardManager");
			
		ServletOutputStream out = response.getOutputStream();
		
		//String json = org.parafia.util.JSONBuilder.buildJson(filePath, request.getContextPath());
		List<Grave> graves = graveyardManager.loadAllWithFiles();
		
		//grave to jest grob prawdziwy, otherGrave - niepotrzebnie dodany
		for (Grave grave : graves) {
			for (Grave otherGrave : graves) {
				if (otherGrave.getNumber() == grave.getNumber() &&
					otherGrave.getId() > grave.getId() &&
					otherGrave.getFiles().size() == 0 && grave.getFiles().size() > 0 &&
					otherGrave.getPoints().equals(grave.getPoints())) {
					
					graveyardManager.deleteGrave(otherGrave.getId());
				}
			}
		}
		
		/*response.setContentLength(json.length());

		for (Character c : json.toString().toCharArray()) {
			out.write(c);
		}*/
		
		out.flush();
		out.close();
	}
}