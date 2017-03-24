package com.fdmgroup.routePlanner.data;

import com.fdmgroup.routePlanner.exception.RoutePlannerException;
import com.fdmgroup.routePlanner.nodes.INode;
import com.fdmgroup.routePlanner.spring.SpringBeanRetriever;

/**
 * A factory which will retrieve instance of an INode implementing class. Which class is selected is based 
 * on spring beans.xml 
 * @author Daniel.Jurin
 *
 */
public class NodeFactory {
	/**
	 * Factory method which will return an instance of a class implementing INode.
	 * @return The instance of the class implementing INode.
	 * @throws RoutePlannerException Thrown if the factory is unable to create an instance of the requested object. 
	 */
	public static INode getInstance() throws RoutePlannerException {
		INode nodes = null;	
		nodes = (INode) SpringBeanRetriever.getBean("node");
		if (nodes == null) {
			throw new RoutePlannerException("Failure retrieving node bean");
		}
		return nodes;
	}
}
