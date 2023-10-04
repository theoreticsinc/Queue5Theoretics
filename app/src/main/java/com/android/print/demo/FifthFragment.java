package com.android.print.demo;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.navigation.fragment.NavHostFragment;

import com.android.print.demo.databinding.FragmentFifthBinding;

public class FifthFragment extends Fragment {

    private FragmentFifthBinding binding;
    private StarterActivity activity;

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {

        binding = FragmentFifthBinding.inflate(inflater, container, false);
        return binding.getRoot();

    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        activity = ((StarterActivity)getActivity());
        if (activity.service.compareTo("Business Permit")==0) {
            binding.mainText.setText(R.string.request_bpa);
            binding.mainDocuments.setText(R.string.attachments_bpa);
        } else {
            binding.mainText.setText(R.string.request_colb);
            binding.mainDocuments.setText(R.string.attachments_colb);
        }

        binding.buttonProceed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(FifthFragment.this)
                        .navigate(R.id.action_FifthFragment_to_FinalFragment);
            }
        });
        binding.buttonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(FifthFragment.this)
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