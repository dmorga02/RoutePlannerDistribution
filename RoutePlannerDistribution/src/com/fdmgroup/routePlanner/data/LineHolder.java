package com.fdmgroup.routePlanner.data;

import java.util.List;

import com.fdmgroup.routePlanner.exception.RoutePlannerException;
import com.fdmgroup.routePlanner.lines.ILine;
/**
 * A LineHolder object will store a list of Line Objects for use. 
 * 
 * @author Daniel.Jurin
 *
 */
public class LineHolder {
	private List<ILine> lines;
	
	/**
	 * Default constructor for a LineHolder object. 
	 */
	public LineHolder() {
		
	}
	/**
	 * Method to get this LineHolder's stored list of Lines.
	 * @return The lines held in this object.
	 */
	public List<ILine> getLines() {
		return lines;
	}

	/**
	 * Method to set this holder's Lines. It is recommended that this is used with LineCreator's MakeLines. 
	 * @param lines The list of ILine objects to use. 
	 */
	public void setLines(List<ILine> lines) {
		this.lines = lines;
	}
	
	/**
	 * Method to try and attain a specific line from the list. 
	 * @param input The Line's name.
	 * @return The ILine object that is being searched for.
	 * @throws RoutePlannerException This will be thrown if the target ILine does not exist within the holder, or if the LineHolder contains no lines. 
	 */
	public ILine getLineFromString(String input) throws RoutePlannerException {
		if (lines == null) {
			throw new RoutePlannerException("LineHolder holds no lines");
		}
		for (ILine line : lines) {
			if (line.getName().equals(input)) {
				return line;
			}
		}
		throw new RoutePlannerException("Target line does not exist");
	}
}
