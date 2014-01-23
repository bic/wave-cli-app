package com.example.restaurantapp;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView.FindListener;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.restaurantapp.TabContent.Course;

public class ProposalFragment extends Fragment {
    int idx;

    /**
     * Create a new instance of CountingFragment, providing "num"
     * as an argument.
     * 
     */
   // private static FragmentManager myFragmentManager; 
    
    static ProposalFragment newInstance(int num) {
        ProposalFragment f = new ProposalFragment();

        // Supply num input as an argument.
        //Bundle args = new Bundle();
        //args.putInt("id", num);
        //f.setArguments(args);

        return f;
    }

    /**
     * When creating, retrieve this instance's number from its arguments.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        idx = getArguments() != null ? getArguments().getInt("id") : 1;
        //myFragmentManager = this.getFragmentManager();
        
    }

    /**
     * The Fragment's UI is just a simple text view showing its
     * instance number.
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.tab_fragment_layout, container, false);
        View tv = v.findViewById(R.id.set_delivery_hour);
        ((TextView)tv).setText("14:40");
       
        //tv.setBackgroundDrawable(getResources().getDrawable(android.R.drawable.gallery_thumb));
       
        String[] locations = new String[] {"Charles de Gaulle", "Piata Victorieri", "Microsoft A"};
        Spinner sp = (Spinner) v.findViewById(R.id.location_spinner);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, locations);
        adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        sp.setAdapter(adapter);
        
      
        
        return v;
        
        
    	//return null;
    }
   
   
}

