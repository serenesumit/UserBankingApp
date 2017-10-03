package app.banking.com.demobankingapp;

        import com.sinch.android.rtc.AudioController;
        import com.sinch.android.rtc.PushPair;
        import com.sinch.android.rtc.calling.Call;
        import com.sinch.android.rtc.calling.CallEndCause;
        import com.sinch.android.rtc.calling.CallState;
        import com.sinch.android.rtc.video.VideoCallListener;
        import com.sinch.android.rtc.video.VideoController;

        import android.media.AudioManager;
        import android.os.Bundle;
        import android.util.Log;
        import android.view.View;
        import android.view.View.OnClickListener;
        import android.view.WindowManager;
        import android.widget.Button;
        import android.widget.LinearLayout;
        import android.widget.RelativeLayout;
        import android.widget.TextView;
        import android.widget.Toast;

        import java.util.List;
        import java.util.Locale;
        import java.util.StringTokenizer;
        import java.util.Timer;
        import java.util.TimerTask;

        import java.io.File;
        import java.io.FileOutputStream;
        import java.util.Date;

        import android.content.Intent;
        import android.graphics.Bitmap;
        import android.media.MediaScannerConnection;
        import android.net.Uri;
        import android.os.Environment;
        import android.app.Activity;
        import android.content.Context;
        import android.content.SharedPreferences;
        import android.os.Bundle;
        import android.view.Menu;
        import android.view.View;
        import android.widget.TextView;

public class CallScreenActivity extends BaseActivity {

    static final String TAG = CallScreenActivity.class.getSimpleName();
    static final String CALL_START_TIME = "callStartTime";
    static final String ADDED_LISTENER = "addedListener";

    private AudioPlayer mAudioPlayer;
    private Timer mTimer;
    private UpdateCallDurationTask mDurationTask;

    private String mCallId;
    private long mCallStart = 0;
    private boolean mAddedListener = false;
    private boolean mVideoViewsAdded = false;

    private TextView mCallDuration;
    private TextView mCallState;
    private TextView mCallerName;

    String[] numbers = new String[]{"8195013229","8219007302","9816259573"};
    SharedPreferences sharedpreferences;
    public static final String mypreference = "mypref";
    public static final String num = "03235063243";


    private class UpdateCallDurationTask extends TimerTask {

        @Override
        public void run() {
            CallScreenActivity.this.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    updateCallDuration();
                }
            });
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle savedInstanceState) {
        savedInstanceState.putLong(CALL_START_TIME, mCallStart);
        savedInstanceState.putBoolean(ADDED_LISTENER, mAddedListener);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        mCallStart = savedInstanceState.getLong(CALL_START_TIME);
        mAddedListener = savedInstanceState.getBoolean(ADDED_LISTENER);
    }
    private Button screenShotButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.callscreen);
        // Keep the screen on
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);


        sharedpreferences = getSharedPreferences(mypreference,
                Context.MODE_PRIVATE);
        mAudioPlayer = new AudioPlayer(this);
        mCallDuration = (TextView) findViewById(R.id.callDuration);
        mCallerName = (TextView) findViewById(R.id.remoteUser);
        mCallState = (TextView) findViewById(R.id.callState);
        Button endCallButton = (Button) findViewById(R.id.hangupButton);
       // screenShotButton= (Button) findViewById(R.id.screenShotButton);
        endCallButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                endCall();
            }
        });

       /* screenShotButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                takeScreenshot();
            }
        });*/

        mCallId = getIntent().getStringExtra(SinchService.CALL_ID);
        if (savedInstanceState == null) {
            mCallStart = System.currentTimeMillis();
        }
    }


   /* private void takeScreenshot() {
        Date now = new Date();
        android.text.format.DateFormat.format("yyyy-MM-dd_hh:mm:ss", now);

        try {
            // image naming and path  to include sd card  appending name you choose for file
            String mPath = Environment.getExternalStorageDirectory().toString() + "/PICTURES/Screenshots/" + "test" + ".jpg";
            File dir = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES).getAbsolutePath());
            if(!dir.exists())
            {
                dir.mkdirs();
            }
            // create bitmap screen capture
            View v1 = getWindow().getDecorView().getRootView();
            v1.setDrawingCacheEnabled(true);
            Bitmap bitmap = Bitmap.createBitmap(v1.getDrawingCache());
            v1.setDrawingCacheEnabled(false);
           // File imageFile = new File(mPath);
            File imageFile = new File(dir,"/test.jpg");

            imageFile.createNewFile();
            FileOutputStream outputStream = new FileOutputStream(imageFile);
            int quality = 100;
            bitmap.compress(Bitmap.CompressFormat.JPEG, quality, outputStream);
            outputStream.flush();
            outputStream.close();


            MediaScannerConnection.scanFile(this,
                    new String[]{imageFile.toString()}, null,

                    new MediaScannerConnection.OnScanCompletedListener() {
                        public void onScanCompleted(String path, Uri uri) {
                            Log.i("ExternalStorage", "Scanned " + path + ":");
                            Log.i("ExternalStorage", "-> uri=" + uri);
                        }
                    });

            openScreenshot(imageFile);
        } catch (Throwable e) {
            // Several error may come out with file handling or OOM
            e.printStackTrace();
        }
    }

    private void openScreenshot(File imageFile) {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_VIEW);
        Uri uri = Uri.fromFile(imageFile);
        intent.setDataAndType(uri, "image*//*");
        startActivity(intent);
    }*/

    @Override
    public void onServiceConnected() {
        Call call = getSinchServiceInterface().getCall(mCallId);
        if (call != null) {
            if (!mAddedListener) {
                call.addCallListener(new SinchCallListener());
                mAddedListener = true;
            }
        } else {
            Log.e(TAG, "Started with invalid callId, aborting.");
            finish();
        }

        updateUI();
    }

    //method to update video feeds in the UI
    private void updateUI() {
        if (getSinchServiceInterface() == null) {
            return; // early
        }

        Call call = getSinchServiceInterface().getCall(mCallId);
        if (call != null) {
            mCallerName.setText(call.getRemoteUserId());
            mCallState.setText(call.getState().toString());
            if (call.getState() == CallState.ESTABLISHED) {
                //when the call is established, addVideoViews configures the video to  be shown
                addVideoViews();
            }
        }
    }

    //stop the timer when call is ended
    @Override
    public void onStop() {
        super.onStop();
        mDurationTask.cancel();
        mTimer.cancel();
        removeVideoViews();
    }

    //start the timer for the call duration here
    @Override
    public void onStart() {
        super.onStart();
        mTimer = new Timer();
        mDurationTask = new UpdateCallDurationTask();
        mTimer.schedule(mDurationTask, 0, 500);
        updateUI();
    }

    @Override
    public void onBackPressed() {
        // User should exit activity by ending call, not by going back.
    }

    //method to end the call
    private void endCall() {
        mAudioPlayer.stopProgressTone();
        Call call = getSinchServiceInterface().getCall(mCallId);
        if (call != null) {
            call.hangup();

        }
        finish();
    }

    private void endCallWithTimeOut() {
        mAudioPlayer.stopProgressTone();
        Call call = getSinchServiceInterface().getCall(mCallId);
        if (call != null) {
            call.hangup();

        }
        finish();
        String currentNumber =GetNumber();
        if(currentNumber =="")
        {
            currentNumber = num;
        }

        String newUser = GetUser(currentNumber);
        if(newUser !=null && newUser !="")
        {
            Call newcall = getSinchServiceInterface().callUserVideo(newUser);
            String callId = newcall.getCallId();

            Intent callScreen = new Intent(this, CallScreenActivity.class);
            callScreen.putExtra(SinchService.CALL_ID, callId);
            startActivity(callScreen);
        }
    }

    public String GetUser(String currentNumber)
    {
        if(currentNumber !=null && currentNumber !="")
        {
            currentNumber = currentNumber.trim();
        }

        if(currentNumber.equals("7986894456"))
        {
            currentNumber ="03235063243";
            SaveNumber(currentNumber);
            return  currentNumber;
        }
        if(currentNumber == "8195013229")
        {
            currentNumber ="8219007302";
            SaveNumber(currentNumber);
            return  currentNumber;
        }
        if(currentNumber =="8219007302")
        {
            currentNumber ="9816259573";
            SaveNumber(currentNumber);
            return  currentNumber;
        }
        return  "";
    }


    private void SaveNumber(String number)
    {
        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.putString(num, number);
        editor.commit();
    }
  private String GetNumber()
  {
      return sharedpreferences.getString(num,"");
  }

    private String formatTimespan(long timespan) {
        long totalSeconds = timespan / 1000;
        long minutes = totalSeconds / 60;
        long seconds = totalSeconds % 60;
        return String.format(Locale.US, "%02d:%02d", minutes, seconds);
    }

    //method to update live duration of the call
    private void updateCallDuration() {
        if (mCallStart > 0) {
            mCallDuration.setText(formatTimespan(System.currentTimeMillis() - mCallStart));
        }
    }

    //method which sets up the video feeds from the server to the UI of the activity
    private void addVideoViews() {
        if (mVideoViewsAdded || getSinchServiceInterface() == null) {
            return; //early
        }

        final VideoController vc = getSinchServiceInterface().getVideoController();
        if (vc != null) {
            RelativeLayout localView = (RelativeLayout) findViewById(R.id.localVideo);
            localView.addView(vc.getLocalView());

            localView.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    //this toggles the front camera to rear camera and vice versa
                    vc.toggleCaptureDevicePosition();
                }
            });

            LinearLayout view = (LinearLayout) findViewById(R.id.remoteVideo);
            view.addView(vc.getRemoteView());
            mVideoViewsAdded = true;
        }
    }

    //removes video feeds from the app once the call is terminated
    private void removeVideoViews() {
        if (getSinchServiceInterface() == null) {
            return; // early
        }

        VideoController vc = getSinchServiceInterface().getVideoController();
        if (vc != null) {
            LinearLayout view = (LinearLayout) findViewById(R.id.remoteVideo);
            view.removeView(vc.getRemoteView());

            RelativeLayout localView = (RelativeLayout) findViewById(R.id.localVideo);
            localView.removeView(vc.getLocalView());
            mVideoViewsAdded = false;
        }
    }

    private class SinchCallListener implements VideoCallListener {

        @Override
        public void onCallEnded(Call call) {
            CallEndCause cause = call.getDetails().getEndCause();
          // Log.d(TAG, "Call ended. Reason: " + cause.toString());

            mAudioPlayer.stopProgressTone();
            setVolumeControlStream(AudioManager.USE_DEFAULT_STREAM_TYPE);
           // String endMsg = "Call ended: " + call.getDetails().toString();
           // Toast.makeText(CallScreenActivity.this, endMsg, Toast.LENGTH_LONG).show();
            if(cause.toString() =="TIMEOUT")
            {
                endCallWithTimeOut();
            }
            else
            {
                endCall();
            }
        }

        @Override
        public void onCallEstablished(Call call) {
            Log.d(TAG, "Call established");
            mAudioPlayer.stopProgressTone();
            mCallState.setText(call.getState().toString());
            setVolumeControlStream(AudioManager.STREAM_VOICE_CALL);
            AudioController audioController = getSinchServiceInterface().getAudioController();
            audioController.enableSpeaker();
            mCallStart = System.currentTimeMillis();
            Log.d(TAG, "Call offered video: " + call.getDetails().isVideoOffered());
        }

        @Override
        public void onCallProgressing(Call call) {
            Log.d(TAG, "Call progressing");
            mAudioPlayer.playProgressTone();
        }

        @Override
        public void onShouldSendPushNotification(Call call, List pushPairs) {
            // Send a push through your push provider here, e.g. GCM
        }


        @Override
        public void onVideoTrackAdded(Call call) {
            Log.d(TAG, "Video track added");
            addVideoViews();
        }
    }
}