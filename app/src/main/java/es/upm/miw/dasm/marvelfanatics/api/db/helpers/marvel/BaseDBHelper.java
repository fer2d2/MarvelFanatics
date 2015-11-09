package es.upm.miw.dasm.marvelfanatics.api.db.helpers.marvel;

import android.content.Context;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;

public abstract class BaseDBHelper extends OrmLiteSqliteOpenHelper {
    public static final String DATABASE_NAME = "marvelmaniacs.db";
    public static final int DATABASE_VERSION = 1;

    public BaseDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
}
