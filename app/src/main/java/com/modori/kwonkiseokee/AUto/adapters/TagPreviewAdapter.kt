package com.modori.kwonkiseokee.AUto.adapters

import android.content.Context
import android.content.Intent
import android.util.DisplayMetrics
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.Glide
import com.modori.kwonkiseokee.AUto.ListOfPhotoActivity
import com.modori.kwonkiseokee.AUto.R
import com.modori.kwonkiseokee.AUto.RetrofitService.RetrofitService
import com.modori.kwonkiseokee.AUto.RetrofitService.api.SearchApi
import com.modori.kwonkiseokee.AUto.data.Tag
import com.modori.kwonkiseokee.AUto.data.api.PhotoSearch
import com.modori.kwonkiseokee.AUto.utilities.OpenDialog
import com.modori.kwonkiseokee.AUto.viewmodels.TagListViewModel
import retrofit2.Call
import retrofit2.Response

class TagPreviewAdapter(val context: Context, val tagList: List<Tag>, val tagViewModel: TagListViewModel) : RecyclerView.Adapter<TagPreviewAdapter.ViewHolder>() {

    lateinit var circularProgress: CircularProgressDrawable

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.tag_preview_item, parent, false)

        circularProgress = CircularProgressDrawable(context)
        circularProgress.strokeWidth = 5f
        circularProgress.centerRadius = 30f
        circularProgress.start()

        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return tagList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val tag = tagList[position].tag

        RetrofitService.createService(SearchApi::class.java).getPhotobyKeyward(tag, 1, 1)
                .enqueue(object : retrofit2.Callback<PhotoSearch> {

                    override fun onResponse(call: Call<PhotoSearch>, response: Response<PhotoSearch>) {
                        if (response.isSuccessful) {
                            Glide.with(context).load(response.body()!!.results[0].urls.small).placeholder(circularProgress).into(holder.tagImageView)
                        }
                    }

                    override fun onFailure(call: Call<PhotoSearch>, t: Throwable) {
                        
                    }

                })

        holder.tagTextView.text = tag

        holder.parentLayout.setOnClickListener {
            val intent = Intent(context, ListOfPhotoActivity::class.java)
            intent.putExtra("photoID", tag)
            intent.putExtra("mode", "none")
            context.startActivity(intent)
        }

        holder.parentLayout.setOnLongClickListener {
            Log.d("선택된 TAG 와 position", "$tag | $position")

            val title: String = context.resources.getString(R.string.openDialog_title)
            val subtitle: String = context.getString(R.string.openDialog_content).toString() + " '" + tag + "' " + context.getString(R.string.openDialog_content2)

            // 화면 조정

            // 화면 조정
            val dm: DisplayMetrics = context.resources.displayMetrics //디바이스 화면크기를 구하기위해

            val width = dm.widthPixels //디바이스 화면 너비

            val height = dm.heightPixels //디바이스 화면 높이


            val openDialog = OpenDialog(context, position, title, subtitle, tagList, tagViewModel)
            val wm: WindowManager.LayoutParams = openDialog.window!!.attributes //다이얼로그의 높이 너비 설정하기위해

            wm.copyFrom(openDialog.window?.attributes) //여기서 설정한값을 그대로 다이얼로그에 넣겠다는의미

            wm.width = width //화면 너비의 절반

            // wm.height = height / 3;  //화면 높이의 1/3
            // wm.height = height / 3;  //화면 높이의 1/3

            openDialog.show()

            true
        }

    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tagImageView = itemView.findViewById<ImageView>(R.id.tagImageView)
        val tagTextView = itemView.findViewById<TextView>(R.id.tagTextView)
        val parentLayout = itemView.findViewById<View>(R.id.parentLayout)
    }
}