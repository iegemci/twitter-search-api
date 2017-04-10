package vngrs.enesgemci.tweetsearch.ui;

import android.content.Intent;
import android.support.annotation.NonNull;

import vngrs.enesgemci.tweetsearch.base.BaseActivity;
import vngrs.enesgemci.tweetsearch.dagger.component.AppComponent;

public class MainActivity
        extends BaseActivity<MainActivityView, MainActivityPresenter>
        implements MainActivityView {

    @Override
    public void onActivityStarted(Intent intent) {
        getPresenter().onActivityStarted();
    }

    @Override
    public void injectActivity(AppComponent component) {
        component.inject(this);
    }

    @NonNull
    @Override
    public MainActivityPresenter createPresenter() {
        return new MainActivityPresenter(repository);
    }
}
