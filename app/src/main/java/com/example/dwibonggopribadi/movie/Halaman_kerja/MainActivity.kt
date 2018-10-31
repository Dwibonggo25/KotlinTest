package com.example.dwibonggopribadi.movie.Halaman_kerja


import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.support.v4.view.ViewPager
import android.support.v7.widget.GridLayoutManager
import android.util.Log
import android.widget.ImageView
import com.example.dwibonggopribadi.movie.Model.Movie
import com.example.dwibonggopribadi.movie.Adapter.MovieAdapter
import com.example.dwibonggopribadi.movie.Adapter.SliderAdapterTest
import com.example.dwibonggopribadi.movie.Model.API_KEY
import com.example.dwibonggopribadi.movie.Model.MovieResponse
import com.example.dwibonggopribadi.movie.R
import com.example.dwibonggopribadi.movie.Services.ApiInterface
import com.example.dwibonggopribadi.movie.Services.ApliClient
import com.viewpagerindicator.CirclePageIndicator
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*


class MainActivity : AppCompatActivity() {

    private lateinit var mPager: ViewPager
    private var currentPage = 0
    private var NUM_PAGES = 0
    //Deklarasi
    private val TAG : String?= MainActivity::class.java.canonicalName
    private lateinit var movies : ArrayList<Movie>
    private lateinit var img_search: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //Deklarasi Gambar cari
        img_search=findViewById(R.id.img_search)
        img_search.setOnClickListener{
            val intent= Intent(this, SearchActivity::class.java)
            startActivity(intent)
        }

        //Seting recyclerviewer supaya grid ada 2
        rvMovies.layoutManager = GridLayoutManager(applicationContext, 2)

        //Memanggil Function
        val apiInterface : ApiInterface = ApliClient.getClient()
       // getLatestMovie(apiInterface, apiKey)
        getPopularMovies(apiInterface, API_KEY)
        //ImageSlider
        getNowPlaying(apiInterface, API_KEY)
    }

    /*

    companion object {

        private var mPager: ViewPager? = null
        private var currentPage = 0
        private var NUM_PAGES = 0
    }
*/

    //Slider Image
     fun getNowPlaying(apiInterface: ApiInterface, apiKey : String)    {
         val call : retrofit2.Call <MovieResponse> = apiInterface.getNowPlaying(apiKey)
         call.enqueue(object : retrofit2.Callback<MovieResponse> {

             override fun onFailure(call: retrofit2.Call<MovieResponse>?, t: Throwable?) {
                 Log.d("$TAG", "Gagal Fetch Popular Movie")
             }

             override fun onResponse(call: retrofit2.Call<MovieResponse>?, response: retrofit2.Response<MovieResponse?>) {

                 movies =  response.body()!!.results
                 mPager = findViewById(R.id.viewPager) as ViewPager
                 mPager.adapter = SliderAdapterTest(this@MainActivity, movies)

                 val indicator = findViewById(R.id.indicator) as CirclePageIndicator

                 indicator.setViewPager(mPager)

                 val density = resources.displayMetrics.density

                 //Set circle indicator radius
                 indicator.setRadius(2 * density)

                 NUM_PAGES = movies.size

                 // Auto start of viewpager
                 val handler = Handler()
                 val Update = Runnable {
                     if (currentPage == NUM_PAGES) {
                         currentPage = 0
                     }
                     mPager.setCurrentItem(currentPage++, true)
                 }
                 val swipeTimer = Timer()
                 swipeTimer.schedule(object : TimerTask() {
                     override fun run() {
                         handler.post(Update)
                     }
                 }, 3000, 2000)

                 // Pager listener over indicator
                 indicator.setOnPageChangeListener(object : ViewPager.OnPageChangeListener {

                     override fun onPageSelected(position: Int) {
                         currentPage = position
                     }

                     override fun onPageScrolled(pos: Int, arg1: Float, arg2: Int) {

                     }

                     override fun onPageScrollStateChanged(pos: Int) {

                     }
                 })

             }
         })
    }

    /*
    //Function untuk menampilkan gambar tanpa recyclerviewer
    fun getLatestMovie(apiInterface: ApiInterface, apiKey : String) : Movie? {
        var movie : com.example.dwibonggopribadi.movie.Model.Movie? = null
        val call : retrofit2.Call <Movie> = apiInterface.getMovieLatest(apiKey)
        call.enqueue(object : retrofit2.Callback<Movie> {
            override fun onFailure(call: retrofit2.Call<Movie>?, t: Throwable?) {
                Log.d("$TAG", "Gagal Fetch Popular Movie")
            }

            override fun onResponse(call: retrofit2.Call<Movie>?, response: retrofit2.Response<Movie?>) {
                if (response != null) {
                    var originalTitle : String? = response.body()?.originalTitle
                    var posterPath : String? = response.body()?.posterPath

                    //Menampilak data di textview dan image
                    collapseToolbar.title = originalTitle
                    val imageUrl = StringBuilder()
                    imageUrl.append(getString(R.string.base_path_poster)).append(posterPath)
                    Glide.with(applicationContext).load(imageUrl.toString()).into(collapseImage)
                }
            }
        })
        return movie
    }
*/

    //Memanggil gambar di recyclerview
    fun getPopularMovies(apiInterface: ApiInterface, apiKey : String) {
        val call : retrofit2.Call <MovieResponse> = apiInterface.getPopularMovie(apiKey)
        call.enqueue( object : retrofit2.Callback<MovieResponse> {

            override fun onFailure(call: retrofit2.Call<MovieResponse>?, t: Throwable?) {
                Log.d("$TAG", "Gagal Fetch Popular Movie")
            }

            override fun onResponse(call: retrofit2.Call<MovieResponse>?, response: retrofit2.Response<MovieResponse>?) {
                movies =  response!!.body()!!.results
                Log.d("$TAG", "Movie size ${movies.size}")
                rvMovies.adapter = MovieAdapter(movies)
            }
        })
    }
}
