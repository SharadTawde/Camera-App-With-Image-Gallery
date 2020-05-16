package com.sharad.camera;

import android.content.Intent;
import android.hardware.Camera;
import android.media.MediaActionSound;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    MediaActionSound sound = new MediaActionSound();
    private FrameLayout frameLayout;
    private Camera mCamera;
    Camera.PictureCallback mPictureCallback = new Camera.PictureCallback() {
        @Override
        public void onPictureTaken(byte[] data, Camera camera) {
            File picture_file = getOutputMediaFile();
            try {
                FileOutputStream fos = new FileOutputStream(picture_file);
                sound.play(MediaActionSound.SHUTTER_CLICK);
                fos.write(data);
                fos.close();
                mCamera.startPreview();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    };
    private ImageView flashBtn;
    private CameraPreview cameraPreview;
    private int currentCameraId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);
        frameLayout = findViewById(R.id.frame_layout);
        flashBtn = findViewById(R.id.flash);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mCamera = getCameraInstance(currentCameraId);
        cameraPreview = new CameraPreview(this, mCamera);
        frameLayout.addView(cameraPreview);
    }

    private Camera getCameraInstance(int currentCameraId) {
        Camera camera = null;
        try {
            camera = Camera.open(currentCameraId);
        } catch (Exception e) {
        }
        return camera;
    }

    public void rotateCamera(View view) {
        mCamera.stopPreview();
        cameraPreview.getHolder().removeCallback(cameraPreview);
        mCamera.release();

        if (currentCameraId == Camera.CameraInfo.CAMERA_FACING_BACK)
            currentCameraId = Camera.CameraInfo.CAMERA_FACING_FRONT;
        else
            currentCameraId = Camera.CameraInfo.CAMERA_FACING_BACK;

        mCamera = getCameraInstance(currentCameraId);
        cameraPreview = new CameraPreview(MainActivity.this, mCamera);
        frameLayout.removeAllViews();
        frameLayout.addView(cameraPreview);
    }

    private File getOutputMediaFile() {
        String state = Environment.getExternalStorageState();
        if (!state.equals(Environment.MEDIA_MOUNTED))
            return null;
        else {
            File folder_ui = new File(Environment.getExternalStorageDirectory() + File.separator + "Camera App");

            if (!folder_ui.exists()) {
                folder_ui.mkdirs();
            }

            File outputFile = new File(folder_ui, System.currentTimeMillis() + "_image.jpg");
            return outputFile;
        }
    }

    public void captureImage(View view) {
        if (mCamera != null) {
            mCamera.takePicture(null, null, mPictureCallback);
        }
    }

    public void isFlashOn(View view) {

    }

    public void openGallery(View view) {
        startActivity(new Intent(this, GalleryActivity.class));
    }
}