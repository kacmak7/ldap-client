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

public class DxfParser {
	private final Logger log = Logger.getLogger(getClass());
	DXFDocument doc = null;
	
	public DxfParser(InputStream stream, String encoding) {
		Parser parser = ParserBuilder.createDefaultParser();
		try {
			parser.parse(stream, encoding);
			doc = parser.getDocument();
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}
	
	public DxfParser(String sourceFile) {
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
	
	/*private void buildGraveRec(DXFEntity[] entitiesArr, DXFLine line, Grave grave, int i) {
		for (int z = (i + 1); z < entitiesArr.length; z++) {
			
			if (entitiesArr[z] != null) {
				
				DXFLine l = (DXFLine)entitiesArr[z];
				
				if (l.getStartPoint().equals(line.getEndPoint())) {
					if (!grave.containsPoint(l.getStartPoint()))
						grave.addPoint(l.getStartPoint());
					entitiesArr[z] = null;
					if (l.getEndPoint().equals(line.getStartPoint())) {
						Point p = l.getStartPoint();
						l.setStartPoint(l.getEndPoint());
						l.setEndPoint(p);
					}
					buildGraveRec(entitiesArr, l, grave, i);
					break;
				} else if (l.getEndPoint().equals(line.getEndPoint()) && !l.getStartPoint().equals(line.getStartPoint())) { //ktos nie po kolei narysowal linie
					if (!grave.containsPoint(l.getEndPoint()))
						grave.addPoint(l.getEndPoint());
					entitiesArr[z] = null;
					
					//zamieniamy miejscami startPoint i endPoint
					Point p = l.getStartPoint();
					l.setStartPoint(l.getEndPoint());
					l.setEndPoint(p);
					
					buildGraveRec(entitiesArr, l, grave, i);
					break;
				}
			}
		}  
	}*/
	
	private List<Point> findPoints(DXFLine[] lines, DXFLine l, List<Point> points) {
		//DXFLine l = (DXFLine)ent;
		//List<Point> points = new ArrayList<Point>();
		if (!points.contains(l.getStartPoint()))
			points.add(l.getStartPoint());
		if (!points.contains(l.getEndPoint()))
			points.add(l.getEndPoint());
		
		for (int i = 0; i < lines.length; i++) //TODO: optimize me
			if (l.equals(lines[i]))
				lines[i] = null;
				
		//DXFEntity next = null;
		for (DXFLine line : lines) {
			//if (line != null && (points.contains(line.getStartPoint())))
			//should add in appropriate order - start point of the lists to the last point
			if (line != null)
				if (line.getStartPoint().equals(points.get(points.size() - 1)) || (line.getEndPoint().equals(points.get(points.size() - 1)))) {
					findPoints(lines, line, points);
					break;
				}
		}
		return points;
	}
	
	public void buildLinesFromPolyline(List<DXFLine> entities, Iterator<DXFVertex> it, Point point) {
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
	 * Layer 0 contains grave numbers
	 */
	public List<Grave> getGraves() {
		Set<DXFLayer> layers = new HashSet<DXFLayer>();
		
		layers.add(doc.getDXFLayer("0"));
		layers.add(doc.getDXFLayer("BUDYNKI"));
		layers.add(doc.getDXFLayer("SYSTEM"));
	  
		DXFLine[] entitiesArr = findAllLines(layers);
		DXFEntity[] textArr = findAllNumbers(doc.getDXFLayer("0"));
		
		//mamy juz wszystkie linie, zakraglamy do centymetrow wspolrzedne wszystkich punktow
		for (DXFEntity ent : entitiesArr) {
			roundAllPoints(ent);
		}
		for (DXFEntity ent : textArr) {
			roundAllPoints(ent);
		}
	  
		List<Grave> graves = new ArrayList<Grave>();
		
		for (int i = 0; i < entitiesArr.length; i++) {
			if (entitiesArr[i] != null) {
				Grave grave = new Grave();
				//DXFLine line = (DXFLine) entitiesArr[i];
				//entitiesArr[i] = null;
				List<Point> points = findPoints(entitiesArr, entitiesArr[i], new ArrayList<Point>());
					
				//grave.addPoint(line.getStartPoint());
				//entitiesArr[i] = null;
				//buildGraveRec(entitiesArr, line, grave, i);
				
				//prawidlowy grob ma 4 wierzcholki
				
				//System.out.println("point " + grave.getPoints().size());
				if (points.size() == 4) {
					for (Point p: points) {
						grave.addPoint(p);
					}
					graves.add(grave);
				}
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
		
		/*for (DXFEntity entity : textArr) {
			DXFText text = (DXFText)entity;
			System.out.println(text.getText());
		}*/
		
		/*for (Grave g: graves) {
			if (g.getNumber() == 4756)
				System.out.println("Znaleziono!");
		}*/
		
		return graves;
	}
	
	private DXFLine[] findAllLines(Set<DXFLayer> layers) {
		List<DXFLine> entities = new ArrayList<DXFLine>();
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
		//return entities.toArray(new DXFEntity[0]);
		return entities.toArray(new DXFLine[0]);
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
			
			log.info("Line points " + line.getStartPoint().getX() + " " + line.getStartPoint().getY() + ", " + line.getEndPoint().getX() + " " + line.getEndPoint().getY());
		}
		
		//zaokraglanie wspolrzednych tesktu odbywa sie w grave.hasText
		
		/*else if (ent instanceof DXFText) {
			DXFText text = (DXFText)ent;
			log.debug(text.calculateAlignmentPoint());
			
			Point p = new Point();
			p.setX(Math.round(text.calculateAlignmentPoint().getX() * 100)/100d);
			p.setY(Math.round(text.calculateAlignmentPoint().getY() * 100)/100d);
			text.setRoundedAlignmentPoint(p);
			//text.setAlignX(Math.round(text.calculateAlignmentPoint().getX() * 100)/100d);
			//text.setAlignY(Math.round(text.calculateAlignmentPoint().getY() * 100)/100d);
			log.debug(text.getRoundedAlignmentPoint());
			
		} else {
			log.error("Obiekt nieoprawidlowy, nie jest ani linia, ani tekstem");
		}*/
	}
}