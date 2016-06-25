package pt.alex.flickrized.modules.details;

import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v7.app.AppCompatActivity;

import pt.alex.flickrized.R;
import pt.alex.flickrized.modules.common.HackyViewPager;
import pt.alex.flickrized.modules.details.adapters.FlickrDetailsPageAdapter;

public class ActivityDetails extends AppCompatActivity {


    private HackyViewPager viewPager;
    private PagerAdapter pagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        viewPager = (HackyViewPager) findViewById(R.id.view_pager);
        pagerAdapter = new FlickrDetailsPageAdapter(getSupportFragmentManager());
        viewPager.setAdapter(pagerAdapter);
        int pos = getIntent().getExtras().getInt("POS");
        viewPager.setCurrentItem(pos);
    }

    // Warning de erro de framentmanager.state
//    https://code.google.com/p/android/issues/detail?id=202037#c14
}
