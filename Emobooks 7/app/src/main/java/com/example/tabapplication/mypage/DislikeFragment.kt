package com.example.tabapplication.mypage

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.tabapplication.R
import com.example.tabapplication.data.Novel
import com.example.tabapplication.initRealm
import com.example.tabapplication.ResultAdapter
import io.realm.Realm
import io.realm.kotlin.where

class DislikeFragment : Fragment() {
    private lateinit var realm: Realm

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        //realmのinit（初期化）
        realm = initRealm()
        val rootView: View = inflater.inflate(R.layout.fragment_mypage_dislike, container, false)
        val recyclerViewMain: RecyclerView = rootView.findViewById(R.id.recyclerView_dislike)
        recyclerViewMain.layoutManager = LinearLayoutManager(activity)
        val novels = realm.where<Novel>().equalTo("isFavorite", 2L).findAll()
        val adapter = ResultAdapter(novels)
        recyclerViewMain.adapter = adapter

        return rootView
    }

    override fun onDestroy() {
        super.onDestroy()
        realm.close()
    }
}