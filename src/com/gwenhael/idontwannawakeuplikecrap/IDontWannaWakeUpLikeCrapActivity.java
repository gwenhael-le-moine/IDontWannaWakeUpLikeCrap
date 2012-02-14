package com.gwenhael.idontwannawakeuplikecrap;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
// import android.widget.Toast;
import android.widget.TextView;
import android.widget.EditText;
// import android.widget.TimePicker;
// import android.widget.Button;
import java.util.Calendar;

public class IDontWannaWakeUpLikeCrapActivity
    extends Activity
    implements OnClickListener
{
    protected EditText falling_asleep_time;
    protected TextView result_text;
    
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main); // before trying to link widgets

        falling_asleep_time = (EditText) findViewById( R.id.falling_asleep_time );
        result_text = (TextView) findViewById( R.id.result_text );
    }

    @Override
    public void onClick( View view )
    {
        // Toast.makeText(this, falling_asleep_time.getText().toString(), Toast.LENGTH_SHORT).show();
        int time_to_fall_asleep = (new Integer( falling_asleep_time.getText().toString() )).intValue(  );

        Calendar now = Calendar.getInstance();
        Calendar asleep = (Calendar)now.clone(  ); // what's with the casting crap?
        asleep.add( Calendar.MINUTE, time_to_fall_asleep );
        Calendar wake_up = (Calendar)asleep.clone(  );
        
        String r = new String(  );
        for ( int i = 0 ; i < 6 ; i++ ) {
            wake_up.add( Calendar.HOUR, 1 );
            wake_up.add( Calendar.MINUTE, 30 );
            r += justTheTime( wake_up ) + "\n";
        }
        result_text.setText( r );
    }

    private String justTheTime( Calendar c ) {
        String r = new String( c.get( Calendar.HOUR_OF_DAY ) + ":" );
        r += ( ( c.get( Calendar.MINUTE ) < 10 ) ? "0" : "" ) + c.get( Calendar.MINUTE );
        r = ( (Calendar.getInstance()).get( Calendar.DAY_OF_YEAR ) != c.get( Calendar.DAY_OF_YEAR ) ? "tomorrow" : "today" ) + " at " + r;

        return r;
    }
}
