package com.websarva.wings.android.navigationsamplesafekotlin.ui

import androidx.lifecycle.ViewModel
import com.websarva.wings.android.navigationsamplesafekotlin.data.model.Memo
import com.websarva.wings.android.navigationsamplesafekotlin.data.repository.MemoRepository
import kotlinx.coroutines.flow.Flow

class MemoListViewModel : ViewModel() {
	private val _memoRepository = MemoRepository()

	fun getMemoListFlow(): Flow<List<Memo>> {
		return _memoRepository.prepareMemoListFlow()
	}
}
