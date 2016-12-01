package com.bixspace.ciclodevida.presentation.contracts;

import android.support.annotation.NonNull;

import com.bixspace.ciclodevida.core.BasePresenter;
import com.bixspace.ciclodevida.core.BaseView;

/**
 * Created by junior on 30/11/16.
 */

public interface LoginContract {


    interface View extends BaseView<Presenter> {
        void showMessage(String msg);

        void loadingIndicator(boolean active);

        void loginSuccess();
    }

    interface Presenter extends BasePresenter {

        void basicLogin(String username, String password);

    }
}
