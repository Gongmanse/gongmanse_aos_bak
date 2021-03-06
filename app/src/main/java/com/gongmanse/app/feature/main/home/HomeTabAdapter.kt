@file:Suppress("DEPRECATION")

package com.gongmanse.app.feature.main.home

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import com.gongmanse.app.feature.main.home.tabs.HomeHotFragment
import com.gongmanse.app.feature.main.home.tabs.HomeListFragment
import com.gongmanse.app.feature.main.home.tabs.HomeBestFragment
import com.gongmanse.app.utils.Constants

class HomeTabAdapter (fm: FragmentManager): FragmentStatePagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

    private val fragments = ArrayList<Fragment>()

    init {
        fragments.add(HomeBestFragment())
        fragments.add(HomeHotFragment())
        fragments.add(HomeListFragment(Constants.SubjectValue.KEM))
        fragments.add(HomeListFragment(Constants.SubjectValue.SCIENCE))
        fragments.add(HomeListFragment(Constants.SubjectValue.SOCIETY))
        fragments.add(HomeListFragment(Constants.SubjectValue.ETC))
    }

    override fun getItem(position: Int): Fragment {
        return fragments[position]
    }

    override fun getCount() = fragments.size

    override fun getPageTitle(position: Int): CharSequence? {
        return when (position) {
            0 -> Constants.Home.TAB_TITLE_BEST
            1 -> Constants.Home.TAB_TITLE_HOT
            2 -> Constants.Home.TAB_TITLE_KEM
            3 -> Constants.Home.TAB_TITLE_SCIENCE
            4 -> Constants.Home.TAB_TITLE_SOCIETY
            5 -> Constants.Home.TAB_TITLE_ETC
            else -> null
        }
    }
}