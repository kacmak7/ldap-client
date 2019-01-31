 <%@ include file="/common/taglibs.jsp"%>

<head>
    <title><fmt:message key="print.confirmation.edit"/></title>
    <meta name="menu" content="PrintMenu"/>
    <script type="text/javascript" src="${ctx}/dwr/engine.js"></script>
    <script type="text/javascript" src="${ctx}/dwr/util.js"></script>
    
    <script type="text/javascript" src="${ctx}/scripts/calendar/calendar.js"></script>
    <script type="text/javascript" src="${ctx}/scripts/calendar/calendar-setup.js"></script>
    <script type="text/javascript" src="${ctx}/scripts/calendar/lang/calendar-pl-utf8.js"></script>
    
    <script type="text/javascript" src="${ctx}/scripts/sha1-min.js"></script>
</head>

<p><spring:message code="print.confirmation.edit"/></p>

<div>
	<spring:bind path="person.*">
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
	
	<form:form commandName="person" method="post" onsubmit="return validatePerson(this)" id="person">
		
		<form:hidden path="remaining.confirmation.id"/>
	
		<ul>
			<fieldset>
				<li>
			    	<div style="float: left; clear: left;">
			    		<appfuse:label styleClass="desc" key="print.remaining.confirmation-filenumber"/>
				        <form:errors path="remaining.confirmation.fileNumber" cssClass="fieldError"/>
				        <form:input path="remaining.confirmation.fileNumber" id="confirmationFileNumber" cssClass="text small" cssErrorClass="text small error"/>
			        </div>
			        <div style="float: left; margin-left: 20px;">
			    		<appfuse:label styleClass="desc" key="print.remaining.confirmation-date"/>
				        <form:errors path="remaining.confirmation.date" cssClass="fieldError"/>
				        <form:input path="remaining.confirmation.date" id="confirmationDate" cssClass="text date" cssErrorClass="text date error"/>
				        <button id="confirmationDateCal" type="button" class="calendarbutton"> ... </button>
			        </div>
			        <div style="float: left; margin-left: 20px;">
			    		<appfuse:label styleClass="desc" key="print.remaining.confirmation-place"/>
				        <form:errors path="remaining.confirmation.place" cssClass="fieldError"/>
				        <form:input path="remaining.confirmation.place" id="confirmationPlace" cssClass="text medium" cssErrorClass="text medium error"/>
			        </div>
			        <div style="float: left; margin-left: 20px;">
			    		<appfuse:label styleClass="desc" key="print.remaining.confirmation-name"/>
				        <form:errors path="remaining.confirmation.confirmationName" cssClass="fieldError"/>
				        <form:input path="remaining.confirmation.confirmationName" id="confirmationName" cssClass="text" cssErrorClass="text error"
				        	cssStyle="width: 120px;"/>
			        </div>
			        <div style="float: left; margin-left: 20px;">
			    		<appfuse:label styleClass="desc" key="print.remaining.bishop"/>
				        <form:errors path="remaining.confirmation.bishop" cssClass="fieldError"/>
				        <form:input path="remaining.confirmation.bishop" id="bishop" cssClass="text medium" cssErrorClass="text medium error"/>
			        </div>
				</li>
			</fieldset>
		
			<li class="buttonBar bottom">
		    	<div style="float: left; clear: both; margin-top: 20px;">
			        <input type="submit" name="submit" class="button" onclick="bCancel=false"
			            value="<fmt:message key="button.save"/>" />
			            
			        <c:url var="personUrl" value="/print/personform.html">
			        	<c:param name="personId" value="${requestScope.person.id}"/>
			        </c:url>
			        <input type="button" name="cancel" class="button" onclick="javascript:location.href='${personUrl}';"
			            value="<fmt:message key="button.cancel"/>" />
				</div>
			</li>
			<li>
				<div style="float: left; clear: left; margin-top: 20px;">
					<c:url var="printBapConDeaCertificateUrl" value="/printbapcondeacertificate.html">
						<c:param name="personId" value="${requestScope.person.id}"/>
						<c:param name="sort" value="confirmation"/>
					</c:url>
					<appfuse:label styleClass="desc" key="print.confirmation-certificate"/>
					<input type="button" name="confirmationCertificate" class="button" value="<fmt:message key='button.print'/>"
						onclick="javascript:window.open('${printBapConDeaCertificateUrl}','Zaswiadczenie','height=300,width=500');"/>
				</div>
			</li>
		</ul>	
	</form:form>
</div>

<script type="text/javascript">
    Calendar.setup({
	    inputField  : "confirmationDate",      // id of the input field
	    ifFormat    : "%d-%m-%Y",      // the date format
	    button      : "confirmationDateCal"    // id of the button
	});
</script>
<v:javascript formName="person" staticJavascript="false"/>
<script type="text/javascript" src="<c:url value="/scripts/validator.jsp"/>"></script>