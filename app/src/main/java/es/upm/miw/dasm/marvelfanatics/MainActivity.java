package es.upm.miw.dasm.marvelfanatics;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.GridView;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.Fullscreen;
import org.androidannotations.annotations.ItemClick;
import org.androidannotations.annotations.OptionsItem;
import org.androidannotations.annotations.OptionsMenu;
import org.androidannotations.annotations.ViewById;

import es.upm.miw.dasm.marvelfanatics.adapters.ComicThumbnailAdapter;
import es.upm.miw.dasm.marvelfanatics.api.models.marvel.Comic;
import es.upm.miw.dasm.marvelfanatics.social.twitter.AuthenticationActivity_;

@EActivity(R.layout.activity_main)
@OptionsMenu(R.menu.menu_main)
@Fullscreen
public class MainActivity extends AppCompatActivity {

    @Bean
    ComicThumbnailAdapter comicThumbnailAdapter;

    @ViewById(R.id.latestComicsGrid)
    GridView latestComicsGrid;

    @AfterViews
    void init() {
        getSupportActionBar().setTitle(R.string.latest_comics_title);
        latestComicsGrid.setAdapter(comicThumbnailAdapter);
    }

    @ItemClick(R.id.latestComicsGrid)
    void thumbnailListItemClick(Comic selectedComic) {
        Log.i("COMIC_CLIC", selectedComic.toString());

        Intent comicDetailIntent = new Intent(MainActivity.this, ComicDetailActivity_.class);
        comicDetailIntent.putExtra("COMIC_DETAIL", selectedComic);
        startActivity(comicDetailIntent);
    }

    @OptionsItem(R.id.actionViewSavedComicsList)
    void viewSavedComicsList() {
        Intent savedComicsIntent = new Intent(MainActivity.this, SavedComicsActivity_.class);
        startActivity(savedComicsIntent);
    }

    @OptionsItem(R.id.actionSocialSettings)
    void openSocialSettings() {
        Intent authenticationIntent = new Intent(MainActivity.this, AuthenticationActivity_.class);
        startActivity(authenticationIntent);
    }
}
