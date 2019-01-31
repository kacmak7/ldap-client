 <%@ include file="/common/taglibs.jsp"%>

<head>
    <title><fmt:message key="print.certificate.edit"/></title>
    <meta name="menu" content="PrintMenu"/>
    <script type="text/javascript" src="${ctx}/dwr/engine.js"></script>
    <script type="text/javascript" src="${ctx}/dwr/util.js"></script>
    
    <script type="text/javascript" src="${ctx}/scripts/calendar/calendar.js"></script>
    <script type="text/javascript" src="${ctx}/scripts/calendar/calendar-setup.js"></script>
    <script type="text/javascript" src="${ctx}/scripts/calendar/lang/calendar-pl-utf8.js"></script>
</head>

<p><spring:message code="print.certificate.edit"/></p>

<div>
	<spring:bind path="fianceePair.*">
	    <c:if test="${not empty status.errorMessages}">
	    <div class="error">
	        <c:forEach var="error" items="${status.errorMessages}">
	            <img src="<c:url value="/images/iconWarning.gif"/>"
	                alt="<fmt:message key="icon.warning"/>" class="icon" />
	            <c:out value="${error}" escapeXml="false"/><br />
	        </c:forEach>
	    </div>
	    </c:if>
	</spring:bind>
	
	<div class="separator"></div>
	
	<form:form commandName="fianceePair" method="post"
	    onsubmit="return validateFianceePair(this)" id="fianceePair">
		
		<form:hidden path="certificate.id"/>
		
		<table>
			<tr>
				<td>
					<appfuse:label styleClass="desc" key="print.certificate.first-witness.firstname"/>
			        <form:errors path="certificate.firstWitnessFirstName" cssClass="fieldError"/>
			        <form:input path="certificate.firstWitnessFirstName" id="firstWitnessFirstName" cssClass="text wide" cssErrorClass="text wide error"/>
				</td>
				<td>
					<appfuse:label styleClass="desc" key="print.certificate.first-witness.surname"/>
			        <form:errors path="certificate.firstWitnessSurname" cssClass="fieldError"/>
			        <form:input path="certificate.firstWitnessSurname" id="firstWitnessSurname" cssClass="text wide" cssErrorClass="text wide error"/>
				</td>
			</tr>
			<tr>
				<td>
					<appfuse:label styleClass="desc" key="print.certificate.second-witness.firstname"/>
			        <form:errors path="certificate.secondWitnessFirstName" cssClass="fieldError"/>
			        <form:input path="certificate.secondWitnessFirstName" id="secondWitnessFirstName" cssClass="text wide" cssErrorClass="text wide error"/>
				</td>
				<td>
					<appfuse:label styleClass="desc" key="print.certificate.second-witness.surname"/>
			        <form:errors path="certificate.secondWitnessSurname" cssClass="fieldError"/>
			        <form:input path="certificate.secondWitnessSurname" id="secondWitnessSurname" cssClass="text wide" cssErrorClass="text wide error"/>
				</td>
			</tr>
			<tr>
				<td>
					<appfuse:label styleClass="desc" key="print.certificate.priest.fullname"/>
			        <form:errors path="certificate.priestFullName" cssClass="fieldError"/>
			        <form:input path="certificate.priestFullName" id="priestFullName" cssClass="text wide" cssErrorClass="text wide error"/>
				</td>
				<td>
					<appfuse:label styleClass="desc" key="print.certificate.priest.position"/>
			        <form:errors path="certificate.priestPosition" cssClass="fieldError"/>
			        <form:input path="certificate.priestPosition" id="priestPosition" cssClass="text wide" cssErrorClass="text wide error"/>
				</td>
			</tr>
			<tr>
				<td>
					<appfuse:label styleClass="desc" key="print.certificate.marriage-date"/>
			        <form:errors path="marriageDate" cssClass="fieldError"/>
			        <form:input path="marriageDate" id="marriageDate" cssClass="text wide" cssErrorClass="text wide error"/>
			        <button id="marriageDateCal" type="button" class="calendarbutton"> ... </button>
				</td>
				<td>
					<appfuse:label styleClass="desc" key="print.certificate.number"/>
			        <form:errors path="certificate.number" cssClass="fieldError"/>
			        <form:input path="certificate.number" id="number" cssClass="text wide" cssErrorClass="text wide error"/>
				</td>
				<td>
					<appfuse:label styleClass="desc" key="print.certificate.marriage-number"/>
			        <form:errors path="certificate.marriageNumber" cssClass="fieldError"/>
			        <form:input path="certificate.marriageNumber" id="marriageNumber" cssClass="text wide" cssErrorClass="text wide error"/>
				</td>
			</tr>
		</table>
		
    	<div style="float: left; clear: both; margin-top: 20px;">
	        <input type="submit" name="submit" class="button" onclick="bCancel=false"
	            value="<fmt:message key="button.save"/>" />
	        
	        <c:url var="certificateUrl" value="/printcertificate.html">
				<c:param name="fianceePairId" value="${requestScope.fianceePair.id}"/>
			</c:url>
			<input type="button" class="button" name="certificate" value="<fmt:message key='button.print'/>"
				onclick="javascript:window.location='${certificateUrl}';"/>
				
			<c:url var="certificateUrl2" value="/printcertificateonlypriest.html">
				<c:param name="fianceePairId" value="${requestScope.fianceePair.id}"/>
			</c:url>
			<input type="button" class="button" name="certificate" value="<fmt:message key='button.print-only-priest'/>"
				onclick="javascript:window.location='${certificateUrl2}';" style="width: 150px;"/>
	        
	        <c:url var="cancelUrl" value="/print/fianceesform.html">
				<c:param name="fianceePairId" value="${requestScope.fianceePair.id}"/>
			</c:url>
	        <input type="button" name="cancel" class="button" onclick="javascript:window.location='${cancelUrl}';" value="<fmt:message key="button.cancel"/>" />
		</div>
	</form:form>
</div>

<script type="text/javascript">

    Calendar.setup({
	    inputField  : "marriageDate",      // id of the input field
	    ifFormat    : "%d-%m-%Y",      // the date format
	    button      : "marriageDateCal"    // id of the button
	});
</script>
<v:javascript formName="fianceePair" staticJavascript="false"/>
<script type="text/javascript" src="<c:url value="/scripts/validator.jsp"/>"></script>