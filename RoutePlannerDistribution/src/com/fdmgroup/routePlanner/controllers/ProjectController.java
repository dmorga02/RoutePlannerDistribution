package com.fdmgroup.routePlanner.controllers;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.fdmgroup.routePlanner.commands.RouteDisplayer;
import com.fdmgroup.routePlanner.data.LineHolder;
import com.fdmgroup.routePlanner.data.NodeHolder;
import com.fdmgroup.routePlanner.exception.RoutePlannerException;

/**
 * This is our Project Controller which will allow our application to flow in a
 * specified sequence that we want it to run by.
 * 
 * @author Song.Chen, Egzon.Zuta
 * 
 */

@Controller
public class ProjectController {

	/**
	 * Used to display the homepage of our application
	 * 
	 * @return the string "index" which will be the requested jsp file to
	 *         display to the user
	 */
	@ExceptionHandler({ Exception.class })
	@RequestMapping("/")
	public String goToHomeHandler(HttpServletRequest request) {
		return "index";
	}

	/**
	 * Used to display the homepage of our application if the user enters a
	 * different url
	 * 
	 * @return the string "index" which will be the requested jspf file to
	 *         display the user
	 * @throws RoutePlannerException
	 */
	@RequestMapping("/**/*")
	public String goToHomePageIfAnythingElseIsEntered(HttpServletRequest request) {
		return "redirect:/";
	}

	/**
	 * When the ViewRoutes is mapped, the index jsp will be sent to the user
	 * with the information that is obtained from user to calculate the
	 * specified route
	 * 
	 * @param request
	 *            object to store information and used to send that information
	 *            to the jsp
	 * @return
	 * @throws RoutePlannerException
	 */
	@RequestMapping("ViewRoutes")
	public String goToViewRoutesHandler(HttpServletRequest request,
			@RequestParam String route_start, @RequestParam String route_end)
			throws RoutePlannerException {

		RouteDisplayer routeDisplayer = new RouteDisplayer();

		NodeHolder nodeHolder = (NodeHolder) request.getSession()
				.getServletContext().getAttribute("nodeHolder");
		LineHolder lineHolder = (LineHolder) request.getSession()
				.getServletContext().getAttribute("lineHolder");

		request.setAttribute("routeToTake", (routeDisplayer.getRoutes(
				route_start, route_end, nodeHolder, lineHolder)));
		request.setAttribute("route_start", route_start);
		request.setAttribute("route_end", route_end);
		request.setAttribute("ErrorMessage", (routeDisplayer.getErrorMessage()));

		return "routePopulate";
	}

	@RequestMapping("/{route_start}/{route_end}")
	public String stationsFromRequestPath(
			@PathVariable("route_start") String route_start,
			@PathVariable("route_end") String route_end,
			HttpServletRequest request, RedirectAttributes redirectAttributes)
			throws RoutePlannerException {

		route_start = camelCase(route_start);
		route_end = camelCase(route_end);
		
		RouteDisplayer routeDisplayer = new RouteDisplayer();

		NodeHolder nodeHolder = (NodeHolder) request.getSession()
				.getServletContext().getAttribute("nodeHolder");
		LineHolder lineHolder = (LineHolder) request.getSession()
				.getServletContext().getAttribute("lineHolder");
		
		redirectAttributes.addFlashAttribute("routeToTake", (routeDisplayer.getRoutes(
				route_start, route_end, nodeHolder, lineHolder)));
		redirectAttributes.addFlashAttribute("route_start",route_start);
		redirectAttributes.addFlashAttribute("route_end", route_end);
		redirectAttributes.addFlashAttribute("ErrorMessage", (routeDisplayer.getErrorMessage()));

		return "redirect:/";
	}
	
	private String camelCase(String station){
		String[] parts = station.replaceAll("_", " ").split("\\s+");
		   String camelCaseString = "";
		   for (String part : parts){
		      camelCaseString = camelCaseString + toProperCase(part)+ " ";
		   }
		   return camelCaseString.trim();
	}
	
	private String toProperCase(String s) {
	    return s.substring(0, 1).toUpperCase() +
	               s.substring(1).toLowerCase();
	}
}
