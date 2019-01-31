<%@ include file="/common/taglibs.jsp"%>

<head>
    <title><fmt:message key="print.intention-tome"/></title>
    <meta name="heading" content="<fmt:message key='print.intention-tome'/>"/>
    <meta name="menu" content="PrintMenu"/> 
</head>

<display:table name="intentions" cellspacing="0" cellpadding="0" requestURI="" defaultsort="1"
		id="intention" pagesize="25" class="table" export="false" style="margin-bottom: 10px; width: auto;" >
	<display:column property="text" escapeXml="true" sortable="true" titleKey="tomes.intentionsList.table.text" style="width: 40%"
        url="/tomes/editIntention.html?edit=true&date=${intention.date.time}" paramId="id" paramProperty="id" media="html"/>
    <display:column property="offering" escapeXml="true" sortable="true" titleKey="tomes.intentionsList.table.offering" style="width: 10%"
        url="/tomes/editIntention.html?edit=true&date=${intention.date.time}" paramId="id" paramProperty="id" media="html"/>
    <display:column property="celebrantsString" escapeXml="true" sortable="false" titleKey="tomes.intentionsList.table.celebrant" style="width: 20%"
        url="/tomes/editIntention.html?edit=true&date=${intention.date.time}" paramId="id" paramProperty="id" media="html"/>
    <display:column titleKey="tomes.intentionsList.table.actions" media="html" style="width: 15%">
    	<a href="${ctx}/tomes/editIntention.html?edit=true&date=${intention.date.time}&id=${intention.id}"><fmt:message key='button.edit'/></a> <a href="${ctx}/tomes/editIntention.html?print=${intention.id}"><fmt:message key='button.print'/></a> <a href="${ctx}/tomes/intentionTome.html?delete=${intention.id}&date=${param.date}" onclick="return deleteConfirm();"><fmt:message key='button.delete'/></a>
    </display:column>
</display:table>

<div style="clear: both; float: left; margin-top: 10px;">
	<a href="${ctx}/tomes/editIntention.html?edit=true&date=${param.date}">
	    <input type="button" name="add" class="wide-button"
	    	value="<fmt:message key="tomes.intentionsList.table.add-new"/>" />
    </a>
</div>

<script type="text/javascript">
	function deleteConfirm() {
		return confirm("<fmt:message key='warning.confirm-delete'/>")
	}
</script>