package com.modori.kwonkiseokee.AUto.ListOfPhotos

import android.content.res.Configuration
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.appcompat.widget.SearchView.OnQueryTextListener
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.google.android.material.snackbar.Snackbar
import com.modori.kwonkiseokee.AUto.R
import com.modori.kwonkiseokee.AUto.Util.ColumnQty
import com.modori.kwonkiseokee.AUto.Util.NETWORKS
import com.modori.kwonkiseokee.AUto.data.data.PhotoSearch
import com.modori.kwonkiseokee.AUto.data.data.Results
import kotlinx.android.synthetic.main.lists_of_photos.*
import kotlinx.android.synthetic.main.select_folder.*

class ListOfPhotoView : AppCompatActivity() {

    var photoCnt = 1
    var viewMode = true
    var isSearchMode = true
    lateinit var adapter: ListOfPhotosAdapter
    var photoArrayList: ArrayList<Results> = ArrayList()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.lists_of_photos)




        if (NETWORKS.getNetWorkType(this) == 0) {
            val builder = AlertDialog.Builder(this).let {
                it.setTitle(getString(R.string.noNetworkErrorTitle))
                it.setMessage(getString(R.string.noNetworkErrorContent))
                it.setPositiveButton(R.string.tab2_DialogOk) { _, _ ->
                }
            }

            builder.show()
        }

        val intent = intent
        val mode = intent.extras.getString("mode")
        var tag = intent.extras.getString("photoID")
        isSearchMode = mode == "search"

        val listOfPhotosViewModel = ViewModelProviders.of(this).get(ListOfPhotosViewModel::class.java)
        listOfPhotosViewModel.init()

        goInfo_list.setOnClickListener { makeDialog() }
        goBack.setOnClickListener { finish() }
        setToGridView.setOnClickListener { setGridView() }

        if (isSearchMode) {
            searchView.visibility = View.VISIBLE
            questMessage.visibility = View.VISIBLE

            searchView.setOnQueryTextListener(object : OnQueryTextListener {
                override fun onQueryTextSubmit(query: String?): Boolean {
                    println("진입")
                    searchView.query?.let {
                        tag = it.toString()
                        println("불러오는 중...")
                    }

                    photoArrayList.clear()

                    getPhotoByKeyword(listOfPhotosViewModel, tag)
                    setUpRecyclerView(listOfPhotosViewModel, tag)

                    //adapter.notifyDataSetChanged()



                    return true
                }

                override fun onQueryTextChange(newText: String?): Boolean {
                    return false
                }

            })
        } else {
            searchMode.visibility = View.GONE

            setUpRecyclerView(listOfPhotosViewModel, tag!!)
            getPhotoByKeyword(listOfPhotosViewModel, tag!!)
        }


        refreshLayout.setOnRefreshListener {
            getPhotoByKeyword(listOfPhotosViewModel,tag)
            refreshLayout.isRefreshing = false
        }


        supportActionBar?.hide()
    }

    fun setUpRecyclerView(listOfPhotosViewModel: ListOfPhotosViewModel, tag: String) {
        val linearLayoutManager = LinearLayoutManager(this)
        recyclerView.layoutManager = linearLayoutManager
        viewMode = true

        recyclerView.setHasFixedSize(true)
        recyclerView.itemAnimator = DefaultItemAnimator()
        recyclerView.isNestedScrollingEnabled = true

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            recyclerView.setOnScrollChangeListener { v, scrollX, scrollY, oldScrollX, oldScrollY ->

                if (!recyclerView.canScrollVertically(-1)) {
                    //Log.i(TAG, "Top of list");

                } else if (!recyclerView.canScrollVertically(1)) {
                    //Log.i(TAG, "End of list");

                    getPhotoByKeyword(listOfPhotosViewModel, tag)
                } else {
                    //Log.i(TAG, "idle");

                }

            }
        } else {
            val visibleItemCount = linearLayoutManager.childCount
            val totalItemCount = linearLayoutManager.itemCount
            val pastVisibleItems = linearLayoutManager.findFirstCompletelyVisibleItemPosition()

            if (pastVisibleItems + visibleItemCount >= totalItemCount) {
                // End of the list is here.
                //Log.i(TAG, "End of list");
                getPhotoByKeyword(listOfPhotosViewModel, tag)
            }
        }
    }

    fun setGridView() {
        val mNoOfColumns = ColumnQty.calculateNoOfColumns(applicationContext, 150f)
        val gridLayoutManager = GridLayoutManager(this, mNoOfColumns)
        val linearLayoutManager = LinearLayoutManager(this)

        if (!viewMode) {
            recyclerView.layoutManager = linearLayoutManager
            viewMode = true
        } else {
            recyclerView.layoutManager = gridLayoutManager
            viewMode = false
        }
    }

    fun getPhotoByKeyword(viewModel: ListOfPhotosViewModel, tag: String) {
        viewModel.getListofPhotos(tag).observe(this, Observer {
            if (isKeywordAvail(it)) {
                questMessage.visibility = View.GONE

                photoArrayList.addAll(it.results)

                if (photoCnt == 1) {
                    adapter = ListOfPhotosAdapter(this, photoArrayList)
                    recyclerView.adapter = adapter

                    val getStr = resources.getString(R.string.tab3_showPictGetCnt)
                    print(it.total)
                    showPictGetCnt.text = "${it.total}  $getStr"
                } else {
                    adapter.notifyItemInserted(photoCnt * 10)
                }

                photoCnt++
                Log.d("ListOfPhotoView.kt", "잘 가져옴")


            } else {
                Log.d("ListOfPhotoView.kt", "불존재 키워드 오류")
                Snackbar.make(mainListLayout, "존재하지 않는 키워드입니다.", Snackbar.LENGTH_SHORT).show()
            }


        })
    }


    private fun isKeywordAvail(response: PhotoSearch): Boolean {
        return response.total != "0"
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)

        val config = resources.configuration
        if (config.orientation == Configuration.ORIENTATION_LANDSCAPE) {

        } else {
            recyclerView.layoutManager = LinearLayoutManager(this)
        }


    }

    fun makeDialog() {
        val builder = AlertDialog.Builder(this).let {
            it.setTitle(R.string.ListsPfPhotos_DialogTitle)
            it.setMessage(R.string.ListsPfPhotos_DialogMessage)
            it.setPositiveButton(R.string.ListsPfPhotos_DialogOk) { _, _ ->
            }
        }
        builder.show()
    }

}