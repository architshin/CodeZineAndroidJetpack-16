package com.websarva.wings.android.navigationsamplesafekotlin.data.repository

import com.websarva.wings.android.navigationsamplesafekotlin.data.model.Memo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow

class MemoRepository {
	private val _memoList: List<Memo>

	init {
		_memoList = mutableListOf()
		for(i in 1..30) {
			val memo = Memo(i.toLong(), "メモ${i}のタイトル", "これはメモ内容${i}です。")
			_memoList.add(memo)
		}
	}

	fun prepareMemoListFlow(): Flow<List<Memo>> {
		return MutableStateFlow(_memoList)
	}

	fun getOneMemo(id: Long): Memo? {
		return _memoList.stream().filter { memo -> memo.id == id }.findFirst().orElse(null)
	}
}
