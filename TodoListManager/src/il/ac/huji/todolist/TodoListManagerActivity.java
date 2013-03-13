package il.ac.huji.todolist;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.IBinder;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

public class TodoListManagerActivity extends Activity {

	private ArrayAdapter<Item> adapter;

	private boolean addItem() {
		return addItem((EditText) findViewById(R.id.edtNewItem));
	}

	private boolean addItem(EditText editText) {
		if (editText == null) {
			return false;
		}
		String title = editText.getText().toString();
		hideKeyboard(editText.getWindowToken());
		Item item = new Item(title);
		adapter.add(item);
		editText.setText("");
		return true;
	}

	private void hideKeyboard() {
		hideKeyboard(getCurrentFocus().getWindowToken());

	}

	private void hideKeyboard(IBinder iBinder) {
		InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
		imm.hideSoftInputFromWindow(iBinder, 0);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_todo_list_manager);
		ListView listCourses = (ListView) findViewById(R.id.lstTodoItems);
		List<Item> items = new ArrayList<Item>();
		adapter = new ItemDisplayAdapter(this, items);
		listCourses.setAdapter(adapter);
		EditText input = (EditText) findViewById(R.id.edtNewItem);
		input.setOnEditorActionListener(new OnEditorActionDoneListener() {
			@Override
			public boolean onEditorDoneAction(TextView editText, int actionId, KeyEvent keyEvent) {
				return addItem((EditText) editText);
			}
		});

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.todo_list_manager, menu);
		hideKeyboard();
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
