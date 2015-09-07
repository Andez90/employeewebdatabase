package com.example.list;

import java.io.Serializable;
import java.util.*;

public class Item implements Serializable{
	private static final long serialVersionUID = 2110868870175565400L;
	public static List<Item> ITEMS = new ArrayList<Item>();
    public static Map<String, Item> ITEM_MAP = new HashMap<String, Item>();

	public String name;
	public String amount;
	public String comment;
	public boolean done;

	public Item() {
		name = "Item_Name";
		amount = "1";
		comment = "Item_Comment";
		done = false;
	}
	
	public Item(String name, String amount, String comment) {
		this.name = name;
		this.amount = amount;
		this.comment = comment;
		done = false;
	}
	
	public void setName(String name){
		this.name = name;
	}
	
	public String getName(){
		return name;
	}
	
	public void setAmount(String amount){
		this.amount = amount;
	}
	
	public String getAmount(){
		return amount;
	}
	
	public void setComment(String comment){
		this.comment = comment;
	}
	
	public String getComment(){
		return comment;
	}
	
	public void setDone(Boolean done){
		this.done = done;
	}
	
	public Boolean getDone(){
		return done;
	}

	@Override
	public String toString() {
		return name + ": " + amount + ": " + comment + ": " + done;
	}
}