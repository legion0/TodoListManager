package il.ac.huji.todolist;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class TodoListManagerActivity extends Activity {

	private ArrayAdapter<Item> adapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_todo_list_manager);
		ListView listCourses = (ListView) findViewById(R.id.lstTodoItems);
		List<Item> items = new ArrayList<Item>();
		adapter = new ItemDisplayAdapter(this, items);
		listCourses.setAdapter(adapter);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.todo_list_manager, menu);
		return true;
	}

	public boolean onOptionsItemSelected(MenuItem menuItem) {
		Item item = null;
		switch (menuItem.getItemId()) {
		case R.id.menuItemAdd:
			TextView edtNewItem = (TextView) findViewById(R.id.edtNewItem);
			String title = edtNewItem.getText().toString();
			item = new Item(title);
			adapter.add(item);
			break;
		case R.id.menuItemDelete:
			ListView listCourses = (ListView) findViewById(R.id.lstTodoItems);
			item = (Item) listCourses.getSelectedItem();
			if (item != null) {
				adapter.remove(item);
			}
			break;
		}
		return true;
	}

}
