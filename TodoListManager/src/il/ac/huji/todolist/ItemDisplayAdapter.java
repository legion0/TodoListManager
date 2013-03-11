package il.ac.huji.todolist;

import java.util.List;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class ItemDisplayAdapter extends ArrayAdapter<Item> {

	public ItemDisplayAdapter(TodoListManagerActivity activity, List<Item> items) {
		super(activity, android.R.layout.simple_list_item_1, items);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		Item item = getItem(position);
		LayoutInflater inflater = LayoutInflater.from(getContext());
		View view = inflater.inflate(R.layout.activity_todo_list_manager_list_item, null);
		TextView txtName = (TextView) view.findViewById(R.id.lstTodoItem);
		int color = position % 2 == 0 ? Color.RED : Color.BLUE;
		txtName.setTextColor(color);
		txtName.setText(item.toString());
		return view;
	}

}
