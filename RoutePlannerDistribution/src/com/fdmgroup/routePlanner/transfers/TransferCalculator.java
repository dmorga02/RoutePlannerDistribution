package com.fdmgroup.routePlanner.transfers;

import java.util.List;
import java.util.Map;
import java.util.Set;

import com.fdmgroup.routePlanner.data.LineHolder;
import com.fdmgroup.routePlanner.exception.RoutePlannerException;
import com.fdmgroup.routePlanner.lines.ILine;
import com.fdmgroup.routePlanner.nodes.INode;
import com.fdmgroup.routePlanner.routes.IRoute;
import com.fdmgroup.routePlanner.spring.SpringBeanRetriever;

/**
 * Calculates the transfers required to get the destination (i.e. the end of the
 * route).<br>
 * Therefore the output of a route to take is much easier to understand<br>
 * by stating when to get on and off a line.
 * 
 * @author binoub.rizk, egzon.zuta
 * 
 */
public class TransferCalculator {

	private LineHolder lineHolder;

	/**
	 * Getter for lineHolder, which holds a list of all the lines inside the
	 * system
	 * 
	 * @return LineHolder
	 */
	public LineHolder getSubwayHolder() {
		return lineHolder;
	}

	/**
	 * Setter for lineHolder, which holds a list of all the lines inside the
	 * system
	 * 
	 * @param lineholder
	 * @throws RoutePlannerException
	 */
	public void setLineHolder(LineHolder lineHolder)
			throws RoutePlannerException {
		if (lineHolder == null)
			throw new RoutePlannerException("LineHolder cannot be null");
		this.lineHolder = lineHolder;
	}

	/**
	 * Used to calculate the transfers required to get to the destination.<br>
	 * Requires the lineHolder to initialized with all the line in the system.<br>
	 * Will only make a transfer when necessary to take one.<br>
	 * When presented with multiple lines to transfer will rank the them by how
	 * long<br>
	 * it can remain on that line and pick the highest ranked.
	 * 
	 * @param route
	 * @return A List of ITransfer object which represent when to get on and off
	 *         a line.
	 * @throws RoutePlannerException
	 */
	public List<ITransfer> showTransfers(IRoute route)
			throws RoutePlannerException {
		@SuppressWarnings("unchecked")
		List<ITransfer> transfers = (List<ITransfer>) SpringBeanRetriever
				.getBean("aList");
		
		if (route == null)
			throw new RoutePlannerException("Route is null");
		if (route.getRoute() == null)
			throw new RoutePlannerException("There is no route");
		if (route.getRoute().size() < 2)
			throw new RoutePlannerException("Route is empty");
		
		for (INode node : route.getRoute()) {
			int index = route.getRoute().indexOf(node);
			INode next = null;
			
			if ((index + 1) < route.getRoute().size())
				next = route.getRoute().get(index + 1);
			
			if (next != null) {
				Set<ILine> validLines = getValidLines(node, next);
				
				if (!transfers.isEmpty()) {
					boolean alreadyOnLine = false;
					List<ILine> currentTransferLines = transfers.get(
							transfers.size() - 1).getLines();

					for (int i = 0; i < currentTransferLines.size(); i++)
						if (validLines.contains(transfers
								.get(transfers.size() - 1).getLines().get(i)))
							alreadyOnLine = true;

					if (!alreadyOnLine) {
						transfers.get(transfers.size() - 1).setNumOfStops(
								calculateNumOfStops(
										transfers.get(transfers.size() - 1)
												.getStartNode(), node, route));
						transfers.get(transfers.size() - 1).setEndNode(node);
						transfers.add(pickLine(validLines, route, node, index));
					}
					
				}
				
				else {
					transfers.add(pickLine(validLines, route, node, index));
				}
				
			} 
			
			else {
				transfers.get(transfers.size() - 1).setNumOfStops(
						calculateNumOfStops(transfers.get(transfers.size() - 1)
								.getStartNode(), node, route));
				transfers.get(transfers.size() - 1).setEndNode(node);

			}
		}
		return transfers;
	}

	private ITransfer pickLine(Set<ILine> lines, IRoute route,
			INode currentNode, int indexOfCurrentNode)
			throws RoutePlannerException {
		@SuppressWarnings("unchecked")
		Map<ILine, Integer> lineValues = (Map<ILine, Integer>) SpringBeanRetriever
				.getBean("hMap");
		for (ILine line : lines) {
			int value = 0;
			for (int i = indexOfCurrentNode; i < route.getRoute().size(); i++) {
				if (line.getNodes().contains(route.getRoute().get(i))) {
					int index = line.getNodes()
							.indexOf(route.getRoute().get(i));
					if ((index + 1) <= line.getNodes().size()) {
						INode nextNode = calculateNextNode(line, index);
						INode pastNode = calculatePastNode(line, index);

						if ((i + 1) < route.getRoute().size()) {
							value = value + incrementValueOfLine(nextNode, pastNode, route, i);
						}
					}
				}
			}
			lineValues.put(line, value);
		}
		List<ILine> bestLines = calculateBestLines(lineValues);

		ITransfer newTransfer = (ITransfer) SpringBeanRetriever
				.getBean("transfer");
		newTransfer.setStartNode(currentNode);
		newTransfer.setLines(bestLines);

		return newTransfer;
	}

	private Set<ILine> getValidLines(INode currentNode, INode next)
			throws RoutePlannerException {
		@SuppressWarnings("unchecked")
		Set<ILine> validLines = (Set<ILine>) SpringBeanRetriever
				.getBean("hSet");
		for (ILine line : lineHolder.getLines()) {
			if (line.getNodes().contains(currentNode)) {
				int index2 = line.getNodes().indexOf(currentNode);

				INode nextNode = calculateNextNode(line, index2);
				INode pastNode = calculatePastNode(line, index2);

				if (next != null) {
					if (next.equals(nextNode)) {
						validLines.add(line);
					}
				}
				if (pastNode != null) {
					if (next.equals(pastNode)) {
						validLines.add(line);
					}
				}
			}
		}
		return validLines;
	}

	private int calculateNumOfStops(INode startNode, INode endNode, IRoute route) {
		int indexOfStartNode = route.getRoute().indexOf(startNode);
		int indexOfEndNode = route.getRoute().indexOf(endNode);
		return indexOfEndNode - indexOfStartNode;
	}

	private List<ILine> calculateBestLines(Map<ILine, Integer> lineValues)
			throws RoutePlannerException {
		@SuppressWarnings("unchecked")
		List<ILine> bestLines = (List<ILine>) SpringBeanRetriever
				.getBean("aList");
		int bestValue = 0;

		for (ILine line : lineValues.keySet()) {
			if (lineValues.get(line) > bestValue) {
				bestValue = lineValues.get(line);
			}
		}

		for (ILine line : lineValues.keySet()) {
			if (lineValues.get(line) == bestValue) {
				bestLines.add(line);
			}
		}
		return bestLines;
	}

	private INode calculateNextNode(ILine line, int index) {
		INode nextNode = null;
		if ((index + 1) < line.getNodes().size())
			nextNode = line.getNodes().get(index + 1);
		else if (line.getNodes().get(line.getNodes().size() - 2)
				.getAdjacentNodes().containsKey(line.getNodes().get(0)))
			nextNode = line.getNodes().get(0);
		return nextNode;
	}

	private INode calculatePastNode(ILine line, int index) {
		INode pastNode = null;
		if ((index - 1) >= 0)
			pastNode = line.getNodes().get(index - 1);
		else if (line.getNodes().get(0).getAdjacentNodes()
				.containsKey(line.getNodes().get(line.getNodes().size() - 2)))
			pastNode = line.getNodes().get(line.getNodes().size() - 2);
		return pastNode;
	}
	
	private int incrementValueOfLine(INode nextNode, INode pastNode, IRoute route, int indexOfCurrentNode) {
		if (nextNode != null) {
			if (nextNode
					.equals(route.getRoute().get(indexOfCurrentNode + 1))) {
				return 1;
			}
		}
		if (pastNode != null) {
			if (pastNode
					.equals(route.getRoute().get(indexOfCurrentNode + 1))) {
				return 1;
			}
		}
		return 0;
	}

}
