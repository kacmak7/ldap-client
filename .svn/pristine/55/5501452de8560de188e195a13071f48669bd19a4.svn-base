 <%@ include file="/common/taglibs.jsp"%>

<head>
    <title><fmt:message key="print.death.edit"/></title>
    <meta name="menu" content="PrintMenu"/>
    <script type="text/javascript" src="${ctx}/dwr/engine.js"></script>
    <script type="text/javascript" src="${ctx}/dwr/util.js"></script>
    
    <script type="text/javascript" src="${ctx}/scripts/calendar/calendar.js"></script>
    <script type="text/javascript" src="${ctx}/scripts/calendar/calendar-setup.js"></script>
    <script type="text/javascript" src="${ctx}/scripts/calendar/lang/calendar-pl-utf8.js"></script>

	<script type="text/javascript" src="${ctx}/scripts/sha1-min.js"></script>
</head>

<p><spring:message code="print.death.edit"/></p>

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
	

	    	<form:hidden path="remaining.death.id"/>
		
		<ul>
			<fieldset>
				<li>
			    	<div style="float: left; clear: left;">
			    		<appfuse:label styleClass="desc" key="print.remaining.death-filenumber"/>
				        <form:errors path="remaining.death.fileNumber" cssClass="fieldError"/>
				        <form:input path="remaining.death.fileNumber" id="deathFileNumber" cssClass="text small" cssErrorClass="text small error"/>
			        </div>
			        <div style="float: left; margin-left: 20px;">
			    		<appfuse:label styleClass="desc" key="print.remaining.death-date"/>
				        <form:errors path="remaining.death.date" cssClass="fieldError"/>
				        <form:input path="remaining.death.date" id="deathDate" cssClass="text date" cssErrorClass="text date error"/>
				        <button id="deathDateCal" type="button" class="calendarbutton"> ... </button>
			        </div>
			        <div style="float: left; margin-left: 20px;">
			    		<appfuse:label styleClass="desc" key="print.remaining.death-burial-date"/>
				        <form:errors path="remaining.death.burialDate" cssClass="fieldError"/>
				        <form:input path="remaining.death.burialDate" id="burialDate" cssClass="text date" cssErrorClass="text date error"/>
				        <button id="burialDateCal" type="button" class="calendarbutton"> ... </button>
			        </div>
			        <div style="float: left; margin-left: 20px;">
			    		<appfuse:label styleClass="desc" key="print.remaining.death-place"/>
				        <form:errors path="remaining.death.place" cssClass="fieldError"/>
				        <form:input path="remaining.death.place" id="deathPlace" cssClass="text medium" cssErrorClass="text medium error"/>
			        </div>
			        <div style="float: left; margin-left: 20px;">
			    		<appfuse:label styleClass="desc" key="print.remaining.burial-place"/>
				        <form:errors path="remaining.death.burialPlace" cssClass="fieldError"/>
				        <form:input path="remaining.death.burialPlace" id="burialPlace" cssClass="text medium" cssErrorClass="text medium error"/>
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
						<c:param name="sort" value="death"/>
					</c:url>
					<appfuse:label styleClass="desc" key="print.death-certificate"/>
					<input type="button" name="deathCertificate" class="button" value="<fmt:message key='button.print'/>"
						onclick="javascript:window.open('${printBapConDeaCertificateUrl}','Zaswiadczenie','height=300,width=500');"/>
				</div>
				
				<div style="float: left; margin-top: 20px; margin-left: 20px;">
					<c:url var="printBapConDeaCertificateUrl" value="/printbapcondeacertificate.html">
						<c:param name="personId" value="${requestScope.person.id}"/>
						<c:param name="sort" value="death"/>
						<c:param name="latin" value="true"/>
					</c:url>
					<appfuse:label styleClass="desc" key="print.death-certificate-latin"/>
					<input type="button" name="deathCertificate" class="button" value="<fmt:message key='button.print'/>"
						onclick="javascript:window.open('${printBapConDeaCertificateUrl}','Zaswiadczenie','height=300,width=500');"/>
				</div>
			</li>
		</ul>	
	</form:form>
</div>

<script type="text/javascript">

	Calendar.setup({
	    inputField  : "deathDate",      // id of the input field
	    ifFormat    : "%d-%m-%Y",      // the date format
	    button      : "deathDateCal"    // id of the button
	});
	
	Calendar.setup({
	    inputField  : "burialDate",      // id of the input field
	    ifFormat    : "%d-%m-%Y",      // the date format
	    button      : "burialDateCal"    // id of the button
	});
</script>
<v:javascript formName="person" staticJavascript="false"/>
<script type="text/javascript" src="<c:url value="/scripts/validator.jsp"/>"></script>