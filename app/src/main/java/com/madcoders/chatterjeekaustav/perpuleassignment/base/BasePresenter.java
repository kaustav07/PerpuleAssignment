package com.madcoders.chatterjeekaustav.perpuleassignment.base;

/**
 * Created by Kaustav on 08-03-2018.
 */

public interface BasePresenter<V> {

    void attchView(V view);

    void detachView();

    void viewPrepared();

}
