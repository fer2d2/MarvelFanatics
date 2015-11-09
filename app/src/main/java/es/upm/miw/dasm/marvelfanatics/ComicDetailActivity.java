package es.upm.miw.dasm.marvelfanatics;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;
import com.j256.ormlite.dao.Dao;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.Fullscreen;
import org.androidannotations.annotations.ViewById;

import java.sql.SQLException;

import es.upm.miw.dasm.marvelfanatics.adapters.ComicDetailAdapter;
import es.upm.miw.dasm.marvelfanatics.api.db.helpers.marvel.MarvelDBHelper;
import es.upm.miw.dasm.marvelfanatics.api.models.marvel.Comic;
import es.upm.miw.dasm.marvelfanatics.api.models.marvel.Thumbnail;
import es.upm.miw.dasm.marvelfanatics.api.models.twitter.TweetAppData;
import es.upm.miw.dasm.marvelfanatics.social.twitter.TweetActivity_;


@EActivity(R.layout.activity_comic_detail)
@Fullscreen
public class ComicDetailActivity extends AppCompatActivity {

    @ViewById(R.id.comicCoverPage)
    AppBarLayout comicCoverPageBar;

    @ViewById(R.id.comicDataList)
    RecyclerView comicDataRecyclerView;

    @ViewById(R.id.comicNameToolbar)
    CollapsingToolbarLayout comicNameToolbarLayout;

    @ViewById(R.id.expandableButtonMenu)
    FloatingActionMenu expandableButtonMenu;

    @ViewById(R.id.actionMarkAsRead)
    FloatingActionButton actionMarkAsRead;
    @ViewById(R.id.actionDeleteRead)
    FloatingActionButton actionDeleteRead;

    @ViewById(R.id.actionMarkAsFavourite)
    FloatingActionButton actionMarkAsFavourite;

    @ViewById(R.id.actionDeleteFavourite)
    FloatingActionButton actionDeleteFavourite;

    // Indicators
    @ViewById(R.id.indicatorFavourite)
    ImageView indicatorFavourite;

    @ViewById(R.id.indicatorUnfavourite)
    ImageView indicatorUnfavourite;

    @ViewById(R.id.indicatorRead)
    ImageView indicatorRead;

    @ViewById(R.id.indicatorUnread)
    ImageView indicatorUnread;

    private Comic comic;

    private Dao<Comic, Integer> comicDao;
    private Dao<Thumbnail, Integer> thumbnailDao;

    @AfterViews
    void init() {
        comic = getIntent().getParcelableExtra(getString(R.string.comic_detail_extra));

        Log.i("COMIC_DETAIL", comic.toString());

        initializeComicDao();
        initializeComicDataRecyclerView();

        addBackgroundImage(comic);
        addToolbarTitle(comic);

        initilizeComicMenu(comic);
    }

    private void initializeComicDao() {
        MarvelDBHelper marvelDBHelper = new MarvelDBHelper(this);

        try {
            comicDao = marvelDBHelper.getComicDao();
            thumbnailDao = marvelDBHelper.getThumbnailDao();
            Thumbnail thumbnail = comic.getThumbnail(); // cascade creation

            if (comicDao.idExists(comic.getId())) {
                Log.i("COMIC_FOUND", String.valueOf(comic.getId()));

                comic = comicDao.queryForId(comic.getId());
                comic.setThumbnail(thumbnail);
                updateComicCascade(comic);

                Log.i("COMIC_FOUND_CONTENT", comic.toString());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void updateComicCascade(Comic comic) {
        try {
            thumbnailDao.createOrUpdate(comic.getThumbnail());
            comicDao.createOrUpdate(comic);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void initializeComicDataRecyclerView() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        this.comicDataRecyclerView.setLayoutManager(linearLayoutManager);
        this.comicDataRecyclerView.setAdapter(new ComicDetailAdapter(comic));
    }

    private void addBackgroundImage(Comic comic) {
        Target target = generatePicassoTarget();
        Picasso.with(this)
                .load(comic.getThumbnail().getFullUrl())
                .into(target);
    }

    @NonNull
    private Target generatePicassoTarget() {
        return new Target() {
            @Override
            public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                BitmapDrawable bg = new BitmapDrawable(ComicDetailActivity.this.getResources(), bitmap);
                bg.setGravity(Gravity.FILL_HORIZONTAL | Gravity.CENTER_VERTICAL);

                int sdk = android.os.Build.VERSION.SDK_INT;
                if (sdk < android.os.Build.VERSION_CODES.JELLY_BEAN) {
                    ComicDetailActivity.this.comicCoverPageBar.setBackgroundDrawable(bg);
                } else {
                    ComicDetailActivity.this.comicCoverPageBar.setBackground(bg);
                }
            }

            @Override
            public void onBitmapFailed(Drawable errorDrawable) {

            }

            @Override
            public void onPrepareLoad(Drawable placeHolderDrawable) {

            }
        };
    }

    private void addToolbarTitle(Comic comic) {
        comicNameToolbarLayout.setTitle(comic.getTitle());
    }

    private void initilizeComicMenu(Comic comic) {
        Log.i("COMIC_MENU_INITIAL", comic.toString());

        setVisibilityForComicFavourite(comic);
        setVisibilityForComicRead(comic);
    }

    private void setVisibilityForComicFavourite(Comic comic) {
        if (comic.isFavourite()) {
            this.actionMarkAsFavourite.setVisibility(View.GONE);
            this.actionDeleteFavourite.setVisibility(View.VISIBLE);

            this.indicatorUnfavourite.setVisibility(View.GONE);
            this.indicatorFavourite.setVisibility(View.VISIBLE);
        } else {
            this.actionMarkAsFavourite.setVisibility(View.VISIBLE);
            this.actionDeleteFavourite.setVisibility(View.GONE);

            this.indicatorUnfavourite.setVisibility(View.VISIBLE);
            this.indicatorFavourite.setVisibility(View.GONE);
        }
    }

    private void setVisibilityForComicRead(Comic comic) {
        if (comic.isRead()) {
            this.actionMarkAsRead.setVisibility(View.GONE);
            this.actionDeleteRead.setVisibility(View.VISIBLE);

            this.indicatorUnread.setVisibility(View.GONE);
            this.indicatorRead.setVisibility(View.VISIBLE);
        } else {
            this.actionMarkAsRead.setVisibility(View.VISIBLE);
            this.actionDeleteRead.setVisibility(View.GONE);

            this.indicatorUnread.setVisibility(View.VISIBLE);
            this.indicatorRead.setVisibility(View.GONE);
        }
    }

    //<editor-fold desc="EXPANDABLE_FAB LISTENERS>

    @Click(R.id.actionViewMoreInfo)
    void viewMoreInfo() {
        Intent viewMoreInfoIntent = new Intent(ComicDetailActivity.this, ComicMoreInfoActivity_.class);
        viewMoreInfoIntent.putExtra("COMIC_MORE_INFO", comic);
        expandableButtonMenu.toggle(true);
        startActivity(viewMoreInfoIntent);
    }

    @Click(R.id.actionMarkAsFavourite)
    void markAsFavourite() {
        if (!comic.isFavourite()) {
            comic.setFavourite(true);
        }

        updateComicCascade(comic);
        Toast.makeText(ComicDetailActivity.this, "Comic marked as favourite", Toast.LENGTH_SHORT).show();

        initilizeComicMenu(comic);
        expandableButtonMenu.toggle(true);
    }

    @Click(R.id.actionDeleteFavourite)
    void deleteFavourite() {
        if (comic.isFavourite()) {
            comic.setFavourite(false);
        }

        updateComicCascade(comic);
        Toast.makeText(ComicDetailActivity.this, "Comic deleted from favourites", Toast.LENGTH_SHORT).show();

        initilizeComicMenu(comic);
        expandableButtonMenu.toggle(true);
    }

    @Click(R.id.actionMarkAsRead)
    void markAsRead() {
        if (!comic.isRead()) {
            comic.setRead(true);
        }

        updateComicCascade(comic);
        Toast.makeText(ComicDetailActivity.this, "Comic marked as read", Toast.LENGTH_SHORT).show();

        initilizeComicMenu(comic);
        expandableButtonMenu.toggle(true);
    }

    @Click(R.id.actionDeleteRead)
    void deleteRead() {
        if (comic.isRead()) {
            comic.setRead(false);
        }

        updateComicCascade(comic);
        Toast.makeText(ComicDetailActivity.this, "Comic removed from read list", Toast.LENGTH_SHORT).show();

        initilizeComicMenu(comic);
        expandableButtonMenu.toggle(true);
    }

    @Click(R.id.actionShare)
    void share() {
        Intent tweetIntent = new Intent(ComicDetailActivity.this, TweetActivity_.class);
        TweetAppData tweetAppData = new TweetAppData(TweetAppData.DEFAULT_HASHTAG, comic.getTitle());
        tweetIntent.putExtra("COMIC_TWEET_DATA", tweetAppData);
        expandableButtonMenu.toggle(true);
        startActivity(tweetIntent);
    }

    @Click(R.id.actionViewSavedComicsListFromDetail)
    void viewSavedComicsList() {
        Intent savedComicsIntent = new Intent(ComicDetailActivity.this, SavedComicsActivity_.class);
        expandableButtonMenu.toggle(true);
        startActivity(savedComicsIntent);
    }

    //</editor-fold>
}
