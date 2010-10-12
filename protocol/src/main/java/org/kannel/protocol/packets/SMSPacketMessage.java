package org.kannel.protocol.packets;

import org.kannel.protocol.exceptions.*;
import org.kannel.protocol.tools.*;
import java.io.Serializable;

/**
 * Kannel's protocol sms_packet message.
 *
 * @author Oscar Medina Duarte
 * @author Garth Patil <garthpatil@gmail.com>
 */
public class SMSPacketMessage
    extends BasicPacket
    implements BasicKannelProtocolMessage, Serializable
{

    /**
     *  Mobile Originated SMS message type 
     */
    public final static int SMSTYPE_MO = 0;

    /**
     *  Mobile Terminated Reply SMS message type 
     */
    public final static int SMSTYPE_MT_REPLY = 1;
    
    /**
     *  Mobile Terminated Push SMS message type 
     */
    public final static int SMSTYPE_MT_PUSH = 2;

    /**
     *  Mobile Originated Report
     */
    public final static int SMSTYPE_REPORT_MO = 3;

    /**
     *  Mobile Terminated Report SMS message type 
     */
    public final static int SMSTYPE_REPORT_MT = 4;
    
    private byte[] message = null;

    private KString sender = null;
    private KString receiver = null;
    private KString udhdata = null;
    private KString msgdata = null;
    private KTime time = null;
    private KString smsc_id = null;
    private KString smsc_number = null;
    private KString foreign_id = null;
    private KString service = null;
    private KString account = null;
    private KUUID uuid = null;
    private KInteger sms_type = null;
    private KInteger mclass = null;
    private KInteger mwi = null;
    private KInteger coding = null;
    private KInteger compress = null;
    private KInteger validity = null;
    private KInteger deferred = null;
    private KInteger dlr_mask = null;
    private KString dlr_url = null;
    private KInteger pid = null;
    private KInteger alt_dcs = null;
    private KInteger rpi = null;
    private KString charset = null;
    private KString boxc_id = null;
    private KString binfo = null;
    private KInteger msg_left = null;
    private KInteger priority = null;
    private KInteger resend_try = null;
    private KTime resend_time = null;
    private KString meta_data = null;
    
    /**
     *  Constructor for the SMSPacketMessage object
     */
    public SMSPacketMessage() { }
    
    /**
     * Constructor for the SMSPacketMessage object
     *
     * @param  sender    Part that sends the message
     * @param  receiver  Part that receives de message
     * @param  udhdata   UDH data
     * @param  msgdata   Message data
     */
    public SMSPacketMessage(String sender, String receiver, String udhdata, String msgdata) {
	int temp = 0;
	super.type = new KInteger(super.SMS_PKT);
	temp += 4;
	// Fill with defaults
	this.sender = new KString(sender);
	temp += this.sender.getLength().getIntValue() + 4;
	this.receiver = new KString(receiver);
	temp += this.receiver.getLength().getIntValue() + 4;
	this.udhdata = new KString(udhdata);
	temp += this.udhdata.getLength().getIntValue() + 4;
	this.msgdata = new KString(msgdata);
	temp += this.msgdata.getLength().getIntValue() + 4;
	this.time = new KTime();
	temp += 4;
	this.smsc_id = new KString("");
	temp += this.smsc_id.getLength().getIntValue() + 4;
	this.smsc_number = new KString("");
	temp += this.smsc_number.getLength().getIntValue() + 4;
	this.foreign_id = new KString("");
	temp += this.foreign_id.getLength().getIntValue() + 4;
	this.service = new KString("");
	temp += this.service.getLength().getIntValue() + 4;
	this.account = new KString("");
	temp += this.account.getLength().getIntValue() + 4;
	this.uuid = new KUUID();
	temp += this.uuid.getLength().getIntValue() + 4;
	this.sms_type = new KInteger(this.SMSTYPE_MT_REPLY);
	temp += 4;
	this.mclass = new KInteger(KInteger.MAXVALUE);
	temp += 4;
	this.mwi = new KInteger(KInteger.MAXVALUE);
	temp += 4;
	this.coding = new KInteger(0);
	temp += 4;
	this.compress = new KInteger(0);
	temp += 4;
	this.validity = new KInteger(KInteger.MAXVALUE);
	temp += 4;
	this.deferred = new KInteger(KInteger.MAXVALUE);
	temp += 4;
	this.dlr_mask = new KInteger(KInteger.MAXVALUE);
	temp += 4;
	this.dlr_url = new KString("");
	temp += this.dlr_url.getLength().getIntValue() + 4;
	this.pid = new KInteger(0);
	temp += 4;
	this.alt_dcs = new KInteger(0);
	temp += 4;
	this.rpi = new KInteger(KInteger.MAXVALUE);
	temp += 4;
	this.charset = new KString("");
	temp += this.charset.getLength().getIntValue() + 4;
	this.boxc_id = new KString("");
	temp += this.boxc_id.getLength().getIntValue() + 4;
	this.binfo = new KString("");
	temp += this.binfo.getLength().getIntValue() + 4;
	this.msg_left = new KInteger(KInteger.MAXVALUE);
	temp += 4;
	this.priority = new KInteger(0);
	temp += 4;
	this.resend_try = new KInteger(0);
	temp += 4;
	this.resend_time = new KTime();
	temp += 4;
	this.meta_data = new KString("");
	temp += this.meta_data.getLength().getIntValue() + 4;
	
	super.length = new KInteger(temp);
    }
    
    
    /**
     * Constructor for the SMSPacketMessage object. This parses an array of bytes into a SMSPacketMessage
     *
     * @param  data                      Byte array as received in a tcp link
     * @exception  PacketParseException  Exception thrown when a byte array is too short or its badly formed
     */
    public SMSPacketMessage(byte[] data) throws PacketParseException {
	this.setMessage(data);
    }


    /**
     * Gets the message attribute of the SMSPacketMessage object
     *
     * @return    Byte array containing the packet to be sent over a tcp connection
     */
    public byte[] getMessage() {
	if (this.message == null) {
	    
	    this.message = DataTypesTools.byteCat(super.length.getBytes(), super.type.getBytes());
	    this.message = DataTypesTools.byteCat(this.message, this.sender.getBytes());
	    this.message = DataTypesTools.byteCat(this.message, this.receiver.getBytes());
	    this.message = DataTypesTools.byteCat(this.message, this.udhdata.getBytes());
	    this.message = DataTypesTools.byteCat(this.message, this.msgdata.getBytes());
	    this.message = DataTypesTools.byteCat(this.message, this.time.getBytes());
	    this.message = DataTypesTools.byteCat(this.message, this.smsc_id.getBytes());
	    this.message = DataTypesTools.byteCat(this.message, this.smsc_number.getBytes());
	    this.message = DataTypesTools.byteCat(this.message, this.foreign_id.getBytes());
	    this.message = DataTypesTools.byteCat(this.message, this.service.getBytes());
	    this.message = DataTypesTools.byteCat(this.message, this.account.getBytes());
	    this.message = DataTypesTools.byteCat(this.message, this.uuid.getBytes());
	    this.message = DataTypesTools.byteCat(this.message, this.sms_type.getBytes());
	    this.message = DataTypesTools.byteCat(this.message, this.mclass.getBytes());
	    this.message = DataTypesTools.byteCat(this.message, this.mwi.getBytes());
	    this.message = DataTypesTools.byteCat(this.message, this.coding.getBytes());
	    this.message = DataTypesTools.byteCat(this.message, this.compress.getBytes());
	    this.message = DataTypesTools.byteCat(this.message, this.validity.getBytes());
	    this.message = DataTypesTools.byteCat(this.message, this.deferred.getBytes());
	    this.message = DataTypesTools.byteCat(this.message, this.dlr_mask.getBytes());
	    this.message = DataTypesTools.byteCat(this.message, this.dlr_url.getBytes());
	    this.message = DataTypesTools.byteCat(this.message, this.pid.getBytes());
	    this.message = DataTypesTools.byteCat(this.message, this.alt_dcs.getBytes());
	    this.message = DataTypesTools.byteCat(this.message, this.rpi.getBytes());
	    this.message = DataTypesTools.byteCat(this.message, this.charset.getBytes());
	    this.message = DataTypesTools.byteCat(this.message, this.boxc_id.getBytes());
	    this.message = DataTypesTools.byteCat(this.message, this.binfo.getBytes());
	    this.message = DataTypesTools.byteCat(this.message, this.msg_left.getBytes());
	    this.message = DataTypesTools.byteCat(this.message, this.priority.getBytes());
	    this.message = DataTypesTools.byteCat(this.message, this.resend_try.getBytes());
	    this.message = DataTypesTools.byteCat(this.message, this.resend_time.getBytes());
	    this.message = DataTypesTools.byteCat(this.message, this.meta_data.getBytes());
	}
	return this.message;
    }
    
    
    /**
     * Sets the message attribute of the SMSPacketMessage object
     *
     * @param  data                      Byte array containing a packet as received over a tcp connection
     * @exception  PacketParseException  Exception thrown when
     */
    public void setMessage(byte[] data) throws PacketParseException {
	KInteger swap = null;
	int index = 0;
	
	try {
	    if (data.length < 8) {
		throw new PacketParseException();
	    }
	    
	    swap = new KInteger(data[0], data[1], data[2], data[3]);
	    if ((swap.getIntValue() + 4) == data.length) {
		super.length = swap;
		swap = null;
	    } else {
		// // System.out.println("--2.12 : " + index);
		throw new PacketParseException();
	    }
	    
	    swap = new KInteger(data[4], data[5], data[6], data[7]);
	    if (swap.getIntValue() == 0x02) {
		super.type = swap;
		swap = null;
	    } else {
		// // System.out.println("--2.13 : " + index);
		throw new PacketParseException();
	    }
	    index = 8;
	    
	    //////  Y parsear el resto ////////
	    // System.out.println("--1 : " + index);
	    this.sender = DataTypesTools.parseKStringFromByteArray(data, index);
	    index += this.sender.getLength().getIntValue() + 4;
	    // System.out.println("--2 : " + index);
	    this.receiver = DataTypesTools.parseKStringFromByteArray(data, index);
	    index += this.receiver.getLength().getIntValue() + 4;
	    // System.out.println("--3 : " + index);
	    this.udhdata = DataTypesTools.parseKStringFromByteArray(data, index);
	    index += this.udhdata.getLength().getIntValue() + 4;
	    // System.out.println("--4 : " + index);
	    this.msgdata = DataTypesTools.parseKStringFromByteArray(data, index);
	    index += this.msgdata.getLength().getIntValue() + 4;
	    // System.out.println("--5 : " + index);
	    // Ojo con este par
	    this.time = DataTypesTools.parseKTimeFromByteArray(data, index);
	    index += 4;
	    // System.out.println("--6 : " + index);
	    this.smsc_id = DataTypesTools.parseKStringFromByteArray(data, index);
	    index += this.smsc_id.getLength().getIntValue() + 4;
	    
	    this.smsc_number = DataTypesTools.parseKStringFromByteArray(data, index);
	    index += this.smsc_number.getLength().getIntValue() + 4;
	    
	    this.foreign_id = DataTypesTools.parseKStringFromByteArray(data, index);
	    index += this.foreign_id.getLength().getIntValue() + 4;
	    
	    // System.out.println("--7 : " + index);
	    this.service = DataTypesTools.parseKStringFromByteArray(data, index);
	    index += this.service.getLength().getIntValue() + 4;
	    // System.out.println("--8 : " + index);
	    this.account = DataTypesTools.parseKStringFromByteArray(data, index);
	    index += this.account.getLength().getIntValue() + 4;
	    // System.out.println("--9 : " + index);
	    // ojo con este parser
	    this.uuid = DataTypesTools.parseKUUIDFromByteArray(data, index);
	    index += this.uuid.getLength().getIntValue() + 4;
	    // System.out.println("--10 : " + index);
	    this.sms_type = DataTypesTools.parseKIntFromByteArray(data, index);
	    index += 4;
	    // System.out.println("--11 : " + index);
	    this.mclass = DataTypesTools.parseKIntFromByteArray(data, index);
	    index += 4;
	    // System.out.println("--12 : " + index);
	    this.mwi = DataTypesTools.parseKIntFromByteArray(data, index);
	    index += 4;
	    // System.out.println("--13 : " + index);
	    this.coding = DataTypesTools.parseKIntFromByteArray(data, index);
	    index += 4;
	    // System.out.println("--14 : " + index);
	    this.compress = DataTypesTools.parseKIntFromByteArray(data, index);
	    index += 4;
	    // System.out.println("--15 : " + index);
	    this.validity = DataTypesTools.parseKIntFromByteArray(data, index);
	    index += 4;
	    // System.out.println("--16 : " + index);
	    this.deferred = DataTypesTools.parseKIntFromByteArray(data, index);
	    index += 4;
	    // System.out.println("--17 : " + index);
	    this.dlr_mask = DataTypesTools.parseKIntFromByteArray(data, index);
	    index += 4;
	    // System.out.println("--18 : " + index);
	    this.dlr_url = DataTypesTools.parseKStringFromByteArray(data, index);
	    index += this.dlr_url.getLength().getIntValue() + 4;
	    // System.out.println("--19 : " + index);
	    this.pid = DataTypesTools.parseKIntFromByteArray(data, index);
	    index += 4;
	    // System.out.println("--20 : " + index);
	    this.alt_dcs = DataTypesTools.parseKIntFromByteArray(data, index);
	    index += 4;
	    // System.out.println("--21 : " + index);
	    this.rpi = DataTypesTools.parseKIntFromByteArray(data, index);
	    index += 4;
	    // System.out.println("--22 : " + index);
	    this.charset = DataTypesTools.parseKStringFromByteArray(data, index);
	    index += this.charset.getLength().getIntValue() + 4;
	    // System.out.println("--23 : " + index);
	    this.boxc_id = DataTypesTools.parseKStringFromByteArray(data, index);
	    index += this.boxc_id.getLength().getIntValue() + 4;
	    // System.out.println("--24 : " + index);
	    this.binfo = DataTypesTools.parseKStringFromByteArray(data, index);
	    index += this.binfo.getLength().getIntValue() + 4;
	    // System.out.println("--25 : " + index);
	    this.msg_left = DataTypesTools.parseKIntFromByteArray(data, index);
	    index += 4;
	    // System.out.println("--26 : " + index);
	    this.priority = DataTypesTools.parseKIntFromByteArray(data, index);
	    index += 4;
	    this.resend_try = DataTypesTools.parseKIntFromByteArray(data, index);
	    index += 4;
	    this.resend_time = DataTypesTools.parseKTimeFromByteArray(data, index);
	    index += 4;
	    this.meta_data = DataTypesTools.parseKStringFromByteArray(data, index);
	    index += this.meta_data.getLength().getIntValue() + 4;
	    
	    ///////////////////////////////////
	} catch (ArrayIndexOutOfBoundsException e) {
	    throw new PacketParseException();
	}catch (PacketParseException e){
	    throw e;
	}
    }
    
    
    /**
     * Sets the sender attribute of the SMSPacketMessage object
     *
     * @param  sender  The new sender value
     */
    public void setSender(KString sender) {
	int len = super.length.getIntValue();
	len -= this.sender.getLength().getIntValue();
	len += sender.getLength().getIntValue();
	
	super.length.setValue(len);
	this.message = null;
	this.sender = sender;
    }
    
    
    /**
     * Sets the receiver attribute of the SMSPacketMessage object
     *
     * @param  receiver  The new receiver value
     */
    public void setReceiver(KString receiver) {
	int len = super.length.getIntValue();
	len -= this.receiver.getLength().getIntValue();
	len += receiver.getLength().getIntValue();
	
	super.length.setValue(len);
	this.message = null;
	this.receiver = receiver;
    }
    
    
    /**
     * Sets the udhdata attribute of the SMSPacketMessage object
     *
     * @param  udhdata  The new udhdata value
     */
    public void setUdhdata(KString udhdata) {
	int len = super.length.getIntValue();
	len -= this.udhdata.getLength().getIntValue();
	len += udhdata.getLength().getIntValue();
	
	super.length.setValue(len);
	this.message = null;
	this.udhdata = udhdata;
    }

    
    /**
     * Sets the msgdata attribute of the SMSPacketMessage object
     *
     * @param  msgdata  The new msgdata value
     */
    public void setMsgdata(KString msgdata) {
	int len = super.length.getIntValue();
	len -= this.msgdata.getLength().getIntValue();
	len += msgdata.getLength().getIntValue();

	super.length.setValue(len);
	this.message = null;
	this.msgdata = msgdata;
    }


    /**
     * Sets the time attribute of the SMSPacketMessage object
     *
     * @param  time  The new time value
     */
    public void setTime(KTime time) {
	this.message = null;
	this.time = time;
    }


    /**
     * Sets the smsc_id attribute of the SMSPacketMessage object
     *
     * @param  smsc_id  The new smsc_id value
     */
    public void setSmsc_id(KString smsc_id) {
	int len = super.length.getIntValue();
	len -= this.smsc_id.getLength().getIntValue();
	len += smsc_id.getLength().getIntValue();

	super.length.setValue(len);
	this.message = null;
	this.smsc_id = smsc_id;
    }


    /**
     * Sets the smsc_number attribute of the SMSPacketMessage object
     *
     * @param  smsc_number  The new smsc_number value
     */
    public void setSmsc_number(KString smsc_number) {
	int len = super.length.getIntValue();
	len -= this.smsc_number.getLength().getIntValue();
	len += smsc_number.getLength().getIntValue();

	super.length.setValue(len);
	this.message = null;
	this.smsc_number = smsc_number;
    }

    /**
     * Sets the foreign_id attribute of the SMSPacketMessage object
     *
     * @param  foreign_id  The new foreign_id value
     */
    public void setForeign_id(KString foreign_id) {
	int len = super.length.getIntValue();
	len -= this.foreign_id.getLength().getIntValue();
	len += foreign_id.getLength().getIntValue();

	super.length.setValue(len);
	this.message = null;
	this.foreign_id = foreign_id;
    }


    /**
     * Sets the service attribute of the SMSPacketMessage object
     *
     * @param  service  The new service value
     */
    public void setService(KString service) {
	int len = super.length.getIntValue();
	len -= this.service.getLength().getIntValue();
	len += service.getLength().getIntValue();

	super.length.setValue(len);
	this.message = null;
	this.service = service;
    }


    /**
     * Sets the account attribute of the SMSPacketMessage object
     *
     * @param  account  The new account value
     */
    public void setAccount(KString account) {
	int len = super.length.getIntValue();
	len -= this.account.getLength().getIntValue();
	len += account.getLength().getIntValue();

	super.length.setValue(len);
	this.message = null;
	this.account = account;
    }


    /**
     * Sets the uuid attribute of the SMSPacketMessage object
     *
     * @param  uuid  The new uuid value
     */
    public void setUuid(KUUID uuid) {
	int len = super.length.getIntValue();
	len -= this.uuid.getLength().getIntValue();
	len += uuid.getLength().getIntValue();

	super.length.setValue(len);
	this.message = null;
	this.uuid = uuid;
    }


    /**
     * Sets the sms_type attribute of the SMSPacketMessage object
     *
     * @param  sms_type  The new sms_type value
     */
    public void setSms_type(KInteger sms_type) {
	this.message = null;
	this.sms_type = sms_type;
    }


    /**
     * Sets the mclass attribute of the SMSPacketMessage object
     *
     * @param  mclass  The new mclass value
     */
    public void setMclass(KInteger mclass) {
	this.message = null;
	this.mclass = mclass;
    }


    /**
     * Sets the mwi attribute of the SMSPacketMessage object
     *
     * @param  mwi  The new mwi value
     */
    public void setMwi(KInteger mwi) {
	this.message = null;
	this.mwi = mwi;
    }


    /**
     * Sets the coding attribute of the SMSPacketMessage object
     *
     * @param  coding  The new coding value
     */
    public void setCoding(KInteger coding) {
	this.message = null;
	this.coding = coding;
    }


    /**
     * Sets the compress attribute of the SMSPacketMessage object
     *
     * @param  compress  The new compress value
     */
    public void setCompress(KInteger compress) {
	this.message = null;
	this.compress = compress;
    }


    /**
     * Sets the validity attribute of the SMSPacketMessage object
     *
     * @param  validity  The new validity value
     */
    public void setValidity(KInteger validity) {
	this.message = null;
	this.validity = validity;
    }


    /**
     * Sets the deferred attribute of the SMSPacketMessage object
     *
     * @param  deferred  The new deferred value
     */
    public void setDeferred(KInteger deferred) {
	this.message = null;
	this.deferred = deferred;
    }


    /**
     * Sets the dlr_mask attribute of the SMSPacketMessage object
     *
     * @param  dlr_mask  The new dlr_mask value
     */
    public void setDlr_mask(KInteger dlr_mask) {
	this.message = null;
	this.dlr_mask = dlr_mask;
    }


    /**
     * Sets the dlr_url attribute of the SMSPacketMessage object
     *
     * @param  dlr_url  The new dlr_url value
     */
    public void setDlr_url(KString dlr_url) {
	int len = super.length.getIntValue();
	len -= this.dlr_url.getLength().getIntValue();
	len += dlr_url.getLength().getIntValue();

	super.length.setValue(len);
	this.message = null;
	this.dlr_url = dlr_url;
    }


    /**
     * Sets the pid attribute of the SMSPacketMessage object
     *
     * @param  pid  The new pid value
     */
    public void setPid(KInteger pid) {
	this.message = null;
	this.pid = pid;
    }


    /**
     * Sets the alt_dcs attribute of the SMSPacketMessage object
     *
     * @param  alt_dcs  The new alt_dcs value
     */
    public void setAlt_dcs(KInteger alt_dcs) {
	this.message = null;
	this.alt_dcs = alt_dcs;
    }


    /**
     * Sets the rpi attribute of the SMSPacketMessage object
     *
     * @param  rpi  The new rpi value
     */
    public void setRpi(KInteger rpi) {
	this.message = null;
	this.rpi = rpi;
    }


    /**
     * Sets the charset attribute of the SMSPacketMessage object
     *
     * @param  charset  The new charset value
     */
    public void setCharset(KString charset) {
	int len = super.length.getIntValue();
	len -= this.charset.getLength().getIntValue();
	len += charset.getLength().getIntValue();

	super.length.setValue(len);
	this.message = null;
	this.charset = charset;
    }


    /**
     * Sets the boxc_id attribute of the SMSPacketMessage object
     *
     * @param  boxc_id  The new boxc_id value
     */
    public void setBoxc_id(KString boxc_id) {
	int len = super.length.getIntValue();
	len -= this.boxc_id.getLength().getIntValue();
	len += boxc_id.getLength().getIntValue();

	super.length.setValue(len);
	this.message = null;
	this.boxc_id = boxc_id;
    }


    /**
     * Sets the binfo attribute of the SMSPacketMessage object
     *
     * @param  binfo  The new binfo value
     */
    public void setBinfo(KString binfo) {
	int len = super.length.getIntValue();
	len -= this.binfo.getLength().getIntValue();
	len += binfo.getLength().getIntValue();

	super.length.setValue(len);
	this.message = null;
	this.binfo = binfo;
    }


    /**
     * Sets the msg_left attribute of the SMSPacketMessage object
     *
     * @param  msg_left  The new msg_left value
     */
    public void setMsg_left(KInteger msg_left) {
	this.message = null;
	this.msg_left = msg_left;
    }


    /**
     * Sets the priority attribute of the SMSPacketMessage object
     *
     * @param  priority  The new priority value
     */
    public void setPriority(KInteger priority) {
	this.message = null;
	this.priority = priority;
    }

    /**
     * Sets the resend_try attribute of the SMSPacketMessage object
     *
     * @param  resend_try  The new resend_try value
     */
    public void setResend_Try(KInteger resend_try) {
	this.message = null;
	this.resend_try = resend_try;
    }


    /**
     * Sets the resend_time attribute of the SMSPacketMessage object
     *
     * @param  resend_time  The new resend_time value
     */
    public void setResend_Time(KTime resend_time) {
	this.message = null;
	this.resend_time = resend_time;
    }


    /**
     * Sets the meta_data attribute of the SMSPacketMessage object
     *
     * @param  meta_data  The new meta_data value
     */
    public void setMeta_Data(KString meta_data) {
	int len = super.length.getIntValue();
	len -= this.meta_data.getLength().getIntValue();
	len += meta_data.getLength().getIntValue();

	super.length.setValue(len);
	this.message = null;
	this.meta_data = meta_data;
    }

    /**
     * Gets the sender attribute of the SMSPacketMessage object
     *
     * @return    The sender value
     */
    public KString getSender() {
	return sender;
    }


    /**
     * Gets the receiver attribute of the SMSPacketMessage object
     *
     * @return    The receiver value
     */
    public KString getReceiver() {
	return receiver;
    }


    /**
     * Gets the udhdata attribute of the SMSPacketMessage object
     *
     * @return    The udhdata value
     */
    public KString getUdhdata() {
	return udhdata;
    }


    /**
     * Gets the msgdata attribute of the SMSPacketMessage object
     *
     * @return    The msgdata value
     */
    public KString getMsgdata() {
	return msgdata;
    }


    /**
     * Gets the time attribute of the SMSPacketMessage object
     *
     * @return    The time value
     */
    public KTime getTime() {
	return time;
    }


    /**
     * Gets the smsc_id attribute of the SMSPacketMessage object
     *
     * @return    The smsc_id value
     */
    public KString getSmsc_id() {
	return smsc_id;
    }


    /**
     * Gets the smsc_number attribute of the SMSPacketMessage object
     *
     * @return    The smsc_number value
     */
    public KString getSmsc_number() {
	return smsc_number;
    }


    /**
     * Gets the foreign_id attribute of the SMSPacketMessage object
     *
     * @return    The foreign_id value
     */
    public KString getForeign_id() {
	return foreign_id;
    }


    /**
     * Gets the service attribute of the SMSPacketMessage object
     *
     * @return    The service value
     */
    public KString getService() {
	return service;
    }


    /**
     * Gets the account attribute of the SMSPacketMessage object
     *
     * @return    The account value
     */
    public KString getAccount() {
	return account;
    }


    /**
     * Gets the uuid attribute of the SMSPacketMessage object
     *
     * @return    The uuid value
     */
    public KUUID getUuid() {
	return uuid;
    }


    /**
     * Gets the sms_type attribute of the SMSPacketMessage object
     *
     * @return    The sms_type value
     */
    public KInteger getSms_type() {
	return sms_type;
    }


    /**
     * Gets the mclass attribute of the SMSPacketMessage object
     *
     * @return    The mclass value
     */
    public KInteger getMclass() {
	return mclass;
    }


    /**
     * Gets the mwi attribute of the SMSPacketMessage object
     *
     * @return    The mwi value
     */
    public KInteger getMwi() {
	return mwi;
    }


    /**
     * Gets the coding attribute of the SMSPacketMessage object
     *
     * @return    The coding value
     */
    public KInteger getCoding() {
	return coding;
    }


    /**
     * Gets the compress attribute of the SMSPacketMessage object
     *
     * @return    The compress value
     */
    public KInteger getCompress() {
	return compress;
    }


    /**
     * Gets the validity attribute of the SMSPacketMessage object
     *
     * @return    The validity value
     */
    public KInteger getValidity() {
	return validity;
    }


    /**
     * Gets the deferred attribute of the SMSPacketMessage object
     *
     * @return    The deferred value
     */
    public KInteger getDeferred() {
	return deferred;
    }


    /**
     * Gets the dlr_mask attribute of the SMSPacketMessage object
     *
     * @return    The dlr_mask value
     */
    public KInteger getDlr_mask() {
	return dlr_mask;
    }


    /**
     * Gets the dlr_url attribute of the SMSPacketMessage object
     *
     * @return    The dlr_url value
     */
    public KString getDlr_url() {
	return dlr_url;
    }


    /**
     * Gets the pid attribute of the SMSPacketMessage object
     *
     * @return    The pid value
     */
    public KInteger getPid() {
	return pid;
    }


    /**
     * Gets the alt_dcs attribute of the SMSPacketMessage object
     *
     * @return    The alt_dcs value
     */
    public KInteger getAlt_dcs() {
	return alt_dcs;
    }


    /**
     * Gets the rpi attribute of the SMSPacketMessage object
     *
     * @return    The rpi value
     */
    public KInteger getRpi() {
	return rpi;
    }


    /**
     * Gets the charset attribute of the SMSPacketMessage object
     *
     * @return    The charset value
     */
    public KString getCharset() {
	return charset;
    }


    /**
     * Gets the boxc_id attribute of the SMSPacketMessage object
     *
     * @return    The boxc_id value
     */
    public KString getBoxc_id() {
	return boxc_id;
    }


    /**
     * Gets the binfo attribute of the SMSPacketMessage object
     *
     * @return    The binfo value
     */
    public KString getBinfo() {
	return binfo;
    }


    /**
     * Gets the msg_left attribute of the SMSPacketMessage object
     *
     * @return    The msg_left value
     */
    public KInteger getMsg_left() {
	return msg_left;
    }


    /**
     * Gets the priority attribute of the SMSPacketMessage object
     *
     * @return    The priority value
     */
    public KInteger getPriority() {
	return priority;
    }

    /**
     * Gets the resend_try attribute of the SMSPacketMessage object
     *
     * @return    The resend_try value
     */
    public KInteger getResend_Try() {
	return resend_try;
    }

    /**
     * Gets the resend_time attribute of the SMSPacketMessage object
     *
     * @return    The resend_time value
     */
    public KTime getResend_Time() {
	return resend_time;
    }

    /**
     * Gets the meta_data attribute of the SMSPacketMessage object
     *
     * @return    The meta_data value
     */
    public KString getMeta_Data() {
	return meta_data;
    }


    /**
     * String representation of this packet showing all its field and contents 
     *
     * @return   String representation
     */
    public String toString() {
	StringBuffer sb = new StringBuffer();
	sb.append("Length: ").append(super.length.toString()).append((char) 10);
	sb.append("Type: ").append(super.type.toString()).append((char) 10);
	sb.append("sender: ").append(this.sender.toString()).append((char) 10);
	sb.append("receiver: ").append(this.receiver.toString()).append((char) 10);
	sb.append("udhdata: ").append(this.udhdata.toString()).append((char) 10);
	sb.append("msgdata: ").append(this.msgdata.toString()).append((char) 10);
	sb.append("time: ").append(this.time.toString()).append((char) 10);
	sb.append("smsc_id: ").append(this.smsc_id.toString()).append((char) 10);
	sb.append("smsc_number: ").append(this.smsc_number.toString()).append((char) 10);
	sb.append("foreign_id: ").append(this.foreign_id.toString()).append((char) 10);
	sb.append("service: ").append(this.service.toString()).append((char) 10);
	sb.append("account: ").append(this.account.toString()).append((char) 10);
	sb.append("uuid: ").append(this.uuid.toString()).append((char) 10);
	sb.append("sms_type: ").append(this.sms_type.toString()).append((char) 10);
	sb.append("mclass: ").append(this.mclass.toString()).append((char) 10);
	sb.append("mwi: ").append(this.mwi.toString()).append((char) 10);
	sb.append("coding: ").append(this.coding.toString()).append((char) 10);
	sb.append("compress: ").append(this.compress.toString()).append((char) 10);
	sb.append("validity: ").append(this.validity.toString()).append((char) 10);
	sb.append("deferred: ").append(this.deferred.toString()).append((char) 10);
	sb.append("dlr_mask: ").append(this.dlr_mask.toString()).append((char) 10);
	sb.append("dlr_url: ").append(this.dlr_url.toString()).append((char) 10);
	sb.append("pid: ").append(this.pid.toString()).append((char) 10);
	sb.append("alt_dcs: ").append(this.alt_dcs.toString()).append((char) 10);
	sb.append("rpi: ").append(this.rpi.toString()).append((char) 10);
	sb.append("charset: ").append(this.charset.toString()).append((char) 10);
	sb.append("boxc_id: ").append(this.boxc_id.toString()).append((char) 10);
	sb.append("binfo: ").append(this.binfo.toString()).append((char) 10);
	sb.append("msg_left: ").append(this.msg_left.toString()).append((char) 10);
	sb.append("priority: ").append(this.priority.toString());
	sb.append("resend_try: ").append(this.resend_try.toString());
	sb.append("resend_time: ").append(this.resend_time.toString());
	sb.append("meta_data: ").append(this.meta_data.toString());
	sb.append("\n");
	return sb.toString();
    }


    /**
     * Returns a Hex Editor style representation of the message
     *
     * @return   String representation in Hex editor style
     */
    public String hexDump() {
	return DataTypesTools.hexDump(this.getMessage());
    }


    /**
     * Testing method
     *
     * @param  args           Description of the Parameter
     * @exception  Exception  Exception thrown when
     */
    public static void main(String args[]) throws Exception
    {
	byte[] test = {0, 0, 0, -48, 0, 0, 0, 2, 0, 0, 0, 5, 111, 115, 99, 97, 114, 0, 0, 0, 12, 108, 111, 115, 32, 107, 97, 110, 110, 101, 108, 111, 115, 0, 0, 0, 10, 102, 117, 116, 117, 114, 111, 32, 117, 100, 104, 0, 0, 0, 11, 72, 111, 108, 97, 32, 109, 117, 110, 100, 111, 33, 0, 0, 0, 0, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 0, 0, 0, 36, 54, 53, 98, 98, 98, 57, 55, 52, 45, 97, 53, 52, 56, 45, 49, 49, 100, 57, 45, 56, 54, 98, 99, 45, 49, 51, 51, 57, 52, 50, 51, 49, 48, 99, 54, 97, 0, 0, 0, 1, -1, -1, -1, -1, -1, -1, -1, -1, 0, 0, 0, 0, 0, 0, 0, 0, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 0, 0, 0, 26, 104, 116, 116, 112, 58, 47, 47, 108, 111, 99, 97, 108, 104, 111, 115, 116, 58, 56, 48, 56, 48, 47, 97, 108, 103, 111, 0, 0, 0, 0, 0, 0, 0, 0, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 0, 0, 0, 0};

	System.out.println("test leng: " + test.length);
	SMSPacketMessage smsMsg = new SMSPacketMessage("oscar", "los kannelos", "futuro udh", "Hola mundo!");

	// SMSPacketMessage smsMsg = new SMSPacketMessage(test);

	smsMsg.setDlr_url(new KString("http://localhost:8080/algo"));

	System.out.println("--2");
	byte[] arr = smsMsg.getMessage();
	System.out.println("--3");
	for (int i = 0; i < arr.length; i++) {
	    System.out.print(arr[i] + ", ");
	}
	System.out.println("\n" + DataTypesTools.byteToHex(arr));
	System.out.println(smsMsg.hexDump());
	System.out.println(smsMsg);
		
	AckMessage ackMsg = new AckMessage(0, smsMsg);
	System.out.println(ackMsg.hexDump());
	System.exit(0);
    }

}

