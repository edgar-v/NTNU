package com.g19.find_my_sheep;


import java.util.ArrayList;

import android.app.ActionBar;
import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

public class SheepMenu extends ListActivity {
	public ArrayList<Integer> sheepId;
	public ArrayList<String> sheepNames;
	DatabaseSuperpower database;

	// May also be triggered from the Activity
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.sheep_menu);

		database = new DatabaseSuperpower(SheepMenu.this);
		database.open();
		sheepNames = database.getSheepNamesFromDatabase();
		sheepId = database.getSheepIdFromDatabase();

		ActionBar actionBar = getActionBar();
		actionBar.setHomeButtonEnabled(true);

		//TextView display = (TextView) findViewById(R.id.textView1);

		ArrayAdapter<String> adapter = new ArrayAdapter<String>(SheepMenu.this, 
				android.R.layout.simple_list_item_1, sheepNames);
		setListAdapter(adapter);
		registerForContextMenu(getListView());

		Button newSheep = (Button) findViewById(R.id.b3newSheep);
		newSheep.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				Toast.makeText(SheepMenu.this, "Add sheep", Toast.LENGTH_SHORT).show();
				Intent intent = new Intent(SheepMenu.this, RegisterSheep.class);
				startActivity(intent);
			}
		});

	}

	@Override
	public void onCreateContextMenu(ContextMenu menu, View v,
			ContextMenuInfo menuInfo) {
		super.onCreateContextMenu(menu, v, menuInfo);
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.popup_menu, menu);
	}

	@Override
	public boolean onContextItemSelected(MenuItem item) {
		AdapterContextMenuInfo info = (AdapterContextMenuInfo) item.getMenuInfo();
		System.out.println(info.id);
		switch (item.getItemId()) {
		case R.id.changeSheep:
			Intent intent = new Intent(SheepMenu.this, EditSheep.class);
			intent.putExtra("id", sheepId.get((int) info.id)); //Your id
			startActivity(intent);
			break;
		case R.id.deleteSheep:
			database.delete(sheepId.get((int) info.id));
			sheepNames.remove(info.id);
			sheepId.remove(info.id);

			onContentChanged();
			break;
			/*case R.id.sheepLog:
			showDialog();
			break;*/

		}
		Toast.makeText(SheepMenu.this, "You clicked: "+item.getTitle(), 
				Toast.LENGTH_SHORT).show();
		return true;
	}

	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {
		super.onListItemClick(l, v, position, id);
		final int pos = position;
		Intent intent = new Intent(SheepMenu.this, EditSheep.class);
		intent.putExtra("id", sheepId.get(pos)); //Your id
		startActivity(intent);
	}

	/*void showDialog() {

        // DialogFragment.show() will take care of adding the fragment
        // in a transaction.  We also want to remove any currently showing
        // dialog, so make our own transaction and take care of that here.
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        Fragment prev = getFragmentManager().findFragmentByTag("dialog");
        if (prev != null) {
            ft.remove(prev);
        }
        ft.addToBackStack(null);

        // Create and show the dialog.
        DialogFragment newFragment = Dialog.newInstance(mStackLevel);
        newFragment.show(ft, "dialog");
    }*/

	@Override
	protected void onResume() {
		database.open();
		super.onResume();
	}

	@Override
	protected void onPause() {
		database.close();
		super.onPause();
	}
}

/*class Dialog extends DialogFragment {
    int mNum;

    /**
 * Create a new instance of MyDialogFragment, providing "num"
 * as an argument.
 */
/* static Dialog newInstance(int num) {
        Dialog f = new Dialog();

        // Supply num input as an argument.
        Bundle args = new Bundle();
        args.putInt("num", num);
        f.setArguments(args);

        return f;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mNum = getArguments().getInt("num");

        // Pick a style based on the num.
        int style = DialogFragment.STYLE_NORMAL, theme = 0;
        switch ((mNum-1)%6) {
            case 1: style = DialogFragment.STYLE_NO_TITLE; break;
            case 2: style = DialogFragment.STYLE_NO_FRAME; break;
            case 3: style = DialogFragment.STYLE_NO_INPUT; break;
            case 4: style = DialogFragment.STYLE_NORMAL; break;
            case 5: style = DialogFragment.STYLE_NORMAL; break;
            case 6: style = DialogFragment.STYLE_NO_TITLE; break;
            case 7: style = DialogFragment.STYLE_NO_FRAME; break;
            case 8: style = DialogFragment.STYLE_NORMAL; break;
        }
        switch ((mNum-1)%6) {
            case 4: theme = android.R.style.Theme_Holo; break;
            case 5: theme = android.R.style.Theme_Holo_Light_Dialog; break;
            case 6: theme = android.R.style.Theme_Holo_Light; break;
            case 7: theme = android.R.style.Theme_Holo_Light_Panel; break;
            case 8: theme = android.R.style.Theme_Holo_Light; break;
        }
        setStyle(style, theme);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_dialog, container, false);
        View tv = v.findViewById(R.id.text);
        ((TextView)tv).setText("Dialog #" + mNum + ": using style "
                + getNameForNum(mNum));

        // Watch for button clicks.
        Button button = (Button)v.findViewById(R.id.changeSheep);
        button.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                // When button is clicked, call up to owning activity.
                ((SheepMenu)getActivity()).showDialog();
            }
        });

        return v;
    }*/

//}

