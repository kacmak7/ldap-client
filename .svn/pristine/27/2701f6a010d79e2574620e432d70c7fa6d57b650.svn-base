<%@ include file="/common/taglibs.jsp"%>
<%--<%@ taglib prefix="myfn" uri="http://samplefn"--%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="myfn" %>

TO NIE DZIALA OBECNIE!!


<head>
    <title><fmt:message key="graveyard.title"/></title>
    <meta name="menu" content="GraveyardMenu"/>
</head>

<display:table name="gravesList" cellspacing="0" cellpadding="0" requestURI=""
    defaultsort="1" id="grave" pagesize="15" class="table" export="false" style="margin-bottom: 10px;">
	<display:column property="owner.firstName" escapeXml="true" sortable="true" titleKey="grave.owner.first-name" style="width: 25%"
		url="/yard/graveDetails.html" paramId="graveNumber" paramProperty="number"/>
	<display:column property="owner.surname" escapeXml="true" sortable="true" titleKey="grave.owner.surname" style="width: 25%"
		url="/yard/graveDetails.html" paramId="graveNumber" paramProperty="number"/>
	<display:column property="personsHtml" escapeXml="true" sortable="true" titleKey="grave.persons" style="width: 25%"
		url="/yard/graveDetails.html" paramId="graveNumber" paramProperty="number"/>
	<display:column property="sector" escapeXml="true" sortable="true" titleKey="grave.sector" style="width: 25%"
		url="/yard/graveDetails.html" paramId="graveNumber" paramProperty="number"/>
	<display:column titleKey="grave.find.on-map" url="/yard/yard.html" paramId="graveNumber" paramProperty="number">
		<fmt:message key="button.search"/>
	</display:column>
</display:table>

<script type="text/javascript">
    highlightTableRows("gravesList");
</script>