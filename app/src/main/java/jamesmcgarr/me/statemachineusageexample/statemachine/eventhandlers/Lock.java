package jamesmcgarr.me.statemachineusageexample.statemachine.eventhandlers;

import android.widget.TextView;

import org.jeasy.states.api.Event;
import org.jeasy.states.api.EventHandler;

import java.util.Date;

/**
 * Created by user on 11/02/18.
 */

public class Lock implements EventHandler {

    private TextView outputTxt;

    public Lock(TextView outputTxt) {
        this.outputTxt = outputTxt;
    }

    @Override
    public void handleEvent(Event event) throws Exception {
        String outputString = "Notified about event '" +
                event.getName() +
                "' triggered at " +
                new Date(event.getTimestamp()) + "\n\n" +
                "Locking turnstile..";
        outputTxt.setText(outputString);
    }
}
