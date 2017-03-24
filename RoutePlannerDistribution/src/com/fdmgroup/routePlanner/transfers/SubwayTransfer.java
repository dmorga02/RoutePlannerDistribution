package com.fdmgroup.routePlanner.transfers;

import java.util.List;

import com.fdmgroup.routePlanner.exception.RoutePlannerException;
import com.fdmgroup.routePlanner.lines.ILine;
import com.fdmgroup.routePlanner.nodes.INode;

/**
 * The SubwayTransfer class will store information about the starting node and
 * ending node of a particular transfer and the specific line that it is on.
 * 
 * @author Song.Chen, Egzon.Zuta
 * 
 */
public class SubwayTransfer implements ITransfer {

	private static final long serialVersionUID = 3253099864071054397L;
	private INode startNode;
	private INode endNode;
	private List<ILine> lines;
	private int numOfStops;

	/**
	 * The following methods are our generated getters and setters, we are going
	 * to need these because our instance member variables are marked private.
	 */
	@Override
	public INode getStartNode() {
		return startNode;
	}

	@Override
	public void setStartNode(INode node) throws RoutePlannerException {
		if (node == null)
			throw new RoutePlannerException("startNode cannot be null");
		startNode = node;

	}

	@Override
	public INode getEndNode() {
		return endNode;
	}

	@Override
	public void setEndNode(INode node) throws RoutePlannerException {
		if (node != null) {
			endNode = node;

		} else {
			throw new RoutePlannerException("EndNode cannot be null");
		}
	}

	@Override
	public List<ILine> getLines() {
		return lines;
	}

	@Override
	public void setLines(List<ILine> lines) throws RoutePlannerException {
		if (lines != null) {
			this.lines = lines;
		} else {
			throw new RoutePlannerException("Lines cannot be null");
		}
	}

	/**
	 * Overridden hashCode method used for our personalised implementation
	 * 
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((endNode == null) ? 0 : endNode.hashCode());
		result = prime * result + ((lines == null) ? 0 : lines.hashCode());
		result = prime * result
				+ ((startNode == null) ? 0 : startNode.hashCode());
		return result;
	}
	
	@Override
	public int getNumOfStops() {
		return numOfStops;
	}

	@Override
	public void setNumOfStops(int numOfStops) throws RoutePlannerException {
		if (numOfStops > 0)
			this.numOfStops=numOfStops;
		else 
			throw new RoutePlannerException("The number of stops for a transfer can not be less than one.");
	}

	/**
	 * Overridden equals method used for our personalised implementation
	 * 
	 * @param obj
	 *            object to be compared with
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
		SubwayTransfer other = (SubwayTransfer) obj;
		if (endNode == null) {
			if (other.endNode != null)
				return false;
		} else if (!endNode.equals(other.endNode))
			return false;
		if (lines == null) {
			if (other.lines != null)
				return false;
		} else if (!lines.equals(other.lines))
			return false;
		if (startNode == null) {
			if (other.startNode != null)
				return false;
		} else if (!startNode.equals(other.startNode))
			return false;
		return true;
	}

}
