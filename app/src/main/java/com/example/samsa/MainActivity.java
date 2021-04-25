package com.example.samsa;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    EditText idField;
    Button getBtn;
    TextView output;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        idField = findViewById(R.id.idField);
        getBtn = findViewById(R.id.getBtn);
        output = findViewById(R.id.output);

        getBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MyAsyncTask myAsyncTask = new MyAsyncTask();
                myAsyncTask.id = Integer.parseInt(idField.getText().toString());
                myAsyncTask.execute("");
            }
        });
    }

    class MyAsyncTask extends AsyncTask<String, String, String> {

        int id;
        User user;

        @Override
        protected String doInBackground(String... strings) {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl("http://10.0.2.2:8080")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            UserService service = retrofit.create(UserService.class);
            Call<User> userCall = service.getUserById("user", id);

            try {
                Response<User> userResponse = userCall.execute();
                user = userResponse.body();
                Log.d("MyHttpClient", user.toString());

            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            if (user != null)
                output.setText(user.getFullName());
        }
    }
}