package com.fdmgroup.routePlanner.routes;

import java.io.Serializable;
import java.util.List;

import com.fdmgroup.routePlanner.exception.RoutePlannerException;
import com.fdmgroup.routePlanner.nodes.INode;
/**
 * This interface is going to be implemented by the route
 * class in our application because it is important
 * that our Route in the application contains a list of
 * Nodes.
 * 
 * 
 * @author Song.Chen, Egzon.Zuta
 *
 */
public interface IRoute extends Serializable{
	List<INode> getRoute();
	void setRoute(List<INode> route) throws RoutePlannerException;
}
