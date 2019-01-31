<%@ include file="/common/taglibs.jsp"%>

<head>
    <title><fmt:message key="niche.details.title"/></title>
    <meta name="menu" content="ColumbariumMenu"/>
    
    <script type="text/javascript" src="${ctx}/dwr/interface/GraveyardManager.js"></script>
	<script type="text/javascript" src="${ctx}/dwr/engine.js"></script>
	<script type="text/javascript" src="${ctx}/dwr/util.js"></script>
	<script type="text/javascript" src="${ctx}/scripts/pages/graveDetails.js"></script>
</head>

<div style="width: 456px;">
	<spring:bind path="niche.*">
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
	
	<form:form commandName="niche" action="submitNiche.html" method="post" enctype="multipart/form-data"
	    onsubmit="return validateNiche(this)" id="niche">
	    <form:hidden path="columbarium.id" id="columbariumId"/>
	    <form:hidden path="id" id="nicheId"/>
	
		<ul>
			<li class="info">
		        <fmt:message key="niche.details.text"/>
		    </li>
		    <li>
		        <appfuse:label styleClass="desc" key="niche.number"/>
		        <form:errors path="number" cssClass="fieldError"/>
		        <form:input path="number" id="number" cssClass="text medium" cssErrorClass="text medium error"/>
		    </li>
		    <li>
		    	<fieldset style="padding-bottom: 20px; width: 800px; text-align: left;">
		    	<legend><fmt:message key="niche.owner"/></legend>
		    		<table>
		    			<tr>
		    				<td>
						        <appfuse:label styleClass="desc" key="niche.owner.surname"/>
						        <form:errors path="owner.surname" cssClass="fieldError"/>
						        <form:input path="owner.surname" id="owner.surname" cssClass="text medium" cssErrorClass="text medium error" maxlength="50"/>
			        		</td>
			        		<td>
						        <appfuse:label styleClass="desc" key="niche.owner.first-name"/>
						        <form:errors path="owner.firstName" cssClass="fieldError"/>
						        <form:input path="owner.firstName" id="owner.firstName" cssClass="text medium" cssErrorClass="text medium error" maxlength="50"/>
			        		</td>
			        		<td>
						        <appfuse:label styleClass="desc" key="niche.owner.phone"/>
						        <form:errors path="owner.phone" cssClass="fieldError"/>
						        <form:input path="owner.phone" id="owner.phone" cssClass="text medium" cssErrorClass="text medium error" maxlength="50"/>
			        		</td>
			           	</tr>
			           	<tr>
							<td>
						        <appfuse:label styleClass="desc" key="niche.owner.address-place"/>
						        <form:errors path="owner.address.place" cssClass="fieldError"/>
						        <form:input path="owner.address.place" id="owner.address.place" cssClass="text medium" cssErrorClass="text medium error" maxlength="100"/>
						    </td>
							<td>
						   	    <appfuse:label styleClass="desc" key="niche.owner.address-postcode"/>
						        <form:errors path="owner.address.postCode" cssClass="fieldError"/>
						        <form:input path="owner.address.postCode" id="owner.address.postCode" cssClass="text medium" cssErrorClass="text medium error" maxlength="100"/>
				   			</td>
							<td>
						        <appfuse:label styleClass="desc" key="niche.owner.address-street"/>
						        <form:errors path="owner.address.street" cssClass="fieldError"/>
						        <form:input path="owner.address.street" id="owner.address.street" cssClass="text medium" cssErrorClass="text medium error" maxlength="100"/>
						   	</td>
							<td>
						        <appfuse:label styleClass="desc" key="niche.owner.address-first-number"/>
						        <form:errors path="owner.address.firstNumber" cssClass="fieldError"/>
						        <form:input path="owner.address.firstNumber" id="owner.address.firstNumber" cssClass="text medium" cssErrorClass="text medium error" maxlength="100"/>
						    </td>
						</tr>
						<tr>
							<td>
							   	<appfuse:label styleClass="desc" key="niche.owner.address-last-number"/>
						        <form:errors path="owner.address.lastNumber" cssClass="fieldError"/>
						        <form:input path="owner.address.lastNumber" id="owner.address.lastNumber" cssClass="text medium" cssErrorClass="text medium error" maxlength="100"/>
						    </td>
						</tr>
			        </table>
		        </fieldset>
		    </li>
		    <li>
			    <div style="float: left;">
			        <appfuse:label key="niche.type" styleClass="desc"/>
			        <form:errors path="owned" cssClass="fieldError"/>
			        <form:select path="owned" cssClass="text" cssErrorClass="text error">
						<form:options items="${requestScope.graveOwnedList}" itemLabel="name" itemValue="id"/>
					</form:select>
			    </div>
			</li>
			
			<li>
				<table style="width: 824px;">
					<tr><td style="width: 400px;">
						<fieldset style="padding-bottom: 20px; text-align: left;">
					    	<legend><fmt:message key="niche.persons"/></legend>
							
							<table class="table" style="margin-top: 20px;">
								<thead>
									<th style="width: 150px;"><fmt:message key="persons.first-name"/></th>
									<th style="width: 150px;"><fmt:message key="persons.last-name"/></th>
									<th style="width: 100px;"><fmt:message key="persons.death-date"/></th>
									<th><fmt:message key="label.actions"/></th>
								</thead>
								<tbody id="personsbody">
									<tr id="pattern" style="display:none;">
										<td>
											<input id="personId" type="hidden"/>
											<input id="personFirstName" size="14"/>
										</td>
										<td>
											<input id="personLastName" size="14"/>
										</td>
										<td>
											<input id="personDeathDate" size="8"/>
										</td>
										<td>
											<input id="deletePersonButton" type="button" value="<fmt:message key='button.delete'/>" onclick="javascript:deletePerson(this.id);"/>
										</td>
									</tr>
								</tbody>
							</table>
						
							<table class="plain">
								<tr>
									<td>
										<input id="personFirstNameIn" type="text" size="14" maxlength="30"/>
									</td>
									<td>
										<input id="personLastNameIn" type="text" size="15" maxlength="30"/>
									</td>
									<td>
										<input id="personDeathDateIn" type="text" size="9" maxlength="10"/>
									</td>
								</tr>
								<tr>
									<td>
										<input type="button" value="<fmt:message key="button.add"/>" onclick="writePerson()"/>
										<input type="button" value="<fmt:message key="button.clear"/>" onclick="clearPersons()"/>
									</td>
								</tr>
							</table>
						</fieldset></td>
						<td align="right" valign="top">
						<fieldset style="padding-bottom: 20px; text-align: left; width: 270px;height: 160px;">
							<legend><fmt:message key="notices"/></legend>
							<form:textarea path="notices" cols="35" rows="8"/>
						</fieldset>
					</td></tr>
				</table>
			</li>
			
			<li>
	    		<table style="width: 800px;">
	    			<tr>
					    <td>
					        <appfuse:label styleClass="desc" key="niche.valid-to"/>
					        <form:errors path="validTo" cssClass="fieldError"/>
					        <form:input path="validTo" id="validTo" cssClass="text medium" cssErrorClass="text medium error" maxlength="4"/>
					    </td>
		        	</tr>
		        </table>
			</li>
			    
		    <li>
				<fieldset>
	  				<legend><fmt:message key="files"/></legend>
	    			<table border="1" class="table" id="filesTable" style="width: 500px; <c:if test="${empty requestScope.niche.files}">display: none;</c:if>">
						<thead>
							<th style="width: 300px;"><fmt:message key="file.name"/></th>
							<th style="width: 240px;"><fmt:message key="file.length"/></th>
							<th style="width: 100px;"><fmt:message key="label.actions"/></th>
						</thead>
						
						<tbody id="filesbody">
							<tr id="patternFile" style="display:none;">
								<td>
									<span id="tableFileName"></span>
									<input type="hidden" id="tableFileId"/>
									<input type="hidden" id="tableHiddenFileName"/>
								</td>
								<td>
									<span id="tableFileLength"></span>
								</td>
								<td>
									<input id="delete" type="button" value="<fmt:message key='button.delete'/>" onclick="deleteFileClicked(this.id)"/>
								</td>
							</tr>
						</tbody>
						<input type="hidden" id="deletedFilesIds" name="deletedFilesIds" value=""/>
					</table>
					
					<table class="plain">
						<th style="text-align: left;"><fmt:message key="add-file"/></th>
						<tr id="newFiles">
							<td>
								<input name="fileData" type="file" onchange="javascript:writeFile();"/>
							</td>
						</tr>
					</table>
				</fieldset>
			</li>
			
			<li class="buttonBar">
		    	<div style="clear: both; float: left; margin-top: 10px;">
			        <input type="submit" name="save" class="button" onclick="bCancel=false"
			            value="<fmt:message key="button.save"/>" />
			            
			        <input type="button" name="cancel" class="button" onclick="javascript:window.location='${ctx}/columbarium/columbarium.html';"
			            value="<fmt:message key="button.cancel"/>" />
			    </div>
		    </li>
		    
		</ul>
	</form:form>
</div>

<c:choose>
	<c:when test="${not empty requestScope.niche.id}">
		<c:set var="nicheId" value="${requestScope.niche.id}"/>
	</c:when>
	<c:otherwise>
		<c:set var="nicheId" value="0"/>
	</c:otherwise>
</c:choose>

<script type="text/javascript">
    function deleteFile() {
   		GraveyardManager.removePhoto('${requestScope.photoUrl}', ${nicheId}, showSuccessMessage);
    }

    function showSuccessMessage(value) {
        window.location.reload();
        if (value)
            alert('<fmt:message key="photo.deleted"/>')
        else
        	alert('<fmt:message key="errors.deleting.grave-photo"/>')
    }

    function errorHandler(msg) {
    	alert(msg);
    }

	dwr.engine.setErrorHandler(errorHandler)
	
	var personsArray = []
	<c:forEach items="${requestScope.niche.persons}" var="person" varStatus="gridRow">
		personsArray[${gridRow.index}] = { jsId:${gridRow.index}+1, personId:'${person.id}', personFirstNameIn:'${person.firstName}',
				personLastNameIn:'${person.surname}', personDeathDateIn:'${person.deathDate}' }
	</c:forEach>

    var filesArray = []
    <c:forEach items="${requestScope.niche.files}" var="file" varStatus="gridRow">
		filesArray[${gridRow.index}] = { jsId:${gridRow.index}+1, id:${file.id}, fileName:'${file.originalName}', length:'${file.size}', contentType:'${file.contentType}' }
	</c:forEach>

	var uploadDir = '${requestScope.uploadDir}'
	var ctx = '${ctx}'

	initNiche()
</script>
<v:javascript formName="niche" staticJavascript="false"/>
<script type="text/javascript" src="<c:url value="/scripts/validator.jsp"/>"></script>