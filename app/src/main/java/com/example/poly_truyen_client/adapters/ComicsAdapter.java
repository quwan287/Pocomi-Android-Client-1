package com.example.poly_truyen_client.adapters;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.poly_truyen_client.R;
import com.example.poly_truyen_client.ViewDetailsComicActivity;
import com.example.poly_truyen_client.api.ConnectAPI;
import com.example.poly_truyen_client.api.HistoryServices;
import com.example.poly_truyen_client.models.Comic;
import com.example.poly_truyen_client.models.History;
import com.example.poly_truyen_client.models.User;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ComicsAdapter extends RecyclerView.Adapter<ComicsAdapter.ComicViewHolder> {

    private ArrayList<Comic> list = new ArrayList<>();
    private Context context;
    private HistoryServices historyServices;
    private SharedPreferences sharedPreferences;

    public ComicsAdapter(ArrayList<Comic> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @SuppressLint("NotifyDataSetChanged")
    public void updateList(ArrayList<Comic> listUpdate) {
        list.clear();
        list.addAll(listUpdate);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ComicViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ComicViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.comic_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ComicViewHolder holder, int position) {
        historyServices = new ConnectAPI().connect.create(HistoryServices.class);
        sharedPreferences = context.getSharedPreferences("poly_comic", Context.MODE_PRIVATE);

        User loggedUser = new Gson().fromJson(sharedPreferences.getString("user", ""), User.class);

        Comic comic = list.get(holder.getAdapterPosition());

        Picasso.get().load(new ConnectAPI().API_URL + "images/" + comic.getPoster()).into(holder.ivPoster);
        holder.tvName.setText(comic.getName());

        holder.ivPoster.setOnClickListener(v -> {

            Call<History> saveCache = historyServices.storeHistory(loggedUser.get_id(), comic.get_id());
            saveCache.enqueue(new Callback<History>() {
                @Override
                public void onResponse(Call<History> call, Response<History> response) {

                }

                @Override
                public void onFailure(Call<History> call, Throwable throwable) {

                }
            });
            context.startActivity(new Intent(context, ViewDetailsComicActivity.class).putExtra("comic", new Gson().toJson(comic)));
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ComicViewHolder extends RecyclerView.ViewHolder {

        private ImageView ivPoster;
        private TextView tvName;


        public ComicViewHolder(@NonNull View itemView) {
            super(itemView);

            ivPoster = (ImageView) itemView.findViewById(R.id.ivPoster);
            tvName = (TextView) itemView.findViewById(R.id.tvName);

        }
    }
}
