<%@ include file="/common/taglibs.jsp"%>

<head>
    <title><fmt:message key="mail.upload.title"/></title>
    <meta name="heading" content="<fmt:message key='mail.upload.message'/>"/>
    <meta name="menu" content="MailMenu"/>
</head>

<div class="separator"></div>

<table class="detail" cellpadding="5" style="text-align: left;">
    <tr>
        <th><fmt:message key="mail.number"/></th>
        <td>${requestScope.file.number}</td>
    </tr>
    <tr>
        <th><fmt:message key="mail.register-date"/></th>
        <td>${requestScope.file.registerDate}</td>
    </tr>
    <tr>
        <th><fmt:message key="mail.upload.filename"/></th>
        <td>${requestScope.file.originalName}</td>
    </tr>
    <tr>
        <th><fmt:message key="mail.receiver"/></th>
        <td>${requestScope.file.receiver.fullName}</td>
    </tr>
	<tr>
        <th><fmt:message key="mail.keywords"/></th>
        <td>${requestScope.file.keyWords}</td>
    </tr>
        <th><fmt:message key="mail.description-name"/></th>
        <td>${requestScope.file.descriptionName}</td>
    </tr>
    <tr>
        <td class="buttonBar" colspan="2">
        	<c:url var="editUrl" value="/mail/editMail.html">
        		<c:param name="mailId" value="${requestScope.file.id}"/>
        	</c:url>
    		<a href="${editUrl}"><input type="button" name="edit" id="edit" value="<fmt:message key='button.edit'/>"/></a>
        	<c:url var="mailMenu" value="/mail/viewMails.html"/>
    		<input type="button" name="done" id="done" value="<fmt:message key='button.done'/>"
                onclick="javascript:window.close();" />
        </td>
    </tr>
</table>


