package vngrs.enesgemci.tweetsearch.base.mvp;

import com.hannesdorfmann.mosby.mvp.MvpBasePresenter;
import com.squareup.otto.Bus;

import javax.inject.Inject;

import vngrs.enesgemci.tweetsearch.data.repository.Repository;

/**
 * Created by enesgemci on 08/04/2017.
 */

public abstract class BaseMvpPresenter<V extends BaseMvpView> extends MvpBasePresenter<V> {

    @Inject
    Bus bus;

    protected Repository repository;

    public BaseMvpPresenter(Repository repository) {
        this.repository = repository;
    }

    public Bus getBus() {
        return bus;
    }
}