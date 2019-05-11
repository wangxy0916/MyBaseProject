package com.fastgo.sydialoglib.manager;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.fastgo.driver.dialog.sydialoglib.R;

import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {

    private List<String> list;
    public int selected;
    private Context context;

    public MyAdapter(Context context, List<String> list, int selected) {
        this.context = context;
        this.list = list;
        this.selected = selected;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.item,viewGroup,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, final int i) {
        viewHolder.tv.setText(list.get(i));
        if (i == selected){
            viewHolder.iv.setVisibility(View.VISIBLE);
        }else{
            viewHolder.iv.setVisibility(View.GONE);
        }
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("---smartwang","点击dialog的子项："+i);
                selected = i;
                notifyDataSetChanged();
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder{
        private TextView tv;
        private ImageView iv;

        public ViewHolder(View itemView) {
            super(itemView);
            tv = itemView.findViewById(R.id.item_tv);
            iv = itemView.findViewById(R.id.item_iv);
        }
    }
}
