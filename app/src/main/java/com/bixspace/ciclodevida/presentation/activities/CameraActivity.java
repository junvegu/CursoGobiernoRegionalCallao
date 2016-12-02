package com.bixspace.ciclodevida.presentation.activities;

import android.Manifest;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.bixspace.ciclodevida.R;
import com.bixspace.ciclodevida.presentation.utils.CamerUtils;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by junior on 30/11/16.
 */

public class CameraActivity extends AppCompatActivity {


    private static final int CAPTURE_IMAGE_FULLSIZE_ACTIVITY_REQUEST_CODE = 100;
    private ImageView mImageView;
    private Button mButton;

    //Estos parametros sirven para controlar la imagen tomada desde la cámara
    private File photo;
    private Bitmap bitmap;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);

        mImageView = (ImageView) findViewById(R.id.iv_image);
        mButton = (Button) findViewById(R.id.btn_capture);

        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                checkSelfPermissons();


            }
        });

    }



    //Codigo de permiso para Android 6.0
    public void checkSelfPermissons() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
                == PackageManager.PERMISSION_GRANTED) {

            //Si los permisos están aceptados
            selectImage();
        } else {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.CAMERA}, 0);
        }

    }


    //Respuesta satisfactoria para los permisos de android 6.0
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == 0) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                //Si le diste aceptar al pequeño dialogo de validar los permisos
                selectImage();
            }
        }
    }







    //Este método llama cuando la cámara ha terminado su funciona, sea aceptaste la foto o rechazaste la foto

    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (resultCode == Activity.RESULT_OK) {


            switch (requestCode) {
                //Caso de exito en todo el flojo de tomar la foto y aceptarla
                case CAPTURE_IMAGE_FULLSIZE_ACTIVITY_REQUEST_CODE:

                        new GetImages().execute();
                    break;



            }
        }


    }

    //Tarea Asincronia que permitira por segundo renderizar la imagen a nuestro gusto
    public class GetImages extends AsyncTask<Void, Void, Void> {

        protected void onPreExecute() {

        }

        @Override
        protected Void doInBackground(Void... params) {

            if (photo != null) {

                bitmap = CamerUtils.decodeBitmapFromFile(photo.getAbsolutePath(), 700, 700);


                if (bitmap != null) {
                    if (!CamerUtils.getDropboxIMGSize(Uri.fromFile(photo))) {
                        bitmap = CamerUtils.rotateImage(bitmap, 70);
                    }

                    bitmap = CamerUtils.getResizedBitmap(bitmap, 720, 1280);

                    try {
                        photo.createNewFile();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    FileOutputStream ostream = null;
                    try {
                        ostream = new FileOutputStream(photo);
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 80, ostream);
                    try {
                        ostream.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else {
                    Log.e("DADADA","falla");
                    showMessage("Ocurrió un problema al capturar la foto, puede que no tenga memoria ram suficiente. Por favor inténtelo nuevamente");
                    this.cancel(true);

                }


            } else {
                Log.e("DADADA","falla 2");
                showMessage("Ocurrió un problema al capturar la foto, puede que no tenga memoria ram suficiente. Por favor inténtelo nuevamente");
                this.cancel(true);

            }


            //  image_drawable.add(d);

            return null;
        }

        @Override
        protected void onCancelled(Void aVoid) {
            super.onCancelled(aVoid);
        }


        protected void onPostExecute(Void result) {

            mImageView.setImageBitmap(bitmap);

              ByteArrayOutputStream stream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
            byte[] byteArray = stream.toByteArray();

            Log.e("DATA",byteArray.toString());
        }
    }

    public void showMessage(String msg){
        //Toast.makeText(this,msg, Toast.LENGTH_SHORT).show();;
    }

    private void cameraIntent() {
        Intent intentImage = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        photo = CamerUtils.getOutputMediaFile();
        intentImage.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(photo));
        startActivityForResult(intentImage, CAPTURE_IMAGE_FULLSIZE_ACTIVITY_REQUEST_CODE);
    }


    private void selectImage() {
        final CharSequence[] items = {"Cámara", "Galería",
                "Cancelar"};

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Adjuntar Imagen");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                if (items[item].equals("Cámara")) {
                    cameraIntent();
                } else if (items[item].equals("Galería")) {


                } else if (items[item].equals("Cancelar")) {
                    dialog.dismiss();
                }
            }
        });

        builder.show();


    }

}
