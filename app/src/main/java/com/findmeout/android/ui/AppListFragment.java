package com.findmeout.android.ui;

/**
 * Created by umesh0492 on 19/07/16.
 */
public class AppListFragment {
}/*extends Fragment {


    RecyclerView rvAppList;

    @Nullable
    @Override
    public View onCreateView (LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View mRoot = inflater.inflate (R.layout.app_list_fragment, container, false);

        rvAppList = (RecyclerView) mRoot.findViewById (R.id.app_list);
        rvAppList.setHasFixedSize (true);
        rvAppList.setLayoutManager (new LinearLayoutManager (getContext ()));

        showList ();
        return mRoot;
    }


    private void showList () {

        AppListAdapter appListAdapter = new AppListAdapter (this, getAllAvailableApp ());
        rvAppList.setAdapter (appListAdapter);

    }

    public enum AppPackageName {
        MEDIUM ("com.medium.reader"), QUORA ("com.quora.android"), TUMBLR ("com.tumblr"),
        WORDPRESS ("org.wordpress.android"), CHROME ("com.android.chrome"),
        HIKE ("com.bsb.hike"),
        GMAIL ("com.google.android.gm"), HANGOUT ("com.google.android.talk"),
        MESSENGER ("com.google.android.apps.messaging"),
        WHATSAPP ("com.whatsapp"), FACEBOOK ("com.facebook.katana");

        private String typeCode;

        private AppPackageName (String s) {
            typeCode = s;
        }

        public String getTypeCode () {
            return typeCode;
        }
    }

    ArrayList<AppListModel> getAllAvailableApp () {

        PackageManager pm = MainApplication.context.getPackageManager ();

        ArrayList<AppListModel> appListModel = new ArrayList<> ();

        Intent intent = new Intent (Intent.ACTION_MAIN, null);
        intent.addCategory (Intent.CATEGORY_LAUNCHER);
        ArrayList<String> packagesAccepted = DataClient.getAppPackageList ();
        List<ResolveInfo> resInfo = pm.queryIntentActivities (intent, 0);
        Log.e ("ResolveInfo", resInfo.size () + "");
        for (ResolveInfo ri : resInfo) {
            // Extract the label, append it, and repackage it in a LabeledIntent
            String packageName = ri.activityInfo.packageName;

            if (packagesAccepted.contains (packageName)) {
                AppListModel app = new AppListModel ();
                app.setPackage_name (packageName);
                app.setApp_icon (ri.loadIcon (pm));
                app.setApp_name (ri.loadLabel (pm).toString ());
                appListModel.add (app);
            }
        }
        return appListModel;
    }
}
*/
