package am.leon.utilities.android.helpers.components.tabLayout

import com.google.android.material.tabs.TabLayout

interface OnTabSelectedListener : TabLayout.OnTabSelectedListener {

    override fun onTabUnselected(tab: TabLayout.Tab?) {}

    override fun onTabReselected(tab: TabLayout.Tab?) {}
}