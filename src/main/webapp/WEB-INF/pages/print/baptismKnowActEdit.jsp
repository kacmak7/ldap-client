 <%@ include file="/common/taglibs.jsp"%>

<head>
    <title><fmt:message key="print.baptism-act-know"/></title>
    <meta name="menu" content="PrintMenu"/>
    <script type="text/javascript" src="${ctx}/dwr/engine.js"></script>
    <script type="text/javascript" src="${ctx}/dwr/util.js"></script>
    
    <script type="text/javascript" src="${ctx}/scripts/calendar/calendar.js"></script>
    <script type="text/javascript" src="${ctx}/scripts/calendar/calendar-setup.js"></script>
    <script type="text/javascript" src="${ctx}/scripts/calendar/lang/calendar-pl-utf8.js"></script>
</head>

<p><spring:message code="print.baptism-act-know"/></p>

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
	
	<form:form commandName="person" method="post" action="baptismKnowActEdit.html"
	 onsubmit="return validatePerson(this)" id="person">
	 
	 
	 		<input type="hidden" name="personId" value="${person.id}"/>
	 		<form:hidden path="remaining.baptismKnowAct.id"/>
	 
	    <table>
			<tr>
				<td>
					<appfuse:label styleClass="desc" key="print.baptism-act-know.witness-firstname"/>
			        <form:errors path="remaining.baptismKnowAct.witness.firstName" cssClass="fieldError"/>
			        <form:input path="remaining.baptismKnowAct.witness.firstName" id="witnessFirstName" cssClass="text wide" cssErrorClass="text wide error"/>
			    </td>
		    	<td>
					<appfuse:label styleClass="desc" key="print.baptism-act-know.witness-surname"/>
			        <form:errors path="remaining.baptismKnowAct.witness.surname" cssClass="fieldError"/>
			        <form:input path="remaining.baptismKnowAct.witness.surname" id="witnessSurname" cssClass="text wide" cssErrorClass="text wide error"/>
			    </td>
			    <td>
					<appfuse:label styleClass="desc" key="print.baptism-act-know.witness-address"/>
			        <form:errors path="remaining.baptismKnowAct.witness.address" cssClass="fieldError"/>
			        <form:input path="remaining.baptismKnowAct.witness.address" id="witnessAddress" cssClass="text wide" cssErrorClass="text wide error"/>
			    </td>
			</tr>
			<tr>
				<td>
					<appfuse:label styleClass="desc" key="print.baptism-act-know.witness-mothers-first-name"/>
			        <form:errors path="remaining.baptismKnowAct.witness.mothersFirstName" cssClass="fieldError"/>
			        <form:input path="remaining.baptismKnowAct.witness.mothersFirstName" id="witnessMothersFirstName" cssClass="text wide" cssErrorClass="text wide error"/>
			    </td>
				<td>
					<appfuse:label styleClass="desc" key="print.baptism-act-know.witness-mothers-surname"/>
			        <form:errors path="remaining.baptismKnowAct.witness.mothersLastName" cssClass="fieldError"/>
			        <form:input path="remaining.baptismKnowAct.witness.mothersLastName" id="witnessMothersLastName" cssClass="text wide" cssErrorClass="text wide error"/>
			    </td>
				<td>
					<appfuse:label styleClass="desc" key="print.baptism-act-know.witness-fathers-first-name"/>
			        <form:errors path="remaining.baptismKnowAct.witness.fathersFirstName" cssClass="fieldError"/>
			        <form:input path="remaining.baptismKnowAct.witness.fathersFirstName" id="witnessFathersFirstName" cssClass="text wide" cssErrorClass="text wide error"/>
			    </td>
				<td>
					<appfuse:label styleClass="desc" key="print.baptism-act-know.witness-fathers-surname"/>
			        <form:errors path="remaining.baptismKnowAct.witness.fathersLastName" cssClass="fieldError"/>
			        <form:input path="remaining.baptismKnowAct.witness.fathersLastName" id="witnessFathersLastName" cssClass="text wide" cssErrorClass="text wide error"/>
				</td>
			</tr>
			<tr>
				<td>
			    	<appfuse:label styleClass="desc" key="print.baptism-act-know.witness-birth-date"/>
			        <form:errors path="remaining.baptismKnowAct.witness.birthDate" cssClass="fieldError"/>
			        <form:input path="remaining.baptismKnowAct.witness.birthDate" id="witnessBirthDate" cssClass="text date" cssErrorClass="text date error"/>
			        <button id="witnessBirthDateCal" type="button" class="calendarbutton"> ... </button>
			    </td>
			    <td>
				    <appfuse:label styleClass="desc" key="print.baptism-act-know.witness-job"/>
			        <form:errors path="remaining.baptismKnowAct.witness.job" cssClass="fieldError"/>
			        <form:input path="remaining.baptismKnowAct.witness.job" id="witnessJob" cssClass="text wide" cssErrorClass="text wide error"/>
			    </td>
				<td>
					<appfuse:label styleClass="desc" key="print.baptism-act-know.witness-religion"/>
			        <form:errors path="remaining.baptismKnowAct.witness.religion" cssClass="fieldError"/>
			        <form:input path="remaining.baptismKnowAct.witness.religion" id="witnessReligion" cssClass="text wide" cssErrorClass="text wide error"/>
			    </td>
				<td>
					<appfuse:label styleClass="desc" key="print.baptism-act-know.witness-practising"/>
			        <form:errors path="remaining.baptismKnowAct.witness.practising" cssClass="fieldError"/>
			        <form:checkbox path="remaining.baptismKnowAct.witness.practising" id="witnessPractising"
			        	value="1" cssClass="text wide" cssErrorClass="text wide error"/>
			    </td>
			</tr>
			<tr>
				<td>
					<appfuse:label styleClass="desc" key="print.baptism-act-know.date"/>
			        <form:errors path="remaining.baptismKnowAct.date" cssClass="fieldError"/>
			        <form:input path="remaining.baptismKnowAct.date" id="baptismKnowActDate" cssClass="text date" cssErrorClass="text date error"/>
			        <button id="baptismKnowActDateCal" type="button" class="calendarbutton"> ... </button>
				</td>
				<td>
					<appfuse:label styleClass="desc" key="print.baptism-act-know.priest-first-name"/>
			        <form:errors path="remaining.baptismKnowAct.priestFirstName" cssClass="fieldError"/>
			        <form:input path="remaining.baptismKnowAct.priestFirstName" id="priestFirstName" cssClass="text wide" cssErrorClass="text wide error"/>
				</td>
				<td>
					<appfuse:label styleClass="desc" key="print.baptism-act-know.priest-last-name"/>
			        <form:errors path="remaining.baptismKnowAct.priestSurname" cssClass="fieldError"/>
			        <form:input path="remaining.baptismKnowAct.priestSurname" id="priestSurname" cssClass="text wide" cssErrorClass="text wide error"/>
				</td>
			</tr>
			<tr style="vertical-align: bottom;">
				<td>
					<appfuse:label styleClass="desc" key="print.baptism-act-know.acquaintance-from"/>
			        <form:errors path="remaining.baptismKnowAct.acquaintanceFrom" cssClass="fieldError"/>
			        <form:input path="remaining.baptismKnowAct.acquaintanceFrom" id="acquaintanceFrom" cssClass="text wide" cssErrorClass="text wide error"/>
				</td>
				<td>
					<appfuse:label styleClass="desc" key="print.baptism-act-know.relationship"/>
			        <form:errors path="remaining.baptismKnowAct.relationship" cssClass="fieldError"/>
			        <form:input path="remaining.baptismKnowAct.relationship" id="relationship" cssClass="text wide" cssErrorClass="text wide error"/>
				</td>
				<td>
					<appfuse:label styleClass="desc" key="print.baptism-act-know.baptism-place"/>
			        <form:errors path="remaining.baptismKnowAct.baptismPlace" cssClass="fieldError"/>
			        <form:select path="remaining.baptismKnowAct.baptismPlace" id="baptismPlace" cssClass="text wide">
			        	<form:options items="${requestScope.baptismPlaces}" itemLabel="name" itemValue="id"/>
			        </form:select>
				</td>
				<td>
					<appfuse:label styleClass="desc" key="print.baptism-act-know.baptism-place"/>
			        <form:errors path="remaining.baptismKnowAct.baptismPlaceAdditional" cssClass="fieldError"/>
			        <form:input path="remaining.baptismKnowAct.baptismPlaceAdditional" id="baptismPlaceAdditional" cssClass="text wide" cssErrorClass="text wide error"/>
				</td>
			</tr>
			<tr>
				<td>
					<appfuse:label styleClass="desc" key="print.baptism-act-know.baptism-number"/>
			        <form:errors path="remaining.baptism.fileNumber" cssClass="fieldError"/>
			        <form:input path="remaining.baptism.fileNumber" id="fileNumber" cssClass="text wide" cssErrorClass="text wide error"/>
				</td>
				<td>
		    		<appfuse:label styleClass="desc" key="print.baptism-act-know.baptism-date"/>
			        <form:errors path="remaining.baptism.date" cssClass="fieldError"/>
			        <form:input path="remaining.baptism.date" id="baptismDate" cssClass="text date" cssErrorClass="text date error"/>
			        <button id="baptismDateCal" type="button" class="calendarbutton"> ... </button>
		        </td>
				<td>
					<appfuse:label styleClass="desc" key="print.baptism-act-know.baptism-giver"/>
			        <form:errors path="remaining.baptismKnowAct.baptismGiver" cssClass="fieldError"/>
			        <form:input path="remaining.baptismKnowAct.baptismGiver" id="baptismGiver" cssClass="text wide" cssErrorClass="text wide error"/>
				</td>
			</tr>
			<tr style="vertical-align: bottom;">
				<td>
					<appfuse:label styleClass="desc" key="print.baptism-act-know.godfather"/>
			        <form:errors path="remaining.baptismKnowAct.godfather" cssClass="fieldError"/>
			        <form:input path="remaining.baptismKnowAct.godfather" id="godfather" cssClass="text wide" cssErrorClass="text wide error"/>
				</td>
				<td>
					<appfuse:label styleClass="desc" key="print.baptism-act-know.godmother"/>
			        <form:errors path="remaining.baptismKnowAct.godmother" cssClass="fieldError"/>
			        <form:input path="remaining.baptismKnowAct.godmother" id="godmother" cssClass="text wide" cssErrorClass="text wide error"/>
				</td>
				<td>
					<appfuse:label styleClass="desc" key="print.baptism-act-know.baptism-know"/>
			        <form:errors path="remaining.baptismKnowAct.baptismKnow" cssClass="fieldError"/>
			        <form:input path="remaining.baptismKnowAct.baptismKnow" id="baptismKnow" cssClass="text wide" cssErrorClass="text wide error"/>
				</td>
				<td>
					<appfuse:label styleClass="desc" key="print.baptism-act-know.reason-not-act"/>
			        <form:errors path="remaining.baptismKnowAct.reasonNotAct" cssClass="fieldError"/>
			        <form:input path="remaining.baptismKnowAct.reasonNotAct" id="reasonNotAct" cssClass="text wide" cssErrorClass="text wide error"/>
				</td>
			</tr>
			<tr>
				<td>
					<appfuse:label styleClass="desc" key="print.baptism-act-know.additionals"/>
			        <form:errors path="remaining.baptismKnowAct.additional" cssClass="fieldError"/>
			        <form:input path="remaining.baptismKnowAct.additional" id="additional" cssClass="text wide" cssErrorClass="text wide error"/>
				</td>
			</tr>
		</table>
			
    	<div style="float: left; clear: both; margin-top: 20px;">
	        <input type="submit" name="submit" class="button" onclick="bCancel=false"
	            value="<fmt:message key="button.save"/>" />
	        
	        <c:url var="baptismChangesUrl" value="/printbaptismknowact.html">
				<c:param name="familyId" value="${requestScope.family.id}"/>
				<c:param name="personId" value="${requestScope.person.id}"/>
			</c:url>
			<input type="button" class="button" name="baptismChanges" value="<fmt:message key='button.print'/>"
				onclick="javascript:window.location='${baptismChangesUrl}';"/>
	        
	        <input type="button" name="cancel" class="button" onclick="window.location='print/personform.html?personId=${person.id}'" value="<fmt:message key="button.cancel"/>" />
		</div>
	</form:form>
</div>

<script type="text/javascript">

    Calendar.setup({
	    inputField  : "baptismKnowActDate",      // id of the input field
	    ifFormat    : "%d-%m-%Y",      // the date format
	    button      : "baptismKnowActDateCal"    // id of the button
	});

    Calendar.setup({
	    inputField  : "witnessBirthDate",      // id of the input field
	    ifFormat    : "%d-%m-%Y",      // the date format
	    button      : "witnessBirthDateCal"    // id of the button
	});

    Calendar.setup({
	    inputField  : "baptismDate",      // id of the input field
	    ifFormat    : "%d-%m-%Y",      // the date format
	    button      : "baptismDateCal"    // id of the button
	});
</script>
<v:javascript formName="person" staticJavascript="false"/>
<script type="text/javascript" src="<c:url value="/scripts/validator.jsp"/>"></script>