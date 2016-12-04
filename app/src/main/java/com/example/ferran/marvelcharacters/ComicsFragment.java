package com.example.ferran.marvelcharacters;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ComicsFragment extends Fragment {

    Comics mMyComics = new Comics();
    List<Item> mListItem = new ArrayList<>();
    RecyclerView recyclerView;

    View mContextView;

    public interface OnComicsFragmentInterface {
        Comics setComicList();
    }

    private OnComicsFragmentInterface mComicsFragCallback;

    public ComicsFragment() {
        // Required empty public constructor
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        try {
            this.mComicsFragCallback = (OnComicsFragmentInterface) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(this.getActivity().toString() + " must implement Interface");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View fragView = inflater.inflate(R.layout.fragment_comics, container, false);
        recyclerView = (RecyclerView) fragView.findViewById(R.id.recycler_view_comics);

        mContextView=fragView;



        /*MyListAdapterRecycler myListAdapterRecycler = new MyListAdapterRecycler(fragView.getContext(), mListItem);
        recyclerView.setAdapter(myListAdapterRecycler);*/





        return fragView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mMyComics = mComicsFragCallback.setComicList();
        Log.i("TAG", "mMyComics titles: " + mMyComics.getComicArray());

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager( //cambiando el elemento permite definir si es linear layout, un grid, un staggeredgridlayout, etc...
                mContextView.getContext(),
                LinearLayoutManager.VERTICAL,
                false)); //para que sea circular o no (false = no)

        List<Item> newItemList = new ArrayList<>();
        int i = 0;

        Log.i("TAG","ComicArray Size"+String.valueOf(mMyComics.getComicArray().size()));
        Log.i("TAG","ImageBitmapArray Size"+String.valueOf(mMyComics.getImageBitmapArray().size()));
        Log.i("TAG","getDescriptionArray Size"+String.valueOf(mMyComics.getDescriptionArray().size()));
        for (String names : mMyComics.getComicArray()) {
            Item hero = new Item(mMyComics.getImageBitmapArray().get(i), mMyComics.getComicArray().get(i), mMyComics.getDescriptionArray().get(i));
            i++;
            newItemList.add(hero);
        }

        MyListAdapterRecycler myListAdapterRecycler = new MyListAdapterRecycler(mContextView.getContext(), newItemList,false);
        recyclerView.setAdapter(myListAdapterRecycler);

    }

    public long firstCallback() {
        return 0;
    }
}
