package org.kannel.protocol.gateway;

import org.kannel.protocol.kbinds.KannelBinding;
import org.kannel.protocol.packets.SMSPacketMessage;
import org.kannel.protocol.packets.BasicKannelProtocolMessage;
import java.io.IOException;
import java.util.LinkedList;

/**
 * This class redirects to a Kannel link. In order to create a useful implementation, you
 * may override onAck, onAdmin, onHeartbeat, onSms and onWdp.
 *
 * @author Oscar Medina Duarte
 * @author Garth Patil <garthpatil@gmail.com>
 */
public class KjWritingThread
    extends Thread
{

    protected KannelBinding kbind = null;
    protected AckCycleThread ackAdminThread = null;
    protected LinkedList messages = null;
    
    /**
     * Constructor for the KjWritingThread object
     * @param  kbind  Description of the Parameter
     */
    public KjWritingThread(KannelBinding kbind)
    {
	this.kbind = kbind;
	this.messages = new LinkedList();
    }

    public void send(String sender, String receiver, String udhdata, String msgdata)
    {
	send(new SMSPacketMessage(sender, receiver, udhdata, msgdata));
    }
    
    public void send(SMSPacketMessage pack)
    {
	try {
	    if (this.ackAdminThread != null) {
		//System.out.println("set ack");
		this.ackAdminThread.waitAck(pack);
	    }
	    // System.out.println("write it");
	    this.kbind.writeNext((BasicKannelProtocolMessage)pack);
	} catch(IOException e) {
	    System.out.println("sendind message failed : " + e);
	}
    }

    public void sendOnThread(String sender, String receiver, String udhdata, String msgdata)
    {
	sendOnThread(new SMSPacketMessage(sender, receiver, udhdata, msgdata));
    }
    
    public void sendOnThread(SMSPacketMessage pack)
    {
	this.messages.add(pack);
    }
    
    public void rawWrite(byte[] pktMessage)
    {
	try {
	    this.kbind.rawWrite(pktMessage);
	} catch(IOException e) {
	    System.out.println("Raw writing failed : " + e);
	}
    }
    
    public void addAckCycleThread(AckCycleThread ackAdminThread){
	this.ackAdminThread = ackAdminThread;
    }
    
    /**
     *  Main processing method for the KjWritingThread object
     */
    public void run() {
	System.out.println("Writing thread started.");
	SMSPacketMessage sms = null;
	while(true){
	    if(this.messages.size() > 0){
		System.out.println("sending");
		sms = (SMSPacketMessage)this.messages.removeFirst();
		send(sms);
	    }
	}
    }

}

