package com.example.meeting.daggerdemo.contract;

import com.example.meeting.daggerdemo.base.BaseModel;
import com.example.meeting.daggerdemo.base.BasePresenter;
import com.example.meeting.daggerdemo.base.BaseView;

/**
 * 协议
 * Created by lihq on 2017/9/4.
 */

public interface MainContract {
    /**
     * M
     */
    interface Model extends BaseModel {
        public String getData();
    }

    /**
     * V
     */
    interface View extends BaseView {
        void updateUI(String string);
    }

    /**
     * P
     */
    abstract class Presenter extends BasePresenter<View, Model> {

        public Presenter(View view, Model model) {
            super(view, model);
        }

        public abstract void loadData();

    }
}
