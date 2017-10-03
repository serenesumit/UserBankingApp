package app.banking.com.demobankingapp;

import android.app.Application;
import android.util.Base64;
import com.google.gson.Gson;
import java.io.IOException;
import java.util.concurrent.TimeUnit;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import okio.Timeout;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
public class BankingApp extends Application {
    private static BankingApp mInstance;
    private static Retrofit retrofit;

    @Override
    public void onCreate() {
        super.onCreate();
        this.mInstance = this;
    }

    public synchronized AppApi getApi() {
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder()
                .connectTimeout(120, TimeUnit.SECONDS);

        //log level
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        httpClient.addInterceptor(interceptor);

        httpClient.addInterceptor(new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request original = chain.request();
                // Request customization: add request headers
                Request.Builder requestBuilder = original.newBuilder()
                        .addHeader("Content-Type", "application/x-www-form-urlencoded");
                       // .addHeader("Authorization", basic);
                Request request = requestBuilder.build();
                return chain.proceed(request);
            }
        });

        httpClient.interceptors().add(new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {

                Request request = chain.request();
                Response response = chain.proceed(request);
                response.code();
                return response;
            }
        });
        OkHttpClient client = httpClient
                .addInterceptor(interceptor).build();
//         OkHttpClient okHttpClient = new OkHttpClient.Builder()
//                .readTimeout(60, TimeUnit.SECONDS)
//                .connectTimeout(60, TimeUnit.SECONDS)
//                .addInterceptor(client)
//                .build();

        retrofit = new Retrofit.Builder()

                .baseUrl(AppApi.API)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create(new Gson()))
                .build();
        AppApi github = retrofit.create(AppApi.class);
        return github;
    }

    public static synchronized BankingApp getInstance() {
        return mInstance;
    }

    public synchronized Retrofit getRetrofit() {
        return retrofit;
    }
}
