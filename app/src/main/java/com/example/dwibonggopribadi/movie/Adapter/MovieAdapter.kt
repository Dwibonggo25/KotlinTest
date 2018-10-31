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
import kotlinx.android.synthetic.main.movie_list.view.*

class MovieAdapter (val movies : ArrayList<Movie>) : RecyclerView.Adapter<MovieAdapter.MovieViewHolder>() {

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        holder.bind(movies.get(position))
    }

    override fun getItemCount() = movies.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(com.example.dwibonggopribadi.movie.R.layout.movie_list, parent, false)
        return MovieViewHolder(view)
    }

    class MovieViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener {
        private var view : View = itemView
        private var movie : Movie? = null

        override fun onClick(p0: View?) {

        }

        init {
            itemView.setOnClickListener(this)
        }

        fun bind(movie: Movie) {
            this.movie = movie
            val imageUrl = StringBuilder()
            imageUrl.append(IMAGE_BASE_URL).append(movie.posterPath)
            view.txt_movie.setText(movie.originalTitle)
            Glide.with(view.context).load(imageUrl.toString()).into(view.Img_movie)

            view.setOnClickListener{
                val inten=Intent(view.context, Halaman_deskripsi::class.java)
                inten.putExtra("data1",movie.originalTitle.toString())
                inten.putExtra("data2",imageUrl.toString())
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