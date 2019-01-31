 <%@ include file="/common/taglibs.jsp"%>

<head>
    <title><fmt:message key="print.investigation.edit"/></title>
    <meta name="menu" content="PrintMenu"/>
    <script type="text/javascript" src="${ctx}/dwr/engine.js"></script>
    <script type="text/javascript" src="${ctx}/dwr/util.js"></script>
</head>

<p><spring:message code="print.investigation.edit"/></p>

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
		
			<table>
				<thead>
					<th><fmt:message key="print.investigation.questions"/></th>
					<th><fmt:message key="print.investigation.answers"/> <fmt:message key="print.investigation.fianceeHe"/></th>
					<th><fmt:message key="print.investigation.answers"/> <fmt:message key="print.investigation.fianceeShe"/></th>
				</thead>
				<tbody>
					<%--<c:forEach items="${requestScope.fianceePair.fianceeHe.protocole.answers}" varStatus="status">--%>
					<c:forEach var="i" begin="1" end="${fn:length(requestScope.fianceePair.fianceeHe.remaining.marriage.protocole.answers)}" step="1" varStatus ="status">
						<tr>
							<td><fmt:message key="print.investigation.question${i}"/></td>
							<td><form:textarea path="fianceeHe.remaining.marriage.protocole.answers[${i-1}]"/></td>
							<td><form:textarea path="fianceeShe.remaining.marriage.protocole.answers[${i-1}]"/></td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
			
	    	<div style="float: left; clear: both; margin-top: 20px;">
		        <input type="submit" name="submit" class="button" onclick="bCancel=false"
		            value="<fmt:message key="button.save"/>" />
		        
		        <c:url var="investigationUrl" value="/printinvestigation.html">
					<c:param name="fianceePairId" value="${requestScope.fianceePair.id}"/>
				</c:url>
				<input type="button" class="button" name="investigation" value="<fmt:message key='button.print'/>"
					onclick="javascript:window.location='${investigationUrl}';"/>
		        
		        <input type="submit" name="cancel" class="button" value="<fmt:message key="button.cancel"/>" />
			</div>
		</ul>	
	</form:form>
</div>

<script type="text/javascript">
    Form.focusFirstElement($('fianceePair'));
    highlightFormElements();

    Calendar.setup({
	    inputField  : "baptismDate",      // id of the input field
	    ifFormat    : "%d-%m-%Y",      // the date format
	    button      : "baptismDateCal"    // id of the button
	});

    Calendar.setup({
	    inputField  : "confirmationDate",      // id of the input field
	    ifFormat    : "%d-%m-%Y",      // the date format
	    button      : "confirmationDateCal"    // id of the button
	});

	Calendar.setup({
	    inputField  : "deathDate",      // id of the input field
	    ifFormat    : "%d-%m-%Y",      // the date format
	    button      : "deathDateCal"    // id of the button
	});
</script>
<v:javascript formName="fianceePair" staticJavascript="false"/>
<script type="text/javascript" src="<c:url value="/scripts/validator.jsp"/>"></script>