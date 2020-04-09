package com.modori.kwonkiseokee.AUto

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.modori.kwonkiseokee.AUto.adapters.TagListsAdapter
import com.modori.kwonkiseokee.AUto.adapters.TagPreviewAdapter
import com.modori.kwonkiseokee.AUto.data.AppDatabase
import com.modori.kwonkiseokee.AUto.data.Tag
import com.modori.kwonkiseokee.AUto.data.TagListRepository
import com.modori.kwonkiseokee.AUto.utilities.NetWorkTool
import com.modori.kwonkiseokee.AUto.viewmodels.TagListViewModel
import com.modori.kwonkiseokee.AUto.viewmodels.TagListViewModelFactory
import kotlinx.android.synthetic.main.tab2_frag.*
import kotlinx.android.synthetic.main.tab2_frag.view.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ExploreFragment : Fragment(), View.OnClickListener {

    lateinit var viewModel: TagListViewModel
    lateinit var circularProgressDrawable: CircularProgressDrawable
    lateinit var tagLists: List<Tag>
    lateinit var mView: View

    lateinit var adapter: TagPreviewAdapter

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

        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        mView = inflater.inflate(R.layout.tab2_frag, container, false)

        mView.inputKeyword.setOnClickListener(this)

        // RecyclerView
        val layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        mView.viewTagLists.layoutManager = layoutManager

        val layoutManager2 = LinearLayoutManager(context, LinearLayoutManager.VERTICAL,false)
        mView.tagPreviewRecyclerView.layoutManager = layoutManager2

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
        if (NetWorkTool.getNetWorkType(requireContext()) == 0) {
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
        viewModel.tagLists.observe(viewLifecycleOwner, androidx.lifecycle.Observer {
            Log.d("ExploreFragmentK", it.toString())

            tagLists = it

            if (tagLists.isNotEmpty()) {
                tagPreviewRecyclerView.adapter = TagPreviewAdapter(requireContext(),tagLists,viewModel)
                tagPreviewRecyclerView.scheduleLayoutAnimation()
                viewTagLists.adapter = TagListsAdapter(context, tagLists, viewModel)
            }

        })
    }

}