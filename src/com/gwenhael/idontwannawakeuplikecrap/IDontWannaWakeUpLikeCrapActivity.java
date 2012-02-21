package com.gwenhael.idontwannawakeuplikecrap;

import java.util.Calendar;
import java.util.Timer;
import java.util.TimerTask;

import android.app.Activity;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.view.View;
import android.widget.TextView;
import android.view.View.OnClickListener;

public class IDontWannaWakeUpLikeCrapActivity
    extends Activity
{
    private static final int DEFAULT_NAP_TIME = 20;
    private static final int DEFAULT_FALL_ASLEEP_TIME = 14;

    protected LinearLayout main_layout;
    protected TextView presentation;
    protected TextView result_text;

    private Timer timer;

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main); // before trying to link widgets

        main_layout = (LinearLayout) findViewById( R.id.layout );
        presentation = (TextView) findViewById( R.id.presentation );
        result_text = (TextView) findViewById( R.id.result_text );
        
        refresh(  );

        timer = new Timer(  );
        timer.schedule( new TimerRefresh(  ), 100, 200 );
    }

    protected void onDestroy()
    {
        super.onDestroy();
        
        timer.cancel();
    }

    private void refresh(  )
    {
        int time_to_fall_asleep = this.DEFAULT_FALL_ASLEEP_TIME;

        Calendar asleep = Calendar.getInstance();
        asleep.add( Calendar.MINUTE, time_to_fall_asleep );
        Calendar wake_up = (Calendar)asleep.clone(  ); // what's with the casting crap?
        
        String r = new String(  );
        for ( int i = 0 ; i < 6 ; i++ ) {
            wake_up.add( Calendar.HOUR, 1 );
            wake_up.add( Calendar.MINUTE, 30 );
            r += justTheTime( wake_up ) + "\n";
        }
        result_text.setText( r );
    }

    private String justTheTime( Calendar c ) {
        String r = new String( ( (Calendar.getInstance()).get( Calendar.DAY_OF_YEAR ) != c.get( Calendar.DAY_OF_YEAR ) ? getString( R.string.tomorrow ) : getString( R.string.today ) ) + getString( R.string.at ) + c.get( Calendar.HOUR_OF_DAY ) + ":" + ( ( c.get( Calendar.MINUTE ) < 10 ) ? "0" : "" ) + c.get( Calendar.MINUTE ) );

        return r;
    }

    class TimerRefresh extends TimerTask
    {
        public void run()
        {
            IDontWannaWakeUpLikeCrapActivity.this.runOnUiThread(new Runnable()
                {
                    public void run()
                    {
                        refresh(  );
                    }
                });
        }
    }
}
