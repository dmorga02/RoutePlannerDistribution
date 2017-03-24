package com.fdmgroup.routePlanner.routes;

import java.util.List;

import com.fdmgroup.routePlanner.exception.RoutePlannerException;
import com.fdmgroup.routePlanner.nodes.INode;

/**
 * This class is going to store the list of nodes
 * that are going to be travelled on to reach the
 * destination node.
 * 
 * @author Song.Chen, Egzon.Zuta
 * 
 */
public class Route implements IRoute {

	private static final long serialVersionUID = -8095332081535672316L;
	private List<INode> routeList;

	/**
	 * This method will return the route as a List of Nodes. The beginning Node of this Route object is the first one, and 
	 * the list is in sequential order for the steps in the Route.
	 * @return This Route's List of Nodes.
	 */
	@Override
	public List<INode> getRoute() {
		return routeList;
	}

	/**
	 * This method will set the route as a List of Nodes. The beginning Node of this Route object is the first one, and 
	 * the list is in sequential order for the steps in the Route.
	 * @param route The inputted List of Nodes to make up a Route. 
	 */
	@Override
	public void setRoute(List<INode> route) throws RoutePlannerException {
		if (route == null)
			throw new RoutePlannerException("Route List cannot be null");
		routeList = route;

	}

}
