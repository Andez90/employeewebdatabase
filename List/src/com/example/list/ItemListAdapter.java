package com.example.list;

import java.util.*;

import android.content.Context;
import android.view.*;
import android.view.View.OnClickListener;
import android.widget.*;

public class ItemListAdapter extends ArrayAdapter<Item>{
	private Map<String, Item> itemMap;
	
    public ItemListAdapter(Context context, ArrayList<Item> values) {
	super(context, R.layout.listview_item, values);
	itemMap = new HashMap<String, Item>();
	if (!values.isEmpty()) {
	    int count = 0;
	    for (Item i : values) {
		itemMap.put("" + count, i);
		count++;
	    }
	}
    }
    
    public List<Item> getItemList() {
	List<Item> items = new ArrayList<Item>();
	for (String s : itemMap.keySet()) {
	    items.add(itemMap.get(s));
	}
	return items;
    }
    
    public Item getItemById(String id) {
	return itemMap.get(id);
    }
    
    @Override
    public void clear() {
	for (String s : itemMap.keySet()) {
	    if (itemMap.get(s).getDone()) {
		this.remove(itemMap.get(s));
	    }
	}
    }
    
    @Override
    public void add(Item item) {
	super.add(item);
	itemMap.put("" + this.getPosition(item), item);
    }
    
    @Override
    public boolean areAllItemsEnabled() {
	return true;
    }
    
    @Override
    public boolean isEnabled(int arg0) {
	return true;
    }
    
    public String getUndoneItems() {
	String s = "";
	for (int i = 0; i < this.getCount(); i++) {
	    Item tmp = this.getItem(i);
	    if (!tmp.getDone()) {
		s += tmp.getName() + " - " + tmp.getAmount();
		s += tmp.getComment().compareToIgnoreCase("no comment") == 0 ? "\n" : " - " + tmp.getComment() + "\n";
	    }
	}
	return s;
    }

    @Override
    public void remove(Item item) {
	super.remove(item);
	itemMap.remove(item);
    }
    
    @Override
    public View getView(int position, View covertView, ViewGroup parent) {
	
	if (covertView == null) {
        	LayoutInflater inflater = (LayoutInflater)this.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        	View row = inflater.inflate(R.layout.listview_item, parent, false);
        	TextView name = (TextView)row.findViewById(R.id.nameTextView);
        	TextView amount = (TextView)row.findViewById(R.id.amountTextView);
        	TextView comment = (TextView)row.findViewById(R.id.commentTextView);
        	CheckBox done = (CheckBox)row.findViewById(R.id.completedCheckBox);
        	
        	name.setText(this.getItem(position).getName());
        	amount.setText("" + this.getItem(position).getAmount());
        	comment.setText("" + this.getItem(position).getComment());
        	done.setOnClickListener((OnClickListener)this.getContext());
        	done.setChecked(this.getItem(position).getDone());
        	done.setText("" + position);
        	done.setTextSize(0);
        	    
        	return row;
	}
	else {
	    	TextView name = (TextView)covertView.findViewById(R.id.nameTextView);
        	TextView amount = (TextView)covertView.findViewById(R.id.amountTextView);
        	TextView comment = (TextView)covertView.findViewById(R.id.commentTextView);
        	CheckBox done = (CheckBox)covertView.findViewById(R.id.completedCheckBox);
        	
        	name.setText(this.getItem(position).getName());
        	amount.setText("" + this.getItem(position).getAmount());
        	comment.setText("" + this.getItem(position).getComment());
        	done.setOnClickListener((OnClickListener)this.getContext());
        	done.setChecked(this.getItem(position).getDone());
        	done.setText("" + position);
        	done.setTextSize(0);
        	
	 	 return covertView;
	}
    }
}
