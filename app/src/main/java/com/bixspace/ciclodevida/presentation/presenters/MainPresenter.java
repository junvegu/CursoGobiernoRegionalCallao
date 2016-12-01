package com.bixspace.ciclodevida.presentation.presenters;

import android.content.Context;

import com.bixspace.ciclodevida.data.AccesToken;
import com.bixspace.ciclodevida.data.ResponseUser;
import com.bixspace.ciclodevida.data.local.SessionManager;
import com.bixspace.ciclodevida.data.remote.ServiceFactory;
import com.bixspace.ciclodevida.data.remote.request.LoginRequest;
import com.bixspace.ciclodevida.presentation.contracts.LoginContract;
import com.bixspace.ciclodevida.presentation.contracts.MainContract;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by junior on 30/11/16.
 */

public class MainPresenter implements MainContract.Presenter {

    private MainContract.View mView;
    private Context context;
    private SessionManager sessionManager;


    public MainPresenter(MainContract.View mView, Context context) {
        this.mView = mView;
        this.context = context;
        this.mView.setPresenter(this);
        sessionManager = new SessionManager(context);
    }




    @Override
    public void start() {
        loadPersons();
    }

    @Override
    public void loadPersons() {
        mView.loadingIndicator(true);
        LoginRequest loginRequest = ServiceFactory.createServiceAlternative(LoginRequest.class);
        Call<ResponseUser> call = loginRequest.loadItems();

        call.enqueue(new Callback<ResponseUser>() {
            @Override
            public void onResponse(Call<ResponseUser> call, Response<ResponseUser> response) {

                //Entran los codigos de respuesta de la serie 200 y 400

                if(response.isSuccessful()){
                    //200 code
                    //Aqui obtenemos todos los elementos de la lista


                    mView.loadingIndicator(false);
                    mView.showList(response.body().getPersonas());


                }else{

                    // 400 code
                    mView.loadingIndicator(false);
                    mView.showMessage("Ocurrió un problema");
                }

            }

            @Override
            public void onFailure(Call<ResponseUser> call, Throwable t) {

                //Entran los errores tipo 500 (Servidor off)
                mView.loadingIndicator(false);
                mView.showMessage("Revise su conexión a internet");
            }
        });
    }
}
