package mdg.iitr.ringerscheduler;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.Window;
import android.widget.ImageButton;
import android.widget.ListView;


public class Schedule extends Activity  {

	private ImageButton b_add;
	
	ListView list;
	Cursor cs;
	List<String> description = new ArrayList<String>();
	List<String> reference;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_schedule);
		
		b_add = (ImageButton) findViewById(R.id.add_schedule);
		list = (ListView) findViewById(R.id.schedules);
				
		Dbase db = new Dbase(getApplicationContext());
		db.getReadableDatabase();
		
		
		reference = db.get_list();
		
		cs = Globals.cursor;
		if(cs.moveToFirst())
		{
			do
			{
				description.add(cs.getString(1)+":"+cs.getString(2)+" , "+cs.getString(3)+":"+cs.getString(4));
				System.out.println(cs.getString(1)+":"+cs.getString(2)+" , "+cs.getString(3)+":"+cs.getString(4)+","+cs.getString(5)+","+cs.getString(0));
			}while(cs.moveToNext());
		}
		
		List_Adapter adaptor = new List_Adapter(Schedule.this, description, reference);
		
		try{
		list.setAdapter(adaptor);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
		
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
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		
		if(cs.moveToFirst())
		{
			do
			{
				description.add(cs.getString(1)+","+cs.getString(2)+","+cs.getString(3)+","+cs.getString(4));
				System.out.println(cs.getString(1)+","+cs.getString(2)+","+cs.getString(3)+","+cs.getString(4)+","+cs.getString(5)+","+cs.getString(0));
			}while(cs.moveToNext());
		}
		
		List_Adapter adaptor = new List_Adapter(Schedule.this, description, reference);
		
		try{
		list.setAdapter(adaptor);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
	
	
}
