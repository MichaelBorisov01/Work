package ru.rsue.borisov.work;

import android.annotation.SuppressLint;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import java.util.Objects;
import java.util.UUID;

public class WorkFragment extends Fragment {
    private static final String ARG_WORK_ID = "work_id";

    private Work mWork;

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        assert getArguments() != null;
        UUID workId = (UUID) getArguments().getSerializable(ARG_WORK_ID);
        assert workId != null;
        mWork = WorkLab.get(getActivity()).getWork(workId);
        setHasOptionsMenu(true);
    }

    @Override
    public void onPause() {
        super.onPause();
        WorkLab.get(getActivity()).updateWork(mWork);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_work, container, false);

        EditText fioField = v.findViewById(R.id.work_fio);
        EditText numberField = v.findViewById(R.id.work_number);
        EditText hourField = v.findViewById(R.id.work_hour);
        EditText rateField = v.findViewById(R.id.work_rate);

        fioField.setText(mWork.getFio());
        numberField.setText(mWork.getNumber());
        hourField.setText(mWork.getHour());
        rateField.setText(mWork.getRate());

        fioField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mWork.setFio(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        numberField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mWork.setNumber(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        hourField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mWork.setHour(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        rateField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mWork.setRate(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        int hour, rate, result;
        if (!TextUtils.isEmpty(hourField.getText().toString())) {
            hour = Integer.parseInt(hourField.getText().toString());
        } else {
            hour = 0;
        }

        if (!TextUtils.isEmpty(rateField.getText().toString())) {
            rate = Integer.parseInt(rateField.getText().toString());
        } else {
            rate = 0;
        }

        TextView wageTextView = v.findViewById(R.id.wage);
        result = hour * rate;
        String res = String.valueOf(result);

        wageTextView.setText(res + " dollars");


        return v;
    }

    static WorkFragment newInstance(UUID workId) {
        Bundle args = new Bundle();
        args.putSerializable(ARG_WORK_ID, workId);
        WorkFragment fragment = new WorkFragment();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.fragment_work, menu);
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.menu_item_delete_worker) {
            WorkLab.get(getActivity()).deleteWorker(mWork);
            Objects.requireNonNull(getActivity()).finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
