package com.example.ferran.marvelcharacters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

/**
 * Created by Ferran on 02/12/2016.
 */

public class MyListAdapterRecycler extends RecyclerView.Adapter<MyListAdapterRecycler.ViewHolder>{
    public class ViewHolder extends RecyclerView.ViewHolder
    {
        public ImageView iconImgView;
        public TextView titleTxtView;
        public TextView bodyTxtView;

        public ViewHolder(View itemView){
            super(itemView);

            this.iconImgView = (ImageView) itemView.findViewById(R.id.image_view_custom_row_layout);
            this.titleTxtView = (TextView) itemView.findViewById(R.id.text_view_tiltle);
            this.bodyTxtView = (TextView) itemView.findViewById(R.id.text_view_body);
        }
    }

    Context mContext;
    List<Item> mitemList;

    public  MyListAdapterRecycler(Context context, List<Item> listItem){
        this.mContext = context;
        this.mitemList = listItem;
    }

    //View viewRow;

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, final int viewType) {
        View viewRow = LayoutInflater.from(this.mContext).inflate(R.layout.list_view_custom_layout,parent,false);

        ViewHolder viewHolder = new ViewHolder(viewRow);

        viewRow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(mContext,"Clicked",Toast.LENGTH_SHORT).show();
            }
        });


        //RecyclerView.ViewHolder viewHolder = new RecyclerView.ViewHolder(){}
        return viewHolder;
    }
    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        holder.titleTxtView.setText(this.mitemList.get(position).getmTitle());
        holder.bodyTxtView.setText("ID: "+position);
       /* viewRow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(mContext,"Clicked "+position,Toast.LENGTH_SHORT).show();
            }
        });*/
    }

    @Override
    public int getItemCount() {
        return mitemList.size();
    }
}
