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

/**
 * Created by junior on 28/11/16.
 */

public class LoginFragment extends Fragment implements LoginContract.View {

    private Button login_button;
    private EditText et_username;
    private EditText et_password;

    private LoginContract.Presenter mPresenter;
    private ProgressDialog mProgressDialog;

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

    }

    @Override
    public void onResume() {
        super.onResume();

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_layout_login, container, false);
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
                mPresenter.basicLogin(et_username.getText().toString(), et_password.getText().toString());


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
}
