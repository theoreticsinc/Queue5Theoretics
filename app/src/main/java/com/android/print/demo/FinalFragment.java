package com.android.print.demo;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.navigation.fragment.NavHostFragment;

import com.android.print.demo.bean.CONSTANTS;
import com.android.print.demo.bean.COUNT;
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
                activity.cocTimer.cancel();
                activity.cocTimer.purge();
                activity.cocTimer = null;
                //Steps
                //1. Retrieve from Central Database the Counts
                //2. Add One then Update to Central Database
                //3. Print the Ticket with [+1] already on it.
                HttpHandler hh = new HttpHandler();

                hh.RetrieveCurrentCount("http://"+ CONSTANTS.getInstance().getSERVERADDR() + "/undpqueue/retrieveCount.php?deskID=0");

                    if (activity.type.contains("REGULAR")) {
                        if (COUNT.getInstance().getRegularCount() <= activity.regularCount) {
                            activity.regularCount++;
                            COUNT.getInstance().setRegularCount(activity.regularCount);
                        } else {
                            activity.regularCount = COUNT.getInstance().getRegularCount() + 1;
                            COUNT.getInstance().setRegularCount(activity.regularCount);
                        }
                        hh.UpdateDisplayConnection("http://"+ CONSTANTS.getInstance().getSERVERADDR() + "/undpqueue/updateDisplay.php?gender=" + activity.gender
                                + "&purpose=" + activity.purpose
                                + "&type=" + activity.type
                                + "&regularCount=" + activity.regularCount
                                + "&service=" + activity.service);
                    } else {
                        if (COUNT.getInstance().getPriorityCount() <= activity.priorityCount) {
                            activity.priorityCount++;
                            COUNT.getInstance().setPriorityCount(activity.priorityCount);
                        } else {
                            activity.priorityCount = COUNT.getInstance().getPriorityCount() + 1;
                            COUNT.getInstance().setPriorityCount(activity.priorityCount);
                        }
                        hh.UpdateDisplayConnection("http://"+ CONSTANTS.getInstance().getSERVERADDR() + "/undpqueue/updateDisplay.php?gender=" + activity.gender
                                + "&purpose=" + activity.purpose
                                + "&type=" + activity.type
                                + "&priorityCount=" + activity.priorityCount
                                + "&service=" + activity.service);
                    }
                    activity.createNewTaskTimer();
                    activity.cocTimer.schedule(activity.createNewCOCTask(), 5000, 5000);
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