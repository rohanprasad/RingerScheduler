package mdg.iitr.ringerscheduler;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

public class Scheduler  {


	public void SetSchedule(Context context, long time1, long time2 , int ids)
    {		
        AlarmManager am= (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
        Intent intent_1 = new Intent(context, SignalRec.class);
        intent_1.putExtra("id", ids);
        PendingIntent pi_1 = PendingIntent.getBroadcast(context, ids, intent_1, PendingIntent.FLAG_UPDATE_CURRENT);
        am.set(AlarmManager.RTC_WAKEUP,time1, pi_1); 
        
        Intent intent_2 = new Intent(context, SgnalRec2.class);
        intent_2.putExtra("id", ids);
        PendingIntent pi_2 = PendingIntent.getBroadcast(context, ids, intent_2, PendingIntent.FLAG_UPDATE_CURRENT);
        am.set(AlarmManager.RTC_WAKEUP, time2, pi_2); 
        
       // Toast.makeText(context, "Set", Toast.LENGTH_LONG).show();
    }
 
    public void CancelAlarm(Context context, int ids)
    {
        Intent intent_1 = new Intent(context, SignalRec.class);
        PendingIntent pi_1 = PendingIntent.getBroadcast(context, ids, intent_1, PendingIntent.FLAG_UPDATE_CURRENT);
        intent_1.putExtra("id", ids);
        
        Intent intent_2 = new Intent(context, SgnalRec2.class);
        intent_2.putExtra("id", ids);
        PendingIntent pi_2 = PendingIntent.getBroadcast(context, ids, intent_2, PendingIntent.FLAG_UPDATE_CURRENT);
        
        AlarmManager am = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        am.cancel(pi_1);
        am.cancel(pi_2);
        
      //  Toast.makeText(context, "Removed",Toast.LENGTH_SHORT).show();
    }

}