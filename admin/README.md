# kannel-java-admin #

A wrapper on the HTTP administration service exposed by Kannel. <http://kannel.org/download/kannel-userguide-snapshot/userguide.html#AEN876>

Usage:

    KannelAdmin admin = new HttpKannelAdmin(new URL("http://localhost:13000/), "password");
    //status
    Status status = admin.getStatus();
    //store-status
    StoreStatus storeStatus = admin.getStoreStatus();
    //suspend
    admin.suspend();
    //isolate
    admin.isolate();
    //resume
    admin.resume();
    //shutdown
    admin.shutdown();
    //flush-dlr
    admin.flushDLR();
    //start-smsc
    admin.startSMSC("smsc-id");
    //stop-smsc
    admin.stopSMSC("smsc-id");
    //add-smsc
    admin.addSMSC("smsc-id");
    //remove-smsc
    admin.removeSMSC("smsc-id");
    //restart
    admin.restart();
    //log-level
    admin.logLevel(0);
    //reload-lists
    admin.reloadLists();
