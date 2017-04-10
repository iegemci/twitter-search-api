package vngrs.enesgemci.tweetsearch.util;

import vngrs.enesgemci.tweetsearch.ui.detail.FragmentDetail;
import vngrs.enesgemci.tweetsearch.ui.master.FragmentSearch;

/**
 * Created by enesgemci on 10/09/15.
 */
public final class FragmentFactory {

    private static FragmentFactory instace;

    private FragmentFactory() {
    }

    public static FragmentFactory getInstace() {
        if (instace == null)
            instace = new FragmentFactory();

        return instace;
    }

    public FragmentBuilder getFragment(Page page, Object... obj) {
        FragmentBuilder fragmentBuilder = new FragmentBuilder();

        switch (page) {
            case DASHBOARD:
                fragmentBuilder = fragmentBuilder.setFragment(FragmentSearch.newInstance(obj))
                        .setTransactionAnimation(TransactionAnimation.NO_ANIM);
                break;
            case DETAIL:
                fragmentBuilder = fragmentBuilder.setFragment(FragmentDetail.newInstance(obj))
                        .setAddToBackStack(true);
                break;
        }

        return fragmentBuilder;
    }
}