package es.upm.miw.dasm.marvelfanatics.api.managers.marvel;

import android.content.Context;

import java.util.Calendar;
import java.util.TimeZone;

import es.upm.miw.dasm.marvelfanatics.R;
import es.upm.miw.dasm.marvelfanatics.util.HashCalculator;
import retrofit.GsonConverterFactory;
import retrofit.Retrofit;

public abstract class BaseManager {
    protected final String API_BASE_URL = "http://gateway.marvel.com:80/";
    protected Context context;
    protected String PRIVATE_KEY;
    protected String PUBLIC_KEY;
    protected String timestamp;
    protected String hash;
    protected final String DEFAULT_DATE_DESCRIPTOR = "thisMonth";
    protected final String DEFAULT_ORDERBY = "-onsaleDate";
    protected Retrofit retrofit;

    protected BaseManager(Context context) {
        this.context = context;
        this.PRIVATE_KEY = context.getString(R.string.private_key_marvel);
        this.PUBLIC_KEY = context.getString(R.string.public_key_marvel);

        Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
        this.timestamp = Long.toString(calendar.getTimeInMillis() / 1000L);

        this.hash = HashCalculator.md5(this.timestamp + this.PRIVATE_KEY + this.PUBLIC_KEY);

        this.retrofit = new Retrofit.Builder()
                .baseUrl(this.API_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }
}
