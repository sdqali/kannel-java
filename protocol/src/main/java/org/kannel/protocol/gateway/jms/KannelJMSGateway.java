package org.kannel.protocol.gateway.jms;

import java.io.File;
import java.io.FileInputStream;
import java.util.Properties;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.kannel.protocol.gateway.KannelGateway;
import org.kannel.protocol.exceptions.NotEnoughPropertiesException;
import org.kannel.protocol.exceptions.WrongPropertieException;
import org.kannel.protocol.kbinds.KannelBinding;

public class KannelJMSGateway
    extends KannelGateway
{
    public static final String props_JMSTransportClass = "kjGateway.JMSTransportClass";
	
    private JMSTransport jmsTransport = null;
	
    KannelJMSGateway()
    {
	super();
    }
    
    KannelJMSGateway(String args[]) throws FileNotFoundException,
					   IOException,
					   ClassNotFoundException,
					   InstantiationException,
					   IllegalAccessException
    {
	super(args);
	// default JMSTransport
	String swap = this.properties.getProperty(this.props_JMSTransportClass);
	//System.out.println(swap);
	this.jmsTransport = (JMSTransport)Class.forName(swap).newInstance();
    }
	
    public void run() {
	try {
	    if (this.jmsTransport == null) {
		System.err.println("No JMS Translator");
	    } else {
		this.jmsTransport.start(this.properties);
	    }
	    super.run();
	} catch (Exception e) {}
    }

    public JMSTransport getJMSTransport() { return this.jmsTransport; }
    public void setJMSTransport(JMSTransport jmsTransport) { this.jmsTransport = jmsTransport; }
	
    public static void main(String args[]) throws Exception{
	System.out.println("Kannel - JMS Gateway v0.1\n by: Oscar Medina Duarte\n moscar[at]gmail.com");
	KannelJMSGateway kjmsGateway = new KannelJMSGateway(args);
	kjmsGateway.setOutBoundThread(new JMSWritingThread(kjmsGateway.kannelBind, kjmsGateway.jmsTransport));
	kjmsGateway.setInBoundThread(new JMSReadingThread(kjmsGateway.kannelBind, kjmsGateway.jmsTransport));
	kjmsGateway.start();
    }
}
