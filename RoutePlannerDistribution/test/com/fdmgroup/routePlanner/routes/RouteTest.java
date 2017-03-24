package com.fdmgroup.routePlanner.routes;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.fdmgroup.routePlanner.exception.RoutePlannerException;
import com.fdmgroup.routePlanner.nodes.INode;
import com.fdmgroup.routePlanner.nodes.Station;

public class RouteTest {

	private Route route,routeSpy;
	private List<INode> routeList,routeListSpy;
	private Station station;
	
	@Before
	public void init() throws RoutePlannerException {
		route=new Route();
		routeSpy=spy(route);
		
		routeList=new ArrayList<INode>();
		routeListSpy=spy(routeList);
		
		station=mock(Station.class);
		routeListSpy.add(station);
		when(routeSpy.getRoute()).thenReturn(routeListSpy);
	}
	@Test (expected = RoutePlannerException.class)
	public void exceptionForSetRouteWhenNullRouteUsed() throws RoutePlannerException{
		routeSpy.setRoute(null);
		
	}
	
	@Test
	public void testGetRoute(){
		assertFalse(routeSpy.getRoute().isEmpty());
		assertTrue(routeSpy.getRoute().contains(station));
	}

}
