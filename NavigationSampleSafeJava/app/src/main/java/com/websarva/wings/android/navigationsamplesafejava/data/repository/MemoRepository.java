package com.websarva.wings.android.navigationsamplesafejava.data.repository;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.websarva.wings.android.navigationsamplesafejava.data.model.Memo;

import java.util.ArrayList;
import java.util.List;

public class MemoRepository {
	private final List<Memo> _memoList = new ArrayList<>();

	public MemoRepository() {
		for(int i = 1; i <= 30; i++) {
			Memo memo = new Memo();
			memo.setId((long) i);
			memo.setTitle("メモ" + i + "のタイトル");
			memo.setContent("これはメモ内容" + i + "です。");
			_memoList.add(memo);
		}
	}

	public LiveData<List<Memo>> prepareMemoListLiveData() {
		MutableLiveData<List<Memo>> memoListLiveData = new MutableLiveData<>();
		memoListLiveData.setValue(_memoList);
		return memoListLiveData;
	}

	public Memo getOneMemo(long id) {
		return _memoList.stream().filter(memo -> memo.getId() == id).findFirst().orElse(null);
	}
}
