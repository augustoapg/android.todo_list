package sheridan.araujope.mytodo.ui;

import android.app.Dialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;
import sheridan.araujope.mytodo.R;

public class AboutFragment extends DialogFragment {
    public AboutFragment() {
        // Required empty public constructor
    }


    public static AboutFragment newInstance(){
        return new AboutFragment();
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {

        // create a new AlertDialog Builder
        AlertDialog.Builder builder =
                new AlertDialog.Builder(getActivity());

        builder.setTitle(R.string.app_name);
        builder.setMessage(R.string.author);

        builder.setPositiveButton(android.R.string.ok, null);

        return builder.create();
    }
}
