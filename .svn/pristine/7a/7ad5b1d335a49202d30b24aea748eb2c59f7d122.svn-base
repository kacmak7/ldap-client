package org.parafia.util;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;
import org.kabeja.dxf.DXFConstants;
import org.kabeja.dxf.DXFDocument;
import org.kabeja.dxf.DXFEntity;
import org.kabeja.dxf.DXFLWPolyline;
import org.kabeja.dxf.DXFLayer;
import org.kabeja.dxf.DXFLine;
import org.kabeja.dxf.DXFText;
import org.kabeja.dxf.DXFVertex;
import org.kabeja.dxf.helpers.Point;
import org.kabeja.parser.ParseException;
import org.kabeja.parser.Parser;
import org.kabeja.parser.ParserBuilder;
import org.parafia.model.Grave;

@Deprecated
public class NewDxfParser {
	private final Logger log = Logger.getLogger(getClass());
	DXFDocument doc = null;
	
	public NewDxfParser(InputStream stream, String encoding) {
		Parser parser = ParserBuilder.createDefaultParser();
		try {
			parser.parse(stream, encoding);
			doc = parser.getDocument();
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}
	
	public NewDxfParser(String sourceFile) {
		Parser parser = ParserBuilder.createDefaultParser();
		try {
			if (sourceFile != null) {
				parser.parse(sourceFile);
				doc = parser.getDocument();
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}
	
	private void buildGraveRec(DXFEntity[] entitiesArr, DXFLine line, Grave grave, int i, String sql) {
		for (int z = (i + 1); z < entitiesArr.length; z++) {
			
			if (entitiesArr[z] != null) {
				
				DXFLine l = (DXFLine)entitiesArr[z];
				
				if (l.getStartPoint().equals(line.getEndPoint())) {
					//if (!grave.containsPoint(l.getStartPoint()))
					//	grave.addPoint(l.getStartPoint());
					sql += ","+l.getStartPoint().getX() + " " + l.getStartPoint().getY();
					entitiesArr[z] = null;
					buildGraveRec(entitiesArr, l, grave, i, sql);
					break;
				} else if (l.getEndPoint().equals(line.getEndPoint()) && !l.getStartPoint().equals(line.getStartPoint())) { //ktos nie po kolei narysowal linie
					//if (!grave.containsPoint(l.getEndPoint()))
					//	grave.addPoint(l.getEndPoint());
					sql += ","+l.getEndPoint().getX() + " " + l.getEndPoint().getY();
					entitiesArr[z] = null;
					
					//zamieniamy miejscami startPoint i endPoint
					Point p = l.getStartPoint();
					l.setStartPoint(l.getEndPoint());
					l.setEndPoint(p);
					
					buildGraveRec(entitiesArr, l, grave, i, sql);
					break;
				}
			}
		}  
	}
	
	public void buildLinesFromPolyline(List<DXFEntity> entities, Iterator<DXFVertex> it, Point point) {
		if (it.hasNext()) {
			DXFLine line = new DXFLine();
			line.setStartPoint(point);
			Point p = it.next().getPoint();
			line.setEndPoint(p);
			entities.add(line);
			buildLinesFromPolyline(entities, it, p);
		}
	}
  
	
	/**
	 * Budujemy linie z warstw 0 i budynki. Warstwa 0 zawiera takze numery grobow.
	 */
	public Set<Grave> initGraves() {
		Set<DXFLayer> layers = new HashSet<DXFLayer>();
		
		Set<Grave> graves = new HashSet<Grave>();
		
		layers.add(doc.getDXFLayer("0"));
		layers.add(doc.getDXFLayer("BUDYNKI"));
	  
		DXFEntity[] entitiesArr = findAllLines(layers);
		DXFEntity[] textArr = findAllNumbers(doc.getDXFLayer("0"));
		
		//mamy juz wszystkie linie, zakraglamy do centymetrow wspolrzedne wszystkich punktow
		for (DXFEntity ent : entitiesArr) {
			roundAllPoints(ent);
		}
		for (DXFEntity ent : textArr) {
			roundAllPoints(ent);
		}
		
		for (int i = 0; i < entitiesArr.length; i++) {
			if (entitiesArr[i] != null) {
				Grave grave = new Grave();
				
				//tymczasowo tylko, do korelacji ze Stringiem sql. Pozniej w kontrolerze ustawione na null 
				grave.setId(Long.valueOf(i));
				
				DXFLine line = (DXFLine) entitiesArr[i];
				
				String sql = "update graves set the_geom=GeometryFromText('POLYGON(((";
				sql += line.getStartPoint().getX() + " " + line.getStartPoint().getY();
				
				//grave.addPoint(line.getStartPoint());
				entitiesArr[i] = null;
				buildGraveRec(entitiesArr, line, grave, i, sql);
				
				sql += ")))') where id="+i+";";
				
				//grave.setSql(sql);
				//prawidlowy grob ma 4 wierzcholki
				//if (grave.getPoints().size() == 4)
				//	graves.add(grave);
			}
		}
		
		for (Grave grave : graves) {
			for (DXFEntity entity : textArr) {
				DXFText text = (DXFText)entity;
				if (grave.hasText(text)) {
					try {
						grave.setNumber(Integer.parseInt(text.getText()));
					} catch(NumberFormatException nfe) {
						log.error("Nie da sie sparsowac", nfe);
					}
					break;
				}
			}
		}
	  
		return graves;
	}
	
	private DXFEntity[] findAllLines(Set<DXFLayer> layers) {
		List<DXFEntity> entities = new ArrayList<DXFEntity>();
		for (DXFLayer layer : layers) {
			if (layer.getDXFEntities(DXFConstants.ENTITY_TYPE_LINE) != null)
				entities.addAll(layer.getDXFEntities(DXFConstants.ENTITY_TYPE_LINE));
			
			if (layer.getDXFEntities(DXFConstants.ENTITY_TYPE_LWPOLYLINE) != null) {
				for (Object ent : layer.getDXFEntities(DXFConstants.ENTITY_TYPE_LWPOLYLINE)) {
					DXFLWPolyline poly = (DXFLWPolyline)ent;
					//for (Iterator<DXFVertex> it = poly.getVertexIterator(); it.hasNext(); ) {
					Iterator<DXFVertex> it = poly.getVertexIterator();
					Point point = it.next().getPoint();
					buildLinesFromPolyline(entities, it, point);
				}
			}
		}
		return entities.toArray(new DXFEntity[0]);
	}
	
	private DXFEntity[] findAllNumbers(DXFLayer layer) {
		List<DXFEntity> entitiesText = new ArrayList<DXFEntity>();
		if (layer.getDXFEntities(DXFConstants.ENTITY_TYPE_TEXT) != null)
			entitiesText.addAll(layer.getDXFEntities(DXFConstants.ENTITY_TYPE_TEXT));
		//if (layer.getDXFEntities(DXFConstants.ENTITY_TYPE_MTEXT) != null)
		//	entities.addAll(layer.getDXFEntities(DXFConstants.ENTITY_TYPE_MTEXT));
		return entitiesText.toArray(new DXFEntity[0]);
	}
	
	private void roundAllPoints(DXFEntity ent) {
		if (ent instanceof DXFLine) {
			DXFLine line = (DXFLine)ent;
			line.getStartPoint().setX(Math.round(line.getStartPoint().getX() * 100)/100d);
			line.getEndPoint().setX(Math.round(line.getEndPoint().getX() * 100)/100d);
			line.getStartPoint().setY(Math.round(line.getStartPoint().getY() * 100)/100d);
			line.getEndPoint().setY(Math.round(line.getEndPoint().getY() * 100)/100d);
		}
	}
	
  	public static void main(String[] args) {
  		DXFLine line = new DXFLine();
  		Point p1 = new Point();
  		p1.setX(4444.32882);
  		p1.setY(-7265.97324);
  		line.setStartPoint(p1);
  		Point p2 = new Point();
  		p2.setX(4444.32882);
  		p2.setY(-7265.97324);
  		line.setEndPoint(p2);
  		
  		NewDxfParser parser = new NewDxfParser(null);
  		parser.roundAllPoints(line);
  		System.out.println(line.getStartPoint().getX());
  	}
}
