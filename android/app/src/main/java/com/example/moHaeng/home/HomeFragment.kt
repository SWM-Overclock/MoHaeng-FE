package com.example.moHaeng.home

import android.app.AlertDialog
import android.content.Context
import android.content.res.Resources
import android.os.Bundle
import android.os.Handler
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.moHaeng.CategoryAdapter
import com.example.moHaeng.CategoryButtonFragment
import com.example.moHaeng.MainActivity
import com.example.moHaeng.productSearch.CategoryItem
import com.example.moHaeng.productSearch.ProductItem
import com.example.moHaeng.R
import com.example.moHaeng.productSearch.RankingAdapter
import com.example.moHaeng.databinding.FragmentHomeBinding
import com.example.moHaeng.location.SetLocationFragment
import com.example.moHaeng.productSearch.ProductRankingFragment

class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding
    private val itemList: List<CategoryItem> = generateDummyCategoryData()
    private val productItemList: List<ProductItem> = generateDummyProductData()
    private val autoScrollHandler = Handler()
    private lateinit var autoScrollRunnable: Runnable


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater, container, false)

        setupCategoryRecyclerView()
        setupCategoryButtonFragment()
        setupRankingRecyclerView()
        setupLocationContainer()
        setupEventCardViewPager()
        setLocationName()

        return binding.root
    }



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.rankingFindMoreButton.setOnClickListener {
            setupRankingFindMoreButton()
        }

        binding.alarmButton.setOnClickListener {
            // 버튼이 클릭되었을 때 AlertDialog 띄우기
            showAlertDialog()
        }
    }

    private fun showAlertDialog() {
        val alertDialogBuilder = AlertDialog.Builder(requireContext())

        alertDialogBuilder.setTitle("알림")
        alertDialogBuilder.setMessage("알람을 설정하시겠습니까?")

        alertDialogBuilder.setPositiveButton("확인") { dialog, _ ->
            // 사용자가 확인 버튼을 눌렀을 때의 동작
            showToast("알람이 설정되었습니다.")
            dialog.dismiss()
        }

        alertDialogBuilder.setNegativeButton("취소") { dialog, _ ->
            // 사용자가 취소 버튼을 눌렀을 때의 동작
            showToast("알람 설정이 취소되었습니다.")
            dialog.dismiss()
        }

        val alertDialog = alertDialogBuilder.create()
        alertDialog.show()
    }

    //rankingFindMoreButton버튼을 누르면 지금 homeFragment가 있는 activity의 fragment를 변경
    private fun setupRankingFindMoreButton() {
        (activity as MainActivity).setFragment("productRanking", ProductRankingFragment())
    }

    fun showToast(message: String) {
        // Toast 메시지 생성 및 표시
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }

    private fun setLocationName() {
        var locationName = getLocationName(requireContext())
        binding.locationName.text = locationName
    }

    private fun getLocationName(context: Context): String? {
        val sharedPreferences = context.getSharedPreferences("MyPreferences", Context.MODE_PRIVATE)
        return sharedPreferences.getString("primaryLocation", "등록이 필요합니다")
    }

    private fun setupEventCardViewPager() {
        val adList = arrayListOf<Int>(R.drawable.event1, R.drawable.event2, R.drawable.event3)
        val eventCardViewPager = binding.eventCardViewPager
        val eventCardAdapter = AddViewPagerAdapter(adList)
        eventCardViewPager.adapter = eventCardAdapter

        autoScrollRunnable = Runnable {
            val currentItem = eventCardViewPager.currentItem
            val totalItems = eventCardAdapter.itemCount

            // 다음 아이템으로 넘기기 (마지막 아이템이면 처음으로)
            eventCardViewPager.setCurrentItem(if (currentItem + 1 < totalItems) currentItem + 1 else 0, true)

            // 10초 후에 다시 호출
            autoScrollHandler.postDelayed(autoScrollRunnable, 10000)
        }

        // 최초 실행
        autoScrollHandler.postDelayed(autoScrollRunnable, 10000)

        // ViewPager의 터치 이벤트가 발생하면 자동 스크롤을 중지
        eventCardViewPager.setOnTouchListener { _, _ ->
            autoScrollHandler.removeCallbacks(autoScrollRunnable)
            false
        }
    }


    private fun setupCategoryRecyclerView() {
        val recyclerViewList: RecyclerView = binding.categoryRecyclerView
        recyclerViewList.layoutManager = GridLayoutManager(requireContext(), 5, GridLayoutManager.VERTICAL, false)

        val categoryAdapter = CategoryAdapter(itemList)
        recyclerViewList.adapter = categoryAdapter

        val space = (Resources.getSystem().displayMetrics.density).toInt()
        recyclerViewList.addItemDecoration(GridSpaceCategoryList(5, space))
    }

    private fun setupCategoryButtonFragment() {
        val categoryButtonFragment = CategoryButtonFragment()

        childFragmentManager.beginTransaction()
            .replace(R.id.categoryButtonContainer, categoryButtonFragment)
            .commit()
    }


    private fun setupRankingRecyclerView() {
        val recyclerViewRanking: RecyclerView = binding.rankingRecyclerView
        recyclerViewRanking.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)

        val rankingAdapter = RankingAdapter(productItemList)
        recyclerViewRanking.adapter = rankingAdapter

        val space = (Resources.getSystem().displayMetrics.density).toInt()
        recyclerViewRanking.addItemDecoration(GridSpaceItemDecorationRanking(space))
    }

    private fun generateDummyCategoryData(): List<CategoryItem> {
        return listOf(
            CategoryItem("편의점", R.drawable.category_convi),
            CategoryItem("카페", R.drawable.category_cafe),
            CategoryItem("음식점", R.drawable.category_food),
            CategoryItem("문화생활", R.drawable.category_culture),
            CategoryItem("과자", R.drawable.category_snack),
            CategoryItem("음료", R.drawable.category_drink),
            CategoryItem("아이스크림", R.drawable.category_ice),
            CategoryItem("즉석식품", R.drawable.category_instant),
            CategoryItem("유제품", R.drawable.category_daily),
            CategoryItem("전체보기", R.drawable.category_all)
        )
    }

    private fun generateDummyProductData(): List<ProductItem> {
        return listOf(
            ProductItem("상점1", "상품1", "10%", "5,000", "4,500", true, "1"),
            ProductItem("상점2", "상품2", "5%", "10,000", "9,500", true, "2"),
            ProductItem("상점3", "상품3", "0%", "7,000", "7,000", false, "3"),
            ProductItem("상점1", "상품1", "10%", "5,000", "4,500", true, "1"),
            ProductItem("상점2", "상품2", "5%", "10,000", "9,500", true, "2")
        )
    }

    //locationContainer늘 누르면 locationFragment로 이동하는 함수
    private fun setupLocationContainer() {
        binding.locationContainer.setOnClickListener {
            (activity as MainActivity).setFragment("location", SetLocationFragment())
        }
    }

}




