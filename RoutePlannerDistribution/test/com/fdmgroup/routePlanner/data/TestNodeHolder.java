package com.fdmgroup.routePlanner.data;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.util.List;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.fdmgroup.routePlanner.exception.RoutePlannerException;
import com.fdmgroup.routePlanner.nodes.INode;
import com.fdmgroup.routePlanner.nodes.Station;
import com.fdmgroup.routePlanner.spring.SpringBeanRetriever;

public class TestNodeHolder {
	private NodeCreator creator;
	private NodeHolder holder;
	private String initfilename = "LondonUnderground.xml";
	private INode node1 = null;
	private INode node2 = null;
	private INode node3 = null;
	private INode node4 = null;
	private INode node5 = null;
	
	
	@BeforeClass
	public static void initialize() throws RoutePlannerException {
		SpringBeanRetriever.init("beans.xml");
	}
	@AfterClass
	public static void cleanup() {
		SpringBeanRetriever.close();
	}
	@Before
	public void init() throws RoutePlannerException, IOException {
		node1 = (Station) SpringBeanRetriever.getBean("node");
		node2 = (Station) SpringBeanRetriever.getBean("node");
		node3 = (Station) SpringBeanRetriever.getBean("node");
		node4 = (Station) SpringBeanRetriever.getBean("node");
		node5 = (Station) SpringBeanRetriever.getBean("node");
		node1.setName("ZLocation");
		node2.setName("BLocation");
		node3.setName("ELocation");
		node4.setName("TLocation");
		node5.setName("YLocation");
		node1.addAdjacentNode(node2, 1);
		
		creator = mock(NodeCreator.class);
		
		holder = new NodeHolder();
		List<INode> nodes = (List<INode>) SpringBeanRetriever.getBean("aList");
		
		nodes.add(node1);
		nodes.add(node2);
		nodes.add(node3);
		nodes.add(node4);
		nodes.add(node5);
		holder.setNodes(nodes);
		when(creator.makeNodes(initfilename)).thenReturn(nodes);
		try {
			nodes = creator.makeNodes(initfilename);
		} catch (RoutePlannerException e) {
			fail();
		} catch (IOException e) {
			fail();
		}
		
		
	}
	
	@Test 
	public void testCheckHasNodes() throws RoutePlannerException {
		assertTrue(holder.getNodes().size() == 5);
	}
	
	@Test (expected = RoutePlannerException.class)
	public void testCheckNotExistantNode() throws RoutePlannerException {
		holder.getNodeFromString("Idontexist");
	}
	
	@Test
	public void testExistantNode() throws RoutePlannerException {
		INode node = holder.getNodeFromString("ZLocation");
		assertTrue(node.getName().equals("ZLocation"));
		assertTrue(node.getAdjacentNodes().get(node2) == 1);
	}
	
	@Test
	public void testSort()	{
		
		holder.sortNodes();
		assertTrue(holder.getNodes().get(0).getName().equals("BLocation"));
		assertTrue(holder.getNodes().get(1).getName().equals("ELocation"));
		assertTrue(holder.getNodes().get(2).getName().equals("TLocation"));
		assertTrue(holder.getNodes().get(3).getName().equals("YLocation"));
		assertTrue(holder.getNodes().get(4).getName().equals("ZLocation"));
		
	}
	
}
