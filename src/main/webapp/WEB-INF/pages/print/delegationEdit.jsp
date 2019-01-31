 <%@ include file="/common/taglibs.jsp"%>

<head>
    <title><fmt:message key="print.delegation.edit"/></title>
    <meta name="menu" content="PrintMenu"/>
    <script type="text/javascript" src="${ctx}/dwr/engine.js"></script>
    <script type="text/javascript" src="${ctx}/dwr/util.js"></script>
</head>

<p><spring:message code="print.delegation.edit"/></p>

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
	    
	    	<form:hidden path="assistanceDelegation.id"/>
		
			<table>
				<tr>
					<td>
						<appfuse:label styleClass="desc" key="print.delegation.priest-firstname"/>
				        <form:errors path="assistanceDelegation.priestFirstName" cssClass="fieldError"/>
				        <form:input path="assistanceDelegation.priestFirstName" id="priestFirstName" cssClass="text wide" cssErrorClass="text wide error"/>
					</td>
				</tr>
				<tr>
					<td>
						<appfuse:label styleClass="desc" key="print.delegation.priest-surname"/>
				        <form:errors path="assistanceDelegation.priestSurname" cssClass="fieldError"/>
				        <form:input path="assistanceDelegation.priestSurname" id="priestSurname" cssClass="text wide" cssErrorClass="text wide error"/>
					</td>
				</tr>
			</table>
			
	    	<div style="float: left; clear: both; margin-top: 20px;">
		        <input type="submit" name="submit" class="button" onclick="bCancel=false"
		            value="<fmt:message key="button.save"/>" />
		        
		        <c:url var="delegationUrl" value="/printdelegation.html">
					<c:param name="fianceePairId" value="${requestScope.fianceePair.id}"/>
				</c:url>
				<input type="button" class="button" name="delegation" value="<fmt:message key='button.print'/>"
					onclick="javascript:window.location='${delegationUrl}';"/>
		        
		        <c:url var="cancelUrl" value="/print/fianceesform.html">
					<c:param name="fianceePairId" value="${requestScope.fianceePair.id}"/>
				</c:url>
		        <input type="button" onclick="javascript:window.location='${cancelUrl}';" name="cancel" class="button" value="<fmt:message key="button.cancel"/>" />
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