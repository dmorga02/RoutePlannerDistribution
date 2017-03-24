package com.fdmgroup.routePlanner.nodes;

import java.util.Map;

import com.fdmgroup.routePlanner.exception.RoutePlannerException;

/**
 * This is a specialised class that will represent nodes in our application.
 * 
 * @author Binoub.Rizk, Egzon.Zuta
 */

public class Station implements INode {

	private static final long serialVersionUID = -2538459960568864453L;
	private String name;
	private Map<INode, Integer> adjacentNodes;

	/**
	 * No-arg constructor that is going instantiate adjacentNodes, adjacentNodes
	 * Map doesn't contain any values at this point
	 * @throws RoutePlannerException 
	 */


	public Station() {

	}
	public Station(Map<INode,Integer> adjacentNodes) throws RoutePlannerException{
		setAdjacentNodes(adjacentNodes);
	}

	/**
	 * The following methods are our generated getters and setters, we are going
	 * to need these because our instance member variables are marked private.
	 */

	@Override
	public Map<INode, Integer> getAdjacentNodes() {
		return adjacentNodes;
	}

	/**
	 * This method will set this Station object's adjacent nodes to the adjacent nodes specified in the input. This method
	 * will overwrite existing adjacent nodes. It is recommended that this is done on both ends of a node at once to ensure
	 * that there are no one-way links, since this method will only set the current object's adjacent nodes.
	 * @param adjacentNodes The new map of Nodes to be used by this object
	 */
	@Override
	public void setAdjacentNodes(Map<INode, Integer> adjacentNodes)
			throws RoutePlannerException {
		if (adjacentNodes == null)
			throw new RoutePlannerException("Nodes cannot be null");
		this.adjacentNodes = adjacentNodes;

	}

	/**
	 * This method will add another node to this Station's adjacent nodes. If the node already exists as an adjacent node of
	 * this Station object, it will be overwritten. Otherwise, it will be added. This will not affect other nodes. It is 
	 * recommended that this is used on the node being added as well to ensure that there are no one-way adjacencies, unless
	 * that is the intended functionality.
	 * @param node The node to be added
	 * @param distance The distance between the two nods as an integer. 
	 */
	@Override
	public void addAdjacentNode(INode node, Integer distance)
			throws RoutePlannerException {
		if (node != null & distance != null) {
			adjacentNodes.put(node, distance);
		} else if (node == null) {
			throw new RoutePlannerException("Node cannot be null");
		} else if (distance == null) {
			throw new RoutePlannerException("Distance cannot be null");
		}
	}

	/**
	 * This method will set the name of the Station to the specified input.
	 * @param name The String input that is to be the new name. 
	 */
	@Override
	public void setName(String name) throws RoutePlannerException {
		if (name == null)
			throw new RoutePlannerException("Name cannot be null");
		this.name = name;

	}

	/**
	 * This method will return this station object's name.
	 * @return The name of this object.
	 */
	@Override
	public String getName() {
		return name;
	}

	/**
	 * Returns a hashcode for this object. The result of the hash code is based on this station's name.
	 * @return The hashcode for this Station Object
	 */

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}

	/**
	 * Compares this Station object to another object. This method will return true if this Station object is compared to
	 * another Station with the exact same name.  
	 * 
	 * @param obj object to be compared with
	 * @return return true if the objects are equal and false otherwise
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Station other = (Station) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}
	
	/**
	 * This will return a String representation of this Object, or rather, the Station's name.
	 * @return The Station's string representation. 
	 */
	@Override
	public String toString(){
		return name;
	}
}
