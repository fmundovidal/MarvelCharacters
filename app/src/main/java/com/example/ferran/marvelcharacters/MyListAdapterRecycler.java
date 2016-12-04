package com.example.ferran.marvelcharacters;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;

/**
 * Created by Ferran on 02/12/2016.
 */

public class MyListAdapterRecycler extends RecyclerView.Adapter<MyListAdapterRecycler.ViewHolder> {

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
    Character mHero;
    boolean mIsActivityMain;

    public int mPosition;

    public  MyListAdapterRecycler(Context context, List<Item> listItem, Character hero,boolean isActivityMain){
        this.mContext = context;
        this.mitemList = listItem;
        this.mHero = hero;
        this.mIsActivityMain = isActivityMain;
    }

    public  MyListAdapterRecycler(Context context, List<Item> listItem,boolean isActivityMain){
        this.mContext = context;
        this.mitemList = listItem;
        this.mIsActivityMain=isActivityMain;
    }


    //View viewRow;

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, final int viewType) {
        View viewRow = LayoutInflater.from(this.mContext).inflate(R.layout.list_view_custom_layout,parent,false);

        ViewHolder viewHolder = new ViewHolder(viewRow);

       /* viewRow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


               Toast.makeText(mContext,"Clicked " ,Toast.LENGTH_SHORT).show();


            }

        });*/

        //RecyclerView.ViewHolder viewHolder = new RecyclerView.ViewHolder(){}
        return viewHolder;
    }
    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        holder.titleTxtView.setText(this.mitemList.get(position).getmTitle());
        holder.bodyTxtView.setText(this.mitemList.get(position).getmBody());
        holder.iconImgView.setImageBitmap(this.mitemList.get(position).getmImage());


    }

    @Override
    public int getItemCount() {
        return mitemList.size();
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position, List<Object> payloads) {
        super.onBindViewHolder(holder, position, payloads);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mIsActivityMain) {
                    Toast.makeText(mContext, "Accessing to " + String.valueOf(mHero.getCharacterArray().get(position)), Toast.LENGTH_SHORT).show();
                    //Log.i("TAG","selectedChar: "+mHero.getCharacterArray().get(position));

                    Intent intentCharInfo = new Intent(mContext, SecondActivity.class);
                    intentCharInfo.setFlags(FLAG_ACTIVITY_NEW_TASK);
                    intentCharInfo.putExtra("CHAR_ID", mHero.getIdArray().get(position));
                    intentCharInfo.putExtra("CHAR_NAME", mHero.getCharacterArray().get(position));
                    intentCharInfo.putExtra("CHAR_DESCRIPTION", mHero.getDescriptionArray().get(position));
                    //intentCharInfo.putExtra("CHAR_IMAGE",mHero.getBitmapArray().get(position));
                    intentCharInfo.putExtra("CHAR_IMAGE", mHero.getImageArray().get(position));
                    intentCharInfo.putExtra("CHAR_WIKI_URL", mHero.getWikiList().get(position));
                    intentCharInfo.putExtra("CHAR_DETAIL_URL", mHero.getDetailList().get(position));
                    intentCharInfo.putExtra("CHAR_COMIC_URL", mHero.getComicList().get(position));

                    mContext.startActivity(intentCharInfo);
                }
            }

        });
    }
}
