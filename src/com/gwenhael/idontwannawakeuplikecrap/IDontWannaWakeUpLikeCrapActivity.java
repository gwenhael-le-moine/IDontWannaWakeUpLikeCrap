package com.gwenhael.idontwannawakeuplikecrap;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Toast;

public class IDontWannaWakeUpLikeCrapActivity
    extends Activity
    implements OnClickListener
{
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
    }

    public void onClick( View view )
    {
        Toast.makeText(this, "Coucou n°2", Toast.LENGTH_SHORT).show();
    }
}
