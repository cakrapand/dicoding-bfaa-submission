package com.example.githubuser

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.annotation.StringRes
import com.bumptech.glide.Glide
import com.example.githubuser.databinding.ActivityDetailBinding
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.tabs.TabLayoutMediator

class DetailActivity : AppCompatActivity() {

    private val detailViewModel: DetailViewModel by viewModels()
    private lateinit var binding: ActivityDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()

        val user = if (Build.VERSION.SDK_INT >= 33) {
            intent.getParcelableExtra(EXTRA_USER)
        }else{
            @Suppress("DEPRECATION")
            intent.getParcelableExtra<User>(EXTRA_USER)
        }

        val sectionPagerAdapter = SectionPagerAdapter(this)
        binding.viewPager.adapter = sectionPagerAdapter
        TabLayoutMediator(binding.tabs, binding.viewPager){ tab, position ->
            tab.text = resources.getString(TAB_TITLES[position], "")
        }.attach()
        supportActionBar?.elevation = 0f

        if (user != null && detailViewModel.userDetail.value == null && detailViewModel.isError.value == null) detailViewModel.getUser(user.login)


        detailViewModel.userDetail.observe(this){
            Glide.with(this)
                .load(it.avatar_url)
                .into(binding.imgAvatar)
            binding.tvUsername.text = it.login

            if(it.name != null){
                binding.tvNama.text = it.name
            }else{
                binding.tvNama.visibility = View.GONE
            }
            binding.tvFollower.text = getString(R.string.followers, "${ it.followers }")
            binding.tvFollowing.text = getString(R.string.following, "${ it.following }")
        }

        detailViewModel.isLoading.observe(this){
            showLoading(it)
        }

        detailViewModel.isError.observe(this){ event ->
            event.getContentIfNotHandled()?.let {
                if(it) Snackbar.make(binding.root, "Data gagal ditampilkan", Snackbar.LENGTH_SHORT).show()
            }
        }
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progrerssBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    companion object{
        private const val TAG = "DetailActivity"
        const val EXTRA_USER = "extra_user"
        @StringRes
        private val TAB_TITLES = intArrayOf(
            R.string.followers,
            R.string.following
        )
    }
}