package com.websarva.wings.android.navigationsampledsl

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.NavHostFragment
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.websarva.wings.android.navigationsampledsl.data.model.Memo
import com.websarva.wings.android.navigationsampledsl.databinding.FragmentMemoListBinding
import com.websarva.wings.android.navigationsampledsl.databinding.MemoListItemBinding
import com.websarva.wings.android.navigationsampledsl.navigation.MemoDetail
import com.websarva.wings.android.navigationsampledsl.ui.MemoListViewModel
import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.launch

class MemoListFragment : Fragment() {
	private val _memoListViewModel: MemoListViewModel by viewModels()
	private var _fragmentMemoListBindingField: FragmentMemoListBinding? = null
	private val _fragmentMemoListBinding get() = _fragmentMemoListBindingField!!
	private lateinit var _adapter: MemoListAdapter


	override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
		_fragmentMemoListBindingField = FragmentMemoListBinding.inflate(inflater)
		return _fragmentMemoListBinding.root
	}

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		_adapter = MemoListAdapter()
		val layoutManager = LinearLayoutManager(requireActivity())
		_fragmentMemoListBinding.rvMenu.setLayoutManager(layoutManager)
		val dividerItemDecoration = DividerItemDecoration(requireActivity(), layoutManager.orientation)
		_fragmentMemoListBinding.rvMenu.addItemDecoration(dividerItemDecoration)
		_fragmentMemoListBinding.rvMenu.setAdapter(_adapter)

		val memoListFlow = _memoListViewModel.getMemoListFlow()
		viewLifecycleOwner.lifecycleScope.launch {
			repeatOnLifecycle(Lifecycle.State.STARTED) {
				memoListFlow.collect(MemoListCollector())
			}
		}
	}

	override fun onDestroyView() {
		super.onDestroyView()
		_fragmentMemoListBindingField = null
	}

	private inner class MemoListCollector : FlowCollector<List<Memo>> {
		override suspend fun emit(value: List<Memo>) {
			_adapter.submitList(value)
		}
	}

	private inner class MemoListAdapter : ListAdapter<Memo, MemoViewHolder?>(MemoDiff()) {
		override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MemoViewHolder {
			val inflater = LayoutInflater.from(requireActivity())
			val binding = MemoListItemBinding.inflate(inflater, parent, false)
			return MemoViewHolder(binding)
		}

		override fun onBindViewHolder(holder: MemoViewHolder, position: Int) {
			val memo: Memo = getItem(position)
			holder.bind(memo)
			holder.itemView.tag = memo.id
			holder.itemView.setOnClickListener(OnListItemClickListener())
		}
	}

	private inner class MemoDiff : DiffUtil.ItemCallback<Memo?>() {
		override fun areItemsTheSame(oldItem: Memo, newItem: Memo): Boolean {
			return oldItem.id == newItem.id
		}

		override fun areContentsTheSame(oldItem: Memo, newItem: Memo): Boolean {
			return oldItem == newItem
		}
	}

	private inner class MemoViewHolder(
		private val _binding: MemoListItemBinding
	) : RecyclerView.ViewHolder(_binding.root) {
		fun bind(memo: Memo) {
			_binding.memoTitleRow.text = memo.title
		}
	}

	private inner class OnListItemClickListener : View.OnClickListener {
		override fun onClick(view: View) {
			val memoId = view.tag as Long
			val navController = NavHostFragment.findNavController(this@MemoListFragment)
			navController.navigate(MemoDetail(memoId))
		}
	}
}
