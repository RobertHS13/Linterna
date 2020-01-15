package com.example.usuario.libre;
import android.Manifest;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.hardware.Camera;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

public class MainActivity extends AppCompatActivity {
    ImageButton btnlinterna;
    Camera camera;
    Camera.Parameters parameters;
    boolean isFlash = false;
    boolean isOn = false;

    @Override
    protected void onStop() {
        super.onStop();
        if(camera != null){
            camera.release();
            camera = null;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        btnlinterna = (ImageButton)findViewById(R.id.btnOnOff);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA,}, 1000);
        } else {


            if (getApplicationContext().getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA_FLASH)) {
                camera = Camera.open();
                parameters = camera.getParameters();
                isFlash = true;
            }


            btnlinterna.setOnClickListener(new View.OnClickListener(){

                @Override
                public  void onClick(View v) {

                    if (isFlash) {

                        if (!isOn) {
                            btnlinterna.setImageResource(R.drawable.off);
                            parameters.setFlashMode(Camera.Parameters.FLASH_MODE_TORCH);
                            camera.setParameters(parameters);
                            camera.startPreview();
                            isOn = true;
                        } else {
                            btnlinterna.setImageResource(R.drawable.on);
                            parameters.setFlashMode(Camera.Parameters.FLASH_MODE_OFF);
                            camera.setParameters(parameters);
                            camera.stopPreview();
                            isOn = false;

                        }


                    } else {

                        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                        builder.setTitle("Error");
                        builder.setMessage("flash no compatible");
                        builder.setPositiveButton("ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                                finish();
                            }

                        });

                        AlertDialog alertDialog = builder.create();
                        alertDialog.show();
                    }

                }


            });

        }     }
}