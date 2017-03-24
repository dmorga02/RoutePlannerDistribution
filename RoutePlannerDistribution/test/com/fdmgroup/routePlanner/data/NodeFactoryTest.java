package com.fdmgroup.routePlanner.data;

import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.fdmgroup.routePlanner.exception.RoutePlannerException;
import com.fdmgroup.routePlanner.nodes.INode;
import com.fdmgroup.routePlanner.nodes.Station;
import com.fdmgroup.routePlanner.spring.SpringBeanRetriever;

public class NodeFactoryTest {
	private Station station;
	@BeforeClass
	public static void beforeClass(){
		SpringBeanRetriever.init("beans.xml");
	}
	@Before
	public void init() throws RoutePlannerException {
		station = (Station) NodeFactory
				.getInstance();
	}

	@Test
	public void testGetInstance() {

		assertTrue(station instanceof INode);
	}

}
