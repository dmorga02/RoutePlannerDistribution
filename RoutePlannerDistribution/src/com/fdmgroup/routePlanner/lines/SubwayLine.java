package com.fdmgroup.routePlanner.lines;

import java.util.ArrayList;
import java.util.List;

import com.fdmgroup.routePlanner.exception.RoutePlannerException;
import com.fdmgroup.routePlanner.nodes.INode;

/**
 * This class is going to store information about a particular
 * subway line that includes the name of the lines and the nodes
 * that it contains.
 * 
 * @author Song.Chen, Egzon.Zuta
 * 
 */
public class SubwayLine implements ILine {

	private static final long serialVersionUID = -2737791336486624851L;
	private List<INode> nodes = new ArrayList<INode>();
	private String name;

	public SubwayLine(){
		
	}
	/**
	 * This method will retrieve the list of Nodes that are on this SubwayLine
	 * @return the List of Nodes on this line
	 */
	@Override
	public List<INode> getNodes() {
		return nodes;
	}

	/**
	 * This method will set this Line's list of nodes to the specified input. If the input is null, then the method will 
	 * throw an exception instead of setting the list.
	 * @param nodes The input List of Nodes to be this Object's new List of Nodes.
	 * @throws RoutePlannerException Thrown if the input is null. 
	 */
	@Override
	public void setNodes(List<INode> nodes) throws RoutePlannerException {
		if (nodes == null)
			throw new RoutePlannerException("Nodes cannot be null");
		this.nodes = nodes;

	}

	/**
	 * This method will set this SubwayLine's name. If the input is null, then the method will throw an exception instead 
	 * of setting the name.
	 * @param name The SubwayLine's new name
	 * @throws RoutePlannerException Thrown if the input String is null. 
	 */
	@Override
	public void setName(String name) throws RoutePlannerException {
		if (name == null)
			throw new RoutePlannerException("SubwayLine name cannot be null!");
		this.name = name;

	}

	/**
	 * This method will retrieve this SubwayLine's name. 
	 * @return This SubwayLine's name.
	 */
	@Override
	public String getName() {
		return name;
	}
	
	@Override
	public String toString(){
		return name;
	}

	/**
	 * Returns a hashCode for this SubwayLine object. The result is based off of a mathematical formula based on it's String
	 * name and what nodes are present within this SubwayLine object
	 * @return integer hashCode for use in hash collections.
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((nodes == null) ? 0 : nodes.hashCode());
		return result;
	}

	/**
	 * Compares this SubwayLine object with another Object specified in the input. This function will determine if the 
	 * Object in the input is equal to this SubwayLine, determined by this SubwayLine's name and nodes. 
	 * 
	 * @param obj	object to be compared with
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
		SubwayLine other = (SubwayLine) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (nodes == null) {
			if (other.nodes != null)
				return false;
		} else if (!nodes.equals(other.nodes))
			return false;
		return true;
	}

}
