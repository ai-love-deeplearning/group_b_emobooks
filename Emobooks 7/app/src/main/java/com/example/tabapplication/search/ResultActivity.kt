package com.example.tabapplication.search

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.tabapplication.R
import com.example.tabapplication.data.Novel
import com.example.tabapplication.initRealm
import com.example.tabapplication.ResultAdapter
import io.realm.Realm
import io.realm.kotlin.where
import kotlinx.android.synthetic.main.activity_result.*


class ResultActivity : AppCompatActivity() {

    //データ受け渡しのための定数
    companion object {
        const val EXTRA_TITLE = "com.example.emobooks.TITLE"
        const val EXTRA_AUTHOR = "com.example.emobooks.AUTHOR"
    }

    private lateinit var realm: Realm

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_result)

        //realmのinit（初期化）
        realm = initRealm()
        //検索文字列をsearchedTextに格納
        val searchedText = intent.getStringExtra(SearchFragment.SEARCH_TEXTDATA)!!

        //RecyclerViewのadapterの設定と画面表示
        recyclerView_result.layoutManager = LinearLayoutManager(this)
        val novels = realm.where<Novel>().contains("name", searchedText).findAll()
        val adapter = ResultAdapter(novels)
        recyclerView_result.adapter = adapter

        //SearchActivityからのデータの受け取り
        val searchedCount = novels.count()
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        if(searchedCount == 0){
            supportActionBar?.title = "作品が見つかりませんでした"
        }else{
            supportActionBar?.title = "$searchedText の検索結果 $searchedCount 件"
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        realm.close()
    }
}