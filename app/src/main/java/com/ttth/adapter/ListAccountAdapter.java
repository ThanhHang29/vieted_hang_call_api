package com.ttth.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ttth.account.MyAccount;
import com.ttth.example.R;

import java.util.ArrayList;

/**
 * Created by Thanh Hang on 21/01/17.
 */

public class ListAccountAdapter extends RecyclerView.Adapter<ListAccountAdapter.ViewHolder> {
    private ArrayList<MyAccount>arrAccoounts;

    public ListAccountAdapter(ArrayList<MyAccount> arrAccoounts) {
        this.arrAccoounts = arrAccoounts;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.item_list,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        MyAccount account = arrAccoounts.get(position);
        holder.tvText.setText("Name :"+account.getName()+"\nPhone :"+account.getPhone()+
        "\nEmail :"+account.getEmail());

    }

    @Override
    public int getItemCount() {
        if (arrAccoounts != null){
            return arrAccoounts.size();
        }
        return 0;
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tvText;
        public ViewHolder(View itemView) {
            super(itemView);
            tvText = (TextView) itemView.findViewById(R.id.tvText);
        }
    }
}
