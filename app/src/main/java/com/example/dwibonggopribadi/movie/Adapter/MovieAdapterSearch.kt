package com.example.dwibonggopribadi.movie.Adapter

import android.content.Intent
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.example.dwibonggopribadi.movie.Halaman_kerja.Halaman_deskripsi
import com.example.dwibonggopribadi.movie.Model.IMAGE_BASE_URL
import com.example.dwibonggopribadi.movie.Model.Movie
import kotlinx.android.synthetic.main.list_searh.view.*

class MovieAdapterSearch (val movies : ArrayList<Movie>) : RecyclerView.Adapter<MovieAdapterSearch.MovieViewHolder>() {

    override fun onBindViewHolder(holder: MovieAdapterSearch.MovieViewHolder, position: Int) {
        holder.bind(movies.get(position))
    }

    override fun getItemCount() = movies.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieAdapterSearch.MovieViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(com.example.dwibonggopribadi.movie.R.layout.list_searh, parent, false)
        return MovieAdapterSearch.MovieViewHolder(view)
    }

    class MovieViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener {
        val view : View = itemView
        var movie : Movie? = null

        override fun onClick(p0: View) {

        }

        init {
            itemView.setOnClickListener(this)
        }

        fun bind(movie: Movie) {
            this.movie = movie
            var posterPath = StringBuilder()
            posterPath.append(IMAGE_BASE_URL).append(movie.posterPath)
            Glide.with(view.context).load(posterPath.toString()).into(view.img_srch_gambar)
            view.txt_srch_nama.setText(movie.originalTitle)
            view.txt_srch_tahun.setText(movie.release_date)

            view.setOnClickListener{
                val inten=Intent(view.context, Halaman_deskripsi::class.java)
                inten.putExtra("data1",movie.originalTitle.toString())
                inten.putExtra("data2",posterPath.toString())
                inten.putExtra("data3",movie.overview.toString())
                inten.putExtra("data4",movie.vote_avarage.toString())
                inten.putExtra("data5",movie.release_date.toString())
                inten.putExtra("data6",movie.genre.toString())
                inten.putExtra("data7",movie.popularity.toString())
                view.context.startActivity(inten)
            }
        }
    }
}