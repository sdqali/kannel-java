package org.kannel.protocol.gateway;

import org.kannel.protocol.kbinds.KannelBinding;
import org.kannel.protocol.exceptions.PacketParseException;
import org.kannel.protocol.packets.AckMessage;
import org.kannel.protocol.packets.BasicPacket;
import java.io.IOException;

/**
 * This class reads packets from a kannel link. In order to create a useful implementation, you
 * may override onAck, onAdmin, onHeartbeat, onSms and onWdp.
 *
 * @author Oscar Medina Duarte
 * @author Garth Patil <garthpatil@gmail.com>
 */
public class KjReadingThread
    extends Thread
{

    protected KannelBinding kbind = null;
    protected AckCycleThread ackAdminThread = null;
    protected boolean halted = false;
    
    public KannelBinding getKbind() { return kbind; }
    public void setKbind(KannelBinding kbind) {	this.kbind = kbind; }

    /**
     *  Constructor for the KjReadingThread object
     * @param  kbind  KannelBinding link
     */
    public KjReadingThread(KannelBinding kbind) {
	this.kbind = kbind;
    }
	
    public void addAckCycleThread(AckCycleThread ackAdminThread){
	this.ackAdminThread = ackAdminThread;
    }
	
    public void halt(){
	halted = true;
    }

    public boolean halted(){
	return halted;
    }

    /**
     * Handles ACK packets from the kbind.
     * @param recPacket BasicPacket.ACK_PKT type
     */
    public void onAck(BasicPacket recPacket)
    {
	if(this.ackAdminThread != null){                                                                                                                          
	    this.ackAdminThread.confirmAck((AckMessage)recPacket);                                                                                                
	}                                                                                                                                                         
    }

    /**
     * Handles ADMIN packets from the kbind.
     * @param recPacket BasicPacket.ADMIN_PKT type
     */
    public void onAdmin(BasicPacket recPacket) {}

    /**
     * Handles HEARTBEAT packets from the kbind.
     * @param recPacket BasicPacket.HEARTBEAT_PKT type
     */
    public void onHeartbeat(BasicPacket recPacket) {}

    /**
     * Handles SMS packets from the kbind.
     * @param recPacket BasicPacket.SMS_PKT type
     */
    public void onSms(BasicPacket recPacket) {}

    /**
     * Handles WDP packets from the kbind.
     * @param recPacket BasicPacket.WDP_PKT type
     */
    public void onWdp(BasicPacket recPacket) {}

    /**
     *  Main processing method for the KjReadingThread object
     */
    public void run() {
	BasicPacket recPacket = null;
	int type = -1;
	System.out.println("Reading thread started.");
	while (!halted){
	    try{
		recPacket = kbind.read();
		if (recPacket != null){
		    type = recPacket.type.getIntValue();
		    
		    if(type == BasicPacket.ACK_PKT){
			onAck(recPacket);
		    }else if(type == BasicPacket.ADMIN_PKT) {
			onAdmin(recPacket);
		    }else if(type == BasicPacket.HEARTBEAT_PKT) {
			onHeartbeat(recPacket);
		    }else if(type == BasicPacket.SMS_PKT) {
			onSms(recPacket);
		    }else if(type == BasicPacket.WDP_PKT) {
			onWdp(recPacket);
		    }else{
			System.out.println("Unknown packet type: "+type);
		    }
		}
	    } catch(Exception e) {
		System.out.println("Packet parse exception");
	    }
	    try{this.sleep(100);}catch(Exception e){e.printStackTrace();}
	}
    }
}

