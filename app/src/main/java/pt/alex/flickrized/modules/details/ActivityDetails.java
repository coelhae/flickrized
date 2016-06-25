package pt.alex.flickrized.modules.details;

import android.animation.LayoutTransition;
import android.animation.ObjectAnimator;
import android.app.Fragment;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.FrameLayout;

import pt.alex.flickrized.R;
import pt.alex.flickrized.modules.common.HackyViewPager;
import pt.alex.flickrized.modules.details.adapters.FlickrDetailsPageAdapter;
import pt.alex.flickrized.modules.details.fragments.DescriptionFragment;

public class ActivityDetails extends AppCompatActivity {


    private HackyViewPager viewPager;
    private PagerAdapter pagerAdapter;
    private ViewGroup descriptContainer;
    private Fragment descriptFrag;

    LayoutTransition transiction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        descriptContainer = (ViewGroup) findViewById(R.id.description_container);
        viewPager = (HackyViewPager) findViewById(R.id.view_pager);
        pagerAdapter = new FlickrDetailsPageAdapter(getSupportFragmentManager());
        viewPager.setAdapter(pagerAdapter);
        int pos = getIntent().getExtras().getInt("POS");
        viewPager.setCurrentItem(pos);


    }

    // Warning de erro de framentmanager.state
//    https://code.google.com/p/android/issues/detail?id=202037#c14

    public void loadDetails(int index){
        slideUpAnimator();

        descriptFrag= new DescriptionFragment();
        Bundle bundle = new Bundle(1);
        bundle.putInt("POS", index);
        descriptFrag.setArguments(bundle);
        getFragmentManager().beginTransaction().add(R.id.description_container, descriptFrag,null).commit();
    }



    public  void slideUpAnimator(){
                descriptContainer.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);


                // criar layout transictions
                 transiction = new LayoutTransition();
                descriptContainer.setLayoutTransition(transiction);

                transiction.setStartDelay(LayoutTransition.APPEARING,0);
                transiction.setStartDelay(LayoutTransition.DISAPPEARING,0);
                transiction.setDuration(LayoutTransition.APPEARING, 500);
                transiction.setDuration(LayoutTransition.DISAPPEARING, 500);
                // anim slide up


                setAnimation();
    }

    public void setAnimation(){
        float height = getWindow().getDecorView().getRootView().getMeasuredHeight();//descriptContainer.getMeasuredHeight();
        ObjectAnimator animIn = ObjectAnimator.ofFloat(null, "y", height, 0f).setDuration(transiction.getDuration(LayoutTransition.CHANGE_APPEARING));
        transiction.setAnimator(LayoutTransition.APPEARING, animIn);
        ObjectAnimator animOut = ObjectAnimator.ofFloat(null, "y", 0f, height).setDuration(transiction.getDuration(LayoutTransition.CHANGE_DISAPPEARING));
        transiction.setAnimator(LayoutTransition.DISAPPEARING, animOut);
    }


    @Override
    public void onBackPressed() {
        /**
         * Override do back para quando o frag dos detalhes está visivel
         * senão sai da actividade
         */
        if (descriptContainer.getChildCount() != 0){
                getFragmentManager().beginTransaction().detach(descriptFrag).commitAllowingStateLoss();
        }else {
            super.onBackPressed();
        }

    }

}
