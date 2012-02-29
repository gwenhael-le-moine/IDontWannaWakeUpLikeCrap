package com.gwenhael.idontwannawakeuplikecrap;

import java.util.Calendar;
import java.util.TimeZone;
import java.util.Timer;
import java.util.TimerTask;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import android.content.Intent;
import android.provider.AlarmClock;

import com.gwenhael.idontwannawakeuplikecrap.AboutActivity;

public class IDontWannaWakeUpLikeCrapActivity
    extends Activity
{
    private static final int DEFAULT_NAP_TIME = 20;
    private static final int DEFAULT_FALL_ASLEEP_TIME = 14;

    protected LinearLayout main_layout;
    protected LinearLayout result_layout;
    protected TextView presentation;

    private Timer timer;

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main); // before trying to link widgets

        main_layout = (LinearLayout) findViewById( R.id.layout );
        result_layout = (LinearLayout) findViewById( R.id.result_layout );
        presentation = (TextView) findViewById( R.id.presentation );
        
        refresh(  );

        timer = new Timer(  );
        timer.schedule( new TimerRefresh(  ), 100, 200 );
    }
    
    @Override
    protected void onDestroy(  )
    {
        super.onDestroy();
        
        timer.cancel();
    }

    @Override
    public boolean onCreateOptionsMenu( Menu menu ) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch ( item.getItemId(  ) ) {
            case R.id.about:
                about(  );
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void about(  )
    {
        startActivity( new Intent(this, AboutActivity.class) );
    }

    private Button makeAlarmButton( Calendar cal )
    {
        Button alarmButton = new Button( this );
        alarmButton.setTag( cal.clone(  ) );
        alarmButton.setText( justTheTime( cal ) );
        alarmButton.setOnClickListener( new View.OnClickListener() {
                public void onClick(View v) {
                    Calendar cal = (Calendar) v.getTag(  );
                    int h = cal.get( Calendar.HOUR_OF_DAY );
                    int m = cal.get( Calendar.MINUTE );

                    Intent i = new Intent( AlarmClock.ACTION_SET_ALARM );
                    i.putExtra( AlarmClock.EXTRA_HOUR, h );
                    i.putExtra( AlarmClock.EXTRA_MINUTES, m );
                    startActivity(i);
                }
            } );

        return alarmButton;
    }
    
    private void refresh(  )
    {
        int time_to_fall_asleep = this.DEFAULT_FALL_ASLEEP_TIME;
        int nap_duration = this.DEFAULT_NAP_TIME;

        Calendar asleep = Calendar.getInstance( TimeZone.getDefault(  ) );
        Calendar nap = (Calendar)asleep.clone(  ); // what's with the casting crap?
        nap.add( Calendar.MINUTE, nap_duration );
        Calendar wake_up = (Calendar)asleep.clone(  ); // what's with the casting crap?
        wake_up.add( Calendar.MINUTE, time_to_fall_asleep );

        result_layout.removeAllViewsInLayout(  );

        result_layout.addView( makeAlarmButton( nap ) );

        for ( int i = 0 ; i < 6 ; i++ ) {
            wake_up.add( Calendar.HOUR, 1 );
            wake_up.add( Calendar.MINUTE, 30 );

            result_layout.addView( makeAlarmButton( wake_up ) );
        }
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
