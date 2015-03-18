
package com.projectyr4x00091174.carl.traingain;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by carl on 21/02/2015.
*/
 public class TrainingProgramAdapter extends ArrayAdapter {

    private Context context;
    private boolean useList = true;

    public TrainingProgramAdapter(Context context, List<SportProgram> items) {
        super(context, android.R.layout.simple_list_item_1, items);
        this.context = context;
    }

    /**
     * Holder for the list items.
     */
    private class ViewHolder{
        TextView titleText;
    }

    /**
     *
     * @param position
     * @param convertView
     * @param parent
     * @return
     */
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        SportProgram item = (SportProgram)getItem(position);
        View viewToUse = null;

        // This block exists to inflate the settings list item conditionally based on whether
        // we want to support a grid or list view.
        LayoutInflater mInflater = (LayoutInflater) context
                .getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        if (convertView == null) {
            if(useList){
                viewToUse = mInflater.inflate(R.layout.example_list_item, null);
            } else {
                viewToUse = mInflater.inflate(R.layout.example_grid_item, null);
            }

            holder = new ViewHolder();
            holder.titleText = (TextView)viewToUse.findViewById(R.id.titleTextView);
            viewToUse.setTag(holder);
        } else {
            viewToUse = convertView;
            holder = (ViewHolder) viewToUse.getTag();
        }

       // holder.titleText.setText(item.getTitle());
        return viewToUse;
    }
}