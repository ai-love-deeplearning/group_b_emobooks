package com.example.tabapplication.main

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import com.example.tabapplication.home.HomeFragment
import com.example.tabapplication.mypage.MyPageFragment
import com.example.tabapplication.search.SearchFragment
import kotlinx.android.synthetic.main.fragment_home_empty.*


class TabAdapter(fm: FragmentManager, context: Context) : FragmentStatePagerAdapter(fm) {

    override fun getItem(p0: Int): Fragment {

        return when(p0) {
            0 -> HomeFragment()
            1 -> SearchFragment()
            2 -> MyPageFragment()
            else -> HomeFragment()
        }
    }

    override fun getCount(): Int {
        return 3
    }
}