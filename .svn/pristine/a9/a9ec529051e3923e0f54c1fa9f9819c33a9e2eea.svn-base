<%@ include file="/common/taglibs.jsp"%>

<head>
    <title><fmt:message key="mail.new.title"/></title>
    <meta name="menu" content="MailMenu"/>
    <script type="text/javascript" src="${ctx}/dwr/interface/AddresseeManager.js"></script>
	<script type="text/javascript" src="${ctx}/dwr/engine.js"></script>
	<script type="text/javascript" src="${ctx}/dwr/util.js"></script>
</head>

<p><spring:message code="mail.edit.message" arguments="${requestScope.editMail.path}"/></p>

DEPRECATED!!!

<div style="width: 456px;">
	<spring:bind path="editMail.*">
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
	
	<!--
	    The most important part is to declare your form's enctype to be "multipart/form-data"
	-->
	<form:form commandName="editMail" method="post" action=""
	    onsubmit="return validateEditMail(this)" id="editMail">
	    <form:hidden path="path"/>
	    <input type="hidden" value="${requestScope.fileId}" name="fileId"/>
	<ul>
	    
	    <li>
	        <appfuse:label styleClass="desc" key="mail.number"/>
	        <form:errors path="number" cssClass="fieldError"/>
	        <form:input path="number" id="number" cssClass="text medium" cssErrorClass="text medium error" maxlength="50"/>
	    </li>
		<li>
			<div style="float: left; margin-right: 20px">
				<appfuse:label styleClass="desc" key="mail.addressee.first-letter"/>
				<select id="letter" onchange="javascript:changeLetter(this.value);">
					<option value="0"><fmt:message key="label.choose"/></option>
					<c:forEach items="${requestScope.letters}" var="letter">
						<!-- <option <c:if test="${fn:substring(requestScope.editMail.receiver.fullName, 0, 1) == letter}">selected="selected"</c:if>>${letter}</option> -->
						<option >${letter}</option>
					</c:forEach>
				</select>
			</div>	    		
			
			<div style="float: left; clear: right;">
				<appfuse:label styleClass="desc" key="mail.receiver"/>
				<form:errors path="receiver" cssClass="fieldError"/>
				<form:select path="receiver" cssClass="text medium" cssErrorClass="text medium error" id="addressees"/>
			</div>
		</li>
	    <li>
	    	<div style="clear: both; float: left;">
		        <appfuse:label key="mail.keywords" styleClass="desc"/>
		        <form:errors path="keyWords" cssClass="fieldError"/>
		        <form:textarea path="keyWords" id="keyWords" cssClass="text large" cssErrorClass="text state error"/>
	        </div>
	    </li>
    	<li>
    		<div style="clear: both; float: left;">
		        <appfuse:label key="mail.description-name" styleClass="desc"/>
		        <form:errors path="descriptionName" cssClass="fieldError"/>
		        <form:input path="descriptionName" id="descriptionName" cssClass="text medium" cssErrorClass="text medium error"/>
	        </div>
	    </li>
	    
	    <li class="buttonBar bottom">
	    	<div style="clear: both; float: left; margin-top: 10px;">
		        <input type="submit" name="upload" class="button" onclick="bCancel=false"
		            value="<fmt:message key="button.upload"/>" />
		        <input type="button" name="cancel" class="button" onclick="javascript:window.close();"
		            value="<fmt:message key="button.cancel"/>" />
	        </div>
	    </li>
	</ul>
	</form:form>
</div>

<script type="text/javascript">
    var labelChoose = '<fmt:message key="label.choose"/>'

    function changeLetter(value) {
        AddresseeManager.getAddresseesWithFirstLetter(value, populateAddressees)
    }

    function populateAddressees(addressees) {
    	dwr.util.removeAllOptions("addressees")
    	addressees.sort(function(p1, p2) { return p1.lastName.localeCompare(p2.lastName); });
    	//var representantChoose = {id:0, name:'<fmt:message key="label.choose"/>'}
    	for (var i = addressees.length; i > 0; i--) {
    		addressees[i] = addressees[i-1]
    	}
    	addressees[0] = {id:0, name:labelChoose}
    	dwr.util.addOptions("addressees", addressees, "id", "fullName")
    }

    document.getElementById('letter').value='${fn:substring(requestScope.editMail.receiver.fullName, 0, 1)}'
    changeLetter(document.getElementById('letter').value)
</script>
<v:javascript formName="editMail" staticJavascript="false"/>
<script type="text/javascript" src="<c:url value="/scripts/validator.jsp"/>"></script>