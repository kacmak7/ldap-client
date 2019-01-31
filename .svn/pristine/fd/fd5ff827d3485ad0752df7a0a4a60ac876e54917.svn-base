package org.parafia.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.parafia.model.Grave;
import org.parafia.model.GravePoint;
import org.parafia.service.GraveyardManager;
import org.springframework.web.HttpRequestHandler;

public class GravesJSONBuilder implements HttpRequestHandler {
	private GraveyardManager graveyardManager;
	
	public void setGraveyardManager(GraveyardManager graveyardManager) {
		this.graveyardManager = graveyardManager;
	}

	private static final long serialVersionUID = 3346156285820955699L;
	
	private final Logger log = Logger.getLogger(getClass());
	
	public void handleRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		log.debug("GravesJSONController action begins");
			
		ServletOutputStream out = response.getOutputStream();
		
		//String json = org.parafia.util.JSONBuilder.buildJson(filePath, request.getContextPath());
		List<Grave> graves = graveyardManager.loadAll();
		
		int offsetX = -4850;
        int offsetY = 7910;
		
		StringBuffer json = new StringBuffer();
		
		//ponizej dobry json
		//json.append("{\"type\": \"FeatureCollection\",\"features\": [{\"geometry\": {\"type\": \"GeometryCollection\",\"geometries\": [{\"type\": \"Polygon\",\"coordinates\":[[[11.0878902207, 45.1602390564],[14.931640625, 40.9228515625],[0.8251953125, 41.0986328125],[11.0878902207, 45.1602390564]]]}]}, \"type\": \"Feature\", \"properties\": {}}]}");
		
		json.append("{\"type\": \"FeatureCollection\", \"features\": [{\"geometry\": {\"type\": \"GeometryCollection\", \"geometries\": [");
		//int number = 15000;
		for (Grave grave : graves) {
			json.append("{\"type\": \"Polygon\", \"coordinates\": [[");
			for (GravePoint point : grave.getPoints()) {
				json.append("[");
				json.append(point.getX() + offsetX);
				//json.append(point.getX());
				json.append(",");
				json.append(point.getY() + offsetY);
				//json.append(point.getY());
				json.append("],");
			}
			json.deleteCharAt(json.length() - 1);
			json.append("]]},");
			/*if (--number < 0)
				break;*/
		}
		json.deleteCharAt(json.length() - 1);
		json.append("]}, \"type\": \"Feature\", \"properties\": {}}]}");
		
		response.setContentLength(json.length());

		for (Character c : json.toString().toCharArray()) {
			out.write(c);
		}
		
		out.flush();
		out.close();
	}
}