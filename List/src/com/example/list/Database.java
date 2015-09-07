package com.example.list;

import java.util.*;

import android.content.*;
import android.database.Cursor;
import android.database.sqlite.*;

public class Database extends SQLiteOpenHelper{
	private static final String DATABASE_NAME = "items";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_NAME = "name";
    private static final String COLUMN_COMMENT = "comment";
    private static final String COLUMN_AMOUNT = "amount";
    private static final String COLUMN_DONE = "done";

	public Database(Context context) {
		super(context, DATABASE_NAME, null, 1);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		String createTableQuery = "CREATE TABLE " + DATABASE_NAME + "(" + COLUMN_ID + " INTEGER PRIMARY KEY, " + COLUMN_NAME + " TEXT, " 
				+ COLUMN_COMMENT + " TEXT, " + COLUMN_AMOUNT + " TEXT, " + COLUMN_DONE + " INTEGER)";
		db.execSQL(createTableQuery);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL("DROP TABLE IF EXISTS " + DATABASE_NAME);
		onCreate(db);
	}
	
	public ArrayList<Item> getItems() {
		SQLiteDatabase db = this.getWritableDatabase();
		ArrayList<Item> items = new ArrayList<Item>();
		String getItemsQuery = "SELECT * FROM " + DATABASE_NAME;
		Cursor cursor = db.rawQuery(getItemsQuery, null);
		
		if (cursor.moveToFirst()) {
		    do {
		    	Item item = new Item(cursor.getString(2), cursor.getString(3), cursor.getString(1));
		    	if (cursor.getInt(4) == 1) {
		    		item.setDone(true);
				}
				else {
				    item.setDone(false);
				}
				items.add(cursor.getInt(0), item);
		    }while (cursor.moveToNext());
		}
		return items;
	}
	    
	public void updateTable(List<Item> items) {
		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues values;
		
		for (Item i : items) {
		    values = new ContentValues();
		    values.put(COLUMN_ID, items.indexOf(i));
		    values.put(COLUMN_NAME, i.getName());
		    values.put(COLUMN_COMMENT, i.getComment());
		    values.put(COLUMN_AMOUNT, i.getAmount());
		    
		    if (i.getDone()) {
		    	values.put(COLUMN_DONE, 1);
		    }
		    
		    else {
		    	values.put(COLUMN_DONE, 0);
		    }
		    
		    db.insert(DATABASE_NAME, null, values);
		}
		db.close();
	}
}