package com.gwenhael.idontwannawakeuplikecrap;

import java.util.Calendar;

import android.app.Activity;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.view.View;
import android.widget.TextView;
import android.widget.Button;
import android.view.View.OnClickListener;

public class IDontWannaWakeUpLikeCrapActivity
    extends Activity
    implements OnClickListener
{
    private static final int DEFAULT_NAP_TIME = 20;
    private static final int DEFAULT_FALL_ASLEEP_TIME = 14;

    protected LinearLayout main_layout;
    protected Button refresh;
    protected TextView presentation;
    protected TextView result_text;
    
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main); // before trying to link widgets

        main_layout = (LinearLayout) findViewById( R.id.layout );
        presentation = (TextView) findViewById( R.id.presentation );
        result_text = (TextView) findViewById( R.id.result_text );
        refresh = (Button) findViewById( R.id.refresh );
        refresh.setOnClickListener( this );
        
        refresh(  );
    }

    @Override
    public void onClick( View v )
    {
        refresh(  );
    }

    private void refresh(  )
    {
        int time_to_fall_asleep = this.DEFAULT_FALL_ASLEEP_TIME;

        Calendar now = Calendar.getInstance();
        Calendar asleep = (Calendar)now.clone(  ); // what's with the casting crap?
        asleep.add( Calendar.MINUTE, time_to_fall_asleep );
        Calendar wake_up = (Calendar)asleep.clone(  );
        
        String r = new String(  );
        for ( int i = 0 ; i < 6 ; i++ ) {
            wake_up.add( Calendar.HOUR, 1 );
            wake_up.add( Calendar.MINUTE, 30 );
            // if ( ( i < 1 ) || ( i > 3 ) ) {
                r += justTheTime( wake_up ) + "\n";
            // }
        }
        result_text.setText( r );
    }

    private String justTheTime( Calendar c ) {
        String r = new String( ( (Calendar.getInstance()).get( Calendar.DAY_OF_YEAR ) != c.get( Calendar.DAY_OF_YEAR ) ? "tomorrow" : "today" ) + " at " + c.get( Calendar.HOUR_OF_DAY ) + ":" + ( ( c.get( Calendar.MINUTE ) < 10 ) ? "0" : "" ) + c.get( Calendar.MINUTE ) );

        return r;
    }
}
