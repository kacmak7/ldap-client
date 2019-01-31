<%@ include file="/common/taglibs.jsp"%>

<head>
    <title><fmt:message key="print.baptism-tome"/></title>
    <meta name="heading" content="<fmt:message key='print.baptism-tome'/>"/>
    <meta name="menu" content="PrintMenu"/>
</head>

<c:set var="buttons">
    <input type="button" style="margin-right: 5px"
        onclick="location.href='<c:url value="/print/personform.html"/>'"
        value="<fmt:message key="button.add"/>"/>
        
    <c:url value="/tomes/baptismTome.html" var="baptismTomeUrl"/>
    <form:form commandName="bapConDeaFilter" id="baptismFilterForm" action="${baptismTomeUrl}">
		<fmt:message key="cards.filter"/> :
		<fmt:message key="cards.filter.surname"/>
		<form:input path="surname" id="surname"/>
		<fmt:message key="cards.filter.firstname"/>
		<form:input path="firstName" id="firstName"/>
		<fmt:message key="cards.filter.year"/>
		<form:select path="year" id="year">
			<form:option value="0">wszystkie</form:option>
			<form:options items="${requestScope.years}"/>
		</form:select>
		<input type="submit" name="submit" class="button" onclick="bCancel=false"
			value="<fmt:message key="button.search"/>" />
	</form:form>
</c:set>

<c:out value="${buttons}" escapeXml="false" />

<display:table name="personsList" cellspacing="0" cellpadding="0" requestURI="" 
    defaultsort="1" id="person" pagesize="25" class="table" export="true" style="margin-bottom: 10px;">
    <display:column property="surname" escapeXml="true" sortable="true" titleKey="print.family.last-name" style="width: 25%"
        url="/print/personform.html" paramId="personId" paramProperty="id" media="html"/>
    <display:column property="firstName" escapeXml="true" sortable="true" titleKey="print.family.first-name" style="width: 25%"
        url="/print/personform.html" paramId="personId" paramProperty="id" media="html"/>
    <display:column property="remaining.baptism.fileNumber" escapeXml="true" sortable="true" titleKey="print.remaining.baptism-filenumber" style="width: 25%"
        url="/print/personform.html" paramId="personId" paramProperty="id" media="html"/>
    <display:column property="remaining.baptism.date" escapeXml="true" sortable="true" titleKey="print.remaining.baptism-date" style="width: 25%"
        url="/print/personform.html" paramId="personId" paramProperty="id" media="html"/>
    
    <display:column property="surname" escapeXml="true" titleKey="print.family.last-name" style="width: 25%"
        media="excel pdf"/>
    <display:column property="firstName" escapeXml="true" titleKey="print.family.first-name" style="width: 25%"
        media="excel pdf"/>
    <display:column property="remaining.baptism.fileNumber" escapeXml="true" titleKey="print.remaining.baptism-filenumber" style="width: 25%"
        media="excel pdf"/>
    <display:column property="remaining.baptism.date" escapeXml="true" titleKey="print.remaining.baptism-date" style="width: 25%"
        media="excel pdf"/>
</display:table>

<c:out value="${buttons}" escapeXml="false" />

<script type="text/javascript">
    highlightTableRows("personsList");
</script>