<%@ include file="/common/taglibs.jsp"%>

<head>
    <title><fmt:message key="fiancees.title"/></title>
    <meta name="heading" content="<fmt:message key='fiancees.heading'/>"/>
    <meta name="menu" content="PrintMenu"/>
</head>

<c:set var="buttons">
    <input type="button" style="margin-right: 5px;"
        onclick="location.href='<c:url value="/print/fianceesform.html?method=Add&from=list"/>'"
        value="<fmt:message key="button.add"/>"/>

    <input type="button" onclick="location.href='<c:url value="/print/fiancees.html"/>'"
        value="<fmt:message key="button.done"/>"/>
</c:set>

<c:out value="${buttons}" escapeXml="false" />

<display:table name="fianceesList" cellspacing="0" cellpadding="0" requestURI="" 
    defaultsort="1" id="fiancees" pagesize="25" class="table" export="false" style="margin-bottom: 10px;">
    <display:column property="fianceeHe.fullNameLastNameFirst" escapeXml="true" sortable="true" titleKey="print.fianceehe.fullname"
        url="/print/fianceesform.html?from=list" paramId="fianceePairId" paramProperty="id"/>
    <display:column property="fianceeShe.fullNameLastNameFirst" escapeXml="true" sortable="true" titleKey="print.fianceeshe.fullname"
        url="/print/fianceesform.html?from=list" paramId="fianceePairId" paramProperty="id"/>
    <display:column property="fullNumber" escapeXml="true" sortable="true" titleKey="print.fiancees.protocole-number"
        url="/print/fianceesform.html?from=list" paramId="fianceePairId" paramProperty="id"/>
</display:table>

<c:out value="${buttons}" escapeXml="false" />

<script type="text/javascript">
    highlightTableRows("familiesList");
</script>
