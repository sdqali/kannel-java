package org.kannel.sms.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Observable;
import java.util.Observer;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;

public class DlrServlet extends HttpServlet
{
    private static Logger logger = Logger.getLogger(DlrServlet.class);
    private Observable obs;

    public void init(ServletConfig config) throws ServletException
    {
        super.init(config);
	//load observers from params
	obs = new Observable();
	String[] observers = config.getInitParameter("dlr-handlers").split(",");
	for (String s:observers) {
	    try {
		Observer o = (Observer)Class.forName(s).newInstance();
		logger.info("Loading DLR handler "+s);
		obs.addObserver(o);
	    } catch (Exception e) {
		throw new ServletException(e);
	    }
	}
	logger.info("Loaded "+obs.countObservers()+" DLR handlers");
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) 
	throws ServletException,IOException {
	doPost(request, response);
    }
    
    public void doPost(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException
    {
       PrintWriter out = response.getWriter();
       out.println("ok");

       //obs.notifyObservers(dlr);
    }
}
