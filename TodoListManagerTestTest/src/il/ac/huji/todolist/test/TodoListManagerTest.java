/**
 * 
 */
package il.ac.huji.todolist.test;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import il.ac.huji.todolist.Item;
import il.ac.huji.todolist.R;
import il.ac.huji.todolist.TodoListManagerActivity;
import il.ac.huji.todolist.ItemDisplayAdapter;
import android.graphics.Color;
import android.test.ActivityInstrumentationTestCase2;
import android.util.Base64;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

public class TodoListManagerTest extends ActivityInstrumentationTestCase2<TodoListManagerActivity> {

	public TodoListManagerTest() {
		super(TodoListManagerActivity.class);
	}

	private TodoListManagerActivity activity;
	private ListView list;
	private ItemDisplayAdapter adapter;
	private EditText input;

	private static Random random = new Random();

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

	public void tearDown() throws Exception {
		assertEquals(adapter.getCount(), 0);
		activity.finish();
		super.tearDown();
	}

	public void testColors() {
		List<Item> items = genRandomItems(10);
		addItems(items);
		assertEquals(adapter.getCount(), 10);
		assertColors();
		deleteItems(items);
		assertEquals(adapter.getCount(), 0);
	}

	private void assertColors() {
		int color;
		for (int i = 0; i < adapter.getCount(); i++) {
			// color = i % 2 == 0 ? Color.BLUE : Color.RED;
			color = i % 2 == 0 ? Color.RED : Color.BLUE;
			TextView listItem = (TextView) adapter.getView(i, null, null).findViewById(R.id.lstTodoItem);
			assertEquals(listItem.getCurrentTextColor(), color);
		}
	}

	private void deleteItems(List<Item> items) {
		for (Item item : items) {
			int pos = adapter.getPosition(item);
			selectListViewItem(pos);
			activity.openOptionsMenu();
			getInstrumentation().invokeMenuActionSync(activity, R.id.menuItemDelete, 0);
		}
	}

	private void addItems(List<Item> items) {
		for (Item item : items) {
			addItem(item);
		}
	}

	private void addItem(Item item) {
		assertTrue(input.requestFocus());
		sendKeys(item.toString());
		activity.openOptionsMenu();
		getInstrumentation().invokeMenuActionSync(activity, R.id.menuItemAdd, 0);
	}

	private List<Item> genRandomItems(int amount) {
		List<Item> items = new ArrayList<Item>();
		for (int i = 0; i < amount; i++) {
			items.add(genRandomItem());
		}
		return items;
	}

	private Item genRandomItem() {
		byte[] buffer = new byte[10];
		random.nextBytes(buffer);
		String s = Base64.encodeToString(buffer, Base64.DEFAULT);
		return new Item(s);
	}

	public void selectListViewItem(int pos) {
		final ListView testListView = list;
		final int position = pos;
		Runnable action = new Runnable() {
			@Override
			public void run() {
				testListView.setSelection(position);
				synchronized (this) {
					this.notify();
				}
			}
		};
		synchronized (action) {
			activity.runOnUiThread(action);
			try {
				action.wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}
