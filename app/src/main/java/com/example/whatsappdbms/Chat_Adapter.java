package com.example.whatsappdbms;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

public class Chat_Adapter extends ArrayAdapter<String> {
    Context context;
    private ArrayList<String> name=new ArrayList<String>();
    private ArrayList<String> message=new ArrayList<String>();

    public Chat_Adapter(Context c,ArrayList<String> name,ArrayList<String> message) {
        super(c, R.layout.private_chat_list, name);
        this.context = c;
        this.name=name;
        this.message=message;

    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater layoutInflater = (LayoutInflater) context.getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View row = layoutInflater.inflate(R.layout.message_view, parent, false);

        TextView sender = row.findViewById(R.id.message_sender);
        sender.setText(name.get(position));
        TextView messageText = row.findViewById(R.id.message);
        messageText.setText(message.get(position));
        return row;
    }
}
