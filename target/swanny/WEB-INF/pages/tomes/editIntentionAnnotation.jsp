<%@ include file="/common/taglibs.jsp"%>

<head>
    <title><fmt:message key="tomes.editIntentionAnnotation.title"/></title>
    <meta name="menu" content="PrintMenu"/>

	<script type="text/javascript" src="${ctx}/scripts/calendar/calendar.js"></script>
    <script type="text/javascript" src="${ctx}/scripts/calendar/calendar-setup.js"></script>
    <script type="text/javascript" src="${ctx}/scripts/calendar/lang/calendar-pl-utf8.js"></script>
</head>

<p><spring:message code="tomes.editIntentionAnnotation.title"/></p>

<div style="width: 556px;">
	<spring:bind path="intentionAnnotation.*">
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
	
	<form:form method="post" commandName="intentionAnnotation">
		<form:hidden path="id"/>
		<form:hidden path="date"/>
		<input type="hidden" name="allDay" value="true"/>
		<ul>
			<li>
			    <appfuse:label styleClass="desc" key="tomes.editIntentionAnnotation.annotation"/>
			    <form:errors path="annotation" cssClass="fieldError"/>		        
			    <form:textarea path="annotation" id="annotation" cssClass="text" cssErrorClass="text error" maxlength="1023"/>
		    </li>
		    
		    <li>			    
			    <c:import url="/WEB-INF/pages/repeatToDate.jsp">
                    <c:param name="sampleParam" value="dummy"/>
                </c:import>
			</li>
		    
		  	<li class="buttonBar bottom">
		    	<div style="clear: both; float: left; margin-top: 10px;">
			        <input type="submit" name="save" class="button" onclick="bCancel=false"
			            value="<fmt:message key="button.save"/>" />
			        <input type="button" name="cancel" class="button" onclick="javascript:window.close();"
			            value="<fmt:message key="button.cancel"/>" />
			    </div>
		    </li>
		</ul>
	</form:form>
</div>

<script type="text/javascript">
	window.onload = function() {
		Calendar.setup({
		    inputField  : "toDate",      // id of the input field
		    ifFormat    : "%d-%m-%Y",      // the date format
		    button      : "dateCal"    // id of the button
		});
	}
</script>