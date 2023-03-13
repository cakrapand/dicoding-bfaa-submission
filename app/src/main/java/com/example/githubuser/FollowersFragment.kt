package com.example.githubuser

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.githubuser.databinding.FragmentFollowersBinding

class FollowersFragment : Fragment() {

    private val detailViewModel : DetailViewModel by activityViewModels()
    private lateinit var binding: FragmentFollowersBinding

    companion object{
        private const val TAG = "FollowerFragment"
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_followers, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentFollowersBinding.bind(view)

        val layoutManager = LinearLayoutManager(requireActivity())
        binding.listUsers.layoutManager = layoutManager

        val itemDecoration = DividerItemDecoration(requireActivity(), layoutManager.orientation)
        binding.listUsers.addItemDecoration(itemDecoration)

        detailViewModel.userFollower.observe(requireActivity()){
            binding.listUsers.adapter = UserAdapter(it)
            showLoading(false)
        }
    }

    private fun showLoading(isLoading: Boolean) {
        if (isLoading){
            binding.progrerssBar.visibility = View.VISIBLE
        }else{
            binding.progrerssBar.visibility = View.GONE
        }
    }

}