<%@ include file="/common/taglibs.jsp"%>

<head>
    <title><fmt:message key="cards.title"/></title>
    <meta name="heading" content="<fmt:message key='cards.heading'/>"/>
    <meta name="menu" content="PrintMenu"/>
</head>

<c:set var="buttons">
	<c:url value="/print/cards.html" var="cardsUrl"/>
    <form:form commandName="familyFilter" id="workFilterForm" action="${cardsUrl}">
	    <input type="button" style="margin-right: 5px"
	        onclick="location.href='<c:url value="/print/familyform.html?method=Add&from=list"/>'"
	        value="<fmt:message key="button.add"/>"/>
		
		<fmt:message key="cards.filter"/> :
		<fmt:message key="cards.filter.surname"/>
		<form:input path="surname" id="surname"/>
		<fmt:message key="cards.filter.street"/>
		<form:input path="street" id="street"/>
		<fmt:message key="cards.filter.first-number"/>
		<form:input path="firstNumber" cssStyle="width: 60px;" id="firstNumber"/>
		<input type="submit" name="submit" class="button" onclick="bCancel=false"
			value="<fmt:message key="button.search"/>" />
	</form:form>
</c:set>

<c:out value="${buttons}" escapeXml="false" />

<display:table name="familiesList" cellspacing="0" cellpadding="0" requestURI="" sort="external"
    defaultsort="2" id="family" pagesize="25" class="table" export="false" style="margin-bottom: 10px;">
    <display:column property="surname" escapeXml="true" sortable="true" titleKey="print.family.surname" style="width: 25%"
        url="/print/familyform.html?from=list" paramId="id" paramProperty="id"/>
    <display:column property="address.fullString" sortProperty="address.street" escapeXml="true" sortable="true" titleKey="print.family.address" style="width: 25%"
        url="/print/familyform.html?from=list" paramId="id" paramProperty="id"/>
</display:table>

<script type="text/javascript">
    highlightTableRows("familiesList");
</script>
