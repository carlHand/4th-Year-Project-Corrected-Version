package com.projectyr4x00091174.carl.traingain;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import com.socialize.entity.Entity;
import com.socialize.ui.SocializeEntityLoader;

/**
 * Created by carl on 05/04/2015.
 */
public class EntityLoader implements SocializeEntityLoader {

    // MUST define a parameterless constructor
    public EntityLoader() {
        super();
    }

    @Override
    public void loadEntity(Activity activity, Entity entity) {
        // This is where you would load an Activity within your app to render the entity
        //Intent intent = new Intent(activity, HomePage.class);
       // Toast.makeText(activity.getBaseContext(),"TEST ENTITY: " + entity.getKey(), Toast.LENGTH_LONG).show();
        // Add the key from the entity
        //intent.putExtra("programPush", entity.getKey());
        //activity.startActivity(intent);
    }

    @Override
    public boolean canLoad(Context context, Entity entity) {
        // Return true if this entity can be loaded
        return true;
    }
}