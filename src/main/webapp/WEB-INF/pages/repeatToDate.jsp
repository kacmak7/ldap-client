<%@ include file="/common/taglibs.jsp"%>
<fieldset class="pickList">
   	<legend><fmt:message key="tomes.editIntentionAnnotation.repeat"/></legend>
	<div style="float: left;">
    	<appfuse:label styleClass="desc" key="tomes.editIntentionAnnotation.repeat"/>		        
		<input type="radio" name="repeat" value="day"> <fmt:message key="tomes.editIntentionAnnotation.repeat-everyday"/></input><br>
		<input type="radio" name="repeat" value="week"> <fmt:message key="tomes.editIntentionAnnotation.repeat-everyweek"/></input><br>
		<input type="radio" name="repeat" value="month"> <fmt:message key="tomes.editIntentionAnnotation.repeat-everymonth"/></input>
	</div>
	<div style="float: left; margin-left: 30px;">
    	<appfuse:label styleClass="desc" key="tomes.editIntentionAnnotation.todate"/>
    	<form:errors path="toDate" cssClass="fieldError"/>
    	<form:input path="toDate" id="toDate" cssClass="text" cssErrorClass="text error" maxlength="11" size="11"/> <input type="button" id="dateCal" type="button" class="dots" value="<fmt:message key='dots'/>"/>
	</div>
</fieldset>