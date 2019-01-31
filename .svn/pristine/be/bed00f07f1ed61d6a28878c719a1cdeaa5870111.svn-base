<%@ include file="/common/taglibs.jsp"%>

<head>
    <title><fmt:message key="addresseesList.title"/></title>
    <meta name="menu" content="MailMenu"/>
</head>

<c:url var="addUrl" value="/mail/addresseeEdit.html"/>
<display:table name="addresseesList" cellspacing="0" cellpadding="0" requestURI="" 
    defaultsort="1" id="addressees" pagesize="25" class="table" export="false" decorator="org.parafia.webapp.util.AddresseeLinkDecorator">
    <display:column property="fullName" sortable="true" titleKey="mail.addressee.name"/>
    <display:column property="institution" sortable="true" titleKey="mail.addressee.institution"/>
    <display:column property="street" sortable="true" titleKey="mail.addressee.street"/>
    <display:column property="post" sortable="true" titleKey="mail.addressee.post"/>
    <display:column property="country" sortable="true" titleKey="mail.addressee.country"/>
</display:table>

<div style="margin-top: 10px;">
	<input type="button" value='<fmt:message key="button.add"/>' onclick="javascript:showEdit();"/>
</div>

<script type="text/javascript">
    highlightTableRows("addressees");

    function showEdit(id) {
    	<c:url var="addUrl" value="/mail/addresseeEdit.html"/>
    	window.open( "${addUrl}"+(typeof(id) == "undefined"?"":"?id="+id), "Dodaj adresata", 
    		"status = 1, height = 500, width = 500, resizable = 0" )
    }
</script>