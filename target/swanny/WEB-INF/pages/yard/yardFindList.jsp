<%@ include file="/common/taglibs.jsp"%>

<head>
    <title><fmt:message key="menu.graveyard-find-list"/></title>
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
			    		<td style="vertical-align: bottom;">
					        <appfuse:label styleClass="desc" key="grave.find.grave-owner.first-name"/>
					        <form:errors path="graveOwnerFirstName" cssClass="fieldError"/>
					        <form:input path="graveOwnerFirstName" id="graveOwnerFirstName" cssClass="text medium" cssErrorClass="text medium error"/>
				        </td>
				        <td style="vertical-align: bottom;">
					        <appfuse:label styleClass="desc" key="grave.find.grave-owner.surname"/>
					        <form:errors path="graveOwnerSurname" cssClass="fieldError"/>
					        <form:input path="graveOwnerSurname" id="graveOwnerSurname" cssClass="text medium" cssErrorClass="text medium error"/>
				        </td>
				        <td style="vertical-align: bottom;">
					        <appfuse:label styleClass="desc" key="grave.find.dead-person.first-name"/>
					        <form:errors path="deadPersonFirstName" cssClass="fieldError"/>
					        <form:input path="deadPersonFirstName" id="deadPersonFirstName" cssClass="text medium" cssErrorClass="text medium error"/>
				        </td>
				        <td style="vertical-align: bottom;">
					        <appfuse:label styleClass="desc" key="grave.find.dead-person.surname"/>
					        <form:errors path="deadPersonSurname" cssClass="fieldError"/>
					        <form:input path="deadPersonSurname" id="deadPersonSurname" cssClass="text medium" cssErrorClass="text medium error"/>
				        </td>
				        <td style="vertical-align: bottom;">
					        <appfuse:label styleClass="desc" key="grave.find.grave-number"/>
					        <form:errors path="graveNumber" cssClass="fieldError"/>
					        <form:input path="graveNumber" id="graveNumber" cssClass="text medium" cssErrorClass="text medium error"/>
				        </td>
				        <td style="vertical-align: bottom;">
				        	<input type="submit" name="save" class="button" onclick="bCancel=false"
			            		value="<fmt:message key="button.search"/>" />
				        </td>
			        </tr>
		        </table>
		    </li>
		</ul>
	</form:form>
</div>

<div>
	<display:table name="gravesList" cellspacing="0" cellpadding="0" requestURI=""
	    defaultsort="1" id="grave" pagesize="15" class="table" export="false" style="margin-bottom: 10px;">
		<display:column property="owner.firstName" escapeXml="true" sortable="true" titleKey="grave.owner.first-name" style="width: 25%"
			url="/yard/graveDetails.html" paramId="graveId" paramProperty="id"/>
		<display:column property="owner.surname" escapeXml="true" sortable="true" titleKey="grave.owner.surname" style="width: 25%"
			url="/yard/graveDetails.html" paramId="graveId" paramProperty="id"/>
		<display:column property="personsHtml" escapeXml="true" sortable="true" titleKey="grave.persons" style="width: 25%"
			url="/yard/graveDetails.html" paramId="graveId" paramProperty="id"/>
		<display:column property="sector" escapeXml="true" sortable="true" titleKey="grave.sector" style="width: 25%"
			url="/yard/graveDetails.html" paramId="graveId" paramProperty="id"/>
		<display:column titleKey="grave.find.on-map" url="/yard/yard.html" paramId="graveId" paramProperty="id">
			<fmt:message key="button.search"/>
		</display:column>
	</display:table>
</div>

<v:javascript formName="find" staticJavascript="false"/>
<script type="text/javascript" src="<c:url value="/scripts/validator.jsp"/>"></script>