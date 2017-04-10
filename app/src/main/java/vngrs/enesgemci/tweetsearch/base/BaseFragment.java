package vngrs.enesgemci.tweetsearch.base;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;

import com.hannesdorfmann.mosby.mvp.MvpFragment;
import com.squareup.otto.Bus;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import vngrs.enesgemci.tweetsearch.base.mvp.BaseMvpPresenter;
import vngrs.enesgemci.tweetsearch.base.mvp.BaseMvpView;
import vngrs.enesgemci.tweetsearch.dagger.component.AppComponent;
import vngrs.enesgemci.tweetsearch.data.repository.Repository;
import vngrs.enesgemci.tweetsearch.network.MRequest;
import vngrs.enesgemci.tweetsearch.util.FragmentBuilder;
import vngrs.enesgemci.tweetsearch.util.L;
import vngrs.enesgemci.tweetsearch.util.Page;

/**
 * Created by enesgemci on 08/04/2017.
 */

public abstract class BaseFragment<V extends BaseMvpView, P extends BaseMvpPresenter<V>>
        extends MvpFragment<V, P>
        implements BaseMvpView {

    @Inject
    Bus bus;

    @Inject
    protected Repository repository;

    protected ViewGroup mContainer;
    private Unbinder unbinder;

    public abstract void onFragmentStarted();

    @NonNull
    public abstract Page getPage();

    public abstract int getLayoutId();

    public abstract void injectFragment(AppComponent component);

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        injectFragment(App.getAppComponent());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mContainer = (ViewGroup) inflater.inflate(getLayoutId(), container, false);
        unbinder = ButterKnife.bind(this, mContainer);

        return mContainer;
    }

    @Override
    public void onViewCreated(View v, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(v, savedInstanceState);

        if (mContainer.getViewTreeObserver().isAlive()) {
            mContainer.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                @Override
                public void onGlobalLayout() {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                        mContainer.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                    } else {
                        mContainer.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                    }

                    if (getActivity() != null && getPresenter() != null && getPresenter().isViewAttached()) {
                        onFragmentStarted();
                    }
                }
            });
        } else {
            if (getActivity() != null && getPresenter() != null && getPresenter().isViewAttached()) {
                onFragmentStarted();
            }
        }
    }

    @Override
    public void onResume() {
        super.onResume();

        getBaseActivity().setTitle(getPage().getTitle());

        try {
            bus.register(getPresenter());
        } catch (Exception e) {
            L.e(e);
        }
    }

    @Override
    public void onDestroyView() {
        if (unbinder != null) {
            try {
                unbinder.unbind();
            } catch (Exception e) {
                L.e(e);
            }
        }

        unbinder = null;

        try {
            bus.unregister(getPresenter());
        } catch (Exception e) {
            L.e(e);
        }

        super.onDestroyView();
    }

    protected BaseActivity getBaseActivity() {
        return (BaseActivity) getActivity();
    }

    @Override
    public boolean isTablet() {
        return getBaseActivity().isTablet();
    }

    @Override
    public void sendRequest(MRequest<Object> request) {
        getBaseActivity().sendRequest(request);
    }

    @Override
    public FragmentBuilder getPage(Page page, Object... obj) {
        return getBaseActivity().getPage(page, obj);
    }

    @Override
    public void showPage(Page page, Object... obj) {
        getBaseActivity().showPage(page, obj);
    }

    @Override
    public void showPage(FragmentBuilder replacer) {
        getBaseActivity().showPage(replacer);
    }
}
