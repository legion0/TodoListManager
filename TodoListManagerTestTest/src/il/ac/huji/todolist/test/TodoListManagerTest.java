/**
 * 
 */
package il.ac.huji.todolist.test;

import il.ac.huji.todolist.TodoListManagerActivity;
import android.test.ActivityInstrumentationTestCase2;
import android.widget.ListAdapter;
import android.widget.ListView;

public class TodoListManagerTest extends ActivityInstrumentationTestCase2<TodoListManagerActivity> {

	public TodoListManagerTest() {
		super(TodoListManagerActivity.class);
	}

	private TodoListManagerActivity activity;
	private ListView list;
	private ListAdapter adapter;

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		setActivityInitialTouchMode(false);
		activity = getActivity();
		list = (ListView) (ListView) activity.findViewById(il.ac.huji.todolist.R.id.lstTodoItems);
		adapter = list.getAdapter();
	}

	public void tearDown() throws Exception {
		activity.finish();
		super.tearDown();
	}

	public void testPreConditions() {
		assertTrue(adapter != null);
		assertEquals(adapter.getCount(), 0);
	}
}
