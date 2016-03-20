package com.luke.mycloth;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.luke.mycloth.fragment.PartenFragment;

public class MainActivity extends AppCompatActivity implements PartenFragment.OnFragmentInteractionListener {
    private DrawerLayout drawerLayout;
    private ListView listView;
    private String[] mPlanetTitles;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView() {
        drawerLayout= (DrawerLayout) findViewById(R.id.dl_layout);
        listView=(ListView)findViewById(R.id.lv_drawer);
        mPlanetTitles=getResources().getStringArray(R.array.part);
        listView.setAdapter(new ArrayAdapter<String>(this, R.layout.item_drawer,mPlanetTitles));
        listView.setOnItemClickListener(new DrawerItemClickListener());
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    private class DrawerItemClickListener implements ListView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView parent, View view, int position, long id) {
            selectItem(position);
        }
    }

    /** Swaps fragments in the main content view */
    private void selectItem(int position) {
        // Create a new fragment and specify the planet to show based on position
        Fragment fragment = PartenFragment.newInstance("","");
        // Insert the fragment by replacing any existing fragment
        FragmentManager fragmentManager=getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.fl_content, fragment)
                .commit();

        // Highlight the selected item, update the title, and close the drawer
        listView.setItemChecked(position, true);
//        setTitle(mPlanetTitles[position]);
        drawerLayout.closeDrawer(listView);
    }

    @Override
    public void setTitle(CharSequence title) {
        getActionBar().setTitle(title);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }
}
