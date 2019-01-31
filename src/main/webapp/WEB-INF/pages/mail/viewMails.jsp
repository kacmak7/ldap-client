<%@ include file="/common/taglibs.jsp"%>

<head>
    <title><fmt:message key="mail.list.title"/></title>
    <meta name="heading" content="<fmt:message key='mail.list.heading'/>"/>
    <meta name="menu" content="MailMenu"/>
    
    <script type="text/javascript" src="${ctx}/scripts/calendar/calendar.js"></script>
    <script type="text/javascript" src="${ctx}/scripts/calendar/calendar-setup.js"></script>
    <script type="text/javascript" src="${ctx}/scripts/calendar/lang/calendar-pl-utf8.js"></script>
    
    <link rel="stylesheet" type="text/css" href="<c:url value='/styles/zapatec/layout/basic.css'/>"/>
    
    <!-- Javascript Zapatec utilities file -->    
	<script type="text/javascript" src="<c:url value='/scripts/zapatec/utils/zapatec.js'/>"></script>
	<!-- Javascript file to support Tree -->
	<script type="text/javascript" src="<c:url value='/scripts/zapatec/zptree/src/tree.js'/>"></script>
	<script type="text/javascript" src="<c:url value='/scripts/zapatec/zpmenu/src/zpmenu.js'/>"></script>
	
	<link rel="stylesheet" type="text/css" href="<c:url value='/styles/zapatec/kde1.css'/>"/>
	
	<!-- <link rel="stylesheet" href="<c:url value='/scripts/zapatec/website/css/zpcal.css'/>" type="text/css"/>
	<link rel="stylesheet" href="<c:url value='/scripts/zapatec/website/css/template.css'/>" type="text/css"/> -->
	
	<script type="text/javascript" src="${ctx}/dwr/interface/FileManager.js"></script>
	<script type="text/javascript" src="${ctx}/dwr/engine.js"></script>
	<script type="text/javascript" src="${ctx}/dwr/util.js"></script>
	<script type="text/javascript" src="${ctx}/scripts/aa.js"></script>
</head>
<body id="activeUsers"/>

<div class="separator"></div>

<div style="margin-bottom: 40px;">
	<div style="float: left;">
		<select name="year" onchange="javascript:window.location='<c:url value="/mail/viewMails.html"></c:url>?year='+this.value;">
			<c:forEach items="${requestScope.yearList}" var="file">
				<option value="${file.name}" <c:if test="${file.name == requestScope.year}">selected="selected"</c:if>>${file.name}</option>
			</c:forEach>
		</select> <a href='<c:url value="/mail/viewMails.html"><c:param name="lastten" value="true"/></c:url>'><input type="button" name="tenmails" value="<fmt:message key='button.tenmails'/>"/></a>
		
		<!-- <a href='<c:url value="/mail/viewMails.html"><c:param name="year" value="${file.name}"/></c:url>'>${file.name}</a> -->
	</div>
	<div style="float: right;margin-bottom: 10px;">
		<input type="text" id="findText"/> <input type="button" value='<fmt:message key="button.search"/>' onclick="javascript:find(document.getElementById('findText').value)"/> <fmt:message key="mail.list.find"/>
		<input type="button" value='<fmt:message key="button.more"/>' onclick="javascript:document.getElementById('moreDiv').style.display='block';"/>
		<div id="moreDiv" style="display: none; text-align: right; margin-top: 7px;">
			<fmt:message key="from-date"/> <input type="text" id="fromDate" size="11"/> <input type="button" id="fromDateCal" type="button" class="dots" value="<fmt:message key='dots'/>"/>
			<fmt:message key="to-date"/> <input type="text" id="toDate" size="11"/> <input type="button" id="toDateCal" type="button" class="dots" value="<fmt:message key='dots'/>"/>
		</div>
	</div>
</div>

<div style="border: 2px solid #74b3dc; float: left; width: 250px; padding: 5px;">
	<ul id="tree">
	</ul>
	
	<ul style="display: none" id="contextMenu">
		<li><a href="javascript:moveDir(contextMenu.triggerObject);"><fmt:message key="mail.list.move"/></a></li>
		<li><a href="javascript:removeDir(contextMenu.triggerObject);"><fmt:message key="mail.list.delete"/></a></li>
		<li><a href="javascript:addSubDir(contextMenu.triggerObject);"><fmt:message key="mail.list.add-subdir"/></a></li>
		<li><a href="javascript:addDir(contextMenu.triggerObject);"><fmt:message key="mail.list.add-dir"/></a></li>
		<li><a href="javascript:uploadFile(contextMenu.triggerObject);"><fmt:message key="mail.list.upload-file"/></a></li>
	</ul>
</div>

<div style="border: 1px solid; float: right; width: 620px;"">
	<table class="table" id="filesTable">
		<thead>
			<tr>
				<th style="width: 280px; width: 160px;"><fmt:message key="mail.list.number"/></th>
				<th style="width: 280px;"><fmt:message key="mail.list.receiver"/></th>
				<th style="width: 280px;"><fmt:message key="mail.keywords"/></th>
				<th style="width: 280px;"><fmt:message key="mail.description-name"/></th>
				<th style="width: 280px;"><fmt:message key="mail.list.actions"/></th>
			</tr>
		</thead>
		<tbody id="filesbody">
			<tr id="pattern" style="display:none;">
				<td><a onclick="javascript:viewAppendix(this.id);" id="number" href="#">Number</a></td>
				<td><a onclick="javascript:viewAppendix(this.id);" id="receiver" href="#">Receiver</a></td>
				<td><a onclick="javascript:viewAppendix(this.id);" id="keyWords" href="#">KeyWords</a></td>
				<td><a onclick="javascript:viewAppendix(this.id);" id="descriptionName" href="#">DescriptionName</a><input type="hidden" id="path" /></td>
				<td>
					<input id="move" type="button" value="<fmt:message key='button.move'/>" onclick="moveClicked(this.id, document.getElementById(this.id.replace('move','path')).value);"/>
					<input id="delete" type="button" value="<fmt:message key='button.delete'/>" onclick="deleteClicked(this.id);"/>
				</td>
			</tr>
		</tbody>
    </table>
</div>

<div id="navDiv" style="float: right; width: 600px; margin-top: 10px; display: none;">
</div>

<script type="text/javascript" charset="utf-8">
var currentId = -1
var name
var fileName
var filesArray = []

function uploadFile(parentDir) {
	<c:url value="/mail/newMail.html" var="url"/>
	window.open( '${url}?parentDir='+encodeURIComponent(parentDir), "Dodaj plik", 
		"status = 1, height = 700, width = 800, resizable = 1" )
}

if(Zapatec.Menu){
	var contextMenu = new Zapatec.Menu({
		source: 'contextMenu',
		vertical: true,
		zIndex: 10,
		theme: 'office_blue',
		dropShadow: 50,
		triggerEvent: "no_event" // hack. To hide menu on document clicks outside menu.
	});

	contextMenu.hideMenu(); // hide menu after creating
}

function find(text) {
	fromDate = document.getElementById('fromDate').value
	toDate = document.getElementById('toDate').value
	if (fromDate != "" && !isDate(fromDate))
		alert('Data od jest nieprawidlowa')
	if (toDate != "" && !isDate(toDate))
		alert('Data do jest nieprawidlowa')
		
	FileManager.findFiles(text, fromDate, toDate, fillFiles);
}


function y2k(number) { return (number < 1000) ? number + 1900 : number; }

function isDate (date) {
// checks if date passed is valid
// will accept dates in following format:
// isDate(dd,mm,ccyy), or
// isDate(dd,mm) - which defaults to the current year, or
// isDate(dd) - which defaults to the current month and year.
// Note, if passed the month must be between 1 and 12, and the
// year in ccyy format.
	var arr = date.split("-")
	if (arr.length != 3) {
		return false;
	} else {
		day=arr[0];
		month=arr[1];
		year=arr[2];
	}

    var today = new Date();
    year = ((!year) ? y2k(today.getYear()):year);
    month = ((!month) ? today.getMonth():month-1);
    if (!day) return false
    var test = new Date(year,month,day);
    if ( (y2k(test.getYear()) == year) &&
         (month == test.getMonth()) &&
         (day == test.getDate()) )
        return true;
    else
        return false
}

function viewAppendix(id) {
	//alert(id)
	myregexp = /[^\d]+/
	mailId = id.replace(myregexp, "")
	<c:url value="/mail/newMail.html" var="url"/>
	window.open( '${url}?mailId='+mailId, "Przejrzyj korespondencje", 
		"status = 1, height = 700, width = 800, resizable = 1" )
}

function showContextMenuAt(x, y){
	if(!contextMenu){
		return false;
	}

	// show menu in 0.1 second - without this delay standard context menu won't be canceled
	setTimeout(function(){contextMenu.popupMenu(x, y);}, 100);
}

function onRightClick(ev){
	var coords = Zapatec.Utils.getMousePos(ev);
	currentId = this.id
	contextMenu.triggerObject = this.data.name
	showContextMenuAt(coords.pageX - 180, coords.pageY - 150);
}

var tree = new Zapatec.Tree({
	tree: "tree",
	defaultIcons: "customIcon",
	expandOnLabelClick: true,
	highlightSelectedNode: true,
	disableContextMenu: true,
	eventListeners: {
		'labelRightClick': onRightClick,
		'iconRightClick': onRightClick,
        'select': select
	},
	saveState: true,
	saveId: "saveStateZapatec"
});

function select(ev) {
	currentId = this.id
	FileManager.getFilesList(this.data.name, fillFiles);
}

function fillFiles(files, page) {
	if (files != null)
		filesArray = files

	if(typeof(page)=="undefined")
		page = 1
	number = 5
	// Delete all the rows except for the "pattern" row
	dwr.util.removeAllRows("filesbody", { filter:function(tr) {
		return (tr.id != "pattern");
	}});
	// Create a new set cloned from the pattern row
	if (filesArray != null && filesArray.length > 0) {
		var file, id;
		//filesArray.sort(function(p1, p2) { return p1.number.localeCompare(p2.number); });

		if (filesArray.size() > number) {
			document.getElementById('navDiv').style.display='block';
			nums = ''
			for (var i = 1; i <= Math.ceil(filesArray.length / number); i++) {
				nums += '<a href="javascript:fillFiles(null, '+i+')">' + i + '</a> '
				var maxPage = i
			}

			if (page == 1)
				back = '[<< / <] '
			else
				back = '[<a href="javascript:fillFiles(null, 1);"><<</a> / <a href="javascript:fillFiles(null, '+(page-1)+');"><</a>] '
			if (page == maxPage)
				forward = ' [> / >>]'
			else
				forward = '[<a href="javascript:fillFiles(null, '+(page+1)+');">></a> / <a href="javascript:fillFiles(null, '+maxPage+');">>></a>] '
			
			document.getElementById('navDiv').innerHTML=back + nums + forward
		} else {
			document.getElementById('navDiv').style.display='none';
		}

		displayFiles = getDisplayFiles(filesArray, page, number)
				
		for (var i = 0; i < displayFiles.length; i++) {
			file = displayFiles[i];
			id = file.id;
			dwr.util.cloneNode("pattern", { idSuffix:id });
			dwr.util.setValue("number" + id, file.number);
			dwr.util.setValue("receiver" + id, file.receiver.fullName);
			dwr.util.setValue("keyWords" + id, file.keyWords);
			dwr.util.setValue("descriptionName" + id, file.descriptionName);
			dwr.util.setValue("path" + id, file.path);
			$("pattern" + id).style.display = ""; // officially we should use table-row, but IE prefers "" for some reason
		}
	} else {
		document.getElementById('navDiv').style.display='none';
	}
}

function getDisplayFiles(files, page, number) {
	var i = 0
	var displayFiles = []
	for (var z = (page-1)*number; z < Math.min(page*number, files.length); z++) {
		displayFiles[i++] = files[z]
	}
	return displayFiles
}

function deleteClicked(elementid) {
	id = elementid.substring(6)
	
	if (confirm("Czy na pewno usunac korespondencje?")) {
		FileManager.removeMail(id, showRemoveFileMessage);
	}
}

function moveClicked(elementid, currentDir) {
	dwr.engine.setErrorHandler(errorHandler);
	id = elementid.substring(4)

	name = prompt("Podaj nowy katalog dla korespondencji. Dotychczasowy to " + currentDir, currentDir);
	if (name != null) {
	    FileManager.moveFile(name, id, showMoveFileMessage);
	}
}

function errorHandler(msg) {
	alert(msg);
}

function removeDir(file) {
	dwr.engine.setErrorHandler(errorHandler);

    fileName = file

	if (confirm("Czy na pewno usunac katalog "+fileName+" wraz z wszystkimi podkatalogami i plikami?")) {
		FileManager.removeDir(fileName, showRemoveMessage);	
	}
}

function moveDir(dir) {
	dwr.engine.setErrorHandler(errorHandler);
	fileName = dir

	name = prompt("Podaj nowa sciezke katalogu " + fileName, fileName);
	if (name != null) {
	    FileManager.moveDir(fileName, name, showMoveMessage);
	}
}

function addSubDir(parentDir) {
	dwr.engine.setErrorHandler(errorHandler);

	fileName = parentDir

	name = prompt("Podaj nazwe podkatalogu umieszczonego w " + fileName);
	if (name != null) {
	    FileManager.addSubDir(fileName + "/" + name, showAddSubDirMessage);
	}
}

function addDir(dir) {
	dwr.engine.setErrorHandler(errorHandler);

	fileName = dir

	name = prompt("Podaj nazwe katalogu rownoleglego do " + fileName);
	if (name != null) {
	    FileManager.addDir(fileName, name, showAddDirMessage);
	}
}

function showRemoveMessage(deleted) {
	alert('showRemoveMessage')
	if (deleted) {
		alert('<fmt:message key="mail.dir.was-deleted"/>');
		tree.removeChild(currentId)
	} else
		alert('<fmt:message key="mail.dir.was-not-deleted"/>');
	currentId = -1
}

function showRemoveFileMessage(deleted) {
	if (deleted) {
		alert('<fmt:message key="mail.mail.was-deleted"/>');
	} else
		alert('<fmt:message key="mail.mail.was-not-deleted"/>');
	window.location.reload()
	currentId = -1
}

function showAddSubDirMessage(added) {
	if (added) {
		alert('<fmt:message key="mail.dir.was-added"/>')
		//alert('Katalog ' + fileName + '${requestScope.fileSeparator}' + name + ' <fmt:message key="mail.list.was-added"/>')
		var json = '{"label": "' + name + '","name": "' + fileName + '/' + name + '","sourceType": "json/url","source": "/swanny/mail/getJSON?filePath='+ fileName + '/' + name + '"}'
	    tree.appendChild(json, currentId)
	} else
		alert('<fmt:message key="mail.dir.was-not-added"/>')
		//alert('Katalog ' + fileName + ' <fmt:message key="mail.list.was-not-added"/>')
	currentId = -1
}

function showAddDirMessage(added) {
	if (added) {
		alert('<fmt:message key="mail.dir.was-added"/>')
	    window.location.reload()
	} else
		alert('<fmt:message key="mail.dir.was-not-added"/>')
	currentId = -1
}

function showMoveMessage(move) {
	alert('showMoveMessage')
	if (move) {
		tree.removeChild(currentId)
		window.location.reload()
		alert('Nazwa katalogu zostala zmieniona')
	} else
		alert('Nazwa katalogu nie zostala zmieniona')
	currentId = -1
}

function showMoveFileMessage(move) {
	if (move) {
		alert('Katalog pliku zostal zmieniony');
	} else {
		alert('Katalog pliku nie zostal zmieniony');
	}
	window.location.reload();
}

<c:forEach items="${requestScope.beginJson}" var="json">
	tree.appendChild('${json}')
</c:forEach>

Calendar.setup({
    inputField  : "fromDate",      // id of the input field
    ifFormat    : "%d-%m-%Y",      // the date format
    button      : "fromDateCal"    // id of the button
});
Calendar.setup({
    inputField  : "toDate",      // id of the input field
    ifFormat    : "%d-%m-%Y",      // the date format
    button      : "toDateCal"    // id of the button
});

<c:if test="${not empty requestScope.lastten}">
	FileManager.getLastTenFilesList(null, '${requestScope.yearDir}', fillFiles);
</c:if>

</script>

<noscript>
	<br/>
	This page uses an <a href='http://www.zapatec.com/website/main/products/suite/'>
	AJAX Component</a> - Zapatec DHTML Tree Widget, but your browser does not support Javascript.
	<br/>
</noscript>