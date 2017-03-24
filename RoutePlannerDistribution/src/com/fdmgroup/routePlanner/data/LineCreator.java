package com.fdmgroup.routePlanner.data;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import com.fdmgroup.routePlanner.exception.RoutePlannerException;
import com.fdmgroup.routePlanner.lines.ILine;
import com.fdmgroup.routePlanner.nodes.INode;
import com.fdmgroup.routePlanner.spring.SpringBeanRetriever;

/**
 * The LineCreator class's primary function is to convert String data in the form of a Map<String, List<String>> into Lines,
 * the keys of the Map being the Line's name and the List<String> values being the nodes which are on the Line. 
 * LineCreator objects must have a LineDataReader and a NodeHolder object set before they can generate Lines properly.
 * 
 * @author Daniel.Jurin
 * 
 */
public class LineCreator {
	private LineDataReader reader;
	private NodeHolder nodes;
	
	/**
	 * Retrieves the LineCreator's dataReader object. This Reader is what is responsible for converting the XML data into
	 * a form which the LineCreator can turn into Line objects.
	 * @return This object's Reader object.
	 */
	public LineDataReader getReader() {
		return reader;
	}

	/**
	 * Sets the LineCreator's dataReader object. This Reader is what is responsible for converting the XML data into
	 * a form which the LineCreator can turn into Line objects.
	 * @param reader This object's new Reader Object
	 */
	public void setReader(LineDataReader reader) {
		this.reader = reader;
	}
	
	/**
	 * Retrieves a NodeHolder object which contains all of the nodes working within this system.  
	 * @return The NodeHolder object which is contained within this class.
	 */
	public NodeHolder getNodes() {
		return nodes;
	}

	/**
	 * Gives this LineCreator a NodeHolder object which contains all of the nodes within this system. These are required to
	 * let the program know which Nodes are part of what lines.
	 * @param nodes A NodeHolder object containing all of the nodes within this system. 
	 */
	public void setNodes(NodeHolder nodes) {
		this.nodes = nodes;
	}

	/**
	 * Constructor for the LineCreator class. While this can create a LineCreator Object, the object must have a valid run
	 * of setReader and setNodes to be run properly. 
	 * 
	 * @throws RoutePlannerException
	 */
	public LineCreator() throws RoutePlannerException {
	}

	/**
	 * Private method to retrieve a map of Line and Station names for use in turning XML data into objects. 
	 * 
	 * @param filename File name of the XML document to be read.
	 * @return A Map of Line Names keyed to a list of Station names. 
	 * @throws InvalidNetWorkException Thrown if the target file is not in the proper format
	 * @throws IOException Thrown if the target file does not exist or otherwise cannot be read.
	 */
	private Map<String, List<String>> getMap(String filename)
			throws RoutePlannerException, IOException {
		if (filename == null)
			throw new RoutePlannerException("No filename entered");
		Map<String, List<String>> stationmap = null;
		stationmap = reader.getNetworkData(Thread.currentThread()
				.getContextClassLoader().getResourceAsStream(filename));

		return stationmap;
	}

	/**
	 * This method will convert an XML file containing the node and line
	 * information and turn it into a list of Line objects for later use.
	 * 
	 * @param filename File name of the XML document to be read.
	 * @return Returns a list of Line objects. 
	 * @throws InvalidNetWorkException Thrown if the target file is not in the proper format
	 * @throws IOException Thrown if the target file does not exist or otherwise cannot be read.
	 * @throws RoutePlannerException Thrown if there are no lines or one of the nodes on a line does not exist.
	 */
	@SuppressWarnings("unchecked")
	public List<ILine> makeLines(String filename) throws RoutePlannerException,
			IOException {
		if (reader == null) {
			throw new RoutePlannerException("Missing LineDataReader.");	
		}
		if (nodes == null) {
			throw new RoutePlannerException("LineCreator has no nodes.");
		}
		if (filename == null)
			throw new RoutePlannerException("No filename entered");

		Map<String, List<String>> submap = getMap(filename);
		List<ILine> lines = (List<ILine>) SpringBeanRetriever.getBean("aList");
		Object[] keys = submap.keySet().toArray();

		if (keys.length == 0)
			throw new RoutePlannerException("No lines in the Map");

		for (int loop = 0; loop < keys.length; loop++) {
			List<String> linenames = submap.get(keys[loop]);
			List<INode> nodelist = (List<INode>) SpringBeanRetriever.getBean("aList");
			for (String linename : linenames) {
				nodelist.add(nodes.getNodeFromString(linename));
			}
			ILine line = LineFactory.getInstance();
			line.setName(keys[loop].toString());
			line.setNodes(nodelist);

			lines.add(line);

		}

		return lines;
	}

	
}
