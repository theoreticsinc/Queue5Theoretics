package com.android.print.demo;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.navigation.fragment.NavHostFragment;

import com.android.print.demo.databinding.FragmentFinalBinding;

public class FinalFragment extends Fragment {

    private FragmentFinalBinding binding;
    private StarterActivity activity;

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {

        binding = FragmentFinalBinding.inflate(inflater, container, false);
        return binding.getRoot();

    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        activity = ((StarterActivity)getActivity());
        String summaryStr = "YOUR SELECTED SERVICE IS: " + "\nGender: " + activity.gender + "\nPurpose: " + activity.purpose + "\nType of Service: " + activity.service + "\n Class: "+ activity.type;

        binding.summary.setText(summaryStr);

        binding.buttonYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                activity.printBigTicket();
                NavHostFragment.findNavController(FinalFragment.this)
                        .navigate(R.id.action_FifthFragment_to_FirstFragment);
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}