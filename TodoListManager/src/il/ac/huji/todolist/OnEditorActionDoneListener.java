package il.ac.huji.todolist;

import android.view.KeyEvent;
import android.view.inputmethod.EditorInfo;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;

public abstract class OnEditorActionDoneListener implements OnEditorActionListener {

	@Override
	public boolean onEditorAction(TextView view, int actionId, KeyEvent keyEvent) {
		if (actionId == EditorInfo.IME_ACTION_DONE
				|| (actionId == EditorInfo.IME_NULL && keyEvent != null
						&& keyEvent.getKeyCode() == KeyEvent.KEYCODE_ENTER && keyEvent.getAction() == KeyEvent.ACTION_DOWN)) {
			return onEditorDoneAction(view, actionId, keyEvent);
		}
		return false;
	}

	public abstract boolean onEditorDoneAction(TextView arg0, int actionId, KeyEvent keyEvent);

}
