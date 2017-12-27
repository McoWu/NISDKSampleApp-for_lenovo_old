package ai.naturali.sdksample;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.singulariti.niapp.NaturaliSDK;
import com.singulariti.niapp.NaturaliSDK.ActionCallback;
import com.singulariti.niapp.NaturaliSDK.QueryCallback;

import java.util.ArrayList;

import ai.naturali.sdksample.fan.Info;
import ai.naturali.sdksample.fan.Utils2;

public class MainActivity extends AppCompatActivity {

    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static String[] PERMISSIONS_STORAGE = {
            "android.permission.READ_EXTERNAL_STORAGE",
            "android.permission.WRITE_EXTERNAL_STORAGE"};
    Handler handler = new Handler();
    private int i;

    public static void verifyStoragePermissions(Activity activity) {

        try {
            //检测是否有写的权限
            int permission = ActivityCompat.checkSelfPermission(activity,
                    "android.permission.WRITE_EXTERNAL_STORAGE");
            if (permission != PackageManager.PERMISSION_GRANTED) {
                // 没有写的权限，去申请写的权限，会弹出对话框
                ActivityCompat.requestPermissions(activity, PERMISSIONS_STORAGE, REQUEST_EXTERNAL_STORAGE);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        verifyStoragePermissions(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        new Utils2.ExcelDataLoader().execute();

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        i = 0;
        fab.setOnClickListener(new View.OnClickListener() {

            private ArrayList<Info> infos;

            @Override
            public void onClick(View view) {
                if (!NaturaliSDK.checkAccessibilityService(MainActivity.this)) {
                    startActivity(new Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS));
                } else {
                    NaturaliSDK.initialize();
                    infos = Utils2.sortByName();
                    Log.i("--------------", "onClick: "+infos.toString());
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            NaturaliSDK.query(MainActivity.this, infos.get(i).getName().toString(), new QueryCallback() {
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
                            i++;
                            handler.postDelayed(this,10000);
                        }
                    }, 10000);


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
