# kannel-java-sms #

A wrapper on the HTTP interface to send SMS messages exposed by Kannel: <http://kannel.org/download/kannel-userguide-snapshot/userguide.html#AEN4623>

This package also provides servlets for accepting SMSs and DLRs from Kannel. 

Usage:

    // Make an SMS
    Sms sms = new Sms();
    sms.setUsername("username");
    sms.setPassword("password");
    sms.setTo("4155551212");
    sms.setFrom("54321");
    sms.setText("Hello world!");

    // Using the HTTP GET interface
    SmsSender s = new HttpSmsSender(new URL("http://localhost:13013/cgi-bin/sendsms"));
    SendStatus status = s.send(sms);
    System.out.println(status.toString());
	    
    // Using the XML HTTP POST interface
    s = new XmlSmsSender(new URL("http://localhost:13013/cgi-bin/sendsms"));
    status = s.send(sms);
    System.out.println(status.toString());
