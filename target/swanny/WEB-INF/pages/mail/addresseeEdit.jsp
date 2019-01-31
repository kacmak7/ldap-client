 <%@ include file="/common/taglibs.jsp"%>

<head>
    <title><fmt:message key="mail.addressee.edit"/></title>
    <meta name="menu" content="MailMenu"/>
</head>

<p><spring:message code="mail.addressee.edit"/></p>

<div style="width: 456px;">
	<spring:bind path="addressee.*">
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
	
	<form:form commandName="addressee" method="post" action=""
	    onsubmit="return onSubmit(this);" id="addressee">
	<form:hidden path = "id"/>
	<ul>
		<li>
	        <appfuse:label styleClass="desc" key="mail.addressee.title"/>
	        <form:errors path="title" cssClass="fieldError"/>
	        <form:input path="title" id="title" cssClass="text medium" cssErrorClass="text medium error" maxlength="50"/>
	    </li>
	    <li>
	        <appfuse:label styleClass="desc" key="mail.addressee.first-name"/>
	        <form:errors path="firstName" cssClass="fieldError"/>
	        <form:input path="firstName" id="firstName" cssClass="text medium" cssErrorClass="text medium error" maxlength="128"/>
	    </li>
	    <li>
	        <appfuse:label styleClass="desc" key="mail.addressee.last-name"/>
	        <form:errors path="lastName" cssClass="fieldError"/>
	        <form:input path="lastName" id="firstName" cssClass="text medium" cssErrorClass="text medium error" maxlength="128"/>
	    </li>
	    <li>
        	<appfuse:label styleClass="desc" key="mail.addressee.institution"/>
            <form:errors path="institution" cssClass="fieldError"/>
            <form:input path="institution" id="receiver" cssClass="text medium" cssErrorClass="text medium error" maxlength="128"/>
	    </li>
	    <li>
        	<appfuse:label styleClass="desc" key="mail.addressee.street"/>
            <form:errors path="street" cssClass="fieldError"/>
            <form:input path="street" id="street" cssClass="text medium" cssErrorClass="text medium error" maxlength="128"/>
	    </li>
	    <li>
        	<appfuse:label styleClass="desc" key="mail.addressee.post"/>
            <form:errors path="post" cssClass="fieldError"/>
            <form:input path="post" id="post" cssClass="text medium" cssErrorClass="text medium error" maxlength="64"/>
	    </li>
	    <li>
        	<appfuse:label styleClass="desc" key="mail.addressee.country"/>
            <form:errors path="country" cssClass="fieldError"/>
            <form:input path="country" id="country" cssClass="text medium" cssErrorClass="text medium error" maxlength="64"/>
	    </li>
		
	    <li class="buttonBar bottom">
	        <input type="submit" name="submit" class="button" onclick="bCancel=false"
	            value="<fmt:message key="button.save"/>" />
	            
	      	<c:if test="${not empty requestScope.addressee.id}">
				<input type="submit" name="delete" class="button" onclick="deleteQuestion();"
					value="<fmt:message key="button.delete"/>" />
		    </c:if>

	        <input type="button" name="cancel" class="button" onclick="javascript:window.close();"
	            value="<fmt:message key="button.cancel"/>" />
	    </li>
	</ul>
	</form:form>
</div>

<script type="text/javascript">
	function onSubmit(form) {
		var inputs = document.getElementsByTagName("input");
		for (var i = 0; i < inputs.length; i++) {
			inputs[i].value = inputs[i].value.substr(0, 1).toUpperCase() + inputs[i].value.substr(1);
		}
		return validateAddressee(form);
	}

	function deleteQuestion() {
		var form = document.getElementById("addressee");
		form.action = "addresseeEdit/delete.html";
		if(confirm("Czy na pewno usunac adresata?") == true)
		{
			form.submit();
		}
	}
</script>
<v:javascript formName="addressee" staticJavascript="false"/>