package com.example.tabapplication

import com.example.tabapplication.data.Novel
import io.realm.OrderedRealmCollection
import io.realm.Realm
import io.realm.RealmConfiguration
import io.realm.kotlin.where
import kotlin.math.abs
import kotlin.math.pow

fun initRealm(): Realm {
    val realmConfiguration = RealmConfiguration.Builder()
        .deleteRealmIfMigrationNeeded()
        .build()
    return Realm.getInstance(realmConfiguration)
}

//データクラスの定義
data class ReturnData(
    val arrayY: Array<Double>,
    val arrayI: Array<Double>,
    val arrayK: Array<Double>,
    val arrayT: Array<Double>,
    val dataHitSize: Int
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as ReturnData

        if (!arrayY.contentEquals(other.arrayY)) return false
        if (!arrayI.contentEquals(other.arrayI)) return false
        if (!arrayK.contentEquals(other.arrayK)) return false
        if (!arrayT.contentEquals(other.arrayT)) return false
        if (dataHitSize != other.dataHitSize) return false

        return true
    }

    override fun hashCode(): Int {
        var result = arrayY.contentHashCode()
        result = 31 * result + arrayI.contentHashCode()
        result = 31 * result + arrayK.contentHashCode()
        result = 31 * result + arrayT.contentHashCode()
        result = 31 * result + dataHitSize
        return result
    }
}

private lateinit var realm: Realm
//喜怒哀楽の平均配列データを配列で返す(二重配列)
fun userGraphMean(): ReturnData {
    realm = initRealm()
    val likeNovels = realm.where<Novel>().equalTo("isFavorite", 1L).findAll()

    //配列の初期化
    val meanY = Array(10) { 0.0 }
    val meanI = Array(10) { 0.0 }
    val meanK = Array(10) { 0.0 }
    val meanT = Array(10) { 0.0 }

    return when (likeNovels.isEmpty()) {
        true -> {
            realm.close()
            ReturnData(meanY, meanI, meanK, meanT, likeNovels.size)
        }
        false -> {
            for (likeNovel in likeNovels) {
                for (i in likeNovel.yorokobi.indices) {
                    meanY[i] += likeNovel.yorokobi[i]!! / likeNovels.size
                    meanI[i] += likeNovel.ikari[i]!! / likeNovels.size
                    meanK[i] += likeNovel.kanashimi[i]!! / likeNovels.size
                    meanT[i] += likeNovel.tanoshimi[i]!! / likeNovels.size
                }
            }
            realm.close()
            ReturnData(meanY, meanI, meanK, meanT, likeNovels.size)
        }
    }
}

//ユーザの喜怒哀楽それぞれの分散を配列で返す関数
fun userVariance(): List<Double> {
    var varianceY = 0.0
    var varianceI = 0.0
    var varianceK = 0.0
    var varianceT = 0.0
    val userMean = userGraphMean()
    val dataSize = userMean.arrayY.size
    //分散を計算する
    return when (userMean.dataHitSize) {
        0 -> listOf(varianceY, varianceI, varianceK, varianceT)
        else -> {
            for (i in userMean.arrayY.indices) {
                varianceY += ((userMean.arrayY[i] - 0.5).pow(2.0)) / dataSize
                varianceI += ((userMean.arrayI[i] - 0.5).pow(2.0)) / dataSize
                varianceK += ((userMean.arrayK[i] - 0.5).pow(2.0)) / dataSize
                varianceT += ((userMean.arrayT[i] - 0.5).pow(2.0)) / dataSize
            }
            listOf(varianceY, varianceI, varianceK, varianceT)
        }
    }
}

//計算量膨大注意　ユーザの分散に近いものを20個返す
fun recommendNovel() : OrderedRealmCollection<Novel> {
    realm = initRealm()
    val likeNovels = realm.where<Novel>().equalTo("isFavorite", 1L).findAll()
    if(likeNovels.count() == 0){
        realm.close()
        return likeNovels
    }else {
        val noChoiceNovels = realm.where<Novel>().equalTo("isFavorite", 0L).findAll()
        val keepIndexes = Array(20){0}
        val keepSumDiff = Array(20){0.0}
        val userVariance = userVariance()
        var sumDiff : Double
        var max : Double
        var indexOfMax : Int
        var preIndex : Int

        for (i in noChoiceNovels.indices) {
            sumDiff =
                abs(noChoiceNovels[i]!!.vYorokobi - userVariance[0]) + abs(noChoiceNovels[i]!!.vIkari - userVariance[1]) +
                        abs(noChoiceNovels[i]!!.vKanashimi - userVariance[2]) + abs(noChoiceNovels[i]!!.vTanoshimi - userVariance[3])
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
        val results = realm.where<Novel>().equalTo("isRecommendation", true).findAll()
        realm.close()
        return results
    }
}

fun createInputData(): Array<Array<Array<Array<Array<Double?>>>>>{
    val recommendNovels = recommendNovel()
    val userMean = userGraphMean()
    val array = Array(20){Array(1){Array(4){Array(10){Array<Double?>(2){0.0} } } }}

    for((i, recommendNovel) in recommendNovels.withIndex()){
        for(j in array[0][0][0].indices){
            when(j) {
                0 -> {for (k in array[0][0][0][0].indices) {
                      array[i][0][j][k][0] = userMean.arrayY[k]
                      array[i][0][j][k][1] = recommendNovel.yorokobi[k]
                      }}
                1 -> {for (k in array[0][0][0][0].indices) {
                      array[i][0][j][k][0] = userMean.arrayI[k]
                      array[i][0][j][k][1] = recommendNovel.ikari[k]
                      }}
                2 -> {for (k in array[0][0][0][0].indices) {
                      array[i][0][j][k][0] = userMean.arrayK[k]
                      array[i][0][j][k][1] = recommendNovel.kanashimi[k]
                      }}
                3 -> {for (k in array[0][0][0][0].indices) {
                      array[i][0][j][k][0] = userMean.arrayT[k]
                      array[i][0][j][k][1] = recommendNovel.tanoshimi[k]
                      }}
            }
        }
    }
    return array
}


