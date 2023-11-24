package com.example.moHaeng.addEvent

import android.app.DatePickerDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.moHaeng.MainActivity
import com.example.moHaeng.databinding.FragmentAddItemBinding
import java.util.Calendar


class AddEventFragment : Fragment() {
    private lateinit var binding: FragmentAddItemBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAddItemBinding.inflate(inflater, container, false)

        setupDate()

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.backButton.setOnClickListener {
            (activity as MainActivity).onBackPressed()
        }

    }


    private fun showDatePickerDialog(listener: DatePickerDialog.OnDateSetListener) {
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        context?.let {
            DatePickerDialog(it, listener, year, month, day).show()
        }
    }

    private fun setupDate() {
        binding.eventDateStart.setOnClickListener {
            showDatePickerDialog { _, year, month, day ->
                binding.eventDateStartText.text =
                    "$year/${month + 1}/$day"
            }
        }

        binding.eventDateEnd.setOnClickListener {
            showDatePickerDialog { _, year, month, day ->
                binding.eventDateEndText.text =
                    "$year/${month + 1}/$day"
            }
        }
    }

}
