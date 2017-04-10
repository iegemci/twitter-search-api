package vngrs.enesgemci.tweetsearch.base.mvp;

import com.hannesdorfmann.mosby.mvp.MvpView;

import vngrs.enesgemci.tweetsearch.network.MRequest;
import vngrs.enesgemci.tweetsearch.util.FragmentBuilder;
import vngrs.enesgemci.tweetsearch.util.Page;

/**
 * Created by enesgemci on 08/04/2017.
 */

public interface BaseMvpView extends MvpView {

    boolean isTablet();

    void sendRequest(MRequest<Object> request);

    FragmentBuilder getPage(Page page, Object... obj);

    void showPage(Page page, Object... obj);

    void showPage(FragmentBuilder replacer);
}
