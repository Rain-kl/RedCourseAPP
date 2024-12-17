package com.example.myapplication.ui.exercise;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.myapplication.ui.exercise.view.ExercisesView;

public class ExerciseFragment extends Fragment {

    private ExercisesView exercisesView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // 创建 ExercisesView 实例
        exercisesView = new ExercisesView(getActivity());

        // 返回 ExercisesView 的视图
        return exercisesView.getView();
    }

    @Override
    public void onResume() {
        super.onResume();
        // 显示视图
        exercisesView.showView();
    }
}
