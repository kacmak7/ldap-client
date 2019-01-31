<%@ include file="/common/taglibs.jsp"%>

<head>
    <title><fmt:message key="columbarium.title"/></title>
    <meta name="menu" content="ColumbariumMenu"/>
    <meta name="heading" content="<fmt:message key='columbarium.title'/>"/>
</head>

<body>
	<form:form method="post" id="columbariumForm">
	<div class="columbariums">
		<p><fmt:message key='columbarium.choose'/></p>
		<table class="inputsToLinks"><tr>
		    <c:forEach var="list" items="${requestScope.columbariums}">
		    	<td><input type="submit" value="${list.name}" name="columbarium"></input></td> 
		    </c:forEach>
		</tr></table>
    </div>
    </form:form>
    
    <div class="niches">
    	<p><fmt:message key='niche.choose'/></p>
	    
	    <table class="inputsToLinks">
	    <c:forEach var="group" items="${requestScope.nichesGroups}"><tr>
	    	<c:forEach var="list" items="${group}">
	    		<td>
	    		<a href="<c:url value='/columbarium/nicheDetails.html'>
	    			<c:param name='nicheNumber'>${list.number}</c:param>
	    		</c:url>">${list.number}</a>
	    		
	    		</td>
	    	</c:forEach>
	    </c:forEach>
	    </table>
    </div>
    

</body>