package com.peter.flashcard.persistence;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.support.ConnectionSource;
import com.peter.flashcard.R;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Created by Peter on 5/15/2014.
 */
public class DatabaseHelper extends OrmLiteSqliteOpenHelper {

    private static final String DATABASE_NAME = "flashCardData.sqlite";
    // any time you make changes to your database objects, you may have to increase the database version
    private static final int DATABASE_VERSION = 1;

    private final Context context;
    private final String TAG = getClass().getSimpleName();

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION, R.raw.ormlite_config);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase database, ConnectionSource connectionSource) {
        try {
            copyDatabase(CopyMode.MODE_CREATE);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase database, ConnectionSource connectionSource, int oldVersion, int newVersion) {
        try {
            copyDatabase(CopyMode.MODE_UPGRADE);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    enum CopyMode {
        MODE_CREATE, MODE_UPGRADE
    }

    private void copyDatabase(CopyMode copyMode) throws IOException {
        InputStream myInput = context.getAssets().open(DATABASE_NAME);

        // Path to the just created empty db

        File database = context.getDatabasePath(DATABASE_NAME);
        if (database.exists()) {
            database.delete();
        }

        Log.i(TAG, "Begining copy database");

        // Open the empty db as the output stream
        OutputStream myOutput = new FileOutputStream(database);

        // transfer bytes from the inputfile to the outputfile
        byte[] buffer = new byte[1024];
        int length;
        while ((length = myInput.read(buffer)) > 0) {
            myOutput.write(buffer, 0, length);
        }

        // Close the streams
        myOutput.flush();

        myOutput.close();
        myInput.close();

        Log.i(TAG, "Finished copying db");
    }
}
