package com.sharad.camera;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.io.File;
import java.util.ArrayList;

public class RecyclerViewMediaAdapter extends RecyclerView.Adapter<RecyclerViewMediaAdapter.FileHolder> {
    private ArrayList<File> filesList;
    private Activity activity;

    public RecyclerViewMediaAdapter(ArrayList<File> filesList, Activity activity) {
        this.filesList = filesList;
        this.activity = activity;
        setHasStableIds(true);
    }

    @Override
    public RecyclerViewMediaAdapter.FileHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View inflatedView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.gallery_image_card, parent, false);
        return new FileHolder(inflatedView);
    }

    @Override
    public void onBindViewHolder(final RecyclerViewMediaAdapter.FileHolder holder, int position) {
        File currentFile = filesList.get(position);
        Glide.with(activity).load(currentFile).placeholder(R.drawable.plceholder).into(holder.imageViewImageMedia);
    }

    @Override
    public int getItemCount() {
        return filesList.size();
    }

    public class FileHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView imageViewImageMedia;

        public FileHolder(View itemView) {
            super(itemView);
            imageViewImageMedia = itemView.findViewById(R.id.image);
            imageViewImageMedia.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.image:
                    ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(activity,
                            imageViewImageMedia, "profileTransition");
                    Intent i = new Intent(activity, ImageActivity.class);
                    i.putExtra("img", filesList.get(getAdapterPosition()));
                    activity.startActivity(i, options.toBundle());
            }
        }

    }
}

