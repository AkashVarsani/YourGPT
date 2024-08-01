package com.example.varsanigpt;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.varsanigpt.databinding.ChatSampleBinding;

import java.util.ArrayList;

public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.VwHolder> {

    Context context;
    ArrayList<ChatModel> list;

    public ChatAdapter(Context context, ArrayList<ChatModel> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public VwHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.chat_sample,parent, false);

        return new VwHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull VwHolder holder, int position) {

//        Typeface font = Typeface.createFromAsset(context.getAssets(), "Century Gothic.ttf");
        ChatModel model = list.get(position);

        if(model.getChatBy()=="u"){
            holder.binding.user.setText(model.getMsg());
            holder.binding.cpu.setVisibility(View.GONE);
            holder.binding.user.setVisibility(View.VISIBLE);
        }
        else{

            holder.binding.cpu.setText(model.getMsg());
            holder.binding.user.setVisibility(View.GONE);
            holder.binding.cpu.setVisibility(View.VISIBLE);
        }


    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class VwHolder extends RecyclerView.ViewHolder {

        ChatSampleBinding binding;

        public VwHolder(@NonNull View itemView) {
            super(itemView);

            binding = ChatSampleBinding.bind(itemView);

        }
    }

}
