package com.example.dwibonggopribadi.movie.Halaman_kerja

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.OrientationHelper
import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.example.dwibonggopribadi.movie.Adapter.MovieAdapter
import com.example.dwibonggopribadi.movie.Model.API_KEY
import com.example.dwibonggopribadi.movie.Model.Movie
import com.example.dwibonggopribadi.movie.Model.MovieResponse
import com.example.dwibonggopribadi.movie.R
import com.example.dwibonggopribadi.movie.Services.ApiInterface
import com.example.dwibonggopribadi.movie.Services.ApliClient
import kotlinx.android.synthetic.main.activity_halaman_deskripsi.*
import retrofit2.Call
import retrofit2.Response

class Halaman_deskripsi : AppCompatActivity() {

    private lateinit var nama: TextView
    private lateinit var nama1: TextView
    private lateinit var gambar: ImageView
    private lateinit var detail: TextView
    private lateinit var release: TextView
    private lateinit var rating: TextView
    private lateinit var genre: TextView
    private lateinit var popular: TextView
    private val TAG : String?= Halaman_deskripsi::class.java.canonicalName
    private lateinit var movies : ArrayList<Movie>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_halaman_deskripsi)

        //Deklarasi
        nama=findViewById(R.id.txt_detail_nama_movie)
        nama1=findViewById(R.id.txt_detail_nama)
        gambar=findViewById(R.id.Img_detail_movie)
        detail=findViewById(R.id.txt_sinopsis)
        rating=findViewById(R.id.txt_rating_movie)
        release=findViewById(R.id.txt_detail_release)
        genre=findViewById(R.id.txt_detail_genre)
        popular=findViewById(R.id.txt_detail_popularity)

        //SetUp Text dan Gambar
        nama.setText(intent.getStringExtra("data1"))
        nama1.setText(intent.getStringExtra("data1"))
        Glide.with(this)
                .load(intent.getStringExtra("data2"))
                .into(gambar)
        detail.setText(intent.getStringExtra("data3"))
        rating.setText(intent.getStringExtra("data4"))
        release.setText(intent.getStringExtra("data5"))
        genre.setText(intent.getStringExtra("data6"))
        popular.setText(intent.getStringExtra("data7"))

        //Setting RecycleViewer
        rcy_srch_detail.layoutManager = LinearLayoutManager (this, OrientationHelper.HORIZONTAL, false)
        val apiInterface:ApiInterface=ApliClient.getClient()
        getPopularMovie (apiInterface , API_KEY)

    }

    fun getPopularMovie( apiInterface: ApiInterface, apiKey: String) {
        val call: retrofit2.Call <MovieResponse> =apiInterface.getPopularMovie(apiKey)
        call.enqueue(object : retrofit2.Callback <MovieResponse>{

            override fun onFailure(call: Call<MovieResponse>, t: Throwable) {
                Log.d("$TAG", "Gagal Update Movie" )
            }

            override fun onResponse(call: Call<MovieResponse>, response: Response<MovieResponse>) {
                movies=response.body()!!.results
                Log.d("$TAG", "Movie size ${movies.size}")
                rcy_srch_detail.adapter = MovieAdapter(movies)
            }
        })
    }

}
