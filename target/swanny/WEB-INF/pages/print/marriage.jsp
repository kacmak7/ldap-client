 <%@ include file="/common/taglibs.jsp"%>

<head>
    <title><fmt:message key="print.marriage.edit"/></title>
    <meta name="menu" content="PrintMenu"/>
    <script type="text/javascript" src="${ctx}/dwr/engine.js"></script>
    <script type="text/javascript" src="${ctx}/dwr/util.js"></script>
    
    <script type="text/javascript" src="${ctx}/scripts/calendar/calendar.js"></script>
    <script type="text/javascript" src="${ctx}/scripts/calendar/calendar-setup.js"></script>
    <script type="text/javascript" src="${ctx}/scripts/calendar/lang/calendar-pl-utf8.js"></script>
</head>

<p><spring:message code="print.marriage.edit"/></p>

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
	
	<form:form commandName="fianceePair" method="post" onsubmit="return validateFianceePair(this)" id="fianceePair">
		<ul>
			<fieldset>
				<li>
			    	<div style="float: left; clear: left;">
			    		<appfuse:label styleClass="desc" key="print.remaining.marriage-filenumber"/>
				        <form:errors path="fileNumber" cssClass="fieldError"/>
				        <form:input path="fileNumber" id="marriageFileNumber" cssClass="text small" cssErrorClass="text small error"/>
			        </div>
			        <div style="float: left; margin-left: 20px;">
			    		<appfuse:label styleClass="desc" key="print.remaining.marriage-date"/>
				        <form:errors path="marriageDate" cssClass="fieldError"/>
				        <form:input path="marriageDate" id="marriageDate" cssClass="text date" cssErrorClass="text date error"/>
				        <button id="marriageDateCal" type="button" class="calendarbutton"> ... </button>
			        </div>
			        <div style="float: left; margin-left: 20px;">
			    		<appfuse:label styleClass="desc" key="print.remaining.marriage-place"/>
				        <form:errors path="place" cssClass="fieldError"/>
				        <form:input path="place" id="marriagePlace" cssClass="text medium" cssErrorClass="text medium error"/>
			        </div>
				</li>				
			</fieldset>
		
			<li class="buttonBar bottom">
		    	<div style="float: left; clear: both; margin-top: 20px;">
			        <input type="submit" name="submit" class="button" onclick="bCancel=false"
			            value="<fmt:message key="button.save"/>" />
			            
			        <c:url var="fianceeUrl" value="/print/fianceesform.html">
			        	<c:param name="fianceePairId" value="${requestScope.fianceePair.id}"/>
			        </c:url>
			        <input type="button" name="cancel" class="button" onclick="javascript:location.href='${fianceeUrl}';"
			            value="<fmt:message key="button.cancel"/>" />
				</div>
			</li>
			<li>
				<div style="float: left; clear: left; margin-top: 20px;">
					<c:url var="printBapConDeaCertificateUrl" value="/printbapcondeacertificate.html">
						<c:param name="fianceePairId" value="${requestScope.fianceePair.id}"/>
						<c:param name="sort" value="marriage"/>
					</c:url>
					<appfuse:label styleClass="desc" key="print.marriage-certificate"/>
					<input type="button" name="marriageCertificate" class="button" value="<fmt:message key='button.print'/>"
						onclick="javascript:window.open('${printBapConDeaCertificateUrl}','Zaswiadczenie','height=300,width=500');"/>
				</div>
				
				<div style="float: left; margin-top: 20px; margin-left: 20px;">
					<c:url var="printBapConDeaCertificateUrl" value="/printbapcondeacertificate.html">
						<c:param name="fianceePairId" value="${requestScope.fianceePair.id}"/>
						<c:param name="sort" value="marriage"/>
						<c:param name="latin" value="true"/>
					</c:url>
					<appfuse:label styleClass="desc" key="print.marriage-certificate-latin"/>
					<input type="button" name="marriageCertificate" class="button" value="<fmt:message key='button.print'/>"
						onclick="javascript:window.open('${printBapConDeaCertificateUrl}','Zaswiadczenie','height=300,width=500');"/>
				</div>
			</li>
		</ul>	
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