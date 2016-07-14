package soylente.com.trakrecord.Adaptor;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.SurfaceHolder;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import soylente.com.trakrecord.R;

/**
 * Created by sam on 2016-07-06.
 */
public class ImageAdapter extends BaseAdapter {
    private Context mContext;
    int notSelected = R.drawable.ic_menu_camera;
    int selected = R.drawable.ic_menu_send;
    Integer[] mThumbIds = {notSelected,
            notSelected,
            notSelected,
            notSelected,
            notSelected,
            notSelected,
            notSelected,
            notSelected,
            notSelected,
    };
    String[] mLabels = {"Camp 1",
            "Camp 2",
            "Camp 3",
            "Camp 4",
            "Camp 5",
            "Camp 6",
            "Camp 7",
            "Camp 8",
            "Camp 9",
    };
    private LayoutInflater inflater;

    public ImageAdapter(Context c) {
        mContext = c;
        inflater = (LayoutInflater) mContext
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }

    public int getCount() {
        return mThumbIds.length;
    }

    public Object getItem(int position) {
        return null;
    }

    public long getItemId(int position) {
        return 0;
    }

    public void updateImage(int position, int toggle) {
        switch (toggle) {
            case 0:
                mThumbIds[position] = notSelected;
                break;
            case 1:
                mThumbIds[position] = selected;
                break;
        }
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        View grid;

        if (convertView == null) {
            grid = inflater.inflate(R.layout.badge_list, null);
            TextView tv = (TextView) grid.findViewById(R.id.badge_label);
            tv.setText(mLabels[position]);
        } else {
            grid = convertView;
        }


        ImageView iv = (ImageView) grid.findViewById(R.id.badge_img);
        iv.setImageResource(mThumbIds[position]);

        return grid;
    }


}