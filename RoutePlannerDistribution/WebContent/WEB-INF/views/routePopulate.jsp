<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<c:choose>
	<c:when test="${requestScope.routeToTake != null}">
		<div id="startToDestination">
			<marquee scrolldelay="0" behavior="slide" direction="left" scrollamount="60">
				<span id="headStart"><span class="toFrom">From:</span> <c:out
						value="${requestScope.route_start}"></c:out> </span>
			</marquee>
			<marquee scrolldelay="0" behavior="slide" direction="left" scrollamount="60">
				<span id="headDestination"><span class="toFrom">To:</span> <c:out
						value="${requestScope.route_end}"></c:out></span>
			</marquee>
		</div>
		<span id="expandHolder"><span id="expandToggle" onclick="optionDropdown()">Expand All</span></span>
		<c:set var="routeCount" value="0" scope="page" />
		<c:forEach items="${requestScope.routeToTake}" var="route">


			<c:forEach items="${route.value}" var="transferList">
				<c:set var="stepCount" value="0" scope="page" />
				<c:set var="routeCount" value="${routeCount + 1}" scope="page" />
				<div
					class="routeOption <c:if test="${routeCount gt 3 }">
					hiddenRoutes
					</c:if>">


					<div class="routeHeader"
						onclick="toggleSteps('dropdown${routeCount}',this);">
						Option
						<c:out value="${routeCount}"></c:out>
						<div style="float: right;">
							<c:out value="${route.key}"></c:out>

							<c:choose>
								<c:when test="${route.key == 1}">
       Stop
    </c:when>
								<c:otherwise>
        Stops
    </c:otherwise>
							</c:choose>

						</div>
					</div>

					<div class="routeDropdown" id="dropdown${routeCount}"
						style="display: none;">
						<c:forEach items="${transferList}" var="transfer">

							<div class="routeStep">
								<c:set var="stepCount" value="${stepCount + 1}" scope="page" />
								<span class="stepNumber"><c:out value="${stepCount}"></c:out>.</span>
								<c:set var="trainCount" value="0" scope="page" />
								<span class="startPoint"><c:out
										value="${transfer.startNode.name}"></c:out></span> <span> to </span>
								<span class="endPoint"><c:out
										value="${transfer.endNode.name}"></c:out> </span> <span> via:
								</span>
								<c:forEach items="${transfer.lines}" var="line">
									<c:set var="trainCount" value="${trainCount + 1}" />
									<span
										style="color: rgba(70, 70, 70, .8); font-size: 14px; font-weight: bold;"><c:out
											value="${line.name}"></c:out></span>
									<c:if test="${ trainCount  lt fn:length(transfer.lines)}">
												or
											</c:if>
								</c:forEach>

								<div class="numberOfTransfers">
									<c:out value="${transfer.numOfStops}"></c:out>
									<c:choose>
										<c:when test="${transfer.numOfStops == 1}">
       Stop
    </c:when>
										<c:otherwise>
        Stops
    </c:otherwise>
									</c:choose>

								</div>
							</div>
						</c:forEach>
					</div>
				</div>
			</c:forEach>
		</c:forEach>
		<c:if test="${routeCount gt 3}">
			<span id="moreNumber" onclick="toggleMore()"> <c:out value="${routeCount - 3 }"></c:out><span
				id="moreButton"> More... </span></span>
		</c:if>
	</c:when>
	<c:otherwise>

		<span class="initialRouteText"> <c:choose>
				<c:when test="${requestScope.ErrorMessage != null}">
					<c:out value="${requestScope.ErrorMessage}"></c:out>
				</c:when>
				<c:otherwise>
				We couldn't find any routes
			</c:otherwise>
			</c:choose>
		</span>
	</c:otherwise>
</c:choose>