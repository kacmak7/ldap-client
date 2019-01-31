var viewedFile = -1

function initGrave() {
	fillPersons()
	fillLevels()
	fillFiles()
}

function initNiche() {
	fillPersons()
	fillFiles()
}

function writePerson() {
	var person = { personId:null, personFirstNameIn:null, personLastNameIn:null, personDeathDateIn:null };
	dwr.util.getValues(person);
	
	person.jsId = getNextId(personsArray)
	personsArray[personsArray.length] = person
	
	fillPersons()
	clearPersons()
}


function fillPersons() {
	// Delete all the rows except for the "pattern" row
	dwr.util.removeAllRows("personsbody", { filter:function(tr) {
		return (tr.id != "pattern");
	}});
	// Create a new set cloned from the pattern row
	var person, id;
	//elementsArray.sort(function(p1, p2) { return p1.elName.localeCompare(p2.elName); });
	
	var sumValue = 0;
	for (var i = 0; i < personsArray.length; i++) {
		person = personsArray[i];
		id = person.jsId;
		dwr.util.cloneNode("pattern", { idSuffix:id });
		dwr.util.setValue("personId" + id, person.personId);
		dwr.util.setValue("personFirstName" + id, person.personFirstNameIn);
		dwr.util.setValue("personLastName" + id, person.personLastNameIn);
		dwr.util.setValue("personDeathDate" + id, person.personDeathDateIn);
		
		$("#personId" + id).attr("name", "persons["+i+"].id");
		//$("personId" + id).name = "persons["+i+"].id"
		$("#personFirstName" + id).attr("name", "persons["+i+"].firstName");
		//$("personFirstName" + id).name = "persons["+i+"].firstName"
		$("#personLastName" + id).attr("name", "persons["+i+"].surname");
		//$("personLastName" + id).name = "persons["+i+"].surname"
		$("#personDeathDate" + id).attr("name", "persons["+i+"].deathDate");
		//$("personDeathDate" + id).name = "persons["+i+"].deathDate"
		
		//$("#childFirstName" + id).attr('name', "children["+(id-1)+"].firstName");
		
		//$("pattern").style.display = "";
		//$("pattern" + id).style.display = ""; // officially we should use table-row, but IE prefers "" for some reason
		$("#pattern" + id).css("display", "");
	}
}

function deletePerson(personId) {
	// we were an id of the form "delete{id}", eg "delete42". We lookup the "42"
	id = personId.substring(18) - 1;
	
	if (confirm("Na pewno usunac osobe?")) {
		personsArray.splice(id, 1)
		for (var i = 0; i < personsArray.length; i++) {
			personsArray[i].jsId = (i+1);
		}
	}
	fillPersons()
}

function clearPersons() {
	dwr.util.setValues({ personFirstNameIn:null, personLastNameIn:null, personDeathDateIn:null, });
}


function getNextId(array) {
	var id = 0
	for (var i = 0; i < array.length; i++) {
		element = array[i]
		if (element.jsId > id)
			id = element.jsId
	}
	return ++id
}

function setLevels(levelsNumber) {
	var result = levelsNumber - levelsArray.length
	if (result > 0) {	//trzeba dodac levele
		for (var i = 0; i < result; i++) {
			var level = new Object()
			level.levelId = levelsArray.length
			level.levelPlacesNumberIn = 0
			levelsArray.push(level)
		}
	} else if (result < 0) {
		for (var i = 0; i > result; i--) {
			levelsArray.pop()
		}
	}
	
	//alert(levelsArray.length)
	fillLevels()
}

function fillLevels() {
	$("#levelsNumber").val(levelsArray.length);
	var levelsDiv = document.getElementById('levelsDiv');
	var levelsDivInternal = document.getElementById('levelsDivInternal');
	levelsDiv.removeChild(levelsDivInternal);
	  
	levelsDivInternal = document.createElement('div');
	levelsDivInternal.setAttribute("id", "levelsDivInternal")
	levelsDivInternal.id = "levelsDivInternal"
	for (var i = 0; i < levelsArray.length; i++) {
		if (typeof levelsArray[i].id != 'undefined') {
			levelsDivInternal.innerHTML += '<input type="hidden" name="levels['+i+'].id" value="'+levelsArray[i].id+'"/>'
		}
		levelsDivInternal.innerHTML += '<input type="hidden" name="levels['+i+'].levelId" value="'+levelsArray[i].levelId+'"/>'
		levelsDivInternal.innerHTML += '<input type="text" maxlength="1" class="small" name="levels['+i+'].placesNumber" value="'+levelsArray[i].levelPlacesNumberIn+'"/>' 
	}
	levelsDiv.appendChild(levelsDivInternal);
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
	//var fileDataId = $("fileData" + file.jsId)
	//var fileNameId = $("fileName" + file.jsId)
	//var newFilesId = $("newFiles" + file.jsId)
	//var fileData = $("#fileData")
	//var fileName = $("#fileName")
	//var newFiles = $("#newFiles")
	
	$("#fileData" + file.jsId).attr("id", "fileData");
	//fileDataId.id="fileData"
	$("#fileName" + file.jsId).attr("id", "fileName");
	//fileNameId.id="fileName"
	$("#newFiles" + file.jsId).attr("id", "newFiles");
	//newFilesId.id="newFiles"
	
	$("#fileData").attr("id", "fileData" + file.jsId);
	//fileData.id="fileData" + file.jsId
	$("#fileName").attr("id", "fileName" + file.jsId);
	//fileName.id="fileName" + file.jsId
	
	$("#newFiles").attr("id", "newFiles" + file.jsId);
	//newFiles.id="newFiles" + file.jsId
	
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
	                              
	$("#fileData" + file.jsId).attr("name", "fileData" + file.jsId);
	$("#fileName" + file.jsId).attr("name", "fileName" + file.jsId);
	$("#fileName" + file.jsId).value = file.fileName;
	
	//$("fileData" + file.jsId).name="fileData" + file.jsId
	//$("fileName" + file.jsId).name="fileName" + file.jsId

	//alert($("fileData" + file.jsId).name + " " + $("fileData" + file.jsId).value)
	//alert($("fileName" + file.jsId).name + " " + $("fileName" + file.jsId).value)
	
	//$("filesTable").style.display = "";
	$("#filesTable").css("display", "");
	//$("newFiles" + file.jsId).style.display = "none";
	$("#newFiles" + file.jsId).css("display", "none");
	
	fillFiles()
	clearFile()
}


function fillFiles() {
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
		
		//alert(content);
		console.log(content);
		
		anchor = content.document.createElement("a");
		if (file.id != null)
			anchor.setAttribute('href', ctx + '/filedownload/'+file.fileName+'?uploadDir='+uploadDir+'&graveFileId='+file.id);
		
		anchorText = content.document.createTextNode(file.fileName);
		anchor.appendChild(anchorText);
		//$("patternFile" + id).appendChild(anchor);
		dwr.util.setValue("tableFileName" + id, anchor);
		
		dwr.util.setValue("tableHiddenFileName" + id, file.fileName);
		$("#tableHiddenFileName" + id).attr("name", 'fileName' + id);
		//$("tableHiddenFileName" + id).name='fileName' + id
		
		if (typeof file.id != 'undefined' && file.id != null) {
			dwr.util.setValue("tableFileId" + id, file.id);
			
			$("#tableFileId" + id).attr("name", "files["+i+"].id");
			//$("tableFileId" + id).name="files["+i+"].id"
		}
		
		if (file.length != null)
			dwr.util.setValue("tableFileLength" + id, file.length);
		else
			dwr.util.setValue("tableFileLength" + id, 'nieznana');
		//$("patternFile" + id).style.display = ""; // officially we should use table-row, but IE prefers "" for some reason
		$("#patternFile" + id).css("display", "");
	}
}

function getFileLocation(jsId) {
	for (var i = 0; i < filesArray.length; i++) {
		file = filesArray[i]
		if (file.jsId == jsId)
			return i
	}
	return -1
}

function deleteFileClicked(fileid) {
	// we were an id of the form "delete{id}", eg "delete42". We lookup the "42"
	file = filesArray[getFileLocation(fileid.substring(6))]
	
	if (confirm("Czy jestes pewien, zeby usunac " + file.fileName + "?")) {
		filesArray.splice(getFileLocation(fileid.substring(6)), 1)
		$('#deletedFilesIds').val($('#deletedFilesIds').val() + file.id + '_')
		//wartosc pola z usuwanymi plikami bedzie przedzielona znakiem _
	}
	fillFiles()
}