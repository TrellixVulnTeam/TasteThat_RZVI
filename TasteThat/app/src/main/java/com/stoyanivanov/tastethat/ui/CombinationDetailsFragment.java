package com.stoyanivanov.tastethat.ui;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.stoyanivanov.tastethat.R;
import com.stoyanivanov.tastethat.activities.MainActivity;
import com.stoyanivanov.tastethat.activities.MyProfileActivity;
import com.stoyanivanov.tastethat.constants.StartConstants;
import com.stoyanivanov.tastethat.models.Combination;
import com.stoyanivanov.tastethat.network.TasteThatApplication;
import com.stoyanivanov.tastethat.view_utils.CustomTextView;

import java.util.ArrayList;

public class CombinationDetailsFragment extends Fragment {
    private CustomTextView combinationNameHeader;
    private CustomTextView authorName;
    private CustomTextView combinationDescription;
    private Combination currCombination;
    private ImageView [] images;
    private String activityName;

    public static Fragment newInstance(String activityName, Combination combination) {
        Bundle bundle = new Bundle();
        CombinationDetailsFragment fragment = new CombinationDetailsFragment();

        bundle.putString(StartConstants.EXTRA_ACTIVITY_NAME, activityName);
        bundle.putSerializable(StartConstants.EXTRA_FRAGMENT_COMBINATION, combination);
        fragment.setArguments(bundle);

        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_combination_details, container, false);

        combinationNameHeader = (CustomTextView) view.findViewById(R.id.ctv_combination_details_header);
        authorName = (CustomTextView) view.findViewById(R.id.ctv_details_username);
        combinationDescription = (CustomTextView) view.findViewById(R.id.ctv_combination_details_description);
        ImageView imageTopLeft = (ImageView) view.findViewById(R.id.iv_top_left);
        ImageView imageTopRight = (ImageView) view.findViewById(R.id.iv_top_right);
        ImageView imageBottomLeft = (ImageView) view.findViewById(R.id.iv_bottom_left);
        ImageView imageBottomRight = (ImageView) view.findViewById(R.id.iv_bottom_right);
        ImageView backArrow = (ImageView) view.findViewById(R.id.iv_back_arrow);

        activityName = getArguments().getString(StartConstants.EXTRA_ACTIVITY_NAME);
        currCombination = (Combination) getArguments().getSerializable(StartConstants.EXTRA_FRAGMENT_COMBINATION);

        images = new ImageView[] {imageTopLeft, imageTopRight, imageBottomLeft, imageBottomRight};

        loadCombinationName();
        loadAuthorField();
        loadImages();
        loadDescription();

        backArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fragmentManager = getFragmentManager();
                fragmentManager.popBackStack();
            }
        });

        return view;
    }

    private void loadCombinationName() {
        StringBuilder displayNameBuilder = new StringBuilder();
        final ArrayList<String> components = currCombination.getComponents();

        for(int i = 0; i < components.size(); i++) {
            if(i < components.size() - 1) {
                displayNameBuilder.append(components.get(i));
                displayNameBuilder.append(" & ");
            } else {
                displayNameBuilder.append(components.get(i));
            }
        }
        combinationNameHeader.setText(displayNameBuilder.toString());
    }

    private void loadAuthorField() {
        String authorField = getString(R.string.author_field) + currCombination.getUsername();
        authorName.setText(authorField);

        authorName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(activityName.equals(MainActivity.class.getSimpleName())) {
                    ((MainActivity) getActivity())
                            .replaceFragment(UserProfileFragment.newInstance(activityName, currCombination));
                } else {
                    if(activityName.equals(MyProfileActivity.class.getSimpleName())) {
                        ((MyProfileActivity) getActivity())
                                .replaceFragment(UserProfileFragment.newInstance(activityName, currCombination));
                    }
                }
            }
        });
    }

    private void loadImages() {
        ArrayList<String> urls = currCombination.getUrls();
        hideImageviewsIfNotUsed(urls.size());

        for(int i = 0; i < urls.size(); i++) {
            Glide.with(TasteThatApplication.getStaticContext())
                    .load(urls.get(i))
                    .centerCrop()
                    .into(images[i]);
        }
    }

    private void loadDescription() {
    }

    //ToDo: REFACTOR AS SOON AS POSSIBLE
    private void hideImageviewsIfNotUsed(int numOfPics) {
        if (numOfPics < 3) {
            images[2].setVisibility(View.GONE);
            images[3].setVisibility(View.GONE);
        }
    }
}
