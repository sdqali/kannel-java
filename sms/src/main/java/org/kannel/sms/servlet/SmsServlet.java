package org.kannel.sms.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Observable;
import java.util.Observer;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;

public class SmsServlet extends HttpServlet
{
    private static Logger logger = Logger.getLogger(SmsServlet.class);
    private Observable obs;

    public void init(ServletConfig config) throws ServletException
    {
        super.init(config);
	//load observers from params
	obs = new Observable();
	String[] observers = config.getInitParameter("sms-handlers").split(",");
	for (String s:observers) {
	    try {
		Observer o = (Observer)Class.forName(s).newInstance();
		logger.info("Loading SMS handler "+s);
		obs.addObserver(o);
	    } catch (Exception e) {
		throw new ServletException(e);
	    }
	}
	logger.info("Loaded "+obs.countObservers()+" SMS handlers");
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

       //obs.notifyObservers(sms);
    }

    private Map<String,String> headersToMap(HttpServletRequest request) 
    {
	Map<String,String> m = new HashMap<String,String>();
	for (Enumeration e = request.getHeaderNames(); e.hasMoreElements() ;) {
	    String s = (String)e.nextElement();
	    m.put(s, request.getHeader(s));
	}	
	return m;
     }
}

//username	X-Kannel-Username
//password	X-Kannel-Password
//from	X-Kannel-From
//to	X-Kannel-To
//text	request body
//charset	charset as in Content-Type: text/html; charset=ISO-8859-1
//udh	X-Kannel-UDH
//smsc	X-Kannel-SMSC
//flash	X-Kannel-Flash (deprecated, see X-Kannel-MClass
//mclass	X-Kannel-MClass
//mwi	X-Kannel-MWI
//compress	X-Kannel-Compress
//coding	X-Kannel-Coding. If unset, defaults to 0 (7 bits) if Content-Type is text/plain , text/html or text/vnd.wap.wml. On application/octet-stream, defaults to 8 bits (1). All other Content-Type values are rejected.
//validity	X-Kannel-Validity
//deferred	X-Kannel-Deferred
//dlr-mask	X-Kannel-DLR-Mask
//dlr-url	X-Kannel-DLR-Url
//account	X-Kannel-Account
//pid	X-Kannel-PID
//alt-dcs	X-Kannel-Alt-DCS
//binfo	X-Kannel-BInfo
//rpi	X-Kannel-RPI
//priority	X-Kannel-Priority
