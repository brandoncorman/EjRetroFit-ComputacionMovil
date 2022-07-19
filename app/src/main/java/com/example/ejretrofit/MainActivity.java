package com.example.ejretrofit;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.example.ejretrofit.Interface.JsonPlaceHolder;
import com.example.ejretrofit.Model.Posts;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private TextView mJsonTxtView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mJsonTxtView = findViewById(R.id.jsonText);
        getPosts();
    }

    private void getPosts(){

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://jsonplaceholder.typicode.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        JsonPlaceHolder jsonPlaceHolder = retrofit.create(JsonPlaceHolder.class);

        Call<List<Posts>> call = jsonPlaceHolder.getPosts();
        call.enqueue(new Callback<List<Posts>>() {
            @Override
            public void onResponse(Call<List<Posts>> call, Response<List<Posts>> response) {

                if (!response.isSuccessful()) {

                    mJsonTxtView.setText("Código respuesta: " + response.code());
                    return;
                }

                List<Posts> listPosts = response.body();

                for(Posts posts: listPosts) {

                    String content = "";
                    content += "Id de usuario: " + posts.getUserId() + "\n\n";
                    content += "Id del post: " + posts.getId() + "\n\n";
                    content += "título: " + posts.getTitle() + "\n\n";
                    content += "Post: " + posts.getBody() + "\n\n\n";
                    mJsonTxtView.append(content);
                }

            }
            @Override
            public void onFailure(Call<List<Posts>> call, Throwable t) {
                    mJsonTxtView.setText(t.getMessage());
            }
        });
    }
}