package com.example.ferran.marvelcharacters;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;


/*
 *
 * Created by Ferran on 03/12/2016.
 *
 * This class is used to manage the fragment for events display on the second activity.
 *
 */
public class EventsFragment extends Fragment {

    Events mMyEvents = new Events();
    List<Item> mListItem = new ArrayList<>();
    RecyclerView recyclerView;
    View mContextView;

    public interface OnEventsFragmentInterface {
        Events setEventList();
    }

    private OnEventsFragmentInterface mEventsFragCallback;

    public EventsFragment() {
    }// Required empty public constructor

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            this.mEventsFragCallback = (OnEventsFragmentInterface) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(this.getActivity().toString() + " must implement Interface");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View fragView = inflater.inflate(R.layout.fragment_events, container, false);
        recyclerView = (RecyclerView) fragView.findViewById(R.id.recycler_view_events);
        mContextView = fragView;
        return fragView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mMyEvents = mEventsFragCallback.setEventList();

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(
                mContextView.getContext(),
                LinearLayoutManager.VERTICAL,
                false));

        List<Item> newItemList = new ArrayList<>();
        int i = 0;

        for (String names : mMyEvents.getEventArray()) {
            Item hero = new Item(mMyEvents.getImageBitmapArray().get(i), mMyEvents.getEventArray().get(i), mMyEvents.getDescriptionArray().get(i));
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
