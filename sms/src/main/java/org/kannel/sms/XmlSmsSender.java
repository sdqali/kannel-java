package org.kannel.sms;

import java.io.DataOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.Charset;
import org.kannel.xml.*;

/**
 * Send SMS to the smsbox by POSTing XML via HTTP.
 *
 * @author Garth Patil <garthpatil@gmail.com>
 */
public class XmlSmsSender
    extends SmsSender
{

    public XmlSmsSender(URL smsbox)
    {
	super(smsbox);
    }

    public SendStatus send(Sms sms) throws Exception
    {
 	MessageDocument message = buildXml(sms);

	HttpURLConnection connection = (HttpURLConnection)smsbox.openConnection();
	connection.setRequestMethod("POST");
	connection.setRequestProperty("Content-Type", "text/xml");
	connection.setUseCaches(false);
	connection.setDoInput(true);
	connection.setDoOutput(true);

	DataOutputStream os = new DataOutputStream(connection.getOutputStream());
	InputStream is = message.newInputStream();

	int read;
	byte[] buf = new byte[1024];
	while ( (read = is.read(buf) ) >= 0) {
	    os.write(buf, 0, read);
	}

	SendStatus status = SendStatus.parse(connection.getInputStream());
	status.setHttpStatus(connection.getResponseCode());
        connection.disconnect(); 

	return status;
    }

    private MessageDocument buildXml(Sms sms)
    {
 	MessageDocument message = MessageDocument.Factory.newInstance();
 	Submit submit = message.addNewMessage().addNewSubmit();

	if (f(sms.getUsername()) && f(sms.getPassword())) {
	    From from = submit.addNewFrom();
	    from.setUsername(sms.getUsername());
	    from.setPassword(sms.getPassword());
	    if (f(sms.getAccount())) {
		from.setAccount(sms.getAccount());
	    }
	}

	if (f(sms.getSmsc())) {
	    submit.setTo(sms.getSmsc());
	}

	if (f(sms.getFrom())) {
	    Address oa = submit.addNewOa();
	    oa.setNumber(sms.getFrom());
	}
	if (f(sms.getTo())) {
	    Address da = submit.addNewDa();
	    da.setNumber(sms.getTo());
	}

	if (f(sms.getText())) {
	    submit.setUd(sms.getText());
	}

// 	if (sms.getUdh().length > 0) {
// 	    submit.setUdh(new String(sms.getUdh()));
// 	}

	if (sms.getDlrMask() != null && sms.getDlrUrl() != null) {
	    Statusrequest statusrequest = submit.addNewStatusrequest();
	    statusrequest.setDlrMask(sms.getDlrMask());
	    statusrequest.setDlrUrl(sms.getDlrUrl().toString());
	}

	if (sms.getPid() != null) {
	    submit.setPid(sms.getPid().intValue());
	}

	if (sms.getRpi() != null) {
	    submit.setRpi(sms.getRpi());
	}

	if (sms.getValidity() != null) {
	    Time vp = submit.addNewVp();
	    vp.setDelay(sms.getValidity());
	}

	if (sms.getDeferred() != null) {
	    Time timing = submit.addNewTiming();
	    timing.setDelay(sms.getDeferred());
	}

	if (sms.getMclass() != null ||
	    sms.getMwi() != null ||
	    sms.getCoding() != null ||
	    sms.getCompress() != null ||
	    sms.getAltDcs() != null) {
	    Dcs dcs = submit.addNewDcs();
	    if (sms.getMclass() != null) dcs.setMclass(sms.getMclass());
	    if (sms.getMwi() != null) dcs.setMwi(sms.getMwi());
	    if (sms.getCompress() != null) dcs.setCompress(sms.getCompress());
	    if (sms.getCoding() != null) dcs.setCoding(sms.getCoding());
	    if (sms.getAltDcs() != null) dcs.setAltDcs(sms.getAltDcs());
	}
	return message;
    }

    private boolean e(String s) { return (s==null || s.trim().equals("")); }
    private boolean f(String s) { return (s!=null && !s.trim().equals("")); }

}