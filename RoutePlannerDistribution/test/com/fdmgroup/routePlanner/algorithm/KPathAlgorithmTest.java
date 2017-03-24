package com.fdmgroup.routePlanner.algorithm;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.naming.NamingException;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

import com.fdmgroup.routePlanner.exception.RoutePlannerException;
import com.fdmgroup.routePlanner.nodes.INode;
import com.fdmgroup.routePlanner.nodes.Station;
import com.fdmgroup.routePlanner.spring.SpringBeanRetriever;

public class KPathAlgorithmTest {

	private KPathAlgorithm pathAlgo;
	
	private static ApplicationContext context;
	
	private INode source;
	private INode firstNode;
	private INode secondNode;
	private INode thirdNode;
	private INode fourthNode;
	private INode fifthNode;
	private INode sixthNode;
	private INode seventhNode;
	private INode destination;
	
	private Map<INode, Integer> sourceNodeMap;
	private Map<INode, Integer> firstNodeMap;
	private Map<INode, Integer> secondNodeMap;
	private Map<INode, Integer> thirdNodeMap;
	private Map<INode, Integer> fourthNodeMap;
	private Map<INode, Integer> fifthNodeMap;
	private Map<INode, Integer> sixthNodeMap;
	private Map<INode, Integer> seventhNodeMap;
	private Map<INode, Integer> destinationNodeMap;
	
	@BeforeClass
    public static void setUpClass() throws NamingException, SQLException{
		
		context = new FileSystemXmlApplicationContext("/src/beans.xml");
		SpringBeanRetriever.setContext(context);
	}	
	
	@AfterClass
	public static void cleanUp() throws NamingException{
		SpringBeanRetriever.close();
	}
	
	
	@Before
	public void init() throws RoutePlannerException{
		pathAlgo = (KPathAlgorithm) SpringBeanRetriever.getBean("IPathAlgorithm");
		
		source = mock(Station.class);
		when(source.toString()).thenReturn("source");
		firstNode = mock(Station.class);
		when(firstNode.toString()).thenReturn("firstNode");
		secondNode = mock(Station.class);
		when(secondNode.toString()).thenReturn("secondNode");
		thirdNode = mock(Station.class);
		when(thirdNode.toString()).thenReturn("thirdNode");
		fourthNode = mock(Station.class);
		when(fourthNode.toString()).thenReturn("fourthNode");
		fifthNode = mock(Station.class);
		when(fifthNode.toString()).thenReturn("fifthNode");
		sixthNode = mock(Station.class);
		when(sixthNode.toString()).thenReturn("sixthNode");
		seventhNode = mock(Station.class);
		when(seventhNode.toString()).thenReturn("seventNode");
		destination = mock (Station.class);
		when(destination.toString()).thenReturn("destination");
		
		sourceNodeMap = new HashMap<INode, Integer>();
		sourceNodeMap.put(firstNode, 1);
		when(source.getAdjacentNodes()).thenReturn(sourceNodeMap);
				
		firstNodeMap = new HashMap<INode, Integer>();
		firstNodeMap.put(source, 1);
		firstNodeMap.put(secondNode, 1);
		firstNodeMap.put(thirdNode, 1);
		when(firstNode.getAdjacentNodes()).thenReturn(firstNodeMap);
				
		secondNodeMap = new HashMap<INode, Integer>();
		secondNodeMap.put(firstNode, 1);
		secondNodeMap.put(fourthNode, 1);
		secondNodeMap.put(destination, 1);
		when(secondNode.getAdjacentNodes()).thenReturn(secondNodeMap);
		
		thirdNodeMap = new HashMap<INode, Integer>();
		thirdNodeMap.put(firstNode, 1);
		thirdNodeMap.put(sixthNode, 1);
		thirdNodeMap.put(destination, 1);
		when(thirdNode.getAdjacentNodes()).thenReturn(thirdNodeMap);
		
		fourthNodeMap = new HashMap<INode, Integer>();
		fourthNodeMap.put(secondNode, 1);
		when(fourthNode.getAdjacentNodes()).thenReturn(fourthNodeMap);
		
		fifthNodeMap = new HashMap<INode, Integer>();
		fifthNodeMap.put(destination, 1);
		when(fifthNode.getAdjacentNodes()).thenReturn(fifthNodeMap);
		
		sixthNodeMap = new HashMap<INode, Integer>();
		sixthNodeMap.put(thirdNode, 1);
		when(sixthNode.getAdjacentNodes()).thenReturn(sixthNodeMap);
		
		seventhNodeMap = new HashMap<INode, Integer>();
		when(seventhNode.getAdjacentNodes()).thenReturn(seventhNodeMap);
		
		destinationNodeMap = new HashMap<INode, Integer>();
		destinationNodeMap.put(secondNode, 1);
		destinationNodeMap.put(thirdNode, 1);
		destinationNodeMap.put(fifthNode, 1);
		when(destination.getAdjacentNodes()).thenReturn(destinationNodeMap);
	}

	@Test (expected = RoutePlannerException.class)
	public void getRoutesThrowsExceptionForNullDestination() throws RoutePlannerException{
		pathAlgo.getRoutes(source, null);
	}

	@Test (expected = RoutePlannerException.class)
	public void getRoutesThrowsExceptionWhenSourceAndDestinationAreEqual () throws RoutePlannerException{
		pathAlgo.getRoutes(source, source);
	}
	
	@Test (expected = RoutePlannerException.class)
	public void getRoutesThrowsExceptionIfDestinationNotConnectedToSource() throws RoutePlannerException{
		pathAlgo.getRoutes(source, seventhNode);
	}
	
	@Test
	public void getRoutesReturnsMultipleEqualRoutes() throws RoutePlannerException{
		List<List<INode>> routes = pathAlgo.getRoutes(source, destination);
		assertTrue(routes.size() == 2);
		assertTrue(pathAlgo.getPathCost(routes.get(0)) == pathAlgo.getPathCost(routes.get(1)));
	}
	
	@Test
	public void getRoutesReturnsMultipleUnequalRoutes() throws RoutePlannerException{
		pathAlgo.setMaxRoutes(2);
		List<List<INode>> routes = pathAlgo.getRoutes(source, secondNode);
		assertTrue(routes.size() == 2);
		assertFalse(pathAlgo.getPathCost(routes.get(0)) == pathAlgo.getPathCost(routes.get(1)));
	}
	
	@Test
	public void canCalculateCostOfAPath() throws RoutePlannerException{
		List<INode> path = new ArrayList<INode>();
		path.add(source);
		path.add(firstNode);
		path.add(thirdNode);
		path.add(sixthNode);
		assertTrue(pathAlgo.getPathCost(path) == 3);
	}
	
	@Test (expected = RoutePlannerException.class)
	public void getPathCostThrowsExceptionIfPathContainsUnconnectedNodes() throws RoutePlannerException{
		List<INode> path = new ArrayList<INode>();
		path.add(source);
		path.add(firstNode);
		path.add(destination);
		pathAlgo.getPathCost(path);
	}
	
	@Test (expected = RoutePlannerException.class)
	public void getPathCostThrowsExceptionIfPathContainsConsecutiveDuplicateNodes() throws RoutePlannerException{
		List<INode> path = new ArrayList<INode>();
		path.add(source);
		path.add(firstNode);
		path.add(firstNode);
		path.add(secondNode);
		pathAlgo.getPathCost(path);
	}
}
