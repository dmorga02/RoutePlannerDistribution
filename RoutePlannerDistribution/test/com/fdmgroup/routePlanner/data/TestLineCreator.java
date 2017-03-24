package com.fdmgroup.routePlanner.data;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.fdmgroup.routePlanner.exception.RoutePlannerException;
import com.fdmgroup.routePlanner.lines.ILine;
import com.fdmgroup.routePlanner.lines.SubwayLine;
import com.fdmgroup.routePlanner.nodes.INode;
import com.fdmgroup.routePlanner.spring.SpringBeanRetriever;

public class TestLineCreator {
	private LineCreator creator;
	private NodeHolder nodes;
	private String filename = "LondonUnderground.xml";
	private String filename2=null;
	private SubwayLine s1;
	private List<INode> nodesList;
	
	@BeforeClass
	public static void prenit() throws RoutePlannerException, IOException {
		SpringBeanRetriever.init("beans.xml");
	}

	@AfterClass
	public static void cleanup() {
		SpringBeanRetriever.close();
	}
	
	@Before
	public void init() throws RoutePlannerException, IOException {
		
		creator = new LineCreator();
		nodes = mock(NodeHolder.class);
		creator.setNodes(nodes);
		LineDataReader beandatareader = ((LineDataReader) SpringBeanRetriever.getBean("lineDataReader"));
		LineDataReader datareader = spy(beandatareader);
		
		creator.setReader(datareader);
		Map<String, List<String>> mockline = (Map<String, List<String>>) SpringBeanRetriever.getBean("hMap");
		List<String> mocklist = (List<String>) SpringBeanRetriever.getBean("aList");
		mocklist.add("node1");
		mocklist.add("node2");
		mocklist.add("node3");
		mocklist.add("node4");
		mockline.put("key", mocklist);
		when(datareader.getNetworkData(Thread.currentThread()
				.getContextClassLoader().getResourceAsStream(filename))).thenReturn(mockline);
		
		s1 = mock(SubwayLine.class);
		when(s1.getName()).thenReturn("Test");
		when(s1.getNodes()).thenReturn(nodesList);
	}
	
	@Test 
	public void testMakeLines() {
		List<ILine> lines = null;
		try {
			lines = creator.makeLines(filename);
		} catch (RoutePlannerException e) {
			fail();
		} catch (IOException e) {
			fail();
		} 
		assertNotNull(lines);
		
		assertTrue(lines.size() != 0);
	}
	
	@Test (expected = RoutePlannerException.class)
	public void checkIfFilenameExists() throws RoutePlannerException, IOException
	{
		creator.makeLines(filename2);
	}
	@Test (expected = RoutePlannerException.class)
	public void checkIfFilenameExists2() throws RoutePlannerException, IOException
	{
		creator.makeLines("pom.xml");
	}
}
