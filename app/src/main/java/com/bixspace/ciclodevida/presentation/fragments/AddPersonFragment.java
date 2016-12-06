package com.bixspace.ciclodevida.presentation.fragments;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.location.LocationManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.bixspace.ciclodevida.R;
import com.bixspace.ciclodevida.core.ScrollChildSwipeRefreshLayout;
import com.bixspace.ciclodevida.data.PersonaEntity;
import com.bixspace.ciclodevida.data.local.SessionManager;
import com.bixspace.ciclodevida.presentation.activities.CameraActivity;
import com.bixspace.ciclodevida.presentation.activities.LoginActivity;
import com.bixspace.ciclodevida.presentation.adapters.PersonsAdapters;
import com.bixspace.ciclodevida.presentation.contracts.AddPersonContract;
import com.bixspace.ciclodevida.presentation.contracts.MainContract;
import com.bixspace.ciclodevida.presentation.presenters.AddPersonPresenter;
import com.bixspace.ciclodevida.presentation.presenters.MainPresenter;
import com.bixspace.ciclodevida.presentation.services.GeolocationService;
import com.bixspace.ciclodevida.presentation.utils.CamerUtils;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsStatusCodes;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by junior on 28/11/16.
 */

public class AddPersonFragment extends Fragment  implements AddPersonContract.View,
       GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, ResultCallback {
    protected LocationRequest locationRequest;
    int REQUEST_CHECK_SETTINGS = 100;
    protected GoogleApiClient mGoogleApiClient;
    private Button btn_send;
    private EditText et_firs_name;
    private EditText et_last_name;
    private EditText et_age;
    private ImageView imageView;



    private static final int CAPTURE_IMAGE_FULLSIZE_ACTIVITY_REQUEST_CODE = 666;
    //Estos parametros sirven para controlar la imagen tomada desde la cámara
    private File photo;
    private Bitmap bitmap;


    private ProgressDialog progressDialog;
    public boolean hasGPSEnabled() {
        final LocationManager manager = (LocationManager) getContext().getSystemService(Context.LOCATION_SERVICE);
        return manager.isProviderEnabled(LocationManager.GPS_PROVIDER) || manager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
    }

    private AddPersonContract.Presenter mPresenter;

    public AddPersonFragment() {
        // Requires empty public constructor
    }

    public static AddPersonFragment newInstance() {
        return new AddPersonFragment();
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPresenter = new AddPersonPresenter(this,getContext());
        progressDialog = new ProgressDialog(getContext());
        progressDialog.setTitle("Enviando datos...");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
    }

    @Override
    public void onResume() {
        super.onResume();
        if(hasGPSEnabled()){
            getActivity().startService(new Intent(getContext(), GeolocationService.class));

        }else{
            mGoogleApiClient = new GoogleApiClient.Builder(getContext())
                    .addApi(LocationServices.API)
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this).build();
            mGoogleApiClient.connect();

            locationRequest = LocationRequest.create();
            locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
            locationRequest.setInterval(30 * 1000);
            locationRequest.setFastestInterval(5 * 1000);
        }


    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_layout_add_persons, container, false);

        btn_send = (Button) root.findViewById(R.id.btn_send);
        et_firs_name = (EditText) root.findViewById(R.id.et_name);
        et_last_name = (EditText) root.findViewById(R.id.et_last_name);
        et_age = (EditText) root.findViewById(R.id.et_age);
        imageView= (ImageView) root.findViewById(R.id.iv_photo);

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               checkSelfPermissons();
            }
        });



        btn_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
                byte[] byteArray = byteArrayOutputStream .toByteArray();

                String encoded = Base64.encodeToString(byteArray, Base64.DEFAULT);

               /* ByteArrayOutputStream stream = new ByteArrayOutputStream();
              //  bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
                byte[] byteArray = stream.toByteArray();
                byte[] imageByte= Base64.encode(byteArray,Base64.DEFAULT);

                String imgCamera = new String(imageByte);
                */

                mPresenter.addPerson(et_firs_name.getText().toString(),
                        et_last_name.getText().toString(),
                        Integer.valueOf(et_age.getText().toString()),
                        encoded);
            }
        });

        return root;


    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);



    }


    @Override
    public void showMessage(String msg) {
        Toast.makeText(getActivity(), msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void loadingIndicator(final boolean active) {
        if (active) {
            progressDialog.show();
        } else {
            if (progressDialog.isShowing())
                progressDialog.dismiss();
        }
    }

    @Override
    public void addPersonSucces() {

            //getActivity().finish();
        //
    }


    @Override
    public void setPresenter(AddPersonContract.Presenter presenter) {
        mPresenter=presenter;
    }



    //Codigo de permiso para Android 6.0
    public void checkSelfPermissons() {
        if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.CAMERA)
                == PackageManager.PERMISSION_GRANTED) {

            //Si los permisos están aceptados
            selectImage();
        } else {
            ActivityCompat.requestPermissions(getActivity(),
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

    private void selectImage() {
        final CharSequence[] items = {"Cámara", "Galería",
                "Cancelar"};

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
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
    private void cameraIntent() {
        Intent intentImage = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        photo = CamerUtils.getOutputMediaFile();
        intentImage.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(photo));
        startActivityForResult(intentImage, CAPTURE_IMAGE_FULLSIZE_ACTIVITY_REQUEST_CODE);
    }



    @Override
    public void onConnected(@Nullable Bundle bundle) {
        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder()
                .addLocationRequest(locationRequest);
        builder.setAlwaysShow(true);
        PendingResult result =
                LocationServices.SettingsApi.checkLocationSettings(
                        mGoogleApiClient,
                        builder.build()
                );

        result.setResultCallback(this);
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    public void onResult(@NonNull Result result) {
        final Status status = result.getStatus();
        switch (status.getStatusCode()) {
            case LocationSettingsStatusCodes.SUCCESS:


                getActivity().startService(new Intent(getContext(), GeolocationService.class));

                // NO need to show the dialog;

                break;

            case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:
                //  Location settings are not satisfied. Show the user a dialog

                try {
                    // Show the dialog by calling startResolutionForResult(), and check the result
                    // in onActivityResult().

                    status.startResolutionForResult(getActivity(), REQUEST_CHECK_SETTINGS);

                } catch (IntentSender.SendIntentException e) {

                    //failed to show
                }
                break;

            case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:
                // Location settings are unavailable so not possible to show any dialog now
                break;
        }
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CHECK_SETTINGS) {

            if (resultCode == Activity.RESULT_OK) {

                Toast.makeText(getContext(), "GPS ENABLED", Toast.LENGTH_SHORT).show();

            } else {
                Toast.makeText(getContext(), "GPS NOT ENABLED", Toast.LENGTH_SHORT).show();

            }

        }


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

                        //aqui cambie el angulo de rotacion
                        bitmap = CamerUtils.rotateImage(bitmap, 90);
                    }

                    bitmap = CamerUtils.getResizedBitmap(bitmap, 800, 800);

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

            imageView.setImageBitmap(bitmap);

            //ByteArrayOutputStream stream = new ByteArrayOutputStream();
            //bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
            //byte[] byteArray = stream.toByteArray();

            //Log.e("DATA",byteArray.toString());
        }
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        getActivity().stopService(new Intent(getContext(), GeolocationService.class));

    }
}
