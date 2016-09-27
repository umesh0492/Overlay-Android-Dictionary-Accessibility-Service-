package com.findmeout.android.ui;

/**
 * Created by umesh0492 on 11/10/15.
 */
public class AppListAdapter {
}
/*extends RecyclerView.Adapter<AppListAdapter.ItemViewHolder> {

    final String TAG = AppListAdapter.class.getSimpleName ();
    private AppListFragment mFragment;
    private ArrayList<AppListModel> mAppListModels = new ArrayList<> ();

    public AppListAdapter (AppListFragment fragment,
                           List<AppListModel> mAppListModels) {
        this.mAppListModels.addAll (mAppListModels);
        mFragment = fragment;
    }

    @Override
    public ItemViewHolder onCreateViewHolder (ViewGroup parent, int viewType) {
        // create a new view based on type.
        View itemLayoutView = LayoutInflater
                .from (mFragment.getContext ()).inflate (R.layout.adapter_app_list, parent, false);

        return new ItemViewHolder (itemLayoutView);
    }

    @Override
    public void onBindViewHolder (final ItemViewHolder holder, final int position) {

        holder.appIcon.setImageDrawable (mAppListModels.get (position).getApp_icon ());
        holder.appName.setText (mAppListModels.get (position).getApp_name ());
        holder.checkBox.setOnCheckedChangeListener (new CompoundButton.OnCheckedChangeListener () {
            @Override
            public void onCheckedChanged (CompoundButton compoundButton, boolean b) {

            }
        });
    }

    @Override
    public int getItemCount () {
        return mAppListModels.size ();
    }

    // inner class to hold a reference to each item of RecyclerView
    public static class ItemViewHolder extends RecyclerView.ViewHolder {

        private TextView appName;
        private ImageView appIcon;
        private CheckBox checkBox;

        public ItemViewHolder (View itemLayoutView) {
            super (itemLayoutView);

            appName = (TextView) itemLayoutView.findViewById (R.id.app_name);
            checkBox = (CheckBox) itemLayoutView.findViewById (R.id.check_box);
            appIcon = (ImageView) itemLayoutView.findViewById (R.id.app_icon);

        }
    }
}
*/
