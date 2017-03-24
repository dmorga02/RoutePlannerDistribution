package com.fdmgroup.routePlanner.data;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import com.fdmgroup.routePlanner.exception.RoutePlannerException;
import com.fdmgroup.routePlanner.nodes.INode;
import com.fdmgroup.routePlanner.spring.SpringBeanRetriever;

/**
 * The NodeCreator class's primary function is to convert String data in the form of a Map<String, List<String>> into Nodes,
 * the Nodes being contained within the values of the Map. NodeCreator objects must have a LineDataReader object set before
 * they can generate Nodes properly.
 * 
 * @author Daniel.Jurin
 *
 */
public class NodeCreator {
	private static final int default_distance = 1;
	private LineDataReader reader;
	
	/**
	 * Retrieves the NodeCreator's dataReader object. This Reader is what is responsible for converting the XML data into
	 * a form which the NodeCreator can turn into Node objects.
	 * @return This object's Reader object.
	 */
	public LineDataReader getReader() {
		return reader;
	}
	
	/**
	 * Sets the NodeCreator's dataReader object. This Reader is what is responsible for converting the XML data into
	 * a form which the NodeCreator can turn into Node objects.
	 * @param reader This object's new Reader Object
	 */
	public void setReader(LineDataReader reader) {
		this.reader = reader;
	}

	/**
	 * Default constructor for NodeCreator. A NodeCreator will need to run setReader before it is ready for use.  
	 * @throws RoutePlannerException Thrown if initialization fails due to being unable to get the proper bean from spring. 
	 */
	public NodeCreator() throws RoutePlannerException {
		
	}

	/**
	 * This is the second method called by makeNodes, and will scan through all lines in order to connect any adjacent 
	 * nodes to one another. This will use the Node's addAdjacentNode method in order to connect them. 
	 * Deprecation warning: It is recommended to use makeNodesWhole instead. While this method with makeNodesNames 
	 * will work fine, the makeNodesWhole method is slightly more computationally efficient and more cleanly coded.
	 * @param nodes A list of nodes with no adjacency information. 
	 * @param submap The map of Strings to be converted into nodes.
	 * @param keys An array of all of the keys to the map.
	 * @return A list of Nodes
	 * @throws RoutePlannerException Thrown if this method is unable to create Node objects.
	 */
	@Deprecated
	private List<INode> makeNodesAdjacencies(List<INode> nodes, Map<String, List<String>> submap, Object[] keys) throws RoutePlannerException {
		for (int loop = 0; loop < keys.length; loop++) {
			List<String> line = submap.get(keys[loop]);
			for (int loop2 = 0; loop2 < line.size(); loop2++) {
				String nodename = line.get(loop2);
				String backstation = null;
				String frontstation = null;
				int index = -1;
				int backindex = -1;
				int frontindex = -1;
				for (int loop3 = 0; loop3 < nodes.size(); loop3++) {
					if (nodename.equals(nodes.get(loop3).getName())) {
						index = loop3;
						loop3 = nodes.size();
					}
				}
				if (loop2 != 0) {
					backstation = line.get(loop2 - 1);
					for (int loop3 = 0; loop3 < nodes.size(); loop3++) {
						if (backstation.equals(nodes.get(loop3).getName())) {
							backindex = loop3;
							loop3 = nodes.size();
						}
					}
				}
				if (loop2 != line.size() - 1) {
					frontstation = line.get(loop2 + 1);
					for (int loop3 = 0; loop3 < nodes.size(); loop3++) {
						if (frontstation.equals(nodes.get(loop3).getName())) {
							frontindex = loop3;
							loop3 = nodes.size();
						}
					}
				}
				if ((backindex != -1) && (backstation != null)) {
					nodes.get(index).addAdjacentNode(nodes.get(backindex),
							default_distance);
					nodes.get(backindex).addAdjacentNode(nodes.get(index),
							default_distance);
				}
				if ((frontindex != -1) && (frontstation != null)) {
					nodes.get(index).addAdjacentNode(nodes.get(frontindex),
							default_distance);
					nodes.get(frontindex).addAdjacentNode(nodes.get(index),
							default_distance);
				}
			}
		}
		return nodes;
	}
	
	
	/**
	 * Private method and the first method called in makeNodes, this will create a list of Nodes, however it will only 
	 * create a list of Nodes and set their names. The nodes will not be listed as having any adjacent nodes. 
	 * Deprecation warning: It is recommended to use makeNodesWhole instead. While this method with makeNodesAdjacencies
	 * will work fine, the makeNodesWhole method is slightly more computationally efficient and more cleanly coded.
	 * @param submap The map of Strings to be converted into nodes.
	 * @param keys An array of all of the keys to the map.
	 * @return A list of Nodes
	 * @throws RoutePlannerException Thrown if this method is unable to create Node objects.
	 */
	@Deprecated
	private List<INode> makeNodesNames(Map<String, List<String>> submap, Object[] keys) throws RoutePlannerException {
		List<String> nodenames = (List<String>) SpringBeanRetriever.getBean("aList");
		List<INode> nodes = (List<INode>) SpringBeanRetriever.getBean("aList");

		for (int loop = 0; loop < keys.length; loop++) {
			List<String> lines = submap.get(keys[loop]);
			for (int loop2 = 0; loop2 < lines.size(); loop2++) {
				String nodename = lines.get(loop2);
				
				if (nodenames.contains(nodename)) {
				} else {
					INode station = NodeFactory.getInstance();
					station.setName(nodename);
					nodes.add(station);
					nodenames.add(nodename);
				}
			}

		}
		return nodes;
	}
	
	/**
	 * Private method and the first method called in makeNodes, this will create a list of Nodes, and fill in any
	 * required adjacencies. It is recommended to use this over makeNodeNames and makeNodesAdjacencies
	 * @param submap The map of Strings to be converted into nodes.
	 * @param keys An array of all of the keys to the map.
	 * @return A list of Nodes
	 * @throws RoutePlannerException Thrown if this method is unable to create Node objects.
	 */
	private List<INode> makeNodesWhole(Map<String, List<String>> submap, Object[] keys) throws RoutePlannerException {
		List<String> nodenames = (List<String>) SpringBeanRetriever.getBean("aList");
		List<INode> nodes = (List<INode>) SpringBeanRetriever.getBean("aList");

		for (int loop = 0; loop < keys.length; loop++) {
			List<String> lines = submap.get(keys[loop]);
			for (int loop2 = 0; loop2 < lines.size(); loop2++) {
				String nodename = lines.get(loop2);

				if (nodenames.contains(nodename)) {
					
				} else {
					INode station = NodeFactory.getInstance();
					station.setName(nodename);
					nodes.add(station);
					nodenames.add(nodename);
				}
			}
		}
		for (int loop = 0; loop < keys.length; loop++) {
			List<String> lines = submap.get(keys[loop]);
			for (int loop2 = 0; loop2 < lines.size(); loop2++) {
				
				if (loop2 != 0) {
					nodes.get(nodenames.indexOf(lines.get(loop2))).addAdjacentNode(nodes.get(nodenames.indexOf(lines.get(loop2 - 1))), default_distance);
					nodes.get(nodenames.indexOf(lines.get(loop2 - 1))).addAdjacentNode(nodes.get(nodenames.indexOf(lines.get(loop2))), default_distance);
				}
				if (loop2 != lines.size() - 1) {
					nodes.get(nodenames.indexOf(lines.get(loop2))).addAdjacentNode(nodes.get(nodenames.indexOf(lines.get(loop2 + 1))), default_distance);
					nodes.get(nodenames.indexOf(lines.get(loop2 + 1))).addAdjacentNode(nodes.get(nodenames.indexOf(lines.get(loop2))), default_distance);
				}
			}
		}
		return nodes;
	}

	/**
	 * This method will take in an XML file and will turn the data from the XML file into a List of Node objects with all of 
	 * their adjacencies, to be used later. It is recommended that the results are set into a NodeHolder object. 
	 * @param filename The XML file to be read
	 * @return A list of Nodes which will be used elsewhere. 
	 * @throws InvalidNetWorkException Thrown if the target file is not in the proper format.
	 * @throws IOException Thrown if the target file does not exist or otherwise cannot be read. 
	 * @throws RoutePlannerException Thrown if no filename is entered. 
	 */
	public List<INode> makeNodes(String filename)
			throws RoutePlannerException, IOException {
		
		if (filename == null) {
			throw new RoutePlannerException("No filename entered");
		}
		Map<String, List<String>> submap = getMap(filename);
		
		Object[] keys = submap.keySet().toArray();
		
		List<INode> nodes = makeNodesWhole(submap, keys);
		
		return nodes;
	}

	/**
	 * Private method to get the map of line names and the names of their stations
	 * @param filename File name of the XML document to be read.
	 * @return A Map of Line Names and the corresponding Nodes on their Lines
	 * @throws InvalidNetWorkException Thrown if the target file is not in the proper format
	 * @throws IOException Thrown if the target file does not exist or otherwise cannot be read. 
	 */
	private Map<String, List<String>> getMap(String filename)
			throws RoutePlannerException, IOException {
		Map<String, List<String>> stationmap = null;

		stationmap = reader.getNetworkData(Thread.currentThread().getContextClassLoader()
				.getResourceAsStream(filename));
		return stationmap;

	}
}
