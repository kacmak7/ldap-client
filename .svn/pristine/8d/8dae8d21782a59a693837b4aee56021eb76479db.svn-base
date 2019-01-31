<%@ include file="/common/taglibs.jsp"%>

<head>
    <title><fmt:message key="print.person.edit"/></title>
    <meta name="menu" content="PrintMenu"/>
    <script type="text/javascript" src="${ctx}/dwr/engine.js"></script>
    <script type="text/javascript" src="${ctx}/dwr/util.js"></script>
    
    <script type="text/javascript" src="${ctx}/scripts/calendar/calendar.js"></script>
    <script type="text/javascript" src="${ctx}/scripts/calendar/calendar-setup.js"></script>
    <script type="text/javascript" src="${ctx}/scripts/calendar/lang/calendar-pl-utf8.js"></script>
    
    <script type="text/javascript" src="${ctx}/scripts/sha1-min.js"></script>
</head>

<p><spring:message code="print.person.edit"/></p>

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
	
	<form:form commandName="person" method="post"
	    onsubmit="return validatePerson(this)" id="person">
		<form:hidden path="id"/>
		<table>
			<tr>
				<td>
		    		<appfuse:label styleClass="desc" key="print.family.first-name"/>
			        <form:errors path="firstName" cssClass="fieldError" cssStyle="width: 130px;"/>
			        <form:input path="firstName" cssClass="text medium" cssErrorClass="text medium error" cssStyle="width: 130px;"/>
				</td>
				<td>
		    		<appfuse:label styleClass="desc" key="print.family.last-name"/>
		    		<c:choose>
		    			<c:when test="${requestScope.person.withoutFamily == true}">
		    				<form:errors path="surname" cssClass="fieldError" cssStyle="width: 130px;"/>
			        		<form:input path="surname" cssClass="text medium" cssErrorClass="text medium error" cssStyle="width: 130px;"/>
		    			</c:when>
			        	<c:otherwise>
			        		<form:errors path="surname" cssClass="fieldError" cssStyle="width: 130px;"/>
			        		<form:input path="surname" cssClass="text medium" cssErrorClass="text medium error" cssStyle="width: 130px;"/>
			        	</c:otherwise>
			        </c:choose>
				</td>
				<td>
		    		<appfuse:label styleClass="desc" key="print.family.birthdate"/>
			        <form:errors path="birthDate" cssClass="fieldError"/>
			        <form:input path="birthDate" id="birthDateIn" cssClass="text" cssErrorClass="text error" maxlength="10" cssStyle="width:75px;"/>
			        <button id="birthDateInCal" type="button" class="calendarbutton"> ... </button>
				</td>
				<td>
		       		<appfuse:label styleClass="desc" key="print.remaining.birth-place"/>
			        <form:errors path="remaining.birthPlace" cssClass="fieldError"/>
			        <form:input path="remaining.birthPlace" id="birthPlace" cssClass="text medium" cssErrorClass="text medium error"/>
			    </td>
			</tr>
			
			<c:if test="${requestScope.person.withoutFamily == true}">
				<tr>
		        	<td>
			    		<appfuse:label styleClass="desc" key="print.family.street"/>
				        <form:errors path="address.street" cssClass="fieldError"/>
				        <form:input path="address.street" id="street" cssClass="text medium" cssErrorClass="text medium error" maxlength="50"/>
				    </td>
		        	<td>
			    		<appfuse:label styleClass="desc" key="print.family.first-number"/>
				        <form:errors path="address.firstNumber" cssClass="fieldError"/>
				        <form:input path="address.firstNumber" id="firstNumber" cssClass="text small" cssErrorClass="text small error" maxlength="50"/>
				    </td>
		        	<td>
			    		<appfuse:label styleClass="desc" key="print.family.last-number"/>
				        <form:errors path="address.lastNumber" cssClass="fieldError"/>
				        <form:input path="address.lastNumber" id="lastNumber" cssClass="text small" cssErrorClass="text small error" maxlength="50"/>
				    </td>
				    <td>
			    		<appfuse:label styleClass="desc" key="print.family.post-code"/>
				        <form:errors path="address.postCode" cssClass="fieldError"/>
				        <form:input path="address.postCode" id="postCode" cssClass="text small" cssErrorClass="text small error" maxlength="50"/>
				    </td>
				    <td>
			    		<appfuse:label styleClass="desc" key="print.family.place"/>
				        <form:errors path="address.place" cssClass="fieldError"/>
				        <form:input path="address.place" id="lastNumber" cssClass="text medium" cssErrorClass="text medium error" maxlength="50"/>
				    </td>
				</tr>
			</c:if>
			
			<tr>
				<td>
		    		<appfuse:label styleClass="desc" key="print.remaining.mothers-firstname"/>
			        <form:errors path="remaining.mothersFirstName" cssClass="fieldError"/>
			        <form:input path="remaining.mothersFirstName" id="mothersFirstName" cssClass="text medium" cssErrorClass="text medium error"/>
				</td>
			    <td>
		    		<appfuse:label styleClass="desc" key="print.remaining.mothers-lastname"/>
			        <form:errors path="remaining.mothersLastName" cssClass="fieldError"/>
			        <form:input path="remaining.mothersLastName" id="mothersLastName" cssClass="text medium" cssErrorClass="text medium error"/>
			    </td>
			    <td colspan="2">
		    		<appfuse:label styleClass="desc" key="print.remaining.mothers-maidensname"/>
			        <form:errors path="remaining.mothersMaidensName" cssClass="fieldError"/>
			        <form:input path="remaining.mothersMaidensName" id="mothersMaidensName" cssClass="text medium" cssErrorClass="text medium error"/>
			    </td>
			</tr>
			<tr>
				<td>
		    		<appfuse:label styleClass="desc" key="print.remaining.fathers-firstname"/>
			        <form:errors path="remaining.fathersFirstName" cssClass="fieldError"/>
			        <form:input path="remaining.fathersFirstName" id="fathersFirstName" cssClass="text medium" cssErrorClass="text medium error"/>
			    </td>
			    <td>
		    		<appfuse:label styleClass="desc" key="print.remaining.fathers-lastname"/>
			        <form:errors path="remaining.fathersLastName" cssClass="fieldError"/>
			        <form:input path="remaining.fathersLastName" id="fathersLastName" cssClass="text medium" cssErrorClass="text medium error"/>
			    </td>
			    <%--<td>
		    		<appfuse:label styleClass="desc" key="print.sex"/>
			        <form:errors path="woman" cssClass="fieldError"/>
			        <form:select path="woman" id="woman" cssClass="text medium" cssErrorClass="text medium error">
			        	<form:option value="true"><fmt:message key="global.woman"/></form:option>
			        	<form:option value="false"><fmt:message key="global.man"/></form:option>
			        </form:select>
			    </td> --%>
			</tr>
		</table>

    	<div style="float: left; clear: both; margin-top: 20px;">
	        <input type="submit" name="submit" class="button" onclick="bCancel=false"
	            value="<fmt:message key="button.save"/>" />
	        
	        <c:if test="${not empty requestScope.person.id}">
		        <input type="submit" name="delete" class="button" value="<fmt:message key="button.delete"/>" onclick="javascript:return inputCode();"/>
	        </c:if>
	        
	        <c:if test="${requestScope.person.withoutFamily == false}">
		        <c:url var="familyUrl" value="/print/familyform.html">
		        	<c:param name="id" value="${requestScope.person.family.id}"/>
		        </c:url>
		        <input type="button" name="cancel" class="button" onclick="javascript:location.href='${familyUrl}'"
		            value="<fmt:message key="button.family"/>" />
	        </c:if>
	        
	        <c:if test="${not empty requestScope.person.id}">
	        	<c:choose>
	        		<c:when test="${empty requestScope.fianceePairId}">		<!-- person1Id to mezczyzna -->
		        		<c:url var="fianceeHeUrl" value="/print/persons.html">
			        		<c:param name="person1Id" value="${requestScope.person.id}"/>
			        	</c:url>
			        	<c:url var="fianceeSheUrl" value="/print/persons.html">
			        		<c:param name="person2Id" value="${requestScope.person.id}"/>
			        	</c:url>
			        	<input type="button" name="fiancee" class="button" onclick="javascript:location.href='${fianceeSheUrl}'"
	            			value="<fmt:message key="button.fiancee-find-he"/>" style="width: 155px;" />
	            		<input type="button" name="fiancee" class="button" onclick="javascript:location.href='${fianceeHeUrl}'"
	            			value="<fmt:message key="button.fiancee-find-she"/>" style="width: 155px;" />
	        		</c:when>
	        		<c:otherwise>
	        			<c:url var="fianceeUrl" value="/print/fianceesform.html">
			        		<c:param name="fianceePairId" value="${requestScope.fianceePairId}"/>
			        	</c:url>
			        	<input type="button" name="fiancee" class="button" onclick="javascript:location.href='${fianceeUrl}'"
	            			value="<fmt:message key="button.fiancee"/>" style="width: 120px;" />
	        		</c:otherwise>
	        	</c:choose>
	        </c:if>
	            
	        <c:url var="personsUrl" value="/print/persons.html"/>
	        <input type="button" name="cancel" class="button" onclick="javascript:location.href='${personsUrl}'"
	            value="<fmt:message key="button.cancel"/>" />
		</div>
		
		<c:if test="${not empty requestScope.person.id}">
			<fieldset style="clear: left; margin-top: 70px;">
				<legend><fmt:message key="print.family.special"/></legend>
				<table>
					<tr>
						<td style="width: 25%;">
							<c:url var="printMoralityBaptismUrl" value="/printmoralitycertificate.html">
								<c:param name="sort" value="baptism"/>
								<c:param name="personId" value="${requestScope.person.id}"/>
							</c:url>
							<appfuse:label styleClass="desc" key="print.morality-baptism"/>
							<input type="button" name="moralityBaptism" class="button" value="<fmt:message key='button.print'/>"
								onclick="javascript:window.open('${printMoralityBaptismUrl}','Zaswiadczenie','height=300,width=500');"/>
						</td>
						<td style="width: 25%;">
							<c:url var="printMoralityConfirmationUrl" value="/printmoralitycertificate.html">
								<c:param name="sort" value="confirmation"/>
								<c:param name="personId" value="${requestScope.person.id}"/>
							</c:url>
							<appfuse:label styleClass="desc" key="print.morality-confirmation"/>
							<input type="button" name="moralityConfirmation" class="button" value="<fmt:message key='button.print'/>"
								onclick="javascript:window.open('${printMoralityConfirmationUrl}','Zaswiadczenie','height=300,width=500');"/>
						</td>
					</tr>
					
					<tr>
						<td style="width: 25%;">
							<c:url var="editBaptismUrl" value="/editBaptism.html">
								<c:param name="personId" value="${requestScope.person.id}"/>
							</c:url>
							<appfuse:label styleClass="desc" key="print.baptism-certificate"/>
							<input type="button" name="baptismEdit" class="button" value="<fmt:message key='button.open'/>"
								onclick="javascript:window.location='${editBaptismUrl}';"/>
						</td>
						<td style="width: 25%;">
							<c:url var="printBaptismChangesUrl" value="/printbaptismchanges.html">
								<c:param name="personId" value="${requestScope.person.id}"/>
							</c:url>
							<appfuse:label styleClass="desc" key="print.baptism-changes"/>
							<input type="button" name="BaptismChanges" class="button" value="<fmt:message key='button.open'/>"
								onclick="javascript:window.location='${printBaptismChangesUrl}';"/>
						</td>
						<td style="width: 25%;">
							<c:url var="printBaptismKnowActUrl" value="/baptismKnowActEdit.html">
								<c:param name="personId" value="${requestScope.person.id}"/>
							</c:url>
							<appfuse:label styleClass="desc" key="print.baptism-act-know"/>
							<input type="button" name="BaptismKnowAct" class="button" value="<fmt:message key='button.open'/>"
								onclick="javascript:window.location='${printBaptismKnowActUrl}';"/>
						</td>
						<td style="width: 25%;">
							<c:url var="baptismPreparationUrl" value="/baptismPreparationEdit.html">
								<c:param name="personId" value="${requestScope.person.id}"/>
							</c:url>
							<appfuse:label styleClass="desc" key="print.baptism-preparation"/>
							<input type="button" name="BaptismPreparation" class="button" value="<fmt:message key='button.open'/>"
								onclick="javascript:window.location='${baptismPreparationUrl}';"/>
						</td>
					</tr>
					
					<tr>
						<td>
							<c:url var="editConfirmationUrl" value="/editConfirmation.html">
								<c:param name="personId" value="${requestScope.person.id}"/>
							</c:url>
							<appfuse:label styleClass="desc" key="print.confirmation-certificate"/>
							<input type="button" name="confirmationCertificate" class="button" value="<fmt:message key='button.open'/>"
								onclick="javascript:window.location='${editConfirmationUrl}';"/>
						</td>
					</tr>
					<tr>
						<td>
							<c:url var="editDeathUrl" value="/editDeath.html">
								<c:param name="personId" value="${requestScope.person.id}"/>
							</c:url>
							<appfuse:label styleClass="desc" key="print.death-certificate"/>
							<input type="button" name="editDeath" class="button" value="<fmt:message key='button.open'/>"
								onclick="javascript:window.location='${editDeathUrl}';"/>
						</td>
					</tr>
				</table>
			</fieldset>
		</c:if>
	</form:form>
</div>

<script type="text/javascript">
	Calendar.setup({
	    inputField  : "birthDateIn",      // id of the input field
	    ifFormat    : "%d-%m-%Y",      // the date format
	    button      : "birthDateInCal"    // id of the button
	});

    Form.focusFirstElement($('person'));
    highlightFormElements();
</script>
<v:javascript formName="person" staticJavascript="false"/>
<script type="text/javascript" src="<c:url value="/scripts/validator.jsp"/>"></script>