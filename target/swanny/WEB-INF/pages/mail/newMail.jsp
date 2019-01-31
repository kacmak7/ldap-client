<%@ include file="/common/taglibs.jsp"%>

<head>
    <title><fmt:message key="mail.new.title"/></title>
    <meta name="menu" content="MailMenu"/>
    
    <script type="text/javascript" src="${ctx}/scripts/calendar/calendar.js"></script>
    <script type="text/javascript" src="${ctx}/scripts/calendar/calendar-setup.js"></script>
    <script type="text/javascript" src="${ctx}/scripts/calendar/lang/calendar-pl-utf8.js"></script>
    
    <script type="text/javascript" src="${ctx}/dwr/interface/AddresseeManager.js"></script>
	<script type="text/javascript" src="${ctx}/dwr/engine.js"></script>
	<script type="text/javascript" src="${ctx}/dwr/util.js"></script>
	<script type="text/javascript" src="${ctx}/scripts/pages/newMail.js"></script>
</head>

<p><spring:message code="mail.new.message" arguments="${requestScope.mail.path}"/></p>

<div style="width: 556px;">
	<spring:bind path="mail.*">
	    <c:if test="${not empty status.errorMessages}">
	    <div class="error">
	        <c:forEach var="error" items="${status.errorMessages}">
	            <img src="<c:url value="/images/iconWarning.gif"/>"
	                alt="<fmt:message key="icon.warning"/>" class="icon" />
	            <c:out value="${error}" escapeXml="false"/><br />
	        </c:forEach>
	    </div>
	    </c:if>
	</spring:bind>
	
	<div class="separator"></div>
	
	<!--
	    The most important part is to declare your form's enctype to be "multipart/form-data"
	-->
	
	<form:form commandName="mail" method="post" action="fileupload.html" enctype="multipart/form-data"
	    onsubmit="return validateMail(this)" id="newMail">
	    <input type="hidden" name="parentDir" value="${requestScope.mail.path}"/>
	    <c:if test="${not empty requestScope.mail.id}">
	    	<input type="hidden" name="mailId" value="${requestScope.mail.id}"/>
	    </c:if>
	<ul>
	    <li class="info">
	        <fmt:message key="mail.new.maxLength"/>
	    </li>
	    
	    <li>
	    	<div style="float: left; margin-right: 20px">
		        <appfuse:label styleClass="desc" key="mail.number"/>
		        <form:errors path="number" cssClass="fieldError"/>
		        <form:input path="number" id="number" cssClass="text medium" cssErrorClass="text medium error" maxlength="50"/>
		    </div>
		    <div style="float: left; clear: right;">
		    	<appfuse:label styleClass="desc" key="mail.register-date"/>
		        <form:errors path="registerDate" cssClass="fieldError"/>
		        <form:input path="registerDate" id="registerDate" cssClass="text datetime" cssErrorClass="text datetime error" maxlength="19"/>
		        <input type="button" id="dateCal" type="button" class="dots" value="<fmt:message key='dots'/>"/>
		    </div>
	    </li>
		<li>
			<div style="float: left; margin-right: 20px; clear: both;">
				<appfuse:label styleClass="desc" key="mail.addressee.first-letter"/>
				<select id="letter" onchange="javascript:changeLetter(this.value);">
					<option value="0"><fmt:message key="label.choose"/></option>
					<c:forEach items="${requestScope.letters}" var="letter">
						<option>${letter}</option>
					</c:forEach>
				</select>
			</div>	    		
			
			<div style="float: left; clear: right;">
				<appfuse:label styleClass="desc" key="mail.receiver"/>
				<form:errors path="receiver" cssClass="fieldError"/>
				<form:select path="receiver" cssClass="text medium" cssErrorClass="text medium error" id="addressees"/>
			</div>
		</li>
		<li></li>
	    <li>
	    	<div style="clear: both; float: left;">
		        <appfuse:label key="mail.keywords" styleClass="desc"/>
		        <form:errors path="keyWords" cssClass="fieldError"/>
		        <form:textarea path="keyWords" id="keyWords" cssClass="text large" cssErrorClass="text state error"/>
	        </div>
	    </li>
    
    	<li>
    		<div style="clear: both; float: left;">
		        <appfuse:label key="mail.description-name" styleClass="desc"/>
		        <form:errors path="descriptionName" cssClass="fieldError"/>
		        <form:input path="descriptionName" id="descriptionName" cssClass="text medium" cssErrorClass="text medium error"/>
	        </div>
	    </li>
	    
	    <li>
	    	<div style="clear: both; float: left;">
		        <fieldset>
	  				<legend><fmt:message key="mail.files"/></legend>
	    			<table border="1" class="table" id="filesTable" style="width: 600px; <c:if test="${empty requestScope.mail.files}">display: none;</c:if>">
						<thead>
							<th style="width: 300px;"><fmt:message key="mail.file.name"/></th>
							<th style="width: 140px;"><fmt:message key="mail.file.length"/></th>
							<th style="width: 180px;"><fmt:message key="label.actions"/></th>
						</thead>
						
						<tbody id="filesbody">
							<tr id="patternFile" style="display:none;">
								<td>
									<span id="tableFileName"></span>
									<input type="hidden" id="tableFileId"/>
									<input type="hidden" id="tableHiddenFileName"/>
								</td>
								<td>
									<span id="tableFileLength"></span>
								</td>
								<td>
									<input id="download" type="button" value="<fmt:message key='button.download'/>" onclick="downloadClicked(this.id);"/>
									<input id="delete" type="button" value="<fmt:message key='button.delete'/>" onclick="deleteFileClicked(this.id)"/>
								</td>
							</tr>
						</tbody>
						<input type="hidden" id="deletedFilesIds" name="deletedFilesIds" value=""/>
					</table>
					
					<table class="plain">
						<th style="text-align: left;"><fmt:message key="mail.add-file"/></th>
						<tr id="newFiles">
							<td>
								<input id="fileData" type="file" onchange="javascript:writeFile();"/>
								<input id="fileName" type="hidden"/>
							</td>
						</tr>
					</table>
				</fieldset>
		    </div>
	    </li>
	    <li class="buttonBar bottom">
	    	<div style="clear: both; float: left; margin-top: 10px;">
		        <input type="submit" name="upload" class="button" onclick="bCancel=false"
		            value="<fmt:message key="button.upload"/>" />
		        <input type="button" name="cancel" class="button" onclick="javascript:window.close();"
		            value="<fmt:message key="button.cancel"/>" />
		    </div>
	    </li>
	</ul>
	</form:form>
</div>

<script type="text/javascript">
    var labelChoose = '<fmt:message key="label.choose"/>'

    function deleteFile() {
   		//GraveyardManager.removePhoto('${requestScope.photoUrl}', ${graveId}, showSuccessMessage);
    }

    function showSuccessMessage(value) {
        window.location.reload();
        if (value)
            alert('<fmt:message key="photo.deleted"/>')
        else
        	alert('<fmt:message key="errors.deleting.grave-photo"/>')
    }

    function errorHandler(msg) {
    	alert(msg);
    }

	dwr.engine.setErrorHandler(errorHandler)

    var filesArray = []
    <c:forEach items="${requestScope.mail.files}" var="file" varStatus="gridRow">
		filesArray[${gridRow.index}] = { jsId:${gridRow.index}+1, id:${file.id}, fileName:'${file.originalName}', length:'${file.size}', contentType:'${file.contentType}' }
	</c:forEach>

	var ctx = '${ctx}'

	function fillFiles() {
		//alert('fillFiles' + filesArray.length)
		// Delete all the rows except for the "pattern" row
		dwr.util.removeAllRows("filesbody", { filter:function(tr) {
			return (tr.id != "patternFile");
		}});
		// Create a new set cloned from the pattern row
		var file, id;
		for (var i = 0; i < filesArray.length; i++) {
			file = filesArray[i];
			id = file.jsId;
			
			dwr.util.cloneNode("patternFile", { idSuffix:id });
			
			dwr.util.setValue("tableHiddenFileName" + id, file.fileName);
			$("tableHiddenFileName" + id).setAttribute("Name", 'fileName' + id);
			$("tableHiddenFileName" + id).name='fileName' + id
			
			if (typeof file.id != 'undefined' && file.id != null) {
				dwr.util.setValue("tableFileId" + id, file.id);
				
				$("tableFileId" + id).setAttribute("Name", "files["+i+"].id");
				$("tableFileId" + id).name="files["+i+"].id"
			}

			if (file.fileName != null)
				dwr.util.setValue("tableFileName" + id, file.fileName);
			
			if (file.length != null)
				dwr.util.setValue("tableFileLength" + id, file.length);
			else
				dwr.util.setValue("tableFileLength" + id, 'nieznana');
			$("patternFile" + id).style.display = ""; // officially we should use table-row, but IE prefers "" for some reason
		}
	}

	function downloadClicked(elementid) {
		id = elementid.substring(8)
		<c:url var="downloadUrl" value="/filedownload"/>
		//alert('${downloadUrl}/'+dwr.util.getValue("originalName" + id)+'?id='+id)
		window.location='${downloadUrl}/'+dwr.util.getValue("tableFileName" + id)+'?id='+dwr.util.getValue("tableFileId" + id)
		//window.location='${downloadUrl}?id='+id
	}

	function setAddressee(id, surname) {
		var letter = surname.substring(0,1)
		changeLetter(letter)
	}
	
	function setReceiver() {
		dwr.util.setValue("receiver", '${requestScope.mail.receiver.id}');
	}
	
	function deleteFileClicked(fileid) {
		// we were an id of the form "delete{id}", eg "delete42". We lookup the "42"
		file = filesArray[getFileLocation(fileid.substring(6))]
		
		if (confirm("Czy jestes pewien, zeby usunac " + file.fileName + "?")) {
			filesArray.splice(getFileLocation(fileid.substring(6)), 1)
			$('deletedFilesIds').value += file.id + '_'					//wartosc pola z usuwanymi plikami bedzie przedzielona znakiem _
		}
		fillFiles()
	}
	
	<c:if test="${not empty requestScope.mail.id}">
		fillFiles()
		<c:choose>
			<c:when test="${not empty requestScope.mail.receiver.lastName}">
				setAddressee(${requestScope.mail.receiver.id}, '${requestScope.mail.receiver.lastName}')
			</c:when>
			<c:otherwise>
				setAddressee(${requestScope.mail.receiver.id}, '${requestScope.mail.receiver.institution}')
			</c:otherwise>
		</c:choose>
	</c:if>
	
	Calendar.setup({
	    inputField  : "registerDate",      // id of the input field
	    ifFormat    : "%d-%m-%Y %H:%M:%S",      // the date format
	    button      : "dateCal"    // id of the button
	});
</script>
<v:javascript formName="mail" staticJavascript="false"/>
<script type="text/javascript" src="<c:url value="/scripts/validator.jsp"/>"></script>