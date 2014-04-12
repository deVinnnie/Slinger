package be.mira.slinger;

import java.text.DecimalFormat;
import be.mira.slinger.model.Slinger;

import android.content.SharedPreferences;
import android.content.res.Resources;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.TextView;

public class LengteBepalingFragment extends AbstractSlingerFragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        View rootView = inflater.inflate(R.layout.fragment_lengte_bepalen, container, false);
        return rootView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view,savedInstanceState);
        Resources res = getResources();
        String instructie = res.getQuantityString(R.plurals.instructie, (int) this.getAantalMetingen(), (int) this.getAantalMetingen());
        TextView instructieTekst = (TextView) getView().findViewById(R.id.instructie);
        instructieTekst.setText(instructie);
    }
    
    /**
     * Bereken de lengte van de slinger aan de hand van de huidige chronometer stand.
     * 
     * @param view
     */
    @Override
    public void bereken(View view) {
		double result = Slinger.berekenLengte(this.getPeriode());
		result*= 100; //Converteren naar centimer. 
		DecimalFormat df = new DecimalFormat("###.##"); //Afronden tot op 2 cijfers na de komma.
		String strResult = "Lengte="+df.format(result)+"cm";
		
		TextView lbl_resultaat = (TextView) getView().findViewById(R.id.lbl_resultaat);
		lbl_resultaat.setText(strResult);

        this.addToHistory("Periode="+df.format(this.getPeriode()) + "s : " + "Lengte="+df.format(result)+"cm");
    }
}
