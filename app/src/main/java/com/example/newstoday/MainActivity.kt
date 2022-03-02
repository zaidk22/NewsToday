package com.example.newstoday

import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.SearchView
import androidx.appcompat.app.AppCompatActivity
import androidx.browser.customtabs.CustomTabsIntent
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*
import kotlin.collections.ArrayList


class MainActivity : AppCompatActivity() {

private lateinit var mAdapter:NewsAdapter
private lateinit var newArrayList:ArrayList<News>
private lateinit var tempArray:ArrayList<News>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

      /*  searchView.setOnQueryTextListener(object :SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {

            return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
               tempArray.clear()
                val searchText = newText!!.toLowerCase(Locale.getDefault())
                if (searchText.isNotEmpty()){

                    newArrayList.forEach {
                        if (it.articles.toLowerCase(Locale.getDefault()).contains(searchText)){

                            tempArray.add(it)
                        }
                    }
                    recyclerview.adapter!!.notifyDataSetChanged()
                }
                else{
                    tempArray.clear()
                    tempArray.addAll(newArrayList)
                    recyclerview.adapter!!.notifyDataSetChanged()
                }



                return false
            }

        })*/

        getNews()

    }


    private fun getNews() {
        val news = NewsService.newsInstance.getHeadlines("in",1)
        news.enqueue(object : Callback<News>{
            override fun onResponse(call: Call<News>, response: Response<News>) {

                val news = response.body()
                if (news!=null)
                {
                   mAdapter = NewsAdapter(this@MainActivity,news.articles)
                    recyclerview.adapter = mAdapter
                    recyclerview.layoutManager = LinearLayoutManager(this@MainActivity)
                    mAdapter.setItemClickListener(object :NewsAdapter.onItemClickedListener{
                        override fun onItemClicked(position: Int) {
                            val builder = CustomTabsIntent.Builder();
                            val   customTabsIntent = builder.build();
                            customTabsIntent.launchUrl(this@MainActivity, Uri.parse(news.articles[position].url));
                        }

                    })
                }
            }


            override fun onFailure(call: Call<News>, t: Throwable) {
            Log.e("msg","error")
            }
        })
    }


}