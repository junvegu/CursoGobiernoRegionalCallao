package com.bixspace.ciclodevida.presentation.contracts;

import com.bixspace.ciclodevida.core.BasePresenter;
import com.bixspace.ciclodevida.core.BaseView;
import com.bixspace.ciclodevida.data.PersonaEntity;

import java.util.ArrayList;

/**
 * Created by junior on 30/11/16.
 */

public interface MainContract {


    interface View extends BaseView<Presenter> {
        void showMessage(String msg);

        void loadingIndicator(boolean active);

        void showList(ArrayList<PersonaEntity> personaEntities);

    }

    interface Presenter extends BasePresenter {

        void loadPersons();

    }
}
