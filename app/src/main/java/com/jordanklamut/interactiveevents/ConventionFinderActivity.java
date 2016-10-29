package com.jordanklamut.interactiveevents;

import android.app.ProgressDialog;
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
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

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
        //Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);

        // Setup the viewPager
        ViewPager viewPager = (ViewPager) findViewById(R.id.view_pager);

        MyPagerAdapter pagerAdapter = new MyPagerAdapter(getSupportFragmentManager());
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

        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        viewPager.setCurrentItem(1);

    }

    private class MyPagerAdapter extends FragmentPagerAdapter {

        public final int PAGE_COUNT = 3;

        private final String[] mTabsTitle = {"Scan", "Search", "Favorites"};

        public MyPagerAdapter(FragmentManager fm) {
            super(fm);
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
                    return ConventionFinderFragment_Scan.newInstance();
                case 1:
                    return ConventionFinderFragment_Search.newInstance();
                case 2:
                    return ConventionFinderFragment_Favorites.newInstance();
            }
            return null;
        }

        @Override
        public int getCount() {
            return PAGE_COUNT;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mTabsTitle[position];
        }
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

    public void onSearchClick(View view) {
        new GetConventionsFromSQLite().execute();
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
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                Thread.interrupted();
            }
            return "Executed";
        }

        @Override
        protected void onPostExecute(String result) {
            //GETS ALL THE CONVENTIONS FROM SQLite
            //Cursor res = dm.getAllConventionsFromSQLite();
//
            ////TODO - res contains all the conventions, would need to modify getAllConventions to include filters
            //if(res.getCount() != 0) {
            //    startActivity(new Intent(ConventionFinderActivity.this, ConventionFinderActivity_SearchResults.class));
            //}
            //else
            //{
            //    Toast.makeText(ConventionFinderActivity.this,"No conventions found",Toast.LENGTH_SHORT).show();
            //}
            Toast.makeText(ConventionFinderActivity.this,"CREATED SQLITE FROM PHP",Toast.LENGTH_SHORT).show();
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

    //EXECUTES ON SEARCH CLICK - QUERY SQLite DB AND RETURNS RESULTS
    private class GetConventionsFromSQLite extends AsyncTask<String, Void, String> {
        DatabaseManager dm;
        ProgressDialog pd;

        @Override
        protected String doInBackground(String... params) {
            dm = new DatabaseManager(ConventionFinderActivity.this);

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                Thread.interrupted();
            }
            return "Executed";
        }

        @Override
        //GETS ALL THE CONVENTIONS FROM SQLite
        protected void onPostExecute(String result) {
            Cursor res = dm.getAllConventionsFromSQLite();

            //TODO - res contains all the conventions, would need to modify getAllConventions to include filters
            if(res.getCount() != 0) {
                startActivity(new Intent(ConventionFinderActivity.this, ConventionFinderActivity_SearchResults.class));
            }
            else
            {
                Toast.makeText(ConventionFinderActivity.this,"No conventions found",Toast.LENGTH_SHORT).show();
            }
            Toast.makeText(ConventionFinderActivity.this,"SEARCHED CONVENTIONS FROM SQLITE",Toast.LENGTH_SHORT).show();
            pd.dismiss();
        }

        @Override
        protected void onPreExecute() {
            pd = new ProgressDialog(ConventionFinderActivity.this);
            pd.setTitle("Please Wait...");
            pd.setMessage("Finding Conventions...");
            pd.setCancelable(false);
            pd.show();
        }

        @Override
        protected void onProgressUpdate(Void... values) {}
    }
}
