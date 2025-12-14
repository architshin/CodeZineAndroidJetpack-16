package com.websarva.wings.android.navigationsamplesafejava;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import com.websarva.wings.android.navigationsamplesafejava.data.model.Memo;
import com.websarva.wings.android.navigationsamplesafejava.databinding.FragmentMemoDetailBinding;
import com.websarva.wings.android.navigationsamplesafejava.ui.MemoDetailViewModel;

public class MemoDetailFragment extends Fragment {
	private FragmentMemoDetailBinding _fragmentMemoDetailBinding;
	private MemoDetailViewModel _memoDetailViewModel;

	@Override
	public void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		ViewModelProvider viewModelProvider = new ViewModelProvider(MemoDetailFragment.this);
		_memoDetailViewModel = viewModelProvider.get(MemoDetailViewModel.class);
		MemoDetailFragmentArgs memoDetailFragmentArgs = MemoDetailFragmentArgs.fromBundle(getArguments());
		long memoId = memoDetailFragmentArgs.getMemoId();
		_memoDetailViewModel.setMemoId(memoId);
	}

	@Override
	public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		_fragmentMemoDetailBinding = FragmentMemoDetailBinding.inflate(inflater, container, false);
		return _fragmentMemoDetailBinding.getRoot();
	}

	@Override
	public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
		_fragmentMemoDetailBinding.tbDetail.setNavigationOnClickListener(new NavigationClickListener());
		Memo memo = _memoDetailViewModel.getMemo();
		if(memo != null) {
			_fragmentMemoDetailBinding.tvMemoTitle.setText(memo.getTitle());
			_fragmentMemoDetailBinding.tvMemoContent.setText(memo.getContent());
		}
	}

	@Override
	public void onDestroyView() {
		super.onDestroyView();
		_fragmentMemoDetailBinding = null;
	}

	private class NavigationClickListener implements View.OnClickListener {
		@Override
		public void onClick(View view) {
			NavController navController = NavHostFragment.findNavController(MemoDetailFragment.this);
			navController.popBackStack();
		}
	}
}
