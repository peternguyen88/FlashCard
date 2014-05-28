package com.peter.flashcard;

import android.app.Application;

import com.cengalabs.flatui.FlatUI;
import com.google.common.eventbus.EventBus;

/**
 * Created by Peter on 5/19/2014.
 */
public class AWLApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        FlatUI.initDefaultValues(this);
    }

    public static final EventBus eventBus = new EventBus();
}
