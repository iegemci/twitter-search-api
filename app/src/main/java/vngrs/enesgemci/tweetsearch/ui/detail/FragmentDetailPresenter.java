package vngrs.enesgemci.tweetsearch.ui.detail;

import vngrs.enesgemci.tweetsearch.base.mvp.BaseMvpPresenter;
import vngrs.enesgemci.tweetsearch.data.repository.Repository;

/**
 * Created by enesgemci on 08/04/2017.
 */

class FragmentDetailPresenter extends BaseMvpPresenter<FragmentDetailView> {

    public FragmentDetailPresenter(Repository repository) {
        super(repository);
    }
}
