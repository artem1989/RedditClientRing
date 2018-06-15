package com.wix.redditclient;

import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.wix.redditclient.databinding.MainActivityBinding;
import com.wix.redditclient.di.VMFactory;
import com.wix.redditclient.model.DecorationInfo;
import com.wix.redditclient.viewmodels.FavouritesViewModel;
import com.wix.redditclient.viewmodels.RedditViewModel;

import javax.inject.Inject;

import dagger.android.support.DaggerAppCompatActivity;

public class MainActivity extends DaggerAppCompatActivity implements WebViewFragment.OnDecorateToolbarlistener{

    MainActivityBinding binding;

    @Inject
    MainNavigator navigator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.main_activity);
        setSupportActionBar(binding.toolbar);
        SectionsPagerAdapter adapter = new SectionsPagerAdapter(getSupportFragmentManager(), binding.tabs.getTabCount());
        binding.container.setAdapter(adapter);
        binding.container.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(binding.tabs){
            @Override
            public void onPageSelected(final int position) {
                if(adapter.instantiateItem(binding.container, position) instanceof OnFragmentSelectedListener){
                    OnFragmentSelectedListener fragment = (OnFragmentSelectedListener) adapter.instantiateItem(binding.container, position);
                    fragment.onFragmentSelected();
                }
            }
        });
        binding.tabs.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(binding.container){
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                binding.container.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                binding.container.setCurrentItem(tab.getPosition());
            }
        });
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
        //binding.tabs.setVisibility(info.isShowTabs() ? View.VISIBLE : View.GONE);
    }

    public class SectionsPagerAdapter extends FragmentStatePagerAdapter {

        static final int FIRST_TAB = 0;
        static final int SECOND_TAB = 1;
        int mNumOfTabs;

        SectionsPagerAdapter(FragmentManager fm, int mNumTabs) {
            super(fm);
            this.mNumOfTabs = mNumTabs;
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case FIRST_TAB:
                    return MainRedditFragment.newInstance();
                case SECOND_TAB:
                    default:
                    return FavouritesRedditFragment.newInstance();
            }
        }

        @Override
        public int getCount() {
            return mNumOfTabs;
        }
    }

    interface OnFragmentSelectedListener {
        void onFragmentSelected();
    }
}
