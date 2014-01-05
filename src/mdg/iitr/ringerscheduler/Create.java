package mdg.iitr.ringerscheduler;


import java.util.Calendar;



import java.util.GregorianCalendar;

import android.app.Activity;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;
import android.widget.AdapterView.OnItemSelectedListener;

public class Create extends Activity {
	
	
	private int fhour;
	private int fmin;
	private int thour;
	private int tmin;
	private int sday;
	static final int idForTime = 992;
	static final int idFortTime = 991;
	private boolean sft;
	private boolean stt;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.create_layout);
		
		sft = false;
		stt = false;
		
		Spinner day_s = (Spinner) findViewById(R.id.day_select);
		Button b_time = (Button) findViewById (R.id.btime);
		Button b_t_time = (Button) findViewById (R.id.b_t_time);
		Button b_set = (Button) findViewById(R.id.s_task);
		
		day_s.setVerticalFadingEdgeEnabled(true);
		day_s.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> adp, View arg1,
					int item, long arg3) {
				// TODO Auto-generated method stub
				//long day = adp.getItemIdAtPosition(item);
				sday =  (int) adp.getItemIdAtPosition(item);
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub
				
			}
		});
	
		
		b_time.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				sft = true;
				final Calendar cal = Calendar.getInstance();
				fhour = cal.get(Calendar.HOUR_OF_DAY);
				fmin = cal.get(Calendar.MINUTE);
				showDialog(idForTime);
			}
		});
		
		b_t_time.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				stt = true;
				final Calendar cal = Calendar.getInstance();
				thour = cal.get(Calendar.HOUR_OF_DAY);
				tmin = cal.get(Calendar.MINUTE);
				showDialog(idFortTime);
			}
		});
	
		b_set.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if(sft && stt)
				c_schedule();
				else
					Toast.makeText(getApplicationContext(), "Set Time", Toast.LENGTH_SHORT).show();
			}
		});
		
		
	}
	
	
			
			private TimePickerDialog.OnTimeSetListener setFTime = new TimePickerDialog.OnTimeSetListener() {
				
				@Override
				public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
					// TODO Auto-generated method stub
										
					fhour = hourOfDay;
					fmin = minute;
					TextView tv = (TextView)findViewById(R.id.f_time_text);
					if(fmin < 10)
						tv.setText(fhour+":0"+fmin);
					else
						tv.setText(fhour+":"+fmin);
				}
			};
			
			private TimePickerDialog.OnTimeSetListener settTime = new TimePickerDialog.OnTimeSetListener() {
				
				@Override
				public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
					// TODO Auto-generated method stub

					thour = hourOfDay;
					tmin = minute;
					TextView tv2 = (TextView)findViewById(R.id.t_time_text);
					if(tmin < 10)
						tv2.setText(thour+":0"+tmin);
					else
						tv2.setText(thour+":"+tmin);
				}
			};
			
			protected Dialog onCreateDialog(int id) {
				switch (id) {
				case idForTime :
					return new TimePickerDialog(this,setFTime, fhour, fmin, true);
				case idFortTime :
					return new TimePickerDialog(this,settTime, thour, tmin, true);
		 		}
				return null;
			}

			public void c_schedule()
			{
				
				if(fhour*60 + fmin > thour*60+tmin)
				{
					Toast.makeText(this,"Improper time", Toast.LENGTH_SHORT).show();
					return;
				}
				
				Dbase db = new Dbase(this);
				db.add(fhour, fmin, thour, tmin, sday);
				db.close();
								
				int id = 0;
				SQLiteDatabase sqldb = db.getReadableDatabase();
				String comm = "SELECT * FROM schedule WHERE f_time_hr="+ fhour +
								" AND f_time_mn=" + fmin + 
								" AND t_time_hr=" + thour +
								" AND t_time_mn=" + tmin +
								" AND day=" + sday;
				Cursor c = sqldb.rawQuery(comm, null);
				if(c.moveToFirst())
				{
						id = c.getInt(c.getColumnIndex("ID"));
				}
				
				
				int a = sday * 10000000 + fhour *1000000 + fmin *10000 + thour * 100 + tmin ;
				
				final Calendar cals = Calendar.getInstance();
				fhour = fhour - cals.get(Calendar.HOUR_OF_DAY);
				fmin = fmin - cals.get(Calendar.MINUTE);
				thour = thour - cals.get(Calendar.HOUR_OF_DAY);
				tmin = tmin - cals.get(Calendar.MINUTE);
				long day = cals.get(Calendar.DAY_OF_WEEK) - 1;
				long secs = cals.get(Calendar.SECOND);
				long milli = cals.get(Calendar.MILLISECOND);
								
				if(day>sday)
				{
					day = day - sday;
				}
				else
				{
					day = sday - day;
				}
				
				
				long time1 = new GregorianCalendar().getTimeInMillis() ;
				time1 = time1 - (secs*1000) - milli;
				time1 = time1 + (day*24*60*60*1000);
				long time2 = time1;
				time1 = time1 + fhour*3600000 + fmin*60000;
				time2 = time2 + thour*3600000 + tmin*60000;
				
				long rem_time = new GregorianCalendar().getTimeInMillis();
				rem_time = time1 - rem_time;
				rem_time = rem_time / (100*60);
				Toast.makeText(this, "ID:"+id, Toast.LENGTH_SHORT).show();
				
				
				Context cont = getApplicationContext();
				Scheduler handler = new Scheduler();
				handler.SetSchedule(cont, time1, time2, id);
				finish();
			}
}
