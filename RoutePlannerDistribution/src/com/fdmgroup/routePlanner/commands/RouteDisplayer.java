package com.fdmgroup.routePlanner.commands;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

import com.fdmgroup.routePlanner.algorithm.IPathAlgorithm;
import com.fdmgroup.routePlanner.data.LineHolder;
import com.fdmgroup.routePlanner.data.NodeHolder;
import com.fdmgroup.routePlanner.exception.RoutePlannerException;
import com.fdmgroup.routePlanner.nodes.INode;
import com.fdmgroup.routePlanner.routes.IRoute;
import com.fdmgroup.routePlanner.spring.SpringBeanRetriever;
import com.fdmgroup.routePlanner.transfers.ITransfer;
import com.fdmgroup.routePlanner.transfers.TransferCalculator;

/**
 * This class is responsible for outputting the route that we are going to take
 * from our starting point to our destination.
 * 
 * @author Dimitri.Morgan, Egzon.Zuta
 * 
 */
public class RouteDisplayer {

	private String routeStart;
	private String routeEnd;
	private Map<Integer, List<List<ITransfer>>> routesToTake;
	private String errorMessage;

	public String getRouteStart() {
		return routeStart;
	}

	public void setRouteStart(String routeStart) {
		this.routeStart = routeStart;
	}

	public String getRouteEnd() {
		return routeEnd;
	}

	public void setRouteEnd(String routeEnd) {
		this.routeEnd = routeEnd;
	}

	public Map<Integer, List<List<ITransfer>>> getRoutesToTake() {
		return routesToTake;
	}

	public void setRoutesToTake(Map<Integer, List<List<ITransfer>>> routesToTake) {
		this.routesToTake = routesToTake;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	@SuppressWarnings("unchecked")
	public Map<Integer, List<List<ITransfer>>> getRoutes(String routeStart,
			String routeEnd, NodeHolder nodeHolder, LineHolder lineHolder) {
		IPathAlgorithm pathAlgorithm;
		try {
			pathAlgorithm = (IPathAlgorithm) SpringBeanRetriever
					.getBean("IPathAlgorithm");
		} catch (RoutePlannerException errorMessage) {
			setErrorMessage(errorMessage.getMessage());
			errorMessage.printStackTrace();
			return null;
		}
		List<INode> allNodes = nodeHolder.getNodes();
		INode source = null;
		INode destination = null;
		try {
			for (INode node : allNodes) {
				if (node.getName().equals(routeStart))
					source = node;
				else if (node.getName().equals(routeEnd))
					destination = node;
			}

			if (routeStart.equalsIgnoreCase(routeEnd))
				throw new RoutePlannerException(
						"Start route and destination route can't be the same");

			List<List<INode>> routes = pathAlgorithm.getRoutes(source,
					destination);
			TransferCalculator transferCalculator = (TransferCalculator) SpringBeanRetriever
					.getBean("transferCalculator");
			transferCalculator.setLineHolder(lineHolder);

			routesToTake = (Map<Integer, List<List<ITransfer>>>) SpringBeanRetriever
					.getBean("tMap");
			int cost = 0;

			List<List<ITransfer>> allRouteOfSameCost = (List<List<ITransfer>>) SpringBeanRetriever
					.getBean("aList");
			for (List<INode> listOfNodes : routes) {

				IRoute route = (IRoute) SpringBeanRetriever.getBean("route");
				route.setRoute(listOfNodes);
				int pathCost = pathAlgorithm.getPathCost(listOfNodes);
				List<ITransfer> transfers = transferCalculator.showTransfers(route);
				if(!routesToTake.containsKey(pathCost)){
					routesToTake.put(pathCost, (List<List<ITransfer>>) SpringBeanRetriever.getBean("aList"));
				}
				routesToTake.get(pathCost).add(transfers);
			}
			for (Integer key: routesToTake.keySet()) {
				List<List<ITransfer>> listToOrder = routesToTake.get(key);
				Collections.sort(listToOrder, new Comparator<List<ITransfer>>(){
					@Override
					public int compare(List<ITransfer> o1, List<ITransfer> o2) {
						int size1 = o1.size();
						int size2 = o2.size();
						return size1 - size2;
					}
				});
				routesToTake.put(key, listToOrder);
			}
			routesToTake.put(cost, allRouteOfSameCost);

			setRoutesToTake(routesToTake);
			return routesToTake;
		} catch (RoutePlannerException errorMessage) {

			setErrorMessage(errorMessage.getMessage());
			return null;
		}

	}

}
