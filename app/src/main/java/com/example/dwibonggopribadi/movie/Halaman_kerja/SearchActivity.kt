package com.example.dwibonggopribadi.movie.Halaman_kerja

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.SearchView
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import com.example.dwibonggopribadi.movie.Adapter.MovieAdapterSearch
import com.example.dwibonggopribadi.movie.Model.Movie
import com.example.dwibonggopribadi.movie.Model.MovieResponse
import com.example.dwibonggopribadi.movie.R
import com.example.dwibonggopribadi.movie.Services.ApiInterface
import com.example.dwibonggopribadi.movie.Services.ApliClient
import kotlinx.android.synthetic.main.activity_search.*
import retrofit2.Call

class SearchActivity : AppCompatActivity() {

    private lateinit var movies : ArrayList<Movie>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)

        //Setting Recyclerview
        setSupportActionBar(toolbarsearch)
        search_box.layoutManager = GridLayoutManager (applicationContext,1)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        val searchItem : MenuItem = menu.findItem(R.id.searchMenu)
        val searchView : SearchView= searchItem.actionView as SearchView
        searchQuery(searchView)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
         //   R.id.setting -> ""
        }
        return super.onOptionsItemSelected(item)
    }

    private fun searchQuery(searchView: SearchView) {
        var options : MutableMap<String, String> = mutableMapOf()
        options.put("api_key","c1b4ac7fcef6c6d96a6eedc9edf6fad8")
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {

            override fun onQueryTextSubmit(query: String?): Boolean {
                if (query.isNullOrEmpty()) {
                    return false
                }
                val apiInterface : ApiInterface = ApliClient.getClient()
                options.put("query",query!!.toString())
                val call : Call<MovieResponse> = apiInterface.searchMovies(options)
                getMoviedata(call)
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                if (newText.isNullOrEmpty()) {
                    return false
                }
                val apiInterface : ApiInterface = ApliClient.getClient()
                options.put("query",newText!!.toString())
                val call : Call<MovieResponse> = apiInterface.searchMovies(options)
                getMoviedata(call)
                return true
            }
        })
    }

    fun getMoviedata(call : Call<MovieResponse>) {
        val TAG : String?= SearchActivity::class.java.canonicalName
        call.enqueue( object : retrofit2.Callback<MovieResponse> {
            override fun onFailure(call: retrofit2.Call<MovieResponse>?, t: Throwable?) {
                Log.d("$TAG", "Gagal Fetch Popular Movie")
            }

            override fun onResponse(call: retrofit2.Call<MovieResponse>?, response: retrofit2.Response<MovieResponse>?) {
                movies =  response!!.body()!!.results
                Log.d("$TAG", "Movie size ${movies.size}")
                search_box.adapter= MovieAdapterSearch(movies)
            }
        })
    }

}
