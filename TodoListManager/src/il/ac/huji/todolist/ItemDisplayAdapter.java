package il.ac.huji.todolist;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import android.annotation.SuppressLint;
import android.content.res.Resources;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class ItemDisplayAdapter extends ArrayAdapter<ITodoItem> {

	@SuppressLint("SimpleDateFormat")
	private DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

	public ItemDisplayAdapter(TodoListManagerActivity activity, List<ITodoItem> items) {
		super(activity, android.R.layout.simple_list_item_1, items);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ITodoItem item = getItem(position);
		LayoutInflater inflater = LayoutInflater.from(getContext());
		View view = inflater.inflate(R.layout.activity_todo_list_manager_list_item, null);
		TextView title = (TextView) view.findViewById(R.id.txtTodoTitle);
		TextView dueDate = (TextView) view.findViewById(R.id.txtTodoDueDate);

		title.setText(item.getTitle());
		if (item.getDueDate() != null) {
			dueDate.setText(dateFormat.format(item.getDueDate()));
			Date today = Functions.today();
			if (item.getDueDate().getTime() < today.getTime()) {
				title.setTextColor(Color.RED);
				dueDate.setTextColor(Color.RED);
			}
		} else {
			Resources res = view.getResources();
			String noDate = res.getString(R.string.empty_due_date);
			dueDate.setText(noDate);
		}
		return view;
	}

}
