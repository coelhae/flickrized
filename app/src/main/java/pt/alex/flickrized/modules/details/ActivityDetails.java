package pt.alex.flickrized.modules.details;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import pt.alex.flickrized.R;
import pt.alex.flickrized.modules.details.fragments.DetailsFragment;

public class ActivityDetails extends AppCompatActivity {


    private ViewPager viewPager;
    private PagerAdapter pagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        viewPager = (ViewPager) findViewById(R.id.view_pager);
        pagerAdapter = new FlickrPageAdapter(getSupportFragmentManager());

        viewPager.setAdapter(pagerAdapter);
        viewPager.setCurrentItem(1);

    }



    private class FlickrPageAdapter extends FragmentStatePagerAdapter {

        public FlickrPageAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return DetailsFragment.of(position);
        }

        @Override
        public int getCount() {
            //todo:
            return 0;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == ((LinearLayout)object);
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            return super.instantiateItem(container, position);
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((LinearLayout)object);
        }
    }
}
