package com.example.moHaeng

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.example.moHaeng.databinding.ActivityMainBinding
import com.example.moHaeng.home.HomeFragment


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
        setFragment(TAG_HOME, HomeFragment())

        binding.navigationView.setOnItemSelectedListener { item ->
            when(item.itemId) {
                R.id.homeFragment -> setFragment(TAG_HOME, HomeFragment())
                R.id.mapFragment -> setFragment(TAG_MAP, MapFragment())
                R.id.addFragment -> setFragment(TAG_ADD_ITEM, AddEventFragment())
                R.id.myPageFragment-> setFragment(TAG_MY_PAGE, MyPageFragment())
            }
            true
        }
    }


    private fun setFragment(tag: String, fragment: Fragment) {
        val manager: FragmentManager = supportFragmentManager
        val fragTransaction = manager.beginTransaction()

        if (manager.findFragmentByTag(tag) == null){
            fragTransaction.add(R.id.mainFragment, fragment, tag)
        }


        val home = manager.findFragmentByTag(TAG_HOME)
        val map = manager.findFragmentByTag(TAG_MAP)
        val addItem = manager.findFragmentByTag(TAG_ADD_ITEM)
        val myPage = manager.findFragmentByTag(TAG_MY_PAGE)



        if (home != null){
            fragTransaction.hide(home)
        }

        if (map != null){
            fragTransaction.hide(map)
        }

        if (addItem != null){
            fragTransaction.hide(addItem)
        }

        if (myPage != null) {
            fragTransaction.hide(myPage)
        }

        if (tag == TAG_HOME) {
            if (home != null) {
                fragTransaction.show(home)
            }
        }
        else if (tag == TAG_MAP){
            if (map != null){
                fragTransaction.show(map)
            }
        }
        else if (tag == TAG_ADD_ITEM){
            if (addItem != null){
                fragTransaction.show(addItem)
            }
        }

        else if (tag == TAG_MY_PAGE){
            if (myPage != null){
                fragTransaction.show(myPage)
            }
        }

        fragTransaction.commitAllowingStateLoss()
    }
}