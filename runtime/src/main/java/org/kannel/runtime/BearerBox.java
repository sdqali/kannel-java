package org.kannel.runtime;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class BearerBox
    extends Box
{

    public BearerBox() {
	this.executable = "/usr/bin/bearerbox";
    }

    public void start() throws Exception
    {
	//TODO: - read the pid file
	//      - check to see if it's already running
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

	//TODO: - read the pid file
	//      - destroy pid
	if (checkPid()) killPid();
    }

    // -SStart the system initially at SUSPENDED state (see below, bearerbox only)
    // --suspended
    private boolean startSuspended = false;
    public boolean getStartSuspended() { return this.startSuspended; }
    public void setStartSuspended(boolean startSuspended) { this.startSuspended = startSuspended; }
    
    // -IStart the system initially at ISOLATED state (see below, bearerbox only)
    // --isolated
    private boolean startIsolated = false;
    public boolean getStartIsolated() { return this.startIsolated; }
    public void setStartIsolated(boolean startIsolated) { this.startIsolated = startIsolated; }

}