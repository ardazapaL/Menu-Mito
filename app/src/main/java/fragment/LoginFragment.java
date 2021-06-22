package fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.menumito.R;

import activity.HomeActivity;

public class LoginFragment extends Fragment {

    private EditText editTextEmail, editTextPass;
    private Button btnLogin;
    private Intent goLogin;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_login, container, false);

        /* variable */
        editTextEmail = view.findViewById(R.id.input_email);
        editTextPass = view.findViewById(R.id.input_password);

        /* intent login */
        goLogin = new Intent(getActivity(), HomeActivity.class);
        goLogin.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK
                | Intent.FLAG_ACTIVITY_CLEAR_TASK);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Login();
            }
        });
        return view;
    }

    /* fungsi login */
    private void Login() {

        String email = editTextEmail.getText().toString();
        String password = editTextPass.getText().toString();

        /* if email text is empty */
        if (email.isEmpty()) {
            editTextEmail.setError(getString(R.string.empty_field));
            return;
        }
        /* if pass text is empty */
        if (password.isEmpty()) {
            editTextPass.setError(getString(R.string.empty_field));
            return;
        }

        startActivity(goLogin);
    }
}
