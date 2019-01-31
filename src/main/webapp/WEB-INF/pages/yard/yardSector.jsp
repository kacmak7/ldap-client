<%@ include file="/common/taglibs.jsp"%>

<head>
    <title><fmt:message key="menu.graveyard-sector"/></title>
    <meta name="menu" content="GraveyardMenu"/>
</head>

<div style="width: 456px;">
	<spring:bind path="sector.*">
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
	
	<form:form commandName="sector" method="post" onsubmit="return validateSector(this)" id="sector">
		<ul>
			<li class="info">
		        <fmt:message key="grave.sector.choose"/>
		    </li>
		    <li>
		        <appfuse:label styleClass="desc" key="grave.sector.choose.sector"/>
		        <form:errors path="sector" cssClass="fieldError"/>
		        <form:select path="sector" items="${requestScope.sectorsList}" id="sector" cssClass="text medium" cssErrorClass="text medium error"/>
		    </li>
		    
		    <li class="buttonBar">
		    	<div style="clear: both; float: left; margin-top: 10px;">
			        <input type="submit" name="save" class="button" onclick="bCancel=false"
			            value="<fmt:message key="button.done"/>" />
			        <input type="button" name="cancel" class="button" onclick="bCancel=true"
			            value="<fmt:message key="button.cancel"/>" />
			    </div>
		    </li>
		</ul>
	</form:form>
</div>

<v:javascript formName="sector" staticJavascript="false"/>
<script type="text/javascript" src="<c:url value="/scripts/validator.jsp"/>"></script>