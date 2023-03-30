package com.example.githubuser.ui.main

import com.example.githubuser.R
import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import com.example.githubuser.ui.splash.SplashActivity
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4ClassRunner::class)
class MainActivityTest{

    private val dummyQueue = "cakra"

    @Before
    fun setup(){
        ActivityScenario.launch(SplashActivity::class.java)
        ActivityScenario.launch(MainActivity::class.java)
    }

    @Test
    fun search(){
        onView(withId(R.id.search)).perform(click())
        onView(withId(androidx.appcompat.R.id.search_src_text)).perform(typeText(dummyQueue), pressImeActionButton())
    }
}