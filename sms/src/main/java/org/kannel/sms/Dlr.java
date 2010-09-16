package org.kannel.sms;

import java.net.URL;
import java.nio.charset.Charset;
import java.util.Date;

public class Dlr
{


    //%k	the keyword in the SMS request (i.e., the first word in the SMS message)
    private String keyword;

    //%s	next word from the SMS message, starting with the second one (i.e., the first word, the keyword, is not included); problematic characters for URLs are encoded (e.g., '+' becomes '%2B')
    private String secondKeyword;

    //%S	same as %s, but '*' is converted to '~' (useful when user enters a URL) and URL encoding isn't done (all others do URL encode)
    private String secondKeywordConverted;

    //%r	words not yet used by %s; e.g., if the message is "FOO BAR FOOBAR BAZ", and the has been one %s, %r will mean "FOOBAR BAZ"
    private String query;

    //%a	all words of the SMS message, including the first one, with spaces squeezed to one
    private String message;

    //%b	the original SMS message, in a binary form
    private byte[] binaryMessage;

    //%t	the time the message was sent, formatted as "YYYY-MM-DD HH:MM", e.g., "1999-09-21 14:18"
    private Date timeSent;

    //%T	the time the message was sent, in UNIX epoch timestamp format
    private long unixtimeSent;

    //%p	the phone number of the sender of the SMS message
    private String from;
    
    //%P	the phone number of the receiver of the SMS message
    private String to;

    //%q	like %p, but a leading `00' is replaced with `+'
    private String fromConverted;

    //%Q	like %P, but a leading `00' is replaced with `+'
    private String toConverted;

    //%i	the smsc-id of the connection that received the message
    private String smscId;
    
    //%I	the SMS ID of the internal message structure
    private String messageId;

    //%d	the delivery report value
    private int dlrValue;

    //%A	the delivery report SMSC reply, if any
    private String dlrReply;

    //%F	the foreign (smsc-provided) message ID. Only relevant on DLR url's.
    private String foreignMessageId;

    //%n	the sendsms-user or sms-service name
    private String serviceName;

    //%c	message coding: 0 (default, 7 bits), 1 (8 bits) or 2 (Unicode)
    private int messageCoding;

    //%m	message class bits of DCS: 0 (directly to display, flash), 1 (to mobile), 2 (to SIM) or 3 (to SIM toolkit).
    private int mclass;

    //%M	mwi (message waiting indicator) bits of DCS: 0 (voice), 1, (fax), 2 (email) or 3 (other) for activation and 4, 5, 6, 7 for deactivation respectively.
    private int mwi;

    //%C	message charset: for a "normal" message, it will be "GSM" (coding=0), "binary" (coding=1) or "UTF-16BE" (coding=2). If the message was successfully recoded from Unicode, it will be "WINDOWS-1252"
    private Charset charset;

    //%u	udh of incoming message
    private byte[] udh;

    //%B	billing identifier/information of incoming message. The value depends on the SMSC module and the associated billing semantics of the specific SMSC providing the information. For EMI2 the value is the XSer 0c field, for SMPP MO it is the service_type of the deliver_sm PDU, and for SMPP DLR it is the DLR message err component. (Note: This is used for proxying billing information to external applications. There is no semantics associated while processing these.)
    private String binfo;

    //%o	account identifier/information of incoming message. The value depends on the SMSC module and has been introduced to allow the forwarding of an operator ID from aggregator SMSCs to the application layer, hence the smsbox HTTP calling instance.
    private String ainfo;

    //%O	DCS (Data coding schema) value.
    private String dcs;

    //%f	Originating SMSC of incoming message. The value is set if the AT driver is used to receive a SMS on a gsm modem. The value of %f will contain the number of the SMSC sending the SMS to the SIM card. Other SMSC types than AT do not set this field so it will be empty.
    private String originSMSC;
}