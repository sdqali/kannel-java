package org.kannel.protocol.jms;

import org.kannel.protocol.packets.SMSPacketMessage;
import java.util.Properties;

public interface JMSTransport {

	public void empty(Object obj);
	
	public void gotMOMessage(SMSPacketMessage obj);
	
	public void gotMTMessage(Object obj);
	
	public void addKjWritingThread(KjWritingThread writer);
	
	public void start(Properties props);
}
