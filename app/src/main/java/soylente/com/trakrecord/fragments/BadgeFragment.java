package soylente.com.trakrecord.fragments;


import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import soylente.com.trakrecord.Adaptor.ImageAdapter;
import soylente.com.trakrecord.R;

public class BadgeFragment extends Fragment {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView (LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_badge,container,false);
        GridView gridView = (GridView) view.findViewById(R.id.badgeGrid);
        gridView.setAdapter(new ImageAdapter(view.getContext())); // uses the view to get the context instead of getActivity().
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }
}