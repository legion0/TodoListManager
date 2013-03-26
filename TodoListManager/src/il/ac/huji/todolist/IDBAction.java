package il.ac.huji.todolist;

import android.database.sqlite.SQLiteDatabase;

public interface IDBAction {
	public Object action(SQLiteDatabase db);
}
