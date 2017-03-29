package be.mira.slinger;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.text.DecimalFormat;

import be.mira.slinger.model.Slinger;

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
    }

    @Override
    public void showHelpDialog(){
        super.showHelpDialog();
        //Dialog
        FragmentTransaction ft = this.getActivity().getSupportFragmentManager().beginTransaction();
        Fragment prev = this.getActivity().getSupportFragmentManager().findFragmentByTag("dialog");
        if (prev != null) {
            ft.remove(prev);
        }
        ft.addToBackStack(null);

        // Create and show the dialog.
        DialogFragment help = new HelpDialogFragment(R.string.dialog_instruction_lengte);
        help.show(ft, "dialog");
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
