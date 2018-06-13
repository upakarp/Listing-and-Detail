package com.example.android.listingdetail.Retrofit;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.android.listingdetail.R;

import java.util.List;

/**
 * Created by ajay on 11/29/17.
 */

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.MyViewHolder>{

    private List<Test> tests;

    public RecyclerAdapter(List<Test> tests){
        this.tests = tests;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_item, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.title.setText(tests.get(position).getTitle());
        holder.body.setText(tests.get(position).getBody());
    }

    @Override
    public int getItemCount() {
        return tests.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{

        TextView title, body;

        public MyViewHolder(View itemView) {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.title);
            body = (TextView) itemView.findViewById(R.id.body);
        }
    }
}