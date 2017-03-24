package com.fdmgroup.routePlanner.data;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.fdmgroup.routePlanner.exception.RoutePlannerException;
import com.fdmgroup.routePlanner.nodes.INode;
import com.fdmgroup.routePlanner.spring.SpringBeanRetriever;

public class TestNodeCreator {
	private NodeCreator creator;
	private String filename = "LondonUnderground.xml";
	
	@BeforeClass
	public static void initialize() {
		SpringBeanRetriever.init("beans.xml");
	}

	@AfterClass
	public static void cleanup() {
		SpringBeanRetriever.close();
	}
	@Before
	public void init() throws RoutePlannerException {
		creator = (NodeCreator) SpringBeanRetriever.getBean("nodeCreator");
	}
	
	
	@Test 
	public void testMakeNodes() throws RoutePlannerException {
		List<INode> nodes = null;
		
		try {
			nodes = creator.makeNodes(filename);
		} catch (RoutePlannerException e) {
			fail();
		} catch (IOException e) {
			fail();
		}
		assertNotNull(nodes);
		for (int loop = 0; loop < nodes.size(); loop++) {
			Map<INode, Integer> adjs = nodes.get(loop).getAdjacentNodes();
			assertTrue(adjs.size() != 0);			
		}
	}
	
	@Test (expected = RoutePlannerException.class)
	public void checkIfFilenameExists() throws IOException, RoutePlannerException {
		creator.makeNodes("Idontexistlol.txt");
	}
	@Test (expected = RoutePlannerException.class)
	public void checkIfFilenameExists2() throws IOException, RoutePlannerException	{
		creator.makeNodes("pom.xml");
	}
	
}
