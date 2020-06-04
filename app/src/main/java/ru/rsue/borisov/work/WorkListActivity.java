package ru.rsue.borisov.work;

        import androidx.fragment.app.Fragment;

public class WorkListActivity extends SingleFragmentActivity {

    @Override
    protected Fragment createFragment() {
        return new WorkListFragment();
    }
}
