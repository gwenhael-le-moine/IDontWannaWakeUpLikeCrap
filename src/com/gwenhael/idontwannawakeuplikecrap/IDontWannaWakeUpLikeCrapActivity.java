package com.gwenhael.idontwannawakeuplikecrap;

import java.util.Calendar;
import java.util.List;
import java.util.TimeZone;
import java.util.Timer;
import java.util.TimerTask;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.Bundle;
import android.provider.AlarmClock;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View.OnClickListener;
import android.view.View;
import android.widget.Button;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.gwenhael.idontwannawakeuplikecrap.AboutActivity;

public class IDontWannaWakeUpLikeCrapActivity
    extends Activity
{
    private static final int DEFAULT_NAP_DURATION = 20;
    private static final int DEFAULT_FALLING_ASLEEP_DURATION = 14;
    private static final int DEFAULT_SLEEP_CYCLE_DURATION = 90;
    private static final String DEFAULT_ALARM_INTENT = AlarmClock.ACTION_SET_ALARM;

    protected ViewGroup result_layout;
    protected boolean alarmIntentAvailable;

    private Timer timer;

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main); // before trying to link widgets

        result_layout = (ViewGroup) findViewById( R.id.result_layout );

        alarmIntentAvailable = isIntentAvailable( this, DEFAULT_ALARM_INTENT );

        if ( ! alarmIntentAvailable ) {
            Toast.makeText( getApplicationContext(  ), getString( R.string.noAlarmApp ), Toast.LENGTH_LONG ).show(  );
        }
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

    // from http://www.curious-creature.org/2008/12/15/android-can-i-use-this-intent/
    /**
     * Indicates whether the specified action can be used as an intent. This
     * method queries the package manager for installed packages that can
     * respond to an intent with the specified action. If no suitable package is
     * found, this method returns false.
     *
     * @param context The application's environment.
     * @param action The Intent action to check for availability.
     *
     * @return True if an Intent with the specified action can be sent and
     *         responded to, false otherwise.
     */
    private boolean isIntentAvailable( Context context, String action ) {
        final PackageManager packageManager = context.getPackageManager(  );
        final Intent intent = new Intent( action );
        List<ResolveInfo> list = packageManager.queryIntentActivities( intent, PackageManager.MATCH_DEFAULT_ONLY );
        return list.size(  ) > 0;
    }
    
    private Button makeAlarmButton( Calendar cal )
    {
        Button alarmButton = new Button( this );
        alarmButton.setTag( cal.clone(  ) );
        alarmButton.setText( justTheTime( cal ) );
        if ( alarmIntentAvailable ) {
            alarmButton.setOnClickListener( new View.OnClickListener() {
                    public void onClick(View v) {
                        Calendar cal = (Calendar) v.getTag(  );
                        int h = cal.get( Calendar.HOUR_OF_DAY );
                        int m = cal.get( Calendar.MINUTE );
                        
                        Intent alarmIntent = new Intent( DEFAULT_ALARM_INTENT );
                        alarmIntent.putExtra( AlarmClock.EXTRA_HOUR, h );
                        alarmIntent.putExtra( AlarmClock.EXTRA_MINUTES, m );
                        try {
                            startActivity( alarmIntent );
                        } catch( ActivityNotFoundException noAlarmApp ) {
                            Toast.makeText( getApplicationContext(  ), getString( R.string.noAlarmApp ), Toast.LENGTH_SHORT ).show(  );
                        }
                    }
                } );
        }
        else {
            alarmButton.setEnabled( alarmIntentAvailable );
        }

        return alarmButton;
    }
    
    private void refresh(  )
    {
        int time_to_fall_asleep = this.DEFAULT_FALLING_ASLEEP_DURATION;
        int nap_duration = this.DEFAULT_NAP_DURATION;
        int sleep_cycle_duration = this.DEFAULT_SLEEP_CYCLE_DURATION;

        Calendar asleep = Calendar.getInstance( TimeZone.getDefault(  ) );
        Calendar nap = (Calendar)asleep.clone(  ); // what's with the casting crap?
        Calendar wake_up = (Calendar)asleep.clone(  ); // what's with the casting crap?
        nap.add( Calendar.MINUTE, nap_duration );
        wake_up.add( Calendar.MINUTE, time_to_fall_asleep );

        result_layout.removeAllViewsInLayout(  );

        result_layout.addView( makeAlarmButton( nap ) );

        for ( int i = 0 ; i < 6 ; i++ ) {
            wake_up.add( Calendar.MINUTE, sleep_cycle_duration );

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
