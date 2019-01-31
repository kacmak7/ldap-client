<%@ include file="/common/taglibs.jsp"%>

<head>
	<title><fmt:message key="print.offering-report" /></title>
		<meta name="heading" content="<fmt:message key='print.offering-report'/>" />
		<meta name="menu" content="PrintMenu" />

		<script type="text/javascript" src="${ctx}/dwr/engine.js"></script>
		<script type="text/javascript" src="${ctx}/dwr/util.js"></script>
		<script type="text/javascript" src="${ctx}/scripts/calendar/calendar.js"></script>
		<script type="text/javascript" src="${ctx}/scripts/calendar/calendar-setup.js"></script>
		<script type="text/javascript" src="${ctx}/scripts/calendar/lang/calendar-pl-utf8.js"></script>
		
		<script type="text/javascript" src="${ctx}/scripts/sha1-min.js"></script>
</head>

<c:set var="buttons">
	<c:url value="/tomes/offeringReport.html" var="offeringReportUrl" />
	<form:form commandName="reportFilter" id="offeringFilterForm" action="${offeringReportUrl}">
		<fmt:message key="offering-report.filter" /> :
	
		<fmt:message key="offering-report.filter.first-date" />
		<form:errors path="firstDate" cssClass="fieldError" />
		<form:input path="firstDate" id="firstDate" cssClass="text" cssErrorClass="text error" maxlength="10" cssStyle="width:75px;" />
		<button id="firstDateInCal" type="button" class="calendarbutton">...</button>

		<fmt:message key="offering-report.filter.last-date" />
		<form:errors path="lastDate" cssClass="fieldError" />
		<form:input path="lastDate" id="lastDate" cssClass="text" cssErrorClass="text error" maxlength="10" cssStyle="width:75px;" />
		<button id="lastDateInCal" type="button" class="calendarbutton">...</button>

		<input type="submit" name="submit" class="button" onclick="bCancel=false" value="<fmt:message key="button.search"/>" />
	</form:form>
</c:set>

<c:out value="${buttons}" escapeXml="false" />

<display:table name="rowList" cellspacing="0" cellpadding="0" requestURI="" sort="external" defaultsort="1" id="intention"
					class="table" export="true" style="margin-bottom: 10px;">

	<c:forEach items="${rowList[0]}" var="item" varStatus="loop">
		<c:set var="index" value="${loop.index}" />

		<display:column property="[${loop.index}]" escapeXml="true"
			sortable="false" titleKey="${celebrantsList[index]}" paramId="id"
			paramProperty="id" media="html" />

		<display:column property="[${loop.index}]" escapeXml="true"
			titleKey="${celebrantsList[index]}" style="width: 20%"
			decorator="org.parafia.util.decorator.DisplaytagDateDecorator"
			media="excel pdf" />
	</c:forEach>
</display:table>

<script type="text/javascript">
	Calendar.setup({
		inputField : "firstDate", // id of the input field
		ifFormat : "%d-%m-%Y", // the date format
		button : "firstDateInCal" // id of the button
	});

	Calendar.setup({
		inputField : "lastDate", // id of the input field
		ifFormat : "%d-%m-%Y", // the date format
		button : "lastDateInCal" // id of the button
	});
</script>