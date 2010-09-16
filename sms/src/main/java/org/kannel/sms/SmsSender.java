package org.kannel.sms;

import java.net.URL;
import java.nio.charset.Charset;

/**
 * Base class for sending SMS messages to the smsbox.
 *
 * @author Garth Patil <garthpatil@gmail.com>
 */
public abstract class SmsSender
{

    public SmsSender(URL smsbox)
    {
	this.smsbox = smsbox;
    }

    /**
     * smsbox smsbox
     */
    protected URL smsbox;
    public URL getSmsbox() { return this.smsbox; }
    public void setSmsbox(URL smsbox) { this.smsbox = smsbox; }

    /**
     * Send an SMS message.
     * @param sms The SMS message to send.
     * @return A SendStatus which includes status, message and the resulting HTTP status.
     */
    public abstract SendStatus send(Sms sms) throws Exception;

    /**
     * Main method for testing.
     * usage: SmsSender <from> <to> <text>
     */
    public static void main(String[] argv)
    {
	if (argv.length != 3) {
	    System.err.println("usage: SmsSender <from> <to> <text>");
	    System.exit(1);
	}

	Sms sms = new Sms();
	sms.setUsername("foo");
	sms.setPassword("bar");
	sms.setFrom(argv[0]);
	sms.setTo(argv[1]);
	sms.setText(argv[2]);

	try {
	    // try HTTP
	    SmsSender s = new HttpSmsSender(new URL("http://localhost:13013/cgi-bin/sendsms"));
	    SendStatus status = s.send(sms);
	    System.out.println(status.toString());
	    
	    // try XML
	    s = new XmlSmsSender(new URL("http://localhost:13013/cgi-bin/sendsms"));
	    status = s.send(sms);
	    System.out.println(status.toString());
	} catch (Exception e) {
	    e.printStackTrace();
	}
    }

}
