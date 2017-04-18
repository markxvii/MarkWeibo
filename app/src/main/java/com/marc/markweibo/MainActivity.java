package com.marc.markweibo;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.marc.markweibo.Fragment.HPFrag;
import com.marc.markweibo.Fragment.MSGFrag;
import com.marc.markweibo.Fragment.OFrag;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private List<Fragment> frags=new ArrayList<>();
    private FragmentPagerAdapter adapter;

    private Toolbar titleToolBar;
    private TabLayout titleTabLayout;
    private ViewPager viewPager;
    private DrawerLayout drawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initToolBar();
        initViewPager();
    }

    private void initViewPager() {
        frags.add(new HPFrag());
        frags.add(new MSGFrag());
        frags.add(new OFrag());
        adapter=new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return frags.get(position);
            }

            @Override
            public int getCount() {
                return frags.size();
            }
        };
        viewPager.setAdapter(adapter);
        titleTabLayout.setupWithViewPager(viewPager);
        titleTabLayout.getTabAt(0).setText("首页");
        titleTabLayout.getTabAt(1).setText("消息");
        titleTabLayout.getTabAt(2).setText("更多");
    }

    private void initView() {
        titleToolBar = (Toolbar) findViewById(R.id.title_tool_bar);
        titleTabLayout = (TabLayout) findViewById(R.id.title_tab_layout);
        viewPager = (ViewPager) findViewById(R.id.view_pager);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
    }

    private void initToolBar() {
        titleToolBar.inflateMenu(R.menu.title_menu);
        titleToolBar.setNavigationIcon(R.drawable.star);
        titleToolBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.openDrawer(GravityCompat.END);
            }
        });
    }
}
