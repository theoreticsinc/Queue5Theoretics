package com.android.print.demo;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;

import androidx.navigation.fragment.NavHostFragment;

import com.android.print.demo.databinding.FragmentFirstBinding;

public class FirstFragment extends Fragment {

    private FragmentFirstBinding binding;
    private StarterActivity activity;

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {

        binding = FragmentFirstBinding.inflate(inflater, container, false);
        return binding.getRoot();

    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        activity = ((StarterActivity)getActivity());

        binding.buttonSenior.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                activity.service = "";
                activity.gender = "";
                activity.type = "PRIORITY (Senior):";
                activity.purpose = "";
                NavHostFragment.findNavController(FirstFragment.this)
                        .navigate(R.id.action_FirstFragment_to_SecondFragment);
            }
        });

        binding.buttonPwd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                activity.service = "";
                activity.gender = "";
                activity.type = "PRIORITY (PWD):";
                activity.purpose = "";
                NavHostFragment.findNavController(FirstFragment.this)
                        .navigate(R.id.action_FirstFragment_to_SecondFragment);
            }
        });

        binding.buttonPregnant.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                activity.service = "";
                activity.gender = "";
                activity.type = "PRIORITY (Pregnant):";
                activity.purpose = "";
                NavHostFragment.findNavController(FirstFragment.this)
                        .navigate(R.id.action_FirstFragment_to_SecondFragment);
            }
        });

        binding.buttonRegular.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                activity.service = "";
                activity.gender = "";
                activity.type = "REGULAR :";
                activity.purpose = "";
                NavHostFragment.findNavController(FirstFragment.this)
                        .navigate(R.id.action_FirstFragment_to_SecondFragment);
            }
        });

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}