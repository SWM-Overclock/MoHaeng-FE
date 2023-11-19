package com.example.moHaeng

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.example.moHaeng.addEvent.AddEventFragment
import com.example.moHaeng.databinding.ActivityMainBinding
import com.example.moHaeng.home.HomeFragment
import com.example.moHaeng.maps.MapFragment
import com.example.moHaeng.userPage.MyPageFragment

private const val TAG_HOME = "home"
private const val TAG_MAP = "calender"
private const val TAG_ADD_ITEM = "addItem"
private const val TAG_MY_PAGE = "myPage"

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // 기본적으로 HomeFragment를 표시
        setFragment(TAG_HOME, HomeFragment())

        binding.navigationView.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.homeFragment -> setFragment(TAG_HOME, HomeFragment())
                R.id.mapFragment -> setFragment(TAG_MAP, MapFragment())
                R.id.addFragment -> setFragment(TAG_ADD_ITEM, AddEventFragment())
                R.id.myPageFragment -> setFragment(TAG_MY_PAGE, MyPageFragment())
            }
            true
        }
    }

    // Fragment를 설정하고 백 스택에 추가하는 함수
    private fun setFragment(tag: String, fragment: Fragment) {
        val manager: FragmentManager = supportFragmentManager
        val fragTransaction = manager.beginTransaction()

        // 백 스택에 현재 Fragment 추가
        fragTransaction.replace(R.id.mainFragment, fragment, tag)
        fragTransaction.addToBackStack(null)
        fragTransaction.commitAllowingStateLoss()
    }

    // 뒤로가기 버튼 처리
    override fun onBackPressed() {
        val manager: FragmentManager = supportFragmentManager
        if (manager.backStackEntryCount > 0) {
            // 백 스택에 Fragment가 있는 경우, 이전 Fragment로 이동
            manager.popBackStack()
        } else {
            super.onBackPressed()
        }
    }
}
