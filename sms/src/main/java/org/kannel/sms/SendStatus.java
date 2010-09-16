package org.kannel.sms;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * The returned status of an SMS sent to the smsbox.
 *
 * @author Garth Patil <garthpatil@gmail.com>
 */
public class SendStatus
{

    private SendStatus(int status, String message)
    {
	this.status = status;
	this.message = message;
    }

    private int status;
    public int getStatus() { return this.status; }

    private String message;
    public String getMessage() { return this.message; }

    private int httpStatus;
    public int getHttpStatus() { return this.httpStatus; }
    public void setHttpStatus(int httpStatus) { this.httpStatus = httpStatus; }

    public static SendStatus parse(InputStream is) throws Exception
    {
	BufferedReader rd = new BufferedReader(new InputStreamReader(is));
	String line;
	StringBuffer response = new StringBuffer(); 
	while((line = rd.readLine()) != null) {
	    response.append(line);
	    response.append('\n');
	}
	rd.close();

	if (response.indexOf(":")>0) {
	    String[] s = response.toString().split(":");
	    return new SendStatus(Integer.parseInt(s[0]), s[1].trim());
	} else {
	    throw new Exception("Unable to parse response: "+response.toString());
	}
    }

    public String toString()
    {
	StringBuffer o = new StringBuffer();
	o.append("Status: ").append(status).append(", Message: ").append(message).append(", HTTP: ").append(httpStatus);
	return o.toString();
    }

}
