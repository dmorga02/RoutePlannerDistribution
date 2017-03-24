package com.fdmgroup.routePlanner.data;

import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.fdmgroup.routePlanner.exception.RoutePlannerException;
import com.fdmgroup.routePlanner.lines.ILine;
import com.fdmgroup.routePlanner.lines.SubwayLine;
import com.fdmgroup.routePlanner.spring.SpringBeanRetriever;

public class LineFactoryTest {
	private SubwayLine line;

	@BeforeClass
	public static void beforeClass(){
		SpringBeanRetriever.init("beans.xml");
	}
	@Before
	public void init() throws RoutePlannerException {
		line = (SubwayLine) LineFactory
				.getInstance();
	}

	@Test
	public void testGetInstance() {

		assertTrue(line instanceof ILine);
	}
}
