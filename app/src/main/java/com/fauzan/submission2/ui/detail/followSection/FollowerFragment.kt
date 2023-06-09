package com.fauzan.submission2.ui.detail.followSection

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.fauzan.submission2.UserAdapter
import com.fauzan.submission2.databinding.FragmentFollowsBinding
import com.fauzan.submission2.ui.detail.DetailActivity

class FollowerFragment : Fragment() {
    private var _binding: FragmentFollowsBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: FollowerViewModel
    private lateinit var adapter: UserAdapter
    private lateinit var username: String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFollowsBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val args = arguments
        username = args?.getString(DetailActivity.EXTRA_USERNAME).toString()



        adapter = UserAdapter(ArrayList(), context)
        adapter.notifyDataSetChanged()

        binding.apply {
            rvFollow.layoutManager = LinearLayoutManager(activity)
            rvFollow.setHasFixedSize(true)
            rvFollow.adapter = adapter
        }

        showLoading(true)
        viewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(
            FollowerViewModel::class.java
        )
        viewModel.setListFollowers(username)
        viewModel.getListFollowerUser().observe(viewLifecycleOwner) {
            if (it != null) {
                adapter.setList(it)
                showLoading(false)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
        }
    }
}