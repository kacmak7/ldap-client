<%@ include file="/common/taglibs.jsp"%>

<head>
    <title><fmt:message key="menu.graveyard-find"/></title>
    <meta name="menu" content="GraveyardMenu"/>
</head>

<div style="width: 456px;">
	<spring:bind path="graveFilter.*">
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
	
	<form:form commandName="graveFilter" method="post" onsubmit="return validateFind(this)" id="find">
		<ul>
			<li class="info">
		        <fmt:message key="grave.find.find"/>
		    </li>
		    <li>
		    	<table>
			    	<tr>
			    		<td>
					        <appfuse:label styleClass="desc" key="grave.find.grave-owner.first-name"/>
					        <form:errors path="graveOwnerFirstName" cssClass="fieldError"/>
					        <form:input path="graveOwnerFirstName" id="graveOwnerFirstName" cssClass="text medium" cssErrorClass="text medium error"/>
				        </td>
				        <td>
					        <appfuse:label styleClass="desc" key="grave.find.grave-owner.surname"/>
					        <form:errors path="graveOwnerSurname" cssClass="fieldError"/>
					        <form:input path="graveOwnerSurname" id="graveOwnerSurname" cssClass="text medium" cssErrorClass="text medium error"/>
				        </td>
			        </tr>
		        </table>
		    </li>
		    
		    <li>
		    	<table>
			    	<tr>
			    		<td>
					        <appfuse:label styleClass="desc" key="grave.find.dead-person.first-name"/>
					        <form:errors path="deadPersonFirstName" cssClass="fieldError"/>
					        <form:input path="deadPersonFirstName" id="deadPersonFirstName" cssClass="text medium" cssErrorClass="text medium error"/>
				        </td>
				        <td>
					        <appfuse:label styleClass="desc" key="grave.find.dead-person.surname"/>
					        <form:errors path="deadPersonSurname" cssClass="fieldError"/>
					        <form:input path="deadPersonSurname" id="deadPersonSurname" cssClass="text medium" cssErrorClass="text medium error"/>
				        </td>
			        </tr>
		        </table>
		    </li>
		    
		    <li>
		    	<table>
			    	<tr>
			    		<td>
					        <appfuse:label styleClass="desc" key="grave.find.grave-number"/>
					        <form:errors path="graveNumber" cssClass="fieldError"/>
					        <form:input path="graveNumber" id="graveNumber" cssClass="text medium" cssErrorClass="text medium error"/>
				        </td>
			        </tr>
		        </table>
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

<v:javascript formName="find" staticJavascript="false"/>
<script type="text/javascript" src="<c:url value="/scripts/validator.jsp"/>"></script>