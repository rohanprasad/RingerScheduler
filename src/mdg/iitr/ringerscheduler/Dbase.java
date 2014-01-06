package mdg.iitr.ringerscheduler;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class Dbase extends SQLiteOpenHelper {

	private static final int DATABASE_VERSION = 1;
	private static final String table_name = "schedule";

	
	public Dbase(Context context) {
		super(context, "ringer.db",null, DATABASE_VERSION);
		// TODO Auto-generated constructor stub
	}

	
	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		String create_table = "create table " + table_name + "(ID INTEGER PRIMARY KEY AUTOINCREMENT, f_time_hr integer, f_time_mn integer, t_time_hr integer, t_time_mn integer, day integer)";
		
		db.execSQL(create_table);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
		Log.w(Dbase.class.getName(),
		        "Upgrading database from version " + oldVersion + " to "
		                + newVersion + ", which will destroy all old data");
		db.execSQL("Drop table if exists "+table_name);
		onCreate(db);
	}
	
	public void add(int f_hr, int f_mn, int t_hr, int t_mn, long day){
		
		SQLiteDatabase database = this.getWritableDatabase();
		ContentValues values = new ContentValues();
		
		values.put("f_time_hr", f_hr);
		values.put("f_time_mn", f_mn);
		values.put("t_time_hr", t_hr);
		values.put("t_time_mn", t_mn);
		values.put("day", day);
		
		database.insert(table_name, null, values);
		database.close();
	}
	
	public void del(int id){
		
		String ids[] = {Integer.toString(id)};
		SQLiteDatabase database = this.getWritableDatabase();
		database.delete(table_name, "ID = ?", ids);
		database.close();
	}
	
	public List<String> get_list(){
		
		List<String> all_data = new ArrayList<String>();
		String sl = "SELECT * FROM "+ table_name;
		
		SQLiteDatabase db = this.getWritableDatabase();
		Cursor cs = db.rawQuery(sl, null);
		Globals.cursor = cs;
		
		if(cs.moveToFirst()){
			do{
				String s = new String();
				s += cs.getString(1); 
				
				all_data.add(s);
			}while(cs.moveToNext());
		}
		
		return all_data;
		
	}
	
	
	
}