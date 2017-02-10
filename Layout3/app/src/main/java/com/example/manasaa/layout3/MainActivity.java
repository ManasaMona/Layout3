package com.example.manasaa.layout3;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;

public class MainActivity extends AppCompatActivity  implements View.OnClickListener{

    private Button mWebPage,mCameraButton, mGalleryImageButton;
    private ImageView mImageView;
    static final int REQUEST_IMAGE_CAPTURE = 1;
    static final int PICK_IMAGE = 2;
    private static final String TAG= MainActivity.class.getSimpleName();
    private static int belowWebView=0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mWebPage=(Button) findViewById(R.id.webPage);
        mCameraButton=(Button) findViewById(R.id.cameraButton);
        mGalleryImageButton=(Button) findViewById(R.id.galleryImageButton);
        mImageView=(ImageView)findViewById(R.id.imageView);



        mWebPage.setOnClickListener(this);
        mCameraButton.setOnClickListener(this);
        mGalleryImageButton.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        int viewId=v.getId();

        switch (viewId){
            case R.id.webPage:
                                mGalleryImageButton.setVisibility(View.VISIBLE);
                                Intent intent= new Intent(this,BrowserActivity.class);
                                intent.putExtra("url","https://www.android.com/");
                                startActivity(intent);
                                break;
            case R.id.cameraButton:
                                dispatchTakePictureIntent();
                                break;
            case R.id.galleryImageButton:
                                Intent pickIntent = new Intent();
                                pickIntent.setType("image/*");
                                pickIntent.setAction(pickIntent.ACTION_GET_CONTENT);
                                startActivityForResult(Intent.createChooser(pickIntent, "Select Picture"), PICK_IMAGE);
                                break;


        }
    }

    private void dispatchTakePictureIntent() {//To take picture from camera
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {//For camera
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            mImageView.setImageBitmap(imageBitmap);
            galleryButtonPosition();
        }

        else if(requestCode == PICK_IMAGE && resultCode != RESULT_CANCELED){//for Gallary
            Uri selectedImageUri = data.getData();
            if (null != selectedImageUri) {
                String path = getPathFromURI(selectedImageUri); // Get the path from the Uri
                mImageView.setImageURI(selectedImageUri);// Set the image in ImageView
                galleryButtonPosition();          // mGalleryImageButton.setVisibility(View.INVISIBLE);
            }
        }
    }

    /* Get the real path from the URI */
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

    /*Check gallery button position*/
    private void galleryButtonPosition() {
        RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams)mGalleryImageButton.getLayoutParams();
        if (belowWebView == 0) {
            params.addRule(RelativeLayout.BELOW, R.id.imageView);
            mGalleryImageButton.setLayoutParams(params); //causes layout update
            belowWebView=1;
        }
    }

    }
