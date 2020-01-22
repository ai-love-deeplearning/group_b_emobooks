package com.example.tabapplication.mypage

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter

class MyPageAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm) {

    override fun getItem(position: Int): Fragment {
        return when (position) {
            0 -> {
                UserGraphFragment()
            }
            1 -> {
                LikeFragment()
            }
            else -> {
                return  DislikeFragment()
            }
        }
    }

    override fun getCount(): Int {
        return 3
    }

    override fun getPageTitle(position: Int): CharSequence {
        return when (position) {
            0 -> "ユーザーグラフ"
            1 -> "好きな本"
            else -> {
                return "嫌いな本"
            }
        }
    }
}