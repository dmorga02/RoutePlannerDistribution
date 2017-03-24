package com.fdmgroup.routePlanner.data;

import java.io.Serializable;
import java.util.List;

import com.fdmgroup.routePlanner.exception.RoutePlannerException;
import com.fdmgroup.routePlanner.nodes.INode;

public class NodeHolder implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -2196320992836335598L;
	private List<INode> nodes;
	
	/**
	 * Default Constructor for a NodeHolder object. The NodeHolder object will store a list of Nodes for use elsewhere. It
	 * is recommended that setNodes is called to determine what nodes will be held within this class. 
	 */
	public NodeHolder() {
		
	}
	
	/**
	 * This method will return the NodeHolder's stored Nodes
	 * @return The nodes held in this object.
	 */
	public List<INode> getNodes() {
		return nodes;
	}
	
	/**
	 * This Method will set this holder's INodes. It is recommended that this is used with NodeCreator's MakeNodes. 
	 * @param nodes The list of INode objects to use. 
	 */
	public void setNodes(List<INode> nodes) {
		this.nodes = nodes;
	}
	
	/**
	 * This method will attain a specific node from this NodeHolder object, determined by their string name. Names should be
	 * unique among nodes. 
	 * @param input The Node's name.
	 * @return The INode object that is being searched for.
	 * @throws RoutePlannerException This will be thrown if the target INode does not exist within the holder. 
	 */
	public INode getNodeFromString(String input) throws RoutePlannerException {
		for (INode node : nodes) {
			if (node.getName() != null) {
				if (node.getName().equals(input)) {
					return node;
				}
			}
		}
		throw new RoutePlannerException("Target node does not exist");
	}
	
	/**
	 * This method will sort the nodes stored in the NodeHolder to be placed in alphabetical order. Note that this 
	 * method will ignore case sensitivity when it comes to sorting by letter. 
	 */
	public void sortNodes() {
		if (nodes == null) {
			return;
		}
		if (nodes.size() == 0) {
			return;
		}
		boolean noswap = true;
		for (int loop2 = 0; loop2 < nodes.size(); loop2++) {
			noswap = true;
			for (int loop = 0; loop < nodes.size() - 1; loop++) {
				String name1 = nodes.get(loop).getName();
				String name2 = nodes.get(loop + 1).getName();
				if (name1.compareToIgnoreCase(name2) > 0) {
					INode node1 = nodes.get(loop);
					INode node2 = nodes.get(loop + 1);
					nodes.set(loop, node2);
					nodes.set(loop + 1, node1);
					noswap = false;
				}
			}
			if (noswap == true) {
				loop2 = nodes.size();
			}
		}
		
	}
}
