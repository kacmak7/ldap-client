<%@ include file="/common/taglibs.jsp"%>

<head>
    <title><fmt:message key="print.family.edit"/></title>
    <meta name="menu" content="PrintMenu"/>
    <script type="text/javascript" src="${ctx}/dwr/engine.js"></script>
    <script type="text/javascript" src="${ctx}/dwr/util.js"></script>
    
    <script type="text/javascript" src="${ctx}/scripts/calendar/calendar.js"></script>
    <script type="text/javascript" src="${ctx}/scripts/calendar/calendar-setup.js"></script>
    <script type="text/javascript" src="${ctx}/scripts/calendar/lang/calendar-pl-utf8.js"></script>
</head>

<p><spring:message code="print.family.edit"/></p>

<div>
	<spring:bind path="family.*">
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
	
	<form:form commandName="family" method="post" onsubmit="return onSubmit(this);" id="family">
	
			<form:hidden path="address.id"/>
			
	<ul>
	    <li>
	    	<div style="float: left;">
		        <appfuse:label styleClass="desc" key="print.family.surname"/>
		        <form:errors path="surname" cssClass="fieldError"/>
		        <form:input path="surname" id="surname" cssClass="text medium" cssErrorClass="text medium error" maxlength="50"/>
		   	</div>
		   	<div style="float: left; margin-left: 20px;">
		   		<appfuse:label styleClass="desc" key="print.family.phone"/>
		        <form:errors path="phone" cssClass="fieldError"/>
		        <form:input path="phone" id="phone" cssClass="text medium" cssErrorClass="text medium error" maxlength="50"/>
		   	</div>
		   	<div style="float: left; margin-left: 20px;">
		   		<appfuse:label styleClass="desc" key="print.family.marriage-status"/>
		        <form:errors path="marriageStatus" cssClass="fieldError"/>
		        <form:select path="marriageStatus" id="marriageStatus" cssClass="text medium" cssErrorClass="text medium error">
		        	<form:options items="${requestScope.marriageStatusesList}" itemLabel="name" itemValue="id"/>
		        </form:select>
		   	</div>
	    </li>
	    <li>
	        <div style="float: left; clear: left;">
	    		<appfuse:label styleClass="desc" key="print.family.post-code"/>
		        <form:errors path="address.postCode" cssClass="fieldError"/>
		        <form:input path="address.postCode" id="postCode" cssClass="text medium" cssErrorClass="text medium error" maxlength="50"/>
	        </div>
	        <div style="float: left; margin-left: 20px;">
	    		<appfuse:label styleClass="desc" key="print.family.street"/>
		        <form:errors path="address.street" cssClass="fieldError"/>
		        <form:input path="address.street" id="street" cssClass="text medium" cssErrorClass="text medium error" maxlength="50"/>
	        </div>
	        <div style="float: left; margin-left: 20px;">
	    		<appfuse:label styleClass="desc" key="print.family.first-number"/>
		        <form:errors path="address.firstNumber" cssClass="fieldError"/>
		        <form:input path="address.firstNumber" id="firstNumber" cssClass="text medium" cssErrorClass="text medium error" maxlength="50"/>
	        </div>
	        <div style="float: left; margin-left: 20px;">
	    		<appfuse:label styleClass="desc" key="print.family.last-number"/>
		        <form:errors path="address.lastNumber" cssClass="fieldError"/>
		        <form:input path="address.lastNumber" id="lastNumber" cssClass="text medium" cssErrorClass="text medium error" maxlength="50"/>
	        </div>
	    </li>
	    
		<li style="clear: both;">
	    	<fieldset class="xlarge" style="width: 830px;">
	            <legend><fmt:message key="print.family.parents"/></legend>
	            <div style="float: left; width: 45px; position: relative; top: 23px;">
	            	<appfuse:label styleClass="desc" key="print.family.husband"/>
	            	<appfuse:label styleClass="desc" key="print.family.wife"/>
	            </div>
	            <div style="float: left; width: 135px;">
		    		<appfuse:label styleClass="desc" key="print.family.first-name"/>
			        <form:errors path="husband.firstName" cssClass="fieldError" cssStyle="width: 130px;"/>
			        <form:input path="husband.firstName" cssClass="text medium" cssErrorClass="text medium error" cssStyle="width: 130px;"/>
			        <form:errors path="wife.firstName" cssClass="fieldError" cssStyle="width: 130px;"/>
			        <form:input path="wife.firstName" cssClass="text medium" cssErrorClass="text medium error" cssStyle="width: 130px;"/>
		        </div>
		        <div style="float: left; margin-left: 10px; width: 135px;">
		    		<appfuse:label styleClass="desc" key="print.family.last-name"/>
			        <form:errors path="husband.surname" cssClass="fieldError" cssStyle="width: 130px;"/>
			        <form:input path="husband.surname" cssClass="text medium" cssErrorClass="text medium error" cssStyle="width: 130px;"/>
			        <form:errors path="wife.surname" cssClass="fieldError" cssStyle="width: 130px;"/>
			        <form:input path="wife.surname" cssClass="text medium" cssErrorClass="text medium error" cssStyle="width: 130px;"/>
		        </div>
		        <div style="float: left; margin-left: 10px; width: 120px;">
		    		<appfuse:label styleClass="desc" key="print.family.birthdate"/>
			        <form:errors path="husband.birthDate" cssClass="fieldError"/>
			        <form:input path="husband.birthDate" id="husbandBirthDateIn" cssClass="text" cssErrorClass="text error" maxlength="10" cssStyle="width:85px;"/>
			        <button id="husbandBirthDateInCal" type="button" class="calendarbutton"> ... </button>
			        <form:errors path="wife.birthDate" cssClass="fieldError"/>
			        <form:input path="wife.birthDate" id="wifeBirthDateIn" cssClass="text" cssErrorClass="text error" maxlength="10" cssStyle="width:85px;"/>
			        <button id="wifeBirthDateInCal" type="button" class="calendarbutton"> ... </button>
		        </div>
		        <div style="float: left; margin-left: 10px; width: 115px;">
		    		<appfuse:label styleClass="desc" key="print.family.job"/>
			        <form:errors path="husband.job" cssClass="fieldError" cssStyle="width: 110px;"/>
			        <form:input path="husband.job" cssClass="text medium" cssErrorClass="text medium error" cssStyle="width: 110px;"/>
			        <form:errors path="wife.job" cssClass="fieldError" cssStyle="width: 110px;"/>
			        <form:input path="wife.job" cssClass="text medium" cssErrorClass="text medium error" cssStyle="width: 110px;"/>
		        </div>
		        <div style="float: left; margin-left: 10px; width: 100px; position: relative; bottom: 13px;">
		    		<appfuse:label styleClass="desc" key="print.family.take-part-mass"/>
			        <form:errors path="husband.takePartMass" cssClass="fieldError"/>
			        <form:select path="husband.takePartMass" cssClass="text" cssErrorClass="text error">
						<form:options items="${requestScope.takePartList}" itemLabel="name" itemValue="id"/>
					</form:select>
					<form:errors path="wife.takePartMass" cssClass="fieldError"/>
			        <form:select path="wife.takePartMass" cssClass="text" cssErrorClass="text error">
						<form:options items="${requestScope.takePartList}" itemLabel="name" itemValue="id"/>
					</form:select>
		        </div>
		        <div style="float: left; margin-left: 10px; width: 95px; position: relative; bottom: 13px; padding-right: 10px;">
		    		<appfuse:label styleClass="desc" key="print.family.take-part-sacrament"/>
			        <form:errors path="husband.takePartSacrament" cssClass="fieldError"/>
			        <form:select path="husband.takePartSacrament" cssClass="text" cssErrorClass="text error">
						<form:options items="${requestScope.takePartList}" itemLabel="name" itemValue="id"/>
					</form:select>
					<form:hidden path="husband.id"/>
					<form:errors path="wife.takePartSacrament" cssClass="fieldError"/>
			        <form:select path="wife.takePartSacrament" cssClass="text" cssErrorClass="text error">
						<form:options items="${requestScope.takePartList}" itemLabel="name" itemValue="id"/>
					</form:select>
					<form:hidden path="wife.id"/>
		        </div>
			</fieldset>
		</li>
		
		<li>
	    	<fieldset class="xlarge">
	            <legend><fmt:message key="print.family.children"/></legend>
	            <table style="width: 830px;">
	            	<thead>
	            		<th><appfuse:label styleClass="desc" key="print.family.first-name"/></th>
	            		<th><appfuse:label styleClass="desc" key="print.family.last-name"/></th>
	            		<th style="width: 185px;"><appfuse:label styleClass="desc" key="print.family.birthdate"/></th>
	            		<th style="width: 115px;"><appfuse:label styleClass="desc" key="print.family.religious-formation"/></th>
	            		<th style="width: 100px;"><appfuse:label styleClass="desc" key="print.family.take-part-mass"/></th>
	            		<th style="width: 80px;"><appfuse:label styleClass="desc" key="print.family.take-part-sacrament"/></th>
	            		<th style="width: 140px;"></th>
	            	</thead>
					<tbody id="childbody">
						<tr id="patternchild" style="display:none; vertical-align: bottom;">
							<td>
								<input id="childFirstName" style="width: 130px;"/>
							</td>
							<td>
								<input id="childSurname" style="width: 130px;"/>
							</td>
							<td>
								<input id="childBirthDate" maxlength="10" style="width: 75px;"/>
							</td>
							<td>
								
								<input id="childReligiousFormation" style="width: 110px;"/>
							</td>
							<td>
								<select id="childTakePartMass">
									<c:forEach items="${requestScope.takePartList}" var="takePart">
										<option value="${takePart.id}">${takePart.name}</option>
									</c:forEach>
								</select>
							</td>
							<td>
								<select id="childTakePartSacrament"/>
									<c:forEach items="${requestScope.takePartList}" var="takePart">
										<option value="${takePart.id}">${takePart.name}</option>
									</c:forEach>
								</select>
								<input id="childId" type="hidden"/>
							</td>
							<td style="text-align: right;">
								<input id="deleteChildButton" type="button" value="<fmt:message key='button.delete'/>" onclick="javascript:deleteChild(this.id);"/>
							</td>
						</tr>
						
						<tr id="patternchildadd" style="vertical-align: bottom;">
							<td><input name="childFirstNameIn" id="childFirstNameIn" style="width: 130px;"/></td>
							<td><input name="childSurnameIn" id="childSurnameIn" style="width: 130px;"/></td>
							<td><input name="childBirthDateIn" id="childBirthDateIn" style="width: 75px;"/><button id="childBirthDateInCal" type="button" class="calendarbutton"> ... </button></td>
							<td><input name="childReligiousFormationIn" id="childReligiousFormationIn" style="width: 110px;"/></td>
							<td>
								<select name="childTakePartMassIn" id="childTakePartMassIn">
									<c:forEach items="${requestScope.takePartList}" var="takePart">
										<option value="${takePart.id}">${takePart.name}</option>
									</c:forEach>
								</select>
							</td>
							<td>
								<select name="childTakePartSacramentIn" id="childTakePartSacramentIn">
									<c:forEach items="${requestScope.takePartList}" var="takePart">
										<option value="${takePart.id}">${takePart.name}</option>
									</c:forEach>
								</select>
							</td>
							<td style="text-align: right;">
							    <input type="button" name="addChild" value="<fmt:message key="button.add"/>" onclick="javascript:writeChild();"/>
								<!-- <input type="button" name="clearChild" value="<fmt:message key="button.clear"/>" onclick="javascript:clearChild();"/>-->
							</td>
						</tr>
						
					</tbody>
				</table>
	        </fieldset>
    	</li>
		
		
	    <li>
	    	<fieldset class="xlarge">
	            <legend><fmt:message key="print.family.other"/></legend>
	            <table style="width: 830px;">
	            	<thead>
	            		<th><appfuse:label styleClass="desc" key="print.family.first-name"/></th>
	            		<th><appfuse:label styleClass="desc" key="print.family.last-name"/></th>
	            		<th style="width: 185px;"><appfuse:label styleClass="desc" key="print.family.birthdate"/></th>
	            		<th style="width: 115px;"><appfuse:label styleClass="desc" key="print.family.religious-formation"/></th>
	            		<th style="width: 100px;"><appfuse:label styleClass="desc" key="print.family.take-part-mass"/></th>
	            		<th style="width: 80px;"><appfuse:label styleClass="desc" key="print.family.take-part-sacrament"/></th>
	            		<th style="width: 140px;"></th>
	            	</thead>
					<tbody id="otherbody">
						<tr id="patternother" style="display:none; vertical-align: bottom;">
							<td>
								<input id="otherFirstName" style="width: 130px;"/>
							</td>
							<td>
								
								<input id="otherSurname" style="width: 130px;"/>
							</td>
							<td>
								<input id="otherBirthDate" maxlength="10" style="width: 75px;"/>
							</td>
							<td>
								
								<input id="otherReligiousFormation" style="width: 110px;"/>
							</td>
							<td>
								<select id="otherTakePartMass">
									<c:forEach items="${requestScope.takePartList}" var="takePart">
										<option value="${takePart.id}">${takePart.name}</option>
									</c:forEach>
								</select>
							</td>
							<td>
								<select id="otherTakePartSacrament"/>
									<c:forEach items="${requestScope.takePartList}" var="takePart">
										<option value="${takePart.id}">${takePart.name}</option>
									</c:forEach>
								</select>
								<input id="otherId" type="hidden"/>
							</td>
							<td style="text-align: right;">
								<input id="deleteOtherButton" type="button" value="<fmt:message key='button.delete'/>" onclick="javascript:deleteOther(this.id);"/>
								<input type="hidden" id="deleteOtherId"/>
							</td>
						</tr>
						
						<tr id="patternotheradd" style="vertical-align: bottom;">
							<td><input name="otherFirstNameIn" id="otherFirstNameIn" style="width: 130px;"/></td>
							<td><input name="otherSurnameIn" id="otherSurnameIn" style="width: 130px;"/></td>
							<td><input name="otherBirthDateIn" id="otherBirthDateIn" style="width: 75px;"/><button id="otherBirthDateInCal" type="button" class="calendarbutton"> ... </button></td>
							<td><input name="otherReligiousFormationIn" id="otherReligiousFormationIn" style="width: 110px;"/></td>
							<td>
								<select name="otherTakePartMassIn" id="otherTakePartMassIn">
									<c:forEach items="${requestScope.takePartList}" var="takePart">
										<option value="${takePart.id}">${takePart.name}</option>
									</c:forEach>
								</select>
							</td>
							<td>
								<select name="otherTakePartSacramentIn" id="otherTakePartSacramentIn">
									<c:forEach items="${requestScope.takePartList}" var="takePart">
										<option value="${takePart.id}">${takePart.name}</option>
									</c:forEach>
								</select>
							</td>
							<td style="text-align: right;">
							    <input type="button" name="addOther" value="<fmt:message key="button.add"/>" onclick="javascript:writeOther();"/>
								<!-- <input type="button" name="clearOther" value="<fmt:message key="button.clear"/>" onclick="javascript:clearOther();"/> -->
							</td>
						</tr>
						
					</tbody>
				</table>
	        </fieldset>
    	</li>
	    
	    <li>
	    	<div style="float: left; clear: left;">
	    		<appfuse:label styleClass="desc" key="print.family.priest-remarks"/>
		        <form:errors path="priestRemarks" cssClass="fieldError"/>
		        <form:textarea path="priestRemarks" id="priestRemarks" cssClass="text" cssErrorClass="text error" cols="34" rows="7"/>
	        </div>
	        <div style="float: left; margin-left: 20px;">
	    		<appfuse:label styleClass="desc" key="print.family.material-situation"/>
		        <form:errors path="materialSituation" cssClass="fieldError"/>
		        <form:textarea path="materialSituation" id="materialSituation" cssClass="text medium" cssErrorClass="text medium error"/>
	        </div>
	        
	        <div style="float: left; margin-left: 20px;">
	    		<fieldset style="width: 350px;">
	            	<legend><fmt:message key="print.family.priestly-visit"/></legend>
		            <table>
		            	<thead>
		            		<th><appfuse:label styleClass="desc" key="print.family.visit-date"/></th>
		            		<th><appfuse:label styleClass="desc" key="print.family.visit-remarks"/></th>
		            		<th></th>
		            	</thead>
						<tbody id="visitbody">
							<tr id="patternvisit" style="display:none; vertical-align: top;">
								<td>
									<input id="visitDate" maxlength="10" style="width: 85px;"/>
								</td>
								<td>
									<textarea style="text medium" id="visitRemarks" rows="1"></textarea>
									<input id="visitId" type="hidden"/>
								</td>
								<td>
									<input id="deleteVisitButton" type="button" value="<fmt:message key='button.delete'/>" onclick="javascript:deleteVisit(this.id);"/>
									<!-- <input type="hidden" id="deleteVisitId"/> -->
								</td>
							</tr>
							
							<tr id="patternvisitadd" style="vertical-align: top;">
								<td style="width: 300px;"><input name="visitDateIn" id="visitDateIn" maxlength="10" style="width: 85px;"/><button id="visitDateInCal" type="button" class="calendarbutton"> ... </button></td>
								<td><textarea name="visitRemarksIn" id="visitRemarksIn" style="text medium" rows="1"></textarea></td>
								<td>
									<input type="button" name="addVisit" value="<fmt:message key="button.add"/>" onclick="javascript:writeVisit();"/>
									<input type="button" name="clearVisit" value="<fmt:message key="button.clear"/>" onclick="javascript:clearVisit();"/>
								</td>
							</tr>
						</tbody>
					</table>
	            </fieldset>
	        </div>
	    </li>
		
	    <li class="buttonBar bottom">
	    	<div style="float: left; clear: both; margin-top: 20px;">
		        <input type="submit" name="submit" class="button" onclick="bCancel=false"
		            value="<fmt:message key="button.save"/>" />
		            
		        <c:if test="${not empty requestScope.family.id}">
			        <input type="submit" name="delete" class="button" onclick="return deleteQuestion();"
			            value="<fmt:message key="button.delete"/>" />
		        </c:if>
		        
		        <c:url var="personsUrl" value="/print/persons.html"/>
		        <input type="button" name="cancel" class="button" onclick="javascript:location.href='${personsUrl}'"
		            value="<fmt:message key="button.cancel"/>" />
			</div>
			
			<c:if test="${not empty requestScope.family.id}">
				<div style="float: left; clear: both; margin-top: 20px;">
					<c:url var="printFilesUrl" value="/printfiles.html">
						<c:param name="familyId" value="${requestScope.family.id}"/>
					</c:url>
					<appfuse:label styleClass="desc" key="print.card"/>
					<input type="button" name="files" class="button" value="<fmt:message key='button.print'/>"
						onclick="javascript:window.open('${printFilesUrl}','Kartoteka','height=300,width=500');"/>
				</div>
			</c:if>
	    </li>
	</ul>
	</form:form>
</div>

<script type="text/javascript">
	var otherArray = new Array()
	var childrenArray = new Array()
	var visitArray = new Array()

	<c:forEach items="${requestScope.family.others}" var="other" varStatus="gridRow">
		otherArray[${gridRow.index}] = { jsId:${gridRow.index}+1, id:'${other.id}', firstName:'${other.firstName}',
			surname:'${other.surname}', birthDate:'${other.birthDate}',
			religiousFormation:'${other.religiousFormation}',
			takePartMass:'${other.takePartMass.id}', takePartSacrament:'${other.takePartSacrament.id}' }
	</c:forEach>

	<c:forEach items="${requestScope.family.children}" var="child" varStatus="gridRow">
		childrenArray[${gridRow.index}] = { jsId:${gridRow.index}+1, id:'${child.id}', firstName:'${child.firstName}',
			surname:'${child.surname}', birthDate:'${child.birthDate}',
			religiousFormation:'${child.religiousFormation}',
			takePartMass:'${child.takePartMass.id}', takePartSacrament:'${child.takePartSacrament.id}' }
	</c:forEach>

	<c:forEach items="${requestScope.family.visits}" var="visit" varStatus="gridRow">
		visitArray[${gridRow.index}] = { jsId:${gridRow.index}+1, id:'${visit.id}', date:'${visit.date}',
			remarks:'${visit.remarks}' }
	</c:forEach>

	Calendar.setup({
	    inputField  : "otherBirthDateIn",      // id of the input field
	    ifFormat    : "%d-%m-%Y",      // the date format
	    button      : "otherBirthDateInCal"    // id of the button
	});

	Calendar.setup({
	    inputField  : "childBirthDateIn",      // id of the input field
	    ifFormat    : "%d-%m-%Y",      // the date format
	    button      : "childBirthDateInCal"    // id of the button
	});

	Calendar.setup({
	    inputField  : "wifeBirthDateIn",      // id of the input field
	    ifFormat    : "%d-%m-%Y",      // the date format
	    button      : "wifeBirthDateInCal"    // id of the button
	});

	Calendar.setup({
	    inputField  : "husbandBirthDateIn",      // id of the input field
	    ifFormat    : "%d-%m-%Y",      // the date format
	    button      : "husbandBirthDateInCal"    // id of the button
	});

	Calendar.setup({
	    inputField  : "visitDateIn",      // id of the input field
	    ifFormat    : "%d-%m-%Y",      // the date format
	    button      : "visitDateInCal"    // id of the button
	});

	function init() {
		fillChildren()
	    fillOther()
	    fillVisit()
	}

	function getNextId(array) {
		var id = 0
		for (var i = 0; i < array.length; i++) {
			element = array[i]
			if (element.jsId > id)
				id = element.jsId
		}
		return ++id
	}

	function getLocation(array, jsId) {
		for (var i = 0; i < array.length; i++) {
			el = array[i]
			if (el.jsId == jsId)
				return i
		}
		return -1
	}

	//dzieci
	
	function fillChildren() {
		dwr.util.removeAllRows("childbody", { filter:function(tr) {
			return (tr.id != "patternchild" && tr.id != "patternchildadd");
		}});
		// Create a new set cloned from the pattern row
		var child, id;
		childrenArray.sort(function(p1, p2) { return p1.birthDate.localeCompare(p2.birthDate); });
		for (var i = 0; i < childrenArray.length; i++) {
			child = childrenArray[i];
			id = child.jsId;
			dwr.util.cloneNode("patternchild", { idSuffix:id });
			dwr.util.setValue("childFirstName" + id, child.firstName);
			dwr.util.setValue("childSurname" + id, child.surname);
			dwr.util.setValue("childBirthDate" + id, child.birthDate);
			dwr.util.setValue("childReligiousFormation" + id, child.religiousFormation);
			dwr.util.setValue("childTakePartMass" + id, child.takePartMass);
			dwr.util.setValue("childTakePartSacrament" + id, child.takePartSacrament);
			$("#patternchild" + id).css("display", "");
			
			$("#childFirstName" + id).attr('name', "children["+(id-1)+"].firstName");
			$("#childSurname" + id).attr('name', "children["+(id-1)+"].surname");
			$("#childBirthDate" + id).attr('name', "children["+(id-1)+"].birthDate");
			$("#childReligiousFormation" + id).attr('name', "children["+(id-1)+"].religiousFormation");
			$("#childTakePartMass" + id).attr('name', "children["+(id-1)+"].takePartMass");
			$("#childTakePartSacrament" + id).attr('name', "children["+(id-1)+"].takePartSacrament");
		
			if (child.id != null) {
				dwr.util.setValue("childId" + id, child.id);
				$("#childId" + id).attr('name', "children["+(id-1)+"].id");
			}
		}
	}


	function writeChild() {
		var child = { childFirstNameIn:null, childSurnameIn:null, childBirthDateIn:null, childReligiousFormationIn:null, childTakePartMassIn: null, childTakePartSacramentIn: null };
		dwr.util.getValues(child);
		child.firstName = child.childFirstNameIn
		child.surname = child.childSurnameIn
		child.birthDate = child.childBirthDateIn
		child.religiousFormation = child.childReligiousFormationIn
		child.takePartMass = child.childTakePartMassIn
		child.takePartSacrament = child.childTakePartSacramentIn
		
		child.jsId = getNextId(childrenArray)
		childrenArray[childrenArray.length] = child
			
		fillChildren()
		clearChild()
	}


	function clearChild() {
		dwr.util.setValues({ childFirstNameIn:null, childSurnameIn:null, childBirthDateIn:null, childReligiousFormationIn:null, childTakePartMassIn: null, childTakePartSacramentIn: null });
	}

	function deleteChild(childId) {
		// we were an id of the form "delete{id}", eg "delete42". We lookup the "42"
		id = childId.substring(17);
		child = childrenArray[getLocation(childrenArray, id)]
		childId = child.id
		
		if (confirm("Na pewno usunac?")) {
			childrenArray.splice(getLocation(childrenArray, id), 1)
			for (var i = 0; i < childrenArray.length; i++) {
				childrenArray[i].jsId = (i+1);
			}
		}
		fillChildren()
	}
	

	//pozostali domownicy

	function fillOther() {
		dwr.util.removeAllRows("otherbody", { filter:function(tr) {
			return (tr.id != "patternother" && tr.id != "patternotheradd");
		}});
		// Create a new set cloned from the pattern row
		var other, id;
		otherArray.sort(function(p1, p2) { return p1.birthDate.localeCompare(p2.birthDate); });
		for (var i = 0; i < otherArray.length; i++) {
			other = otherArray[i];
			id = other.jsId;
			dwr.util.cloneNode("patternother", { idSuffix:id });
			dwr.util.setValue("otherFirstName" + id, other.firstName);
			dwr.util.setValue("otherSurname" + id, other.surname);
			dwr.util.setValue("otherBirthDate" + id, other.birthDate);
			dwr.util.setValue("otherReligiousFormation" + id, other.religiousFormation);
			dwr.util.setValue("otherTakePartMass" + id, other.takePartMass);
			dwr.util.setValue("otherTakePartSacrament" + id, other.takePartSacrament);
			//$("patternother" + id).style.display = "";
			$("#patternother" + id).css("display", "");
			
			$("#otherFirstName" + id).attr('name', "others["+(id-1)+"].firstName");
			$("#otherSurname" + id).attr('name', "others["+(id-1)+"].surname");
			$("#otherBirthDate" + id).attr('name', "others["+(id-1)+"].birthDate");
			$("#otherReligiousFormation" + id).attr('name', "others["+(id-1)+"].religiousFormation");
			$("#otherTakePartMass" + id).attr('name', "others["+(id-1)+"].takePartMass");
			$("#otherTakePartSacrament" + id).attr('name', "others["+(id-1)+"].takePartSacrament");
		
			if (other.id != null) {
				dwr.util.setValue("otherId" + id, other.id);
				$("#otherId" + id).attr('name', "others["+(id-1)+"].id");
			}
		}
	}


	function writeOther() {
		var other = { otherFirstNameIn:null, otherSurnameIn:null, otherBirthDateIn:null, otherReligiousFormationIn:null, otherTakePartMassIn: null, otherTakePartSacramentIn: null };
		dwr.util.getValues(other);
		other.firstName = other.otherFirstNameIn
		other.surname = other.otherSurnameIn
		other.birthDate = other.otherBirthDateIn
		other.religiousFormation = other.otherReligiousFormationIn
		other.takePartMass = other.otherTakePartMassIn
		other.takePartSacrament = other.otherTakePartSacramentIn
		
		other.jsId = getNextId(otherArray)
		otherArray[otherArray.length] = other
			
		fillOther()
		clearOther()
	}


	function clearOther() {
		dwr.util.setValues({ otherFirstNameIn:null, otherSurnameIn:null, otherBirthDateIn:null, otherReligiousFormationIn:null, otherTakePartMassIn: null, otherTakePartSacramentIn: null });
	}

	function deleteOther(otherId) {
		// we were an id of the form "delete{id}", eg "delete42". We lookup the "42"
		id = otherId.substring(17);
		other = otherArray[getLocation(otherArray, id)]
		otherId = other.id
		
		if (confirm("Na pewno usunac?")) {
			otherArray.splice(getLocation(otherArray, id), 1)
			for (var i = 0; i < otherArray.length; i++) {
				otherArray[i].jsId = (i+1);
			}
		}
		fillOther()
	}



	/* wizyty duszpasterskie */
	
	
	function fillVisit() {
		dwr.util.removeAllRows("visitbody", { filter:function(tr) {
			return (tr.id != "patternvisit" && tr.id != "patternvisitadd");
		}});
		// Create a new set cloned from the pattern row
		var visit, id;
		visitArray.sort(function(p1, p2) {
			return p1.date.substring(p1.date.length-4,p1.date.length).localeCompare(p2.date.substring(p2.date.length-4,p2.date.length));
		});
		for (var i = 0; i < visitArray.length; i++) {
			visit = visitArray[i];
			id = visit.jsId;
			dwr.util.cloneNode("patternvisit", { idSuffix:id });
			dwr.util.setValue("visitDate" + id, visit.date);
			dwr.util.setValue("visitRemarks" + id, visit.remarks);
			//$("patternvisit" + id).style.display = "";
			$("#patternvisit" + id).css("display", "");
			
			$("#visitDate" + id).attr('name', "visits["+(id-1)+"].date");
			$("#visitRemarks" + id).attr('name', "visits["+(id-1)+"].remarks");
			//$("#visitDate" + id).name = "visits["+(id-1)+"].date"
			//$("#visitRemarks" + id).name = "visits["+(id-1)+"].remarks"
		
			if (visit.id != null) {
				dwr.util.setValue("visitId" + id, visit.id);
				//$("#visitId" + id).name = "visits["+(id-1)+"].id"
				$("#visitId" + id).attr('name', "visits["+(id-1)+"].id");
			}
		}
	}


	function writeVisit() {
		var visit = { visitDateIn:null, visitRemarksIn:null };
		dwr.util.getValues(visit);
		visit.date = visit.visitDateIn
		visit.remarks = visit.visitRemarksIn

		visit.jsId = getNextId(visitArray)
		visitArray[visitArray.length] = visit
			
		fillVisit()
		clearVisit()
	}


	function deleteVisit(visitId) {
		// we were an id of the form "delete{id}", eg "delete42". We lookup the "42"
		id = visitId.substring(17) - 1;
		//visit = visitArray[id]
		
		if (confirm("Na pewno usunac wizyte?")) {
			visitArray.splice(id, 1)
			for (var i = 0; i < visitArray.length; i++) {
				visitArray[i].jsId = (i+1);
			}
		}
		fillVisit()
	}


	function clearVisit() {
		dwr.util.setValues({ visitDateIn:null, visitRemarksIn:null });
	}


	function onSubmit(form) {
		var inputs = document.getElementsByTagName("input");
		for (var i = 0; i < inputs.length; i++) {
			inputs[i].value = inputs[i].value.substr(0, 1).toUpperCase() + inputs[i].value.substr(1);
			inputs[i].value = trim(inputs[i].value)
		}
		return validateFamily(form);
	}

	function deleteQuestion() {
		return confirm("Czy na pewno usunac rodzine?")
	}
	
	init();
</script>
<v:javascript formName="family" staticJavascript="false"/>
<script type="text/javascript" src="<c:url value="/scripts/validator.jsp"/>"></script>