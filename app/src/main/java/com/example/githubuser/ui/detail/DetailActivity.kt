package com.example.githubuser.ui.detail

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.annotation.StringRes
import com.bumptech.glide.Glide
import com.example.githubuser.R
import com.example.githubuser.adapter.SectionPagerAdapter
import com.example.githubuser.data.UserResult
import com.example.githubuser.data.remote.response.User
import com.example.githubuser.databinding.ActivityDetailBinding
import com.example.githubuser.ui.ViewModelFactory
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.tabs.TabLayoutMediator

class DetailActivity : AppCompatActivity() {

    private var _activityDetailBinding: ActivityDetailBinding? = null
    private val binding get() = _activityDetailBinding

    private val detailViewModel: DetailViewModel by viewModels {
        ViewModelFactory.getInstance(application)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        _activityDetailBinding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        supportActionBar?.hide()

        val user = if (Build.VERSION.SDK_INT >= 33) {
            intent.getParcelableExtra(EXTRA_USER)
        }else{
            @Suppress("DEPRECATION")
            intent.getParcelableExtra<User>(EXTRA_USER)
        }

        val sectionPagerAdapter = SectionPagerAdapter(this) //user null?
        binding!!.viewPager.adapter = sectionPagerAdapter
        TabLayoutMediator(binding!!.tabs, binding!!.viewPager){ tab, position ->
            tab.text = resources.getString(TAB_TITLES[position], "")
        }.attach()
        supportActionBar?.elevation = 0f

        if (user != null && detailViewModel.userDetail.value == null) {

            detailViewModel.getUser(user.login)

            detailViewModel.getFavoriteUserByLogin(user.login).observe(this){
                if(it == null){
                    binding!!.fabFollow.setImageResource(R.drawable.ic_favorite_border)
                    binding!!.fabFollow.setOnClickListener {
                        detailViewModel.addFavouriteUser(user)
                        Snackbar.make(binding!!.root, "${user.login} added to favourite", Snackbar.LENGTH_SHORT).show()
                    }
                }else{
                    binding!!.fabFollow.setImageResource(R.drawable.ic_favorite_full)
                    binding!!.fabFollow.setOnClickListener {
                        detailViewModel.deleteFavouriteUser(user.login)
                        Snackbar.make(binding!!.root, "${user.login} deleted from favourite", Snackbar.LENGTH_SHORT).show()
                    }
                }
            }
        }

        detailViewModel.userDetail.observe(this){ userResult ->
            if(userResult != null){
                when(userResult){
                    is UserResult.Loading -> {
                        binding!!.progrerssBar.visibility = View.VISIBLE

                        binding!!.fabShare.setOnClickListener {
                            Snackbar.make(binding!!.root, "Data is loading...", Snackbar.LENGTH_SHORT).show()
                        }
                    }

                    is UserResult.Success -> {
                        binding!!.progrerssBar.visibility = View.GONE
                        Glide.with(this)
                            .load(userResult.data.avatarUrl)
                            .into(binding!!.imgAvatar)
                        binding!!.tvUsername.text = userResult.data.login
                        if(userResult.data.name.isNullOrEmpty()){
                            binding!!.tvNama.visibility = View.GONE
                        }else{
                            binding!!.tvNama.text = userResult.data.name
                        }
                        binding!!.tvFollower.text = getString(R.string.followers, "${ userResult.data.followers }")
                        binding!!.tvFollowing.text = getString(R.string.following, "${ userResult.data.following }")

                        binding!!.fabShare.setOnClickListener {
                            val intent = Intent(Intent.ACTION_SEND)
                            intent.type = "text/plain"
                            intent.putExtra(Intent.EXTRA_TEXT, "Check out ${userResult.data.login} GitHub: ${userResult.data.url}")
                            startActivity(intent)
                        }
                    }

                    is UserResult.Error -> {
                        binding!!.progrerssBar.visibility = View.GONE
                        Snackbar.make(binding!!.root, "Error getting data: ${userResult.error}", Snackbar.LENGTH_SHORT).show()

                        binding!!.fabShare.setOnClickListener {
                            Snackbar.make(binding!!.root, "Error getting data: ${userResult.error}", Snackbar.LENGTH_SHORT).show()
                        }
                    }

                }
            }
        }

    }

    override fun onDestroy() {
        super.onDestroy()
        _activityDetailBinding = null
    }

    companion object{
        const val EXTRA_USER = "extra_user"
        @StringRes
        private val TAB_TITLES = intArrayOf(
            R.string.followers,
            R.string.following
        )
    }
}