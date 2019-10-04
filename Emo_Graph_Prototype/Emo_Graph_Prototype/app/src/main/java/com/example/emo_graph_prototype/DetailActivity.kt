package com.example.emo_graph_prototype

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.emo_graph_prototype.R
import kotlinx.android.synthetic.main.activity_main.*

class DetailActivity: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_resultproto)

        recyclerView_resultproto.layoutManager= LinearLayoutManager(this)
        recyclerView_resultproto.adapter= BookDetailAdapter()

        val navBarTitle=intent.getStringExtra(CustomViewHolder.BOOK_TITLE_KEY)
        supportActionBar?.title=navBarTitle
    }


    private class BookDetailAdapter: RecyclerView.Adapter<BookViewHoldeer>(){
        override fun onBindViewHolder(holder: BookViewHoldeer, position: Int) {
        }

        override fun getItemCount(): Int {
            return 1
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookViewHoldeer {

            val layoutInflater =LayoutInflater.from(parent?.context)
            val customView=layoutInflater.inflate(R.layout.book_detail,parent,false)

           // val blueView= View(parent?.context)
           // blueView.setBackgroundColor(Color.BLUE)
           // blueView.minimumHeight=50
            return BookViewHoldeer(customView)
        }
    }

    private class BookViewHoldeer(val customView:View):RecyclerView.ViewHolder(customView){

    }


}