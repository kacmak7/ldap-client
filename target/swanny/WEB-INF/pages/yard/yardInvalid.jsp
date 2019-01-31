<%@ include file="/common/taglibs.jsp"%>

<head>
    <title><fmt:message key="menu.graveyard-invalid"/></title>
    <meta name="menu" content="GraveyardMenu"/>
</head>

<div>
	<display:table name="graves" cellspacing="0" cellpadding="0" requestURI=""
	    defaultsort="1" id="grave" pagesize="15" class="table" export="false" style="margin-bottom: 10px;">
		<display:column property="owner.firstName" escapeXml="true" sortable="true" titleKey="grave.owner.first-name" style="width: 25%"
			url="/yard/graveDetails.html" paramId="graveId" paramProperty="id"/>
		<display:column property="owner.surname" escapeXml="true" sortable="true" titleKey="grave.owner.surname" style="width: 25%"
			url="/yard/graveDetails.html" paramId="graveId" paramProperty="id"/>
		<display:column property="personsHtml" escapeXml="true" sortable="true" titleKey="grave.persons" style="width: 25%"
			url="/yard/graveDetails.html" paramId="graveId" paramProperty="id"/>
		<display:column property="sector" escapeXml="true" sortable="true" titleKey="grave.sector" style="width: 25%"
			url="/yard/graveDetails.html" paramId="graveId" paramProperty="id"/>
		<display:column property="number" escapeXml="true" sortable="true" titleKey="grave.number" style="width: 25%"
			url="/yard/graveDetails.html" paramId="graveId" paramProperty="id"/>
		<display:column titleKey="grave.find.on-map" url="/yard/yard.html" paramId="graveId" paramProperty="id">
			<fmt:message key="button.search"/>
		</display:column>
	</display:table>
</div>