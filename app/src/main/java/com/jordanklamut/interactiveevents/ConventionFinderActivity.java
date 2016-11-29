package com.jordanklamut.interactiveevents;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashMap;
import java.util.Map;

public class ConventionFinderActivity extends AppCompatActivity {

    private int[] mTabsIcons = {
            R.drawable.ic_qr_selector,
            R.drawable.ic_search_selector,
            R.drawable.ic_favorite_selector};

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        TabLayout mTabLayout;
        //GET ALL FROM PHP AND CREATE SQLite
        new GetAllConventionsPHPtoSQLite().execute();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.convention_finder_activity);

        // Setup the viewPager
        final ViewPager viewPager = (ViewPager) findViewById(R.id.view_pager);

        final MyPagerAdapter pagerAdapter = new MyPagerAdapter(getSupportFragmentManager());
        if (viewPager != null)
            viewPager.setAdapter(pagerAdapter);

        mTabLayout = (TabLayout) findViewById(R.id.tab_layout);
        if (mTabLayout != null) {
            mTabLayout.setupWithViewPager(viewPager);

            for (int i = 0; i < mTabLayout.getTabCount(); i++) {
                TabLayout.Tab tab = mTabLayout.getTabAt(i);
                if (tab != null)
                    tab.setCustomView(pagerAdapter.getTabView(i));
            }
            mTabLayout.getTabAt(0).getCustomView().setSelected(true);
        }

        mTabLayout.setOnTabSelectedListener(
            new TabLayout.ViewPagerOnTabSelectedListener(viewPager) {
                @Override
                public void onTabSelected(TabLayout.Tab tab) {
                    viewPager.setCurrentItem(tab.getPosition());
                    pagerAdapter.refreshFragment(tab.getPosition());
                }
            });

        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        viewPager.setCurrentItem(1);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private class MyPagerAdapter extends FragmentPagerAdapter {

        public final int PAGE_COUNT = 3;
        private final String[] mTabsTitle = {"Scan", "Search", "Favorites"};

        private Map<Integer, String> mFragmentTags;
        private FragmentManager mFragmentManager;
        private Context mContext;

        private ConventionFinderFragment_Scan tabScan;
        private ConventionFinderFragment_Search tabSearch;
        private ConventionFinderFragment_Favorites tabFavorite;

        public MyPagerAdapter(FragmentManager fm) {
            super(fm);
            mFragmentManager = fm;
            mFragmentTags = new HashMap<Integer, String>();
        }

        public View getTabView(int position) {
            // Given you have a custom layout in `res/layout/custom_tab.xml` with a TextView and ImageView
            View view = LayoutInflater.from(ConventionFinderActivity.this).inflate(R.layout.convention_finder_tab, null);
            TextView title = (TextView) view.findViewById(R.id.title);
            title.setText(mTabsTitle[position]);
            ImageView icon = (ImageView) view.findViewById(R.id.icon);
            icon.setImageResource(mTabsIcons[position]);
            return view;
        }

        @Override
        public Fragment getItem(int pos) {
            switch (pos) {
                case 0:
                    tabScan = ConventionFinderFragment_Scan.newInstance();
                    return tabScan;
                case 1:
                    tabSearch = ConventionFinderFragment_Search.newInstance();
                    return tabSearch;
                case 2:
                    tabFavorite = ConventionFinderFragment_Favorites.newInstance();
                    return tabFavorite;
            }
            return null;
        }

        public void refreshFragment(int position) {
            switch (position) {
                case 0:
                    break;
                case 1:
                    break;
                case 2:
                    if (tabFavorite == null)
                        tabFavorite = ConventionFinderFragment_Favorites.newInstance();
                    tabFavorite.update();
                    break;
            }
        }

        @Override
        public int getCount() {
            return PAGE_COUNT;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mTabsTitle[position];
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position){
            Object obj = super.instantiateItem(container, position);
            if (obj instanceof  Fragment) {
                Fragment f = (Fragment) obj;
                String tag = f.getTag();
                mFragmentTags.put(position, tag);
            }
            return obj;
        }

        public Fragment getFragment(int position) {
            String tag = mFragmentTags.get(position);
            if (tag == null)
                return null;
            return mFragmentManager.findFragmentByTag(tag);
        }
    }

    //EXECUTES BEFORE PAGE LOADS - GETS ALL CONVENTIONS FROM PHP, CREATES SQLite AND RETURNS
    private class GetAllConventionsPHPtoSQLite extends AsyncTask<String, Void, String> {
        DatabaseManager dm;
        ProgressDialog pd;

        @Override
        protected String doInBackground(String... params) {
            dm = new DatabaseManager(ConventionFinderActivity.this);
            dm.setConventionList(ConventionFinderActivity.this); //gets from php, and inserts into sqlite

            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                Thread.interrupted();
            }
            return "Executed";
        }

        @Override
        protected void onPostExecute(String result) {
            ///Toast.makeText(ConventionFinderActivity.this,"CREATED SQLITE FROM PHP",Toast.LENGTH_SHORT).show();
            pd.dismiss();
        }

        @Override
        protected void onPreExecute() {
            pd = new ProgressDialog(ConventionFinderActivity.this);
            pd.setTitle("Please Wait...");
            pd.setMessage("Gettings Conventions...");
            pd.setCancelable(false);
            pd.show();
        }

        @Override
        protected void onProgressUpdate(Void... values) {}
    }
}
