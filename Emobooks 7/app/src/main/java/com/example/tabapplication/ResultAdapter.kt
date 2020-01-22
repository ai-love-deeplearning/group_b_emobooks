package com.example.tabapplication


import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.tabapplication.data.Novel
import com.example.tabapplication.search.ResultActivity
import io.realm.OrderedRealmCollection
import io.realm.RealmRecyclerViewAdapter



class ResultAdapter(data: OrderedRealmCollection<Novel>) :
    RealmRecyclerViewAdapter<Novel, ResultAdapter.CustomViewHolder>(data, true) {

    init {
        setHasStableIds(true)
    }

    //表示するセルの管理
    class CustomViewHolder(cell: View) : RecyclerView.ViewHolder(cell) {
        val name: TextView = cell.findViewById(R.id.book_title)
        val author: TextView = cell.findViewById(R.id.book_author)
        val story: TextView = cell.findViewById(R.id.book_story)

        //GraphActivityへのデータ渡しと画面遷移
        init{
            cell.setOnClickListener {
                val intent = Intent(cell.context, DetailActivity::class.java)
                intent.putExtra(ResultActivity.EXTRA_TITLE, name.text)
                intent.putExtra(ResultActivity.EXTRA_AUTHOR, author.text)
                cell.context.startActivity(intent)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int):
            CustomViewHolder {
        val layoutInflater = LayoutInflater.from(parent?.context)
        val cellForRow = layoutInflater.inflate(R.layout.book_row, parent, false)
        return CustomViewHolder(cellForRow)
    }

    //book_rowに本のデータを入れる
    override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {
        val novel: Novel? = getItem(position)
        holder.name.text = novel?.name
        holder.author.text = novel?.author
        holder.story.text= novel?.story
    }

    //init{}と合わせて使うビューの高速化機能
    override fun getItemId(position: Int): Long{
        return getItem(position)?.id ?: 0
    }
}