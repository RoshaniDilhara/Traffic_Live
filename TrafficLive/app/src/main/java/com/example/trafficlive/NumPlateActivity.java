package com.example.trafficlive;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.SparseArray;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.android.gms.vision.CameraSource;
import com.google.android.gms.vision.Detector;
import com.google.android.gms.vision.text.TextBlock;
import com.google.android.gms.vision.text.TextRecognizer;

import java.io.IOException;

public class NumPlateActivity extends AppCompatActivity {


    SurfaceView cameraView;
    TextView textView;
    CameraSource cameraSource;
    final int RequestCameraPermissionId=1001;
    private String qr_code;





    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode){
            case RequestCameraPermissionId:
            {
                if(grantResults[0]==PackageManager.PERMISSION_GRANTED){
                    if(ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA)!= PackageManager.PERMISSION_GRANTED){
                        return;
                    }
                    try {
                        cameraSource.start(cameraView.getHolder());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

        }
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_num_plate);

        //----------------------------------------------------
        Bundle bundle = getIntent().getExtras();
        qr_code = bundle.getString("QRActivity");
        //----------------------------------------------------


        cameraView=(SurfaceView)findViewById(R.id.surface_view);
        textView=(TextView)findViewById(R.id.text_view);
        final Button btn_submittext = (Button) findViewById(R.id.btn_submittext);

        TextRecognizer textRecognizer=new TextRecognizer.Builder(getApplicationContext()).build();

        if(!textRecognizer.isOperational()){
            Log.w("NumPlateActivity","Detector  dependencies are not yet available");
        }else{
            cameraSource=new CameraSource.Builder(getApplicationContext(),textRecognizer)
                    .setFacing(CameraSource.CAMERA_FACING_BACK)
                    .setRequestedPreviewSize(1280,1024)
                    .setRequestedFps(2.0f)
                    .setAutoFocusEnabled(true)
                    .build();

            cameraView.getHolder().addCallback(new SurfaceHolder.Callback() {
                @Override
                public void surfaceCreated(SurfaceHolder holder) {
                    try{
                        if(ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.CAMERA)!= PackageManager.PERMISSION_GRANTED){
                            ActivityCompat.requestPermissions(NumPlateActivity.this,new String[]{Manifest.permission.CAMERA},
                                    RequestCameraPermissionId);
                            return;
                        }
                        cameraSource.start(cameraView.getHolder());
                    }catch(IOException e){
                        e.printStackTrace();
                    }
                }

                @Override
                public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

                }

                @Override
                public void surfaceDestroyed(SurfaceHolder holder) {
                    cameraSource.stop();
                }
            });

            textRecognizer.setProcessor(new Detector.Processor<TextBlock>() {
                @Override
                public void release() {

                }

                @Override
                public void receiveDetections(Detector.Detections<TextBlock> detections) {
                    final SparseArray<TextBlock> items=detections.getDetectedItems();
                    if(items.size() != 0){
                        textView.post(new Runnable() {
                            @Override
                            public void run() {
                                StringBuilder stringBuilder=new StringBuilder();

                                for(int i=0;i<items.size();++i){
                                    TextBlock item=items.valueAt(i);
                                    stringBuilder.append(item.getValue());
                                    //stringBuilder.append("/n");

                                }
                                textView.setText(stringBuilder.toString());

                                btn_submittext.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        btn_submittext.setVisibility(View.GONE);
                                        RelativeLayout f2 = (RelativeLayout) findViewById(R.id.container2);
                                        f2.removeAllViews();



                                        Bundle bundle = new Bundle();
                                        bundle.putString("NumPlateActivity", (String) textView.getText());
                                        bundle.putString("QRActivity",qr_code);


                                        Intent intent81 = new Intent(NumPlateActivity.this, BtnnavActivity.class);
                                        intent81.putExtras(bundle);
                                        startActivity(intent81);
                                        finish();
// set Fragmentclass Arguments

                                    }

                                });
                            }
                        });
                    }
                }
            });
        }



    }



}
