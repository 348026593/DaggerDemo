package com.example.meeting.daggerdemo.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.example.meeting.daggerdemo.R;
import com.example.meeting.daggerdemo.contract.MainContract;
import com.example.meeting.daggerdemo.iml.DaggerMainComponent;
import com.example.meeting.daggerdemo.modules.MainModule;
import com.example.meeting.daggerdemo.presenter.MainPresenter;
import com.example.meeting.daggerdemo.repertory.MainModel;

import javax.inject.Inject;

public class MainActivity extends AppCompatActivity implements MainContract.View{

    @Inject
    MainPresenter mMainPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        mMainPresenter = new MainPresenter(this,new MainModel());

        DaggerMainComponent.builder()
                .mainModule(new MainModule(this, new MainModel()))
                .build()
                .inject(this);
        mMainPresenter.loadData();
    }

    @Override
    public void updateUI(String string) {
        Toast.makeText(this, string, Toast.LENGTH_SHORT).show();
    }
}
