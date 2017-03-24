package com.fdmgroup.routePlanner.transfers;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.fdmgroup.routePlanner.exception.RoutePlannerException;
import com.fdmgroup.routePlanner.lines.ILine;
import com.fdmgroup.routePlanner.lines.SubwayLine;
import com.fdmgroup.routePlanner.nodes.Station;

public class SubwayTransferTest {

	private SubwayTransfer transfer,transferSpy;
	private Station stationA,stationB;
	private SubwayLine line;
	private List<ILine>lines,linesSpy;
	@Before
	public void init() throws RoutePlannerException {
		transfer=new SubwayTransfer();
		transferSpy=spy(transfer);
		stationA=mock(Station.class);
		stationB=mock(Station.class);
		line=mock(SubwayLine.class);
		lines=new ArrayList<ILine>();
		linesSpy=spy(lines);
		linesSpy.add(line);
		when(transferSpy.getStartNode()).thenReturn(stationA);
		when(transferSpy.getEndNode()).thenReturn(stationB);
		when(transferSpy.getLines()).thenReturn(linesSpy);

	}
	
	@Test
	public void testGetStartNode(){
		assertTrue(transferSpy.getStartNode().equals(stationA));
	}
	@Test
	public void testGetEndNode(){
		assertTrue(transferSpy.getEndNode().equals(stationB));
	}
	@Test
	public void testGetLines(){
		assertTrue(transferSpy.getLines().equals(linesSpy));
	}
	@Test (expected = RoutePlannerException.class)
	public void exceptionForSetStartNodeWhenNullNodeUsed() throws RoutePlannerException{
		transferSpy.setStartNode(null);
		
	}
	@Test (expected = RoutePlannerException.class)
	public void exceptionForSetEndNodeWhenNullNodeUsed() throws RoutePlannerException{
		transferSpy.setEndNode(null);
		
	}
	@Test (expected = RoutePlannerException.class)
	public void exceptionForSetLinesWhenNullLinesUsed() throws RoutePlannerException{
		transferSpy.setLines(null);
		
	}
	

}
