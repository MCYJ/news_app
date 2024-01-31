package com.news_app.news_app

import android.content.ContentValues
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val listItems = ArrayList<Article>()

        val recyclerView: RecyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)

        val adapter = MyAdapter(listItems)
        recyclerView.adapter = adapter


        // retrofit HTTP request
        val retrofit = Retrofit.Builder()
            .baseUrl("https://newsapi.org")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        val newsService = retrofit.create(NewsService::class.java)
        newsService.getNews("4debf998c9b64761bcfbfe9ca7997ef5", "us")
            .enqueue(object : Callback<NewsDto> {
                override fun onFailure(call: Call<NewsDto>, t: Throwable) {
                    Log.d(ContentValues.TAG, t.toString())
                    Log.d("test1", "fail")
                }

                override fun onResponse(
                    call: Call<NewsDto>,
                    response: Response<NewsDto>
                ) {
                    if (response.isSuccessful.not()) {
                        Log.d("test2", response.code().toString())
                        return
                    }
                    response.body()?.let {
                        val adapter = MyAdapter(it.articles)
                        recyclerView.adapter = adapter
                        Log.d("test1",it.articles.toString())
                    }
                }
            })

    }

    // view holder
    class MyViewHolder(view: View): RecyclerView.ViewHolder(view) {
        var textCategory: TextView = itemView.findViewById<TextView>(R.id.category)
        var textCountry: TextView = itemView.findViewById<TextView>(R.id.country)
        var textDescription: TextView = itemView.findViewById<TextView>(R.id.description)
    }


    // adapter
    // param: source list를 받음
    class MyAdapter(var list: List<Article>) : RecyclerView.Adapter<MyViewHolder>() {
        override fun onCreateViewHolder(parent: ViewGroup,
                                        viewType: Int): MyViewHolder {
            val inflater = LayoutInflater.from(parent.context)
            val view = inflater.inflate(R.layout.item, parent, false)
            return MyViewHolder(view)
        }

        override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
            val article = list[position]
            Log.d("binding_test", article.toString())
            holder.textCountry.text = article.author
            holder.textCategory.text = article.title
            holder.textDescription.text = article.description+"\n"
//            val name = list[position]
//            holder.textView.text = name
        }

        override fun getItemCount(): Int {
            return list.size
        }
    }
}