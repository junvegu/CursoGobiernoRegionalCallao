package com.bixspace.ciclodevida.presentation.fragments;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.bixspace.ciclodevida.R;
import com.bixspace.ciclodevida.core.ScrollChildSwipeRefreshLayout;
import com.bixspace.ciclodevida.data.PersonaEntity;
import com.bixspace.ciclodevida.data.local.SessionManager;
import com.bixspace.ciclodevida.presentation.activities.LoginActivity;
import com.bixspace.ciclodevida.presentation.adapters.PersonsAdapters;
import com.bixspace.ciclodevida.presentation.contracts.MainContract;
import com.bixspace.ciclodevida.presentation.presenters.MainPresenter;

import java.util.ArrayList;

/**
 * Created by junior on 28/11/16.
 */

public class MainFragment extends Fragment  implements MainContract.View{


    private Button buttonClose;
    private MainContract.Presenter mPresenter;
    private PersonsAdapters personsAdapters;
    private LinearLayoutManager linearLayoutManager;
    private RecyclerView recyclerView;
    private ProgressDialog mProgressDialog;
    public MainFragment() {
        // Requires empty public constructor
    }

    public static MainFragment newInstance() {
        return new MainFragment();
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPresenter = new MainPresenter(this,getContext());
        mProgressDialog = new ProgressDialog(getContext());
        mProgressDialog.setTitle("Validando datos ...");
        mProgressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.loadPersons();

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_layout_main, container, false);

        final ScrollChildSwipeRefreshLayout swipeRefreshLayout =
                (ScrollChildSwipeRefreshLayout) root.findViewById(R.id.refresh_layout);
        swipeRefreshLayout.setColorSchemeColors(
                ContextCompat.getColor(getActivity(), R.color.colorPrimary),
                ContextCompat.getColor(getActivity(), R.color.colorPrimaryDark),
                ContextCompat.getColor(getActivity(), R.color.colorAccent)
        );
        // Set the scrolling view in the custom SwipeRefreshLayout.
        swipeRefreshLayout.setScrollUpChild(recyclerView);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                mPresenter.loadPersons();
            }
        });
        return root;


    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        buttonClose = (Button) getActivity().findViewById(R.id.close_sesion);
        recyclerView = (RecyclerView)getActivity().findViewById(R.id.rv_persons);

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


        personsAdapters = new PersonsAdapters(getContext(),new ArrayList<PersonaEntity>());
        linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(personsAdapters);


    }


    @Override
    public void showMessage(String msg) {
        Toast.makeText(getActivity(), msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void loadingIndicator(final boolean active) {

        if (getView() == null) {
            return;
        }
        final SwipeRefreshLayout srl =
                (SwipeRefreshLayout) getView().findViewById(R.id.refresh_layout);

        // Make sure setRefreshing() is called after the layout is done with everything else.
        srl.post(new Runnable() {
            @Override
            public void run() {
                srl.setRefreshing(active);
            }
        });
    }

    @Override
    public void showList(ArrayList<PersonaEntity> personaEntities) {
        personsAdapters = new PersonsAdapters(getContext(),personaEntities);
        recyclerView.setAdapter(personsAdapters);


    }

    @Override
    public void setPresenter(MainContract.Presenter presenter) {
        mPresenter =presenter;
    }
}
