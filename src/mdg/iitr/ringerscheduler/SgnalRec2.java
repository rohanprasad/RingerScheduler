package mdg.iitr.ringerscheduler;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.widget.Toast;

public class SgnalRec2 extends BroadcastReceiver {

	//@SuppressWarnings("deprecation")
	@Override
	public void onReceive(Context context, Intent intent) {
		// TODO Auto-generated method stub
		AudioManager audi = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
		audi.setRingerMode(AudioManager.RINGER_MODE_NORMAL);
		Toast.makeText(context, "Normal Ringer", Toast.LENGTH_LONG).show();
	}

}
