package com.fdmgroup.routePlanner.exception;
/**
 * Personalised exception class to be used when needed in the application
 * @author Alex.Fong, Egzon.Zuta
 *
 */
public class RoutePlannerException extends Exception{

	private static final long serialVersionUID = 8203761557232299079L;

		public RoutePlannerException(String message)
	    {
	        super(message);
	    }
  
	    public RoutePlannerException(Exception e,String message)
	    {
	        super(message,e);
	    }
	  
	    public RoutePlannerException(Exception e)
	    {
	        super(e);
	    }
}
