package org.parafia.model;

import java.awt.Polygon;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedNativeQueries;
import javax.persistence.NamedNativeQuery;
import javax.persistence.OneToMany;
import javax.persistence.Transient;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import org.kabeja.dxf.DXFText;
import org.kabeja.dxf.helpers.Point;
import org.parafia.model.dictionary.GraveDoubled;
import org.parafia.model.dictionary.GraveGrounded;

@NamedNativeQueries({
	@NamedNativeQuery(
		name = "findGraveWithGeometryLikeSQL",
		query = "select * from graves g where ST_Equals(g.the_geom, ST_GeomFromText(:postgisString))",
	        resultClass = Grave.class
	        //opposite to ST_OrderingEquals is ST_AsText
	)
})
@Entity
@DiscriminatorValue(value="1")
public class Grave extends GraveNiche {
	private static final long serialVersionUID = 684922108028611420L;
	
	private GraveDoubled doubled = new GraveDoubled();		//grob podwojny jesli 1, pojedynczy jesli 0
	private GraveGrounded grounded = new GraveGrounded();	//grob murowany jesli 1, ziemny jesli 0
	private String sector;
	private String postgisString;							//geometry w postaci POLYGON(X Y, X1 Y1, ...)

	private List<GraveLevel> levels = new ArrayList<GraveLevel>();
	
	private List<GravePoint> points = new ArrayList<GravePoint>();
	
	@ManyToOne(optional=false)
	@JoinColumn(name="grave_doubled_id", nullable=false)	
	public GraveDoubled getDoubled() {
		return doubled;
	}
	public void setDoubled(GraveDoubled doubled) {
		this.doubled = doubled;
	}
	
	@ManyToOne(optional=false)
	@JoinColumn(name="grave_grounded_id", nullable=false)
	public GraveGrounded getGrounded() {
		return grounded;
	}
	public void setGrounded(GraveGrounded grounded) {
		this.grounded = grounded;
	}
	
	@Column
	public String getSector() {
		return sector;
	}
	public void setSector(String sector) {
		this.sector = sector;
	}
	
	@LazyCollection(LazyCollectionOption.TRUE)
	@OneToMany(cascade=CascadeType.ALL, mappedBy="grave")
	@Cascade(value=org.hibernate.annotations.CascadeType.DELETE_ORPHAN)
	public List<GraveLevel> getLevels() {
		return levels;
	}
	public void setLevels(List<GraveLevel> levels) {
		this.levels = levels;
	}
	
	/*@LazyCollection(LazyCollectionOption.FALSE)
	@OneToMany(cascade=CascadeType.ALL, mappedBy="grave")
	@Cascade(value=org.hibernate.annotations.CascadeType.DELETE_ORPHAN)
	@Fetch(FetchMode.JOIN)*/
	@Transient
	public List<GravePoint> getPoints() {
		return points;
	}
	public void setPoints(List<GravePoint> points) {
		this.points = points;
	}
	
	@Transient
	public void addPoint(Point point) {
		GravePoint gPoint = new GravePoint();
		gPoint.setX(point.getX());
		gPoint.setY(point.getY());
		points.add(gPoint);
	}
	
	/**
	 * Wielokat zawiera text jesli punkt text.calculateAlignmentPoint() zawiera sie wewnatrz wielokata
	 * czyli dla wspolrzednej X textu mozna znalezc takie wierzcholki, ktorych x jest wiekszy i x jest mniejszy
	 * analogicznie z y
	 * @param DxfText
	 * @return boolean
	 */
	public boolean hasText(DXFText text) {
		//int i, j = 0;
		//boolean result = false;
		
		int z = 0;
		
		int polySides = this.points.size();
		int[] polyX = new int[polySides];
		int[] polyY = new int[polySides];
		
		for (GravePoint point : this.points) {
			polyX[z] = (int) (point.getX() * 100);
			polyY[z++] = (int) (point.getY() * 100);
		}
		
		int x = (int) Math.round(text.calculateAlignmentPoint().getX() * 100d);
		int y = (int) Math.round(text.calculateAlignmentPoint().getY() * 100d);
		
		/*int x1 = (int) Math.round(text.getInsertPoint().getX() * 100d);
		int y1 = (int) Math.round(text.getInsertPoint().getY() * 100d);
		
		boolean temp = new Polygon(polyX, polyY, polySides).contains(x1, y1);*/
		
		return new Polygon(polyX, polyY, polySides).contains(x, y);
		
		//this.points.get(0).get
		
	    /*for (i = 0, j = this.points.size() - 1; i < points.size(); j = i++) {
	      if ((this.points.get(i).getY() > y) != (this.points.get(j).getY() > y) &&
	          (x < (points.get(j).getX() - points.get(i).getX()) * (y - points.get(i).getY()) / (points.get(j).getY() - points.get(i).getY()) + points.get(i).getX()))
	    	  result = !result;
	    }*/
	    
		
		/*for (i = 0; i < polySides; i++) {
		    j++;
		    if (j == polySides)
		    	j = 0;
		    if (polyY[i] < y && polyY[j] >= y || polyY[j] < y && polyY[i] >= y) {
		    	if (polyX[i] + (y - polyY[i]) / (polyY[j] - polyY[i]) * (polyX[j] - polyX[i]) < x) {
		    		oddNODES = !oddNODES;
		    	}
		    }
		}*/
		//return result;
		//return false;
	}
	
	/*private double[] getArrayOfMinMaxXY(List<GravePoint> points) {
		double maxX = Double.MIN_VALUE;
		double minX = Double.MAX_VALUE;
		double maxY = Double.MIN_VALUE;
		double minY = Double.MAX_VALUE;
		for (GravePoint point : getPoints()) {
			if (point.getX() > maxX)
				maxX = point.getX();
			else if (point.getX() < minX)
				minX = point.getX();
			if (point.getY() > maxY)
				maxY = point.getY();
			else if (point.getY() < minY)
				minY = point.getY();
		}
		double[] result = new double[4];
		result[0] = maxX;
		result[1] = minX;
		result[2] = maxY;
		result[3] = minY;

		return result;
	}*/
	
	public boolean containsPoint(Point point) {
		for (GravePoint p : this.points) {
			if (p.getX() == point.getX() && p.getY() == point.getY())
				return true;
		}
		return false;
	}
	
	@Transient
	public String getPostgisString() {
		if (this.postgisString == null) {
			if (this.getPoints() != null && this.getPoints().size() > 0) {
				String ret = "POLYGON((";
				for (GravePoint point : this.getPoints()) {
					ret += point.getX() + " " + point.getY()+",";
				}
				ret += this.getPoints().get(0).getX() + " " + this.getPoints().get(0).getY();
				ret += "))";
				return ret;
			} else
				return null;
		} else
			return this.postgisString;
	}
	
	public void setPostgisString(String postgisString) {
		this.postgisString = postgisString;
	}
}
