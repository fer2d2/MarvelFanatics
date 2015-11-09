package es.upm.miw.dasm.marvelfanatics.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.androidannotations.annotations.AfterInject;
import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.RootContext;

import java.util.ArrayList;
import java.util.List;

import es.upm.miw.dasm.marvelfanatics.R;
import es.upm.miw.dasm.marvelfanatics.api.managers.marvel.ComicManager;
import es.upm.miw.dasm.marvelfanatics.api.models.marvel.Comic;
import es.upm.miw.dasm.marvelfanatics.api.models.marvel.MarvelWrapper;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

@EBean
public class ComicThumbnailAdapter extends BaseAdapter {

    private List<Comic> comics;

    @RootContext
    Context context;

    @AfterInject
    void initAdapter() {
        this.comics = new ArrayList<>();
        initializeComicsList();
    }

    private void initializeComicsList() {
        ComicManager.getInstance(context)
                .getComics(50, 0)
                .enqueue(new Callback<MarvelWrapper<Comic>>() {
                    @Override
                    public void onResponse(Response<MarvelWrapper<Comic>> response, Retrofit retrofit) {
                        Log.i("REQUEST", response.headers().toString());
                        MarvelWrapper<Comic> marvelWrapper = response.body();
                        List<Comic> comics = marvelWrapper.getData().getResults();

                        ComicThumbnailAdapter.this.comics = comics;
                        ComicThumbnailAdapter.this.notifyDataSetChanged();
                    }

                    @Override
                    public void onFailure(Throwable t) {
                        Log.e("ERROR ON REQUEST", t.getMessage());
                    }
                });
    }

    @Override
    public int getCount() {
        return comics.size();
    }

    @Override
    public Object getItem(int position) {
        return comics.get(position);
    }

    @Override
    public long getItemId(int position) {
        return comics.get(position).getId();
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.grid_item, parent, false);
        }

        ImageView comicThumbnailImage = (ImageView) convertView.findViewById(R.id.comicThumbnail);
        TextView comicName = (TextView) convertView.findViewById(R.id.comicName);

        Comic comic = (Comic) this.getItem(position);

        Log.i("IMG_URL", comic.getThumbnail().getFullUrl());
        Picasso.with(context)
                .load(comic.getThumbnail().getFullUrl())
                .into(comicThumbnailImage);

        comicName.setText(comic.getTitle());

        return convertView;
    }
}
