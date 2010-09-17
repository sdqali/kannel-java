package org.kannel.protocol.gateway.jms;

import org.kannel.protocol.gateway.KjReadingThread;
import org.kannel.protocol.kbinds.KannelBinding;
import org.kannel.protocol.packets.SMSPacketMessage;
import org.kannel.protocol.packets.BasicPacket;
import java.io.IOException;

/**
 * This class reads packets from a kannel link and redirects them to a JMS Queue
 *
 * @author Oscar Medina Duarte
 * @author Garth Patil <garthpatil@gmail.com>
 */
public class JMSReadingThread
    extends KjReadingThread
{

    private JMSTransport jmsTransport = null;
	
    /**
     * Constructor for the JMSReadingThread object
     * @param kbind KannelBinding link
     */
    public JMSReadingThread(KannelBinding kbind) {
	super(kbind);
    }
	
    /**
     * Constructor for the JMSReadingThread object
     * @param kbind KannelBinding link
     * @param jmsTransport JMSTransport
     */
    public JMSReadingThread(KannelBinding kbind, JMSTransport jmsTransport) {
	super(kbind);
	this.addJMSTransport(jmsTransport);
    }

    public void addJMSTransport(JMSTransport jmsTransport){
	this.jmsTransport = jmsTransport;
    }
    
    /**
     * Handles SMS packets from the kbind.
     * @param recPacket BasicPacket.SMS_PKT type
     */
    public void onSms(BasicPacket recPacket)
    {
	this.jmsTransport.gotMOMessage((SMSPacketMessage)recPacket);
    }

}

