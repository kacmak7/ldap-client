<%@ include file="/common/taglibs.jsp"%>

<head>
    <title><fmt:message key="menu.columbarium-find-list"/></title>
    <meta name="menu" content="ColumbariumMenu"/>
</head>

<div style="width: 456px;">
	<spring:bind path="nicheFilter.*">
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
	
	<form:form commandName="nicheFilter" action="submitList.html" method="post" onsubmit="return validateFind(this)" id="find">
		<ul>
			<li class="info">
		        <fmt:message key="columbarium.find.find"/>
		    </li>
		    <li>
		    	<table>
			    	<tr>
			    		<td style="vertical-align: bottom;">
					        <appfuse:label styleClass="desc" key="niche.find.grave-owner.first-name"/>
					        <form:errors path="ownerFirstName" cssClass="fieldError"/>
					        <form:input path="ownerFirstName" id="ownerFirstName" cssClass="text medium" cssErrorClass="text medium error"/>
				        </td>
				        <td style="vertical-align: bottom;">
					        <appfuse:label styleClass="desc" key="niche.find.grave-owner.surname"/>
					        <form:errors path="ownerSurname" cssClass="fieldError"/>
					        <form:input path="ownerSurname" id="ownerSurname" cssClass="text medium" cssErrorClass="text medium error"/>
				        </td>
				        <td style="vertical-align: bottom;">
					        <appfuse:label styleClass="desc" key="niche.find.dead-person.first-name"/>
					        <form:errors path="deadPersonFirstName" cssClass="fieldError"/>
					        <form:input path="deadPersonFirstName" id="deadPersonFirstName" cssClass="text medium" cssErrorClass="text medium error"/>
				        </td>
				        <td style="vertical-align: bottom;">
					        <appfuse:label styleClass="desc" key="niche.find.dead-person.surname"/>
					        <form:errors path="deadPersonSurname" cssClass="fieldError"/>
					        <form:input path="deadPersonSurname" id="deadPersonSurname" cssClass="text medium" cssErrorClass="text medium error"/>
				        </td>
				        <td style="vertical-align: bottom;">
					        <appfuse:label styleClass="desc" key="niche.find.niche-number"/>
					        <form:errors path="number" cssClass="fieldError"/>
					        <form:input path="number" id="graveNumber" cssClass="text medium" cssErrorClass="text medium error"/>
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
	<display:table name="nichesList" cellspacing="0" cellpadding="0" requestURI=""
	    defaultsort="1" id="niche" pagesize="15" class="table" export="false" style="margin-bottom: 10px;">
		<display:column property="owner.firstName" escapeXml="true" sortable="true" titleKey="niche.owner.first-name" style="width: 20%"
			url="/columbarium/nicheDetails.html" paramId="nicheId" paramProperty="id"/>
		<display:column property="owner.surname" escapeXml="true" sortable="true" titleKey="niche.owner.surname" style="width: 20%"
			url="/columbarium/nicheDetails.html" paramId="nicheId" paramProperty="id"/>
		<display:column property="personsHtml" escapeXml="true" sortable="true" titleKey="niche.persons" style="width: 28%"
			url="/columbarium/nicheDetails.html" paramId="nicheId" paramProperty="id"/>
		<display:column property="number" escapeXml="true" sortable="true" titleKey="niche.find.niche-number" style="width: 12%"
			url="/columbarium/nicheDetails.html" paramId="nicheId" paramProperty="id"/>
		<display:column property="columbarium.name" escapeXml="true" sortable="true" titleKey="niche.columbarium" style="width: 20%"
			url="/columbarium/nicheDetails.html" paramId="nicheId" paramProperty="id"/>
	</display:table>
</div>

<v:javascript formName="find" staticJavascript="false"/>
<script type="text/javascript" src="<c:url value="/scripts/validator.jsp"/>"></script>