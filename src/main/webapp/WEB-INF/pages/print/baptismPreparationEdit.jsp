 <%@ include file="/common/taglibs.jsp"%>

<head>
    <title><fmt:message key="print.baptism-preparation"/></title>
    <meta name="menu" content="PrintMenu"/>
    <script type="text/javascript" src="${ctx}/dwr/engine.js"></script>
    <script type="text/javascript" src="${ctx}/dwr/util.js"></script>
</head>

<p><spring:message code="print.baptism-preparation"/></p>

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
	
	<form:form commandName="person" method="post" action="baptismPreparationEdit.html"
	    onsubmit="return validatePerson(this)" id="person">
	    
	    	<input type="hidden" name="personId" value="${person.id}"/>
	 		<form:hidden path="remaining.baptismPreparation.id"/>
	    
			<ul>
				<li>
					<appfuse:label styleClass="desc" key="print.baptism-preparation.prepared-by"/>
			        <form:errors path="remaining.baptismPreparation.preparedBy" cssClass="fieldError"/>
			        <form:textarea path="remaining.baptismPreparation.preparedBy" id="preparedBy" cssClass="text wide" cssErrorClass="text wide error"/>
				</li>
			</ul>
			
	    	<div style="float: left; clear: both; margin-top: 20px;">
		        <input type="submit" name="submit" class="button" onclick="bCancel=false"
		            value="<fmt:message key="button.save"/>" />
		        
		        <c:url var="BaptismPreparationUrl" value="/printbaptismpreparation.html">
					<c:param name="personId" value="${requestScope.person.id}"/>
				</c:url>
				<input type="button" class="button" name="baptismPreparation" value="<fmt:message key='button.print'/>"
					onclick="javascript:window.location='${BaptismPreparationUrl}';"/>
		        
		        <input type="button" name="cancel" class="button" onclick="window.location='print/personform.html?personId=${person.id}'" value="<fmt:message key="button.cancel"/>" />
			</div>
		</ul>	
	</form:form>
</div>

<v:javascript formName="person" staticJavascript="false"/>
<script type="text/javascript" src="<c:url value="/scripts/validator.jsp"/>"></script>