package com.app.devrah;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;

public class ProfileActivity extends AppCompatActivity {

    private static int IMG_RESULT = 2;
    Intent intent;
    String mCurrentPhotoPath,ImageDecode;

    Button chooseFile;
    private static final int REQUEST_PERMISSIONS=0;
    static final int REQUEST_IMAGE_CAPTURE = 1;

    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        toolbar = (Toolbar) findViewById(R.id.header);
        toolbar.setTitle("Update Profile");
        setSupportActionBar(toolbar);
        toolbar.setTitleTextColor(getResources().getColor(android.R.color.white));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        chooseFile= (Button) findViewById(R.id.btnchoosePic);
        chooseFile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startDialogForProfile();
            }
        });

    }
    private void startDialogForProfile() {
        AlertDialog.Builder myAlertDialog = new AlertDialog.Builder(
                ProfileActivity.this);
        myAlertDialog.setTitle("Upload Pictures Option");
        myAlertDialog.setMessage("How do you want to set your picture?");

        myAlertDialog.setPositiveButton("Gallery",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface arg0, int arg1) {
                        /*Intent intent = new Intent(MainActivity.this, AlbumSelectActivity.class);
                        intent.putExtra(Constants.INTENT_EXTRA_LIMIT, 4);
                        startActivityForResult(intent, Constants.REQUEST_CODE);*/

                        if (ActivityCompat.checkSelfPermission(ProfileActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {


                            if (Build.VERSION.SDK_INT > 22) {

                                requestPermissions(new String[]{Manifest.permission
                                                .WRITE_EXTERNAL_STORAGE},
                                        REQUEST_PERMISSIONS);

                            }

                        } else {
                            upload();

                        }

                    }
                });

        myAlertDialog.setNegativeButton("Camera",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface arg0, int arg1) {
                        if (ActivityCompat.checkSelfPermission(ProfileActivity.this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {


                            if (Build.VERSION.SDK_INT > 22) {

                                requestPermissions(new String[]{Manifest.permission
                                                .CAMERA},
                                        REQUEST_IMAGE_CAPTURE);
                      /*  Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                        Uri uri = Uri.fromParts("package", MainActivity.this.getPackageName(), null);
                        intent.setData(uri);
                        startActivityForResult(intent, REQUEST_PERMISSION_SETTING);*/
                                // Toast.makeText(MainActivity.this.getBaseContext(), "Go to Permissions to Grant Storage", Toast.LENGTH_LONG).show();

                            }

                        } else {
                            camera();

                        }

                    }
                });
        myAlertDialog.show();
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {

        if (requestCode == REQUEST_PERMISSIONS || requestCode==REQUEST_IMAGE_CAPTURE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                //The External Storage Write Permission is granted to you... Continue your left job...
                if(requestCode==0) {
                    upload();
                }
                else if(requestCode==1){
                    camera();
                }
            } else {

                Toast.makeText(ProfileActivity.this, "Permission Denied", Toast.LENGTH_SHORT).show();
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }
    private void camera() {

        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
           /* uri = getOutputMediaFileUri();
            intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);*/
        startActivityForResult(intent, REQUEST_IMAGE_CAPTURE);
        //  imageView.mCurrentShape = DrawingView.SMOOTHLINE;
        //imageView.reset();
    }

    private void upload() {
        intent = new Intent(Intent.ACTION_PICK,
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, IMG_RESULT);

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        try {
            if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK ) {
                Bundle extras = data.getExtras();
                Bitmap imageBitmap = (Bitmap) extras.get("data");


                ImageView imageView=(ImageView) findViewById(R.id.profilePic);
                imageView.setImageBitmap(imageBitmap);
                //imageView.setImageBitmap(imageBitmap);

                // CALL THIS METHOD TO GET THE URI FROM THE BITMAP
                Uri tempUri = getImageUri(getApplicationContext(), imageBitmap);
                // Toast.makeText(MainActivity.this,"Here "+ getRealPathFromURI(tempUri),Toast.LENGTH_LONG).show();
                mCurrentPhotoPath=getPathFromURI(tempUri);

            }
            //  if (requestCode == Constants.REQUEST_CODE && resultCode == RESULT_OK && data != null) {
            if (requestCode == IMG_RESULT  && resultCode == RESULT_OK && data != null) {
                // textView.setText(stringBuffer.toString());
                Uri URI = data.getData();
                String[] FILE = {MediaStore.Images.Media.DATA};


                Cursor cursor = getContentResolver().query(URI,
                        FILE, null, null, null);

                cursor.moveToFirst();

                int columnIndex = cursor.getColumnIndex(FILE[0]);
                ImageDecode = cursor.getString(columnIndex);
                cursor.close();
                ImageView imageView=(ImageView) findViewById(R.id.profilePic);

                Bitmap unscaled= BitmapFactory.decodeFile(ImageDecode);
                int w=imageView.getWidth();
                int h=imageView.getHeight();

               Bitmap scaled=unscaled.createScaledBitmap(unscaled,w,h,true);

//                imageView=(DrawingView)findViewById(R.id.drawingview);
                mCurrentPhotoPath=ImageDecode;
                imageView.setImageBitmap(scaled);
//
                // imageView.setImageBitmap(BitmapFactory
                //       .decodeFile(ImageDecode));

            }
        } catch (Exception e) {
            Toast.makeText(this, "Please try again"+e.toString(), Toast.LENGTH_LONG)
                    .show();
        }
    }
    public Uri getImageUri(Context inContext, Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, "Title", null);
        return Uri.parse(path);
    }
    public String getPathFromURI(Uri contentUri) {
        String res = null;
        String[] proj = {MediaStore.Images.Media.DATA};
        Cursor cursor = getContentResolver().query(contentUri, proj, null, null, null);
        if (cursor.moveToFirst()) {
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            res = cursor.getString(column_index);
        }
        cursor.close();
        return res;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                //NavUtils.navigateUpFromSameTask(this);
                Intent intent=new Intent(this,Dashboard.class);
                finish();
                startActivity(intent);
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
