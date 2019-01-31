<%@ include file="/common/taglibs.jsp"%>

<head>
    <title><fmt:message key="print.death-tome"/></title>
    <meta name="heading" content="<fmt:message key='print.death-tome'/>"/>
    <meta name="menu" content="PrintMenu"/>
</head>

<c:set var="buttons">
    <input type="button" style="margin-right: 5px"
        onclick="location.href='<c:url value="/print/personform.html"/>'"
        value="<fmt:message key="button.add"/>"/>
        
	<c:url value="/tomes/deathTome.html" var="deathTomeUrl"/>
    <form:form commandName="bapConDeaFilter" id="deathFilterForm" action="${deathTomeUrl}">
		<fmt:message key="cards.filter"/> :
		<fmt:message key="cards.filter.surname"/>
		<form:input path="surname" id="surname"/>
		<fmt:message key="cards.filter.firstname"/>
		<form:input path="firstName" id="firstName"/>
		<fmt:message key="cards.filter.death-year"/>
		<form:select path="year" id="death.year">
			<form:option value="0">wszystkie</form:option>
			<form:options items="${requestScope.years}"/>
		</form:select>
   		<fmt:message key="cards.filter.burial-year"/>
		<form:select path="burialYear" id="burial.year">
			<form:option value="0">wszystkie</form:option>
			<form:options items="${requestScope.burialYears}"/>
		</form:select>
		<input type="submit" name="submit" class="button" onclick="bCancel=false"
			value="<fmt:message key="button.search"/>" />
	</form:form>
</c:set>

<c:out value="${buttons}" escapeXml="false" />

<display:table name="personsList" cellspacing="0" cellpadding="0" requestURI="" 
    defaultsort="1" id="person" pagesize="25" class="table" export="true" style="margin-bottom: 10px;">
    <display:column property="surname" escapeXml="true" sortable="true" titleKey="print.family.last-name" style="width: 20%"
        url="/print/personform.html" paramId="personId" paramProperty="id" media="html"/>
    <display:column property="firstName" escapeXml="true" sortable="true" titleKey="print.family.first-name" style="width: 20%"
        url="/print/personform.html" paramId="personId" paramProperty="id" media="html"/>
    <display:column property="remaining.death.fileNumber" escapeXml="true" sortable="true" titleKey="print.remaining.death-filenumber" style="width: 20%"
        url="/print/personform.html" paramId="personId" paramProperty="id" media="html"/>
    <display:column property="remaining.death.date" escapeXml="true" sortable="true" titleKey="print.remaining.death-date" style="width: 20%"
        url="/print/personform.html" paramId="personId" paramProperty="id" decorator="org.parafia.util.decorator.DisplaytagDateDecorator" media="html"/>
   <display:column property="remaining.death.burialDate" escapeXml="true" sortable="true" titleKey="print.remaining.death-burial-date" style="width: 20%"
        url="/print/personform.html" paramId="personId" paramProperty="id" decorator="org.parafia.util.decorator.DisplaytagDateDecorator" media="html"/>
    
    <display:column property="surname" escapeXml="true" titleKey="print.family.last-name" style="width: 20%"
        media="excel pdf"/>
    <display:column property="firstName" escapeXml="true" titleKey="print.family.first-name" style="width: 20%"
        media="excel pdf"/>
    <display:column property="remaining.death.fileNumber" escapeXml="true" titleKey="print.remaining.death-filenumber" style="width: 20%"
        media="excel pdf"/>
    <display:column property="remaining.death.date" escapeXml="true" titleKey="print.remaining.death-date" style="width: 20%"
        decorator="org.parafia.util.decorator.DisplaytagDateDecorator" media="excel pdf"/>
   	<display:column property="remaining.death.burialDate" escapeXml="true" titleKey="print.remaining.death-burial-date" style="width: 20%"
        decorator="org.parafia.util.decorator.DisplaytagDateDecorator" media="excel pdf"/>
</display:table>

<c:out value="${buttons}" escapeXml="false" />

<script type="text/javascript">
    highlightTableRows("personsList");
</script>