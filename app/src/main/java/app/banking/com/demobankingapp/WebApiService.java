package app.banking.com.demobankingapp;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;
import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

class WebApiService extends AsyncTask<Void, Void, String> {

    private Exception exception;

    protected String doInBackground(Void... urls) {
        String apiUrl = "http://bankingappwebapi.azurewebsites.net/api/Admins";
        // Do some validation here

        try {
            URL url = new URL(apiUrl);
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            try {
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
                StringBuilder stringBuilder = new StringBuilder();
                String line;
                while ((line = bufferedReader.readLine()) != null) {
                    stringBuilder.append(line).append("\n");
                }
                bufferedReader.close();
                return stringBuilder.toString();
            }
            finally{
                urlConnection.disconnect();
            }
        }
        catch(Exception e) {
            Log.e("ERROR", e.getMessage(), e);
            return null;
        }
    }

    protected void onPostExecute(String response) {
        if(response == null) {
            response = "THERE WAS AN ERROR";
        }
        try
        {

            JSONObject jsonObj = new JSONObject("{\"FirstName\":\"LastName\",\"PhoneNumber\":\"DateCreated\"}");
            Log.d("My App", response.toString());
           // JSONObject admins = new JSONObject(response.());
           // JSONArray data = admins.getJSONArray("data");
          //  for (int i = 0; i < data.length(); i++) {
             //   JSONObject phoneNumber = data.getJSONObject(i).getString("");
          //  }
        }
        catch (Exception e)
        {

        }


    }
}