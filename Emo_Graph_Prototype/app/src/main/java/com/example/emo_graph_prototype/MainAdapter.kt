package com.example.emo_graph_prototype

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.book_row.view.*

class MainAdapter : RecyclerView.Adapter<CustomViewHolder>() {

    val bookTitles = listOf("First title", "second", "third")
    val bookAuthors = listOf("First author", "second author", "third author")
    val bookStorys = listOf("First story", "second story", "third story")

    private var mRecyclerView : RecyclerView? = null

    override fun getItemCount(): Int {
        return bookTitles.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewHolder {
        val layoutiInflater = LayoutInflater.from(parent?.context)
        val cellForRow = layoutiInflater.inflate(R.layout.book_row, parent, false)
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

    companion object{
        val BOOK_TITLE_KEY ="BOOK_TITLE"
    }
    init{
        view.setOnClickListener {
            val intent=Intent(view.context,DetailActivity::class.java)

            intent.putExtra(BOOK_TITLE_KEY,"a")

            view.context.startActivity(intent)
        }
    }
}