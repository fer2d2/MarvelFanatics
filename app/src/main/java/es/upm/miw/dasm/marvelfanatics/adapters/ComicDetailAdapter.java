package es.upm.miw.dasm.marvelfanatics.adapters;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import es.upm.miw.dasm.marvelfanatics.R;
import es.upm.miw.dasm.marvelfanatics.api.models.marvel.Comic;

public class ComicDetailAdapter extends RecyclerView.Adapter<ComicDetailAdapter.ComicDataHolder> {
    List<Pair<String, String>> cardListContent;

    public ComicDetailAdapter(Comic comic) {
        this.cardListContent = new ArrayList<>();


        this.cardListContent.add(new Pair<>("Title", comic.getTitle()));
        this.cardListContent.add(new Pair<>("Description", comic.getDescription()));
        this.cardListContent.add(new Pair<>("Pages", Integer.toString(comic.getPageCount())));
        this.cardListContent.add(new Pair<>("Format", comic.getFormat()));

        this.addPairIfSecondNotEmpty("ISBN", comic.getIsbn());
        this.addPairIfSecondNotEmpty("UPC", comic.getUpc());
        this.addPairIfSecondNotEmpty("Diamond Code", comic.getDiamondCode());
        this.addPairIfSecondNotEmpty("EAN", comic.getEan());
        this.addPairIfSecondNotEmpty("ISSN", comic.getIssn());

        Log.i("ComicDetailAdapter", this.cardListContent.toString());
    }

    private void addPairIfSecondNotEmpty(String first, String second) {
        if (second.isEmpty()) {
            return; // guard clause
        }

        this.cardListContent.add(new Pair<>(first, second));
    }

    @Override
    public ComicDataHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.
                from(parent.getContext()).
                inflate(R.layout.comic_data_card, parent, false);

        Log.i("onCreateViewHolder", itemView.toString());
        return new ComicDataHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ComicDataHolder holder, int position) {
        Pair<String, String> currentCardContent = cardListContent.get(position);

        holder.cardTitle.setText(currentCardContent.first);
        holder.cardContent.setText(currentCardContent.second);

        Log.i("onCreateViewHolder", holder.toString());
    }

    @Override
    public int getItemCount() {
        return cardListContent.size();
    }

    public static class ComicDataHolder extends RecyclerView.ViewHolder {
        protected TextView cardTitle;
        protected TextView cardContent;

        public ComicDataHolder(View itemView) {
            super(itemView);
            this.cardTitle = (TextView) itemView.findViewById(R.id.cardTitle);
            this.cardContent = (TextView) itemView.findViewById(R.id.cardContent);
        }
    }
}
