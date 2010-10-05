package org.kannel.sms;

import java.net.URL;
import java.nio.charset.Charset;
import java.util.Date;
import java.util.Map;

/**
 * A delivery report (DLR).
 *
 * @author Garth Patil <garthpatil@gmail.com>
 */
public class Dlr
    extends Msg
{

    /**
     * %d
     * the delivery report value
     */
    private Integer dlrValue;
    public Integer getDlrValue() { return this.dlrValue; }
    public void setDlrValue(Integer dlrValue) { this.dlrValue = dlrValue; }

    /**
     * %A
     * the delivery report SMSC reply, if any
     */
    private String dlrReply;
    public String getDlrReply() { return this.dlrReply; }
    public void setDlrReply(String dlrReply) { this.dlrReply = dlrReply; }

    /**
     * %F
     * the foreign (smsc-provided) message ID. Only relevant on DLR url's.
     */
    private String foreignMessageId;
    public String getForeignMessageId() { return this.foreignMessageId; }
    public void setForeignMessageId(String foreignMessageId) { this.foreignMessageId = foreignMessageId; }

    public static Dlr buildFromTemplate(UrlTemplate u, Map m)
    {
	return u.parseDlr(m);
    }

}