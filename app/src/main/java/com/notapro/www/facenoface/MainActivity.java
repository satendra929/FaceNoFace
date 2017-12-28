package com.notapro.www.facenoface;

import android.content.Intent;
import android.graphics.Bitmap;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.util.SparseArray;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.google.android.gms.vision.Frame;
import com.google.android.gms.vision.MultiProcessor;
import com.google.android.gms.vision.face.Face;
import com.google.android.gms.vision.face.FaceDetector;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageView IMcam;
    private Button BTclick;
    private Bitmap bp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        IMcam=(ImageView) findViewById(R.id.IVcam);
        BTclick=(Button) findViewById(R.id.BTpic);
        BTclick.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.BTpic :
                openCamera();
                break;
        }
    }

    private void openCamera() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent,0);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_OK) {
            bp = (Bitmap) data.getExtras().get("data");
            IMcam.setImageBitmap(bp);
            FaceDetector faceDetector = new
                    FaceDetector.Builder(getApplicationContext()).setTrackingEnabled(false)
                    .build();
            if(!faceDetector.isOperational()){
                new AlertDialog.Builder(this).setMessage("Could not set up the face detector!").show();
                return;
            }
            Frame frame = new Frame.Builder().setBitmap(bp).build();
            SparseArray<Face> faces = faceDetector.detect(frame);
            if (faces.size()!=0) {
                new AlertDialog.Builder(this).setMessage("Face Detected").show();
            }
            else {
                new AlertDialog.Builder(this).setMessage("No Face").show();
            }
        }
    }
}
