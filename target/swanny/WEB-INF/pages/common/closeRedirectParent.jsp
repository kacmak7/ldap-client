<%@ include file="/common/taglibs.jsp"%>

<script type="text/javascript">
	<c:url var="redirectUrl" value="${requestScope.redirectUrl}">
		<c:forEach var="p" items="${requestScope.urlParams}">
			<c:param name="${p.key}" value="${p.value}"/>
		</c:forEach>
	</c:url>
	window.opener.location.href='${redirectUrl}'
	window.close();
</script>