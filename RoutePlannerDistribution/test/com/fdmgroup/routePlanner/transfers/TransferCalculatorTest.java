package com.fdmgroup.routePlanner.transfers;

import static org.junit.Assert.assertTrue;
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

import com.fdmgroup.routePlanner.data.LineHolder;
import com.fdmgroup.routePlanner.exception.RoutePlannerException;
import com.fdmgroup.routePlanner.lines.ILine;
import com.fdmgroup.routePlanner.lines.SubwayLine;
import com.fdmgroup.routePlanner.nodes.INode;
import com.fdmgroup.routePlanner.nodes.Station;
import com.fdmgroup.routePlanner.routes.IRoute;
import com.fdmgroup.routePlanner.routes.Route;
import com.fdmgroup.routePlanner.spring.SpringBeanRetriever;


public class TransferCalculatorTest {

	private TransferCalculator tranCalc;
	private IRoute mockRoute;
	
	private INode source;
	private INode firstNode;
	private INode secondNode;
	private INode thirdNode;
	private INode destination;
	
	private Map<INode, Integer> sourceNodeMap;
	private Map<INode, Integer> firstNodeMap;
	private Map<INode, Integer> secondNodeMap;
	private Map<INode, Integer> thirdNodeMap;
	private Map<INode, Integer> destinationNodeMap;
	
	private LineHolder mockLineHolder;
	private ILine mockLineOne;
	private ILine mockLineTwo;
	
	@BeforeClass
    public static void setUpClass() throws NamingException, SQLException{
		SpringBeanRetriever.init("beans.xml");
	}	
	
	@AfterClass
	public static void cleanUp() throws NamingException{
		SpringBeanRetriever.close();
	}
	
	@Before
	public void init() throws RoutePlannerException{
		tranCalc = new TransferCalculator();

		source = mock(Station.class);
		when(source.toString()).thenReturn("source");
		when(source.getName()).thenReturn("source");
		
		firstNode = mock(Station.class);
		when(firstNode.toString()).thenReturn("firstNode");
		when(firstNode.getName()).thenReturn("firstNode");
		
		secondNode = mock(Station.class);
		when(secondNode.toString()).thenReturn("secondNode");
		when(secondNode.getName()).thenReturn("secondNode");
		
		thirdNode = mock(Station.class);
		when(thirdNode.toString()).thenReturn("thirdNode");
		when(thirdNode.getName()).thenReturn("thirdNode");
		
		destination = mock (Station.class);
		when(destination.toString()).thenReturn("destination");
		when(destination.getName()).thenReturn("destination");
		
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
		secondNodeMap.put(destination, 1);
		when(secondNode.getAdjacentNodes()).thenReturn(secondNodeMap);
		
		thirdNodeMap = new HashMap<INode, Integer>();
		thirdNodeMap.put(firstNode, 1);
		thirdNodeMap.put(destination, 1);
		when(thirdNode.getAdjacentNodes()).thenReturn(thirdNodeMap);
		
		destinationNodeMap = new HashMap<INode, Integer>();
		destinationNodeMap.put(secondNode, 1);
		destinationNodeMap.put(thirdNode, 1);
		when(destination.getAdjacentNodes()).thenReturn(destinationNodeMap);
		
		mockLineOne = mock(SubwayLine.class);
		when(mockLineOne.getName()).thenReturn("Green Line");
		when(mockLineOne.toString()).thenReturn("Green Line");
		List<INode> greenLine = new ArrayList<INode>();
		greenLine.add(source);
		greenLine.add(firstNode);
		greenLine.add(secondNode);
		when(mockLineOne.getNodes()).thenReturn(greenLine);
		
		
		mockLineTwo = mock(SubwayLine.class);
		when(mockLineTwo.getName()).thenReturn("Red Line");
		when(mockLineTwo.toString()).thenReturn("Red Line");
		List<INode> redLine = new ArrayList<INode>();
		redLine.add(secondNode);
		redLine.add(destination);
		when(mockLineTwo.getNodes()).thenReturn(redLine);
		
		mockRoute = mock (Route.class);
		List<INode> path = new ArrayList<INode>();
		path.add(source);
		path.add(firstNode);
		path.add(secondNode);
		path.add(destination);
		when(mockRoute.getRoute()).thenReturn(path);
		
		mockLineHolder = mock(LineHolder.class);
		List<ILine> allLines = new ArrayList<ILine>();
		allLines.add(mockLineOne);
		allLines.add(mockLineTwo);
		when(mockLineHolder.getLines()).thenReturn(allLines);
		
		tranCalc.setLineHolder(mockLineHolder);
		
	}
	
	@Test (expected = RoutePlannerException.class)
	public void displayTransferThrowsExceptionIfRouteIsNull() throws RoutePlannerException{
		tranCalc.showTransfers(null);
	}
	
	@Test (expected = RoutePlannerException.class)
	public void displayTransferThrowsExceptionIfPathInRouteIsNull() throws RoutePlannerException{
		IRoute route = mock (Route.class);
		tranCalc.showTransfers(route);
	}
	
	@Test (expected = RoutePlannerException.class)
	public void displayTransferThrowsExceptionIfPathInRouteIsEmpty() throws RoutePlannerException{
		IRoute route = mock (Route.class);
		List<INode> path = new ArrayList<INode>();
		when(route.getRoute()).thenReturn(path);
		tranCalc.showTransfers(route);
	}
	
	@Test
	public void displayTransferReturnsListOfTransfersForRoute() throws RoutePlannerException{
		List<ITransfer> transfers = tranCalc.showTransfers(mockRoute);
		assertTrue(transfers != null);
		assertTrue(transfers.size() == 2);
		
		for(ITransfer transfer : transfers){
			if(transfer.getLines().contains(mockLineOne)){
				assertTrue(transfer.getStartNode().equals(source));
				assertTrue(transfer.getEndNode().equals(secondNode));
			}
			else{
				assertTrue(transfer.getStartNode().equals(secondNode));
				assertTrue(transfer.getEndNode().equals(destination));
			}
		}
	}
}
