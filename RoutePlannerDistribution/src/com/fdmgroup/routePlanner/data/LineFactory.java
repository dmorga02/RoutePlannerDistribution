package com.fdmgroup.routePlanner.data;

import com.fdmgroup.routePlanner.exception.RoutePlannerException;
import com.fdmgroup.routePlanner.lines.ILine;
import com.fdmgroup.routePlanner.spring.SpringBeanRetriever;

/**
 * A factory which will retrieve instance of an ILine implementing class. Which class is selected is based 
 * on spring beans.xml 
 * @author Daniel.Jurin
 *
 */
public class LineFactory {
	
	/**
	 * Factory method which will return an instance of a class implementing ILine.
	 * @return The instance of the class implementing ILine.
	 * @throws RoutePlannerException Thrown if the factory is unable to create an instance of the requested object. 
	 */
	public static ILine getInstance() throws RoutePlannerException {
		ILine line = null;
	
		line = (ILine) SpringBeanRetriever.getBean("line");
		if (line == null) {
			throw new RoutePlannerException("Failure retrieving line bean");
		}
		return line;
	}
}	

