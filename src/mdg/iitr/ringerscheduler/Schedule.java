package mdg.iitr.ringerscheduler;

import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ImageButton;
import android.app.Activity;
import android.content.Intent;

public class Schedule extends Activity {

	private ImageButton add;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_schedule);
		
    add = (ImageButton) findViewById(R.id.action_settings);
    
//    add.setOnClickListener(new View.OnClickListener() {
//		
//		@Override
//		public void onClick(View v) {
//			// TODO Auto-generated method stub
//		//Intent i = new Intent(getApplicationContext(), Create.class);
//		//startActivity(i);
//		}
//	});
//	}
	}
}
