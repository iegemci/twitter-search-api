package vngrs.enesgemci.tweetsearch.ui;

import vngrs.enesgemci.tweetsearch.base.mvp.BaseMvpPresenter;
import vngrs.enesgemci.tweetsearch.data.repository.Repository;
import vngrs.enesgemci.tweetsearch.util.Page;

/**
 * Created by enesgemci on 08/04/2017.
 */

public class MainActivityPresenter extends BaseMvpPresenter<MainActivityView> {

    public MainActivityPresenter(Repository repository) {
        super(repository);
    }

    public void onActivityStarted() {
        getView().showPage(Page.DASHBOARD);
    }
}