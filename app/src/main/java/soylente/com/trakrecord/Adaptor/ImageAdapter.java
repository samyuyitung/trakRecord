package soylente.com.trakrecord.Adaptor;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import java.util.ArrayList;

import soylente.com.trakrecord.R;

/**
 * Created by sam on 2016-07-06.
 */
public class ImageAdapter extends BaseAdapter {
    private Context mContext;
    int notSelected = R.drawable.ic_media_pause;
    int selected = R.drawable.ic_media_play;
    Integer[] mThumbIds = { notSelected,
                                    notSelected,
                                    notSelected,
                                    notSelected,
                                    notSelected,
                                    notSelected,
                                    notSelected,
                                    notSelected,
                                    notSelected,
    };
    private ArrayList<ImageView> mImageViewArrayList = new ArrayList<>(mThumbIds.length);


    public ImageAdapter(Context c) {
        mContext = c;
    }

    public int getCount() {
        return mThumbIds.length;
    }

    public ImageView getItem(int position) {
        return mImageViewArrayList.get(position);
    }

    public long getItemId(int position) {
        return 0;
    }

    public void updateImage(int position, int toggle){
        switch (toggle){
            case 0:
                mThumbIds[position] = notSelected;
                break;
            case 1:
                mThumbIds[position] = selected;
                break;
        }
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        ImageView imageView;
        if (convertView == null) {  // if it's not recycled, initialize some attributes
            imageView = new ImageView(mContext);
            imageView.setLayoutParams(new GridView.LayoutParams(200, 200));
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            imageView.setPadding(8, 8, 8, 8);
        } else {
            imageView = (ImageView) convertView;
        }
        imageView.setImageResource(mThumbIds[position]);

        return imageView;
    }
}