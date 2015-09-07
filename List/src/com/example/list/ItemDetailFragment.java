package com.example.list;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.*;
import android.view.View.OnFocusChangeListener;
import android.widget.*;
import android.widget.CompoundButton.OnCheckedChangeListener;

public class ItemDetailFragment extends Fragment implements OnCheckedChangeListener, OnFocusChangeListener{
	public static final String ARG_ITEM_ID = "item_id";
	private Item item;

	public ItemDetailFragment() {
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		if (getArguments().containsKey(ARG_ITEM_ID)) {
			item = ItemListActivity.itemListAdapter.getItemById(getArguments().getString(ARG_ITEM_ID));
		}
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		final View rootView = inflater.inflate(R.layout.fragment_item_detail, container, false);

		if (item != null) {	
			((EditText) rootView.findViewById(R.id.editName)).setText(item.getName());
			((EditText) rootView.findViewById(R.id.editAmount)).setText(item.getAmount());
			((EditText) rootView.findViewById(R.id.editComment)).setText(item.getComment());
			this.getActivity().setTitle(item.getName());
			((CheckBox) rootView.findViewById(R.id.checkDone)).setChecked(item.getDone());
			
			rootView.findViewById(R.id.editName).setOnFocusChangeListener(this);
			rootView.findViewById(R.id.editAmount).setOnFocusChangeListener(this);
			rootView.findViewById(R.id.editComment).setOnFocusChangeListener(this);
			((CheckBox)rootView.findViewById(R.id.checkDone)).setOnCheckedChangeListener(this);
		}

		return rootView;
	}

	@Override
	public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
		if (buttonView.getId() == R.id.checkDone) {
		    item.setDone(isChecked);
		    ItemListActivity.itemListAdapter.notifyDataSetChanged();
		}
	}
	
	@Override
    public void onPause() {
	super.onPause();
    }

	@Override
	public void onFocusChange(View v, boolean hasFocus) {
		if (!hasFocus) {
			if (v.getId() == R.id.editName) {
				if (item.getName().compareToIgnoreCase(((TextView)v).getText().toString()) != 0) {
					   item.setName(((TextView)v).getText().toString());
				}
			}
			else if (v.getId() == R.id.editAmount) {
				if (item.getAmount().compareToIgnoreCase(((TextView)v).getText().toString()) != 0){
					item.setAmount(((TextView)v).getText().toString());
				}
			}
			else if (v.getId() == R.id.editComment) {
				if (item.getComment().compareToIgnoreCase(((TextView)v).getText().toString()) != 0) {
					   item.setComment(((TextView)v).getText().toString());
				}
			}
		}
	}
}
