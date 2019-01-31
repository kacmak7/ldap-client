<%@ include file="/common/taglibs.jsp"%>

<head>
    <title><fmt:message key="tomes.editCelebrant.title"/></title>
    <meta name="menu" content="PrintMenu"/>
    
	<script type="text/javascript" src="${ctx}/dwr/engine.js"></script>
	<script type="text/javascript" src="${ctx}/dwr/util.js"></script>
</head>

<p><spring:message code="tomes.editCelebrant.title"/></p>

<div style="width: 556px;">
	<spring:bind path="celebrant.*">
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
	
	<form:form method="post" commandName="celebrant">
		<ul>
		    <li>
			    <appfuse:label styleClass="desc" key="tomes.editCelebrant.table.first-name"/>
			    <form:errors path="firstName" cssClass="fieldError"/>
			    <form:input path="firstName" id="firstName" cssClass="text medium" cssErrorClass="text medium error" maxlength="63"/>
			</li>
			<li>
			    <appfuse:label styleClass="desc" key="tomes.editCelebrant.table.surname"/>
			    <form:errors path="surname" cssClass="fieldError"/>		        
			    <form:input path="surname" id="surname" cssClass="text medium" cssErrorClass="text medium error" maxlength="63"/>
		    </li>
		    <li>
			    <appfuse:label styleClass="desc" key="tomes.editCelebrant.active"/>
			    <form:errors path="active" cssClass="fieldError"/>		        
			    <form:checkbox path="active" id="active" cssClass="" cssErrorClass="text medium error"/>
		    </li>
		    
		    <li class="buttonBar bottom">
		    	<div style="clear: both; float: left; margin-top: 10px;">
			        <input type="submit" name="save" class="button" onclick="bCancel=false"
			            value="<fmt:message key="button.save"/>" />
			        <input type="button" name="cancel" class="button" onclick="javascript:window.close();"
			            value="<fmt:message key="button.cancel"/>" />
			    </div>
		    </li>
		</ul>
	</form:form>
</div>

<v:javascript formName="celebrant" staticJavascript="false"/>
<script type="text/javascript" src="<c:url value="/scripts/validator.jsp"/>"></script>