package org.kannel.sms.servlet;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Observable;
import java.util.Observer;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import org.apache.log4j.Logger;

/**
 * Abstract servlet for receiving requests from Kannel
 *
 * @author Garth Patil <garthpatil@gmail.com>
 */
public abstract class KannelServlet extends HttpServlet
{
    private static Logger logger = Logger.getLogger(KannelServlet.class);

    protected Observable obs;

    public void init(ServletConfig config) throws ServletException
    {
        super.init(config);
	//load observers from params
	obs = new Observable();
	String[] observers = config.getInitParameter("handlers").split(",");
	for (String s:observers) {
	    try {
		Observer o = (Observer)Class.forName(s).newInstance();
		logger.info("Loading handler "+s);
		obs.addObserver(o);
	    } catch (Exception e) {
		throw new ServletException(e);
	    }
	}
	logger.info("Loaded "+obs.countObservers()+" handlers");
    }

    protected Map<String,String> getHeaderMap(HttpServletRequest request) 
    {
	Map<String,String> m = new HashMap<String,String>();
	for (Enumeration e = request.getHeaderNames(); e.hasMoreElements() ;) {
	    String s = (String)e.nextElement();
	    if (s.contains("X-Kannel")) m.put(s, request.getHeader(s));
	}	
	return m;
     }

    protected String getContent(ServletInputStream is) throws IOException
    {
	BufferedReader rd = new BufferedReader(new InputStreamReader(is));
	String line;
	StringBuffer content = new StringBuffer();
	while((line = rd.readLine()) != null) {
	    content.append(line);
	}
	rd.close();
	return content.toString();
    }

}
