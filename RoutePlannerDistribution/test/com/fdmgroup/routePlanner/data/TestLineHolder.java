package com.fdmgroup.routePlanner.data;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.fdmgroup.routePlanner.exception.RoutePlannerException;
import com.fdmgroup.routePlanner.lines.ILine;
import com.fdmgroup.routePlanner.lines.SubwayLine;
import com.fdmgroup.routePlanner.spring.SpringBeanRetriever;

public class TestLineHolder {
	private static LineCreator creator;
	private static LineHolder holder;
	private static String filename = "LondonUnderground.xml";
	private ILine line1, line2, line3;
	
	@BeforeClass
	public static void initialization() {
		SpringBeanRetriever.init("beans.xml");
	}

	@AfterClass
	public static void cleanup() {
		SpringBeanRetriever.close();
	}
	@Before
	public void init() throws RoutePlannerException, IOException
	{
		line1 = (SubwayLine) SpringBeanRetriever.getBean("line");
		line1.setName("Test");
		
		line2 = (SubwayLine) SpringBeanRetriever.getBean("line");
		line2.setName("Test2");
		
		line3 = (SubwayLine) SpringBeanRetriever.getBean("line");
		line3.setName("Test3");
		
		List<ILine> lines = new ArrayList<ILine>();
		lines.add(line1);
		lines.add(line2);
		
		holder = (LineHolder) SpringBeanRetriever.getBean("lineHolder");
		holder.setLines(lines);
		//Mockito
		creator = mock(LineCreator.class);
		
		when(creator.makeLines(filename)).thenReturn(lines);
	}
	
	@Test
	public void test() throws RoutePlannerException
	{
		line3 = holder.getLineFromString("Test");
		assertTrue(line3.getName().equalsIgnoreCase("Test"));
	}
	
	@Test (expected=RoutePlannerException.class)
	public void testNotExists() throws RoutePlannerException {
		line3 = holder.getLineFromString("idontexistlol");		
	}
}
