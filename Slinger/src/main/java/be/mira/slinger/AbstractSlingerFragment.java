package be.mira.slinger;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.SystemClock;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.ListView;

import java.util.ArrayList;

public abstract class AbstractSlingerFragment extends Fragment {
    private ArrayList<String> history = new ArrayList<String>();
    private Chronometer chronometer;
    private double periode=0; // in s
    private boolean chronoRunning = false;
    private double aantalMetingen;
    private Button start_stop_button;

    //Maximum value for chrono before it is automatically reset.
    private double maxTimeEllapsed;//in s

    //<editor-fold desc="Getters en Setters">
    protected void setChronometer(Chronometer chronometer) {
        this.chronometer = chronometer;
    }

    public Chronometer getChronometer() {
        return chronometer;
    }

    public double getPeriode() {
        return periode;
    }

    protected void setPeriode(double periode) {
        this.periode = periode;
    }

    public boolean isChronoRunning() {
        return chronoRunning;
    }

    protected void setChronoRunning(boolean chronoRunning) {
        this.chronoRunning = chronoRunning;
    }

    public double getAantalMetingen() {
        return aantalMetingen;
    }

    protected void setAantalMetingen(double aantalMetingen) {
        this.aantalMetingen = aantalMetingen;
    }

    public Button getStart_stop_button() {
        return start_stop_button;
    }

    protected void setStart_stop_button(Button start_stop_button) {
        this.start_stop_button = start_stop_button;
    }
    //</editor-fold>

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.setChronometer((Chronometer) getView().findViewById(R.id.chrono_periode));

        this.getChronometer().setOnChronometerTickListener(new Chronometer.OnChronometerTickListener() {
            @Override
            public void onChronometerTick(Chronometer chronometer) {
                //Reset chronomoter if the ellapsed time is larger than maxTimeEllapsed.
                double timeElapsed = SystemClock.elapsedRealtime() - chronometer.getBase(); //miliseconds.
                if((timeElapsed / 1000d) >= maxTimeEllapsed) {
                    chronometer.stop();
                    AbstractSlingerFragment.this.setPeriode((timeElapsed/1000d) / getAantalMetingen());
                    AbstractSlingerFragment.this.setChronoRunning(false);
                    AbstractSlingerFragment.this.getStart_stop_button().setText("Start");
                }
            }
        });

        Button startStopButton = (Button) getView().findViewById(R.id.start_stop_button);
        startStopButton.setOnClickListener(
                new Button.OnClickListener() {
                    public void onClick(View view) {
                        startStopChrono(view);
                    }
                }
        );

        this.setStart_stop_button(startStopButton);

        Button berekenButton = (Button) getView().findViewById(R.id.bereken_button);
        berekenButton.setOnClickListener(
                new Button.OnClickListener() {
                    public void onClick(View view) {
                        bereken(view);
                    }
                }
        );

        //Load Preferences
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(getActivity());
        try{
            this.aantalMetingen = Double.parseDouble(sharedPref.getString("aantal_metingen",""));
        }
        catch(NumberFormatException ex){
            this.aantalMetingen = 10d;
        }

        try{
            this.maxTimeEllapsed = Double.parseDouble(sharedPref.getString("max_time_ellapsed",""));
        }
        catch(NumberFormatException ex){
            this.maxTimeEllapsed = 120d;
        }

        ListView lst_history = (ListView) getView().findViewById(R.id.lst_history);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this.getActivity(), android.R.layout.simple_list_item_1, android.R.id.text1, history);
        lst_history.setAdapter(adapter);
    }

    public void startStopChrono(View view){
        Chronometer chronometer = this.getChronometer();
        if(!this.isChronoRunning()){
            chronometer.setBase(SystemClock.elapsedRealtime());
            chronometer.start();
            this.setChronoRunning(true);
            this.getStart_stop_button().setText("Stop");
            getView().findViewById(R.id.bereken_button).setEnabled(false);
        }
        else{
            chronometer.stop();
            double timeElapsed = SystemClock.elapsedRealtime() - chronometer.getBase();
            this.setPeriode((timeElapsed/1000d) / this.getAantalMetingen());
            this.setChronoRunning(false);
            this.getStart_stop_button().setText("Start");
            getView().findViewById(R.id.bereken_button).setEnabled(true);
        }
    }

    public abstract void bereken(View view);

    protected void addToHistory(String message){
        ListView lst_history = (ListView) getView().findViewById(R.id.lst_history);
        ((ArrayAdapter<String>) lst_history.getAdapter()).insert(message, 0);
        ((ArrayAdapter) lst_history.getAdapter()).notifyDataSetChanged();
    }
}
