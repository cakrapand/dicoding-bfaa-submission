package com.example.githubuser

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.githubuser.databinding.FragmentFollowBinding

class FollowFragment : Fragment() {

    private val detailViewModel : DetailViewModel by activityViewModels()
    private var _binding: FragmentFollowBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        // Inflate the layout for this fragment
        _binding = FragmentFollowBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val index = arguments?.getInt(ARG_SECTION_NUMBER, 0)

        val layoutManager = LinearLayoutManager(requireActivity())
        binding.listUsers.layoutManager = layoutManager

        detailViewModel.isLoadingFollow.observe(requireActivity()){
            showLoading(it)
        }

        when (index) {
            0 -> detailViewModel.userFollower.observe(requireActivity()) { setAdapter(it) }
            1 -> detailViewModel.userFollowing.observe(requireActivity()) { setAdapter(it) }
        }


    }

    private fun setAdapter(listUsers: List<User>){
        binding.listUsers.adapter = UserAdapter(listUsers) { user ->
            val intent = Intent(requireActivity(), DetailActivity::class.java)
            intent.putExtra(DetailActivity.EXTRA_USER, user)
            startActivity(intent)
        }
    }

    private fun showLoading(isLoading: Boolean) {
        if (isLoading){
            binding.progrerssBar.visibility = View.VISIBLE
        }else{
            binding.progrerssBar.visibility = View.GONE
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    companion object{
        private const val TAG = "FollowFragment"
        const val ARG_SECTION_NUMBER = "section_number"
    }

}