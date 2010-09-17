package org.kannel.protocol.gateway.jms;

import org.kannel.protocol.packets.SMSPacketMessage;
import java.io.Serializable;
import java.io.InputStreamReader;
import java.io.FileInputStream;
import java.io.File;
import java.io.IOException;
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
import javax.jms.*;

public class SimpleService implements MessageListener {
	
	public static final String prop_ContentFile = "simpleService.contentFile";
	
	private String defaultMessage = "Contenido no encontrado.";
	private Properties properties = null;
	private Properties contenido = null;
	private SimpleQueueReceiverThread sqrThread = null;
	private long stress = 0;
	private long stressTime = 0;
	
	/*/////////////////////////////////////////////////////
	// JMS Related attributes
	private Context jndiContext = null;
	private TopicConnectionFactory queueConnectionFactory = null;
	private TopicConnection queueConnection = null;
	private TopicSession queueSession = null;
	private Topic inBoundTopic = null;
	private Topic outBoundTopic = null;
	private TopicPublisher outBoundPublisher = null;
	private TopicSubscriber inBoundSubscriber = null;
	private ObjectMessage outOMessage = null;
	private ObjectMessage inOMessage = null;
	/////////////////////////////////////////////////////*/
	
	/////////////////////////////////////////////////////
	// JMS Related attributes
	private Context jndiContext = null;
	private QueueConnectionFactory queueConnectionFactory = null;
	private QueueConnection queueConnection = null;
	private QueueSession queueSession = null;
	private Queue inBoundTopic = null;
	private Queue outBoundTopic = null;
	private QueueSender outBoundPublisher = null;
	private QueueReceiver inBoundSubscriber = null;
	private ObjectMessage outOMessage = null;
	private ObjectMessage inOMessage = null;
	/////////////////////////////////////////////////////
	
	
	public SimpleService(Properties props) throws NamingException,
							JMSException {
		this.properties = props;
		
		try {
		this.contenido = new Properties();
		this.contenido.load(new FileInputStream(
					new File(this.properties.getProperty(prop_ContentFile))));
					
		this.contenido.list(System.out);
		
		}catch(IOException e){
			System.out.println("Error cargando contenido...");
			this.contenido = null;
		}
		 
		
		// Set up all the JMS connection stuff
		if (this.properties != null){
			jndiContext = new InitialContext(this.properties);
		}else{
			jndiContext = new InitialContext();
		}
		
		queueConnectionFactory = (QueueConnectionFactory)jndiContext
					.lookup(this.properties.getProperty(SimpleJMSTransport.prop_ConnectionFactoryName));
					
		inBoundTopic = (Queue)jndiContext.lookup(this.properties.getProperty(SimpleJMSTransport.prop_OutBoundTopicName));
		outBoundTopic = (Queue)jndiContext.lookup(this.properties.getProperty(SimpleJMSTransport.prop_InBoundTopicName));
		
		queueConnection = queueConnectionFactory.createQueueConnection();
		//queueSession = queueConnection.createQueueSession(false, Session.AUTO_ACKNOWLEDGE);
		queueSession = queueConnection.createQueueSession(true, Session.AUTO_ACKNOWLEDGE);
		
		outBoundPublisher = queueSession.createSender(outBoundTopic);
		inBoundSubscriber = queueSession.createReceiver(inBoundTopic);
		
		inBoundSubscriber.setMessageListener(this);
		//sqrThread = new SimpleQueueReceiverThread(this, inBoundSubscriber);
		queueConnection.start();
		//sqrThread.start();
		
		
	}
	
	
	public void start(){
	
		
	
		System.out.println("Para terminar presiona *  ENTER");
		InputStreamReader inputStreamReader = new InputStreamReader(System.in);
		char last = '\0'; 
		while (!(last == '*')){
			try{
				last = (char) inputStreamReader.read();
			}catch(IOException e){
				System.out.println("I/O Error");
			}

		}

	}
	
	public void onMessage(Message parMessage){
		System.out.print(".");
		inOMessage = null;
		outOMessage = null;
		SimpleMessage mesg = null;
		String swap = null;
		try{
			if(parMessage instanceof ObjectMessage){
				inOMessage = (ObjectMessage) parMessage;
				// Mensaje listo para traducir y enviar a Kannel
				mesg = (SimpleMessage)inOMessage.getObject();
				
				swap = mesg.getMsgData().trim().toUpperCase();
				System.out.println("Contenido recibido: " + swap);
				if (this.contenido != null){
					swap = this.contenido.getProperty(swap);
					
					if(swap != null){
						System.out.println("Enviando contenido: " + swap);
						outOMessage = queueSession.createObjectMessage();
						outOMessage.setObject(new SimpleMessage(mesg.getReceiver(),
											mesg.getSender(),
											"",
											swap));
						//System.out.println("llego1");
						outBoundPublisher.send(outOMessage);
						queueSession.commit();
						//System.out.println("llego");
					}
				}
				
				/*outOMessage = queueSession.createObjectMessage();
				outOMessage.setObject(mesg);
				outBoundPublisher.send(outOMessage);
				stress++;
				if (stress <= 1){
					stressTime = System.currentTimeMillis();
				}
				if((stress % 1000) == 0){
					System.out
						.println("tiempo: " + (System.currentTimeMillis()-stressTime) + " millis.");
					stress = 0;
					stressTime = System.currentTimeMillis();
				}*/
				
			}else{
				System.out.println("Otro tipo de mensaje recibido:");
				System.out.println(parMessage.getClass().getName());
			}
		
		}catch( JMSException e){
			System.out.println("JMSException");
		}catch( Throwable tjms){
			System.out.println("Otra excepcion: " + tjms);
		}
	
	}
	
	public static void main(String arg[]) throws Exception {
		System.out.println("Starting SimpleService");
		Properties props = new Properties();
		props.load(new FileInputStream(
				new File(
				new String(System.getProperty("user.dir") +
				System.getProperty("file.separator") +
				"kjGateway.cfg"))));
				
		SimpleService serv = new SimpleService(props);
		serv.start();
	}

}
