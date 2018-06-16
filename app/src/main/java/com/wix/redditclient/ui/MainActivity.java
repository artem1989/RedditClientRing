package com.wix.redditclient.ui;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.view.MenuItem;
import android.view.View;

import com.wix.redditclient.R;
import com.wix.redditclient.databinding.MainActivityBinding;

import dagger.android.support.DaggerAppCompatActivity;

import static com.wix.redditclient.common.Utils.getTopmostVisibleFragment;

public class MainActivity extends DaggerAppCompatActivity {

    MainActivityBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.main_activity);
        setSupportActionBar(binding.toolbar);
        SectionsPagerAdapter adapter = new SectionsPagerAdapter(getSupportFragmentManager(), binding.tabs.getTabCount());
        binding.container.setAdapter(adapter);
        binding.container.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(binding.tabs) {
            @Override
            public void onPageSelected(final int position) {
                if (adapter.instantiateItem(binding.container, position) instanceof OnFragmentSelectedListener) {
                    OnFragmentSelectedListener fragment = (OnFragmentSelectedListener) adapter.instantiateItem(binding.container, position);
                    fragment.onFragmentSelected();
                }
            }
        });
        binding.tabs.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(binding.container) {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                binding.container.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                binding.container.setCurrentItem(tab.getPosition());
            }
        });
        getSupportFragmentManager().addOnBackStackChangedListener(this::decorateToolbar);
    }

    private void decorateToolbar() {
        Fragment topFragment = getTopmostVisibleFragment(getSupportFragmentManager());

        if (topFragment != null) {
            boolean isRootFragment = topFragment instanceof MainRedditFragment || topFragment instanceof FavouritesRedditFragment;

            getSupportActionBar().setDisplayShowHomeEnabled(!isRootFragment);
            getSupportActionBar().setDisplayHomeAsUpEnabled(!isRootFragment);
            getSupportActionBar().setDisplayShowTitleEnabled(isRootFragment);
            binding.tabs.setVisibility(isRootFragment ? View.VISIBLE : View.GONE);
        }
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
