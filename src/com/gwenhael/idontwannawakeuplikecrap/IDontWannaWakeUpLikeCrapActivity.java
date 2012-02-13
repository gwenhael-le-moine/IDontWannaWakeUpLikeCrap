package com.gwenhael.idontwannawakeuplikecrap;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Toast;
import android.widget.TextView;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Button;

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
        setContentView(R.layout.main); // 
        
        falling_asleep_time = (EditText) findViewById( R.id.falling_asleep_time );
        result_text = (TextView) findViewById( R.id.result_text );
    }

    public void onClick( View view )
    {
        Toast.makeText(this, falling_asleep_time.getText().toString(), Toast.LENGTH_SHORT).show();

        result_text.setText( falling_asleep_time.getText().toString() );
    }
}
