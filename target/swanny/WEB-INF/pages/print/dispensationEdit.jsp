 <%@ include file="/common/taglibs.jsp"%>

<head>
    <title><fmt:message key="print.dispensation.edit"/></title>
    <meta name="menu" content="PrintMenu"/>
    <script type="text/javascript" src="${ctx}/dwr/engine.js"></script>
    <script type="text/javascript" src="${ctx}/dwr/util.js"></script>
</head>

<p><spring:message code="print.dispensation.edit"/></p>

<div>
	<spring:bind path="fianceePair.*">
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
	
	<form:form commandName="fianceePair" method="post"
	    onsubmit="return validateFianceePair(this)" id="fianceePair">
	    
	    <input type="hidden" name="fianceePairId" value="${fianceePair.id}"/>
	    <form:hidden path="dispensation.id"/>
		
			<table>
				<tr>
					<td>
						<appfuse:label styleClass="desc" key="print.dispensation.sacrament"/>
				        <form:errors path="dispensation.sacrament" cssClass="fieldError"/>
				        <form:input path="dispensation.sacrament" id="sacrament" cssClass="text wide" cssErrorClass="text wide error"/>
					</td>
				</tr>
				<tr>
					<td>
						<appfuse:label styleClass="desc" key="print.dispensation.reason"/>
				        <form:errors path="dispensation.reason" cssClass="fieldError"/>
				        <form:textarea path="dispensation.reason" id="reason" cssClass="text wide" cssErrorClass="text wide error"/>
					</td>
				</tr>
				<tr>
					<td>
						<appfuse:label styleClass="desc" key="print.dispensation.underage"/>
				        <form:errors path="dispensation.underAge" cssClass="fieldError"/>
				        <form:checkbox path="dispensation.underAge" id="underAge" cssClass="text" cssErrorClass="text wide error"/>
					</td>
				</tr>
			</table>
			
	    	<div style="float: left; clear: both; margin-top: 20px;">
		        <input type="submit" name="submit" class="button" onclick="bCancel=false"
		            value="<fmt:message key="button.save"/>" />
		        
		        <c:url var="dispensationUrl" value="/printdispensation.html">
					<c:param name="fianceePairId" value="${requestScope.fianceePair.id}"/>
				</c:url>
				<input type="button" class="button" name="dispensation" value="<fmt:message key='button.print'/>"
					onclick="javascript:window.location='${dispensationUrl}';"/>
		        
		        <input type="button" name="cancel" class="button"
		        onclick="window.location='print/fianceesform.html?fianceePairId=${fianceePair.id}'" value="<fmt:message key="button.cancel"/>" />
			</div>
		</ul>	
	</form:form>
</div>

<script type="text/javascript">
    Form.focusFirstElement($('fianceePair'));
    highlightFormElements();
</script>
<v:javascript formName="fianceePair" staticJavascript="false"/>
<script type="text/javascript" src="<c:url value="/scripts/validator.jsp"/>"></script>