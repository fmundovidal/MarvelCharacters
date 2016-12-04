package com.example.ferran.marvelcharacters;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import java.util.List;
import java.util.Random;

public class ComicsFragment extends Fragment {
    public interface OnComicsFragmentInterface {
        Character setComicList();
    }
    private OnComicsFragmentInterface mComicsFragCallback;

    public ComicsFragment() {
        // Required empty public constructor
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        try{
            this.mComicsFragCallback = (OnComicsFragmentInterface) context;
        }catch(ClassCastException e){
            throw new ClassCastException(this.getActivity().toString()+" must implement Interface");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View fragView = inflater.inflate(R.layout.fragment_comics,container,false);

        return fragView;
    }

    public long firstCallback(){
        return 0;
    }
}
