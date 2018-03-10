package com.madcoders.chatterjeekaustav.perpuleassignment.base;

import com.madcoders.chatterjeekaustav.perpuleassignment.data.DataHelper;

/**
 * Created by Kaustav on 09-03-2018.
 */

public abstract class BasePresenterImpl<V extends BaseView> implements BasePresenter<V> {

    private V mvpView;
    private DataHelper mDataHelper;

    public BasePresenterImpl(DataHelper dataHelper){
        mDataHelper = dataHelper;
    }

    @Override
    public void attchView(V view) {
        mvpView = view;
    }

    @Override
    public void detachView() {
        mvpView = null;
    }

    public V getMvpView() {
        return mvpView;
    }

    public DataHelper getDataManager() {
        return mDataHelper;
    }
}
