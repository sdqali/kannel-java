package org.kannel.protocol.jms;

import org.kannel.protocol.packets.KUUID;
import java.util.Enumeration;
import java.util.Hashtable;
import org.kannel.protocol.packets.SMSPacketMessage;
import org.kannel.protocol.packets.AckMessage;

public class AckCycleThread extends Thread{

	public static final String prop_waitForAckTTL = "kjGateway.waitForAckTTL";
	public static final String prop_acknowledgementCycleRate = "kjGateway.acknowledgementCycleRate";
	
	private KjWritingThread outWriting = null;
	private long frecuency = 100;
	private boolean keepRuning = true;
	private Enumeration uuidList = null;
	private Hashtable uuidHash = null;
	private Hashtable smsHash = null;
	private long ackTTL = 0;
	
	public AckCycleThread(KjWritingThread outWriting, long frecuency){
		this.outWriting = outWriting;
		this.frecuency = frecuency;
		uuidHash = new Hashtable();
		smsHash = new Hashtable();
		
	}
	
	public void confirmAck(AckMessage ackMessage){
		this.confirmAck(ackMessage.getUuid());
	}
	
	public void confirmAck(KUUID uuid){
		/// remove uuid from uuidHash
		// remove sms from smsHash
		synchronized(uuidHash){
			//System.out.println("Confirmando" + uuid);
			uuidHash.remove(uuid);
			uuidList = uuidHash.keys();
			this.yield();
		}
		synchronized(smsHash){
			smsHash.remove(uuid);
			this.yield();
		}
	}
	
	public void waitAck(SMSPacketMessage smsPacketMessage){
		long currentTime = System.currentTimeMillis();
		// Handle both Hashes which are sync
		// and add expiration time, wich is 
		// currentTime + waitForAckTTL prop
		synchronized(uuidHash){
			//System.out.println("esperando" + smsPacketMessage.getUuid());
			uuidHash.put(smsPacketMessage.getUuid(),
				new Long(ackTTL + System.currentTimeMillis()));
			// This is a very un optimized stuff to do
			// How should we change it to be a lot more efficient?
			uuidList = uuidHash.keys();
			this.yield();
		}
		synchronized(smsHash){
			smsHash.put(smsPacketMessage.getUuid(), smsPacketMessage);
			this.yield();
		}
		
	}
	
	public void setAckTTL(long ackTTL) {
		this.ackTTL = ackTTL;
	}
	public long getAckTTL() {
		return ackTTL;
	}

	
	public void halt(){
		keepRuning = false;
	}
	
	public boolean isHalted(){
		return !keepRuning;
	}
	
	public void run(){
		long currentTime = 0;
		Object kuuid = null;
		keepRuning = false;
		System.out.println("AckThread starting");
		while(true){
			
			if (uuidList != null){
				synchronized(uuidList){
					// check out things
					// System.out.println("ack ing : 1");
					if (uuidList != null){
						currentTime = System.currentTimeMillis();
						for (; uuidList.hasMoreElements() ;){
							// Compare UUID's expiration time with currentTime
							// System.out.println(e.nextElement());
							kuuid = uuidList.nextElement();
							if( currentTime > ((Long)uuidHash.get(kuuid)).longValue() ){
								// Si expiro, lo removemos de la lista...
								//System.out.println("Remover ack expirado");
								uuidHash.remove(kuuid);
								smsHash.remove(kuuid);
								uuidList = uuidHash.keys();
								//System.out.println("Ack expirado removido");
							}else{
								// Reenviamos msg
								//System.out.println("Hay mensajes para reenviar..");
								//outWriting.rawWrite(((SMSPacketMessage)smsHash.get(kuuid)).getMessage());
							}
						}
					}
				}
			}
			try{
				this.sleep(frecuency);
			}catch(InterruptedException e){
				System.out.println("Ack waiting Interrupted...");
			}
			// Obtener nuevo enumeration
			
			// Garbage collection
			System.gc();
		}
	}
	
}
