package com.example.whatsappdbms;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

public class PersonalChatListAdapter extends ArrayAdapter<String> {
    Context context;
    private ArrayList<String> name=new ArrayList<String>();

    public PersonalChatListAdapter(Context c,ArrayList<String> name) {
        super(c, R.layout.private_chat_list, name);
        this.context = c;
        this.name=name;

    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater layoutInflater = (LayoutInflater) context.getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View row = layoutInflater.inflate(R.layout.private_chat_list, parent, false);

        TextView row_name = row.findViewById(R.id.person_name);
        ImageView imageView = row.findViewById(R.id.imageView);
        imageView.setImageResource(R.drawable.user);
        row_name.setText(name.get(position));

        return row;
    }
}
