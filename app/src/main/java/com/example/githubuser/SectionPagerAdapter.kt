package com.example.githubuser

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter

class SectionPagerAdapter(activity: AppCompatActivity): FragmentStateAdapter(activity) {

    override fun createFragment(position: Int): Fragment {

        val fragment = FollowFragment()
        fragment.arguments = Bundle().apply {
            putInt(FollowFragment.ARG_SECTION_NUMBER, position)
        }
        return fragment


//        var fragment: Fragment? = null
//        when (position) {
//            0 -> fragment = FollowersFragment()
//            1 -> fragment = FollowingFragment()
//        }
//        return fragment as Fragment
    }

    override fun getItemCount(): Int {
        return 2
    }


}