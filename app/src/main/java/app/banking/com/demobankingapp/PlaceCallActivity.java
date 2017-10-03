package app.banking.com.demobankingapp;

        import com.sinch.android.rtc.calling.Call;

        import android.content.Intent;
        import android.os.Bundle;
        import android.os.StrictMode;
        import android.view.View;
        import android.view.View.OnClickListener;
        import android.widget.Button;
        import android.widget.EditText;
        import android.widget.TextView;
        import android.widget.Toast;

        import java.io.BufferedInputStream;
        import java.io.BufferedReader;
        import java.io.InputStream;
        import java.io.InputStreamReader;
        import java.net.HttpURLConnection;
        import java.net.URL;
        import java.util.List;

public class PlaceCallActivity extends BaseActivity {

    private Button mCallButton;
    private EditText mCallName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        //initializing UI elements
       // mCallName = (EditText) findViewById(R.id.callName);
        mCallButton = (Button) findViewById(R.id.callButton);
        mCallButton.setEnabled(false);
        mCallButton.setOnClickListener(buttonClickListener);

        Button stopButton = (Button) findViewById(R.id.stopButton);
        stopButton.setOnClickListener(buttonClickListener);
    }

    // invoked when the connection with SinchServer is established
    @Override
    protected void onServiceConnected() {
        TextView userName = (TextView) findViewById(R.id.loggedInName);
        userName.setText(getSinchServiceInterface().getUserName());
        mCallButton.setEnabled(true);
    }

    @Override
    public void onDestroy() {
        if (getSinchServiceInterface() != null) {
            getSinchServiceInterface().stopClient();
        }
        super.onDestroy();
    }

    //to kill the current session of SinchService
    private void stopButtonClicked() {
        if (getSinchServiceInterface() != null) {
            getSinchServiceInterface().stopClient();
        }
        finish();
    }

    //to place the call to the entered name
    private void callButtonClicked() {

      // new WebApiService().execute();
        List<Admin> admins = Service.CommonService.GetAdmins();
        String userName = admins.get(0).PhoneNumber;

       // String userName = mCallName.getText().toString();
        if (userName.isEmpty()) {
            Toast.makeText(this, "Please enter a phone number to call", Toast.LENGTH_LONG).show();
            return;
        }

        /*if (!isValidMobile(userName))
        {
            Toast.makeText(this, "Please enter a valid number to call", Toast.LENGTH_LONG).show();
            return;
        }*/

        Call call = getSinchServiceInterface().callUserVideo(userName);
        String callId = call.getCallId();

        Intent callScreen = new Intent(this, CallScreenActivity.class);
        callScreen.putExtra(SinchService.CALL_ID, callId);
        startActivity(callScreen);
    }


    private OnClickListener buttonClickListener = new OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.callButton:
                    callButtonClicked();
                    break;

                case R.id.stopButton:
                    stopButtonClicked();
                    break;

            }
        }
    };


    // validate phone number
    private boolean isValidMobile(String phone){
        Boolean check=false;
        String Regex = "[^\\d]";
        String PhoneDigits = phone.replaceAll(Regex, "");
        if (PhoneDigits.length()!=10)
        {
            return  check;
        }

        else
        {
            phone = "+";
            phone = phone.concat(PhoneDigits); // adding the plus sign

            return  true;

        }
    }
}
