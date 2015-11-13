package Comm;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;

import java.util.ArrayList;
import java.util.List;

import Models.AppInfoModel;

/**
 * Created by Administrator on 2015/11/13 0013.
 */
public class CommTool {

    private static CommTool _instance = null;

    public interface OnLoadAppsListener {

        void OnSuccLoadApps(List<AppInfoModel> list);

        void OnFailLoadApps();
    }

    public static CommTool getInstance() {
        if (_instance == null) {

            _instance = new CommTool();
        }
        return _instance;
    }

    public void loadApps(final OnLoadAppsListener listener, PackageManager manager) {

        if (manager == null) {

            listener.OnFailLoadApps();
            return;
        }

        ArrayList<AppInfoModel> list = new ArrayList<>();

        Intent intent = new Intent(Intent.ACTION_MAIN, null);
        intent.addCategory(Intent.CATEGORY_LAUNCHER);

        List<ResolveInfo> resolvesList = manager.queryIntentActivities(intent, 0);
        for (ResolveInfo ri:resolvesList) {

            AppInfoModel appInfoModel = new AppInfoModel();

            appInfoModel.label = ri.loadLabel(manager);
            appInfoModel.name = ri.activityInfo.packageName;
            appInfoModel.icon = ri.activityInfo.loadIcon(manager);

            list.add(appInfoModel);
        }

        if (list.size() > 0) {

            listener.OnSuccLoadApps(list);
        } else {

            listener.OnFailLoadApps();
        }
    }
}
