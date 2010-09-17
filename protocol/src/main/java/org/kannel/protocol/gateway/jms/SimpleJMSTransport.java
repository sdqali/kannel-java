package org.kannel.protocol.gateway.jms;

import org.kannel.protocol.gateway.KjWritingThread;
import org.kannel.protocol.packets.SMSPacketMessage;
import org.kannel.protocol.packets.AckMessage;
import java.io.Serializable;
import java.util.Properties;
import javax.jms.Message;
import javax.jms.Topic;
import javax.jms.TopicConnection;
import javax.jms.MessageListener;
import javax.jms.TopicConnectionFactory;
import javax.jms.TopicSubscriber;
import javax.jms.TopicPublisher;
import javax.jms.TopicSession;
import javax.jms.Session;
import javax.jms.ObjectMessage;
import javax.naming.NamingException;
import javax.jms.JMSException;
import javax.naming.Context;
import javax.naming.InitialContext;
import java.util.LinkedList;
import javax.jms.*;


public class SimpleJMSTransport
    implements JMSTransport, MessageListener
{
	
    public static final String prop_ConnectionFactoryName = "jmsTransport.ConnectionFactoryName";
    public static final String prop_InBoundTopicName = "jmsTransport.inBoundTopicName";
    public static final String prop_OutBoundTopicName = "jmsTransport.outBoundTopicName";
    public static final String prop_JMSTranslatorClass = "jmsTransport.JMSTranslatorClass";
    
    public static final int NOTSTARTED = -1;
    public static final int STARTED = 0;
    public static final int JMSFAILED = 1;
    public static final int JNDIFAILED = 2;
    public static final int CLASSLOADING = 4;
    public static final int OTHER = 256;
    
    private KjWritingThread writer = null;
    private Properties props = null;
    private JMSTranslator jmsTranslator = null;
    private Serializable inObject = null;
    private SMSPacketMessage outSMS = null;
    private int status = NOTSTARTED;
    private SimpleQueueReceiverThread sqrThread = null;
    
	
    /////////////////////////////////////////////////////
    // JMS Related attributes
    private Context jndiContext = null;
    private QueueConnectionFactory topicConnectionFactory = null;
    private QueueConnection topicConnection = null;
    private QueueSession topicSession = null;
    private Queue inBoundTopic = null;
    private Queue outBoundTopic = null;
    private QueueSender outBoundPublisher = null;
    private QueueReceiver inBoundSubscriber = null;
    private ObjectMessage outOMessage = null;
    private ObjectMessage inOMessage = null;
    /////////////////////////////////////////////////////
    
    
    public void start(Properties props)
    {
	try {
	    this.props = props;
	    
	    // get translator
	    String swap = this.props.getProperty(this.prop_JMSTranslatorClass);
	    this.jmsTranslator = (JMSTranslator)Class.forName(swap).newInstance();
	    
	    // Set up all the JMS connection stuff
	    if (this.props != null) {
		jndiContext = new InitialContext(this.props);
	    } else {
		jndiContext = new InitialContext();
	    }
	    
	    topicConnectionFactory = (QueueConnectionFactory)jndiContext
		.lookup(this.props.getProperty(prop_ConnectionFactoryName));
	    
	    inBoundTopic = (Queue)jndiContext.lookup(this.props.getProperty(prop_InBoundTopicName));
	    outBoundTopic = (Queue)jndiContext.lookup(this.props.getProperty(prop_OutBoundTopicName));
	    
	    topicConnection = topicConnectionFactory.createQueueConnection();
	    topicSession = topicConnection.createQueueSession(true, Session.AUTO_ACKNOWLEDGE);
	    
	    outBoundPublisher = topicSession.createSender(outBoundTopic);
	    inBoundSubscriber = topicSession.createReceiver(inBoundTopic);
	    
	    inBoundSubscriber.setMessageListener(this);
	    topicConnection.start();
	    
	    status = STARTED;
	} catch(JMSException e) {
	    status = JMSFAILED;
	} catch(NamingException e) {
	    status = JNDIFAILED;
	} catch(ClassNotFoundException e) {
	    status = CLASSLOADING;
	} catch(InstantiationException e) {
	    status = CLASSLOADING;
	} catch(IllegalAccessException e) {
	    status  = CLASSLOADING;
	} catch(Throwable e){
	    status = OTHER;
	}
    }
    
    public int getStatus() {
	return status;
    }
    
    
    public void empty(Object ack){
	System.out.println(ack);
    }
    
    public void onMessage(Message parMessage){
	System.out.println(".");
	inOMessage = null;
	outSMS = null;
	try {
	    if (parMessage instanceof ObjectMessage) {
		inOMessage = (ObjectMessage) parMessage;
		outSMS = this.jmsTranslator.objectToKannel((Object)inOMessage.getObject());
		writer.send(outSMS);
		topicSession.commit();
	    } else {
		System.out.println("Otro tipo de mensaje recibido:");
		System.out.println(parMessage.getClass().getName());
	    }
	    
	}/*catch( JMSException e){
	   System.out.println("JMSException");
	   }*/catch( Throwable tjms){
	    System.out.println("Otra excepcion");
	}
    }
	
    public void addKjWritingThread(KjWritingThread writer){
	this.writer = writer;
    }
    
    public void gotMOMessage(SMSPacketMessage smsMessage){
	// Read from Kannel
	// Write this message to JMS topic
	outOMessage = null;
	inObject = this.jmsTranslator.kannelToObject(smsMessage);
	try {
	    outOMessage = topicSession.createObjectMessage();
	    outOMessage.setObject(inObject);
	    outBoundPublisher.send(outOMessage);
	    topicSession.commit();
	    // Enviar ACK
	    System.out.println("writing ack");
	    writer.rawWrite((new AckMessage(AckMessage.ACK_STAT_SUCCESS, smsMessage)).getMessage());
	    System.out.println("ack written");
	} catch(Exception e) {
	    System.out.println("Message publishing failed: " + e);
	}
	
    }
    
    public void gotMTMessage(Object obj){
	// Read from a topic
	// Write this message to kannel link
	
    }
}

