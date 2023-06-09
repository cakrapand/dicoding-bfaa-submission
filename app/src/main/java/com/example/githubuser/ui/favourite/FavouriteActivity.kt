package com.example.githubuser.ui.favourite

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.githubuser.R
import com.example.githubuser.adapter.UserAdapter
import com.example.githubuser.databinding.ActivityFavouriteBinding
import com.example.githubuser.ui.ViewModelFactory
import com.example.githubuser.ui.detail.DetailActivity
import com.google.android.material.snackbar.Snackbar

class FavouriteActivity : AppCompatActivity() {

    private var _activityFavouriteBinding: ActivityFavouriteBinding? = null
    private val binding get() = _activityFavouriteBinding!!

    private val favouriteViewModel: FavouriteViewModel by viewModels {
        ViewModelFactory.getInstance(application)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        _activityFavouriteBinding = ActivityFavouriteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.title = getString(R.string.favorite)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        favouriteViewModel.getFavouriteUser().observe(this){
            val listFavouriteAdapter = UserAdapter(it){ user ->
                val intent = Intent(this@FavouriteActivity, DetailActivity::class.java)
                intent.putExtra(DetailActivity.EXTRA_USER, user)
                startActivity(intent)
            }
            binding.listUserFav.apply {
                layoutManager = LinearLayoutManager(this@FavouriteActivity)
                adapter = listFavouriteAdapter
            }
            if(it.isEmpty())Snackbar.make(binding.root, "Data is empty", Snackbar.LENGTH_SHORT).show()
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            android.R.id.home ->{
                finish()
                return true
            }
        }
        return true
    }

    override fun onDestroy() {
        super.onDestroy()
        _activityFavouriteBinding = null
    }
}