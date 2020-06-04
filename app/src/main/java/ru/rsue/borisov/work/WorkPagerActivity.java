package ru.rsue.borisov.work;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import java.util.List;
import java.util.UUID;

public class WorkPagerActivity extends AppCompatActivity {
    private List<Work> mWorks;
    private static final String EXTRA_WORK_ID = "ru.rsue.borisov.work.work_id";

    public static Intent newIntent(Context packageContext, UUID workId) {
        Intent intent = new Intent(packageContext, WorkPagerActivity.class);
        intent.putExtra(EXTRA_WORK_ID, workId);
        return intent;
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_work_pager);
        UUID workId = (UUID) getIntent().getSerializableExtra(EXTRA_WORK_ID);
        ViewPager viewPager = findViewById(R.id.activity_work_pager_view_pager);
        mWorks = WorkLab.get(this).getWorks();
        FragmentManager fragmentManager = getSupportFragmentManager();
        viewPager.setAdapter(new FragmentStatePagerAdapter(fragmentManager) {
            @NonNull
            @Override
            public Fragment getItem(int position) {
                Work work = mWorks.get(position);
                return WorkFragment.newInstance(work.getId());
            }

            @Override
            public int getCount() {
                return mWorks.size();
            }
        });
        for (int i = 0; i < mWorks.size(); i++) {
            if (mWorks.get(i).getId().equals(workId)) {
                viewPager.setCurrentItem(i);
                break;
            }
        }
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        return super.onOptionsItemSelected(item);
    }
}
