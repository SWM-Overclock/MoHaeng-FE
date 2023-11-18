package com.example.moHaeng

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import com.example.moHaeng.databinding.FragmentSortedBinding

class SortedFragment : Fragment() {

    private var _binding: FragmentSortedBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSortedBinding.inflate(inflater, container, false)
        val view = binding.root

        // Spinner를 찾아서 어댑터를 설정하고 데이터를 추가합니다.
        val spinner = binding.spinner
        val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, sortList.map { it.sortName })
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner.adapter = adapter

        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

val sortList = listOf(
    SortItem("인기순", true),
    SortItem("최신순", false),
    SortItem("가격 낮은순", false),
    SortItem("가격 높은순", false)
)
