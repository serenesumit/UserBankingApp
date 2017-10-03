package app.banking.com.demobankingapp;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
        import java.util.Date;
        import java.util.Locale;

        import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
        import android.graphics.Bitmap;
        import android.graphics.BitmapFactory;
        import android.net.Uri;
        import android.os.Bundle;
        import android.os.Environment;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
        import android.view.View;
        import android.widget.Button;
        import android.widget.ImageView;
        import android.widget.Toast;
        import android.widget.VideoView;

import com.google.gson.Gson;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DepositChequeActivity extends Activity {

    // Activity request codes
    private static final int CAMERA_CAPTURE_IMAGE_REQUEST_CODE = 100;
    private static final int CAMERA_CAPTURE_VIDEO_REQUEST_CODE = 200;
    public static final int MEDIA_TYPE_IMAGE = 1;
    public static final int MEDIA_TYPE_VIDEO = 2;

    // directory name to store captured images and videos
    private static final String IMAGE_DIRECTORY_NAME = "cheque";

    Bitmap FixBitmap;
    Button UploadImageOnServerButton;
    String ImageTag = "image_tag" ;

    String ImageName = "image_data" ;

    private ProgressDialog mSpinner;
    ByteArrayOutputStream byteArrayOutputStream ;

    byte[] byteArray ;

    String ConvertImage ;

    String GetImageNameFromEditText;

    HttpURLConnection httpURLConnection ;

    URL url;

    OutputStream outputStream;

    BufferedWriter bufferedWriter ;

    int RC ;

    BufferedReader bufferedReader ;


    StringBuilder stringBuilder;

    boolean check = true;

    private Uri fileUri; // file url to store image/video

    private ImageView imgPreview;
    private VideoView videoPreview;
    private Button btnCapturePicture, btnRecordVideo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.depositcheque);

        imgPreview = (ImageView) findViewById(R.id.imgPreview);
        btnCapturePicture = (Button) findViewById(R.id.btnCapturePicture);
        UploadImageOnServerButton = (Button)findViewById(R.id.uploadcheque);

        byteArrayOutputStream = new ByteArrayOutputStream();
        /**
         * Capture image button click event
         */
        btnCapturePicture.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // capture picture
                captureImage();
            }
        });
        UploadImageOnServerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                UploadImageToServer();

            }
        });
        // Checking camera availability
        if (!isDeviceSupportCamera()) {
            Toast.makeText(getApplicationContext(),
                    "Sorry! Your device doesn't support camera",
                    Toast.LENGTH_LONG).show();
            // will close the app if the device does't have camera
            finish();
        }
    }

    /**
     * Checking device has camera hardware or not
     * */
    private boolean isDeviceSupportCamera() {
        if (getApplicationContext().getPackageManager().hasSystemFeature(
                PackageManager.FEATURE_CAMERA)) {
            // this device has a camera
            return true;
        } else {
            // no camera on this device
            return false;
        }
    }

    /**
     * Capturing Camera Image will lauch camera app requrest image capture
     */
    private void captureImage() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        fileUri = getOutputMediaFileUri(MEDIA_TYPE_IMAGE);

        intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);

        // start the image capture Intent
        startActivityForResult(intent, CAMERA_CAPTURE_IMAGE_REQUEST_CODE);
    }

    /**
     * Here we store the file url as it will be null after returning from camera
     * app
     */
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        // save file url in bundle as it will be null on scren orientation
        // changes
        outState.putParcelable("file_uri", fileUri);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        // get the file url
        fileUri = savedInstanceState.getParcelable("file_uri");
    }


    /**
     * Receiving activity result method will be called after closing the camera
     * */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // if the result is capturing Image
        if (requestCode == CAMERA_CAPTURE_IMAGE_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                // successfully captured the image
                // display it in image view
                previewCapturedImage();
            } else if (resultCode == RESULT_CANCELED) {
                // user cancelled Image capture
                Toast.makeText(getApplicationContext(),
                        "User cancelled image capture", Toast.LENGTH_SHORT)
                        .show();
            } else {
                // failed to capture image
                Toast.makeText(getApplicationContext(),
                        "Sorry! Failed to capture image", Toast.LENGTH_SHORT)
                        .show();
            }
        }
    }

    /**
     * Display image from a path to ImageView
     */
    private void previewCapturedImage() {
        try {
            imgPreview.setVisibility(View.VISIBLE);

            // bimatp factory
            BitmapFactory.Options options = new BitmapFactory.Options();

            // downsizing image as it throws OutOfMemory Exception for larger
            // images
            options.inSampleSize = 8;

            FixBitmap = BitmapFactory.decodeFile(fileUri.getPath(),
                    options);

            imgPreview.setImageBitmap(FixBitmap);
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }

    /**
     * ------------ Helper Methods ----------------------
     * */

    /**
     * Creating file uri to store image/video
     */
    public Uri getOutputMediaFileUri(int type) {
        return Uri.fromFile(getOutputMediaFile(type));
    }

    /**
     * returning image / video
     */
    private static File getOutputMediaFile(int type) {

        // External sdcard location
        File mediaStorageDir = new File(
                Environment
                        .getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),
                IMAGE_DIRECTORY_NAME);

        // Create the storage directory if it does not exist
        if (!mediaStorageDir.exists()) {
            if (!mediaStorageDir.mkdirs()) {
                Log.d(IMAGE_DIRECTORY_NAME, "Oops! Failed create "
                        + IMAGE_DIRECTORY_NAME + " directory");
                return null;
            }
        }

        // Create a media file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss",
                Locale.getDefault()).format(new Date());
        File mediaFile;
        if (type == MEDIA_TYPE_IMAGE) {
            mediaFile = new File(mediaStorageDir.getPath() + File.separator
                    + "IMG_" + timeStamp + ".jpg");
        } else {
            return null;
        }

        return mediaFile;
    }

    public void UploadImageToServer(){
        showSpinner();
        FixBitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
        byteArray = byteArrayOutputStream.toByteArray();
        ConvertImage = Base64.encodeToString(byteArray, Base64.DEFAULT);
        ChequeModel model = new ChequeModel();
        SharedPreferences appSharedPrefs = PreferenceManager
                .getDefaultSharedPreferences(this.getApplicationContext());
        Gson gson = new Gson();
        String json = appSharedPrefs.getString("user", "");
        UserModelResponse obj = gson.fromJson(json, UserModelResponse.class);
        model.setUserId(obj.getId());
        model.setImageBase64String(ConvertImage);

        Call<ChequeModel> appApi = BankingApp.getInstance().getApi().SaveCheque(model);
        appApi.enqueue(new Callback<ChequeModel>() {
            @Override
            public  void onResponse(Call<ChequeModel> call, Response<ChequeModel> response) {
                if (response.isSuccessful()) {
                    Log.d("response", response.body().getImageUrl());
                    mSpinner.hide();
                }

            }

            @Override
            public void onFailure(Call<ChequeModel> call, Throwable t) {
                Log.d("OnFailure :", t.getMessage());
                mSpinner.hide();
            }
        });

    }

    private void showSpinner() {
        mSpinner = new ProgressDialog(this);
        mSpinner.setTitle("Uploading");
        mSpinner.setMessage("Please wait...");
        mSpinner.show();
    }




}



