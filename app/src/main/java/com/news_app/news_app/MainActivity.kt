package com.news_app.news_app

import android.content.ContentValues
import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class MainActivity : AppCompatActivity() {
    private var currentFragment = true
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        supportFragmentManager.beginTransaction()
            .add(R.id.fragment_container, FragmentA())
            .commit()

        val retrofit = Retrofit.Builder()
            .baseUrl("https://newsapi.org")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        val newsService = retrofit.create(NewsService::class.java)
        newsService.getNews("4debf998c9b64761bcfbfe9ca7997ef5")
            .enqueue(object : Callback<NewsDto> {
                override fun onFailure(call: Call<NewsDto>, t: Throwable) {
                    Log.d(ContentValues.TAG, t.toString())
                }

                override fun onResponse(
                    call: Call<NewsDto>,
                    response: Response<NewsDto>
                ) {
                    if (response.isSuccessful.not()) {
                        return
                    }
                    response.body()?.let {
                        Log.d(ContentValues.TAG, it.toString())

                        it.sources.forEach { source ->
                            Log.d(ContentValues.TAG, source.name)
                        }
                    }
                }
            })

        findViewById<Button>(R.id.menuBtn1).setOnClickListener {
            var fragmentTransaction = supportFragmentManager.beginTransaction()
            fragmentTransaction.replace(R.id.fragment_container, FragmentA())
            fragmentTransaction.commit()
        }

        findViewById<Button>(R.id.menuBtn2).setOnClickListener {
            var fragmentTransaction = supportFragmentManager.beginTransaction()
            fragmentTransaction.replace(R.id.fragment_container, FragmentB())
            fragmentTransaction.commit()
        }


    }
}