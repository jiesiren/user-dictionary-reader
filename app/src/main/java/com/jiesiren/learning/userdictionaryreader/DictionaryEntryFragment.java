package com.jiesiren.learning.userdictionaryreader;

import android.content.ContentResolver;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.UserDictionary;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.TextView;

import java.util.LinkedList;

public class DictionaryEntryFragment extends Fragment {

    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    //private static final String ARG_PARAM1 = "param1";

    //private String mParam1;

    /**
     * The fragment's ListView/GridView.
     */
    private AbsListView mListView;

    /**
     * The Adapter which will be used to populate the ListView/GridView with
     * Views.
     */
    private ListAdapter mAdapter;

    public static DictionaryEntryFragment newInstance() {
        DictionaryEntryFragment fragment = new DictionaryEntryFragment();
        Bundle args = new Bundle();
        // args.putString(ARG_PARAM1, param1);
        fragment.setArguments(args);
        return fragment;
    }

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public DictionaryEntryFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

//        if (getArguments() != null) {
//            mParam1 = getArguments().getString(ARG_PARAM1);
//        }

        updateEntries();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_dictionaryentry, container, false);

        // Set the adapter
        mListView = (AbsListView) view.findViewById(R.id.listview_dictionary_entry);
        mListView.setAdapter(mAdapter);

        mListView.setEmptyView(view.findViewById(R.id.listview_dictionary_entry_empty));

        setEmptyText("No entries!");

        return view;
    }


    public void updateEntries() {
        ContentResolver resolver = getActivity().getContentResolver();

        Cursor cursor = resolver.query(UserDictionary.Words.CONTENT_URI, null, null, null, null);

        LinkedList<String> entries = new LinkedList<String>();

        Log.e("TEST", Integer.toString(cursor.getCount()));

        while (cursor.moveToNext()) {
            String word = cursor.getString(cursor.getColumnIndex("word"));
            entries.add(word);
        }

        mAdapter = new ArrayAdapter<String>(getActivity(),
                R.layout.dictionary_entry, R.id.dictionary_entry_textview, entries);
    }

    public void setEmptyText(CharSequence emptyText) {
        View emptyView = mListView.getEmptyView();

        if (emptyView instanceof TextView) {
            ((TextView) emptyView).setText(emptyText);
        }
    }
}
