package com.example.githubuser.ui.main

import android.app.SearchManager
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.githubuser.R
import com.example.githubuser.adapter.UserAdapter
import com.example.githubuser.data.UserResult
import com.example.githubuser.databinding.ActivityMainBinding
import com.example.githubuser.ui.ViewModelFactory
import com.example.githubuser.ui.detail.DetailActivity
import com.example.githubuser.ui.favourite.FavouriteActivity
import com.example.githubuser.ui.settings.SettingActivity
import com.google.android.material.snackbar.Snackbar

class MainActivity : AppCompatActivity() {

    private var _activityMainBinding: ActivityMainBinding? = null
    private val binding get() = _activityMainBinding!!

    private val mainViewModel: MainViewModel by viewModels {
        ViewModelFactory.getInstance(application)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        _activityMainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        mainViewModel.userResult.observe(this){ userResult ->
            if(userResult != null){
                when(userResult){
                    is UserResult.Loading -> {
                        binding.progrerssBarMain.visibility = View.VISIBLE
                    }

                    is UserResult.Success -> {
                        binding.progrerssBarMain.visibility = View.GONE
                        val listUserAdapter = UserAdapter(userResult.data) { user ->
                            val intent = Intent(this@MainActivity, DetailActivity::class.java)
                            intent.putExtra(DetailActivity.EXTRA_USER, user)
                            startActivity(intent)
                        }
                        binding.listUserMain.apply {
                            layoutManager = LinearLayoutManager(this@MainActivity)
                            adapter = listUserAdapter
                        }
                        if(userResult.data.isEmpty()) Snackbar.make(binding.root, "Data not found", Snackbar.LENGTH_SHORT).show()
                    }

                    is UserResult.Error -> {
                        binding.progrerssBarMain.visibility = View.GONE
                        userResult.error?.getContentIfNotHandled().let {
                            if(!it.isNullOrEmpty()) Snackbar.make(binding.root, "Error getting data: $it", Snackbar.LENGTH_SHORT).show()
                        }
                    }
                }
            }
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId){
            R.id.favorite -> {
                startActivity(Intent(this, FavouriteActivity::class.java))
                true
            }
            R.id.setting -> {
                startActivity(Intent(this, SettingActivity::class.java))
                true
            }
            R.id.search -> {
                true
            }
            else -> true
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.option_menu, menu)

        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        val searchView = menu?.findItem(R.id.search)?.actionView as SearchView

        searchView.setSearchableInfo(searchManager.getSearchableInfo(componentName))
        searchView.queryHint = resources.getString(R.string.search_hint)

        searchView.setOnQueryTextListener(object: SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                if(query != null){
                    mainViewModel.searchUser(query)
                    searchView.clearFocus()
                }
                return true
            }
            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }
        })
        return true
    }

    override fun onDestroy() {
        super.onDestroy()
        _activityMainBinding = null
    }
}