package com.projectyr4x00091174.carl.traingain;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by carl on 14/11/2014.
 */
public class MyHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "DBtraingain.db";
    private static final String TABLE_NAME = "Person";
    private static final String UID = "userId";
    private static final String NAME = "name";
    private static final String EMAIL = "email";
    private static final String PASSWORD = "password";
    private static final String DOB = "dob";
    private static final String WEIGHT = "weight";
    private static final String HEIGHT = "height";
    private static final String SPORT = "SPORT";
    private static final String GENDER = "gender";
    private static final String ACTIVITY_LEVEL = "activityLevel";
    private static final String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + " (" + UID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + NAME + " VARCHAR(255), " + EMAIL + " VARCHAR(255), " + PASSWORD + " VARCHAR(255), " +
                                            DOB + " VARCHAR(255), " + WEIGHT + " DOUBLE, " + HEIGHT + " DOUBLE, " + SPORT + " VARCHAR(255), " + GENDER + " VARCHAR(255), " + ACTIVITY_LEVEL + " VARCHAR(255));";
    private static final String DROP_TABLE = "DROP TABLE IF EXISTS " + TABLE_NAME;
    private Context context;
    Message m;
    private static final int DATABASE_VERSION = 11; //NEED TO INCREMENT THIS NUMBER EACH TIME YOU CHANGE THE DATABASE CREATE_TABLE VARIABLE IF YOU WANT THE ONUPGRADE METHOD BELOW TO BE CALLED

    MyHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        //this.context = context;
//        m.message(context, "Constructor called");
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        try {
            db.execSQL(CREATE_TABLE);
//            m.message(context, "Constructor called");
        } catch (SQLException e) {
  //          m.message(context, "" + e);
        }
    }
/*
    //use this method when you change the database table
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        try {
            db.execSQL(DROP_TABLE);
            onCreate(db);
        } catch (SQLException e) {
            m.message(context, "" + e);
        }
    }
*/
    public void recreateDb(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        try{
            System.out.println("UPGRADE DB oldVersion="+oldVersion+" - newVersion="+newVersion);
           recreateDb(sqLiteDatabase);
            if (oldVersion<10){
                sqLiteDatabase.execSQL(CREATE_TABLE);
            }
        }
        catch (Exception e){e.printStackTrace();}
    }

    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // super.onDowngrade(db, oldVersion, newVersion);
        System.out.println("DOWNGRADE DB oldVersion="+oldVersion+" - newVersion="+newVersion);
    }


    public Person insertUser(Person queryValues)
    {
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("name", queryValues.getName());
        values.put("email", queryValues.email);
        values.put("password", queryValues.password);
        values.put("dob", queryValues.getAge());
        values.put("weight", queryValues.getWeight());
        values.put("height", queryValues.getHeight());
        values.put("sport", queryValues.getSport());
        values.put("gender", queryValues.getGender());
        values.put("activityLevel", queryValues.getActivityLevel());

        queryValues.userId = database.insert("Person", null, values);
        database.close();
        return queryValues;
    }

    public int updateUserPassword (Person queryValues){
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("name", queryValues.getName());
        values.put("email", queryValues.email);
        values.put("password", queryValues.password);
        values.put("dob", queryValues.getAge());
        values.put("weight", queryValues.getWeight());
        values.put("height", queryValues.getHeight());
        values.put("sport", queryValues.getSport());
        values.put("gender", queryValues.getGender());
        values.put("activityLevel", queryValues.getActivityLevel());
        queryValues.userId = database.insert("Person", null, values);
        database.close();
        return database.update("Person", values, "userId = ?", new String[] {String.valueOf(queryValues.userId)});
    }

    public Person getUser (String email) {
        String query = "Select userId, name, password, dob, weight, height, sport, gender, activityLevel from Person where email ='" + email + "'";
        Person myUser = new Person(0, "",email,"", "", 0.0, 0.0, "", "", "");
        //Person myUser = new Person();
        SQLiteDatabase database = this.getReadableDatabase();
        Cursor cursor = database.rawQuery(query, null);
        /*
        if (cursor.moveToFirst()){
            do {
                myUser.userId=cursor.getLong(0);
                myUser.password=cursor.getString(1);
            } while (cursor.moveToNext());
        }
        return myUser;
        */
        if (cursor.moveToFirst()) {
            do {
                myUser.setUserId(cursor.getLong(0));
                myUser.setName(cursor.getString(1));
                myUser.setPassword(cursor.getString(2));
                myUser.setAge(cursor.getString(3));
                myUser.setWeight(Double.parseDouble(cursor.getString(4)));
                myUser.setHeight(Double.parseDouble(cursor.getString(5)));
                myUser.setSport(cursor.getString(6));
                myUser.setGender(cursor.getString(7));
                myUser.setActivityLevel(cursor.getString(8));
            } while (cursor.moveToNext());
        }
        return myUser;
    }
}
