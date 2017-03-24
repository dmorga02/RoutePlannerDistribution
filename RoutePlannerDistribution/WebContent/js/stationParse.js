var savedValue;
function removeFromEnd(currentValue) {
	var valueToRestore = savedValue;
	savedValue = document.getElementById("end" + currentValue);
	savedValue.style.display = "none";
	valueToRestore.style.display = "block";
}

function checkStart(startValue) {

	if (document.getElementById("route_end").value == startValue) {
		document.getElementById("endHeader").selected = "selected";
	}

}

function toggleSteps(routeNumber, selectedRoute) {

	if (document.getElementById(routeNumber).style.display == "none") {
		document.getElementById(routeNumber).style.display = "block";
		selectedRoute.style.width = "370px";
		selectedRoute.style.borderColor = "green";
	} else {
		document.getElementById(routeNumber).style.display = "none";
		selectedRoute.style.width = "225px";
		selectedRoute.style.borderColor = "red";
	}

}

var routeStart;
var routeEnd;

function getRoutes() {
	var desiredStart = document.getElementById("route_start").value;
	var desiredEnd = document.getElementById("route_end").value;

	if (!(desiredStart == routeStart && desiredEnd == routeEnd)) {
		routeStart = desiredStart;
		routeEnd = desiredEnd;

		if (routeStart == "Select Station" || routeEnd == "Select Station") {
			document.getElementById("messageBox").innerHTML = "Please select two stations!";
			document.getElementById("messageBox").style.display = "block";
			return false;
		} else {
			document.getElementById("messageBox").style.display = "none";
			loadRoutes(routeStart, routeEnd);
			return true;
		}
	}
}

function createXMLHttpRequest() {
	if (typeof XMLHttpRequest == "undefined")
		XMLHttpRequest = function() {
			try {
				return new ActiveXObject("Msxml2.XMLHTTP.6.0");
			} catch (e) {
			}
			try {
				return new ActiveXObject("Msxml2.XMLHTTP.3.0");
			} catch (e) {
			}
			try {
				return new ActiveXObject("Msxml2.XMLHTTP");
			} catch (e) {
			}
			try {
				return new ActiveXObject("Microsoft.XMLHTTP");
			} catch (e) {
			}
			throw new Error("This browser does not support XMLHttpRequest.");
		};
	return new XMLHttpRequest();
}

var AJAX = createXMLHttpRequest();

function handler() {
	if (AJAX.readyState == 4 && AJAX.status == 200) {
		document.getElementById("routes").innerHTML = AJAX.responseText;
	} else if (AJAX.readyState == 4 && AJAX.status != 200) {
		alert('Something went wrong...');
	}
}

function loadRoutes(start, destination) {
	AJAX.onreadystatechange = handler;
	AJAX.open("GET", "ViewRoutes?route_start=" + start + "&route_end="
			+ destination);
	AJAX.send("");
}
var toggleStateHolder = true;
function toggleMore() {
	if (toggleStateHolder) {
		var hiddenRoutes = document.getElementsByClassName('hiddenRoutes');
		for (var i = 0; i < hiddenRoutes.length; i++) {
			hiddenRoutes.item(i).style.display = "block";
		}
		document.getElementById("moreButton").innerHTML = " Less...";
		toggleStateHolder = false;
	} else {
		var hiddenRoutes = document.getElementsByClassName('hiddenRoutes');
		for (var i = 0; i < hiddenRoutes.length; i++) {
			hiddenRoutes.item(i).style.display = "none";
		}
		document.getElementById("moreButton").innerHTML = " More...";
		toggleStateHolder = true;
	}
}

var dropdownStateHolder = true;
var singletonDropdown = true;
function optionDropdown() {
	if (singletonDropdown) {
		singletonDropdown = false;
		setTimeout(function() {
		if (dropdownStateHolder) {
			setTimeout(function() {
				var hiddenSteps = document
						.getElementsByClassName('routeDropdown');
				for (var i = 0; i < hiddenSteps.length; i++) {
					hiddenSteps.item(i).style.display = "block";
				}
			}, 195);

			var routeHeaders = document.getElementsByClassName('routeHeader');
			for (var i = 0; i < routeHeaders.length; i++) {

				routeHeaders.item(i).style.width = "370px";
				routeHeaders.item(i).style.borderColor = "green";

			}

			document.getElementById("expandToggle").innerHTML = "Contract All";
			dropdownStateHolder = false;
		} else {
			var hiddenSteps = document.getElementsByClassName('routeDropdown');
			for (var i = 0; i < hiddenSteps.length; i++) {
				hiddenSteps.item(i).style.display = "none";
			}

			setTimeout(function() {
				var routeHeaders = document
						.getElementsByClassName('routeHeader');
				for (var i = 0; i < routeHeaders.length; i++) {
					routeHeaders.item(i).style.width = "225px";
					routeHeaders.item(i).style.borderColor = "red";
				}
			}, 195);

			document.getElementById("expandToggle").innerHTML = " Expand All";
			dropdownStateHolder = true;
		}
		
		singletonDropdown = true;
		}, 200);
	}
		
}