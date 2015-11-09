package es.upm.miw.dasm.marvelfanatics.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.j256.ormlite.dao.Dao;

import org.androidannotations.annotations.AfterInject;
import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.RootContext;

import java.sql.SQLException;
import java.util.List;

import es.upm.miw.dasm.marvelfanatics.ComicDetailActivity_;
import es.upm.miw.dasm.marvelfanatics.R;
import es.upm.miw.dasm.marvelfanatics.api.db.helpers.marvel.MarvelDBHelper;
import es.upm.miw.dasm.marvelfanatics.api.models.marvel.Comic;

@EBean
public class SavedComicsAdapter extends RecyclerView.Adapter<SavedComicsAdapter.ComicDataHolder> {

    private List<Comic> savedComics;
    private Dao<Comic, Integer> comicDao;

    @RootContext
    Context context;

    @AfterInject
    void initAdapter() {
        this.initializeComicDao();
        this.populateSavedComicsList();
    }

    private void initializeComicDao() {
        MarvelDBHelper marvelDBHelper = new MarvelDBHelper(context);

        try {
            comicDao = marvelDBHelper.getComicDao();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void populateSavedComicsList() {
        try {
            this.savedComics = comicDao.query(
                    comicDao.queryBuilder().where()
                            .eq("read", true)
                            .or()
                            .eq("favourite", true)
                            .prepare()
            );
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public ComicDataHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.
                from(parent.getContext()).
                inflate(R.layout.saved_comic_card, parent, false);

        Log.i("onCreateViewHolder", itemView.toString());
        return new ComicDataHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ComicDataHolder holder, int position) {
        Comic selectedComic = savedComics.get(position);
        holder.savedComicCardTitle.setText(selectedComic.getTitle());

        setIconsVisibility(holder, selectedComic);

        final int listenerPosition = position;
        holder.savedComicData.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Comic selectedComic = savedComics.get(listenerPosition);

                        Intent comicDetailIntent = new Intent(context, ComicDetailActivity_.class);
                        comicDetailIntent.putExtra("COMIC_DETAIL", selectedComic);
                        context.startActivity(comicDetailIntent);
                    }
                });
    }

    @Override
    public int getItemCount() {
        return savedComics.size();
    }

    public static class ComicDataHolder extends RecyclerView.ViewHolder {
        protected TextView savedComicCardTitle;
        protected CardView savedComicData;

        protected ImageView listButtonFavourite;
        protected ImageView listButtonUnfavourite;
        protected ImageView listButtonRead;
        protected ImageView listButtonUnread;

        public ComicDataHolder(View itemView) {
            super(itemView);
            this.savedComicCardTitle = (TextView) itemView.findViewById(R.id.savedComicCardTitle);
            this.savedComicData = (CardView) itemView.findViewById(R.id.savedComicData);

            this.listButtonFavourite = (ImageView) itemView.findViewById(R.id.listButtonFavourite);
            this.listButtonUnfavourite = (ImageView) itemView.findViewById(R.id.listButtonUnfavourite);
            this.listButtonRead = (ImageView) itemView.findViewById(R.id.listButtonRead);
            this.listButtonUnread = (ImageView) itemView.findViewById(R.id.listButtonUnread);
        }
    }

    private void setIconsVisibility(ComicDataHolder holder, Comic comic) {
        if (comic.isFavourite()) {
            holder.listButtonFavourite.setVisibility(View.VISIBLE);
            holder.listButtonUnfavourite.setVisibility(View.GONE);
        } else {
            holder.listButtonFavourite.setVisibility(View.GONE);
            holder.listButtonUnfavourite.setVisibility(View.VISIBLE);
        }

        if (comic.isRead()) {
            holder.listButtonRead.setVisibility(View.VISIBLE);
            holder.listButtonUnread.setVisibility(View.GONE);
        } else {
            holder.listButtonRead.setVisibility(View.GONE);
            holder.listButtonUnread.setVisibility(View.VISIBLE);
        }
    }
}
