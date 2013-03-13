package il.ac.huji.todolist;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

public class TodoListManagerActivity extends Activity {

	private ArrayAdapter<Item> adapter;

	private boolean addItem() {
		EditText editText = (EditText) findViewById(R.id.edtNewItem);
		if (editText == null) {
			return false;
		}
		String title = editText.getText().toString();
		Item item = new Item(title);
		adapter.add(item);
		return true;
	}

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

	@Override
	public boolean onOptionsItemSelected(MenuItem menuItem) {
		Item item = null;
		switch (menuItem.getItemId()) {
		case R.id.menuItemAdd:
			return addItem();
		case R.id.menuItemDelete:
			ListView listCourses = (ListView) findViewById(R.id.lstTodoItems);
			item = (Item) listCourses.getSelectedItem();
			if (item != null) {
				adapter.remove(item);
				return true;
			}
			break;
		}
		return false;
	}

}
