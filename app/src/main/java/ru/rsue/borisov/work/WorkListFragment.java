package ru.rsue.borisov.work;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class WorkListFragment extends Fragment {

    private RecyclerView mWorkRecyclerView;
    private WorkAdapter mAdapter;
    private int mPosition;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_work_list, container, false);
        mWorkRecyclerView = view.findViewById(R.id.work_recycle_view);
        mWorkRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        updateUI();
        return view;
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private void updateUI() {
        WorkLab workLab = WorkLab.get(getActivity());
        List<Work> works = workLab.getWorks();
        if (works.size() == 0) {
            Toast.makeText(getContext(), R.string.empty_list, Toast.LENGTH_SHORT).show();
        }
        if (mAdapter == null) {
            mAdapter = new WorkAdapter(works);
            mWorkRecyclerView.setAdapter(mAdapter);
        } else {
            mAdapter.setWorks(works);
            mAdapter.notifyItemChanged(mPosition);
            mAdapter.notifyItemChanged(works.size());
        }

    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public void onResume() {
        super.onResume();
        updateUI();
    }

    private class WorkHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private Work mWork;

        private TextView mFioTextView;
        private TextView mNumberTextView;
        private TextView mHourTextView;
        private TextView mRateTextView;

        WorkHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            mFioTextView = itemView.findViewById(R.id.list_item_work_fio_text_view);
            mNumberTextView = itemView.findViewById(R.id.list_item_work_number_text_view);
            mHourTextView = itemView.findViewById(R.id.list_item_work_hour_text_view);
            mRateTextView = itemView.findViewById(R.id.list_item_work_rate_text_view);

        }

        void bindWork(Work work) {
            mWork = work;
            mFioTextView.setText(mWork.getFio());
            mNumberTextView.setText(mWork.getNumber());
            mHourTextView.setText(mWork.getHour());
            mRateTextView.setText(mWork.getRate());

        }

        @Override
        public void onClick(View v) {
            Intent intent = WorkPagerActivity.newIntent(getActivity(), mWork.getId());
            startActivity(intent);
            mPosition = getAdapterPosition();

        }
    }

    private class WorkAdapter extends RecyclerView.Adapter<WorkHolder> {
        private List<Work> mWorks;

        WorkAdapter(List<Work> works) {
            mWorks = works;
        }


        void setWorks(List<Work> works) {
            mWorks = works;
        }

        @NonNull
        @Override
        public WorkHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            View view = layoutInflater.inflate(R.layout.list_item_work, parent, false);
            return new WorkHolder(view);
        }

        @Override
        public void onBindViewHolder(WorkHolder holder, int position) {
            Work work = mWorks.get(position);
            holder.bindWork(work);
        }

        @Override
        public int getItemCount() {
            return mWorks.size();
        }
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.fragment_work_list, menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.menu_item_new_worker) {
            Work work = new Work();
            WorkLab.get(getActivity()).addWorker(work);
            Intent intent = WorkPagerActivity.newIntent(getActivity(), work.getId());
            startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


}
