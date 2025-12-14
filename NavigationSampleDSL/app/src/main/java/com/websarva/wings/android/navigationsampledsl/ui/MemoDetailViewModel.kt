package com.websarva.wings.android.navigationsampledsl.ui

import androidx.lifecycle.ViewModel
import com.websarva.wings.android.navigationsampledsl.data.model.Memo
import com.websarva.wings.android.navigationsampledsl.data.repository.MemoRepository

class MemoDetailViewModel : ViewModel() {
	private val _memoRepository: MemoRepository = MemoRepository()
	var memoId = 0L

	fun getMemo(): Memo? {
		return _memoRepository.getOneMemo(memoId)
	}
}
