package il.ac.huji.todolist;

import java.util.Date;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;

public class AddNewTodoItemActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_add_new_todo_item);
		Button ok = (Button) findViewById(R.id.btnOK);
		ok.setOnClickListener(onOKClick);
		Button cancel = (Button) findViewById(R.id.btnCancel);
		cancel.setOnClickListener(onCancelClick);
	}

	private OnClickListener onOKClick = new OnClickListener() {

		@Override
		public void onClick(View arg0) {
			TextView titleView = (TextView) findViewById(R.id.edtNewItem);
			DatePicker datePicker = (DatePicker) findViewById(R.id.datePicker);

			String title = titleView.getText().toString();
			Date date = Functions.newDate(datePicker.getYear(), datePicker.getMonth(), datePicker.getDayOfMonth());

			Intent result = new Intent();
			result.putExtra("title", title);
			result.putExtra("dueDate", date);
			setResult(RESULT_OK, result);
			finish();
		}
	};

	private OnClickListener onCancelClick = new OnClickListener() {

		@Override
		public void onClick(View arg0) {
			Intent result = new Intent();
			setResult(RESULT_CANCELED, result);
			finish();
		}
	};

}
