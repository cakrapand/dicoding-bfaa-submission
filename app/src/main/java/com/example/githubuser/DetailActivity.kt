package com.example.githubuser

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.annotation.StringRes
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.example.githubuser.databinding.ActivityDetailBinding
import com.google.android.material.tabs.TabLayoutMediator

class DetailActivity : AppCompatActivity() {

    private val detailViewModel: DetailViewModel by viewModels()
    private lateinit var binding: ActivityDetailBinding

    companion object{
        private const val TAG = "DetailActivity"
        const val EXTRA_USER = "extra_user"
        @StringRes
        private val TAB_TITLES = intArrayOf(
            R.string.follower,
            R.string.following
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

//        val layoutManager = LinearLayoutManager(this)
//        binding.listUserDetail.layoutManager = layoutManager
//
//        val itemDecoration = DividerItemDecoration(this, layoutManager.orientation)
//        binding.listUserDetail.addItemDecoration(itemDecoration)

        val user = if (Build.VERSION.SDK_INT >= 33) {
            intent.getParcelableExtra<User>(EXTRA_USER)
        }else{
            @Suppress("DEPRECATION")
            intent.getParcelableExtra<User>(EXTRA_USER)
        }

        if (user != null && detailViewModel.userDetail.value == null){
            detailViewModel.getUser(user.login)
        }

        detailViewModel.userDetail.observe(this){
            Glide.with(this)
                .load(it.avatar_url)
                .into(binding.gambar)
            binding.username.text = it.name
            binding.follower.text = it.followers.toString()
            binding.following.text = it.following.toString()
        }

        detailViewModel.isLoading.observe(this){
            showLoading(it)
        }

//        detailViewModel.userFollower.observe(this){
//            binding.listUserDetail.adapter = UserAdapter(it)
//        }



        val sectionPagerAdapter = SectionPagerAdapter(this)
        binding.viewPager.adapter = sectionPagerAdapter

        TabLayoutMediator(binding.tabs, binding.viewPager){ tab, position ->
            tab.text = resources.getString(TAB_TITLES[position])
        }.attach()

        supportActionBar?.elevation = 0f

    }

    private fun showLoading(isLoading: Boolean) {
        if (isLoading){
            binding.progrerssBar.visibility = View.VISIBLE
        }else{
            binding.progrerssBar.visibility = View.GONE
        }
    }
}