package com.example.meeting.daggerdemo.presenter;

import com.example.meeting.daggerdemo.contract.MainContract;

import javax.inject.Inject;

/**
 * P
 * Created by lihq on 2017/9/4.
 */

public class MainPresenter extends MainContract.Presenter {
    @Inject
    public MainPresenter(MainContract.View view, MainContract.Model model) {
        super(view, model);
    }

    @Override
    public void loadData() {
        mView.updateUI(mModel.getData());
    }

}
