<%@ include file="/common/taglibs.jsp"%>

<head>
    <title><fmt:message key="graveyard.parsedxf.title"/></title>
    <meta name="menu" content="GraveyardMenu"/>
</head>

<p><spring:message code="graveyard.parsedxf.message"/></p>

<div style="width: 456px;">
	<spring:bind path="dxffile.*">
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
	
	<form:form commandName="dxffile" method="post" enctype="multipart/form-data" id="dxffile">
	<ul>
	    <li>
	    	<div style="clear: both; float: left;">
		        <appfuse:label key="parsedxf.file" styleClass="desc"/>
		        <form:errors path="file" cssClass="fieldError"/>
		        <input type="file" name="file" id="file" class="file large"/>
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
    Form.focusFirstElement($('dxffile'));
    highlightFormElements();
</script>
<v:javascript formName="dxffile" staticJavascript="false"/>
<script type="text/javascript" src="<c:url value="/scripts/validator.jsp"/>"></script>