package com.example.tabapplication.main

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.AppLaunchChecker
import com.example.tabapplication.R
import com.example.tabapplication.data.*
import com.example.tabapplication.initRealm
import io.realm.Realm
import io.realm.kotlin.createObject
import io.realm.kotlin.where

class SplashActivity : AppCompatActivity() {

    private lateinit var realm : Realm
    private val handler = Handler()
    private val runnable = Runnable {

        // MainActivityへ遷移させる
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)

        // ここでfinish()を呼ばないとMainActivityでAndroidの戻るボタンを押すとスプラッシュ画面に戻ってしまう
        finish()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        //インストール後初回起動かどうかを判定し、プログラムを実行する
        if(AppLaunchChecker.hasStartedFromLauncher(applicationContext)){
            Log.d("AppLaunchChecker","2回目以降")
            realm = initRealm()
            realm.executeTransaction { db: Realm ->
                val novels = db.where<Novel>().findAll()
                for (i in novels.indices){
                    novels[i]!!.isRecommendation = false
                }
            }
        } else {
            Log.d("AppLaunchChecker","はじめてアプリを起動した")
            //realmの初期化
            realm = initRealm()
            realm.executeTransaction{db: Realm ->
                db.where<Novel>().findAll()
                    ?.deleteAllFromRealm()
            }
            for (i in authorD.indices) {
                realm.executeTransaction { db: Realm ->
                    val maxId = db.where<Novel>().max("id")
                    val nextId = (maxId?.toLong() ?: 0L) + 1
                    //idの最大値に+1した値を主キーとして新しいレコードを作成
                    val novel: Novel = db.createObject(nextId)
                    novel.name = nameD[i]
                    novel.author = authorD[i]
                    //novel.story = storyD[i]
                    yorokobiD[i].split(",").map { novel.yorokobi.add(it.toDouble()) }
                    ikariD[i].split(",").map { novel.ikari.add(it.toDouble()) }
                    kanashimiD[i].split(",").map { novel.kanashimi.add(it.toDouble()) }
                    tanoshimiD[i].split(",").map { novel.tanoshimi.add(it.toDouble()) }
                    novel.vYorokobi = vYorokobiD[i]
                    novel.vIkari = vIkariD[i]
                    novel.vKanashimi = vKanashimiD[i]
                    novel.vTanoshimi = vTanoshimiD[i]
                }
            }
        }
        AppLaunchChecker.onActivityCreate(this)

        supportActionBar!!.hide()

        // スプラッシュ表示1000ms(1秒)後にrunnableを呼んでMainActivityへ遷移させる
        handler.postDelayed(runnable, 1000)
    }

    override fun onStop() {
        super.onStop()
        // スプラッシュ画面中にアプリを落とした時にはrunnableが呼ばれないようにする
        // これがないとアプリを消した後にまた表示される
        handler.removeCallbacks(runnable)
    }
}
