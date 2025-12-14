package com.websarva.wings.android.navigationsampledsl.navigation

import android.content.res.Resources
import androidx.navigation.NavController
import androidx.navigation.createGraph
import androidx.navigation.fragment.fragment
import com.websarva.wings.android.navigationsampledsl.MemoDetailFragment
import com.websarva.wings.android.navigationsampledsl.MemoListFragment
import com.websarva.wings.android.navigationsampledsl.R

fun createNavGraph(navController: NavController, resources: Resources) {
	navController.graph = navController.createGraph(MemoList) {
		fragment<MemoListFragment, MemoList> {
			label = resources.getString(R.string.tb_list_title)
		}
		fragment<MemoDetailFragment, MemoDetail> {
			label = resources.getString(R.string.tb_detail_title)
		}
	}
}
