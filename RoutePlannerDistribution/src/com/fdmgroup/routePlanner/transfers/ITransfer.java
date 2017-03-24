package com.fdmgroup.routePlanner.transfers;

import java.io.Serializable;
import java.util.List;

import com.fdmgroup.routePlanner.exception.RoutePlannerException;
import com.fdmgroup.routePlanner.lines.ILine;
import com.fdmgroup.routePlanner.nodes.INode;
/**
 * ITransfer allows for greater readability when displaying a route by breaking 
 * down a route into a start node, line, how many nodes to transverse on that line,
 * and an end node. Thus any implementing class must implement getters and setters for
 * all the previously stated instance variables. <br>
 * Note: The interface does not require an implementing class to contain instance variables to corresponding 
 * setters and getters however is it strongly recommended. 
 * @author Binoub.Rizk, Egzon.Zuta
 *
 */
public interface ITransfer extends Serializable{
	
	INode getStartNode();
	void setStartNode(INode node) throws RoutePlannerException;
	INode getEndNode();
	void setEndNode(INode node) throws RoutePlannerException;
	List<ILine> getLines();
	void setLines(List<ILine> line) throws RoutePlannerException;
	int getNumOfStops();
	void setNumOfStops(int numOfStops) throws RoutePlannerException;

}
