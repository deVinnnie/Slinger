package be.mira.slinger;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;

public class HelpDialogFragment extends DialogFragment {

    int instructionID; //Android Recources ID number.

    public HelpDialogFragment(){}

    public HelpDialogFragment(int instructionID){
        this.instructionID = instructionID;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the Builder class for convenient dialog construction
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage(getResources().getText(this.instructionID))
                .setPositiveButton("Klaar!", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        //((MainActivity) getActivity()).resetUserInteractionTimer();
                        ((MainActivity) getActivity()).dialogActive = false;
                        ((MainActivity) getActivity()).resetUserInteractionTimer();
                    }
                })
                .setTitle(R.string.dialog_title);

        // Create the AlertDialog object and return it
        return builder.create();
    }

    @Override
    public void onDismiss(DialogInterface dialog) {
        super.onDismiss(dialog);
        ((MainActivity) getActivity()).dialogActive = false;
        ((MainActivity) getActivity()).resetUserInteractionTimer();
    }
}