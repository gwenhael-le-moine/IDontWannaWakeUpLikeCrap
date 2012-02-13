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
    
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main); // 
        
        falling_asleep_time = (EditText) findViewById( R.id.falling_asleep_time );
    }

    public void onClick( View view )
    {
        Toast.makeText(this, falling_asleep_time.getText().toString(), Toast.LENGTH_SHORT).show();
    }
}
