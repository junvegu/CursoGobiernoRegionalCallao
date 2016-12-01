package com.bixspace.ciclodevida.presentation.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.bixspace.ciclodevida.R;
import com.bixspace.ciclodevida.data.local.SessionManager;
import com.bixspace.ciclodevida.presentation.activities.LoginActivity;

/**
 * Created by junior on 28/11/16.
 */

public class MainFragment extends Fragment {


    private Button buttonClose;
    public MainFragment() {
        // Requires empty public constructor
    }

    public static MainFragment newInstance() {
        return new MainFragment();
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public void onResume() {
        super.onResume();

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_layout_main, container, false);


        return root;


    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        buttonClose = (Button) getActivity().findViewById(R.id.close_sesion);
        buttonClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                //Cerramos la sesion del session Manager
                SessionManager sessionManager = new SessionManager(getContext());
                sessionManager.closeSession();

                //enviamos al usuario a la pantalla Login
                Intent intent = new Intent(getActivity(), LoginActivity.class);
                startActivity(intent);

                //Cerramos la ventana actual y la quitamos de la pila de activities
                getActivity().finish();

            }
        });



    }



}
