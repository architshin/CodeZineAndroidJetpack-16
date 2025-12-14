package com.websarva.wings.android.navigationsamplesafejava.ui;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.websarva.wings.android.navigationsamplesafejava.data.model.Memo;
import com.websarva.wings.android.navigationsamplesafejava.data.repository.MemoRepository;

import java.util.List;

public class MemoListViewModel extends ViewModel {
	private final MemoRepository _memoRepository;

	public MemoListViewModel() {
		_memoRepository = new MemoRepository();
	}

	public LiveData<List<Memo>> getMemoListLiveData() {
		return _memoRepository.prepareMemoListLiveData();
	}
}
