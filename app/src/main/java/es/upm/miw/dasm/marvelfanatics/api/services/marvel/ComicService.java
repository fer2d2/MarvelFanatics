package es.upm.miw.dasm.marvelfanatics.api.services.marvel;

import es.upm.miw.dasm.marvelfanatics.api.models.marvel.Comic;
import es.upm.miw.dasm.marvelfanatics.api.models.marvel.Creator;
import es.upm.miw.dasm.marvelfanatics.api.models.marvel.MarvelWrapper;
import retrofit.Call;
import retrofit.http.GET;
import retrofit.http.Headers;
import retrofit.http.Path;
import retrofit.http.Query;

public interface ComicService {

    @Headers("Cache-Control: public, max-age=640000, s-maxage=640000 , max-stale=2419200")
    @GET("/v1/public/comics")
    Call<MarvelWrapper<Comic>> getComics(@Query("limit") int limit
            , @Query("offset") int offset
            , @Query("ts") String timestamp
            , @Query("apikey") String apikey
            , @Query("hash") String hash
            , @Query("dateDescriptor") String dateDescriptor
            , @Query("orderBy") String orderBy
    );

    @Headers("Cache-Control: public, max-age=640000, s-maxage=640000 , max-stale=2419200")
    @GET("/v1/public/comics/{comicId}")
    Call<MarvelWrapper<Comic>> getComic(@Path("comicId") int comicId
            , @Query("limit") int limit
            , @Query("offset") int offset
            , @Query("ts") String timestamp
            , @Query("apikey") String apikey
            , @Query("hash") String hash
            , @Query("dateDescriptor") String dateDescriptor
            , @Query("orderBy") String orderBy
    );

    @Headers("Cache-Control: public, max-age=640000, s-maxage=640000 , max-stale=2419200")
    @GET("/v1/public/comics/{comicId}/creators")
    Call<MarvelWrapper<Creator>> getComicCreators(@Path("comicId") int comicId
            , @Query("limit") int limit
            , @Query("offset") int offset
            , @Query("ts") String timestamp
            , @Query("apikey") String apikey
            , @Query("hash") String hash
    );
}
