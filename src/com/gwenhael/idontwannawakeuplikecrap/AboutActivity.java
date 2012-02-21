package com.gwenhael.idontwannawakeuplikecrap;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;

public class AboutActivity
    extends Activity
    implements OnClickListener
{
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.about); // before trying to link widgets
    }

    @Override
    public void onClick( View v )
    {
        this.finish();
    }
}
