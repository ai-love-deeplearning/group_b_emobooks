package com.example.tabapplication.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.tabapplication.*
import com.example.tabapplication.data.Novel
import com.example.tabapplication.main.ModelActivity
import io.realm.Realm
import io.realm.kotlin.where
import kotlin.math.abs

class HomeFragment : Fragment() {
    private lateinit var realm: Realm


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        realm = initRealm()
        val rootView: View = inflater.inflate(R.layout.fragment_home, container, false)
        val recyclerViewMain: RecyclerView = rootView.findViewById(R.id.recyclerView_home)
        recyclerViewMain.layoutManager = LinearLayoutManager(activity)

        val modelActivity = ModelActivity()
        val inputDatas = createInputData()
        val output: ArrayList<FloatArray> = ArrayList()

        for(i in 0 until 20){
            val input = modelActivity.convertImputDataToByteBuffer(inputDatas[i])
            output.add(modelActivity.runModelInference2(input))
            Log.d("デバッグうううううううううう","${output[i].max()}")
        }


        //オススメ候補を0にする(初期化)
        val recommendNovels = realm.where<Novel>().equalTo("isRecommendation", true).findAll()
        for (novel in recommendNovels){
            realm.executeTransaction {
                novel.isRecommendation = false
            }
        }
        //「好き」と評価された作品があれば、オススメ候補を20作品挙げる
        val likeNovel = realm.where<Novel>().equalTo("isFavorite", 1L).findFirst()
        if(likeNovel != null) {
            val noChoiceNovels = realm.where<Novel>().equalTo("isFavorite", 0L).findAll()
            val keepIndexes = Array(20) { 0 }
            val keepSumDiff = Array(20) { 0.0 }
            val userVariance = userVariance()
            var sumDiff: Double
            var max: Double
            var indexOfMax: Int
            var preIndex: Int

            for (i in noChoiceNovels.indices) {
                sumDiff =
                    abs(noChoiceNovels[i]!!.vYorokobi - userVariance[0]) + abs(noChoiceNovels[i]!!.vIkari - userVariance[1]) +
                            abs(noChoiceNovels[i]!!.vKanashimi - userVariance[2]) + abs(
                        noChoiceNovels[i]!!.vTanoshimi - userVariance[3]
                    )
                if (i < 20) {
                    keepIndexes[i] = i
                    keepSumDiff[i] = sumDiff
                    realm.executeTransaction {
                        noChoiceNovels[i]!!.isRecommendation = true
                    }
                } else {
                    max = keepSumDiff.max()!!
                    if (max > sumDiff) {
                        realm.executeTransaction {
                            indexOfMax = keepSumDiff.indexOf(max)
                            keepSumDiff[indexOfMax] = sumDiff
                            preIndex = keepIndexes[indexOfMax]
                            keepIndexes[indexOfMax] = i
                            noChoiceNovels[i]!!.isRecommendation = true
                            noChoiceNovels[preIndex]!!.isRecommendation = false
                        }
                    }
                }
            }
        }
        //オススメ候補を検索し、リサイクラービューで表示
        val results = realm.where<Novel>().equalTo("isRecommendation", true).findAll()
        val adapter = ResultAdapter(results)
        recyclerViewMain.adapter = adapter
        return rootView
    }

    override fun onDestroy() {
        super.onDestroy()
        realm.close()
    }
}