package com.example.meeting.daggerdemo.base;

/**
 * 描述:Presenter[MVP]
 * <p>
 * Created by lihq on 2017/9/4
 */

public abstract class BasePresenter<V extends BaseView,M extends BaseModel> {


    /**
     * V
     */
    protected final V mView;

    /**
     * M
     */
    protected final M mModel;

    public BasePresenter(V view, M model) {
        mView = view;
        mModel = model;
    }
}
