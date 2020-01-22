package com.example.tabapplication.data

import io.realm.RealmList
import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

open class Novel : RealmObject() {
    @PrimaryKey
    var id : Long = 0

    var ncode : String = ""
    var name : String = ""
    var author : String = ""
    var genre : String = ""
    var bigGenre: String = ""
    var story : String = ""
    var numOfPart : Long? = null
    var longQuit : Boolean? = null
    var conversationRate : Int? = null
    var yorokobi : RealmList<Double> = RealmList()
    var ikari : RealmList<Double> = RealmList()
    var kanashimi : RealmList<Double> = RealmList()
    var tanoshimi : RealmList<Double> = RealmList()
    var vYorokobi : Double = 0.0
    var vIkari : Double = 0.0
    var vKanashimi : Double = 0.0
    var vTanoshimi : Double = 0.0
    var updateAt : String = ""

    //isFavoriteは 0, 1, 2 がそれぞれ null, like, dislikeに相当する
    var isFavorite : Int = 0
    var isRecommendation : Boolean = false
    var isModelRecommendation : Boolean = false
}