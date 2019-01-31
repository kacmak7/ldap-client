<%@ include file="/common/taglibs.jsp"%>

<head>
    <title><fmt:message key="menu.admin"/></title>
    <meta name="heading" content="<fmt:message key='menu.admin'/>"/>
    <meta name="menu" content="AdminMenu"/>
</head>

<p><fmt:message key="admin.message"/></p>

<div class="separator"></div>

<ul class="glassList">
    <li>
        <a href="<c:url value='/admin/users.html'/>"><fmt:message key="menu.admin.users"/></a>
    </li>
    <li>
        <a href="<c:url value='/admin/activeUsers.html'/>"><fmt:message key="menu.admin.activeUsers"/></a>
    </li>
</ul>
