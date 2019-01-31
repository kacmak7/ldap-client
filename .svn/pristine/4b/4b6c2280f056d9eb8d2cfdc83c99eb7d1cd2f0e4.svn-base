Tomcat will use ISO-8859-1 as the default character encoding of the entire URL, including the query string ("GET parameters").
There are two ways to specify how GET parameters are interpreted:
    Set the URIEncoding attribute on the <Connector> element in server.xml to something specific (e.g. URIEncoding="UTF-8").
    Set the useBodyEncodingForURI attribute on the <Connector> element in server.xml to true. This will cause the Connector to use the request body's encoding for GET parameters.
    
    
1. I added URIEncoding="UTF-8" to the line <Connector connectionTimeout="20000" port="8080" protocol="HTTP/1.1" redirectPort="8443" URIEncoding="UTF-8"/> of server.xml of Tomcat server
2. I changed tomcat/conf/context.xml --> the line <Context useHttpOnly="false"> instead <Context>
3. Set max memory to at least 1024 MB
4. Add to tomcat/conf/web.xml of GEOSERVER tomcat:

	<filter>
	  <filter-name>CorsFilter</filter-name>
	  <filter-class>org.apache.catalina.filters.CorsFilter</filter-class>
	  <init-param>
	    <param-name>cors.allowed.origins</param-name>
	    <param-value>*</param-value>
	  </init-param>
	</filter>
	<filter-mapping>
	  <filter-name>CorsFilter</filter-name>
	  <url-pattern>/*</url-pattern>
	</filter-mapping>

5. Run in eclipse:
 - import as a maven project
 - add Project Facets to the project: Dynamic Web Module 2.5 and Java
 - Project properties -> Deployment Assembly -> Add -> Java Build Path Entries -> Maven Dependencies
 - All test classes/resources removed from Deploment Assembly
6. user admin pass 20admin17
7. To show graves in LibreCAD I needed to select View -> Autozoom after loading a dxf file
8. Geoserver(Login: 'admin' password: 'geoserver'), Login and password are default:
	- add new workspace
	- add new store
	- add new layer
		* set Declared SRS: EPSG:3857
		* set SRS handling: Force declared
		* bounding Boxes - compute from data
	- add new styles ('graves', 'redgraves')
9. To export as png use http://localhost:8081/geoserver/wms?SERVICE=WMS&VERSION=1.3.0&REQUEST=GetMap&FORMAT=image/png&TRANSPARENT=true&LAYERS=graves:graves&TILED=true&STYLES=graves&WIDTH=4096&HEIGHT=4096&CRS=EPSG:3857&BBOX=4752.7,-8003.55,4965.6900000000005,-7770.5599999999995


style graves:

<?xml version="1.0" encoding="UTF-8"?>
<StyledLayerDescriptor version="1.0.0" xmlns="http://www.opengis.net/sld" xmlns:ogc="http://www.opengis.net/ogc"
  xmlns:xlink="http://www.w3.org/1999/xlink" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
  xsi:schemaLocation="http://www.opengis.net/sld http://schemas.opengis.net/sld/1.0.0/StyledLayerDescriptor.xsd">
  <NamedLayer>
    <Name>green</Name>
    <UserStyle>
      <Name>green</Name>
      <Title>Green polygon</Title>
      <Abstract>Green fill with black outline</Abstract>
      <FeatureTypeStyle>
        <Rule>
          <PolygonSymbolizer>
            <Fill>
              <CssParameter name="fill">#CCFF66</CssParameter>
            </Fill>
            <Stroke>
              <CssParameter name="stroke">#99FF00</CssParameter>
              <CssParameter name="stroke-width">1</CssParameter>
            </Stroke>
          </PolygonSymbolizer>
          
          <TextSymbolizer>
	         <Label>
	           <ogc:PropertyName>number</ogc:PropertyName>
	         </Label>
	         <Font>
	           <CssParameter name="font-family">Arial</CssParameter>
	           <CssParameter name="font-size">11</CssParameter>
	           <CssParameter name="font-style">normal</CssParameter>
	           <CssParameter name="font-weight">bold</CssParameter>
	         </Font>
	         <LabelPlacement>
	           <PointPlacement>
	             <AnchorPoint>
	               <AnchorPointX>0.5</AnchorPointX>
	               <AnchorPointY>0.5</AnchorPointY>
	             </AnchorPoint>
	           </PointPlacement>
	         </LabelPlacement>
		  </TextSymbolizer>
          
        </Rule>
      </FeatureTypeStyle>
    </UserStyle>
  </NamedLayer>
</StyledLayerDescriptor>

style redgraves:

<?xml version="1.0" encoding="UTF-8"?>
<StyledLayerDescriptor version="1.0.0" xmlns="http://www.opengis.net/sld" xmlns:ogc="http://www.opengis.net/ogc"
  xmlns:xlink="http://www.w3.org/1999/xlink" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
  xsi:schemaLocation="http://www.opengis.net/sld http://schemas.opengis.net/sld/1.0.0/StyledLayerDescriptor.xsd">
  <NamedLayer>
    <Name>green</Name>
    <UserStyle>
      <Name>green</Name>
      <Title>Green polygon</Title>
      <Abstract>Green fill with black outline</Abstract>
      <FeatureTypeStyle>
        <Rule>
          <PolygonSymbolizer>
            <Fill>
              <CssParameter name="fill">#FF0000</CssParameter>
            </Fill>
            <Stroke>
              <CssParameter name="stroke">#EE0000</CssParameter>
              <CssParameter name="stroke-width">1</CssParameter>
            </Stroke>
          </PolygonSymbolizer>
          
          <TextSymbolizer>
	         <Label>
	           <ogc:PropertyName>number</ogc:PropertyName>
	         </Label>
	         <Font>
	           <CssParameter name="font-family">Arial</CssParameter>
	           <CssParameter name="font-size">11</CssParameter>
	           <CssParameter name="font-style">normal</CssParameter>
	           <CssParameter name="font-weight">bold</CssParameter>
	         </Font>
	         <LabelPlacement>
	           <PointPlacement>
	             <AnchorPoint>
	               <AnchorPointX>0.5</AnchorPointX>
	               <AnchorPointY>0.5</AnchorPointY>
	             </AnchorPoint>
	           </PointPlacement>
	         </LabelPlacement>
		  </TextSymbolizer>
          
        </Rule>
      </FeatureTypeStyle>
    </UserStyle>
  </NamedLayer>
</StyledLayerDescriptor>