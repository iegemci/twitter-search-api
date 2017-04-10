package vngrs.enesgemci.tweetsearch.ui.master;

import android.support.v4.app.FragmentManager;

import java.util.List;

import vngrs.enesgemci.tweetsearch.base.mvp.BaseMvpView;
import vngrs.enesgemci.tweetsearch.data.model.BasicAuthKey;
import vngrs.enesgemci.tweetsearch.data.model.Status;

/**
 * Created by enesgemci on 08/04/2017.
 */

interface FragmentSearchView extends BaseMvpView {

    String getSearchText();

    BasicAuthKey getBasicAuthKey();

    String getAuthTokenKey();

    void setResult(List<Status> statusList, boolean loadMore);

    int getItemCount();

    int getContainerId();

    FragmentManager getFM();
}