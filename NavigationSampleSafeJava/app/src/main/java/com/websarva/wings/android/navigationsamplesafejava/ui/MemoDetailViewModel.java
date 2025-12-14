package com.websarva.wings.android.navigationsamplesafejava.ui;

import androidx.lifecycle.ViewModel;

import com.websarva.wings.android.navigationsamplesafejava.data.model.Memo;
import com.websarva.wings.android.navigationsamplesafejava.data.repository.MemoRepository;

public class MemoDetailViewModel extends ViewModel {
	private final MemoRepository _memoRepository;
	private long _memoId = 0;

	public MemoDetailViewModel() {
		_memoRepository = new MemoRepository();
	}

	public Memo getMemo() {
		return _memoRepository.getOneMemo(_memoId);
	}

	public long getMemoId() {
		return _memoId;
	}
	public void setMemoId(long memoId) {
		_memoId = memoId;
	}
}