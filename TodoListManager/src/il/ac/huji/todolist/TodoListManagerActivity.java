package il.ac.huji.todolist;

import java.util.Date;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Bundle;
import android.os.IBinder;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class TodoListManagerActivity extends Activity {

	private static final int REQC_ADDING_NEW_TODO_ITEM = 2;
	private ArrayAdapter<ITodoItem> adapter;
	private ListView listCourses;
	private TodoDAL dal;

	private boolean addItem(String title, Date dueDate) {
		if (title == null) {
			return false;
		}
		TodoItem item = new TodoItem(title, dueDate);
		if (!dal.insert(item)) {
			return false;
		}
		adapter.add(item);
		return true;
	}

	private void hideKeyboard() {
		View focus = getCurrentFocus();
		if (focus != null) {
			hideKeyboard(focus.getWindowToken());
		}

	}

	private void hideKeyboard(IBinder iBinder) {
		InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
		imm.hideSoftInputFromWindow(iBinder, 0);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_todo_list_manager);
		listCourses = (ListView) findViewById(R.id.lstTodoItems);
		dal = new TodoDAL(this);
		List<ITodoItem> items = dal.all();
		adapter = new ItemDisplayAdapter(this, items);
		listCourses.setAdapter(adapter);
		registerForContextMenu(listCourses);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		hideKeyboard();
		getMenuInflater().inflate(R.menu.todo_list_manager, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem menuItem) {
		switch (menuItem.getItemId()) {
		case R.id.menuItemAdd:
			return openAddItemActivity();
		}
		return false;
	}

	private boolean openAddItemActivity() {
		Intent intent = new Intent(this, AddNewTodoItemActivity.class);
		startActivityForResult(intent, REQC_ADDING_NEW_TODO_ITEM);
		return true;
	}

	@Override
	protected void onActivityResult(int reqCode, int resCode, Intent data) {
		switch (reqCode) {
		case REQC_ADDING_NEW_TODO_ITEM:
			if (resCode == RESULT_OK) {
				String title = data.getStringExtra("title");
				Date dueDate = (Date) data.getExtras().get("dueDate");
				addItem(title, dueDate);
			}
		}
	}

	@Override
	public void onCreateContextMenu(ContextMenu menu, View v, ContextMenuInfo info) {
		getMenuInflater().inflate(R.menu.todo_list_list_context, menu);
		AdapterContextMenuInfo adapterInfo = (AdapterContextMenuInfo) info;
		ITodoItem item = adapter.getItem(adapterInfo.position);
		menu.setHeaderTitle(item.getTitle());
		Resources res = getResources();
		String callPrefix = res.getString(R.string.prefix_call);
		if (item.getTitle().startsWith(callPrefix)) {
			MenuItem callView = menu.findItem(R.id.menuItemCall);
			callView.setTitle(item.getTitle());
		} else {
			menu.removeItem(R.id.menuItemCall);
		}
	}

	@Override
	public boolean onContextItemSelected(MenuItem menuItem) {
		AdapterContextMenuInfo info = (AdapterContextMenuInfo) menuItem.getMenuInfo();
		ITodoItem item = adapter.getItem(info.position);
		switch (menuItem.getItemId()) {
		case R.id.menuItemDelete:
			return deleteItem(item);
		case R.id.menuItemCall:
			return callSomeone(item);
		}
		return false;
	}

	private boolean callSomeone(ITodoItem item) {
		Resources res = getResources();
		String callPrefix = res.getString(R.string.prefix_call);
		String number = item.getTitle().substring(callPrefix.length());
		Intent dial = new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", number, null));
		startActivity(dial);
		return true;
	}

	private boolean deleteItem(ITodoItem item) {
		adapter.remove(item);
		dal.delete(item);
		return true;
	}
}
