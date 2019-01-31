<%@ include file="/common/taglibs.jsp"%>

<head>
    <title><fmt:message key="print.fiancees-tome"/></title>
    <meta name="heading" content="<fmt:message key='print.fiancees-tome'/>"/>
    <meta name="menu" content="PrintMenu"/>
</head>

<c:set var="buttons">
    <input type="button" style="margin-right: 5px"
        onclick="location.href='<c:url value="/print/personform.html"/>'"
        value="<fmt:message key="button.add"/>"/>
        
	<c:url value="/tomes/fianceesTome.html" var="fianceesTomeUrl"/>
    <form:form commandName="bapConDeaFilter" id="fianceesFilterForm" action="${fianceesTomeUrl}">
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
		<input type="submit" class="button" onclick="bCancel=false"
			value="<fmt:message key="button.search"/>" />
	</form:form>
</c:set>

<c:out value="${buttons}" escapeXml="false" />

<display:table name="fianceesList" cellspacing="0" cellpadding="0" requestURI="" defaultsort="1"
		id="fianceePair" pagesize="25" class="table" export="true" style="margin-bottom: 10px;">
	<display:column property="fianceeHe.surnameNotNull" sortProperty="fianceeHe.surname" escapeXml="true" sortable="true" titleKey="print.fiancee-he.last-name" style="width: 15%"
        url="/print/fianceesform.html" paramId="fianceePairId" paramProperty="id" media="html"/>
    <display:column property="fianceeHe.firstName" escapeXml="true" sortable="true" titleKey="print.fiancee-he.first-name" style="width: 15%"
        url="/print/fianceesform.html" paramId="fianceePairId" paramProperty="id" media="html"/>
    <display:column property="fianceeShe.surnameNotNull" sortProperty="fianceeShe.surname" escapeXml="true" sortable="true" titleKey="print.fiancee-she.last-name" style="width: 15%"
        url="/print/fianceesform.html" paramId="fianceePairId" paramProperty="id" media="html"/>
    <display:column property="fianceeShe.firstName" escapeXml="true" sortable="true" titleKey="print.fiancee-she.first-name" style="width: 15%"
        url="/print/fianceesform.html" paramId="fianceePairId" paramProperty="id" media="html"/>
    <display:column property="fileNumber" escapeXml="true" sortable="true" titleKey="print.remaining.marriage-filenumber" style="width: 25%"
        url="/print/fianceesform.html" paramId="fianceePairId" paramProperty="id" media="html"/>
    
    <display:column property="fianceeHe.surnameNotNull" escapeXml="true" titleKey="print.fiancee-he.last-name" style="width: 15%"
        media="excel pdf"/>
    <display:column property="fianceeHe.firstName" escapeXml="true" titleKey="print.fiancee-he.first-name" style="width: 15%"
        media="excel pdf"/>
    <display:column property="fianceeShe.surnameNotNull" escapeXml="true" titleKey="print.fiancee-she.last-name" style="width: 15%"
        media="excel pdf"/>
    <display:column property="fianceeShe.firstName" escapeXml="true" titleKey="print.fiancee-she.first-name" style="width: 15%"
        media="excel pdf"/>
    <display:column property="fileNumber" escapeXml="true" titleKey="print.remaining.marriage-filenumber" style="width: 25%"
        media="excel pdf"/>
</display:table>

<c:out value="${buttons}" escapeXml="false" />

<script type="text/javascript">
    highlightTableRows("fianceesList");
</script>