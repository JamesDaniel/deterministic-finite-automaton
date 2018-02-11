package jamesmcgarr.me.statemachineusageexample.statemachine.events;

import org.jeasy.states.api.Event;

/**
 * Created by user on 11/02/18.
 */

public class CoinEvent extends Event {

    public CoinEvent() {
        super("CoinEvent");
    }

    protected CoinEvent(String name) {
        super(name);
    }

}
