package com.ashtiv.myshows;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class ShowAdapter extends RecyclerView.Adapter<ShowAdapter.ShowViewHolder> {
    private ArrayList<showInfo> showInfoArrayList;
    private Context mcontext;

    public ShowAdapter(ArrayList<showInfo> showInfoArrayList, Context mcontext) {
        this.showInfoArrayList = showInfoArrayList;
        this.mcontext = mcontext;
    }
    @NonNull
    @Override
    public ShowViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // inflating our layout for item of recycler view item.
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.book_rv_item, parent, false);
        return new ShowViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ShowViewHolder holder, int position) {
        showInfo showInfo = showInfoArrayList.get(position);
        holder.nameTV.setText(showInfo.getTitle());
        holder.publisherTV.setText(showInfo.getShowtype());
        holder.runtimeTV.setText("Runtime : " + showInfo.getRuntime()+" minutes");
        holder.dateTV.setText(showInfo.getPremierDate());
        Picasso.get().load(showInfo.getThumbnail()).into(holder.bookIV);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(mcontext, ShowDetails.class);
                i.putExtra("title", showInfo.getTitle());
                i.putExtra("genres", showInfo.getAuthors());
                i.putExtra("language", showInfo.getLang());
                i.putExtra("showtype", showInfo.getShowtype());
                i.putExtra("Date", showInfo.getPremierDate());
                i.putExtra("description", showInfo.getDescription());
                i.putExtra("runtime", showInfo.getRuntime());
                i.putExtra("thumbnail", showInfo.getThumbnail());
                i.putExtra("previewLink", showInfo.getPreviewLink());
                i.putExtra("infoLink", showInfo.getInfoLink());
                i.putExtra("buyLink", showInfo.getBuyLink());

                // after passing that data we are
                // starting our new  intent.
                mcontext.startActivity(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return showInfoArrayList.size();
    }

    public class ShowViewHolder extends RecyclerView.ViewHolder {
        // below line is use to initialize
        // our text view and image views.
        TextView nameTV, publisherTV, runtimeTV, dateTV;
        ImageView bookIV;

        public ShowViewHolder(View itemView) {
            super(itemView);
            nameTV = itemView.findViewById(R.id.idTVBookTitle);
            publisherTV = itemView.findViewById(R.id.idTVpublisher);
            runtimeTV = itemView.findViewById(R.id.idTVPageCount);
            dateTV = itemView.findViewById(R.id.idTVDate);
            bookIV = itemView.findViewById(R.id.idIVbook);
        }
    }
}
