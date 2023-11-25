package com.example.moHaeng.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.moHaeng.MainActivity
import com.example.moHaeng.R
import com.example.moHaeng.databinding.FragmentAlarmBinding

class AlarmFragment : Fragment() {

    private lateinit var binding: FragmentAlarmBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAlarmBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.backButton.setOnClickListener {
            (activity as MainActivity).setFragment("home", HomeFragment())
        }
    }


}