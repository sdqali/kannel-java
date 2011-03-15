# kannel-java-runtime #

A utility for starting/stopping the "box" daemons from Java. Currently, only the "bearerbox" is implemented. 

Usage:

    BearerBox box = new BearerBox();
    box.setConfigFileName("/etc/kannel.conf");
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
