package com.news_app.news_app

import android.content.ContentValues
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class FragmentA : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.fragment_a, container, false)
        val textArea = rootView.findViewById<TextView>(R.id.fragment_a_textview)
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
                        Log.d(ContentValues.TAG, it.sources[0].country)

                        textArea.text = it.sources[0].country
                    }
                }
            })
        // Inflate the layout for this fragment
        return rootView
    }
}