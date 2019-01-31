<%@ include file="/common/taglibs.jsp"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="myfn" %>

<head>
    <title><fmt:message key="graveyard.title"/></title>
    <meta name="menu" content="GraveyardMenu"/>
    <link rel="stylesheet" type="text/css" href="<c:url value='/styles/ol.css'/>" />
    
    <script type="text/javascript">
    	var imgPath = "<c:url value='/scripts/img/'/>";		//used in ol.js
    </script>
    
    <script type="text/javascript" src="<c:url value='/scripts/ol.js'/>"></script>
    <script type="text/javascript" src="<c:url value='/scripts/cookies.js'/>"></script>
    
    <!-- <script type="text/javascript" src="<c:url value='/scripts/prototype.js'/>"></script>
    <script type="text/javascript" src="<c:url value='/scripts/scriptaculous.js'/>"></script>
    <script type="text/javascript" src="<c:url value='/scripts/global.js'/>"></script> -->

<style type="text/css">
	.ol-attribution.ol-logo-only {
		display: none;
	}
</style>
</head>

<div id="map" class="map" style="width: 1030px; height: 650px;border: 1px solid #0066CC;margin-bottom: 10px;"></div>
<div id="no-download" class="alert alert-danger" style="display: none">
  Drukowanie mapy wymaga przegladarki wspomagajacej atrybut
  <a href="http://caniuse.com/#feat=download">link download</a>.
</div>
<a id="export-png" class="btn btn-default" download="mapa.png" href="#"><fmt:message key='button.print'/></a>

<script>
	var cqlFilter = null
	<c:choose>
		<c:when test="${not empty param.graveId}">
			cqlFilter = 'id=' + ${param.graveId}
		</c:when>
		<c:when test="${not empty requestScope.redGraves}">
			cqlFilter = 'id=' + ${requestScope.redGraves[0]}
			<c:forEach items="${requestScope.redGraves}" var="redGrave" begin="1">
    			cqlFilter += ' or id=' + ${redGrave}
    		</c:forEach>
		</c:when>
  		<c:when test="${not empty requestScope.sectorx}">
			cqlFilter = "sector = '${requestScope.sectorx}'"
		</c:when>
		<c:otherwise>
			cqlFilter = "id < 0"
		</c:otherwise>
	</c:choose>

	var projection = new ol.proj.Projection({
	  	code: 'EPSG:3857',
	  	extent: [4762.7, -8003.55, 4950.55, -7717.57]
	});
	ol.proj.addProjection(projection);
	
	var mapcenter = Get_Cookie('mapcenter');
	if (mapcenter == null)
		mapcenter = [4880, -7930];
	else {
		var cent1 = Number(mapcenter.split(",")[0]);
		var cent2 = Number(mapcenter.split(",")[1]);
		mapcenter = [cent1, cent2];
	}
		
	var mapzoom = Get_Cookie('mapzoom');
	if (mapzoom == null)
		mapzoom = 2;
	
	console.log(mapcenter);
	
	var view = new ol.View({
        center: mapcenter,
        zoom: mapzoom,
    });
	
	var wmsSource = new ol.source.TileWMS({
      url: '${geoUrl}',
      params: {'LAYERS': 'graves:graves', 'TILED': true, 'STYLES': 'graves', 'CQL_FILTER': 'not('+cqlFilter+')' },
      serverType: 'geoserver',
      crossOrigin: 'anonymous'
    });
	
	var wmsSourceRed = new ol.source.TileWMS({
      url: '${geoUrl}',
      params: {'LAYERS': 'graves:graves', 'TILED': true, 'STYLES': 'redgraves', 'CQL_FILTER': cqlFilter },
      serverType: 'geoserver',
      crossOrigin: 'anonymous'
    });
	
	var pointFeatures = Array()
   	pointFeatures[0] = new ol.Feature({'geometry': new ol.geom.Point([4870, -7925]), name: "I"});
   	pointFeatures[1] = new ol.Feature({'geometry': new ol.geom.Point([4940, -7915]), name: "II"});
   	pointFeatures[2] = new ol.Feature({'geometry': new ol.geom.Point([4905, -7965]), name: "III"});
   	pointFeatures[3] = new ol.Feature({'geometry': new ol.geom.Point([4920, -7912]), name: "IV"});
   	pointFeatures[4] = new ol.Feature({'geometry': new ol.geom.Point([4900, -7890]), name: "V"});
   	pointFeatures[5] = new ol.Feature({'geometry': new ol.geom.Point([4870, -7970]), name: "VI"});
   	pointFeatures[6] = new ol.Feature({'geometry': new ol.geom.Point([4835, -7955]), name: "VII"});
   	pointFeatures[7] = new ol.Feature({'geometry': new ol.geom.Point([4815, -7925]), name: "VIII"});
   	pointFeatures[8] = new ol.Feature({'geometry': new ol.geom.Point([4835, -7890]), name: "IX"});
   	pointFeatures[9] = new ol.Feature({'geometry': new ol.geom.Point([4872, -7880]), name: "X"});
   	pointFeatures[10] = new ol.Feature({'geometry': new ol.geom.Point([4915, -7855]), name: "XI"});
   	pointFeatures[11] = new ol.Feature({'geometry': new ol.geom.Point([4902, -7833]), name: "XII"});
   	pointFeatures[12] = new ol.Feature({'geometry': new ol.geom.Point([4848, -7830]), name: "XIII"});
   	pointFeatures[13] = new ol.Feature({'geometry': new ol.geom.Point([4800, -7875]), name: "XIV"});
   	pointFeatures[14] = new ol.Feature({'geometry': new ol.geom.Point([4787, -7945]), name: "XV"});
   	
   	function pointStyle(feature, resolution) {
   	    return [
   	        new ol.style.Style({
   	            text: new ol.style.Text({
   	                text: feature.get('name'),
   	                font: '20px bold Courier New, monospace',
   	                fill: new ol.style.Fill({ color: '#0066CC' }),
   	             	stroke: new ol.style.Stroke({color: '#FFF', width: '5'})
   	            })
   	        })
   	    ]
   	};
  	
		
    var map = new ol.Map({
        layers: [ 
          new ol.layer.Tile({
              source: wmsSource,
          }),
          new ol.layer.Tile({
              source: wmsSourceRed
          }),
          new ol.layer.Vector({
     	      source: new ol.source.Vector({
     	        features: pointFeatures
     	      }),
     	      style: function(feature, res) { return pointStyle(feature, res); }
     	  })
        ],
        target: 'map',
        view: view
    });
          
	
    map.on("click", function(evt) {
    	console.log(evt);
    	var viewResolution = /** @type {number} */ (view.getResolution());
    	var url = wmsSource.getGetFeatureInfoUrl(
    	      evt.coordinate, viewResolution, 'EPSG:3857',
    	      {'INFO_FORMAT': 'text/plain'});
    	invokeAjax(url);
    	
    	var url = wmsSourceRed.getGetFeatureInfoUrl(
    	      evt.coordinate, viewResolution, 'EPSG:3857',
    	      {'INFO_FORMAT': 'text/plain'});
    	invokeAjax(url);
    });
	
	function invokeAjax(appUrl) {
    	//console.log(url)
		$.ajax({
			url: appUrl,
			type: 'GET'
		}) .done(function( transport ) {
			console.log(transport);
			var responseTexts = transport.split("\n");
			//var numberEx = /number = \d+/
			var str = "id = "

			for (var i = 0; i < responseTexts.length; i++) {
				//alert(responseTexts[i])
				if (responseTexts[i].startsWith(str)) {
					graveId = responseTexts[i].substring(str.length)
					//alert(graveNumber)
 	                
					<c:url value="/yard/graveDetails.html" var="url"/>
					<c:choose>
						<c:when test="${not empty requestScope.sectorx}">
							window.location='${url}?sectorx=${requestScope.sectorx}&graveId='+graveId
						</c:when>
						<c:otherwise>
							window.location='${url}?graveId='+graveId
						</c:otherwise>
					</c:choose>
 	                
					break;
				}
			}  
		}) .fail(function( problem ) {
   			alert('Blad polaczenia z serwerem geoserver.');
   		})
	}  // end invokeAjax
	
	//printing
	
	var exportPNGElement = document.getElementById('export-png');
        
    function setDPI(canvas, dpi) {
        // Set up CSS size if it's not set up already
         if (!canvas.style.width)
             canvas.style.width = canvas.width + "px";
         if (!canvas.style.height)
             canvas.style.height = canvas.height + "px";

         var scaleFactor = dpi / 96;
         canvas.width = Math.ceil(canvas.width * scaleFactor);
         canvas.height = Math.ceil(canvas.height * scaleFactor);
         var ctx = canvas.getContext("2d");
         ctx.scale(scaleFactor, scaleFactor);
     }

    if ('download' in exportPNGElement) {
      exportPNGElement.addEventListener('click', function() {
      	map.once('precompose', function(event) {
      		var canvas = event.context.canvas;
      	       setDPI(canvas,200);
      	    });  	
    
        map.once('postcompose', function(event) {
          var canvas = event.context.canvas;
          exportPNGElement.href = canvas.toDataURL('image/png');
        });
        map.renderSync();
      }, false);
    } else {
      var info = document.getElementById('no-download');
      /**
       * display error message
       */
      info.style.display = '';
    }
    
  	//save map state in a cookie
    
    map.on('moveend', onMoveEnd);

    function onMoveEnd(evt) {
      var map = evt.map;
      /*var extent = map.getView().calculateExtent(map.getSize());
      var center = ol.proj.transform(ol.extent.getCenter(extent),
		'EPSG:2001', 'EPSG:2001');*/
	  var center = view.getCenter();
	  var zoom = view.getZoom();
      
      if (center != null && !isNaN(center[0]) && !isNaN(center[1])) {
    	  //alert('Set_Cookie ' + center);
          Set_Cookie('mapcenter', center, 30 );
          Set_Cookie('mapzoom', zoom, 30 );
  	  }
    }
    
    //end of cookie
</script>