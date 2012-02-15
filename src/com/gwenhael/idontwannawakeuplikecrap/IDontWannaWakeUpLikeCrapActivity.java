package com.gwenhael.idontwannawakeuplikecrap;

import java.util.Calendar;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
// import android.widget.Toast;
import android.widget.TextView;
import android.widget.EditText;
// import android.widget.TimePicker;
import android.text.TextWatcher;
import android.text.Editable;


public class IDontWannaWakeUpLikeCrapActivity
    extends Activity
    implements TextWatcher
{
    private static final int DEFAULT_NAP_TIME = 20;
    private static final int DEFAULT_FALL_ASLEEP_TIME = 14;
    
    protected EditText falling_asleep_time;
    protected TextView result_text;
    
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main); // before trying to link widgets

        falling_asleep_time = (EditText) findViewById( R.id.falling_asleep_time );
        falling_asleep_time.addTextChangedListener(this);
        result_text = (TextView) findViewById( R.id.result_text );

        falling_asleep_time.setText( new String( ""+DEFAULT_FALL_ASLEEP_TIME ) );
    }

    @Override
    public void afterTextChanged(Editable s)
    {
        update(  );
    }
    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {  }
    @Override
    public void onTextChanged(CharSequence s, int start, int count, int after) {  }

    private void update(  )
    {
        // Toast.makeText(this, falling_asleep_time.getText().toString(), Toast.LENGTH_SHORT).show();
        int time_to_fall_asleep;
        try {
            time_to_fall_asleep = (new Integer( falling_asleep_time.getText().toString() )).intValue(  );
        } catch( Exception e ) {
            time_to_fall_asleep = 0;
        }

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
        String r = new String( ( (Calendar.getInstance()).get( Calendar.DAY_OF_YEAR ) != c.get( Calendar.DAY_OF_YEAR ) ? "tomorrow" : "today" ) + " at " + c.get( Calendar.HOUR_OF_DAY ) + ":" + ( ( c.get( Calendar.MINUTE ) < 10 ) ? "0" : "" ) + c.get( Calendar.MINUTE ) );

        return r;
    }
}
