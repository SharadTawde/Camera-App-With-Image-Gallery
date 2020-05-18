package com.sharad.camera.Activity;

import android.os.Bundle;
import android.os.Environment;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.sharad.camera.Utils.Permission;
import com.sharad.camera.R;
import com.sharad.camera.Adapter.RecyclerViewAdapter;

import java.io.File;
import java.util.ArrayList;

public class GalleryActivity extends AppCompatActivity {
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery);
        recyclerView = findViewById(R.id.recyclerView);
        Permission.checkAndRequestPermissions(this);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 4));
        RecyclerViewAdapter recyclerViewAdapter = new RecyclerViewAdapter(this.getListFiles
                (new File(Environment.getExternalStorageDirectory().toString() + "/Camera App")), this);
        recyclerView.setAdapter(recyclerViewAdapter);
    }

    private ArrayList<File> getListFiles(File parentDir) {
        ArrayList<File> inFiles = new ArrayList<File>();
        File[] files;
        files = parentDir.listFiles();
        if (files != null) {
            for (File file : files) {
                if (!inFiles.contains(file))
                    inFiles.add(file);
            }
        }
        return inFiles;
    }

}
