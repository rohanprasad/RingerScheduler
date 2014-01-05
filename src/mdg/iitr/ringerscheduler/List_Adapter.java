package mdg.iitr.ringerscheduler;


import java.util.List;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;


public class List_Adapter extends ArrayAdapter<String> {

	private final Activity context;
	private final List<String> Description;
	
	//constructor
	public List_Adapter(Activity context, List<String> data, List<String> id) {
		super(context, R.layout.item, id);
		// TODO Auto-generated constructor stub
		this.context = context;
		this.Description = data;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		LayoutInflater inflator = context.getLayoutInflater();
		
		View my_view = inflator.inflate(R.layout.item, null, false);
		
		TextView time_t = (TextView) my_view.findViewById(R.id.items);
		TextView day_t = (TextView) my_view.findViewById(R.id.day);
		
		try{
		String temp = Description.get(position);
		String temp1 = temp;
		String temp2 = temp.substring(4);
		time_t.setText(temp1);
		day_t.setText(temp1);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
		return my_view;
	}
}
