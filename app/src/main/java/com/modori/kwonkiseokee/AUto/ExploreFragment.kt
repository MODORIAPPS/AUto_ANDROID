package com.modori.kwonkiseokee.AUto

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.Glide
import com.modori.kwonkiseokee.AUto.RetrofitService.RetrofitService
import com.modori.kwonkiseokee.AUto.RetrofitService.api.SearchApi
import com.modori.kwonkiseokee.AUto.adapters.TagListsAdapter
import com.modori.kwonkiseokee.AUto.data.AppDatabase
import com.modori.kwonkiseokee.AUto.data.Tag
import com.modori.kwonkiseokee.AUto.data.TagListRepository
import com.modori.kwonkiseokee.AUto.data.api.PhotoSearch
import com.modori.kwonkiseokee.AUto.utilities.NetworkTool
import com.modori.kwonkiseokee.AUto.viewmodels.TagListViewModel
import com.modori.kwonkiseokee.AUto.viewmodels.TagListViewModelFactory
import kotlinx.android.synthetic.main.tab2_frag.*
import kotlinx.android.synthetic.main.tab2_frag.view.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Call
import retrofit2.Response

class ExploreFragment : Fragment(), View.OnClickListener {

    lateinit var viewModel: TagListViewModel
    lateinit var circularProgressDrawable: CircularProgressDrawable
    lateinit var tagLists: List<Tag>
    lateinit var mView: View

    override fun onClick(v: View?) {
        val intent = Intent(activity, ListOfPhotoActivity::class.java)
        when (v?.id) {
            R.id.goReset -> {
                val builder = AlertDialog.Builder(context)
                builder.setTitle(getString(R.string.tab2_resetContent))
                        .setMessage(getString(R.string.tab2_resetContent))
                        .setPositiveButton(getString(R.string.tab2_DialogOk)) { _, _ ->
                            viewModel.resetTagList()
                        }
                builder.show()
            }

            R.id.inputKeyword -> {
                intent.putExtra("mode", "search")
                startActivity(intent)
            }

            R.id.grid1 -> {
                intent.putExtra("photoID", tagLists[0].tag)
                intent.putExtra("mode", "none")
                startActivity(intent)
            }
            R.id.grid2 -> {
                intent.putExtra("photoID", tagLists[1].tag)
                intent.putExtra("mode", "none")
                startActivity(intent)
            }
            R.id.grid3 -> {
                intent.putExtra("photoID", tagLists[2].tag)
                intent.putExtra("mode", "none")
                startActivity(intent)
            }
            R.id.grid4 -> {
                intent.putExtra("photoID", tagLists[3].tag)
                intent.putExtra("mode", "none")
                startActivity(intent)
            }
            R.id.grid5 -> {
                intent.putExtra("photoID", tagLists[4].tag)
                intent.putExtra("mode", "none")
                startActivity(intent)
            }
            R.id.grid6 -> {
                intent.putExtra("photoID", tagLists[5].tag)
                intent.putExtra("mode", "none")
                startActivity(intent)
            }

        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        mView = inflater.inflate(R.layout.tab2_frag, container, false)

        mView.inputKeyword.setOnClickListener(this)
        mView.grid1.setOnClickListener(this)
        mView.grid2.setOnClickListener(this)
        mView.grid3.setOnClickListener(this)
        mView.grid4.setOnClickListener(this)
        mView.grid5.setOnClickListener(this)
        mView.grid6.setOnClickListener(this)

        // RecyclerView
        val layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        mView.viewTagLists.layoutManager = layoutManager

        // ProgressDrawable Of Each Grids
        circularProgressDrawable = CircularProgressDrawable(mView.context)
        circularProgressDrawable.strokeWidth = 5f
        circularProgressDrawable.centerRadius = 30f
        circularProgressDrawable.start()

        // Ads Request


        // SetUp ViewModel
        CoroutineScope(Dispatchers.IO).launch {
            val dao = AppDatabase.getInstance(mView.context).tagDao
            val repo = TagListRepository(dao)
            val factory = TagListViewModelFactory(repo)
            viewModel = ViewModelProvider(viewModelStore, factory).get(TagListViewModel::class.java)

            if (!viewModel.tagAvailable) {
                viewModel.resetTagList()
            }
            withContext(Dispatchers.Main) {
                updateTagList()
            }

        }


        networkCheck()

        return mView
    }

    private fun networkCheck(): Boolean {
        if (NetworkTool.getNetWorkType(context) == 0) {
            val builder = AlertDialog.Builder(context)
            builder.setTitle(getString(R.string.noNetworkErrorTitle))
                    .setMessage(getString(R.string.noNetworkErrorContent))
                    .setPositiveButton(R.string.tab2_DialogOk) { _, _ ->

                    }

            builder.show()
            return false
        }
        return true
    }

    private fun updateTagList() {
        viewModel.tagLists.observe(this, androidx.lifecycle.Observer {
            Log.d("ExploreFragmentK", it.toString())

            tagLists = it

            if (tagLists.isNotEmpty()) {
                setEachTagToGrids(it)
                getPhotosEachTag(it)
                viewTagLists.adapter = TagListsAdapter(context, tagLists, viewModel)
            }

        })
    }

    private fun setEachTagToGrids(tagList: List<Tag>) {
        mView.view_tag1Grid.text = tagList[0].tag
        mView.view_tag2Grid.text = tagList[1].tag
        mView.view_tag3Grid.text = tagList[2].tag
        mView.view_tag4Grid.text = tagList[3].tag
        mView.view_tag5Grid.text = tagList[4].tag
        mView.view_tag6Grid.text = tagList[5].tag
    }

    private fun getPhotosEachTag(tagList: List<Tag>) {

        Log.d("TagList", tagList.toString())

        RetrofitService.createService(SearchApi::class.java).getPhotobyKeyward(tagList[0].tag, 1, 1)
                .enqueue(object : retrofit2.Callback<PhotoSearch> {

                    override fun onResponse(call: Call<PhotoSearch>, response: Response<PhotoSearch>) {
                        if (response.isSuccessful) {
                            setImageView(response.body()!!.results[0].urls.small, tag1Gridview)

                        }
                    }

                    override fun onFailure(call: Call<PhotoSearch>, t: Throwable) {
                    }


                })

        RetrofitService.createService(SearchApi::class.java).getPhotobyKeyward(tagList[1].tag, 1, 1)
                .enqueue(object : retrofit2.Callback<PhotoSearch> {

                    override fun onResponse(call: Call<PhotoSearch>, response: Response<PhotoSearch>) {
                        if (response.isSuccessful) {
                            setImageView(response.body()!!.results[0].urls.small, tag2Gridview)

                        }
                    }

                    override fun onFailure(call: Call<PhotoSearch>, t: Throwable) {
                    }


                })


        RetrofitService.createService(SearchApi::class.java).getPhotobyKeyward(tagList[2].tag, 1, 1)
                .enqueue(object : retrofit2.Callback<PhotoSearch> {

                    override fun onResponse(call: Call<PhotoSearch>, response: Response<PhotoSearch>) {
                        if (response.isSuccessful) {
                            setImageView(response.body()!!.results[0].urls.small, tag3Gridview)

                        }
                    }

                    override fun onFailure(call: Call<PhotoSearch>, t: Throwable) {
                    }


                })


        RetrofitService.createService(SearchApi::class.java).getPhotobyKeyward(tagList[3].tag, 1, 1)
                .enqueue(object : retrofit2.Callback<PhotoSearch> {

                    override fun onResponse(call: Call<PhotoSearch>, response: Response<PhotoSearch>) {
                        if (response.isSuccessful) {
                            setImageView(response.body()!!.results[0].urls.small, tag4Gridview)

                        }
                    }

                    override fun onFailure(call: Call<PhotoSearch>, t: Throwable) {
                    }


                })

        RetrofitService.createService(SearchApi::class.java).getPhotobyKeyward(tagList[4].tag, 1, 1)
                .enqueue(object : retrofit2.Callback<PhotoSearch> {

                    override fun onResponse(call: Call<PhotoSearch>, response: Response<PhotoSearch>) {
                        if (response.isSuccessful) {
                            setImageView(response.body()!!.results[0].urls.small, tag5Gridview)

                        }
                    }

                    override fun onFailure(call: Call<PhotoSearch>, t: Throwable) {
                    }


                })

        RetrofitService.createService(SearchApi::class.java).getPhotobyKeyward(tagList[5].tag, 1, 1)
                .enqueue(object : retrofit2.Callback<PhotoSearch> {

                    override fun onResponse(call: Call<PhotoSearch>, response: Response<PhotoSearch>) {
                        if (response.isSuccessful) {
                            setImageView(response.body()!!.results[0].urls.small, tag6Gridview)

                        }
                    }

                    override fun onFailure(call: Call<PhotoSearch>, t: Throwable) {
                    }


                })
    }

    fun setImageView(photoUrl: String, target: ImageView) {
//        if(activity != null){
//            if(!activity.isFinishing){
//                Glide.with(mView.context).load(photoUrl).into(target)
//            }
//        }

        Glide.with(mView.context).load(photoUrl).placeholder(circularProgressDrawable).into(target)

    }
}