package app.banking.com.demobankingapp;

        import com.google.gson.Gson;
        import com.sinch.android.rtc.SinchError;
        import com.sinch.verification.CodeInterceptionException;
        import com.sinch.verification.InvalidInputException;
        import com.sinch.verification.ServiceErrorException;
        import com.sinch.verification.Verification;
        import com.sinch.verification.VerificationListener;

        import android.Manifest;
        import android.app.AlertDialog;
        import android.app.ProgressDialog;
        import android.content.DialogInterface;
        import android.content.Intent;
        import android.content.SharedPreferences;
        import android.os.Build;
        import android.os.Bundle;
        import android.os.StrictMode;
        import android.preference.PreferenceManager;
        import android.text.Html;
        import android.util.Log;
        import android.view.View;
        import android.view.View.OnClickListener;
        import android.widget.Button;
        import android.widget.EditText;
        import android.widget.ProgressBar;
        import android.widget.TextView;
        import android.widget.Toast;

        import retrofit2.Call;
        import retrofit2.Callback;
        import retrofit2.Response;

public class LoginActivity extends BaseActivity implements SinchService.StartFailedListener {

    // Test Starts
    private TextView numberPhone;
    private Button verify;
    private ProgressBar progressBar;
    private Verification verification;
    // Test Ends

    private Button mLoginButton;
    private EditText mLoginName;
    private ProgressDialog mSpinner;
    SharedPreferences appSharedPrefs;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        appSharedPrefs = PreferenceManager
                .getDefaultSharedPreferences(this.getApplicationContext());

        //asking for permissions here
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermissions(new String[]{Manifest.permission.RECORD_AUDIO, Manifest.permission.CAMERA, Manifest.permission.ACCESS_NETWORK_STATE, Manifest.permission.READ_PHONE_STATE},100);
        }

        //initializing UI elements
        mLoginName = (EditText) findViewById(R.id.loginName);
        mLoginButton = (Button) findViewById(R.id.loginButton);
        mLoginButton.setEnabled(false);
        mLoginButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                loginClicked();
            }
        });
    }

    //this method is invoked when the connection is established with the SinchService
    @Override
    protected void onServiceConnected() {
        mLoginButton.setEnabled(true);
        getSinchServiceInterface().setStartListener(this);
    }

    @Override
    protected void onPause() {
        if (mSpinner != null) {
            mSpinner.dismiss();
        }
        super.onPause();
    }

    @Override
    public void onStartFailed(SinchError error) {
        Toast.makeText(this, error.toString(), Toast.LENGTH_LONG).show();
        if (mSpinner != null) {
            mSpinner.dismiss();
        }
    }

    //Invoked when just after the service is connected with Sinch
    @Override
    public void onStarted() {
        openPlaceCallActivity();
    }

    //Login is Clicked to manually to connect to the Sinch Service
    private void loginClicked() {
       final String userName = mLoginName.getText().toString();
        UserModelResponse user= new UserModelResponse();
        user.setContact(userName);
        showSpinner();
        Call<UserModelResponse> appApi = BankingApp.getInstance().getApi().UserLogin(user);
        appApi.enqueue(new Callback<UserModelResponse>() {
            @Override
            public  void onResponse(Call<UserModelResponse> call, Response<UserModelResponse> response) {
                if (response.isSuccessful()) {
                    Log.d("response", response.body().getContact());
                    UserModelResponse userModel =response.body();
                    final boolean isProfileApproved = userModel.isIsApproved();
                    SharedPreferences.Editor prefsEditor = appSharedPrefs.edit();
                    Gson gson = new Gson();
                    String json = gson.toJson(userModel);
                    prefsEditor.putString("user", json);
                    prefsEditor.commit();
                    if(isProfileApproved)
                    {
                        mSpinner.hide();
                        openDepositChequeActivity();
                    }
                    else
                    {
                        mSpinner.hide();
                        if (!getSinchServiceInterface().isStarted()) {
                            getSinchServiceInterface().startClient(userName);
                            showSpinner();
                        }
                        else
                        {
                            openPlaceCallActivity();
                        }
                    }
                }

            }

            @Override
            public void onFailure(Call<UserModelResponse> call, Throwable t) {
                Log.d("OnFailure :", t.getMessage());

            }
        });


      //  UserModelResponse userModel = Service.CommonService.UserLogin(user);
        if (userName.isEmpty()) {
            Toast.makeText(this, "Please enter a phone number", Toast.LENGTH_LONG).show();
            return;
        }
       /* if(!isValidMobile(userName))
        {
            Toast.makeText(this, "Please enter a valid phone", Toast.LENGTH_LONG).show();
            return;
        }*/



    }


    //Once the connection is made to the Sinch Service, It takes you to the next activity where you enter the name of the user to whom the call is to be placed
    private void openPlaceCallActivity() {
        Intent mainActivity = new Intent(this, PlaceCallActivity.class);
        startActivity(mainActivity);
    }
    private void openDepositChequeActivity() {
        Intent mainActivity = new Intent(this, DepositChequeActivity.class);
        startActivity(mainActivity);
    }

    private void showSpinner() {
        mSpinner = new ProgressDialog(this);
        mSpinner.setTitle("Logging in");
        mSpinner.setMessage("Please wait...");
        mSpinner.show();
    }

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


    // Test Starts

    private void showProgressDialog() {
        progressBar.setVisibility(ProgressBar.VISIBLE);
    }

    private void hideProgressDialog() {
        progressBar.setVisibility(ProgressBar.INVISIBLE);
    }

    private class MyVerificationListener implements VerificationListener {

        @Override
        public void onInitiated() {
        }

        @Override
        public void onInitiationFailed(Exception e) {
            hideProgressDialog();
            if (e instanceof InvalidInputException) {
                Toast.makeText(LoginActivity.this, "Incorrect number provided", Toast.LENGTH_LONG).show();
            } else if (e instanceof ServiceErrorException) {
                Toast.makeText(LoginActivity.this, "Sinch service error", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(LoginActivity.this, "Other system error, check your network state", Toast.LENGTH_LONG).show();
            }
        }

        @Override
        public void onVerified() {
            hideProgressDialog();
            new AlertDialog.Builder(LoginActivity.this)
                    .setMessage(Html.fromHtml("<font color=\"purple\">Verification Successful!</font>"))
                    .setPositiveButton("Done", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int whichButton) {
                            dialog.cancel();
                        }
                    })
                    .show();
        }

        @Override
        public void onVerificationFailed(Exception e) {
            hideProgressDialog();
            if (e instanceof CodeInterceptionException) {
                Toast.makeText(LoginActivity.this, "Intercepting the verification call automatically failed", Toast.LENGTH_LONG).show();
            } else if (e instanceof ServiceErrorException) {
                Toast.makeText(LoginActivity.this, "Sinch service error", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(LoginActivity.this, "Other system error, check your network state", Toast.LENGTH_LONG).show();
            }
        }
    }

    //test Ends

}


