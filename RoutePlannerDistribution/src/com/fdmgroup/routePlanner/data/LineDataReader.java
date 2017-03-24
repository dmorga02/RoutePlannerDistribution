package com.fdmgroup.routePlanner.data;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.jdom.Attribute;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;
import org.xml.sax.helpers.DefaultHandler;

import com.fdmgroup.routePlanner.exception.RoutePlannerException;
import com.fdmgroup.routePlanner.spring.SpringBeanRetriever;


/**
 * Simple means of reading-in XML format data files for station listings.
 *
 * Parses either single line of station information (using getLineData()) or
 * complete network data file (using getNetworkData()).
 *
 * @author stuartj
 */
public class LineDataReader extends DefaultHandler {

	public LineDataReader(){

	}
	
	/**
	 * Parses data file to return information about complete network, as a Map where keys are line names
	 * on network, and corresponding value objects are Lists of strings representing stations on those lines.
	 *
	 * @param s	InputStream from file holding network data (NB: for use when bundling files).
	 * @return Map	the Map of Lists holding station name sequences as Strings.
	 * @throws RoutePlannerException
	 * @throws IOException 
	 */
    
    public Map<String, List<String>> getNetworkData(InputStream s) throws RoutePlannerException, IOException
    {
    	if (s == null) {
    		throw new RoutePlannerException("The Network File is Invalid or cannot be read.");
    	}
	    SAXBuilder saxbuilder = new SAXBuilder();
		try {
			Document doc = saxbuilder.build(s);
			return generateNetwork( doc );
		} catch (JDOMException e) {
			throw new RoutePlannerException("The NetWork File is Invalid");
		}
    }

    /**
     * Parses data file to return information about complete network, as a Map where keys are line names
     * on network, and corresponding value objects are Lists of strings representing stations on those lines.
     *
	 * @param s File holding network data .
	 * @return Map	the Map of Lists holding station name sequences as Strings.
	 * @throws RoutePlannerException
     * @throws IOException 
	 */
	public Map<String, List<String>> getNetworkData(File networkFile) throws RoutePlannerException, IOException
    {
		SAXBuilder saxbuilder = new SAXBuilder();
		try {
			Document saxbuild = saxbuilder.build(networkFile);
			return generateNetwork( saxbuild );
		} catch (JDOMException e) {
			throw new RoutePlannerException(e,"The NetWork File is Invalid");
		}
    }

	/**
	 * Private method which will return the inputed XML document as a Map<String, List<String>>
	 * @param doc
	 * The XML document to be read
	 * @return
	 * A map containing the Line names as keys and their station names as their values. 
	 * @throws RoutePlannerException
	 * This exception will trigger if the file inputed is not in the proper format for reading. 
	 */
	private Map<String, List<String>> generateNetwork(Document doc) throws RoutePlannerException
	{
		Map<String, List<String>> map = new HashMap<String, List<String>>();
		List<?> lines = null;
		List<?> stations = null;
		String currentLineName, currentStationName = null;
		Element currentLine = null, currentStation = null;
		Element root = doc.getRootElement();
		lines = root.getChildren();

		for (Iterator<?> iter = lines.iterator(); iter.hasNext();) {
			currentLine = (Element) iter.next();
			Attribute attr = currentLine.getAttribute("name");
			if (attr == null) {
				throw new RoutePlannerException("Invalid input map file, XML is not formatted correctly.");
			}
			currentLineName = attr.getValue();
			
			stations = currentLine.getChildren();
			@SuppressWarnings("unchecked")
			List<String> stationNames = (List<String>) SpringBeanRetriever.getBean("aList"); 
			for (Iterator<?> iterator = stations.iterator();iterator.hasNext();) {
				currentStation = (Element) iterator.next();
				currentStationName = currentStation.getText();
				stationNames.add(currentStationName);
			}
			map.put(currentLineName, stationNames);
		}

		return map;
	}
}
