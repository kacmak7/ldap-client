 <%@ include file="/common/taglibs.jsp"%>

<head>
    <title><fmt:message key="print.remaining.edit"/></title>
    <meta name="menu" content="PrintMenu"/>
    <script type="text/javascript" src="${ctx}/dwr/engine.js"></script>
    <script type="text/javascript" src="${ctx}/dwr/util.js"></script>
    
    <script type="text/javascript" src="${ctx}/scripts/calendar/calendar.js"></script>
    <script type="text/javascript" src="${ctx}/scripts/calendar/calendar-setup.js"></script>
    <script type="text/javascript" src="${ctx}/scripts/calendar/lang/calendar-pl-utf8.js"></script>
</head>

<p><spring:message code="print.remaining.edit"/></p>

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
	
	<appfuse:label styleClass="desc" key="print.family.choose-person"/>
	<c:url var="remainingUrl" value="/print/remaining.html">
		<c:param name="familyId" value="${requestScope.family.id}"/>
    </c:url>
	<select id="personChoice" onchange="javascript:location.href='${remainingUrl}&personId='+document.getElementById('personChoice').value">
		<c:forEach items="${requestScope.family.allPersons}" var="person">
			<option value="${person.id}" <c:if test="${requestScope.person.id==person.id}">selected="selected"</c:if>>${person.fullName}</option>
		</c:forEach>
	</select>
	
	<form:form commandName="person" method="post"
	    onsubmit="return validatePerson(this)" id="person">
		
		<ul>
			<fieldset>
				<li>
			    	<div style="float: left; clear: left;">
			    		<appfuse:label styleClass="desc" key="print.remaining.mothers-firstname"/>
				        <form:errors path="remaining.mothersFirstName" cssClass="fieldError"/>
				        <form:input path="remaining.mothersFirstName" id="mothersFirstName" cssClass="text medium" cssErrorClass="text medium error"/>
			        </div>
			        <div style="float: left; margin-left: 20px;">
			    		<appfuse:label styleClass="desc" key="print.remaining.mothers-lastname"/>
				        <form:errors path="remaining.mothersLastName" cssClass="fieldError"/>
				        <form:input path="remaining.mothersLastName" id="mothersLastName" cssClass="text medium" cssErrorClass="text medium error"/>
			        </div>
			        <div style="float: left; margin-left: 20px;">
			    		<appfuse:label styleClass="desc" key="print.remaining.mothers-maidensname"/>
				        <form:errors path="remaining.mothersMaidensName" cssClass="fieldError"/>
				        <form:input path="remaining.mothersMaidensName" id="mothersMaidensName" cssClass="text medium" cssErrorClass="text medium error"/>
			        </div>
				</li>
				
				<li>
			    	<div style="float: left; clear: left;">
			    		<appfuse:label styleClass="desc" key="print.remaining.fathers-firstname"/>
				        <form:errors path="remaining.fathersFirstName" cssClass="fieldError"/>
				        <form:input path="remaining.fathersFirstName" id="fathersFirstName" cssClass="text medium" cssErrorClass="text medium error"/>
			        </div>
			        <div style="float: left; margin-left: 20px;">
			    		<appfuse:label styleClass="desc" key="print.remaining.fathers-lastname"/>
				        <form:errors path="remaining.fathersLastName" cssClass="fieldError"/>
				        <form:input path="remaining.fathersLastName" id="fathersLastName" cssClass="text medium" cssErrorClass="text medium error"/>
			        </div>
			    </li>
			    <li>
			        <div style="float: left; clear: left;">
			    		<appfuse:label styleClass="desc" key="print.remaining.birth-place"/>
				        <form:errors path="remaining.birthPlace" cssClass="fieldError"/>
				        <form:input path="remaining.birthPlace" id="birthPlace" cssClass="text medium" cssErrorClass="text medium error"/>
			        </div>
				</li>
			</fieldset>
		
			<li class="buttonBar bottom">
		    	<div style="float: left; clear: both; margin-top: 20px;">
			        <input type="submit" name="submit" class="button" onclick="bCancel=false"
			            value="<fmt:message key="button.save"/>" />
			        
			        <c:url var="cardsUrl" value="/print/cards.html"/>
			        <input type="button" name="cancel" class="button" onclick="javascript:location.href='${cardsUrl}'"
			            value="<fmt:message key="button.cancel"/>" />
				</div>
			</li>
		</ul>	
	</form:form>
	
	<fieldset style="clear: left; margin-top: 70px;">
		<legend><fmt:message key="print.family.special"/></legend>
		<table>
			<tr>
				<td style="width: 25%;">
					<c:url var="printMoralityBaptismUrl" value="/printmoralitycertificate.html">
						<c:param name="familyId" value="${requestScope.family.id}"/>
						<c:param name="sort" value="baptism"/>
					</c:url>
					<appfuse:label styleClass="desc" key="print.morality-baptism"/>
					<input type="button" name="moralityBaptism" class="button" value="<fmt:message key='button.print'/>"
						onclick="javascript:window.open('${printMoralityBaptismUrl}&personId='+document.getElementById('personChoice').value,'Zaswiadczenie','height=300,width=500');"/>
				</td>
				<td style="width: 25%;">
					<c:url var="printMoralityConfirmationUrl" value="/printmoralitycertificate.html">
						<c:param name="familyId" value="${requestScope.family.id}"/>
						<c:param name="sort" value="confirmation"/>
					</c:url>
					<appfuse:label styleClass="desc" key="print.morality-confirmation"/>
					<input type="button" name="moralityConfirmation" class="button" value="<fmt:message key='button.print'/>"
						onclick="javascript:window.open('${printMoralityConfirmationUrl}&personId='+document.getElementById('personChoice').value,'Zaswiadczenie','height=300,width=500');"/>
				</td>
			</tr>
			
			<tr>
				<td style="width: 25%;">
					<c:url var="editBaptismUrl" value="/editBaptism.html">
						<c:param name="familyId" value="${requestScope.family.id}"/>
					</c:url>
					<appfuse:label styleClass="desc" key="print.baptism-certificate"/>
					<input type="button" name="baptismEdit" class="button" value="<fmt:message key='button.print'/>"
						onclick="javascript:window.location='${editBaptismUrl}&personId='+document.getElementById('personChoice').value;"/>
				</td>
				<td style="width: 25%;">
					<c:url var="printBaptismChangesUrl" value="/print/baptismchangesEdit.html">
						<c:param name="familyId" value="${requestScope.family.id}"/>
					</c:url>
					<appfuse:label styleClass="desc" key="print.baptism-changes"/>
					<input type="button" name="BaptismChanges" class="button" value="<fmt:message key='button.print'/>"
						onclick="javascript:window.location='${printBaptismChangesUrl}&personId='+document.getElementById('personChoice').value;"/>
				</td>
				<td style="width: 25%;">
					<c:url var="printBaptismKnowActUrl" value="/baptismKnowActEdit.html">
						<c:param name="familyId" value="${requestScope.family.id}"/>
					</c:url>
					<appfuse:label styleClass="desc" key="print.baptism-act-know"/>
					<input type="button" name="BaptismKnowAct" class="button" value="<fmt:message key='button.print'/>"
						onclick="javascript:window.location='${printBaptismKnowActUrl}&personId='+document.getElementById('personChoice').value;"/>
				</td>
				<td style="width: 25%;">
					<c:url var="baptismPreparationUrl" value="/baptismPreparationEdit.html">
						<c:param name="familyId" value="${requestScope.family.id}"/>
					</c:url>
					<appfuse:label styleClass="desc" key="print.baptism-preparation"/>
					<input type="button" name="BaptismPreparation" class="button" value="<fmt:message key='button.print'/>"
						onclick="javascript:window.location='${baptismPreparationUrl}&personId='+document.getElementById('personChoice').value;"/>
				</td>
			</tr>
			
			<tr>
				<td>
					<c:url var="editConfirmationUrl" value="/editConfirmation.html">
						<c:param name="familyId" value="${requestScope.family.id}"/>
					</c:url>
					<appfuse:label styleClass="desc" key="print.confirmation-certificate"/>
					<input type="button" name="confirmationCertificate" class="button" value="<fmt:message key='button.print'/>"
						onclick="javascript:window.location='${editConfirmationUrl}&personId='+document.getElementById('personChoice').value;"/>
				</td>
			</tr>
			<tr>
				<td>
					<c:url var="editDeathUrl" value="/editDeath.html">
						<c:param name="familyId" value="${requestScope.family.id}"/>
					</c:url>
					<appfuse:label styleClass="desc" key="print.death-certificate"/>
					<input type="button" name="editDeath" class="button" value="<fmt:message key='button.print'/>"
						onclick="javascript:window.location='${editDeathUrl}&personId='+document.getElementById('personChoice').value;"/>
				</td>
			</tr>
		</table>
	</fieldset>
</div>

<script type="text/javascript">
    Form.focusFirstElement($('person'));
    highlightFormElements();
</script>
<v:javascript formName="person" staticJavascript="false"/>
<script type="text/javascript" src="<c:url value="/scripts/validator.jsp"/>"></script>