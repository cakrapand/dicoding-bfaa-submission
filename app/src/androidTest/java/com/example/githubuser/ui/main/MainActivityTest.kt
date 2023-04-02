package com.example.githubuser.ui.main

import com.example.githubuser.R
import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import com.example.githubuser.adapter.UserAdapter
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
    fun testComponentShowCorrectly() {
        onView(withId(R.id.search)).check(matches(isDisplayed()))
        onView(withId(R.id.list_user_main)).check(matches(isDisplayed()))
    }

    @Test
    fun testSearch(){
        onView(withId(R.id.search)).check(matches(isDisplayed()))
        onView(withId(R.id.search)).perform(click())
        onView(withId(androidx.appcompat.R.id.search_src_text)).perform(typeText(dummyQueue), pressImeActionButton())
        onView(withId(R.id.list_user_main)).check(matches(isDisplayed()))
    }

    @Test
    fun testSelectUser() {
        Thread.sleep(1000)
        onView(withId(R.id.list_user_main)).check(matches(isDisplayed()))
        onView(withId(R.id.list_user_main)).perform(actionOnItemAtPosition<UserAdapter.ViewHolder>(0, click()))
        onView(withId(R.id.img_avatar)).check(matches(isDisplayed()))
        onView(withId(R.id.tv_username)).check(matches(isDisplayed()))
        onView(withId(R.id.tv_follower)).check(matches(isDisplayed()))
        onView(withId(R.id.tv_following)).check(matches(isDisplayed()))
        onView(withId(R.id.tabs)).check(matches(isDisplayed()))
        onView(withId(R.id.view_pager)).check(matches(isDisplayed()))
        onView(withId(R.id.fab_favourite)).check(matches(isDisplayed()))
        onView(withId(R.id.fab_share)).check(matches(isDisplayed()))
    }

    @Test
    fun testAddFavourite(){
        Thread.sleep(1000)
        onView(withId(R.id.list_user_main)).check(matches(isDisplayed()))
        onView(withId(R.id.list_user_main)).perform(actionOnItemAtPosition<UserAdapter.ViewHolder>(0, click()))
        onView(withId(R.id.img_avatar)).check(matches(isDisplayed()))
        onView(withId(R.id.tv_username)).check(matches(isDisplayed()))
        onView(withId(R.id.tv_follower)).check(matches(isDisplayed()))
        onView(withId(R.id.tv_following)).check(matches(isDisplayed()))
        onView(withId(R.id.tabs)).check(matches(isDisplayed()))
        onView(withId(R.id.view_pager)).check(matches(isDisplayed()))
        onView(withId(R.id.fab_favourite)).check(matches(isDisplayed()))
        onView(withId(R.id.fab_share)).check(matches(isDisplayed()))
        onView(withId(R.id.fab_favourite)).perform(click())
        onView(isRoot()).perform(pressBack())
        onView(withId(R.id.favorite)).check(matches(isDisplayed()))
        onView(withId(R.id.favorite)).perform(click())
        onView(withId(R.id.list_user_fav)).check(matches(isDisplayed()))
    }

    @Test
    fun testDeleteFavourite(){
        onView(withId(R.id.favorite)).check(matches(isDisplayed()))
        onView(withId(R.id.favorite)).perform(click())
        Thread.sleep(1000)
        onView(withId(R.id.list_user_fav)).check(matches(isDisplayed()))
        onView(withId(R.id.list_user_fav)).perform(actionOnItemAtPosition<UserAdapter.ViewHolder>(0, click()))
        onView(withId(R.id.img_avatar)).check(matches(isDisplayed()))
        onView(withId(R.id.tv_username)).check(matches(isDisplayed()))
        onView(withId(R.id.tv_follower)).check(matches(isDisplayed()))
        onView(withId(R.id.tv_following)).check(matches(isDisplayed()))
        onView(withId(R.id.tabs)).check(matches(isDisplayed()))
        onView(withId(R.id.view_pager)).check(matches(isDisplayed()))
        onView(withId(R.id.fab_favourite)).check(matches(isDisplayed()))
        onView(withId(R.id.fab_share)).check(matches(isDisplayed()))
        onView(withId(R.id.fab_favourite)).perform(click())
        onView(isRoot()).perform(pressBack())
    }
}