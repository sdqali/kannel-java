package org.kannel.sms;

import java.util.Observable;
import java.util.Observer;

public abstract class SmsHandler
    implements Observer
{

    public void update(Observable o, Object arg) 
    {
	handleSms((Sms)arg);
    }

    public abstract void handleSms(Sms sms);

}