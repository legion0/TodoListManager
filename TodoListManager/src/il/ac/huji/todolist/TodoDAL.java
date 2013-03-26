package il.ac.huji.todolist;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class TodoDAL {
	private final DBHelper dbHelper;
	private static final String TABLE_NAME = "todo";

	public TodoDAL(Context context) {
		dbHelper = new DBHelper(context);
	}

	public boolean insert(ITodoItem todoItem) {
		if (todoItem.getTitle() == null) {
			return false;
		}
		final ContentValues values = new ContentValues(1);
		values.put("title", todoItem.getTitle());
		Long due = null;
		if (todoItem.getDueDate() != null) {
			due = todoItem.getDueDate().getTime();
		}
		values.put("due", due);
		Object resO = doDBAction(new IDBAction() {

			@Override
			public Object action(SQLiteDatabase db) {
				long rowId = db.insert(TABLE_NAME, null, values);
				return rowId != -1;
			}
		}, true);

		if (resO == null) {
			return false;
		}
		boolean res = (Boolean) resO;
		return res;
	}

	public boolean update(final ITodoItem todoItem) {
		if (todoItem.getTitle() == null) {
			return false;
		}
		final ContentValues values = new ContentValues(1);
		Long due = null;
		if (todoItem.getDueDate() != null) {
			due = todoItem.getDueDate().getTime();
		}
		values.put("due", due);
		Object resO = doDBAction(new IDBAction() {

			@Override
			public Object action(SQLiteDatabase db) {
				int rowsAffected = db.update(TABLE_NAME, values, "title = ?", new String[] { todoItem.getTitle() });
				return rowsAffected > 0;
			}
		}, true);

		if (resO == null) {
			return false;
		}
		boolean res = (Boolean) resO;
		return res;
	}

	public boolean delete(final ITodoItem todoItem) {
		if (todoItem.getTitle() == null) {
			return false;
		}
		Object resO = doDBAction(new IDBAction() {

			@Override
			public Object action(SQLiteDatabase db) {
				int rowsAffected = 0;
				rowsAffected = db.delete(TABLE_NAME, "title = ?", new String[] { todoItem.getTitle() });
				return rowsAffected > 0;
			}
		}, true);

		if (resO == null) {
			return false;
		}
		boolean res = (Boolean) resO;
		return res;
	}

	public List<ITodoItem> all() {
		Object res = doDBAction(new IDBAction() {

			@Override
			public Object action(SQLiteDatabase db) {
				List<ITodoItem> items = new ArrayList<ITodoItem>();
				Cursor cursor = db.query("todo", new String[] { "title", "due" }, null, null, null, null, null);
				if (cursor.moveToFirst()) {
					do {
						String title = cursor.getString(0);
						Long due = cursor.getLong(1);
						Date dueDate = null;
						if (due != null) {
							dueDate = new Date(due);
						}
						items.add(new TodoItem(title, dueDate));
					} while (cursor.moveToNext());
				}
				return items;
			}
		});
		if (res == null) {
			return new ArrayList<ITodoItem>();
		}
		@SuppressWarnings("unchecked")
		List<ITodoItem> items = (List<ITodoItem>) res;
		return items;
	}

	private Object doDBAction(IDBAction action) {
		return doDBAction(action, false);
	}

	private Object doDBAction(IDBAction action, boolean writes) {
		Object res = null;
		SQLiteDatabase db = writes ? dbHelper.getWritableDatabase() : dbHelper.getReadableDatabase();
		db.beginTransaction();
		try {
			res = action.action(db);
			db.setTransactionSuccessful();
		} finally {
			db.endTransaction();
		}
		return res;
	}
}