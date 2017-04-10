package vngrs.enesgemci.tweetsearch.base;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.AnyThread;
import android.support.annotation.UiThread;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.Toolbar;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;

import com.google.gson.Gson;
import com.hannesdorfmann.mosby.mvp.MvpActivity;
import com.squareup.otto.Bus;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import rx.Observer;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import vngrs.enesgemci.tweetsearch.R;
import vngrs.enesgemci.tweetsearch.base.mvp.BaseMvpPresenter;
import vngrs.enesgemci.tweetsearch.base.mvp.BaseMvpView;
import vngrs.enesgemci.tweetsearch.dagger.component.AppComponent;
import vngrs.enesgemci.tweetsearch.data.repository.Repository;
import vngrs.enesgemci.tweetsearch.network.MRequest;
import vngrs.enesgemci.tweetsearch.util.FragmentBuilder;
import vngrs.enesgemci.tweetsearch.util.FragmentFactory;
import vngrs.enesgemci.tweetsearch.util.FragmentTransactionType;
import vngrs.enesgemci.tweetsearch.util.L;
import vngrs.enesgemci.tweetsearch.util.MInjector;
import vngrs.enesgemci.tweetsearch.util.Page;

/**
 * Created by enesgemci on 08/04/2017.
 */

public abstract class BaseActivity<V extends BaseMvpView, P extends BaseMvpPresenter<V>>
        extends MvpActivity<V, P>
        implements BaseMvpView {

    @BindView(R.id.container)
    ConstraintLayout mContainer;

    @BindView(R.id.toolbar)
    Toolbar mToolbar;

    @Inject
    protected Repository repository;

    @Inject
    Bus bus;

    @Inject
    Gson gson;

    private Unbinder unbinder;
    private Subscription subscription;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        injectActivity(App.getAppComponent());
        MInjector.inject(this);

        if (isTablet()) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR_LANDSCAPE);
        }

        if (getCustomLayoutId() == -1) {
            setContentView(R.layout.activity_base);
        } else {
            setContentView(getCustomLayoutId());
        }

        unbinder = ButterKnife.bind(this);

        setSupportActionBar(mToolbar);

        final ViewGroup rootView = ButterKnife.findById(getWindow().getDecorView(), android.R.id.content);

        if (rootView != null) {
            ViewTreeObserver observer = rootView.getViewTreeObserver();

            if (observer.isAlive()) {
                observer.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {

                    @Override
                    public void onGlobalLayout() {
                        ViewTreeObserver observer = rootView.getViewTreeObserver();

                        if (observer != null && observer.isAlive()) {
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                                observer.removeOnGlobalLayoutListener(this);
                            } else {
                                observer.removeGlobalOnLayoutListener(this);
                            }

                            onActivityStarted(getIntent());
                        }
                    }
                });
            }
        } else {
            onActivityStarted(getIntent());
        }
    }

    @Override
    public boolean isTablet() {
        return this.getResources().getBoolean(R.bool.is_tablet);
    }

    public int getCustomLayoutId() {
        return -1;
    }

    public abstract void onActivityStarted(Intent intent);

    public abstract void injectActivity(AppComponent component);

    public void setTitle(String title) {
        mToolbar.setTitle(title);
    }

    @Override
    protected void onDestroy() {
        if (unbinder != null) {
            unbinder.unbind();
        }

        try {
            bus.unregister(getPresenter());
        } catch (Exception e) {
        }

        try {
            bus.unregister(this);
        } catch (Exception e) {
        }

        super.onDestroy();
    }

    @Override
    protected void onResume() {
        super.onResume();

        try {
            bus.unregister(getPresenter());
        } catch (Exception e) {
        }

        try {
            bus.unregister(this);
        } catch (Exception e) {
        }

        try {
            bus.register(getPresenter());
        } catch (Exception e) {
        }

        try {
            bus.register(this);
        } catch (Exception e) {
        }
    }

    @Override
    protected void onPause() {
        if (subscription != null && !subscription.isUnsubscribed()) {
            subscription.unsubscribe();
        }

        super.onPause();
    }

    @Override
    public FragmentBuilder getPage(Page page, Object... obj) {
        return FragmentFactory.getInstace().getFragment(page, obj);
    }

    @Override
    public void showPage(Page page, Object... obj) {
        showPage(getPage(page, obj));
    }

    @Override
    public void showPage(FragmentBuilder builder) {
        Page page = builder.getFragment().getPage();
        String TAG = page.name();

        FragmentManager fManager = builder.getFragmentManager() != null
                ? builder.getFragmentManager()
                : getSupportFragmentManager();
        FragmentTransaction fTransaction = fManager.beginTransaction();

        int containerId = -1;

        if (builder.isSettedContainer()) {
            containerId = builder.getContainerId();
        } else if (mContainer != null) {
            containerId = mContainer.getId();
        }

        switch (builder.getTransactionAnimation()) {
            case ENTER_FROM_LEFT:
                fTransaction.setCustomAnimations(
                        R.anim.anim_horizontal_fragment_in_from_pop, R.anim.anim_horizontal_fragment_out_from_pop,
                        R.anim.anim_horizontal_fragment_in, R.anim.anim_horizontal_fragment_out);
                break;
            case ENTER_FROM_RIGHT:
                fTransaction.setCustomAnimations(
                        R.anim.anim_horizontal_fragment_in, R.anim.anim_horizontal_fragment_out,
                        R.anim.anim_horizontal_fragment_in_from_pop, R.anim.anim_horizontal_fragment_out_from_pop);
                break;
            case ENTER_FROM_BOTTOM:
                fTransaction.setCustomAnimations(
                        R.anim.anim_vertical_fragment_in, R.anim.anim_vertical_fragment_out,
                        R.anim.anim_vertical_fragment_in_from_pop, R.anim.anim_vertical_fragment_out_from_pop);
                break;
            case ENTER_FROM_RIGHT_STACK:
                fTransaction.setCustomAnimations(
                        R.anim.anim_open_next, R.anim.anim_close_main,
                        R.anim.anim_open_main, R.anim.anim_close_next);
                break;
            case ENTER_FROM_RIGHT_NO_ENTRANCE:
                fTransaction.setCustomAnimations(
                        0, R.anim.anim_horizontal_fragment_out,
                        R.anim.anim_horizontal_fragment_in_from_pop, R.anim.anim_horizontal_fragment_out_from_pop);
                break;
            case ENTER_WITH_ALPHA:
//                        fTransaction.setCustomAnimations(
//                                R.anim.anim_alphain, R.anim.anim_alphaout,
//                                R.anim.anim_alphain, R.anim.anim_alphaout);
                fTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                break;
            case NO_ANIM:
            default:
                break;
        }

        if (containerId == -1) {
            throw new RuntimeException("Fragment Container is Empty");
        }

        if (builder.isClearBackStack()) {
            goToPage(null);
        }

        BaseFragment fragment = builder.getFragment();

        if (builder.getTransactionType() == FragmentTransactionType.REPLACE) {
            fTransaction.replace(containerId, fragment, TAG);
        } else {
            fTransaction.add(containerId, fragment, TAG);
        }

        if (builder.isAddToBackStack()) {
            fTransaction.addToBackStack(TAG);
        }

        fTransaction.commitAllowingStateLoss();
    }

    public void goToPage(Page page) {
        if (page != null) {
            getSupportFragmentManager().popBackStack(page.name(), FragmentManager.POP_BACK_STACK_INCLUSIVE);
        } else {
            getSupportFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
        }
    }

    @Override
    public void sendRequest(MRequest<Object> request) {
        if (isConnectedToNet()) {
            showLoading();

            subscription = request.getObservable()
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeOn(Schedulers.newThread())
                    .subscribe(new Observer<Object>() {

                        @Override
                        public void onCompleted() {

                        }

                        @Override
                        public void onError(Throwable e) {
                            L.e("----------------------------------------");
                            L.e("----------------------------------------");
                            L.e(e);
                        }

                        @Override
                        public void onNext(Object response) {
                            L.e("----------------------------------------");
                            L.e("----------------------------------------");
                            L.i(gson.toJson(response));
                            bus.post(response);
                        }
                    });
        } else {
            // TODO: 08/04/2017 call offline repository or show error
        }
    }

    @AnyThread
    public boolean isConnectedToNet() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
        boolean connected = false;

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            Network[] networkInfoList = connectivityManager.getAllNetworks();

            if (networkInfoList == null || networkInfoList.length == 0) {
                return false;
            }

            for (Network network : networkInfoList) {
                NetworkInfo networkInfo = connectivityManager.getNetworkInfo(network);

                if (networkInfo != null) {
                    connected = networkInfo.isConnected();

                    if (connected) {
                        return true;
                    }
                }
            }
        } else {
            NetworkInfo[] networkInfoList = connectivityManager.getAllNetworkInfo();

            if (networkInfoList == null || networkInfoList.length == 0) {
                return false;
            }

            for (NetworkInfo networkInfo : networkInfoList) {
                if (networkInfo != null) {
                    connected = networkInfo.isConnected();

                    if (connected) {
                        return true;
                    }
                }
            }
        }

        return connected;
    }

    @UiThread
    public void showLoading() {
        runOnUiThread(() -> {
//            if (mContainer != null) {
//                val fragment = getSupportFragmentManager().findFragmentById(mContainer.getId());
//
//                if (fragment != null) {
//                    if (((BaseFragment) fragment).showLoadingOnContainer()) {
//                        if (mProgressableLayout != null) {
//                            try {
//                                mProgressableLayout.show();
//                            } catch (Exception e) {
//                                Log.e(TAG,e.getMessage());
//                            }
//
//                            return;
//                        }
//                    }
//                }
//            }
//
//            responseHandler.lockScreen(isDialogRequest);
        });
    }

    @UiThread
    public synchronized void dismissLoading() {
//        if (!requestHandler.isOnAction()) {
//            runOnUiThread(() -> {
//                responseHandler.unlockScreen();
//
//                if (mProgressableLayout != null) {
//                    try {
//                        mProgressableLayout.hide();
//                    } catch (Exception e) {
//                        Log.e(TAG,e.getMessage());
//                    }
//                }
//            });
//        }
    }
}
