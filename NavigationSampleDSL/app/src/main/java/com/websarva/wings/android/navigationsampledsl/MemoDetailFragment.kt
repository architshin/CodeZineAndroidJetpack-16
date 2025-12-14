package com.websarva.wings.android.navigationsampledsl

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.toRoute
import com.websarva.wings.android.navigationsampledsl.databinding.FragmentMemoDetailBinding
import com.websarva.wings.android.navigationsampledsl.navigation.MemoDetail
import com.websarva.wings.android.navigationsampledsl.ui.MemoDetailViewModel

class MemoDetailFragment : Fragment() {
	private val _memoDetailViewModel: MemoDetailViewModel by viewModels()
	private var _fragmentMemoDetailBindingField: FragmentMemoDetailBinding? = null
	private val _fragmentMemoDetailBinding get() = _fragmentMemoDetailBindingField!!


	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		val navController = NavHostFragment.findNavController(this@MemoDetailFragment)
		val backStackEntry = navController.getBackStackEntry<MemoDetail>()
		val backStackEntryRoute = backStackEntry.toRoute<MemoDetail>()
		_memoDetailViewModel.memoId = backStackEntryRoute.id
	}

	override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
		_fragmentMemoDetailBindingField = FragmentMemoDetailBinding.inflate(inflater)
		return _fragmentMemoDetailBinding.root
	}

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		_fragmentMemoDetailBinding.tbDetail.setNavigationOnClickListener(NavigationClickListener())
		val memo = _memoDetailViewModel.getMemo()
		if(memo != null) {
			_fragmentMemoDetailBinding.tvMemoTitle.text = memo.title
			_fragmentMemoDetailBinding.tvMemoContent.text = memo.content
		}
	}

	override fun onDestroyView() {
		super.onDestroyView()
		_fragmentMemoDetailBindingField = null
	}

	private inner class NavigationClickListener : View.OnClickListener {
		override fun onClick(view: View?) {
			val navController = NavHostFragment.findNavController(this@MemoDetailFragment)
			navController.popBackStack()
		}
	}
}
