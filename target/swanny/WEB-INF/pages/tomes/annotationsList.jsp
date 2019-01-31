<%@ include file="/common/taglibs.jsp"%>

<head>
    <title><fmt:message key="print.intention-tome"/></title>
    <meta name="heading" content="<fmt:message key='menu.print.intention-tome'/>"/>
    <meta name="menu" content="PrintMenu"/> 
</head>

<display:table name="annotations" cellspacing="0" cellpadding="0" requestURI="" defaultsort="1"
		id="annotation" pagesize="25" class="table" export="false" style="margin-bottom: 10px; width: auto;" >
	<display:column property="annotation" escapeXml="true" sortable="true" titleKey="tomes.annotationsList.table.annotation" style="width: auto;"
        url="/tomes/editIntention.html?edit=true&allDay=true&date=${annotation.date.time}" paramId="id" paramProperty="id" media="html"/>
	<display:column titleKey="tomes.annotationsList.table.actions" media="html" style="width: 20%">
    	<a href="${ctx}/tomes/editIntention.html?edit=true&allDay=true&date=${annotation.date.time}&id=${annotation.id}">
    	<fmt:message key='button.edit'/></a> <a href="${ctx}/tomes/intentionTome.html?allDay=true&delete=${annotation.id}&date=${param.date}" onclick="return deleteConfirm();">
    	<fmt:message key='button.delete'/></a>
    </display:column>
</display:table>

<div style="clear: both; float: left; margin-top: 10px;">
	<a href="${ctx}/tomes/editIntention.html?edit=true&allDay=true&date=${param.date}">
	    <input type="button" name="add" class="wide-button"
	    	value="<fmt:message key="tomes.annotationsList.table.add-new"/>" />
    </a>
</div>

<script type="text/javascript">
	function deleteConfirm() {
		return confirm("<fmt:message key='warning.confirm-delete'/>")
	}
</script>