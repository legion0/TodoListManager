package il.ac.huji.todolist;

import java.util.Date;

public class TodoItem implements ITodoItem {

	private String title = null;
	private Date dueDate = null;

	public TodoItem(String title, Date dueDate) {
		this.title = title;
		this.dueDate = dueDate;
	}

	public String getTitle() {
		return this.title;
	}

	// public void title(String title) {
	// this.title = title;
	// }

	public Date getDueDate() {
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
