<%@ include file="/common/taglibs.jsp"%>

<head>
    <title><fmt:message key="print.fiancee.edit"/></title>
    <meta name="menu" content="PrintMenu"/>
    <script type="text/javascript" src="${ctx}/dwr/engine.js"></script>
    <script type="text/javascript" src="${ctx}/dwr/util.js"></script>
    
    <script type="text/javascript" src="${ctx}/scripts/calendar/calendar.js"></script>
    <script type="text/javascript" src="${ctx}/scripts/calendar/calendar-setup.js"></script>
    <script type="text/javascript" src="${ctx}/scripts/calendar/lang/calendar-pl-utf8.js"></script>
    
    <script type="text/javascript" src="${ctx}/scripts/sha1-min.js"></script>
</head>

<p><spring:message code="print.fiancee.edit"/></p>

<div>
	<spring:bind path="fianceePair.*">
	    <c:if test="${not empty status.errorMessages}">
	    <div class="error">
	        <c:forEach var="error" items="${status.errorMessages}">
	            <img src="<c:url value="/images/iconWarning.gif"/>"
	                alt="<fmt:message key="icon.warning"/>" class="icon" />
	            <c:out value="${error}" escapeXml="false"/><br/>
	        </c:forEach>
	    </div>
	    </c:if>
	</spring:bind>
	
	<div class="separator"></div>
	
	<form:form commandName="fianceePair" method="post"
	    onsubmit="return validateFianceePair(this)" id="fianceePair">
	    
		<form:hidden path="id"/>
	    <input type="hidden" name="person1Id" value="${fianceePair.fianceeHe.id}"/>
	    <input type="hidden" name="person2Id" value="${fianceePair.fianceeShe.id}"/>
	    
	    <form:hidden path="fianceeHe.id"/>
	    <form:hidden path="fianceeShe.id"/>
	    <form:hidden path="fianceeHe.address.id"/>
	    <form:hidden path="fianceeShe.address.id"/>
	<ul>
		<li>
		    <fieldset style="padding-bottom: 20px; width: 800px; text-align: left;">
				<legend><fmt:message key="print.fianceehe.fullname"/></legend>
				<table>
					<tr>
						<td style="width: 200px;">
					        <appfuse:label styleClass="desc" key="print.fiancee.firstname"/>
					        <form:errors path="fianceeHe.firstName" cssClass="fieldError"/>
					        <form:input path="fianceeHe.firstName" id="fianceeHe.firstName" cssClass="text medium" cssErrorClass="text medium error" maxlength="50"/>
					    </td>
					    
						<td style="width: 200px;">
					        <appfuse:label styleClass="desc" key="print.fiancee.lastname"/>
					        <form:errors path="fianceeHe.surname" cssClass="fieldError"/>
					        <form:input path="fianceeHe.surname" id="fianceeHe.surname" cssClass="text medium" cssErrorClass="text medium error" maxlength="50"/>
					   	</td>
					   	
						<td style="width: 200px;">
					        <appfuse:label styleClass="desc" key="print.fiancee.parish"/>
					        <form:errors path="fianceeHe.remaining.marriage.parish" cssClass="fieldError"/>
					        <form:input path="fianceeHe.remaining.marriage.parish" id="fianceeHe.parish" cssClass="text medium" cssErrorClass="text medium error" maxlength="100"/>
					   	</td>
						<td style="width: 200px;">
							<appfuse:label styleClass="desc" key="print.fiancee.birth-date"/>
					        <form:errors path="fianceeHe.birthDate" cssClass="fieldError"/>
					        <form:input path="fianceeHe.birthDate" id="fianceeHeBirthDateIn" cssClass="text date" cssErrorClass="text medium error" maxlength="10"/>
					        <button id="fianceeHeBirthDateInCal" type="button" class="calendarbutton"> ... </button>
					    </td>
					    <td>
					   	    <appfuse:label styleClass="desc" key="print.fiancee.birth-place"/>
					        <form:errors path="fianceeHe.remaining.birthPlace" cssClass="fieldError"/>
					        <form:input path="fianceeHe.remaining.birthPlace" id="fianceeHe.birthPlace" cssClass="text medium" cssErrorClass="text medium error" maxlength="100"/>
						</td>
					</tr>
					<tr>
						<td>
					        <appfuse:label styleClass="desc" key="print.fiancee.fathers-first-name"/>
					        <form:errors path="fianceeHe.remaining.fathersFirstName" cssClass="fieldError"/>
					        <form:input path="fianceeHe.remaining.fathersFirstName" id="fianceeHe.fathersFirstName" cssClass="text medium" cssErrorClass="text medium error" maxlength="100"/>
					   	</td>
					   	<td>
					        <appfuse:label styleClass="desc" key="print.fiancee.fathers-last-name"/>
					        <form:errors path="fianceeHe.remaining.fathersLastName" cssClass="fieldError"/>
					        <form:input path="fianceeHe.remaining.fathersLastName" id="fianceeHe.fathersLastName" cssClass="text medium" cssErrorClass="text medium error" maxlength="100"/>
					   	</td>
						<td>
					        <appfuse:label styleClass="desc" key="print.fiancee.mothers-first-name"/>
					        <form:errors path="fianceeHe.remaining.mothersFirstName" cssClass="fieldError"/>
					        <form:input path="fianceeHe.remaining.mothersFirstName" id="fianceeHe.mothersFirstName" cssClass="text medium" cssErrorClass="text medium error" maxlength="100"/>
					   	</td>
					   	<td>
					        <appfuse:label styleClass="desc" key="print.remaining.mothers-maidensname"/>
					        <form:errors path="fianceeHe.remaining.mothersMaidensName" cssClass="fieldError"/>
					        <form:input path="fianceeHe.remaining.mothersMaidensName" id="fianceeHe.mothersMaidensName" cssClass="text medium" cssErrorClass="text medium error" maxlength="100"/>
					   	</td>
					   	<td>
					        <appfuse:label styleClass="desc" key="print.fiancee.religion"/>
					        <form:errors path="fianceeHe.remaining.marriage.religion" cssClass="fieldError"/>
					        <form:input path="fianceeHe.remaining.marriage.religion" id="fianceeHe.religion" cssClass="text medium" cssErrorClass="text medium error" maxlength="100"/>
					   	</td>
					</tr>
					<tr>
						<td>
					        <appfuse:label styleClass="desc" key="print.fiancee.address-place"/>
					        <form:errors path="fianceeHe.address.place" cssClass="fieldError"/>
					        <form:input path="fianceeHe.address.place" id="fianceeHe.address.place" cssClass="text medium" cssErrorClass="text medium error" maxlength="100"/>
					    </td>
						<td>
					   	    <appfuse:label styleClass="desc" key="print.fiancee.address-postcode"/>
					        <form:errors path="fianceeHe.address.postCode" cssClass="fieldError"/>
					        <form:input path="fianceeHe.address.postCode" id="fianceeHe.address.postCode" cssClass="text medium" cssErrorClass="text medium error" maxlength="100"/>
			   			</td>
						<td>
					        <appfuse:label styleClass="desc" key="print.fiancee.address-street"/>
					        <form:errors path="fianceeHe.address.street" cssClass="fieldError"/>
					        <form:input path="fianceeHe.address.street" id="fianceeHe.address.street" cssClass="text medium" cssErrorClass="text medium error" maxlength="100"/>
					   	</td>
						<td>
					        <appfuse:label styleClass="desc" key="print.fiancee.address-first-number"/>
					        <form:errors path="fianceeHe.address.firstNumber" cssClass="fieldError"/>
					        <form:input path="fianceeHe.address.firstNumber" id="fianceeHe.address.firstNumber" cssClass="text medium" cssErrorClass="text medium error" maxlength="100"/>
					    </td>
					    <td>
						   	<appfuse:label styleClass="desc" key="print.fiancee.address-last-number"/>
					        <form:errors path="fianceeHe.address.lastNumber" cssClass="fieldError"/>
					        <form:input path="fianceeHe.address.lastNumber" id="fianceeHe.address.lastNumber" cssClass="text medium" cssErrorClass="text medium error" maxlength="100"/>
					    </td>
					</tr>
					<tr>
						<td>
					        <appfuse:label styleClass="desc" key="print.fiancee.baptism-date"/>
					        <form:errors path="fianceeHe.remaining.marriage.baptismDate" cssClass="fieldError"/>
					        <form:input path="fianceeHe.remaining.marriage.baptismDate" id="fianceeHeBaptismDateIn" cssClass="text date" cssErrorClass="text medium error" maxlength="100"/>
					        <button id="fianceeHeBaptismDateInCal" type="button" class="calendarbutton"> ... </button>
					    </td>
						<td>
					   	    <appfuse:label styleClass="desc" key="print.fiancee.baptism-church"/>
					        <form:errors path="fianceeHe.remaining.marriage.baptismChurch" cssClass="fieldError"/>
					        <form:input path="fianceeHe.remaining.marriage.baptismChurch" id="fianceeHe.baptismChurch" cssClass="text medium" cssErrorClass="text medium error" maxlength="100"/>
					   	</td>
						<td>
					        <appfuse:label styleClass="desc" key="print.fiancee.baptism-church-in"/>
					        <form:errors path="fianceeHe.remaining.marriage.baptismChurchIn" cssClass="fieldError"/>
					        <form:input path="fianceeHe.remaining.marriage.baptismChurchIn" id="fianceeHe.baptismChurchIn" cssClass="text medium" cssErrorClass="text medium error" maxlength="50"/>
					   	</td>
						<td>
					        <appfuse:label styleClass="desc" key="print.fiancee.baptism-number"/>
					        <form:errors path="fianceeHe.remaining.marriage.baptismNumber" cssClass="fieldError"/>
					        <form:input path="fianceeHe.remaining.marriage.baptismNumber" id="fianceeHe.baptismNumber" cssClass="text medium" cssErrorClass="text medium error" maxlength="100"/>
					    </td>
					</tr>
					<tr>
						<td>
					   		<appfuse:label styleClass="desc" key="print.fiancee.church-address"/>
					        <form:errors path="fianceeHe.remaining.marriage.churchPostAddress" cssClass="fieldError"/>
					        <form:input path="fianceeHe.remaining.marriage.churchPostAddress" id="fianceeHe.churchPostAddress" cssClass="text medium" cssErrorClass="text medium error" maxlength="100"/>
					    </td>
					    <td>
							<appfuse:label styleClass="desc" key="print.fiancee.confirmation-date"/>
					        <form:errors path="fianceeHe.remaining.marriage.confirmationDate" cssClass="fieldError"/>
					        <form:input path="fianceeHe.remaining.marriage.confirmationDate" id="fianceeHeConfirmationDateIn" cssClass="text date" cssErrorClass="text medium error" maxlength="10"/>
					        <button id="fianceeHeConfirmationDateInCal" type="button" class="calendarbutton"> ... </button>
					    </td>
					    <td>
					   	    <appfuse:label styleClass="desc" key="print.fiancee.confirmation-church"/>
					        <form:errors path="fianceeHe.remaining.marriage.confirmationChurch" cssClass="fieldError"/>
					        <form:input path="fianceeHe.remaining.marriage.confirmationChurch" id="fianceeHe.confirmationChurch" cssClass="text medium" cssErrorClass="text medium error" maxlength="100"/>
					    </td>
			   		</tr>
			   	</table>
		    </fieldset>
		    
		    <fieldset style="padding-bottom: 20px; width: 800px; text-align: left;">
				<legend><fmt:message key="print.fianceeshe.fullname"/></legend>
				<table>
					<tr>
						<td style="width: 200px;">
					        <appfuse:label styleClass="desc" key="print.fiancee.firstname"/>
					        <form:errors path="fianceeShe.firstName" cssClass="fieldError"/>
					        <form:input path="fianceeShe.firstName" id="fianceeShe.firstName" cssClass="text medium" cssErrorClass="text medium error" maxlength="50"/>
					   	</td>
						<td style="width: 200px;">
					        <appfuse:label styleClass="desc" key="print.fiancee.lastname"/>
					        <form:errors path="fianceeShe.surname" cssClass="fieldError"/>
					        <form:input path="fianceeShe.surname" id="fianceeShe.surname" cssClass="text medium" cssErrorClass="text medium error" maxlength="50"/>
					   	</td>
						<td style="width: 200px;">
					        <appfuse:label styleClass="desc" key="print.fiancee.parish"/>
					        <form:errors path="fianceeShe.remaining.marriage.parish" cssClass="fieldError"/>
					        <form:input path="fianceeShe.remaining.marriage.parish" id="fianceeShe.parish" cssClass="text medium" cssErrorClass="text medium error" maxlength="100"/>
					   	</td>
						<td style="width: 200px;">
					        <appfuse:label styleClass="desc" key="print.fiancee.birth-date"/>
					        <form:errors path="fianceeShe.birthDate" cssClass="fieldError"/>
					        <form:input path="fianceeShe.birthDate" id="fianceeSheBirthDateIn" cssClass="text date" cssErrorClass="text medium error" maxlength="10"/>
					        <button id="fianceeSheBirthDateInCal" type="button" class="calendarbutton"> ... </button>
					    </td>
					    <td>
					        <appfuse:label styleClass="desc" key="print.fiancee.birth-place"/>
					        <form:errors path="fianceeShe.remaining.birthPlace" cssClass="fieldError"/>
					        <form:input path="fianceeShe.remaining.birthPlace" id="fianceeShe.birthPlace" cssClass="text medium" cssErrorClass="text medium error" maxlength="100"/>
						</td>
					</tr>
					<tr>
						<td>
					        <appfuse:label styleClass="desc" key="print.fiancee.fathers-first-name"/>
					        <form:errors path="fianceeShe.remaining.fathersFirstName" cssClass="fieldError"/>
					        <form:input path="fianceeShe.remaining.fathersFirstName" id="fianceeShe.fathersName" cssClass="text medium" cssErrorClass="text medium error" maxlength="100"/>
					   	</td>
					   	<td>
					        <appfuse:label styleClass="desc" key="print.fiancee.fathers-last-name"/>
					        <form:errors path="fianceeShe.remaining.fathersLastName" cssClass="fieldError"/>
					        <form:input path="fianceeShe.remaining.fathersLastName" id="fianceeShe.fathersName" cssClass="text medium" cssErrorClass="text medium error" maxlength="100"/>
					   	</td>
					    <td>
					        <appfuse:label styleClass="desc" key="print.fiancee.mothers-first-name"/>
					        <form:errors path="fianceeShe.remaining.mothersFirstName" cssClass="fieldError"/>
					        <form:input path="fianceeShe.remaining.mothersFirstName" id="fianceeShe.mothersName" cssClass="text medium" cssErrorClass="text medium error" maxlength="100"/>
					   	</td>
					   	<td>
					        <appfuse:label styleClass="desc" key="print.remaining.mothers-maidensname"/>
					        <form:errors path="fianceeShe.remaining.mothersMaidensName" cssClass="fieldError"/>
					        <form:input path="fianceeShe.remaining.mothersMaidensName" id="fianceeShe.mothersName" cssClass="text medium" cssErrorClass="text medium error" maxlength="100"/>
					   	</td>
					    <td>
					        <appfuse:label styleClass="desc" key="print.fiancee.religion"/>
					        <form:errors path="fianceeShe.remaining.marriage.religion" cssClass="fieldError"/>
					        <form:input path="fianceeShe.remaining.marriage.religion" id="fianceeShe.religion" cssClass="text medium" cssErrorClass="text medium error" maxlength="100"/>
					   	</td>
					</tr>
					<tr>
						<td>
					        <appfuse:label styleClass="desc" key="print.fiancee.address-place"/>
					        <form:errors path="fianceeShe.address.place" cssClass="fieldError"/>
					        <form:input path="fianceeShe.address.place" id="fianceeShe.address.place" cssClass="text medium" cssErrorClass="text medium error" maxlength="100" />
					   	</td>
					    <td>
					        <appfuse:label styleClass="desc" key="print.fiancee.address-postcode"/>
					        <form:errors path="fianceeShe.address.postCode" cssClass="fieldError"/>
					        <form:input path="fianceeShe.address.postCode" id="fianceeShe.address.postCode" cssClass="text medium" cssErrorClass="text medium error" maxlength="100"/>
					   	</td>
					    <td>
					        <appfuse:label styleClass="desc" key="print.fiancee.address-street"/>
					        <form:errors path="fianceeShe.address.street" cssClass="fieldError"/>
					        <form:input path="fianceeShe.address.street" id="fianceeShe.address.street" cssClass="text medium" cssErrorClass="text medium error" maxlength="100"/>
					   	</td>
					    <td>
					        <appfuse:label styleClass="desc" key="print.fiancee.address-first-number"/>
					        <form:errors path="fianceeShe.address.firstNumber" cssClass="fieldError"/>
					        <form:input path="fianceeShe.address.firstNumber" id="fianceeShe.address.firstNumber" cssClass="text medium" cssErrorClass="text medium error" maxlength="100"/>
					   	</td>
					   	<td>
					        <appfuse:label styleClass="desc" key="print.fiancee.address-last-number"/>
					        <form:errors path="fianceeShe.address.lastNumber" cssClass="fieldError"/>
					        <form:input path="fianceeShe.address.lastNumber" id="fianceeShe.address.lastNumber" cssClass="text medium" cssErrorClass="text medium error" maxlength="100"/>
					   	</td>
					</tr>
					<tr>
						<td>
					        <appfuse:label styleClass="desc" key="print.fiancee.baptism-date"/>
					        <form:errors path="fianceeShe.remaining.marriage.baptismDate" cssClass="fieldError"/>
					        <form:input path="fianceeShe.remaining.marriage.baptismDate" id="fianceeSheBaptismDateIn" cssClass="text date" cssErrorClass="text medium error" maxlength="100"/>
					        <button id="fianceeSheBaptismDateInCal" type="button" class="calendarbutton"> ... </button>
					   	</td>
					    <td>
					        <appfuse:label styleClass="desc" key="print.fiancee.baptism-church"/>
					        <form:errors path="fianceeShe.remaining.marriage.baptismChurch" cssClass="fieldError"/>
					        <form:input path="fianceeShe.remaining.marriage.baptismChurch" id="fianceeShe.baptismChurch" cssClass="text medium" cssErrorClass="text medium error" maxlength="100"/>
					   	</td>
					    <td>
					        <appfuse:label styleClass="desc" key="print.fiancee.baptism-church-in"/>
					        <form:errors path="fianceeShe.remaining.marriage.baptismChurchIn" cssClass="fieldError"/>
					        <form:input path="fianceeShe.remaining.marriage.baptismChurchIn" id="fianceeShe.baptismChurchIn" cssClass="text medium" cssErrorClass="text medium error" maxlength="50"/>
					   	</td>
					    <td>
					        <appfuse:label styleClass="desc" key="print.fiancee.baptism-number"/>
					        <form:errors path="fianceeShe.remaining.marriage.baptismNumber" cssClass="fieldError"/>
					        <form:input path="fianceeShe.remaining.marriage.baptismNumber" id="fianceeShe.baptismNumber" cssClass="text medium" cssErrorClass="text medium error" maxlength="100"/>
					    </td>
					</tr>
			   		<tr>
			   			<td>
					        <appfuse:label styleClass="desc" key="print.fiancee.church-address"/>
					        <form:errors path="fianceeShe.remaining.marriage.churchPostAddress" cssClass="fieldError"/>
					        <form:input path="fianceeShe.remaining.marriage.churchPostAddress" id="fianceeShe.churchPostAddress" cssClass="text medium" cssErrorClass="text medium error" maxlength="100"/>
					   	</td>
					    <td>
					        <appfuse:label styleClass="desc" key="print.fiancee.confirmation-date"/>
					        <form:errors path="fianceeShe.remaining.marriage.confirmationDate" cssClass="fieldError"/>
					        <form:input path="fianceeShe.remaining.marriage.confirmationDate" id="fianceeSheConfirmationDateIn" cssClass="text date" cssErrorClass="text medium error" maxlength="10"/>
					        <button id="fianceeSheConfirmationDateInCal" type="button" class="calendarbutton"> ... </button>
					   	</td>
					    <td>
					        <appfuse:label styleClass="desc" key="print.fiancee.confirmation-church"/>
					        <form:errors path="fianceeShe.remaining.marriage.confirmationChurch" cssClass="fieldError"/>
					        <form:input path="fianceeShe.remaining.marriage.confirmationChurch" id="fianceeShe.confirmationChurch" cssClass="text medium" cssErrorClass="text medium error" maxlength="100"/>
			        	</td>
			        </tr>
				</table>
		    </fieldset>
		    
		    <fieldset style="padding-bottom: 20px; width: 800px; text-align: left;">
				<legend><fmt:message key="print.fiancee.other-information"/></legend>
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
			        <div style="float: left; margin-left: 20px;">
						<appfuse:label styleClass="desc" key="print.remaining.marriage-time"/>
				        <form:errors path="marriageTime" cssClass="fieldError"/>
				        <form:input path="marriageTime" id="fianceeShe.confirmationChurch" cssClass="text medium" cssErrorClass="text medium error" maxlength="100"/>
					</div>
			</fieldset>
		</li>
	
	    <li class="buttonBar bottom">
	    	<div style="float: left; clear: both; margin-top: 20px; width: 500px;">
		        <input type="submit" name="submit" class="button" onclick="bCancel=false"
		            value="<fmt:message key="button.save"/>" />
		            
		        <c:url var="fianceesTomeUrlCancel" value="/tomes/fianceesTome.html" />
		        <input type="button" name="cancel" class="button" onclick="javascript:window.location='${fianceesTomeUrlCancel}'"
		            value="<fmt:message key="button.cancel"/>" />
				
				<input type="submit" name="delete" class="button"
    				value="<fmt:message key="button.delete"/>" 
    				onclick="javascript:document.getElementById('fianceePair').action = 'fianceesform/delete.html?fianceePairId=${fianceePair.id}'; return inputCode();"/>
    			
    			<%--	another way but OK button it's work, Cancel button it isn't work (security window)
    			<c:url var="fianceesTomeUrlDelete" value="fianceesform/delete.html">
		        	<c:param name="fianceePairId" value="${requestScope.fianceePair.id}"/>
				</c:url>
   
				<input type="submit" name="delete" class="button"
    				value="<fmt:message key="button.delete"/>" 
    				onclick="javascript:if (inputCode()) { document.getElementById('fianceePair').action='${fianceesTomeUrlDelete}'; return true }"/> 
    			--%>  			
    					        
		        <c:url var="investigationUrl" value="/investigation.html">
					<c:param name="fianceePairId" value="${requestScope.fianceePair.id}"/>
				</c:url>
				<appfuse:label styleClass="desc" key="print.protocole"/>
				<input type="button" class="button" name="investigation" value="<fmt:message key='button.open'/>"
					onclick="javascript:window.location='${investigationUrl}';"/>
				
				<c:url var="certificateUrl" value="/certificate.html">
					<c:param name="fianceePairId" value="${requestScope.fianceePair.id}"/>
				</c:url>
				<appfuse:label styleClass="desc" key="print.certificate"/>
				<input type="button" class="button" name="certificate" value="<fmt:message key='button.open'/>"
					onclick="javascript:window.location='${certificateUrl}';"/>
					
				<c:url var="dispensationUrl" value="/dispensation.html">
					<c:param name="fianceePairId" value="${requestScope.fianceePair.id}"/>
				</c:url>
				<appfuse:label styleClass="desc" key="print.dispensation"/>
				<input type="button" class="button" name="dispensation" value="<fmt:message key='button.open'/>"
					onclick="javascript:window.location='${dispensationUrl}';"/>
					
				<c:url var="delegationUrl" value="/delegation.html">
					<c:param name="fianceePairId" value="${requestScope.fianceePair.id}"/>
				</c:url>
				<appfuse:label styleClass="desc" key="print.delegation"/>
				<input type="button" class="button" name="delegation" value="<fmt:message key='button.open'/>"
					onclick="javascript:window.location='${delegationUrl}';"/>
					
				<c:url var="licenseAssistanceUrl" value="/licenseAssistance.html">
					<c:param name="fianceePairId" value="${requestScope.fianceePair.id}"/>
				</c:url>
				<appfuse:label styleClass="desc" key="print.license-assistance"/>
				<input type="button" class="button" name="licenseAssistance" value="<fmt:message key='button.open'/>"
					onclick="javascript:window.location='${licenseAssistanceUrl}';"/>
				
				<table>
					<tr>
						<td>
							<c:url var="printLicenceUrl" value="/printlicence.html">
								<c:param name="fianceePairId" value="${requestScope.fianceePair.id}"/>
								<c:param name="polish" value="true"/>
							</c:url>
							<appfuse:label styleClass="desc" key="print.licence.polish"/>
							<input type="button" class="button" name="licence" value="<fmt:message key='button.print'/>"
								onclick="javascript:window.open('${printLicenceUrl}','Licencja','height=300,width=500');"/>
						</td>
						<td>
							<c:url var="printLicenceUrl" value="/printlicence.html">
								<c:param name="fianceePairId" value="${requestScope.fianceePair.id}"/>
							</c:url>
							<appfuse:label styleClass="desc" key="print.licence"/>
							<input type="button" class="button" name="licence" value="<fmt:message key='button.print'/>"
								onclick="javascript:window.open('${printLicenceUrl}','Licencja','height=300,width=500');"/>
						</td>
					</tr>
				</table>
					
				<c:url var="editMarriageUrl" value="/editMarriage.html">
					<c:param name="fianceePairId" value="${requestScope.fianceePair.id}"/>
				</c:url>
				<appfuse:label styleClass="desc" key="print.marriage-certificate"/>
				<input type="button" name="editMarriage" class="button" value="<fmt:message key='button.open'/>"
					onclick="javascript:window.location='${editMarriageUrl}';"/>
			</div>
	    </li>
	</ul>
	</form:form>
</div>

<script language="javascript">
	Calendar.setup({
	    inputField  : "fianceeHeBirthDateIn",      // id of the input field
	    ifFormat    : "%d-%m-%Y",      // the date format
	    button      : "fianceeHeBirthDateInCal"    // id of the button
	});
	
	Calendar.setup({
	    inputField  : "fianceeHeBaptismDateIn",      // id of the input field
	    ifFormat    : "%d-%m-%Y",      // the date format
	    button      : "fianceeHeBaptismDateInCal"    // id of the button
	});
	
	Calendar.setup({
	    inputField  : "fianceeHeConfirmationDateIn",      // id of the input field
	    ifFormat    : "%d-%m-%Y",      // the date format
	    button      : "fianceeHeConfirmationDateInCal"    // id of the button
	});

	Calendar.setup({
	    inputField  : "fianceeSheBirthDateIn",      // id of the input field
	    ifFormat    : "%d-%m-%Y",      // the date format
	    button      : "fianceeSheBirthDateInCal"    // id of the button
	});

	Calendar.setup({
	    inputField  : "fianceeSheBaptismDateIn",      // id of the input field
	    ifFormat    : "%d-%m-%Y",      // the date format
	    button      : "fianceeSheBaptismDateInCal"    // id of the button
	});

	Calendar.setup({
	    inputField  : "fianceeSheConfirmationDateIn",      // id of the input field
	    ifFormat    : "%d-%m-%Y",      // the date format
	    button      : "fianceeSheConfirmationDateInCal"    // id of the button
	});

	Calendar.setup({
	    inputField  : "marriageDate",      // id of the input field
	    ifFormat    : "%d-%m-%Y",      // the date format
	    button      : "marriageDateCal"    // id of the button
	});
	
</script>

<v:javascript formName="fianceePair" staticJavascript="false"/>
<script type="text/javascript" src="<c:url value="/scripts/validator.jsp"/>"></script>
