package org.kannel.sms;

import java.util.Observable;
import java.util.Observer;

public abstract class DlrHandler
    implements Observer
{

    public void update(Observable o, Object arg) 
    {
	handleDlr((Dlr)arg);
    }

    public abstract void handleDlr(Dlr dlr);

}
