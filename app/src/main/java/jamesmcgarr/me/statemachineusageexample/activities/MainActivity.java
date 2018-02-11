package jamesmcgarr.me.statemachineusageexample.activities;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import org.jeasy.states.api.FiniteStateMachine;
import org.jeasy.states.api.FiniteStateMachineException;
import org.jeasy.states.api.State;
import org.jeasy.states.api.Transition;
import org.jeasy.states.core.FiniteStateMachineBuilder;
import org.jeasy.states.core.TransitionBuilder;

import java.util.HashSet;
import java.util.Set;

import jamesmcgarr.me.statemachineusageexample.R;
import jamesmcgarr.me.statemachineusageexample.statemachine.eventhandlers.Lock;
import jamesmcgarr.me.statemachineusageexample.statemachine.eventhandlers.Unlock;
import jamesmcgarr.me.statemachineusageexample.statemachine.events.CoinEvent;
import jamesmcgarr.me.statemachineusageexample.statemachine.events.PushEvent;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button coinBtn;
    private Button pushBtn;
    private TextView outputTxt;
    private ImageView gitHub;

    private FiniteStateMachine turnstileStateMachine;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        coinBtn = findViewById(R.id.coin);
        pushBtn = findViewById(R.id.push);
        outputTxt = findViewById(R.id.outputTxt);
        gitHub = findViewById(R.id.github);

        coinBtn.setOnClickListener(this);
        pushBtn.setOnClickListener(this);
        gitHub.setOnClickListener(this);

        State locked = new State("locked");
        State unlocked = new State("unlocked");

        Set<State> states = new HashSet<>();
        states.add(locked);
        states.add(unlocked);

        Transition unlock = new TransitionBuilder()
                .name("unlock")
                .sourceState(locked)
                .eventType(CoinEvent.class)
                .eventHandler(new Unlock(outputTxt))
                .targetState(unlocked)
                .build();

        Transition pushLocked = new TransitionBuilder()
                .name("pushLocked")
                .sourceState(locked)
                .eventType(PushEvent.class)
                .targetState(locked)
                .build();

        Transition lock = new TransitionBuilder()
                .name("lock")
                .sourceState(unlocked)
                .eventType(PushEvent.class)
                .eventHandler(new Lock(outputTxt))
                .targetState(locked)
                .build();

        Transition coinUnlocked = new TransitionBuilder()
                .name("coinUnlocked")
                .sourceState(unlocked)
                .eventType(CoinEvent.class)
                .targetState(unlocked)
                .build();

        turnstileStateMachine = new FiniteStateMachineBuilder(states, locked)
                .registerTransition(lock)
                .registerTransition(pushLocked)
                .registerTransition(unlock)
                .registerTransition(coinUnlocked)
                .build();
    }

    public void onClick(View v) {
        try {
            handleBtnClick(v.getId());
        } catch (FiniteStateMachineException ex) {
            ex.printStackTrace();
        }
    }

    private void handleBtnClick(int btnId) throws FiniteStateMachineException {
        switch (btnId) {
            case R.id.push:
                turnstileStateMachine.fire(new PushEvent());
                break;
            case R.id.coin:
                turnstileStateMachine.fire(new CoinEvent());
                break;
            case R.id.github:
                launchGitHub();
        }
    }

    private void launchGitHub() {
        Uri uri = Uri.parse("https://github.com/JamesDaniel/deterministic-finite-automaton");
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        startActivity(intent);
    }
}
