package es.upm.miw.dasm.marvelfanatics;

import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.Fullscreen;
import org.androidannotations.annotations.ViewById;

import es.upm.miw.dasm.marvelfanatics.adapters.CreatorsAdapter;
import es.upm.miw.dasm.marvelfanatics.api.models.marvel.Comic;
import es.upm.miw.dasm.marvelfanatics.util.ListviewUtils;

@EActivity(R.layout.activity_comic_more_info)
@Fullscreen
public class ComicMoreInfoActivity extends AppCompatActivity implements RefreshableListviewInActivity {

    @ViewById(R.id.creatorsList)
    ListView creatorsList;

    Comic comic;

    @AfterViews
    void init() {
        comic = getIntent().getParcelableExtra("COMIC_MORE_INFO");
        getSupportActionBar().setTitle(comic.getTitle());
        initializeCreatorsList();
    }

    private void initializeCreatorsList() {
        creatorsList.setAdapter(new CreatorsAdapter(this, comic.getId(), this));
        refreshList();
    }

    @Override
    public void refreshList() {
        ListviewUtils.setDynamicHeight(creatorsList);
    }
}
