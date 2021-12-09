package com.wposs.retrofitexercise.retrofit;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.wposs.retrofitexercise.R;
import com.wposs.retrofitexercise.retrofit.interfaces.JsonPlaceHolderApi;
import com.wposs.retrofitexercise.retrofit.model.Posts;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    TextView tvJson;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initUi();
        getPosts();
    }

    private void initUi() {

        tvJson = findViewById(R.id.tvJson);
    }

    private void getPosts() {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://jsonplaceholder.typicode.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        JsonPlaceHolderApi jsonPlaceHolderApi = retrofit.create(JsonPlaceHolderApi.class);
        Call<List<Posts>> call = jsonPlaceHolderApi.getPosts();

        call.enqueue(new Callback<List<Posts>>() {
            @Override
            public void onResponse(@NonNull Call<List<Posts>> call, @NonNull Response<List<Posts>> response) {

                if (!response.isSuccessful()) {
                    StringBuilder codeError = new StringBuilder();
                    tvJson.setText(codeError.append("Codigo : ").append(response.code()).toString());
                    return;
                }

                List<Posts> postsList = response.body();

                assert postsList != null;
                for (Posts posts : postsList) {

                    String content = "";
                    content += "userId: " + posts.getUserId() + "\n";
                    content += "id: " + posts.getId() + "\n";
                    content += "title: " + posts.getTitle() + "\n";
                    content += "body: " + posts.getBody() + "\n\n";

                    tvJson.append((content));
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<Posts>> call, @NonNull Throwable t) {

                tvJson.setText(t.getMessage());
            }
        });
    }


}