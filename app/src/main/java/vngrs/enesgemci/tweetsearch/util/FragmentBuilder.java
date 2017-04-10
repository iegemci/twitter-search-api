package vngrs.enesgemci.tweetsearch.util;

import android.support.v4.app.FragmentManager;

import java.io.Serializable;

import vngrs.enesgemci.tweetsearch.base.BaseFragment;

/**
 * Created by emesgemci on 09/09/15.
 */
public final class FragmentBuilder implements Serializable {

    private transient BaseFragment mFragment;
    private FragmentTransactionType mTransactionType = FragmentTransactionType.REPLACE;
    private int mContainerId = -1;
    private boolean mAddToBackStack = false;
    private TransactionAnimation transactionAnimation = TransactionAnimation.ENTER_FROM_RIGHT;
    private PageType mPageType = PageType.NORMAL;
    private transient FragmentManager fragmentManager;
    private boolean mClearBackStack = false;
    private boolean mUseDialogContainer = true;

    public FragmentBuilder() {
    }

    public FragmentBuilder setFragment(BaseFragment fragment) {
        this.mFragment = fragment;
        mPageType = PageType.NORMAL;
        return this;
    }

    public FragmentBuilder setAddToBackStack(boolean addToBackStack) {
        this.mAddToBackStack = addToBackStack;
        return this;
    }

    public FragmentBuilder setTransactionType(FragmentTransactionType transactionType) {
        this.mTransactionType = transactionType;
        return this;
    }

    public FragmentBuilder setContainerId(int mContainerId) {
        this.mContainerId = mContainerId;
        return this;
    }

    public boolean isSettedContainer() {
        return mContainerId == -1 ? false : true;
    }

    public boolean isSettedFragment() {
        return mFragment == null ? false : true;
    }

    public BaseFragment getFragment() {
        return mFragment;
    }

    public int getContainerId() {
        return mContainerId;
    }

    public FragmentTransactionType getTransactionType() {
        return mTransactionType;
    }

    public boolean isAddToBackStack() {
        return mAddToBackStack;
    }

    public TransactionAnimation getTransactionAnimation() {
        return transactionAnimation;
    }

    public FragmentBuilder setTransactionAnimation(TransactionAnimation transactionAnimation) {
        this.transactionAnimation = transactionAnimation;
        return this;
    }

    public PageType getPageType() {
        return mPageType;
    }

    public boolean isDialog() {
        return mPageType == PageType.DIALOG;
    }

    public FragmentManager getFragmentManager() {
        return fragmentManager;
    }

    public FragmentBuilder setFragmentManager(FragmentManager fragmentManager) {
        this.fragmentManager = fragmentManager;
        return this;
    }

    public boolean isClearBackStack() {
        return mClearBackStack;
    }

    public FragmentBuilder setClearBackStack(boolean mClearBackStack) {
        this.mClearBackStack = mClearBackStack;
        return this;
    }

    public boolean isUseDialogContainer() {
        return mUseDialogContainer;
    }

    public void setUseDialogContainer(boolean mUseDialogContainer) {
        this.mUseDialogContainer = mUseDialogContainer;
    }
}