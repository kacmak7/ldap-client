var viewedFile = -1

function getNextId(array) {
	var id = 0
	for (var i = 0; i < array.length; i++) {
		element = array[i]
		if (element.jsId > id)
			id = element.jsId
	}
	return ++id
}

function changeLetter(value) {
	dwr.util.setValue("letter", value);
    AddresseeManager.getAddresseesWithFirstLetter(value, populateAddressees)
}

function populateAddressees(addressees) {
	dwr.util.removeAllOptions("addressees")
	addressees.sort(function(p1, p2) { return p1.lastName.localeCompare(p2.lastName); });
	//var representantChoose = {id:0, name:'<fmt:message key="label.choose"/>'}
	for (var i = addressees.length; i > 0; i--) {
		addressees[i] = addressees[i-1]
	}
	addressees[0] = {id:0, name:labelChoose}
	dwr.util.addOptions("addressees", addressees, "id", "fullName")
	setReceiver()
}

function clearFile() {
	viewedFile = -1;
	dwr.util.setValues({ fileData:null });
}

function writeFile() {
	var file = { jsId:viewedFile, fileData:null };
	dwr.util.getValues(file);
	if (file.jsId < 0) {
		file.jsId = getNextId(filesArray)
		file.id = null
		filesArray[filesArray.length] = file
	} else {
		file.id = filesArray[getFileLocation(file.jsId)].id
		filesArray[getFileLocation(file.jsId)] = file
	}
	
	//alert(file.jsId + ' ' + file.fileData)
	dwr.util.cloneNode("newFiles", { idSuffix:file.jsId });
	
	// zamiana - trzeba zrobic z uwagi na to, ze IE klonuje input type file bez zawartosci
	var fileDataId = $("fileData" + file.jsId)
	var fileNameId = $("fileName" + file.jsId)
	var newFilesId = $("newFiles" + file.jsId)
	var fileData = $("fileData")
	var fileName = $("fileName")
	var newFiles = $("newFiles")
	
	fileDataId.setAttribute("Id", "fileData");
	fileDataId.id="fileData"
	fileNameId.setAttribute("Id", "fileName");
	fileNameId.id="fileName"
	newFilesId.setAttribute("Id", "newFiles");
	newFilesId.id="newFiles"
	
	fileData.setAttribute("Id", "fileData" + file.jsId);
	fileData.id="fileData" + file.jsId
	fileName.setAttribute("Id", "fileName" + file.jsId);
	fileName.id="fileName" + file.jsId
	
	newFiles.setAttribute("Id", "newFiles" + file.jsId);
	newFiles.id="newFiles" + file.jsId
	
	// koniec zamiany
	
	var slash = file.fileData.indexOf('/');
	var bslash = file.fileData.indexOf('\\');
	var fileSep;
	if (bslash == -1)
		fileSep = '/';
	else if (slash == -1)
		fileSep = '\\';
	else if (slash < bslash)
		fileSep = '/';
	else
		fileSep = '\\';
	//alert("file.fileData=" + file.fileData)
	var fileDataArray = file.fileData.split(fileSep)
	file.fileName = fileDataArray[fileDataArray.length - 1]
	//alert(file.fileName)
	                              
	$("fileData" + file.jsId).setAttribute("Name", "fileData" + file.jsId);
	$("fileName" + file.jsId).setAttribute("Name", "fileName" + file.jsId);
	$("fileName" + file.jsId).value = file.fileName;
	
	$("fileData" + file.jsId).name="fileData" + file.jsId
	$("fileName" + file.jsId).name="fileName" + file.jsId

	//alert($("fileData" + file.jsId).name + " " + $("fileData" + file.jsId).value)
	//alert($("fileName" + file.jsId).name + " " + $("fileName" + file.jsId).value)
	
	$("filesTable").style.display = "";
	$("newFiles" + file.jsId).style.display = "none";
	
	fillFiles()
	clearFile()
}

function getFileLocation(jsId) {
	for (var i = 0; i < filesArray.length; i++) {
		file = filesArray[i]
		if (file.jsId == jsId)
			return i
	}
	return -1
}