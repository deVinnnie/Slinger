package be.mira.slinger;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import java.text.DecimalFormat;

import be.mira.slinger.model.Slinger;

public class ValversnellingFragment extends AbstractSlingerFragment {
    private RadioGroup radioGroup;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View rootView = inflater.inflate(R.layout.fragment_valversnelling_bepalen, container, false);
        return rootView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        radioGroup = (RadioGroup) getView().findViewById(R.id.radioGroup);
    }

    /**
     * Geef lengte van de slinger in meter.
     *
     * @return Lengte in meter.
     */
    private double getLengte(){
        //Get selected radio button from radioGroup
        int selectedId = radioGroup.getCheckedRadioButtonId();
        RadioButton selectedRadioButton = (RadioButton) getView().findViewById(selectedId);

        if(selectedRadioButton == null){
            return 0;
        }
        Object data = selectedRadioButton.getText();

        if(null == data){
            return 0;
        }

        String strLengte = (String) data;
        double lengte = Double.parseDouble(strLengte.substring(0, strLengte.length() - 2));
        return lengte / 100; //Converteert van cm naar m.
    }
    
    /**
     * Bereken de lengte van de slinger aan de hand van de huidige chronometer stand.
     * 
     * @param view
     */
    @Override
    public void bereken(View view) {
        double lengte = this.getLengte();
        double result = Slinger.berekenValversnelling(this.getPeriode(),lengte);

        DecimalFormat df = new DecimalFormat("###.##"); //Afronden tot op 2 cijfers na de komma.
        String strResult = "Valversnelling=" + df.format(result)+"m/s";

        TextView lbl_resultaat = (TextView) getView().findViewById(R.id.lbl_resultaat);
        lbl_resultaat.setText(strResult);

//        this.addToHistory("Periode="+df.format(this.getPeriode()) + "s : " + "Valversnelling="+strResult);
    }

    @Override
    public void showHelpDialog() {
        super.showHelpDialog();

        //Dialog
        FragmentTransaction ft = this.getActivity().getSupportFragmentManager().beginTransaction();
        Fragment prev = this.getActivity().getSupportFragmentManager().findFragmentByTag("dialog");
        if (prev != null) {
            ft.remove(prev);
        }
        ft.addToBackStack(null);

        // Create and show the dialog.
        DialogFragment help = new HelpDialogFragment(R.string.dialog_instruction_valversnelling);
        help.show(ft, "dialog");
    }
}
