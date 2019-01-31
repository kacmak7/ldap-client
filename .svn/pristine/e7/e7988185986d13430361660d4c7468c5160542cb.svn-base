<%@ include file="/common/taglibs.jsp"%>

<head>
    <title><fmt:message key="userProfile.title"/></title>
    <meta name="heading" content="<fmt:message key='userProfile.heading'/>"/>
    <meta name="menu" content="UserMenu"/>
    <script type="text/javascript" src="<c:url value='/scripts/selectbox.js'/>"></script>
    
    <script type="text/javascript" src="<c:url value='/scripts/prototype.js'/>"></script>
    <script type="text/javascript" src="<c:url value='/scripts/scriptaculous.js'/>"></script>
    <script type="text/javascript" src="<c:url value='/scripts/global.js'/>"></script>
</head>

<spring:bind path="user.*">
    <c:if test="${not empty status.errorMessages}">
    <div class="error">
        <c:forEach var="error" items="${status.errorMessages}">
            <img src="<c:url value="/images/iconWarning.gif"/>"
                alt="<fmt:message key="icon.warning"/>" class="icon"/>
            <c:out value="${error}" escapeXml="false"/><br />
        </c:forEach>
    </div>
    </c:if>
</spring:bind>

<form:form commandName="user" method="post" action="userform.html" onsubmit="return onFormSubmit(this)" id="userForm">
<form:hidden path="id"/>
<form:hidden path="version"/>
<input type="hidden" name="from" value="<c:out value="${param.from}"/>"/>

<c:if test="${cookieLogin == 'true'}">
    <form:hidden path="password"/>
    <form:hidden path="confirmPassword"/>
</c:if>

<c:if test="${empty user.version}">
    <input type="hidden" name="encryptPass" value="true"/>
</c:if>

<ul>
    <li class="buttonBar right">
        <%-- So the buttons can be used at the bottom of the form --%>
        <c:set var="buttons">
            <input type="submit" class="button" name="save" onclick="acept()" value="<fmt:message key="button.save"/>"/>

        <c:if test="${param.from == 'list' and param.method != 'Add'}">
            <input type="submit" class="button" name="delete" onclick="remove()"
                value="<fmt:message key="button.delete"/>"/>
        </c:if>

            <input type="button" class="button" name="cancel" onclick="abort()" value="<fmt:message key="button.cancel"/>"/>
        </c:set>
        <c:out value="${buttons}" escapeXml="false"/>
    </li>
    <li class="info">
        <c:choose>
            <c:when test="${param.from == 'list'}">
                <p><fmt:message key="userProfile.admin.message"/></p>
            </c:when>
            <c:otherwise>
                <p><fmt:message key="userProfile.message"/></p>
            </c:otherwise>
        </c:choose>
    </li>
    <li>
        <appfuse:label styleClass="desc" key="user.username"/>
        <form:errors path="username" cssClass="fieldError"/>
        <form:input path="username" id="username" cssClass="text large" cssErrorClass="text large error"/>
    </li>
    <c:if test="${cookieLogin != 'true'}">
    <li>
        <div>
            <div class="left">
                <appfuse:label styleClass="desc" key="user.password"/>
                <form:errors path="password" cssClass="fieldError"/>
                <form:password path="password" id="password" cssClass="text medium" cssErrorClass="text medium error" onchange="passwordChanged(this)" showPassword="true"/>
            </div>
            <div>
                <appfuse:label styleClass="desc" key="user.confirmPassword"/>
                <form:errors path="confirmPassword" cssClass="fieldError"/>
                <form:password path="confirmPassword" id="confirmPassword" cssClass="text medium" cssErrorClass="text medium error" showPassword="true"/>
            </div>
        </div>
    </li>
    </c:if>
    <li>
    	<div>
            <appfuse:label styleClass="desc" key="user.deletecode"/>
            <form:errors path="deleteCode" cssClass="fieldError"/>
            <form:password path="deleteCode" id="deleteCode" cssClass="text medium" cssErrorClass="text medium error" showPassword="true"/>
        </div>
    </li>
    <li>
        <div class="left">
            <appfuse:label styleClass="desc" key="user.firstName"/>
            <form:errors path="firstName" cssClass="fieldError"/>
            <form:input path="firstName" id="firstName" cssClass="text medium" cssErrorClass="text medium error" maxlength="50"/>
        </div>
        <div>
            <appfuse:label styleClass="desc" key="user.lastName"/>
            <form:errors path="lastName" cssClass="fieldError"/>
            <form:input path="lastName" id="lastName" cssClass="text medium" cssErrorClass="text medium error" maxlength="50"/>
        </div>
    </li>
<c:choose>
    <c:when test="${param.from == 'list' or param.method == 'Add'}">
    <li>
        <fieldset>
            <legend><fmt:message key="userProfile.accountSettings"/></legend>
            <form:checkbox path="enabled" id="enabled"/>
            <label for="enabled" class="choice"><fmt:message key="user.enabled"/></label>

            <form:checkbox path="accountExpired" id="accountExpired"/>
            <label for="accountExpired" class="choice"><fmt:message key="user.accountExpired"/></label>

            <form:checkbox path="accountLocked" id="accountLocked"/>
            <label for="accountLocked" class="choice"><fmt:message key="user.accountLocked"/></label>

            <form:checkbox path="credentialsExpired" id="credentialsExpired"/>
            <label for="credentialsExpired" class="choice"><fmt:message key="user.credentialsExpired"/></label>
        </fieldset>
    </li>
    <li>
        <fieldset class="pickList">
            <legend><fmt:message key="userProfile.assignRoles"/></legend>
            <table class="pickList">
                <tr>
                    <th class="pickLabel">
                        <appfuse:label key="user.availableRoles" colon="false" styleClass="required"/>
                    </th>
                    <td></td>
                    <th class="pickLabel">
                        <appfuse:label key="user.roles" colon="false" styleClass="required"/>
                    </th>
                </tr>
                <c:set var="leftList" value="${availableRoles}" scope="request"/>
                <c:set var="rightList" value="${user.roleList}" scope="request"/>
                <c:import url="/WEB-INF/pages/pickList.jsp">
                    <c:param name="listCount" value="1"/>
                    <c:param name="leftId" value="availableRoles"/>
                    <c:param name="rightId" value="userRoles"/>
                </c:import>
            </table>
        </fieldset>
    </li>
    </c:when>
    <c:when test="${not empty user.username}">
    <li>
        <strong><appfuse:label key="user.roles"/>:</strong>
        <%-- <c:forEach var="role" items="${user.roleList}" varStatus="status">
            <c:out value="${role.label}"/><c:if test="${!status.last}">,</c:if>
            <input type="hidden" name="userRoles" value="<c:out value="${role.label}"/>"/>
        </c:forEach> --%>
        <form:hidden path="enabled"/>
        <form:hidden path="accountExpired"/>
        <form:hidden path="accountLocked"/>
        <form:hidden path="credentialsExpired"/>
    </li>
    </c:when>
</c:choose>
    <li class="buttonBar bottom">
        <c:out value="${buttons}" escapeXml="false"/>
    </li>
</ul>
</form:form>

<script type="text/javascript">
    function passwordChanged(passwordField) {
        if (passwordField.id == "password") {
            var origPassword = "${user.password}";
        } else if (passwordField.id == "confirmPassword") {
            var origPassword = "${user.confirmPassword}";
        }

        if (passwordField.value != origPassword) {
            createFormElement("input", "hidden",  "encryptPass", "encryptPass",
                              "true", passwordField.form);
        }
    }

	<%-- This is here so we can exclude the selectAll call when roles is hidden --%>
	function onFormSubmit(theForm) {
	<c:if test="${param.from == 'list'}">
	    selectAll('userRoles');
	</c:if>
	    return validateUser(theForm);
	}
	
	<%-- Buttons scripts --%>
	function acept(){
		bCancel=false
		var form = document.getElementById("userForm");
		form.action = "submitUser.html";
		form.submit();
	}
	
	function remove(){
		bCancel=true;
		if(confirmDelete('user') == true)
		{
			var form = document.getElementById("userForm");
			form.action = "deleteUser.html";
			form.submit();
		}
	}
	
	function abort() {
		bCancel=true;
		window.location.replace("admin/users.html");
	}
</script>

<v:javascript formName="user" staticJavascript="false"/>
<script type="text/javascript" src="<c:url value="/scripts/validator.jsp"/>"></script>