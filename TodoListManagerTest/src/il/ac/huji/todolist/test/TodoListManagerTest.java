/**
 * 
 */
package il.ac.huji.todolist.test;

import il.ac.huji.todolist.Item;
import il.ac.huji.todolist.ItemDisplayAdapter;
import il.ac.huji.todolist.R;
import il.ac.huji.todolist.TodoListManagerActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import android.graphics.Color;
import android.test.ActivityInstrumentationTestCase2;
import android.test.TouchUtils;
import android.util.Base64;
import android.util.Log;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

public class TodoListManagerTest extends ActivityInstrumentationTestCase2<TodoListManagerActivity> {

	private TodoListManagerActivity activity;

	private ListView list;
	private ItemDisplayAdapter adapter;
	private EditText input;
	private static Random random = new Random();

	public TodoListManagerTest() {
		super(TodoListManagerActivity.class);
	}

	private void addItem(Item item) {
		TouchUtils.tapView(this, input);
		inputTitle(item.toString());
		activity.openOptionsMenu();
		getInstrumentation().invokeMenuActionSync(activity, R.id.menuItemAdd, 0);
		inputTitle("");
		assertEquals("", input.getText().toString());
	}

	private void addItems(List<Item> items) {
		for (Item item : items) {
			addItem(item);
		}
	}

	private void assertColors() {
		int color;
		Log.d("assertColors: getChildCount", Integer.toString(list.getChildCount()));
		for (int i = 0; i < list.getChildCount(); i++) {

			color = i % 2 == 0 ? Color.RED : Color.BLUE;
			TextView listItem = (TextView) list.getChildAt(i).findViewById(R.id.lstTodoItem);
			assertEquals(listItem.getCurrentTextColor(), color);
		}
	}

	private void deleteItems() {

		Log.d("deleteItems: getChildCount", Integer.toString(list.getChildCount()));
		while (list.getChildCount() > 0) {
			Log.d("deleteItems", "Selecting 0");
			selectListViewItem(0);
			Log.d("deleteItems", "Opening Menu");
			activity.openOptionsMenu();
			Log.d("deleteItems", "Deleting");
			getInstrumentation().invokeMenuActionSync(activity, R.id.menuItemDelete, 0);
		}
	}

	private Item genRandomItem() {
		byte[] buffer = new byte[10];
		random.nextBytes(buffer);
		String s = Base64.encodeToString(buffer, Base64.DEFAULT);
		Log.d("XXX", s);
		return new Item(s);
	}

	private List<Item> genRandomItems(int amount) {
		List<Item> items = new ArrayList<Item>();
		for (int i = 0; i < amount; i++) {
			items.add(genRandomItem());
		}
		return items;
	}

	private void inputTitle(final String title) {
		final EditText edit = input;
		Runnable action = new Runnable() {
			@Override
			public void run() {
				edit.setText(title);
			}
		};
		runOnUiThread(action);
	}

	private void runOnUiThread(final Runnable r) {
		Log.d("runOnUiThread", "");
		Runnable action = new Runnable() {
			@Override
			public void run() {
				r.run();
				Log.d("UI", "Notify");
				synchronized (this) {
					this.notify();
				}
			}
		};
		synchronized (action) {
			Log.d("runOnUiThread", "assigning");
			activity.runOnUiThread(action);
			try {
				Log.d("runOnUiThread", "waiting");
				action.wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
				fail("Failed To wait for gui thread");
			}
		}
	}

	private void selectListViewItem(final int pos) {
		final ListView listView = list;

		Runnable action = new Runnable() {

			@Override
			public void run() {
				sleep(200); // wait for idle before requesting focus or focus
							// will fail
				assertTrue(listView.requestFocusFromTouch());
				listView.setSelection(pos);
			}
		};
		runOnUiThread(action);
		assertEquals(pos, listView.getSelectedItemPosition());
	}

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		setActivityInitialTouchMode(false);
		activity = getActivity();
		list = (ListView) activity.findViewById(il.ac.huji.todolist.R.id.lstTodoItems);
		adapter = (ItemDisplayAdapter) list.getAdapter();
		input = (EditText) activity.findViewById(il.ac.huji.todolist.R.id.edtNewItem);

		assertEquals(adapter.getCount(), 0);
	}

	private void sleep(long time) {
		try {
			Thread.sleep(time);
		} catch (InterruptedException e) {
			e.printStackTrace();
			fail("Failed To Sleep");
		}
	}

	@Override
	public void tearDown() throws Exception {

		activity.finish();
		super.tearDown();
	}

	public void testColors() {
		List<Item> items = genRandomItems(10);
		addItems(items);
		assertEquals(adapter.getCount(), 10);
		assertColors();
		deleteItems();
		assertEquals(adapter.getCount(), 0);
	}
}
