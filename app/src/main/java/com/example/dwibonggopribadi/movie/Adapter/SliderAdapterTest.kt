package com.example.dwibonggopribadi.movie.Adapter

import android.content.Context
import android.content.Intent
import android.os.Parcelable
import android.support.v4.view.PagerAdapter
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.example.dwibonggopribadi.movie.Halaman_kerja.Halaman_deskripsi
import com.example.dwibonggopribadi.movie.Model.IMAGE_BASE_URL
import com.example.dwibonggopribadi.movie.Model.Movie
import com.example.dwibonggopribadi.movie.R

class SliderAdapterTest (context: Context, val movies: ArrayList<Movie>): PagerAdapter() {

    private var inflater: LayoutInflater
    private var movie: Movie? = null
    private var context: Context

    init {
        this.movie=movie
        this.context=context
        inflater = LayoutInflater.from(context)
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object` as View)
    }

    override fun getCount(): Int {
        return movies.size
    }

    override fun instantiateItem(view: ViewGroup, position: Int): Any {

        //Deklarasi
        val imageLayout = inflater.inflate(R.layout.sliderview_item, view, false)!!
        val imageView = imageLayout.findViewById(R.id.slider_image1) as ImageView
        val txtcoba=imageLayout.findViewById(R.id.txt_judul) as TextView
        val movieImgUrl = "$IMAGE_BASE_URL${movies.get(position).posterPath}"

        txtcoba.setText("${movies.get(position).originalTitle}")

        Glide.with(context)
                .load(movieImgUrl)
                .into(imageView);
        view.addView(imageLayout, 0)

        imageLayout.setOnClickListener {
            val inten=Intent(view.context, Halaman_deskripsi::class.java)
            inten.putExtra("data1",movies.get(position).originalTitle.toString())
            inten.putExtra("data2",movieImgUrl)
            inten.putExtra("data3",movies.get(position).overview.toString())
            inten.putExtra("data4",movies.get(position).vote_avarage.toString())
            inten.putExtra("data5",movies.get(position).release_date.toString())
            inten.putExtra("data6",movies.get(position).genre.toString())
            inten.putExtra("data7",movies.get(position).popularity.toString())
            view.context.startActivity(inten)
        }
        return imageLayout
    }

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view == `object`
    }

    override fun restoreState(state: Parcelable?, loader: ClassLoader?) {}

    override fun saveState(): Parcelable? {
        return null
    }
}