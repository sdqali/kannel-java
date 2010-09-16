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

}
