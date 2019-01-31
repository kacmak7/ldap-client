<%@ include file="/common/taglibs.jsp"%>

<head>
    <title><fmt:message key="print.persons"/></title>
    <c:choose>
    	<c:when test="${empty param.person1Id}">
    		<meta name="heading" content="<fmt:message key='print.persons'/>"/>
    	</c:when>
    	<c:otherwise>
    		<meta name="heading" content="<fmt:message key='print.fiancees'/>"/>
    	</c:otherwise>
    </c:choose>
    <meta name="menu" content="PrintMenu"/>
</head>

<c:set var="buttons">
	<c:url value="/print/persons.html" var="personsUrl">
		<c:param name="person1Id" value="${param.person1Id}"/>
	</c:url>
	<c:url value="/print/persons.html" var="personsUrl">
		<c:param name="person2Id" value="${param.person2Id}"/>
	</c:url>
	<form:form commandName="filter" id="personsFilterForm" action="${personsUrl}">
	    <input type="button" style="margin-right: 5px"
	        onclick="location.href='<c:url value="/print/personform.html"/>'"
	        value="<fmt:message key="button.add"/>"/>
	        
	    <fmt:message key="person.filter"/> :
	    <fmt:message key="person.filter.first-name"/>
		<form:input path="firstName" id="firstName"/>
		<fmt:message key="person.filter.surname"/>
		<form:input path="surname" id="surname"/>
		<input type="submit" name="submit" class="button" onclick="bCancel=false"
			value="<fmt:message key="button.search"/>" />
	</form:form>
</c:set>

<c:out value="${buttons}" escapeXml="false" />

<display:table name="personsList" cellspacing="0" cellpadding="0" requestURI="" 
    defaultsort="1" id="person" pagesize="15" class="table" export="false" style="margin-bottom: 10px;">
    <c:choose>
    	<c:when test="${empty param.person1Id and empty param.person2Id}">
		    <display:column property="surnameNotNull" escapeXml="true" sortable="true" titleKey="print.family.last-name" style="width: 25%"
		        url="/print/personform.html" paramId="personId" paramProperty="id"/>
		    <display:column property="firstName" escapeXml="true" sortable="true" titleKey="print.family.first-name" style="width: 25%"
		        url="/print/personform.html" paramId="personId" paramProperty="id"/>
	    </c:when>
	   	<c:otherwise>
	   		<c:choose>
		   		<c:when test="${not empty param.person1Id}">
				    <display:column property="surnameNotNull" escapeXml="true" sortable="true" titleKey="print.family.last-name" style="width: 25%"
				        url="/print/fianceesform.html?person1Id=${param.person1Id}" paramId="person2Id" paramProperty="id"/>
				    <display:column property="firstName" escapeXml="true" sortable="true" titleKey="print.family.first-name" style="width: 25%"
				        url="/print/fianceesform.html?person1Id=${param.person1Id}" paramId="person2Id" paramProperty="id"/>
				</c:when>
				<c:otherwise>
				    <display:column property="surnameNotNull" escapeXml="true" sortable="true" titleKey="print.family.last-name" style="width: 25%"
				        url="/print/fianceesform.html?person2Id=${param.person2Id}" paramId="person1Id" paramProperty="id"/>
				    <display:column property="firstName" escapeXml="true" sortable="true" titleKey="print.family.first-name" style="width: 25%"
				        url="/print/fianceesform.html?person2Id=${param.person2Id}" paramId="person1Id" paramProperty="id"/>
				</c:otherwise>
			</c:choose>
	    </c:otherwise>
    </c:choose>
</display:table>

<!--<c:out value="${buttons}" escapeXml="false" />-->

<script type="text/javascript">
    highlightTableRows("personsList");
</script>