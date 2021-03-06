package com.gongmanse.app.feature.main.home.tabs

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.gongmanse.app.R
import com.gongmanse.app.data.model.video.VideoBody
import com.gongmanse.app.data.network.home.VideoRepository
import com.gongmanse.app.databinding.FragmentBestBinding
import com.gongmanse.app.feature.main.home.VideoViewModel
import com.gongmanse.app.feature.main.home.VideoViewModelFactory
import com.gongmanse.app.utils.Constants
import com.gongmanse.app.utils.EndlessRVScrollListener
import com.gongmanse.app.utils.Preferences


class HomeBestFragment : Fragment(), SwipeRefreshLayout.OnRefreshListener {

    companion object {
        private val TAG = HomeBestFragment::class.java.simpleName
    }

    private lateinit var binding: FragmentBestBinding
    private lateinit var scrollListener: EndlessRVScrollListener
    private lateinit var mRecyclerAdapter: HomeBestAdapter
    private lateinit var mSliderAdapter: HomeBestSliderAdapter
    private lateinit var mVideoViewModelFactory: VideoViewModelFactory
    private lateinit var videoViewModel: VideoViewModel
    private val linearLayoutManager = LinearLayoutManager(context)
    private var mOffset: Int = 0
    private var isLoading = false

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_best, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
    }

    override fun onRefresh() {
        mOffset = 0
        videoViewModel.liveDataClear()
        mRecyclerAdapter.clear()
        isLoading = false
        binding.refreshLayout.isRefreshing = false
        prepareData()
    }

    private fun initView() {
        if (::mVideoViewModelFactory.isInitialized.not()) {
            mVideoViewModelFactory = VideoViewModelFactory(VideoRepository())
        }
        if (::videoViewModel.isInitialized.not()) {
            videoViewModel = ViewModelProvider(this, mVideoViewModelFactory).get(VideoViewModel::class.java)
        }
        binding.refreshLayout.setOnRefreshListener(this)
        videoViewModel = ViewModelProvider(this).get(VideoViewModel::class.java)
        videoViewModel.currentValue.observe( viewLifecycleOwner) {
            if(isLoading) mRecyclerAdapter.removeLoading()
            mRecyclerAdapter.addItems(it)
//            Log.d(TAG,"$it")
            isLoading = false
        }
        videoViewModel.currentBannerValue.observe(viewLifecycleOwner) {
            mSliderAdapter.addItems(it)
//            Log.d(TAG,"$it")
        }

        setRVLayout()
        prepareData()
    }

    fun scrollToTop() {
        binding.rvVideo.smoothScrollToPosition(0)
    }

    private fun setRVLayout() {
        binding.rvVideo.apply {
            setHasFixedSize(true)
            setItemViewCacheSize(20)
            layoutManager = linearLayoutManager
        }
        if (binding.rvVideo.adapter == null) {
            mSliderAdapter = HomeBestSliderAdapter()
            mRecyclerAdapter = HomeBestAdapter(mSliderAdapter)
            binding.rvVideo.adapter = mRecyclerAdapter
        }
    }

    private fun loadVideo() {
        var sGrade : String? = null
        if(Preferences.grade.isEmpty()){
            Log.d(TAG, "Preferences.grade => ${Preferences.grade}")
        }else {
            sGrade = when (Preferences.grade[0]) {
                '???' -> Constants.GradeType.ELEMENTARY
                '???' -> Constants.GradeType.MIDDLE
                '???' -> Constants.GradeType.HIGH
                else -> null
            }
        }
        videoViewModel.loadBest(sGrade,mOffset,Constants.DefaultValue.LIMIT_INT)
        videoViewModel.loadBanner()
    }

    private fun prepareData() {
        // ?????? ??????
        val bannerData = VideoBody().apply { viewType = Constants.ViewType.BANNER }
        val titleData = VideoBody().apply { viewType = Constants.ViewType.TITLE }
        mRecyclerAdapter.addItems(listOf(bannerData, titleData))

        loadVideo()

        // ????????? ?????????
        scrollListener = object : EndlessRVScrollListener(linearLayoutManager) {
            override fun onLoadMore(offset: Int, totalItemsCount: Int, view: RecyclerView?) {
                if (!isLoading && mOffset != totalItemsCount && totalItemsCount >= 20) {
                    isLoading = true
                    loadMoreData(totalItemsCount)
                }
            }
        }

        // ????????? ????????? ?????????
        binding.rvVideo.addOnScrollListener(scrollListener)
        scrollListener.resetState()
    }

    private fun loadMoreData(offset: Int) {
        mOffset = offset
//        Log.e(TAG,"loadMoreData offset => $offset")
        if (isLoading) {
            mRecyclerAdapter.addLoading()
        }
        Handler(Looper.getMainLooper()).postDelayed({
            loadVideo()
        }, Constants.Delay.VALUE_OF_ENDLESS)
    }

}
