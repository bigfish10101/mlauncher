package com.ml.mlauncher;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import java.util.List;

import Comm.CommTool;
import Models.AppInfoModel;

public class LauncherActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_launcher);

        CommTool.getInstance().loadApps(new CommTool.OnLoadAppsListener() {
            @Override
            public void OnSuccLoadApps(List<AppInfoModel> list) {

                Log.i("OnSuccLoadApps -> ", String.valueOf(list.size()));
            }

            @Override
            public void OnFailLoadApps() {

                Log.i("OnFailLoadApps", "Fail to Load.....");
            }
        }, getPackageManager());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_launcher, menu);
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
