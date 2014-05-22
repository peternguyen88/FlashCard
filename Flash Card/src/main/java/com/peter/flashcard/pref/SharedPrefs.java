package com.peter.flashcard.pref;

import org.androidannotations.annotations.sharedpreferences.DefaultBoolean;
import org.androidannotations.annotations.sharedpreferences.SharedPref;

/**
 * Created by Peter on 5/15/2014.
 */

@SharedPref(value = SharedPref.Scope.UNIQUE)
public interface SharedPrefs {

    @DefaultBoolean(true)
    boolean firstRun();
}
