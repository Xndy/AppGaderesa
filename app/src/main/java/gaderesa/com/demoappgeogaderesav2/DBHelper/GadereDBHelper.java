package gaderesa.com.demoappgeogaderesav2.DBHelper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import gaderesa.com.demoappgeogaderesav2.DBHelper.Contract.GadereContract;

/**
 * Created by Xndy on 08/01/2017.
 */

public class GadereDBHelper extends SQLiteOpenHelper {

    public GadereDBHelper(Context context) {
        super(context, GadereContract.DB_NAME, null, GadereContract.DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(GadereContract.SQL_CREATE_ENTRIES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(GadereContract.SQL_DELETE_ENTRIES);
        onCreate(db);
    }
}
