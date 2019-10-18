package com.example.emo_books_1

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.book_row.view.*

class MainAdapter : RecyclerView.Adapter<CustomViewHolder>() {

    val bookTitles = listOf("嘘が見抜ける令嬢は愛人を脅し婚約者への復讐を決意する", "second", "third")
    val bookAuthors = listOf("稲井田そう", "second author", "third author")
    val bookStorys = listOf("嘘を吐いた人間が仮面をつけて見えるシルウィンは、王家から公爵家であり初恋の相手であるカーティスとの結婚を突如命じられる。式を挙げるまでの三ヶ月間、カーティスの屋敷で花嫁修業をすることになり、シルウィンは自らが住んでいた辺境の地から王都へ嬉々として向かうが、そこで待っていたのは自分に冷たい眼差しを向け、愛人に想いを向けるカーティスとの暮らしだった。シルウィンは物置のような部屋に閉じ込められ使用人たちからも虐げられるが、あることをきっかけにカーティスの愛人の嘘を見抜いたことでカーティスへの復讐を決め……。", "second story", "third story")

    private var mRecyclerView : RecyclerView? = null

    override fun getItemCount(): Int {
        return bookTitles.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewHolder {
        val layoutInflater = LayoutInflater.from(parent?.context)
        val cellForRow = layoutInflater.inflate(R.layout.book_row, parent, false)
        return CustomViewHolder(cellForRow)


    }

    override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {
        val bookTitle = bookTitles.get(position)
        holder.view.book_title.text= bookTitle
        val bookAuthor = bookAuthors.get(position)
        holder.view.book_author.text= bookAuthor
        val bookStory = bookStorys.get(position)
        holder.view.book_story.text= bookStory
    }

}

class CustomViewHolder(val view: View) : RecyclerView.ViewHolder(view) {

     // companion object{
     //   val BOOK_TITLE_KEY ="BOOK_TITLE"
    //}
    init{
        view.setOnClickListener {
            val intent=Intent(view.context,GraphActivity::class.java)

            //intent.putExtra(BOOK_TITLE_KEY,"a")

            view.context.startActivity(intent)
        }
    }
}