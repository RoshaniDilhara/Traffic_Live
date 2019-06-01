package com.example.trafficlive;

import android.app.AlertDialog;
import android.app.FragmentTransaction;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.ml.vision.FirebaseVision;
import com.google.firebase.ml.vision.barcode.FirebaseVisionBarcode;
import com.google.firebase.ml.vision.barcode.FirebaseVisionBarcodeDetector;
import com.google.firebase.ml.vision.barcode.FirebaseVisionBarcodeDetectorOptions;
import com.google.firebase.ml.vision.common.FirebaseVisionImage;
import com.wonderkiln.camerakit.CameraKitError;
import com.wonderkiln.camerakit.CameraKitEvent;
import com.wonderkiln.camerakit.CameraKitEventListener;
import com.wonderkiln.camerakit.CameraKitImage;
import com.wonderkiln.camerakit.CameraKitVideo;
import com.wonderkiln.camerakit.CameraView;

import java.util.List;

import dmax.dialog.SpotsDialog;

public class QRActivity extends AppCompatActivity {

    CameraView cameraView;
    Button btnDetect;
    AlertDialog waitingDialog;
    private static FragmentManager fragmentManager;
    private String numPlate_code;


    protected void onResume(){
        super.onResume();
        cameraView.start();
    }


    @Override
    protected void onPause() {
        super.onPause();
        cameraView.stop();
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        //----------------------------------------------------
        Bundle bundle = getIntent().getExtras();
        numPlate_code = bundle.getString("NumPlateActivity");
        //----------------------------------------------------

        setContentView(R.layout.activity_qr);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);





        cameraView=(CameraView)findViewById(R.id.cameraview);
        btnDetect=(Button)findViewById(R.id.btn_detect);

        waitingDialog = new SpotsDialog.Builder()
                .setContext(this)
                .setMessage("Please wait")
                .setCancelable(false)
                .build();
        btnDetect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cameraView.start();
                cameraView.captureImage();
            }
        });

        cameraView.addCameraKitListener(new CameraKitEventListener() {
            @Override
            public void onEvent(CameraKitEvent cameraKitEvent) {

            }

            @Override
            public void onError(CameraKitError cameraKitError) {

            }

            @Override
            public void onImage(CameraKitImage cameraKitImage) {
                waitingDialog.show();
                Bitmap bitmap=cameraKitImage.getBitmap();
                bitmap=Bitmap.createScaledBitmap(bitmap,cameraView.getWidth(),cameraView.getHeight(),false);
                cameraView.stop();
                runDetector(bitmap);
            }

            @Override
            public void onVideo(CameraKitVideo cameraKitVideo) {

            }
        });
    }





    private void runDetector(Bitmap bitmap) {
        FirebaseVisionImage image=FirebaseVisionImage.fromBitmap(bitmap);
        FirebaseVisionBarcodeDetectorOptions options= new FirebaseVisionBarcodeDetectorOptions.Builder()
                .setBarcodeFormats(
                        FirebaseVisionBarcode.FORMAT_QR_CODE,
                        FirebaseVisionBarcode.FORMAT_PDF417,
                        FirebaseVisionBarcode.FORMAT_AZTEC
                )
                .build();
        FirebaseVisionBarcodeDetector detector= FirebaseVision.getInstance().getVisionBarcodeDetector();
        Task<List<FirebaseVisionBarcode>> result = detector.detectInImage(image)
                .addOnSuccessListener(new OnSuccessListener<List<FirebaseVisionBarcode>>() {
                    @Override
                    public void onSuccess(List<FirebaseVisionBarcode> firebaseVisionBarcodes) {
                        processResult(firebaseVisionBarcodes);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(QRActivity.this,e.getMessage(),Toast.LENGTH_SHORT).show();

                    }
                });
    }
    private void processResult(List<FirebaseVisionBarcode> firebaseVisionBarcodes) {
        for(FirebaseVisionBarcode item: firebaseVisionBarcodes)
        {
            int value_type=item.getValueType();
            switch (value_type){
                case FirebaseVisionBarcode.TYPE_TEXT:
                {


                    btnDetect.setVisibility(View.GONE);
                    RelativeLayout f1 = (RelativeLayout) findViewById(R.id.container);

                    f1.removeAllViews();




                    //SharedPreferences preferences=getSharedPreferences("PhoneBook",MODE_PRIVATE);
                    //SharedPreferences.Editor editor=preferences.edit();


                    //------------------------------------------------------
                    Bundle bundle = new Bundle();
                    bundle.putString("QRActivity", item.getRawValue());
                    bundle.putString("NumPlateActivity",numPlate_code);
                    //------------------------------------------------------



                    Intent intent80 = new Intent(QRActivity.this, BtnnavActivity.class);
                    //intent80.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                    intent80.putExtras(bundle);
                    startActivity(intent80);
                    finish();
// set Fragmentclass Arguments




                    /*
                    android.support.v7.app.AlertDialog.Builder  builder=new android.support.v7.app.AlertDialog.Builder(this);
                    builder.setMessage(item.getRawValue());
                    builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            //dialogInterface.dismiss();
                        }
                    });
                    android.support.v7.app.AlertDialog dialog=builder.create();

                    dialog.show();
                    */





                }
                break;

                case FirebaseVisionBarcode.TYPE_URL:
                {
                    //start browse url
                    Intent intent=new Intent(Intent.ACTION_VIEW, Uri.parse(item.getRawValue()));
                    startActivity(intent);
                }
                break;

                case FirebaseVisionBarcode.TYPE_CONTACT_INFO:
                {
                    String info=new StringBuilder("Name: ")
                            .append(item.getContactInfo().getName().getFormattedName())
                            .append("\n")
                            .append("Address: ")
                            .append(item.getContactInfo().getAddresses().get(0).getAddressLines())
                            .append("\n")
                            .append("Email: ")
                            .append(item.getContactInfo().getEmails().get(0).getAddress())
                            .toString();
                    android.support.v7.app.AlertDialog.Builder  builder=new android.support.v7.app.AlertDialog.Builder(this);
                    builder.setMessage(info);
                    builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.dismiss();
                        }
                    });
                    android.support.v7.app.AlertDialog dialog=builder.create();
                    dialog.show();

                }
                break;
                default:
                    break;
            }
        }
        waitingDialog.dismiss();
    }

}
