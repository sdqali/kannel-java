package org.kannel.admin;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.IOException;
import java.net.URI;
import java.net.URL;

/**
 * Implementation of KannelAdmin using Kannel's HTTP administration interface
 *
 * @author Garth Patil <garthpatil@gmail.com>
 */
public class HttpKannelAdmin
    implements KannelAdmin
{
    
    public HttpKannelAdmin(URL baseUrl, String password) {
	this.baseUrl = baseUrl;
	this.password = password;
    }
	
    private URL baseUrl;
    private String password;	

    private URL getUrl(String method, boolean requiresPassword) throws Exception
    {
	if (requiresPassword) method = addPassword(method);
	return this.baseUrl.toURI().resolve(method).toURL();
    }

    private String addPassword(String method)
    {
	StringBuffer o = new StringBuffer(method);
	if (!method.contains("?")) o.append("?").append("password=").append(this.password);
	else o.append("&").append("password=").append(this.password);
	return o.toString();
    }

    /**
     * status
     * Get the current status of the gateway in a text version. Tells the current state (see above) and total number of messages relied and queuing in the system right now. Also lists the total number of smsbox and wapbox connections. No password required, unless status-password set, in which case either that or main admin password must be supplied.
     */
    public Status getStatus() throws AdminException
    {
	try {
	    InputStream is = getUrl("status", true).openStream();
	    Status s = Status.parse(is);
	    is.close();
	    return s;
	} catch (Exception e) {
	    throw new AdminException("Error executing status", e);
	}
    }

    /**
     * store-status
     * Get the current content of the store queue of the gateway in a text version. No password required, unless status-password set, in which case either that or main admin password must be supplied.
     */
    public StoreStatus getStoreStatus() throws AdminException
    {
	try {
	    InputStream is = getUrl("store-status", true).openStream();
	    StoreStatus s = StoreStatus.parse(is);
	    is.close();
	    return s;
	} catch (Exception e) {
	    throw new AdminException("Error executing store-status", e);
	}
    }

    /**
     * suspend
     * Set Kannel state as 'suspended' (see above). Password required.
     */
    public void suspend() throws AdminException
    {
	try {
	    String response = getContent(getUrl("suspend", true));
	    if (response.contains("Already susptended")) throw new AdminException(response.trim());
	} catch (Exception e) {
	    throw new AdminException("Error executing suspend", e);
	}
    }

    /**
     * isolate
     * Set Kannel state as 'isolated' (see above). Password required.
     */
    public void isolate() throws AdminException
    {
	try {
	    String response = getContent(getUrl("isolate", true));
	    if (response.contains("Already isolated")) throw new AdminException(response.trim());
	} catch (Exception e) {
	    throw new AdminException("Error executing isolate", e);
	}
    }

    /**
     * resume
     * Set Kannel state as 'running' if it is suspended or isolated. Password required.
     */
    public void resume() throws AdminException
    {
	try {
	    String response = getContent(getUrl("resume", true));
	    if (response.contains("Already running")) throw new AdminException(response.trim());
	} catch (Exception e) {
	    throw new AdminException("Error executing resume", e);
	}
    }

    /**
     * shutdown
     * Bring down the gateway, by setting state to 'shutdown'. After a shutdown is initiated, there is no other chance to resume normal operation. However, 'status' command still works. Password required. If shutdown is sent for a second time, the gateway is forced down, even if it has still messages in queue.
     */
    public void shutdown() throws AdminException
    {
	try {
	    String response = getContent(getUrl("shutdown", true));
	} catch (Exception e) {
	    throw new AdminException("Error executing shutdown", e);
	}
    }

    /**
     * flush-dlr
     * If Kannel state is 'suspended' this will flush all queued DLR messages in the current storage space. Password required.
     */
    public void flushDLR() throws AdminException
    {
	try {
	    String response = getContent(getUrl("flush-dlr", true));
	    if (response.contains("before trying to flush DLR queue")) throw new AdminException(response.trim());
	} catch (Exception e) {
	    throw new AdminException("Error executing flush-dlr", e);
	}
    }

    /**
     * start-smsc
     * Re-start a single SMSC link. Password required. Additionally the smsc parameter must be given to identify which smsc-admin-id should be re-started. The configuration for the SMSC link is re-read from disk before the action is performed.
     */
    public void startSMSC(String smsc) throws AdminException
    {
	try {
	    StringBuffer query = new StringBuffer("start-smsc");
	    query.append("?").append("smsc=").append(smsc);
	    String response = getContent(getUrl(query.toString(), true));
	    if (response.contains("Could not re-start smsc-id") ||
		response.contains("SMSC id not given")) throw new AdminException(response.trim());
	} catch (Exception e) {
	    throw new AdminException("Error executing flush-dlr", e);
	}
    }

    /**
     * stop-smsc
     * Shutdown a single SMSC link. Password required. Additionally the smsc parameter must be given (see above).
     */
    public void stopSMSC(String smsc) throws AdminException
    {
	try {
	    StringBuffer query = new StringBuffer("stop-smsc");
	    query.append("?").append("smsc=").append(smsc);
	    String response = getContent(getUrl(query.toString(), true));
	    if (response.contains("Could not shut down smsc-id") ||
		response.contains("SMSC id not given")) throw new AdminException(response.trim());
	} catch (Exception e) {
	    throw new AdminException("Error executing flush-dlr", e);
	}
    }

    /**
     * add-smsc
     * Adds an SMSC link previously removed or created after the service was started. Password required. Additionally the smsc parameter must be given (see above).
     */
    public void addSMSC(String smsc) throws AdminException
    {
	try {
	    StringBuffer query = new StringBuffer("add-smsc");
	    query.append("?").append("smsc=").append(smsc);
	    String response = getContent(getUrl(query.toString(), true));
	    if (response.contains("Could not add smsc-id") ||
		response.contains("SMSC id not given")) throw new AdminException(response.trim());
	} catch (Exception e) {
	    throw new AdminException("Error executing flush-dlr", e);
	}
    }

    /**
     * remove-smsc
     * Removes an existing SMSC link. Password required. Additionally the smsc parameter must be given (see above). If you want a permanent removal, you should also remove the entry from the configuration file or it will be recreated after a service restart.
     */
    public void removeSMSC(String smsc) throws AdminException
    {
	try {
	    StringBuffer query = new StringBuffer("remove-smsc");
	    query.append("?").append("smsc=").append(smsc);
	    String response = getContent(getUrl(query.toString(), true));
	    if (response.contains("Could not remove smsc-id") ||
		response.contains("SMSC id not given")) throw new AdminException(response.trim());
	} catch (Exception e) {
	    throw new AdminException("Error executing flush-dlr", e);
	}
    }

    /**
     * restart
     * Re-start whole bearerbox, hence all SMSC links. Password required. Beware that you loose the smsbox connections in such a case.
     */
    public void restart() throws AdminException
    {
	try {
	    String response = getContent(getUrl("restart", true));
	    if (response.contains("Trying harder to restart")) throw new AdminException(response.trim());
	} catch (Exception e) {
	    throw new AdminException("Error executing restart", e);
	}
    }

    /**
     * loglevel
     * Set Kannel log-level of log-files while running. This allows you to change the current log-level of the log-files on the fly.
     */
    public void logLevel(int logLevel) throws AdminException
    {
	try {
	    StringBuffer query = new StringBuffer("log-level");
	    query.append("?").append("level=").append(Integer.toString(logLevel));
	    String response = getContent(getUrl(query.toString(), true));
	    if (response.contains("New level not given")) throw new AdminException(response.trim());
	} catch (Exception e) {
	    throw new AdminException("Error executing flush-dlr", e);
	}
    }    

    /**
     * reload-lists
     * Re-loads the 'white-list' and 'black-list' URLs provided in the core group. This allows Kannel to keep running while the remote lists change and signal bearerbox to re-load them on the fly. 
     */
    public void reloadLists() throws AdminException
    {
	try {
	    String response = getContent(getUrl("reload-lists", true));
	    if (response.contains("Could not re-load lists")) throw new AdminException(response.trim());
	} catch (Exception e) {
	    throw new AdminException("Error executing reload-lists", e);
	}
    }

    private static String getContent(URL u) throws IOException
    {
	InputStream is = u.openStream();
	String response = parseStreamToString(is, -1);
	is.close();
	return response;
    }

    private static String parseStreamToString(InputStream is, int maxSize) throws IOException
    {
	BufferedReader bf = new BufferedReader(new InputStreamReader(is));
	StringBuffer o = new StringBuffer();
	String line = null;
	while ((line = bf.readLine()) != null && ((maxSize < 0) || (o.length() < maxSize))) {
	    o.append(line + "\n");
	}
	bf.close();
	return o.toString();
    }

}
