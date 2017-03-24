package com.fdmgroup.routePlanner.lines;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.fdmgroup.routePlanner.exception.RoutePlannerException;
import com.fdmgroup.routePlanner.nodes.INode;
import com.fdmgroup.routePlanner.nodes.Station;

public class SubwayLineTest {
	private SubwayLine line;
	private List<INode>nodes;
	private Station station;

	@Before
	public void init() throws RoutePlannerException {
		line=new SubwayLine();
		line.setName("Circle");
		station=mock(Station.class);
		nodes=new ArrayList<INode>();
		nodes.add(station);
		line.setNodes(nodes);
	}
	@Test
	public void testGetName(){
		assertTrue(line.getName().equals("Circle"));
		assertFalse(line.getName().equals("District"));
	}
	@Test
	public void testNodes(){
		assertTrue(nodes.contains(station));
		assertFalse(nodes.isEmpty());
		assertFalse(line.getNodes().isEmpty());
		assertTrue(line.getNodes().contains(station));
	}
	@Test (expected = RoutePlannerException.class)
	public void exceptionForSetNameWhenNullNameUsed() throws RoutePlannerException{
		line.setName(null);
	}
	@Test (expected=RoutePlannerException.class)
	public void exceptionForSetNodesWhenNullNodesUsed() throws RoutePlannerException{
		line.setNodes(null);
	}
}
