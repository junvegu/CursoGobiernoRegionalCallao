package com.bixspace.ciclodevida.presentation.presenters;

import android.content.Context;

import com.bixspace.ciclodevida.data.AccesToken;
import com.bixspace.ciclodevida.data.ResponseAddPersonModel;
import com.bixspace.ciclodevida.data.local.SessionManager;
import com.bixspace.ciclodevida.data.remote.ServiceFactory;
import com.bixspace.ciclodevida.data.remote.request.LoginRequest;
import com.bixspace.ciclodevida.presentation.contracts.AddPersonContract;
import com.bixspace.ciclodevida.presentation.contracts.LoginContract;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by junior on 30/11/16.
 */

public class AddPersonPresenter implements AddPersonContract.Presenter {

    private AddPersonContract.View mView;
    private Context context;
    private SessionManager sessionManager;


    public AddPersonPresenter(AddPersonContract.View mView, Context context) {
        this.mView = mView;
        this.context = context;
        this.mView.setPresenter(this);
        sessionManager = new SessionManager(context);
    }



    @Override
    public void start() {

    }

    @Override
    public void addPerson(String name, String last_name, int age, String photo) {
        mView.loadingIndicator(true);
        LoginRequest loginRequest = ServiceFactory.createServiceAlternative(LoginRequest.class);
        Call<ResponseAddPersonModel> call = loginRequest.addPerson(name,last_name,age,photo);

        call.enqueue(new Callback<ResponseAddPersonModel>() {
            @Override
            public void onResponse(Call<ResponseAddPersonModel> call, Response<ResponseAddPersonModel> response) {

                //Entran los codigos de respuesta de la serie 200 y 400

                if(response.isSuccessful()){
                    //200 code
                    //El usuario se ha logueado satisfactoriamente

                    mView.loadingIndicator(false);
                    mView.showMessage(response.body().getMensaje());
                    mView.addPersonSucces();
                }else{

                    // 400 code
                    mView.loadingIndicator(false);
                    mView.showMessage("Credenciales inválidas");
                }




            }

            @Override
            public void onFailure(Call<ResponseAddPersonModel> call, Throwable t) {

                //Entran los errores tipo 500 (Servidor off)
                mView.loadingIndicator(false);
                mView.showMessage("Revise su conexión a internet");
            }
        });

    }
}
