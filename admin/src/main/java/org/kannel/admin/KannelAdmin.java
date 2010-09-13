package org.kannel.admin;

/**
 * Interface to Kannel administration
 *
 * @author Garth Patil <garthpatil@gmail.com>
 */
public interface KannelAdmin
{

    /**
     * status
     * Get the current status of the gateway in a text version. Tells the current state (see above) and total number of messages relied and queuing in the system right now. Also lists the total number of smsbox and wapbox connections. No password required, unless status-password set, in which case either that or main admin password must be supplied.
     */
    public Status getStatus() throws AdminException;

    /**
     * store-status
     * Get the current content of the store queue of the gateway in a text version. No password required, unless status-password set, in which case either that or main admin password must be supplied.
     */
    public StoreStatus getStoreStatus() throws AdminException;

    /**
     * suspend
     * Set Kannel state as 'suspended' (see above). Password required.
     */
    public void suspend() throws AdminException;

    /**
     * isolate
     * Set Kannel state as 'isolated' (see above). Password required.
     */
    public void isolate() throws AdminException;

    /**
     * resume
     * Set Kannel state as 'running' if it is suspended or isolated. Password required.
     */
    public void resume() throws AdminException;

    /**
     * shutdown
     * Bring down the gateway, by setting state to 'shutdown'. After a shutdown is initiated, there is no other chance to resume normal operation. However, 'status' command still works. Password required. If shutdown is sent for a second time, the gateway is forced down, even if it has still messages in queue.
     */
    public void shutdown() throws AdminException;

    /**
     * flush-dlr
     * If Kannel state is 'suspended' this will flush all queued DLR messages in the current storage space. Password required.
     */
    public void flushDLR() throws AdminException;

    /**
     * start-smsc
     * Re-start a single SMSC link. Password required. Additionally the smsc parameter must be given to identify which smsc-admin-id should be re-started. The configuration for the SMSC link is re-read from disk before the action is performed.
     */
    public void startSMSC(String smsc) throws AdminException;

    /**
     * stop-smsc
     * Shutdown a single SMSC link. Password required. Additionally the smsc parameter must be given (see above).
     */
    public void stopSMSC(String smsc) throws AdminException;

    /**
     * add-smsc
     * Adds an SMSC link previously removed or created after the service was started. Password required. Additionally the smsc parameter must be given (see above).
     */
    public void addSMSC(String smsc) throws AdminException;

    /**
     * remove-smsc
     * Removes an existing SMSC link. Password required. Additionally the smsc parameter must be given (see above). If you want a permanent removal, you should also remove the entry from the configuration file or it will be recreated after a service restart.
     */
    public void removeSMSC(String smsc) throws AdminException;

    /**
     * restart
     * Re-start whole bearerbox, hence all SMSC links. Password required. Beware that you loose the smsbox connections in such a case.
     */
    public void restart() throws AdminException;

    /**
     * loglevel
     * Set Kannel log-level of log-files while running. This allows you to change the current log-level of the log-files on the fly.
     */
    public void logLevel(int logLevel) throws AdminException;
    
    /**
     * reload-lists
     * Re-loads the 'white-list' and 'black-list' URLs provided in the core group. This allows Kannel to keep running while the remote lists change and signal bearerbox to re-load them on the fly. 
     */
    public void reloadLists() throws AdminException;
}