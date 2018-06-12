package com.wix.redditclient;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.MenuItem;
import android.view.View;

import com.wix.redditclient.databinding.MainActivityBinding;
import com.wix.redditclient.model.DecorationInfo;

import dagger.android.support.DaggerAppCompatActivity;

public class MainActivity extends DaggerAppCompatActivity implements WebViewFragment.OnDecorateToolbarlistener{

    MainActivityBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.main_activity);

        setSupportActionBar(binding.toolbar);
        binding.container.setAdapter(new SectionsPagerAdapter(getSupportFragmentManager()));
        binding.container.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(binding.tabs));
        binding.tabs.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(binding.container));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void decorate(DecorationInfo info) {
        getSupportActionBar().setDisplayShowHomeEnabled(info.isShowBackArrow());
        getSupportActionBar().setDisplayHomeAsUpEnabled(info.isShowBackArrow());
        getSupportActionBar().setDisplayShowTitleEnabled(info.isShowTitle());
        binding.tabs.setVisibility(info.isShowTabs() ? View.VISIBLE : View.GONE);
    }

    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    return MainRedditFragment.newInstance(position);
                case 1:
                default:
                    return MainRedditFragment.newInstance(position + 1);
            }
        }

        @Override
        public int getCount() {
            return 2;
        }
    }
}
