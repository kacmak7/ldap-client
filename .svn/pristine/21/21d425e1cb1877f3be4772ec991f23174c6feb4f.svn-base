 <%@ include file="/common/taglibs.jsp"%>

<head>
    <title><fmt:message key="print.baptism-changes"/></title>
    <meta name="menu" content="PrintMenu"/>
    <script type="text/javascript" src="${ctx}/dwr/engine.js"></script>
    <script type="text/javascript" src="${ctx}/dwr/util.js"></script>
</head>

<p><spring:message code="print.baptism-changes"/></p>

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
	
	<form:form commandName="person" method="post" action="baptismchangesEdit.html"
	    onsubmit="return validatePerson(this)" id="person">
	    
	    	<input type="hidden" name="personId" value="${person.id}"/>
	    	<form:hidden path="remaining.baptismChanges.id"/>
	
			<ul>
				<li>
					<appfuse:label styleClass="desc" key="print.baptism-changes.baptism-number"/>
			        <form:errors path="remaining.baptism.fileNumber" cssClass="fieldError"/>
			        <form:input path="remaining.baptism.fileNumber" id="fileNumber" cssClass="text wide" cssErrorClass="text wide error" disabled="true"/>
				</li>
				<li>
					<appfuse:label styleClass="desc" key="print.baptism-changes.personal-data"/>
			        <form:errors path="remaining.baptismChanges.personalData" cssClass="fieldError"/>
			        <form:textarea path="remaining.baptismChanges.personalData" id="personalData" cssClass="text wide" cssErrorClass="text wide error"/>
				</li>
				<li>
					<appfuse:label styleClass="desc" key="print.baptism-changes.civil-personal-data"/>
			        <form:errors path="remaining.baptismChanges.civilPersonalData" cssClass="fieldError"/>
			        <form:textarea path="remaining.baptismChanges.civilPersonalData" id="civilPersonalData" cssClass="text wide" cssErrorClass="text wide error"/>
				</li>
				<li>
					<appfuse:label styleClass="desc" key="print.baptism-changes.civil-documents"/>
			        <form:errors path="remaining.baptismChanges.civilDocuments" cssClass="fieldError"/>
			        <form:input path="remaining.baptismChanges.civilDocuments" id="civilDocuments" cssClass="text medium" cssErrorClass="text medium error"/>
				</li>
				<li>
					<appfuse:label styleClass="desc" key="print.baptism-changes.differences"/>
			        <form:errors path="remaining.baptismChanges.differences" cssClass="fieldError"/>
			        <form:textarea path="remaining.baptismChanges.differences" id="differences" cssClass="text wide" cssErrorClass="text wide error"/>
				</li>
			</ul>

	    	<div style="float: left; clear: both; margin-top: 20px;">
		        <input type="submit" name="submit" class="button" onclick="bCancel=false"
		            value="<fmt:message key="button.save"/>" />
		        
		        <c:url var="baptismChangesUrl" value="/printbaptismchangesfile.html">
					<c:param name="personId" value="${requestScope.person.id}"/>
				</c:url>
				<input type="button" class="button" name="baptismChanges" value="<fmt:message key='button.print'/>"
					onclick="javascript:window.location='${baptismChangesUrl}';"/>
							        
		        <input type="button" name="cancel" class="button" onclick="window.location='print/personform.html?personId=${person.id}'" value="<fmt:message key="button.cancel"/>" />
			</div>

	</form:form>
</div>

<v:javascript formName="person" staticJavascript="false"/>
<script type="text/javascript" src="<c:url value="/scripts/validator.jsp"/>"></script>