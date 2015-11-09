package es.upm.miw.dasm.marvelfanatics.api.db.helpers.marvel;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.sql.SQLException;

import es.upm.miw.dasm.marvelfanatics.api.models.marvel.Comic;
import es.upm.miw.dasm.marvelfanatics.api.models.marvel.Thumbnail;


public class MarvelDBHelper extends BaseDBHelper {

    private Dao<Comic, Integer> comicDao;
    private Dao<Thumbnail, Integer> thumbnailDao;

    public MarvelDBHelper(Context context) {
        super(context);
    }

    @Override
    public void onCreate(SQLiteDatabase database, ConnectionSource connectionSource) {
        try {
            TableUtils.createTable(connectionSource, Comic.class);
            TableUtils.createTable(connectionSource, Thumbnail.class);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase database, ConnectionSource connectionSource, int oldVersion, int newVersion) {
        onCreate(database, connectionSource);
    }

    public Dao<Comic, Integer> getComicDao() throws SQLException {
        if (comicDao == null) {
            comicDao = getDao(Comic.class);
        }
        return comicDao;
    }

    public Dao<Thumbnail, Integer> getThumbnailDao() throws SQLException {
        if (thumbnailDao == null) {
            thumbnailDao = getDao(Thumbnail.class);
        }
        return thumbnailDao;
    }

    @Override
    public void close() {
        super.close();
        comicDao = null;
        thumbnailDao = null;
    }
}
