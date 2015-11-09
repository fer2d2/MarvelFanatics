package es.upm.miw.dasm.marvelfanatics.api.managers.marvel;

import android.content.Context;

import com.squareup.okhttp.OkHttpClient;

import es.upm.miw.dasm.marvelfanatics.api.models.marvel.Comic;
import es.upm.miw.dasm.marvelfanatics.api.models.marvel.Creator;
import es.upm.miw.dasm.marvelfanatics.api.models.marvel.MarvelWrapper;
import es.upm.miw.dasm.marvelfanatics.api.services.marvel.ComicService;
import retrofit.Call;
import retrofit.GsonConverterFactory;
import retrofit.Retrofit;

public class ComicManager extends BaseManager {
    private static ComicManager ourInstance = null;
    private ComicService apiService;
    private OkHttpClient client;

    public static ComicManager getInstance(Context context) {
        if (ourInstance == null) {
            ourInstance = new ComicManager(context);
        }
        return ourInstance;
    }

    private ComicManager(Context context) {
        super(context);

        this.retrofit = new Retrofit.Builder()
                .baseUrl(this.API_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        this.apiService = retrofit.create(ComicService.class);
    }

    public Call<MarvelWrapper<Comic>> getComics(int limit, int offset) {
        return apiService.getComics(limit, offset, this.timestamp, this.PUBLIC_KEY, this.hash,
                this.DEFAULT_DATE_DESCRIPTOR, this.DEFAULT_ORDERBY);
    }

    public Call<MarvelWrapper<Creator>> getComicCreators(int comicId, int limit, int offset) {
        return apiService.getComicCreators(comicId, limit, offset, this.timestamp, this.PUBLIC_KEY,
                this.hash);
    }
}
