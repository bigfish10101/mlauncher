package com.ml.mlauncher;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

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

                reloadAppsList(list);
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

    public void reloadAppsList(final List<?> list) {

        if (list.size() == 0 || list == null) return;

        ListView listView = (ListView) findViewById(R.id.app_list_view);
        if (listView != null) {

            listView.setAdapter(new BaseAdapter() {
                @Override
                public int getCount() {
                    return list.size();
                }

                @Override
                public Object getItem(int position) {
                    return String.valueOf(position);
                }

                @Override
                public long getItemId(int position) {
                    return position;
                }

                @Override
                public View getView(int position, View convertView, ViewGroup parent) {
                    View listItemView = convertView;
                    if (listItemView == null) {

                        listItemView = LayoutInflater.from(getApplicationContext()).inflate(R.layout.app_list_item_view, parent, false);
                    }

                    AppInfoModel appInfoModel = (AppInfoModel)list.get(position);
                    if (appInfoModel != null) {

                        TextView textView = (TextView) listItemView.findViewById(R.id.app_list_item_view_text_view);
                        if (textView != null) {

                            textView.setText(appInfoModel.label.toString() + " : " + appInfoModel.name.toString());
                        }

                        ImageView imgView = (ImageView) listItemView.findViewById(R.id.app_list_item_view_icon_img_view);
                        if (imgView != null) {

                            imgView.setImageDrawable(appInfoModel.icon);
                        }
                    }

                    return listItemView;
                }
            });

            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                    AppInfoModel appInfoModel = (AppInfoModel) list.get(position);
                    if (appInfoModel != null) {
                        Intent intent = getPackageManager().getLaunchIntentForPackage(appInfoModel.name.toString());
                        startActivity(intent);
                    }
                }
            });
        }
    }
}
