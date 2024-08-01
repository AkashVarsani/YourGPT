package com.example.varsanigpt;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.varsanigpt.databinding.ActivityMainBinding;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;
    Context context;
    ArrayList<ChatModel> list;
    ChatAdapter adapter;
    public static final MediaType JSON = MediaType.get("application/json");

    OkHttpClient client = new OkHttpClient();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        list = new ArrayList<>();

        adapter = new ChatAdapter(getApplicationContext(), list);
        LinearLayoutManager manager = new LinearLayoutManager(getApplicationContext());
        manager.setStackFromEnd(true);
        binding.rv.setAdapter(adapter);
        binding.rv.setLayoutManager(manager);

        addResponse("Welcome to VARSANI GPT...\nWrite what you are thinking...");
        binding.send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String msg = binding.editTextText.getText().toString();
                addToChat(msg, "u");
                binding.editTextText.setText("");
                callAPI(msg);
                addResponse("...");
            }
        });
    }

    void addResponse(String response){
        addToChat(response, "b");
    }
    void addToChat(String msg, String by){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                list.add(new ChatModel(msg,by));
                adapter.notifyDataSetChanged();
                binding.rv.smoothScrollToPosition(adapter.getItemCount());
            }
        });
    }


    void callAPI(String que){

        JSONObject object = new JSONObject();
        try {
            object.put("model","gpt-3.5-turbo-instruct");
            object.put("prompt", que);
            object.put("max_tokens",4000);
            object.put("temperature",0);
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }

        RequestBody body = RequestBody.create(object.toString(), JSON);
        Request request = new Request.Builder()
                .url("https://api.openai.com/v1/completions")
                .header("Authorization", "Bearer //ApiKey//")
                .post(body)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                list.remove(list.size()-1);
                addResponse("Failed to give response to your question...");
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
              try {
                    JSONObject  object1 = new JSONObject(response.body().string());
                    JSONArray array = object1.getJSONArray("choices");
                    String ss = array.getJSONObject(0).getString("text");
                    Log.d("asasas", "On Response....121...*********::::\n"+ss.trim());
                    list.remove(list.size()-1);
                    addResponse(ss.trim());
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }
            }
        });



    }

}