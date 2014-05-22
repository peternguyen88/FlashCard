package com.peter.flashcard.persistence;

import com.j256.ormlite.android.apptools.OrmLiteConfigUtil;

/**
 * Created by Peter on 5/15/2014.
 */
public class DatabaseConfigUtil extends OrmLiteConfigUtil {
    public static void main(String[] args) throws Exception {
        writeConfigFile("ormlite_config.txt");
    }
}
