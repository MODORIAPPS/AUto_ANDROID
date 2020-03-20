package com.modori.kwonkiseokee.AUto.ListOfPhotos

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.Glide
import com.modori.kwonkiseokee.AUto.PhotoDetail.PhotoDetailViewK
import com.modori.kwonkiseokee.AUto.R
import com.modori.kwonkiseokee.AUto.ShowPhotoOnly
import com.modori.kwonkiseokee.AUto.data.data.Results

class ListOfPhotosAdapter(val context: Context, val photoData: ArrayList<Results>) : RecyclerView.Adapter<ListOfPhotosAdapter.ViewHolder>() {

    var cnt = 0
    lateinit var circularProgressDrawable: CircularProgressDrawable

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.photo_item, parent, false)
        val params = view.layoutParams as ViewGroup.MarginLayoutParams

        circularProgressDrawable = CircularProgressDrawable(context)
        circularProgressDrawable.strokeWidth = 5f
        circularProgressDrawable.centerRadius = 30f
        circularProgressDrawable.start()
//
//        if(cnt % 2 == 0){
//            params.leftMargin = 14
//            params.rightMargin = 7
//        }else{
//            params.rightMargin = 14
//            params.leftMargin = 7
//        }



        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return photoData.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {



        val photoUrl = this.photoData[position].urls.regular
        val photoId = this.photoData[position].id

        Glide.with(context).load(photoUrl).placeholder(circularProgressDrawable).centerCrop().into(holder.imageCleanView)

        holder.photoCardOfList.setOnClickListener {
            val intent = Intent(it.context, PhotoDetailViewK::class.java)
            intent.putExtra("id", photoId)
            it.context.startActivity(intent)
        }

        holder.photoCardOfList.setOnLongClickListener {
            val intent = Intent(it.context, ShowPhotoOnly::class.java)
            intent.putExtra("photoUrl", photoUrl)
            context.startActivity(intent)

            false
        }


    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imageCleanView = itemView.findViewById<ImageView>(R.id.imageCleanView)
        val photoCardOfList = itemView.findViewById<CardView>(R.id.photoCardofList)
    }

}