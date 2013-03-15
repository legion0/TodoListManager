package il.ac.huji.todolist;

import java.util.Calendar;
import java.util.Date;

public class Functions {
	public static final Date today() {
		Calendar cal = Calendar.getInstance();
		int year = cal.get(Calendar.YEAR);
		int month = cal.get(Calendar.MONTH);
		int day = cal.get(Calendar.DAY_OF_MONTH);
		cal.clear();
		cal.set(year, month, day);
		return cal.getTime();
	}

	public static final Date newDate(int year, int month, int dayOfMonth) {
		Calendar cal = Calendar.getInstance();
		cal.clear();
		cal.set(year, month, dayOfMonth);
		return cal.getTime();
	}
}
