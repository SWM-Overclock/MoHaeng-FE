package com.example.moHaeng

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.example.moHaeng.addEvent.AddEventFragment
import com.example.moHaeng.databinding.ActivityMainBinding
import com.example.moHaeng.home.HomeFragment
import com.example.moHaeng.maps.FindMapFragment
import com.example.moHaeng.userPage.MyPageFragment

private const val TAG_HOME = "home"
private const val TAG_MAP = "calender"
private const val TAG_ADD_ITEM = "addItem"
private const val TAG_MY_PAGE = "myPage"

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private var backPressedTime: Long = 0
    private val DOUBLE_BACK_PRESS_INTERVAL: Long = 2000

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setFirstFragment(TAG_HOME, HomeFragment())

        binding.navigationView.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.homeFragment -> setFragment(TAG_HOME, HomeFragment())
                R.id.mapFragment -> setFragment(TAG_MAP, FindMapFragment())
                R.id.addFragment -> setFragment(TAG_ADD_ITEM, AddEventFragment())
                R.id.myPageFragment -> setFragment(TAG_MY_PAGE, MyPageFragment())
            }
            true
        }
    }

    private fun setFirstFragment(tag: String, fragment: Fragment) {
        val manager: FragmentManager = supportFragmentManager
        val fragTransaction = manager.beginTransaction()

        fragTransaction.replace(R.id.mainFragment, fragment, tag)
        fragTransaction.commitAllowingStateLoss()
    }

    // Fragment를 설정하고 백 스택에 추가하는 함수
    fun setFragment(tag: String, fragment: Fragment) {
        val manager: FragmentManager = supportFragmentManager
        val fragTransaction = manager.beginTransaction()

        // 백 스택에 현재 Fragment 추가
        fragTransaction.replace(R.id.mainFragment, fragment, tag)
        //백 스택에 이미 존재하는 Fragment가 아닌 경우에만 추가
        if (!manager.popBackStackImmediate(tag, 0)) {
            fragTransaction.addToBackStack(tag)
        }
        fragTransaction.commitAllowingStateLoss()
    }

    // 뒤로가기 버튼 처리
    override fun onBackPressed() {
        val manager: FragmentManager = supportFragmentManager
        if (manager.backStackEntryCount > 0) {
            manager.popBackStack()
        } else {
            if (System.currentTimeMillis() > backPressedTime + DOUBLE_BACK_PRESS_INTERVAL) {
                backPressedTime = System.currentTimeMillis()
                Toast.makeText(this, "뒤로가기 버튼을 한번 더 누르면 종료됩니다.", Toast.LENGTH_SHORT).show()
            } else {
                super.onBackPressed()
            }
        }
    }

}
