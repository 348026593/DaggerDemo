package com.example.meeting.daggerdemo.iml;

import com.example.meeting.daggerdemo.activities.MainActivity;
import com.example.meeting.daggerdemo.modules.MainModule;

import dagger.Component;

/**
 * Component
 * Created by lihq on 2017/9/4.
 */

@Component(modules = MainModule.class)
public interface MainComponent {
    void inject(MainActivity activity);
}