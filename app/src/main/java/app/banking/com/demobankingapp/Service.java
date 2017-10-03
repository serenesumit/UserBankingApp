package app.banking.com.demobankingapp;
import android.util.Log;
import android.widget.Toast;

import com.android.internal.http.multipart.MultipartEntity;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.microsoft.azure.storage.CloudStorageAccount;
import com.microsoft.azure.storage.blob.CloudBlobClient;
import com.microsoft.azure.storage.blob.CloudBlobContainer;
import com.microsoft.azure.storage.blob.CloudBlockBlob;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.lang.reflect.Type;
import java.util.List;
import java.util.concurrent.ExecutionException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Sumit on 9/8/2017.
 */

 public   class Service {
    public static class CommonService{

        private static final String storageURL = "https://bankingapp.blob.core.windows.net";
        private static final String storageContainer = "videos";
        public static final String storageConnectionString = "DefaultEndpointsProtocol=https;"
                + "AccountName=bankingapp;"
                + "AccountKey=Zw+ji0DqFFbB7uf2m4yVEC4B3HeFx72yJ7gnpv9vbhZXtytG27WNQJ/p+siNGzpZXKZrgS5p95e3K8gAi12kzA==";

        public static String storeImageInBlobStorage(String videoPath){
            String blobUri = "";
            if(videoPath == null)
            {
                return blobUri;
            }
            try
            {
                // Retrieve storage account from connection-string.
                CloudStorageAccount storageAccount = CloudStorageAccount.parse(storageConnectionString);

                // Create the blob client.
                CloudBlobClient blobClient = storageAccount.createCloudBlobClient();

                // Retrieve reference to a previously created container.
                CloudBlobContainer container = blobClient.getContainerReference(storageContainer);
                String filename=videoPath.substring(videoPath.lastIndexOf("/")+1);
                // Create or overwrite the blob (with the name "example.jpeg") with contents from a local file.
                CloudBlockBlob blob = container.getBlockBlobReference(filename);
                blob.getProperties().setContentType("image/jpg");
                File source = new File(videoPath);
                blob.upload(new FileInputStream(source), source.length());
                blobUri =  blob.getUri().toString();

            }
            catch (Exception e)
            {
                // Output the stack trace.
                e.printStackTrace();
            }
         return blobUri ;
        }


        public static List<Admin> GetAdmins()
        {
            List<Admin> result =null;
            try
            {
                DefaultHttpClient httpClient = new DefaultHttpClient();
                HttpEntity httpEntity = null;
                HttpResponse httpResponse = null;
                HttpGet httpGet = new HttpGet("http://bankingappwebapi.azurewebsites.net/api/Admins");

                httpResponse = httpClient.execute(httpGet);
                httpEntity = httpResponse.getEntity();
                String response = EntityUtils.toString(httpEntity);

                Gson gson = new GsonBuilder().create();
                Type t = new TypeToken<List<Admin>>(){}.getType();
                 result = gson.fromJson(response,t);
            }
            catch (Exception e)
            {

            }
return result;
        }
    }
}
