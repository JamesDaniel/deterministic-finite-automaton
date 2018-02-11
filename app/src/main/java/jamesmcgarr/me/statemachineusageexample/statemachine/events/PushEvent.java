package jamesmcgarr.me.statemachineusageexample.statemachine.events;

import org.jeasy.states.api.Event;

/**
 * Created by user on 11/02/18.
 */

public class PushEvent extends Event {

    public PushEvent() {
        super("PushEvent");
    }

    protected PushEvent(String name) {
        super(name);
    }

}
