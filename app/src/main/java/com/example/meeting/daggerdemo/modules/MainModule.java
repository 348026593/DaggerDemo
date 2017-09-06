package com.example.meeting.daggerdemo.modules;

import com.example.meeting.daggerdemo.contract.MainContract;

import dagger.Module;
import dagger.Provides;

/**
 * Module
 * Created by lihq on 2017/9/4.
 */
@Module
public class MainModule {

    private final MainContract.View mView;
    private final MainContract.Model mModel;

    public MainModule(MainContract.View view, MainContract.Model model) {
        mView = view;
        mModel = model;
    }

    @Provides
    MainContract.View provideMainView() {
        return mView;
    }

    @Provides
    MainContract.Model provideMainModel() {
        return mModel;
    }

}
