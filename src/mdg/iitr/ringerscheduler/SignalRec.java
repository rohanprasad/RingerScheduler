package mdg.iitr.ringerscheduler;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.os.PowerManager;
import android.widget.Toast;

public class SignalRec extends BroadcastReceiver{

	//@SuppressWarnings("deprecation")
	@Override
	public void onReceive(Context context, Intent intent) {
		// TODO Auto-generated method stub
		
		PowerManager p_manager = (PowerManager) context.getSystemService(Context.POWER_SERVICE);
		PowerManager.WakeLock wake_lock = p_manager.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, "Partial wake");
		wake_lock.acquire();
		
		AudioManager audi = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
		audi.setRingerMode(AudioManager.RINGER_MODE_VIBRATE);
		Toast.makeText(context, "Vibrate Mode", Toast.LENGTH_LONG).show();
		
		wake_lock.release();
		
		
	}

}
