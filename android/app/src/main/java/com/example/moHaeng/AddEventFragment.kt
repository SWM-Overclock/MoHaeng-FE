package com.example.moHaeng

import android.app.DatePickerDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.moHaeng.databinding.FragmentAddItemBinding
import java.util.Calendar

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [AddEventFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class AddEventFragment : Fragment() {
    private lateinit var binding: FragmentAddItemBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAddItemBinding.inflate(inflater, container, false)

        setupDate()

        return binding.root
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
