package com.example.meeting.daggerdemo.repertory;

import com.example.meeting.daggerdemo.contract.MainContract;

/**
 * M
 * Created by lihq on 2017/9/4.
 */

public class MainModel implements MainContract.Model {
    @Override
    public String getData() {
        return "Success";
    }
}
