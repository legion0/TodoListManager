package il.ac.huji.todolist;

import java.io.IOError;
import java.io.IOException;
import java.util.Date;

public class Item {

	private String title = null;
	private Date dueDate = null;

	public Item(String title, Date dueDate) {
		this.title = title;
		this.dueDate = dueDate;
	}

	public String title() {
		return this.title;
	}

	// public void title(String title) {
	// this.title = title;
	// }

	public Date dueDate() {
		return this.dueDate;
	}

	// public void dueDate(Date dueDate) {
	// this.dueDate = dueDate;
	// }

	@Override
	public String toString() {
		return title;
	}
}
