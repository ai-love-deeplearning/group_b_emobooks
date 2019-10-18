package com.example.emo_books_1

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_result.*

class GraphActivity: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_result)

        recyclerView_main.layoutManager= LinearLayoutManager(this)
        recyclerView_main.adapter= BookDetailAdapter()

       // val navBarTitle=intent.getStringExtra(CustomViewHolder.BOOK_TITLE_KEY)
       // supportActionBar?.title=navBarTitle
    }


    private class BookDetailAdapter: RecyclerView.Adapter<BookViewHolder>(){
        override fun onBindViewHolder(holder: BookViewHolder, position: Int) {
        }

        override fun getItemCount(): Int {
            return 1
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookViewHolder {

            val layoutInflater =LayoutInflater.from(parent?.context)
            val customView=layoutInflater.inflate(R.layout.graph_detail,parent,false)

            return BookViewHolder(customView)
        }
    }

    private class BookViewHolder(val customView:View):RecyclerView.ViewHolder(customView){

    }
}