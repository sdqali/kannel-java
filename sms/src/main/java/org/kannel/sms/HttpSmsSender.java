package org.kannel.sms;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

/**
 * Send SMS to the smsbox using the HTTP GET method and URL parameters.
 *
 * @author Garth Patil <garthpatil@gmail.com>
 */
public class HttpSmsSender
    extends SmsSender
{

    public HttpSmsSender(URL smsbox)
    {
	super(smsbox);
    }

    public SendStatus send(Sms sms) throws Exception
    {
	URL u = new URL(smsbox.toString() + "?" + createQuery(sms));
	HttpURLConnection connection = (HttpURLConnection)u.openConnection();
	connection.setRequestProperty("Accept", "text/plain");
	connection.connect();
	InputStream is = connection.getInputStream();
	SendStatus status = SendStatus.parse(connection.getInputStream());
	status.setHttpStatus(connection.getResponseCode());
        connection.disconnect(); 
	return status;
    }

    private String createQuery(Sms sms)
    {
	Map<String,String> params = new HashMap<String,String>();
	add("username", sms.getUsername(), params);
	add("password", sms.getPassword(), params);
	add("from", sms.getFrom(), params);
	add("to", sms.getTo(), params);
	add("text", sms.getText(), params);
	//charset
	// Charset of text message. Used to convert to a format suitable for 7 bits or to UCS-2.
	// Defaults to UTF-8 if coding is 7 bits and UTF-16BE if coding is UCS-2.
	//add("charset", sms.getCharset(), params);
	//udh
	// Optional User Data Header (UDH) part of the message. Must be URL encoded.
	//add("udh", new String(sms.getUdh()), params);
	add("smsc", sms.getSmsc(), params);
	add("mclass", sms.getMclass(), params);
	add("mwi", sms.getMwi(), params);
	add("compress", sms.getCompress(), params);
	add("coding", sms.getCoding(), params);
	add("validity", sms.getValidity(), params);
	add("deferred", sms.getDeferred(), params);
	add("dlr-mask", sms.getDlrMask(), params);
	//dlr-url
	add("dlr-url", sms.getDlrUrl(), params);
	//pid
	//add("pid", sms.getPid().intValue(), params);
	if (sms.getPid() != null) params.put("pid", Integer.toString(sms.getPid().intValue()));
	add("alt-dcs", sms.getAltDcs(), params);
	add("rpi", sms.getRpi(), params);
	add("account", sms.getAccount(), params);
	add("binfo", sms.getBinfo(), params);
	add("priority", sms.getPriority(), params);
	return join(params);
    }
    
    private Map<String,String> add(String name, Object value, Map<String,String> m)
    {
	if (value != null) {
	    String s = value.toString();
	    if (!s.equals("")) m.put(name, s);
	}
	return m;
    }

    private String join(Map<String,String> params)
    {
	Set<Map.Entry<String,String>> entries = params.entrySet();
	int i = 0;
	StringBuffer o = new StringBuffer();
	for (Map.Entry<String,String> entry:entries) {
	    i++;
	    o.append(entry.getKey()).append("=").append(URLEncoder.encode(entry.getValue()));
	    if (i == entries.size()) break;
	    else o.append("&");
	}
	return o.toString();
    }

}