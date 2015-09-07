package com.example.list;

import java.util.ArrayList;

import org.apache.http.protocol.HTTP;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.*;
import android.view.*;
import android.view.View.OnClickListener;
import android.widget.CheckBox;

public class ItemListActivity extends ActionBarActivity implements ItemListFragment.Callbacks, OnClickListener {
	static boolean mTwoPane;
	public static ItemListAdapter itemListAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_item_list);

		if (findViewById(R.id.item_detail_container) != null) {
			mTwoPane = true;
			((ItemListFragment) getSupportFragmentManager().findFragmentById(R.id.item_list)).setActivateOnItemClick(true);
		}
	}
	
	@Override
	public void onStart(){
		super.onStart();
		if(itemListAdapter == null){
			Database db = new Database(this);
			itemListAdapter = new ItemListAdapter(this, db.getItems());
			((ItemListFragment) getSupportFragmentManager().findFragmentById(R.id.item_list)).setListAdapter(itemListAdapter);
		}
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_item_list, menu);
        super.onCreateOptionsMenu(menu);
        return true;
    }
	
	@Override
    public void onStop() {
		Database db = new Database(this);
		db.updateTable(itemListAdapter.getItemList());
		super.onStop();
    }

	@Override
	public void onItemSelected(String id) {
		if (mTwoPane) {
			Bundle arguments = new Bundle();
			arguments.putString(ItemDetailFragment.ARG_ITEM_ID, id);
			ItemDetailFragment fragment = new ItemDetailFragment();
			fragment.setArguments(arguments);
			getSupportFragmentManager().beginTransaction().replace(R.id.item_detail_container, fragment).commit();

		} 
		else {
			Intent detailIntent = new Intent(this, ItemDetailActivity.class);
			detailIntent.putExtra(ItemDetailFragment.ARG_ITEM_ID, id);
			startActivity(detailIntent);
		}
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()){
		case R.id.addItem:
			Item newItem = new Item();
			itemListAdapter.add(newItem);
		    break;
		case R.id.delete:
			itemListAdapter.clear();
		    itemListAdapter.notifyDataSetChanged();
		    break;
		case R.id.send:
			String message = itemListAdapter.getUndoneItems();
		    Intent intent = new Intent();
		    intent.setAction(Intent.ACTION_SEND);
		    intent.putExtra(Intent.EXTRA_TEXT, message);
		    intent.setType(HTTP.PLAIN_TEXT_TYPE);
		    startActivity(intent);
		    break;	
		}
		
		return super.onOptionsItemSelected(item);
	}
	
	@Override
    public void onClick(View v) {
		if (v.getId() == R.id.completedCheckBox) {
		    CheckBox cb = (CheckBox)v;
		    if (cb.isChecked()) {
			itemListAdapter.getItem(Integer.parseInt(cb.getText().toString())).setDone(true);
		    }
		    else {
			itemListAdapter.getItem(Integer.parseInt(cb.getText().toString())).setDone(false);
		    }
		}
    }
}
