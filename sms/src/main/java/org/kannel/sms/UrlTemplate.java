package org.kannel.sms;

import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

/**
 * A helper to create a get-url or dlr-url
 * For example:
 *   URL u = new UrlTemplate(new URL("http://myhost.com/dlr")).text().from("from").to("to").timeSent().smsc().dlrValue().addParameter("myId", "134719").toUrl();
 * will produce:
 *   http://myhost.com/dlr?a=%a&from=%p&to=%P%t=%t&i=%i&d=%d&myId=134719
 * Also creates a parser for sms and dlr requests to this URL.
 *
 * @author Garth Patil <garthpatil@gmail.com>
 */
public class UrlTemplate
{

    public UrlTemplate(URL baseUrl)
    {
	this.baseUrl = baseUrl;
	this.params = new HashMap<String,String>();
    }

    private URL baseUrl;
    private Map<String,String> params;

    /**
     * @return A url that combines the baseUrl with the query containing the parameters requested.
     */
    public URL toUrl() 
    {
	try {
	    return new URL(baseUrl.toString() + "?" + join(params));
	} catch (Exception e) {
	    throw new UnsupportedOperationException("URL could not be created with these parameters.", e);
	}
    }

    /**
     */
    public Sms parseSms(Map m)
    {
	Sms sms = new Sms();
	return (Sms)parse(sms, m);
    }

    /**
     */
    public Dlr parseDlr(Map m)
    {
	Dlr sms = new Dlr();
	return (Dlr)parse(sms, m);
    }

    /**
     * To be used when receiving a request to this UrlTemplate. In the case of using a servlet container,
     * call getParameterMap() on the ServletRequest, and pass the resulting Map to this method.
     * @param m The input parameter map parsed from the query.
     * @return A Msg object representing the values in the query.
     */
    public Msg parse(Msg msg, Map m)
    {
	Set<Map.Entry> entries = m.entrySet();
	for (Map.Entry entry:entries) {
	    String k = (String)entry.getKey();
	    String v = (String)entry.getValue();
	    String t = params.get(k);
	    if (t.equals("%k")) msg.setKeyword(v);
	    if (t.equals("%s")) msg.setSecondKeyword(v);
	    if (t.equals("%p")) msg.setSecondKeywordConverted(v);
	    if (t.equals("%r")) msg.setQuery(v);
	    if (t.equals("%a")) msg.setText(v);
	    if (t.equals("%b")) msg.setBinaryMessage(v.getBytes());
	    if (t.equals("%t")) { //timeSent
		//"YYYY-MM-DD HH:MM", e.g., "1999-09-21 14:18"
		try {
		    DateFormat fmt = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		    msg.setTimeSent(fmt.parse(v));
		} catch (ParseException e) {}
	    }
	    if (t.equals("%T")) msg.setUnixtimeSent(Long.parseLong(v));
	    if (t.equals("%p")) msg.setFrom(v);
	    if (t.equals("%P")) msg.setTo(v);
	    if (t.equals("%q")) msg.setFromConverted(v);
	    if (t.equals("%Q")) msg.setToConverted(v);
	    if (t.equals("%i")) msg.setSmsc(v);
	    if (t.equals("%I")) msg.setMessageId(v);
	    if (t.equals("%d")) ((Dlr)msg).setDlrValue(Integer.parseInt(v));
	    if (t.equals("%A")) ((Dlr)msg).setDlrReply(v);
	    if (t.equals("%F")) ((Dlr)msg).setForeignMessageId(v);
	    if (t.equals("%n")) msg.setService(v);
	    if (t.equals("%c")) msg.setCoding(Integer.parseInt(v));
	    if (t.equals("%m")) msg.setMclass(Integer.parseInt(v));
	    if (t.equals("%M")) msg.setMwi(Integer.parseInt(v));
	    if (t.equals("%C")) { //charset
		msg.setCharset(Charset.forName(v));
	    }
	    if (t.equals("%u")) msg.setUdh(v.getBytes());
	    if (t.equals("%B")) msg.setBinfo(v);
	    if (t.equals("%o")) msg.setAccount(v);
	    if (t.equals("%O")) msg.setDcs(v);
	    if (t.equals("%f")) msg.setOriginSmsc(v);
	}
	return msg;
    }

    public UrlTemplate addParameter(String key, String value) { params.put(key, value); return this; }

    public UrlTemplate keyword() { return keyword("k"); }
    public UrlTemplate keyword(String p) { params.put(p, "%k"); return this; }

    public UrlTemplate secondKeyword() { return secondKeyword("s"); }
    public UrlTemplate secondKeyword(String p) { params.put(p, "%s"); return this; }

    public UrlTemplate secondKeywordConverted() { return secondKeywordConverted("S"); }
    public UrlTemplate secondKeywordConverted(String p) { params.put(p, "%S"); return this; }

    public UrlTemplate query() { return query("r"); }
    public UrlTemplate query(String p) { params.put(p, "%r"); return this; }

    public UrlTemplate text() { return text("a"); }
    public UrlTemplate text(String p) { params.put(p, "%a"); return this; }

    public UrlTemplate binaryMessage() { return binaryMessage("b"); }
    public UrlTemplate binaryMessage(String p) { params.put(p, "%b"); return this; }

    public UrlTemplate timeSent() { return timeSent("t"); }
    public UrlTemplate timeSent(String p) { params.put(p, "%t"); return this; }

    public UrlTemplate unixTimeSent() { return unixTimeSent("T"); }
    public UrlTemplate unixTimeSent(String p) { params.put(p, "%T"); return this; }

    public UrlTemplate from() { return from("p"); }
    public UrlTemplate from(String p) { params.put(p, "%p"); return this; }

    public UrlTemplate to() { return to("P"); }
    public UrlTemplate to(String p) { params.put(p, "%P"); return this; }

    public UrlTemplate fromConverted() { return fromConverted("q"); }
    public UrlTemplate fromConverted(String p) { params.put(p, "%q"); return this; }

    public UrlTemplate toConverted() { return toConverted("Q"); }
    public UrlTemplate toConverted(String p) { params.put(p, "%Q"); return this; }

    public UrlTemplate smsc() { return smsc("i"); }
    public UrlTemplate smsc(String p) { params.put(p, "%i"); return this; }

    public UrlTemplate messageId() { return messageId("I"); }
    public UrlTemplate messageId(String p) { params.put(p, "%I"); return this; }

    public UrlTemplate dlrValue() { return dlrValue("d"); }
    public UrlTemplate dlrValue(String p) { params.put(p, "%d"); return this; }

    public UrlTemplate dlrReply() { return dlrReply("A"); }
    public UrlTemplate dlrReply(String p) { params.put(p, "%A"); return this; }

    public UrlTemplate foreignMessageId() { return foreignMessageId("F"); }
    public UrlTemplate foreignMessageId(String p) { params.put(p, "%F"); return this; }

    public UrlTemplate service() { return service("n"); }
    public UrlTemplate service(String p) { params.put(p, "%n"); return this; }

    public UrlTemplate coding() { return coding("c"); }
    public UrlTemplate coding(String p) { params.put(p, "%c"); return this; }

    public UrlTemplate mclass() { return mclass("m"); }
    public UrlTemplate mclass(String p) { params.put(p, "%m"); return this; }

    public UrlTemplate mwi() { return mwi("M"); }
    public UrlTemplate mwi(String p) { params.put(p, "%M"); return this; }

    public UrlTemplate charset() { return charset("C"); }
    public UrlTemplate charset(String p) { params.put(p, "%C"); return this; }

    public UrlTemplate udh() { return udh("u"); }
    public UrlTemplate udh(String p) { params.put(p, "%u"); return this; }

    public UrlTemplate binfo() { return binfo("B"); }
    public UrlTemplate binfo(String p) { params.put(p, "%B"); return this; }

    public UrlTemplate account() { return account("o"); }
    public UrlTemplate account(String p) { params.put(p, "%o"); return this; }

    public UrlTemplate dcs() { return dcs("O"); }
    public UrlTemplate dcs(String p) { params.put(p, "%O"); return this; }

    public UrlTemplate originSmsc() { return originSmsc("f"); }
    public UrlTemplate originSmsc(String p) { params.put(p, "%f"); return this; }

    public UrlTemplate all()
    {
	keyword();
	secondKeyword();
	secondKeywordConverted();
	query();
	text();
	binaryMessage();
	timeSent();
	unixTimeSent();
	from();
	to();
	fromConverted();
	toConverted();
	smsc();
	messageId();
	dlrValue();
	dlrReply();
	foreignMessageId();
	service();
	coding();
	mclass();
	mwi();
	charset();
	udh();
	binfo();
	account();
	dcs();
	originSmsc();
	return this;
    }

    private String join(Map<String,String> params)
    {
	Set<Map.Entry<String,String>> entries = params.entrySet();
	int i = 0;
	StringBuffer o = new StringBuffer();
	for (Map.Entry<String,String> entry:entries) {
	    i++;
	    o.append(entry.getKey()).append("=").append(entry.getValue());
	    if (i == entries.size()) break;
	    else o.append("&");
	}
	return o.toString();
    }

    public static void main(String[] argv)
    {
	try {
	    URL u = new UrlTemplate(new URL("http://myhost.com/dlr")).text().from("from").to("to").timeSent().smsc().dlrValue().addParameter("myId", "134719").toUrl();
	    System.out.println(u.toString());
	} catch (Exception e) {
	    e.printStackTrace();
	}
    }

}