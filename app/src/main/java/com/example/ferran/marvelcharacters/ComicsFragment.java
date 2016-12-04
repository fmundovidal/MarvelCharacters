package com.example.ferran.marvelcharacters;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import java.util.ArrayList;
import java.util.List;

/*
 *
 * Created by Ferran on 03/12/2016.
 *
 * This class is used to manage the fragment for comics display on the second activity.
 *
 */
public class ComicsFragment extends Fragment {

    Comics mMyComics = new Comics();
    List<Item> mListItem = new ArrayList<>();
    RecyclerView recyclerView;
    View mContextView;

    public interface OnComicsFragmentInterface {Comics setComicList();}

    private OnComicsFragmentInterface mComicsFragCallback;

    public ComicsFragment(){}// Required empty public constructor


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
        mContextView = fragView;
        return fragView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mMyComics = mComicsFragCallback.setComicList();

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(
                mContextView.getContext(),
                LinearLayoutManager.VERTICAL,
                false));

        List<Item> newItemList = new ArrayList<>();
        int i = 0;
        for (String names : mMyComics.getComicArray()) {
            Item hero = new Item(mMyComics.getImageBitmapArray().get(i), mMyComics.getComicArray().get(i), mMyComics.getDescriptionArray().get(i));
            i++;
            newItemList.add(hero);
        }

        MyListAdapterRecycler myListAdapterRecycler = new MyListAdapterRecycler(mContextView.getContext(), newItemList, false);
        recyclerView.setAdapter(myListAdapterRecycler);
    }
    public long firstCallback() {
        return 0;
    }
}
