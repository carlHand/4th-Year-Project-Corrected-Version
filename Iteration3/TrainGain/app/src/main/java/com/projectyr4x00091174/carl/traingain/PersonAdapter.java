package com.projectyr4x00091174.carl.traingain;

import android.content.Context;
import android.widget.ArrayAdapter;

/**
 * Created by carl on 11/11/2014.
 */
public class PersonAdapter extends ArrayAdapter<Person> {
    Context mContext;
    /**
     * Adapter View layout
     */
    int mLayoutResourceId;

    public PersonAdapter(Context context, int layoutResourceId) {
        super(context, layoutResourceId);
        mContext = context;
        mLayoutResourceId = layoutResourceId;
    }
    /**
     * Returns the view for a specific item on the list
     */
    /*
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        final Person currentItem = getItem(position);
        if (row == null) {
            LayoutInflater inflater = ((Activity) mContext).getLayoutInflater();
            row = inflater.inflate(mLayoutResourceId, parent, false);
        }
        row.setTag(currentItem);
        final CheckBox checkBox = (CheckBox) row.findViewById(R.id.checkToDoItem);
        checkBox.setText(currentItem.getText());
        checkBox.setChecked(false);
        checkBox.setEnabled(true);
        checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                if (checkBox.isChecked()) {
                    checkBox.setEnabled(false);
                    if (mContext instanceof LoginActivity) {
                        LoginActivity activity = (LoginActivity) mContext;
                        activity.checkItem(currentItem);
                    }
                }
            }
        });
        return row;
    }
    */
}