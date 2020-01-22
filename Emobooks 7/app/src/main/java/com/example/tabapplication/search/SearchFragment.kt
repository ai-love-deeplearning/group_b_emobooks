package com.example.tabapplication.search

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.tabapplication.R
import kotlinx.android.synthetic.main.fragment_search.*


class SearchFragment : Fragment() {

    //データ受け渡しのための定数
    companion object {
        const val SEARCH_TEXTDATA = "com.example.emo_books_1.TEXTDATA"
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //検索ボタンが押されたときの処理
        //検索テキストが空であればエラーメッセージを表示
        //ResultActivityへのデータ渡しと画面遷移

        search_button.setOnClickListener {
            if (search_editText.text.toString() == "") {
                search_editText.error = "文字を入力してください"
            } else {
                val intent = Intent(view.context, ResultActivity::class.java)
                intent.putExtra(SEARCH_TEXTDATA, search_editText.text.toString())
                startActivity(intent)
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?

    ): View? {
        return inflater.inflate(R.layout.fragment_search, container, false)
    }
}