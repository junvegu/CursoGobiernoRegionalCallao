package com.bixspace.ciclodevida.presentation.presenters;

import android.content.Context;

import com.bixspace.ciclodevida.data.AccesToken;
import com.bixspace.ciclodevida.data.remote.ServiceFactory;
import com.bixspace.ciclodevida.data.remote.request.LoginRequest;
import com.bixspace.ciclodevida.presentation.contracts.LoginContract;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by junior on 30/11/16.
 */

public class LoginPresenter implements LoginContract.Presenter {

    private LoginContract.View mView;
    private Context context;


    public LoginPresenter(LoginContract.View mView, Context context) {
        this.mView = mView;
        this.context = context;
        this.mView.setPresenter(this);
    }

    /**
     * Este método permite hacer las peticiones al servidor para poder ingresar a la aplicacion
     *
     * @param username Nombre de usuario
     * @param password Contraseña del usuario
     */
    @Override
    public void basicLogin(String username, String password) {
        mView.loadingIndicator(true);
        LoginRequest loginRequest = ServiceFactory.createService(LoginRequest.class);
        Call<AccesToken> call = loginRequest.loginUser(username,password);

        call.enqueue(new Callback<AccesToken>() {
            @Override
            public void onResponse(Call<AccesToken> call, Response<AccesToken> response) {

                //Entran los codigos de respuesta de la serie 200 y 400

                if(response.isSuccessful()){
                    //200 code
                    mView.loadingIndicator(false);
                    mView.showMessage("Hola usuario con el id : "+response.body().getId());

                }else{

                    // 400 code
                    mView.loadingIndicator(false);
                    mView.showMessage("Credenciales inválidas");
                }




            }

            @Override
            public void onFailure(Call<AccesToken> call, Throwable t) {

                //Entran los errores tipo 500 (Servidor off)
                mView.loadingIndicator(false);
                mView.showMessage("El login ha fallado");
            }
        });


    }

    @Override
    public void start() {

    }
}
