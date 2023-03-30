package com.example.githubuser.ui.detail

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.githubuser.data.remote.response.User
import com.example.githubuser.adapter.UserAdapter
import com.example.githubuser.data.UserResult
import com.example.githubuser.databinding.FragmentFollowBinding
import com.example.githubuser.ui.ViewModelFactory

class FollowFragment : Fragment() {

    private var _binding: FragmentFollowBinding? = null
    private val binding get() = _binding!!

    private val detailViewModel: DetailViewModel by activityViewModels {
        ViewModelFactory.getInstance(requireActivity())
    }

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

        when (index) {
            0 -> detailViewModel.listFollower.observe(requireActivity()) {setAdapter(it)}
            1 -> detailViewModel.listFollowing.observe(requireActivity()) {setAdapter(it)}
        }


    }

    private fun setAdapter (userResult: UserResult<List<User>>?){
        if(userResult != null){
            when(userResult){
                is UserResult.Loading -> {
                    binding.progrerssBar.visibility = View.VISIBLE
                }

                is UserResult.Success -> {
                    binding.progrerssBar.visibility = View.GONE
                    binding.listUsers.adapter = UserAdapter(userResult.data) { user ->
                        val intent = Intent(requireActivity(), DetailActivity::class.java)
                        intent.putExtra(DetailActivity.EXTRA_USER, user)
                        startActivity(intent)
                    }
                }

                is UserResult.Error -> {
                    binding.progrerssBar.visibility = View.GONE
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }


    companion object{
        const val ARG_SECTION_NUMBER = "section_number"
    }

}