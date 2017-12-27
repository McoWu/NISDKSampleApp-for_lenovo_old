package ai.naturali.sdksample;

import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import com.singulariti.niapp.NaturaliSDK;
import com.singulariti.niapp.NaturaliSDK.ActionCallback;
import com.singulariti.niapp.NaturaliSDK.QueryCallback;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!NaturaliSDK.checkAccessibilityService(MainActivity.this)) {
                    startActivity(new Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS));
                } else {
                    NaturaliSDK.initialize();
                    NaturaliSDK.query(MainActivity.this, "微信扫一扫", new QueryCallback() {
                        @Override
                        public void onResult(boolean b) {
                            if (b) {
                                NaturaliSDK.doAction(new ActionCallback() {
                                    @Override
                                    public void onSuccess() {
                                    }

                                    @Override
                                    public void onFail() {
                                    }

                                    @Override
                                    public void onCancel() {
                                    }
                                });
                            } else {
                                // do something else
                            }
                        }
                    });
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
