package com.websarva.wings.android.navigationsamplesafejava;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.websarva.wings.android.navigationsamplesafejava.data.model.Memo;
import com.websarva.wings.android.navigationsamplesafejava.databinding.FragmentMemoListBinding;
import com.websarva.wings.android.navigationsamplesafejava.databinding.MemoListItemBinding;
import com.websarva.wings.android.navigationsamplesafejava.ui.MemoListViewModel;

import java.util.List;

public class MemoListFragment extends Fragment {
	private FragmentMemoListBinding _fragmentMemoListBinding;
	private MemoListViewModel _memoListViewModel;
	private MemoListAdapter _adapter;

	@Override
	public void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		ViewModelProvider viewModelProvider = new ViewModelProvider(MemoListFragment.this);
		_memoListViewModel = viewModelProvider.get(MemoListViewModel.class);
	}

	@Override
	public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		_fragmentMemoListBinding = FragmentMemoListBinding.inflate(inflater);
		return _fragmentMemoListBinding.getRoot();
	}

	@Override
	public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
		_adapter = new MemoListAdapter();
		LinearLayoutManager layoutManager = new LinearLayoutManager(requireActivity());
		_fragmentMemoListBinding.rvMenu.setLayoutManager(layoutManager);
		DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(requireActivity(), layoutManager.getOrientation());
		_fragmentMemoListBinding.rvMenu.addItemDecoration(dividerItemDecoration);
		_fragmentMemoListBinding.rvMenu.setAdapter(_adapter);

		LiveData<List<Memo>> _memoListLiveData = _memoListViewModel.getMemoListLiveData();
		MemoListObserver memoListObserver = new MemoListObserver();
		_memoListLiveData.observe(getViewLifecycleOwner(), memoListObserver);
	}

	@Override
	public void onDestroyView() {
		super.onDestroyView();
		_fragmentMemoListBinding = null;
	}

	private class MemoListObserver implements Observer<List<Memo>> {
		@Override
		public void onChanged(List<Memo> memoList) {
			_adapter.submitList(memoList);
		}
	}

	private class MemoListAdapter extends ListAdapter<Memo, MemoViewHolder> {
		public MemoListAdapter() {
			super(new MemoDiff());
		}

		@NonNull
		@Override
		public MemoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
			LayoutInflater inflater = LayoutInflater.from(requireActivity());
			MemoListItemBinding binding = MemoListItemBinding.inflate(inflater, parent, false);
			return new MemoViewHolder(binding);
		}

		@Override
		public void onBindViewHolder(@NonNull MemoViewHolder holder, int position) {
			Memo memo = getItem(position);
			holder.bind(memo);
			holder.itemView.setTag(memo.getId());
			holder.itemView.setOnClickListener(new ListItemClickListener());
		}
	}

	private class MemoDiff extends DiffUtil.ItemCallback<Memo> {
		@Override
		public boolean areItemsTheSame(@NonNull Memo oldItem, @NonNull Memo newItem) {
			return oldItem.getId().equals(newItem.getId());
		}

		@Override
		public boolean areContentsTheSame(@NonNull Memo oldItem, @NonNull Memo newItem) {
			return oldItem.getTitle().equals(newItem.getTitle()) || oldItem.getContent().equals(newItem.getContent());
		}
	}

	private class MemoViewHolder extends RecyclerView.ViewHolder {
		private final MemoListItemBinding _binding;

		public MemoViewHolder(MemoListItemBinding binding) {
			super(binding.getRoot());
			_binding = binding;
		}

		public void bind(Memo memo) {
			_binding.memoTitleRow.setText(memo.getTitle());
		}
	}

	private class ListItemClickListener implements View.OnClickListener {
		@Override
		public void onClick(View view) {
			Long memoId = (Long) view.getTag();
			MemoListFragmentDirections.ActionMemoListFragmentToMemoDetailFragment action = MemoListFragmentDirections.actionMemoListFragmentToMemoDetailFragment();
			action.setMemoId(memoId);
			NavController navController = NavHostFragment.findNavController(MemoListFragment.this);
			navController.navigate(action);
		}
	}
}
