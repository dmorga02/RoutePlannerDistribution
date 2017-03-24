package com.fdmgroup.routePlanner.nodes;

import java.io.Serializable;
import java.util.Map;

import com.fdmgroup.routePlanner.exception.RoutePlannerException;
/**
 * This interface is going to be implemented by different types
 * of stops. A stop can be a train station, subway station, bus stop, etc.
 * @author Song.Chen, Egzon.Zuta
 *
 */
public interface INode extends Serializable{
	Map<INode, Integer> getAdjacentNodes();
	void setAdjacentNodes(Map<INode, Integer> adjacentNodes) throws RoutePlannerException;
	void addAdjacentNode(INode node, Integer cost) throws RoutePlannerException;
	void setName(String name) throws RoutePlannerException;
	String getName();
	
}
