package org.kannel.runtime;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * BearerBox runtime control.
 *
 * @author Garth Patil <garthpatil@gmail.com>
 */
public class BearerBox
    extends Box
{

    public BearerBox() {
	super();
	this.executable = "/usr/local/sbin/bearerbox";
    }

    public BearerBox(String executable) {
	super();
	this.executable = executable;
    }

    public void start() throws Exception
    {
	if (checkPid()) killPid();

	List<String> args = new ArrayList<String>();
	args.add(this.executable);
	addArgs(args); //add defaults
	if (startSuspended) args.add("-S");
	if (startIsolated) args.add("-I");
	args.add(this.configFileName); //add config file

	ProcessBuilder pb = new ProcessBuilder(args);
	process = pb.start();
    }

    public void stop() throws Exception
    {
	process.destroy();
	process.waitFor();

	if (checkPid()) killPid();
    }

    /**
     * -SStart the system initially at SUSPENDED state (see below, bearerbox only)
     * --suspended
     */
    private boolean startSuspended = false;
    public boolean getStartSuspended() { return this.startSuspended; }
    public void setStartSuspended(boolean startSuspended) { this.startSuspended = startSuspended; }
    
    /**
     * -IStart the system initially at ISOLATED state (see below, bearerbox only)
     * --isolated
     */
    private boolean startIsolated = false;
    public boolean getStartIsolated() { return this.startIsolated; }
    public void setStartIsolated(boolean startIsolated) { this.startIsolated = startIsolated; }

    /**
     * Main method for testing. Ctrl-c will kill this and the running box.
     */
    public static void main(String[] argv)
    {
	BearerBox box = new BearerBox();
	box.setConfigFileName(argv[0]);
	try {
	    box.start();
	    BufferedReader in = new BufferedReader(new InputStreamReader(box.process.getErrorStream()));
	    String line = null;
	    while (true) {
		while((line = in.readLine()) != null) {
		    System.out.println(line);
		}
		Thread.sleep(1000);
	    }
	} catch (Exception e) {
	    e.printStackTrace();
	}
    }
}