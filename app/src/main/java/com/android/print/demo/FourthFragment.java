package com.android.print.demo;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.navigation.fragment.NavHostFragment;

import com.android.print.demo.databinding.FragmentFourthBinding;

public class FourthFragment extends Fragment {

    private FragmentFourthBinding binding;
    private StarterActivity activity;

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {

        binding = FragmentFourthBinding.inflate(inflater, container, false);
        return binding.getRoot();

    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        activity = ((StarterActivity)getActivity());

        binding.buttonBusinesspermit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                activity.service = "Business Permit";
                NavHostFragment.findNavController(FourthFragment.this)
                        .navigate(R.id.action_FourthFragment_to_FifthFragment);
            }
        });

        binding.buttonBc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                activity.service = "Birth Certificate";
                //activity.printBigTicket();
                NavHostFragment.findNavController(FourthFragment.this)
                        .navigate(R.id.action_FourthFragment_to_FifthFragment);
            }
        });

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}