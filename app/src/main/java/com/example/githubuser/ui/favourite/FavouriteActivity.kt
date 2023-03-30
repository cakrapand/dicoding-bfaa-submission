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
    private val binding get() = _activityFavouriteBinding

    private val favouriteViewModel: FavouriteViewModel by viewModels {
        ViewModelFactory.getInstance(application)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        _activityFavouriteBinding = ActivityFavouriteBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        supportActionBar?.title = getString(R.string.favourite)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val layoutManager = LinearLayoutManager(this)
        binding!!.listUserFav.layoutManager = layoutManager

        favouriteViewModel.getFavouriteUser().observe(this){
            if(it.isNotEmpty()){
                binding!!.listUserFav.adapter = UserAdapter(it){ user ->
                    val intent = Intent(this@FavouriteActivity, DetailActivity::class.java)
                    intent.putExtra(DetailActivity.EXTRA_USER, user)
                    startActivity(intent)
                }
            }else{
                Snackbar.make(binding!!.root, "Data is empty", Snackbar.LENGTH_SHORT).show()
            }
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