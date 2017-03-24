package com.fdmgroup.routePlanner.lines;

import java.io.Serializable;
import java.util.List;

import com.fdmgroup.routePlanner.exception.RoutePlannerException;
import com.fdmgroup.routePlanner.nodes.INode;
/**
 * This interface is going to be implemented by different
 * lines that the application is going to contain. The ideal classes
 * that will implement this interface are subways or buses.
 * 
 * @author Daniel.Jurin, Egzon.Zuta
 *
 */
public interface ILine extends Serializable{
	void setName(String name) throws RoutePlannerException;
	String getName();
	List<INode> getNodes();
	void setNodes(List<INode> nodes) throws RoutePlannerException;
}
