package com.fdmgroup.routePlanner.algorithm;

import java.util.List;

import com.fdmgroup.routePlanner.exception.RoutePlannerException;
import com.fdmgroup.routePlanner.nodes.INode;

/**
 * In graph theory, the shortest path between two points is one such that the sum
 * of the weights of its constituent edges is minimized. Depending on the specific 
 * implementation of the <code>INode</code> interface, edges do not necessarily need to 
 * be represented in order for the path finding algorithm to be used. In the same vein,
 * a representation of the graph is not needed.
 * @see KPathAlgorithm
 * @author yip.fong
 * @author jessica.gonsalves
 */
public interface IPathAlgorithm {

	/**
	 * This method is used to generate one or more routes between a source and destination.  
	 * @param source the starting point
	 * @param destination the end point
	 * @return a <code>List</code> of a <code>List</code> of <code>INode</code>
	 * @throws RoutePlannerException if source or destination is null; if source is the destination;
	 * if the source is not connected to the destination
	 */
	public List<List<INode>> getRoutes(INode source, INode destination) throws RoutePlannerException;
	
	/**
	 * This method returns a number representing the total cost of a path.
	 * @param a <code>List</code> of <code>INode</code>s representing a path.
	 */
	public int getPathCost(List<INode> path) throws RoutePlannerException;
}
