package orz.kassy.coordimateshop;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.parse.GetCallback;
import com.parse.GetDataCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private SectionsPagerAdapter mSectionsPagerAdapter1;
    private ViewPager mViewPager1;
    private SectionsPagerAdapter2 mSectionsPagerAdapter2;
    private ViewPager mViewPager2;

    @InjectView(R.id.viewAll)
    View mViewAll;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final ImageView imageView = (ImageView) findViewById(R.id.imgGetParse);
        mSectionsPagerAdapter1 = new SectionsPagerAdapter(getSupportFragmentManager());
        mSectionsPagerAdapter2 = new SectionsPagerAdapter2(getSupportFragmentManager());

        mViewPager1 = (ViewPager) findViewById(R.id.container1);
        mViewPager1.setAdapter(mSectionsPagerAdapter1);
        mViewPager2 = (ViewPager) findViewById(R.id.container2);
        mViewPager2.setAdapter(mSectionsPagerAdapter2);
        ButterKnife.inject(this);

        ParseQuery<ParseObject> query = ParseQuery.getQuery("TestObject");
        query.orderByDescending("createdAt");
        query.getFirstInBackground(new GetCallback<ParseObject>() {
            public void done(ParseObject object, ParseException e) {
                if (e == null) {
                    // object will be your game score
                    Log.i(TAG, "foo=" + object.get("foo"));
                    ParseFile file = (ParseFile) object.get("image");
                    file.getDataInBackground(new GetDataCallback() {
                        public void done(byte[] data, ParseException e) {
                            if (e == null) {
                                Log.d("test", "We've got data in data.");
                                // use data for something
                                Bitmap bmp = BitmapFactory.decodeByteArray(data, 0, data.length);
                                imageView.setImageBitmap(bmp);
                            } else {
                                Log.d("test", "There was a problem downloading the data.");
                            }
                        }
                    });
                } else {
                    // something went wrong
                    Log.i(TAG, "error");
                }
            }
        });

    }

    @OnClick(R.id.btnSendCoordinate)
    void onClickSendCoordinate() {
        mViewAll.setDrawingCacheEnabled(true);

        // Viewのキャッシュを取得
        Bitmap cache = mViewAll.getDrawingCache();
        Bitmap screenShot = Bitmap.createBitmap(cache);
        mViewAll.setDrawingCacheEnabled(false);
        saveBitmapAtParse(screenShot);
    }

    private void saveBitmapAtParse(Bitmap screenShot) {

    }

    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return ClothesFragment.newInstance(position);

        }

        @Override
        public int getCount() {
            return 3;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return null;
        }
    }

    public static class ClothesFragment extends Fragment {
        static int mPosition;

        public static ClothesFragment newInstance(int position) {
            ClothesFragment fragment = new ClothesFragment();
            Bundle args = new Bundle();
            args.putInt("a", position);
            fragment.setArguments(args);
            return fragment;
        }

        public ClothesFragment() {
            super();
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            View view = inflater.inflate(R.layout.fragment_clothes, null);
            ImageView imgClothes = (ImageView) view.findViewById(R.id.imgClothes);
            switch (getArguments().getInt("a")) {
                case 0:
                    imgClothes.setImageResource(R.drawable.top1);
                    break;
                case 1:
                    imgClothes.setImageResource(R.drawable.top2);
                    break;
                case 2:
                    imgClothes.setImageResource(R.drawable.top3);
                    break;

            }

            return view;
        }
    }


    public class SectionsPagerAdapter2 extends FragmentPagerAdapter {

        public SectionsPagerAdapter2(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return ClothesFragment2.newInstance(position);

        }

        @Override
        public int getCount() {
            return 3;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return null;
        }
    }

    public static class ClothesFragment2 extends Fragment {
        static int mPosition;
        public static ClothesFragment2 newInstance(int position) {
            ClothesFragment2 fragment = new ClothesFragment2();
            Bundle args = new Bundle();
            args.putInt("a", position);
            fragment.setArguments(args);
            return fragment;
        }

        public ClothesFragment2() {
            super();
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            View view = inflater.inflate(R.layout.fragment_clothes, null);
            ImageView imgClothes = (ImageView) view.findViewById(R.id.imgClothes);
            switch (getArguments().getInt("a")) {
                case 0:
                    imgClothes.setImageResource(R.drawable.bottom1);
                break;
                case 1:
                    imgClothes.setImageResource(R.drawable.bottom2);
                    break;
                case 2:
                    imgClothes.setImageResource(R.drawable.bottom3);
                    break;

            }

            return view;
        }
    }

}