<%@ include file="/common/taglibs.jsp"%>

<head>
    <title><fmt:message key="tomes.celebrantsList.title"/></title>
    <meta name="heading" content="<fmt:message key='tomes.celebrantsList.title'/>"/>
    <meta name="menu" content="PrintMenu"/>
</head>

<display:table name="celebrants" cellspacing="0" cellpadding="0" requestURI="" defaultsort="1"
		id="celebrant" pagesize="25" class="table" export="false" style="margin-bottom: 10px; width: auto;" >
	<display:column property="firstName" escapeXml="true" sortable="true" titleKey="tomes.celebrantsList.first-name"
        url="/tomes/editCelebrant.html?edit=true" paramId="id" paramProperty="id" media="html"/>
    <display:column property="surname" escapeXml="true" sortable="true" titleKey="tomes.celebrantsList.last-name"
        url="/tomes/editCelebrant.html?edit=true" paramId="id" paramProperty="id" media="html"/>
    <display:column sortable="true" titleKey="tomes.celebrantsList.active" url="/tomes/editCelebrant.html?edit=true" paramId="id" paramProperty="id" media="html">
    	<input type="checkbox" <c:if test="${celebrant.active == true}">checked</c:if>/>
    </display:column>
    <display:column titleKey="tomes.intentionsList.table.actions" media="html">
    	<a href="${ctx}/tomes/editCelebrant.html?edit=${celebrant.id}"><fmt:message key='button.edit'/></a> <a href="${ctx}/tomes/editCelebrant.html?delete=${celebrant.id}" onclick="return deleteConfirm();"><fmt:message key='button.delete'/></a>
    </display:column>
</display:table>

<div style="clear: both; float: left; margin-top: 10px;">
	<a href="${ctx}/tomes/editCelebrant.html?edit=true">
	    <input type="button" name="add" class="wide-button"
	    	value="<fmt:message key="tomes.celebrantsList.add-new"/>" />
    </a>
</div>

<script type="text/javascript">
	function deleteConfirm() {
		return confirm("<fmt:message key='warning.confirm-delete'/>")
	}
</script>