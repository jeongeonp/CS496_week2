package com.example.q.trialtwo;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by user on 2017-12-27.
 */

public class GalleryList {

    private Context context;
    private int w, h, nc;
    public Bitmap bitmap;

    public GalleryList(Context context, int w, int h, int numColumns) {
        this.context = context;
        this.w = w;
        this.h = h;
        this.nc = numColumns;
    }

    public GridViewAdapter getGallaryList() {

        //Uri uri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
        Uri uri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;

        String[] projection = new String[] {
                MediaStore.Images.Media.DATA };

        String[] selectionArgs = null;

        String sortOrder = null;

        // Send query to get contacts
        Cursor imageCursor = context.getContentResolver().query(uri, projection, null,
                selectionArgs, sortOrder);


        // Generate contacts list into array list
        ArrayList<Uri> imageUriList = new ArrayList<Uri>();

        if (imageCursor.moveToFirst()) {
            do {
                String path = imageCursor.getString(0);
                Uri imageUri = Uri.parse(path);

                imageUriList.add(imageUri);
            } while (imageCursor.moveToNext());
        }
        Cursor cursor = context.getContentResolver().query(uri, projection, null,
                null,null);

        cursor.moveToFirst();
        int columnIndex = cursor.getColumnIndex(projection[0]);
        String filePath = cursor.getString(columnIndex);
        bitmap = BitmapFactory.decodeFile(filePath);

        return new GridViewAdapter(imageUriList);

    }

    public class GridViewAdapter extends BaseAdapter {
        private Context context;
        private ArrayList<Uri> gridViewItemList;
        private ViewHolder viewHolder;

        class ViewHolder {
            ImageView imageView;
        }

        public GridViewAdapter() {
            gridViewItemList = new ArrayList<Uri>();
        }

        public GridViewAdapter(List<Uri> list) {
            gridViewItemList = new ArrayList<Uri>();
            for(Uri elem : list) {
                addItem(elem);
            }
        }

        @Override
        public int getCount() {
            return gridViewItemList.size();
        }

        @Override
        public Object getItem(int i) {
            return gridViewItemList.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            context = parent.getContext();

            if (convertView == null) {
                //LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                //convertView = inflater.inflate(R.layout.gridview_item, parent, false);
                ImageView view = new ImageView(context);
                int length = (w - 20*(nc+1))/nc;
                view.setLayoutParams(new GridView.LayoutParams(length, length));
                view.setScaleType(ImageView.ScaleType.CENTER_CROP);

                convertView = view;
                viewHolder = new ViewHolder();
                //viewHolder.imageView = (ImageView) convertView.findViewById(R.id.imageView);
                viewHolder.imageView = view;
                convertView.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) convertView.getTag();
            }

            Uri gridViewItem = gridViewItemList.get(position);
            viewHolder.imageView.setImageURI(gridViewItem);

            return convertView;

        }

        public void addItem(Uri img) {
            gridViewItemList.add(img);
        }

    }

}
