package mdg.iitr.ringerscheduler;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.Window;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;


public class Schedule extends Activity  {

	private ImageButton b_add;
	
	ListView list;
	int idForList;
	Cursor cs;
	SharedPreferences s_pref;
	SharedPreferences.Editor s_pref_editor;
	List<String> description = new ArrayList<String>();
	List<String> reference;
	List_Adapter adaptor;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_schedule);
		
		b_add = (ImageButton) findViewById(R.id.add_schedule);
		list = (ListView) findViewById(R.id.schedules);
		
		set_all();
		
		registerForContextMenu(list);
		
		s_pref = getApplicationContext().getSharedPreferences("maps", Context.MODE_PRIVATE);
		s_pref_editor = s_pref.edit();
		
		b_add.setOnTouchListener(new OnTouchListener() {
			
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				// TODO Auto-generated method stub
				
				if(event.getAction() == MotionEvent.ACTION_DOWN)
				{
					b_add.setImageResource(R.drawable.add_down);
						Intent  i = new Intent(getApplicationContext(), Create.class );
						startActivity(i);
				}
				else{
					b_add.setImageResource(R.drawable.add);
				}
				
				return true;
			}
		});
		
	}
	
	

	@Override
	public void onCreateContextMenu(ContextMenu menu, View v,ContextMenuInfo menuInfo) {
		// TODO Auto-generated method stub
		super.onCreateContextMenu(menu, v, menuInfo);
		AdapterContextMenuInfo info = (AdapterContextMenuInfo) menuInfo;
        idForList = info.position;
        idForList++;
        Toast.makeText(getApplicationContext(), "selected pos: "+ idForList , Toast.LENGTH_SHORT).show();
        MenuInflater inflater = getMenuInflater();
	    inflater.inflate(R.menu.listoption, menu);
	}


	@Override
	public boolean onContextItemSelected(MenuItem item) {
		
		switch (item.getItemId()) {
	    case R.id.remove_item:
	    	int ids = s_pref.getInt(Integer.toString(idForList), 10);
	    	Toast.makeText(this, "ID = "+ids, Toast.LENGTH_SHORT).show();
	    	Scheduler handler = new Scheduler();
	    	Context cnt = this.getApplicationContext();
	    	handler.CancelAlarm(cnt, ids);
	    	Dbase db = new Dbase(this.getApplicationContext());
	    	db.del(ids);

	    	{
	    		Intent i = getIntent();
		    	finish();
		    	startActivity(i);
	    	}
	    	return true;
		}
	  return false;
	}
	
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		
		super.onResume();
		set_all();
		if(Globals.settin)
		{
			Toast.makeText(getApplicationContext(), "Item pos: "+ list.getCount(), Toast.LENGTH_SHORT).show();
			if(s_pref.contains(Integer.toString(list.getCount()-1)))
			{
				s_pref_editor.remove(Integer.toString(list.getCount()-1));
			}
			s_pref_editor.putInt(Integer.toString(list.getCount()-1), Globals.ids);
			s_pref_editor.commit();
		}
		Globals.settin = false;
	}
	
	public void set_all(){
		
		
		Dbase db = new Dbase(getApplicationContext());
		db.getReadableDatabase();
		
		
		reference = db.get_list();
		description.clear();
		cs = Globals.cursor;
		if(cs.moveToFirst())
		{
			do
			{ 	int day_i = Integer.parseInt(cs.getString(5));
				String day;
				if(day_i == 0)
					day = "Sunday";
				else if(day_i == 1)
					day = "Monday";
				else if(day_i == 2)
					day = "Tuesday";
				else if(day_i == 3)
					day = "Wednesday";
				else if(day_i == 4)
					day = "Thursday";
				else if(day_i == 5)
					day = "Friday";
				else if(day_i == 6)
					day = "Saturday";
				else
					day = day_i +""	;
				
				String time = "";
				
				for(int i = 1;i<5;i++)
				{	
					if(i==3)
						time += " - ";
					else if(i>1)
						time += ":";
					if(Integer.parseInt(cs.getString(i)) < 10)
						time += "0"+cs.getString(i);
					else
						time +=cs.getString(i);
					
				}

				description.add(day+"||"+time);
			}while(cs.moveToNext());
		}
		
		adaptor = new List_Adapter(Schedule.this, description, reference);
		
		try{
		list.setAdapter(adaptor);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}

	}
	
}
