<%@ include file="/common/taglibs.jsp"%>

<head>
    <title><fmt:message key="intention.new.title"/></title>
    <meta name="menu" content="PrintMenu"/>
    
	<script type="text/javascript" src="${ctx}/dwr/engine.js"></script>
	<script type="text/javascript" src="${ctx}/dwr/util.js"></script>
	<script type="text/javascript" src="<c:url value='/scripts/selectbox.js'/>"></script>
	
	<script type="text/javascript" src="${ctx}/scripts/calendar/calendar.js"></script>
    <script type="text/javascript" src="${ctx}/scripts/calendar/calendar-setup.js"></script>
    <script type="text/javascript" src="${ctx}/scripts/calendar/lang/calendar-pl-utf8.js"></script>
</head>

<p><spring:message code="print.intention.edit" arguments="${intentionDate}"/></p>

<div style="width: 556px;">
	<spring:bind path="intention.*">
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
	
	<form:form method="post" commandName="intention" id="intentionForm" onsubmit="return onFormSubmit(this)">
		<form:hidden path="date"/>
		<ul>
			<li>
			    <appfuse:label styleClass="desc" key="print.intention-text"/>
			    <form:errors path="text" cssClass="fieldError"/>		        
			    <form:textarea path="text" id="text" cssClass="text" cssErrorClass="text error" maxlength="1023"/>
		    </li>
		    
		    <li>
			    <appfuse:label styleClass="desc" key="print.intention-offering"/>
			    <form:errors path="offering" cssClass="fieldError"/>
			    <form:input path="offering" id="offering" cssClass="text medium" cssErrorClass="text medium error" maxlength="10"/>
			</li>
			
			<li>
			    <appfuse:label styleClass="desc" key="tomes.editCelebrant.acceptor"/>
			    <form:errors path="acceptor" cssClass="fieldError"/>
			    <form:select path="acceptor" id="acceptor" cssClass="text medium" cssErrorClass="text medium error">
			    	<option value="0"><fmt:message key="label.choose"/></option>
			    	<form:options items="${celebrants}" itemLabel="fullName" itemValue="id"/>
			    </form:select>
			    <a href="${ctx}/tomes/celebrantsList.html"><fmt:message key='tomes.editIntention.edit-acceptor'/></a>
		    </li>
			
			<li>
		        <fieldset class="pickList">
		            <legend><fmt:message key="tomes.editCelebrant.confession"/></legend>
		            <table class="pickList">
		                <tr>
		                    <th class="pickLabel">
		                        <appfuse:label key="tomes.editIntention.all-confessions" colon="false" styleClass="required"/>
		                    </th>
		                    <td></td>
		                    <th class="pickLabel">
		                        <appfuse:label key="tomes.editIntention.set-confessions" colon="false" styleClass="required"/>
		                    </th>
		                </tr>
		                <c:set var="leftList" value="${celebrantList}" scope="request"/>
		                <c:set var="rightList" value="${intention.confessionList}" scope="request"/>
		                <c:import url="/WEB-INF/pages/pickList.jsp">
		                    <c:param name="listCount" value="1"/>
		                    <c:param name="leftId" value="confessions"/>
		                    <c:param name="rightId" value="confessionList"/>
		                </c:import>
		            </table>
		        </fieldset>
		        <a href="${ctx}/tomes/celebrantsList.html"><fmt:message key='tomes.editIntention.edit-confession'/></a>
		    </li>
			
			<li>
			    <appfuse:label styleClass="descBlock" key="tomes.editCelebrant.type"/>
			    <appfuse:label styleClass="descBlockMargin" key="tomes.editCelebrant.duration"/>
			</li>
			<li>
			    <form:errors path="type" cssClass="fieldError"/>
			    <form:select path="type" id="type" cssClass="text medium" cssErrorClass="text medium error">
			    	<option value="0"><fmt:message key="label.choose"/></option>
			    	<form:options items="${types}" itemLabel="name" itemValue="id"/>
			    </form:select>
			    
  			    <form:errors path="duration" cssClass="fieldError"/>
			   	<form:select path="duration" id="duration" cssClass="text medium" cssErrorClass="text medium error">
			    	<form:options items="${durations}" itemLabel="duration" itemValue="id"/>
			    </form:select>
		    </li>

		    <li>
		        <fieldset class="pickList">
		            <legend><fmt:message key="tomes.editIntention.celebrants"/></legend>
		            <table class="pickList">
		                <tr>
		                    <th class="pickLabel">
		                        <appfuse:label key="tomes.editIntention.all-celebrants" colon="false" styleClass="required"/>
		                    </th>
		                    <td></td>
		                    <th class="pickLabel">
		                        <appfuse:label key="tomes.editIntention.set-celebrants" colon="false" styleClass="required"/>
		                    </th>
		                </tr>
		                <c:set var="leftList" value="${celebrantList}" scope="request"/>
		                <c:set var="rightList" value="${intention.celebrantList}" scope="request"/>
		                <c:import url="/WEB-INF/pages/pickList.jsp">
		                    <c:param name="listCount" value="1"/>
		                    <c:param name="leftId" value="celebrants"/>
		                    <c:param name="rightId" value="celebrantList"/>
		                </c:import>
		            </table>
		        </fieldset>
		        <a href="${ctx}/tomes/celebrantsList.html"><fmt:message key='tomes.editIntention.edit-celebrant'/></a>
		    </li>
		    
		    <li>			    
			    <c:import url="/WEB-INF/pages/repeatToDate.jsp">
                    <c:param name="sampleParam" value="dummy"/>
                </c:import>
			</li>
			
			<li>
			    <appfuse:label styleClass="desc" key="tomes.editIntention.notices"/>
			    <form:errors path="notices" cssClass="fieldError"/>		        
			    <form:textarea path="notices" id="notices" cssClass="text" cssErrorClass="text error" maxlength="1023"/>
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
    //Form.focusFirstElement($('celebrantList'));
    //highlightFormElements();

	<!-- This is here so we can exclude the selectAll call when roles is hidden -->
	function onFormSubmit(theForm) {
	    selectAll('celebrantList');
	    selectAll('confessionList');
	    return true;
	}

	window.onload = function() {
		Calendar.setup({
		    inputField  : "toDate",      // id of the input field
		    ifFormat    : "%d-%m-%Y",      // the date format
		    button      : "dateCal"    // id of the button
		});
	}
</script>