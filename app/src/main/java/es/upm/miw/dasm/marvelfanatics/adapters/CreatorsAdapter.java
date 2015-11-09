package es.upm.miw.dasm.marvelfanatics.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import es.upm.miw.dasm.marvelfanatics.R;
import es.upm.miw.dasm.marvelfanatics.RefreshableListviewInActivity;
import es.upm.miw.dasm.marvelfanatics.api.managers.marvel.ComicManager;
import es.upm.miw.dasm.marvelfanatics.api.models.marvel.Creator;
import es.upm.miw.dasm.marvelfanatics.api.models.marvel.MarvelWrapper;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

public class CreatorsAdapter extends BaseAdapter {

    private List<Creator> creators;
    private int comicId;
    private Context context;
    private RefreshableListviewInActivity refreshableListviewInActivity;

    public CreatorsAdapter(Context context, int comicId, RefreshableListviewInActivity refreshableListviewInActivity) {
        this.context = context;
        this.comicId = comicId;
        this.refreshableListviewInActivity = refreshableListviewInActivity;
        this.creators = new ArrayList<>();
        this.initializeCreatorsList();
    }

    private void initializeCreatorsList() {
        ComicManager.getInstance(context)
                .getComicCreators(comicId, 20, 0)
                .enqueue(new Callback<MarvelWrapper<Creator>>() {
                    @Override
                    public void onResponse(Response<MarvelWrapper<Creator>> response, Retrofit retrofit) {
                        Log.i("REQUEST", response.headers().toString());

                        MarvelWrapper<Creator> marvelWrapper = response.body();
                        creators = marvelWrapper.getData().getResults();

                        CreatorsAdapter.this.notifyDataSetChanged();
                        refreshableListviewInActivity.refreshList();
                        Log.i("CREATOR_LIST_UPDATE", creators.toString());
                    }

                    @Override
                    public void onFailure(Throwable t) {
                        Log.e("ERROR ON REQUEST", t.getMessage());
                    }
                });
    }

    @Override
    public int getCount() {
        return creators.size();
    }

    @Override
    public Object getItem(int position) {
        return creators.get(position);
    }

    @Override
    public long getItemId(int position) {
        return creators.get(position).getId();
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater inflater =
                    (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.creator_element, null);
        }

        Creator creator = creators.get(position);

        if (creator == null) {
            return convertView; // guard clause
        }

        TextView creatorName = (TextView) convertView.findViewById(R.id.creatorName);
        creatorName.setText(creator.getLastName() + ", " + creator.getFirstName());

        return convertView;
    }
}
