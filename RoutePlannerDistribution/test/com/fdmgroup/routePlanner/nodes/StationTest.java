package com.fdmgroup.routePlanner.nodes;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.HashMap;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import com.fdmgroup.routePlanner.exception.RoutePlannerException;

public class StationTest {
	private Station station,stationSpy;
	private Map<INode, Integer> adjacentNodes,adjacentNodesSpy;
	@Before
	public void init() throws RoutePlannerException {
		station = new Station();
		stationSpy=spy(station);
		adjacentNodes = new HashMap<INode, Integer>();
		adjacentNodesSpy=spy(adjacentNodes);
		adjacentNodesSpy.put(station, 1);
		when(stationSpy.getName()).thenReturn("King Circle");
		when(stationSpy.getAdjacentNodes()).thenReturn(adjacentNodesSpy);
		
	}

	@Test
	public void testGetName() {
		assertTrue(stationSpy.getName().equals("King Circle"));
		assertFalse(stationSpy.getName().equals("Kew"));
		verify(stationSpy,Mockito.times(2)).getName();
	}

	@Test
	public void testAdjacentNode() {
		assertTrue(adjacentNodesSpy.containsKey(station));
		assertTrue(!adjacentNodesSpy.isEmpty());
		assertTrue(adjacentNodesSpy.get(station)==1);
	}
	@Test (expected = RoutePlannerException.class)
	public void exceptionForSetNameWhenNullNameUsed() throws RoutePlannerException{
		stationSpy.setName(null);
		
	}
	@Test (expected=RoutePlannerException.class)
	public void exceptionForSetAdjacentNodeWhenNullAdjacentNodeUsed() throws RoutePlannerException{
		stationSpy.setAdjacentNodes(null);
	}
	@Test (expected=RoutePlannerException.class)
	public void exceptionForAddAdjacentNodeWhenNullNodeUsed() throws RoutePlannerException{
		stationSpy.addAdjacentNode(null, 1);
	}
	@Test (expected=RoutePlannerException.class)
	public void exceptionForAddAdjacentNodeWhenNullDistanceUsed() throws RoutePlannerException{
		stationSpy.addAdjacentNode(stationSpy, null);
	}
	@Test (expected=RoutePlannerException.class)
	public void exceptionForAddAdjacentNodeWhenNullDistanceNullNodeUsed() throws RoutePlannerException{
		stationSpy.addAdjacentNode(null, null);
	}

}
