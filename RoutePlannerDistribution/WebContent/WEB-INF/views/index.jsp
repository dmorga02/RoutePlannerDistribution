<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html>
<html>
<head>
<title>London Underground Planner</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<meta name="author" content="Dimitri Morgan">
<script src="js/stationParse.js"></script>
<link rel="stylesheet" type="text/css" href="css/indexStyle.css" />
<script src="http://ajax.aspnetcdn.com/ajax/jQuery/jquery-1.11.1.min.js"></script>
<link rel="shortcut icon" href="images/favicon.ico" type="image/x-icon" />
</head>
<body>
	<header>
		<span id="logo"><span id="fdm">FDM</span>London Underground</span>
		<span id="headerMessage"> <c:out value="${requestScope.ErrorMessage}"></c:out></span>
	</header>
	<div id="main">
		<div id="routes">
		<c:choose>
		<c:when test="${requestScope.routeToTake != null }">
		<%@include file="routePopulate.jsp" %>
		</c:when>
		<c:otherwise>
			<span class="initialRouteText">Please select a starting point and destination!</span>
			</c:otherwise>
			</c:choose>
		</div>

		<div id="optionContainer">
			<label for="route_start">Starting Point</label> <select
				name="route_start" id="route_start"
				onchange="removeFromEnd(this.value); checkStart(this.value);"
				required="required">
				<option disabled="disabled" style="display: none;"
					selected="selected" id="startHeader">Select Station</option>
				<c:forEach items="${applicationScope.nodeHolder.nodes}" var="route">
					<option value="${route.name}" id="start${route.name}"><c:out
							value="${route.name}"></c:out></option>
				</c:forEach>
			</select> <label for="route_end">Destination</label> <select name="route_end"
				id="route_end" required="required">
				<option disabled="disabled" style="display: none;"
					selected="selected" id="endHeader">Select Station</option>
				<c:forEach items="${applicationScope.nodeHolder.nodes}" var="route">
					<option value="${route.name}" id="end${route.name}"><c:out
							value="${route.name}"></c:out></option>
				</c:forEach>
			</select>
			<button id="routesButton" onclick="getRoutes()">View
				Route(s)</button>
			<div id="messageBox"></div>
		</div>
	</div>
	<div id="scroll">

		<img id="backgroundPan" src="images/standard-tube-map.svg" />

	</div>

	<footer>Alex Fong | Binoub Rizk | Daniel Jurin | Dimitri Morgan | Egzon
		Zuta | Jessica Gonsalves | Song Chen</footer>
	<script>
		var clicking = false;
		var previousX;
		var previousY;

		$("#scroll").mousedown(function(e) {
			e.preventDefault();
			previousX = e.clientX;
			previousY = e.clientY;
			clicking = true;
		});

		$(document).mouseup(function() {

			clicking = false;
		});

		$("#scroll").mousemove(
				function(e) {

					if (clicking) {
						e.preventDefault();
						$("#scroll").scrollLeft(
								$("#scroll").scrollLeft()
										+ (previousX - e.clientX));
						$("#scroll").scrollTop(
								$("#scroll").scrollTop()
										+ (previousY - e.clientY));
						previousX = e.clientX;
						previousY = e.clientY;
					}
				});

		$("#scroll").mouseleave(function(e) {
			clicking = false;
		});
		
		$(document).keypress(function(event){
		 
			var keycode = (event.keyCode ? event.keyCode : event.which);
			if(keycode == '13'){
				getRoutes();
			}
		 
		});
		 
	</script>
</body>
</html>
