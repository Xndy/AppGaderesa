package gaderesa.com.demoappgeogaderesav2.DBHelper.Contract;

import android.provider.BaseColumns;

/**
 * Created by Xndy on 08/01/2017.
 */

public class GadereContract {

    public static final String DB_NAME = "gadere.db";

    public static final int DATABASE_VERSION = 1;

    private static final String TEXT_TYPE = " TEXT";

    private static final String COMMA_SEP = ",";

    public static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + GadereCrd1.TABLE_NAME + " (" +
                    GadereCrd1.COLUMN_NAME_ID + " INTEGER PRIMARY KEY " + COMMA_SEP +
                    GadereCrd1.COLUMN_NAME_MANIFIEST + TEXT_TYPE + COMMA_SEP +
                    GadereCrd1.COLUMN_NAME_GEOLOCATION + TEXT_TYPE + COMMA_SEP +
                    GadereCrd1.COLUMN_NAME_ESTADO + TEXT_TYPE + " )";

    public static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + GadereCrd1.TABLE_NAME;


    public GadereContract() {

    }

    public static class GadereCrd1 implements BaseColumns {
        public static final String TABLE_NAME = "crd1";
       // public static final String COLUMN_NAME_CARDCODE = "cardcorde";
        public static final String COLUMN_NAME_ID = "ud";
        public static final String COLUMN_NAME_MANIFIEST = "docnum";
        public static final String COLUMN_NAME_GEOLOCATION = "geolocation";
        public static final String COLUMN_NAME_ESTADO = "estado";
    }

}
