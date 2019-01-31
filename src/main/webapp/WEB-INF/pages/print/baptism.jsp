 <%@ include file="/common/taglibs.jsp"%>

<head>
    <title><fmt:message key="print.baptism.edit"/></title>
    <meta name="menu" content="PrintMenu"/>
    <script type="text/javascript" src="${ctx}/dwr/engine.js"></script>
    <script type="text/javascript" src="${ctx}/dwr/util.js"></script>
    
    <script type="text/javascript" src="${ctx}/scripts/calendar/calendar.js"></script>
    <script type="text/javascript" src="${ctx}/scripts/calendar/calendar-setup.js"></script>
    <script type="text/javascript" src="${ctx}/scripts/calendar/lang/calendar-pl-utf8.js"></script>
    
    <script type="text/javascript" src="${ctx}/scripts/sha1-min.js"></script>
</head>

<p><spring:message code="print.baptism.edit"/></p>

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
	
	<form:form commandName="person" method="post" action="baptism.html" 
				onsubmit="return validatePerson(this)" id="person">
				
		<input type="hidden" name="personId" value="${person.id}"/>
		<form:hidden path="remaining.baptism.id"/>
		
		<ul>
			<fieldset>
				<li>
			    	<div style="float: left; clear: left;">
			    		<appfuse:label styleClass="desc" key="print.remaining.baptism-filenumber"/>
				        <form:errors path="remaining.baptism.fileNumber" cssClass="fieldError"/>
				        <form:input path="remaining.baptism.fileNumber" id="baptismFileNumber" cssClass="text small" cssErrorClass="text small error"/>
			        </div>
			        <div style="float: left; margin-left: 20px;">
			    		<appfuse:label styleClass="desc" key="print.remaining.baptism-date"/>
				        <form:errors path="remaining.baptism.date" cssClass="fieldError"/>
				        <form:input path="remaining.baptism.date" id="baptismDate" cssClass="text date" cssErrorClass="text date error"/>
				        <button id="baptismDateCal" type="button" class="calendarbutton"> ... </button>
			        </div>
			        <div style="float: left; margin-left: 20px;">
			    		<appfuse:label styleClass="desc" key="print.remaining.baptism-place"/>
				        <form:errors path="remaining.baptism.place" cssClass="fieldError"/>
				        <form:input path="remaining.baptism.place" id="baptismPlace" cssClass="text medium" cssErrorClass="text medium error"/>
			        </div>
				</li>
				
				<li>
				  	<div style="float: left; clear: left;">
			    		<appfuse:label styleClass="desc" key="print.remaining.baptism-certificate-reason"/>
				        <form:errors path="remaining.baptism.reason" cssClass="fieldError"/>
				        <form:textarea path="remaining.baptism.reason" id="reason" cssClass="text large" cssErrorClass="text large error"/>
			        </div>
				</li>
				
				<li>
					<fieldset style="clear: both; width: 800px; margin-top: 50px;">
						<legend><fmt:message key="print.remaining.baptism-remarks"/></legend>
				    	<div style="float: left; clear: left;">
				    		<appfuse:label styleClass="desc" key="print.remaining.confirmation-remarks"/>
					        <form:errors path="remaining.baptism.confirmationRemarks" cssClass="fieldError"/>
					        <form:textarea path="remaining.baptism.confirmationRemarks" id="baptismRemarks" cssClass="text medium" cssErrorClass="text medium error"/>
				        </div>
				        <div style="float: left; margin-left: 20px;">
				    		<appfuse:label styleClass="desc" key="print.remaining.marriage-remarks"/>
					        <form:errors path="remaining.baptism.marriageRemarks" cssClass="fieldError"/>
					        <form:textarea path="remaining.baptism.marriageRemarks" id="marriageRemarks" cssClass="text medium" cssErrorClass="text medium error"/>
				        </div>
				        <div style="float: left; margin-left: 20px;">
				    		<appfuse:label styleClass="desc" key="print.remaining.other-remarks"/>
					        <form:errors path="remaining.baptism.otherRemarks" cssClass="fieldError"/>
					        <form:textarea path="remaining.baptism.otherRemarks" id="otherRemarks" cssClass="text medium" cssErrorClass="text medium error"/>
				        </div>
			        </fieldset>
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
						<c:param name="sort" value="baptism"/>
					</c:url>
					<appfuse:label styleClass="desc" key="print.baptism-certificate"/>
					<input type="button" name="baptismCertificate" class="button" value="<fmt:message key='button.print'/>"
						onclick="javascript:window.open('${printBapConDeaCertificateUrl}','Zaswiadczenie','height=300,width=500');"/>
				</div>
				
				<div style="float: left; margin-top: 20px; margin-left: 20px;">
					<c:url var="printBapConDeaCertificateUrl" value="/printbapcondeacertificate.html">
						<c:param name="personId" value="${requestScope.person.id}"/>
						<c:param name="sort" value="baptism"/>
						<c:param name="latin" value="true"/>
					</c:url>
					<appfuse:label styleClass="desc" key="print.baptism-certificate-latin"/>
					<input type="button" name="baptismCertificate" class="button" value="<fmt:message key='button.print'/>"
						onclick="javascript:window.open('${printBapConDeaCertificateUrl}','Zaswiadczenie','height=300,width=500');"/>
				</div>
			</li>
		</ul>	
	</form:form>
</div>

<script type="text/javascript">
    Calendar.setup({
	    inputField  : "baptismDate",      // id of the input field
	    ifFormat    : "%d-%m-%Y",      // the date format
	    button      : "baptismDateCal"    // id of the button
	});
</script>
<v:javascript formName="person" staticJavascript="false"/>
<script type="text/javascript" src="<c:url value="/scripts/validator.jsp"/>"></script>