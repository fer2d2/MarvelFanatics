package es.upm.miw.dasm.marvelfanatics;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.Fullscreen;
import org.androidannotations.annotations.ViewById;

import es.upm.miw.dasm.marvelfanatics.adapters.SavedComicsAdapter;

@EActivity(R.layout.activity_saved_comics)
@Fullscreen
public class SavedComicsActivity extends AppCompatActivity {

    @ViewById(R.id.savedComicsList)
    RecyclerView savedComicsListRecyclerView;

    @Bean
    SavedComicsAdapter savedComicsAdapter;

    @AfterViews
    void init() {
        getSupportActionBar().setTitle(R.string.saved_comics_title);

        initializeComicDataRecyclerView();
    }

    private void initializeComicDataRecyclerView() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        this.savedComicsListRecyclerView.setLayoutManager(linearLayoutManager);
        this.savedComicsListRecyclerView.setAdapter(savedComicsAdapter);
    }
}
