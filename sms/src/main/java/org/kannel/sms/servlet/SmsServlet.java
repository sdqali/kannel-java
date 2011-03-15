package org.kannel.sms.servlet;

import java.io.InputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.Charset;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;
import org.kannel.sms.Sms;
import org.kannel.sms.UrlTemplate;
import org.kannel.xml.*;

/**
 * A sample servlet for receiving SMSs from Kannel.
 *
 * @author Garth Patil <garthpatil@gmail.com>
 */
public class SmsServlet extends KannelServlet
{
    private static Logger logger = Logger.getLogger(SmsServlet.class);

    public void service(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException
    {
	Sms sms = null;
	Map h = getHeaderMap(request);
	if (h.size() > 1) {
	    //X-Kannel header request
	    sms = Sms.buildFromHeaders(h);
	    sms.setCharset(Charset.forName(request.getCharacterEncoding()));
	    sms.setText(getContent(request.getInputStream()));
	} else if (request.getContentType().contains("xml")) {
	    //XML post
	    sms = smsFromXml(request.getInputStream());
	} else {
	    //parameters
	    UrlTemplate u = new UrlTemplate(null).all();
	    sms = Sms.buildFromTemplate(u, request.getParameterMap());
	}

	PrintWriter out = response.getWriter();
	out.println("ok");

	obs.notifyObservers(sms);
    }


    /**
     * TODO: Convert XML MessageDocument to Sms object.
     */
    private Sms smsFromXml(InputStream is) throws IOException
    {
	try {
	    Submit s = MessageDocument.Factory.parse(is).getMessage().getSubmit();
	    Sms sms = new Sms();
	    //   <submit>
	    //       <da><number>destination number (to)</number></da>
	    sms.setTo(s.getDa().getNumber());
	    //       <oa><number>originating number (from)</number></oa>
	    sms.setFrom(s.getOa().getNumber());
	    //       <ud>user data (text)</ud>
	    sms.setText(s.getUd());
	    //       <udh>user data header (udh)</udh>
	    sms.setUdh(s.getUdh().getBytes()); //TODO: test this is correct
	    //     <meta-data>meta-data</meta-data>
	    //     <dcs>
	    //       <mclass>mclass</mclass>
	    sms.setMclass(s.getDcs().getMclass());
	    //       <coding>coding</coding>
	    sms.setCoding(s.getDcs().getCoding());
	    //       <mwi>mwi</mwi>
	    sms.setMwi(s.getDcs().getMwi());
	    //       <compress>compress</compress>
	    sms.setCompress(s.getDcs().getCompress());
	    //       <alt-dcs>alt-dcs</alt-dcs>
	    sms.setAltDcs(s.getDcs().getAltDcs());
	    //     </dcs>
	    //     <pid>pid</pid>
	    sms.setPid((new Integer(s.getPid()).byteValue()));
	    //     <rpi>rpi</rpi>
	    sms.setRpi(s.getRpi());
	    //     <vp>
	    //       <delay>validity time in minutes</delay>
	    sms.setValidity(s.getVp().getDelay());
	    //     </vp>
	    //     <timing>
	    //       <delay>deferred time in minutes</delay>
	    sms.setDeferred(s.getTiming().getDelay());
	    //     </timing>
	    //     <!-- request from application to Kannel -->
	    //     <statusrequest>
	    //       <dlr-mask>dlr-mask</dlr-mask>
	    //       <dlr-url>dlr-url</dlr-url>
	    //     </statusrequest>
	    //     <from>
	    //       <user>username</user>
	    //       <username>username</username> 
	    //       <pass>password</pass>
	    //       <password>password</password>
	    //       <account>account</account>
	    //     </from>
	    //     <to>smsc-id</to>
	    //     <!-- request from Kannel to application -->
	    //     <from>smsc-id</from>
	    sms.setSmsc(s.getFrom().toString()); //TODO: this may be incorrect
	    //     <to>service-name</to>
	    sms.setService(s.getTo());
	    //   </submit>
	    return sms;
	} catch (Exception e) {
	    throw new IOException(e);
	}
    }

}
