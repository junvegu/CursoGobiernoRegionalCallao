package com.bixspace.ciclodevida.presentation.contracts;

import com.bixspace.ciclodevida.core.BasePresenter;
import com.bixspace.ciclodevida.core.BaseView;

import java.io.File;

/**
 * Created by junior on 30/11/16.
 */

public interface AddPersonContract {


    interface View extends BaseView<Presenter> {

        void showMessage(String msg);

        void loadingIndicator(boolean active);

        void addPersonSucces();

    }

    interface Presenter extends BasePresenter {

        void addPerson(String name, String last_name, int age, String photo);

    }
}
