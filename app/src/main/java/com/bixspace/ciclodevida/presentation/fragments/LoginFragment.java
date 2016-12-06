package com.bixspace.ciclodevida.presentation.fragments;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.bixspace.ciclodevida.R;
import com.bixspace.ciclodevida.presentation.activities.LoginActivity;
import com.bixspace.ciclodevida.presentation.activities.MainActivity;
import com.bixspace.ciclodevida.presentation.contracts.LoginContract;
import com.bixspace.ciclodevida.presentation.presenters.LoginPresenter;
import com.mobsandgeeks.saripaar.ValidationError;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.annotation.Email;
import com.mobsandgeeks.saripaar.annotation.NotEmpty;
import com.mobsandgeeks.saripaar.annotation.Password;
import com.mobsandgeeks.saripaar.annotation.Past;

import java.util.List;

/**
 * Created by junior on 28/11/16.
 */

public class LoginFragment extends Fragment implements LoginContract.View,
        Validator.ValidationListener{



    private Button login_button;

    @NotEmpty(message = "Este campo no puede ser vacio")
    @Email( message = "Formato de email inválido")
    private EditText et_username;


    @NotEmpty(message = "Este campo no puede ser vacio")
    @Password(min = 6, message = "Minimo de caracteres es de 6")

    private EditText et_password;

    private LoginContract.Presenter mPresenter;
    private ProgressDialog mProgressDialog;
    private Validator validator;

    public LoginFragment() {
        // Requires empty public constructor
    }

    public static LoginFragment newInstance() {
        return new LoginFragment();
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPresenter = new LoginPresenter(this, getContext());
        mProgressDialog = new ProgressDialog(getContext());
        mProgressDialog.setTitle("Validando datos ...");
        mProgressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);


        validator = new Validator(this);
        validator.setValidationListener(this);

    }

    @Override
    public void onResume() {
        super.onResume();

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_layout_login, container, false);


        // et_username = (EditText) root.findViewById(R.id.et_username);

        return root;

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        et_password = (EditText) getActivity().findViewById(R.id.et_password);
        et_username = (EditText) getActivity().findViewById(R.id.et_username);
        login_button = (Button) getActivity().findViewById(R.id.btn_login);


        login_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // Intent intent = new Intent(getContext(), MainActivity.class);
                //startActivity(intent);
                validator.validate();

            }
        });

    }


    @Override
    public void showMessage(String msg) {
        Toast.makeText(getContext(), msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void loadingIndicator(boolean active) {

        if (active) {
            mProgressDialog.show();
        } else {
            if (mProgressDialog.isShowing())
                mProgressDialog.dismiss();
        }
    }

    @Override
    public void loginSuccess() {
        Intent intent = new Intent(getActivity(),MainActivity.class);
        startActivity(intent);
        getActivity().finish();
    }

    @Override
    public void setPresenter(LoginContract.Presenter presenter) {
        this.mPresenter = presenter;
    }


    @Override
    public void onValidationSucceeded() {

        mPresenter.basicLogin(et_username.getText().toString(), et_password.getText().toString());


    }

    @Override
    public void onValidationFailed(List<ValidationError> errors) {
        for (ValidationError error : errors) {
            View view = error.getView();
            String message = error.getCollatedErrorMessage(getContext());

            // Display error messages ;)
            if (view instanceof EditText) {
                ((EditText) view).setError(message);
            } else {

            }
        }
    }
}
