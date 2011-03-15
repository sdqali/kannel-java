# kannel-java-protocol #

The Kannel "box" protocol for building event-driven applications to interact with the "bearerbox". (The protocol library was built originally by Oscar Medina Duarte for his graduate thesis project. This project is forked from that one. Original sources can be found on his website <http://www.medina-web.com/kjGateway/index.html>). Currently, the "box" protocol is compatible only with the 1.5.0 development release of Kannel. This library can be used to build Java applications that behave like Kannel's "smsbox" to send/receive messages from the "bearerbox" (e.g. using a JMS queue).

For examples of how to use the library, see org.kannel.protocol.gateway.KannelGateway, org.kannel.protocol.gateway.jms.KannelJMSGateway and the main() method in org.kannel.protocol.kbinds.KannelBindingQueue.
