package com.example.tabapplication

import android.graphics.Color
import android.graphics.Typeface
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.tabapplication.data.Novel
import com.example.tabapplication.search.ResultActivity
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.components.YAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet
import io.realm.Realm
import io.realm.kotlin.where
import kotlinx.android.synthetic.main.activity_detail.*

class DetailActivity: AppCompatActivity() {

    private lateinit var realm : Realm
    private var mTypeface = Typeface.create(Typeface.SANS_SERIF, Typeface.NORMAL)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        //realmの初期化
        realm = initRealm()
        val name = intent.getStringExtra(ResultActivity.EXTRA_TITLE)

        //ResultActivityからのデータの受け取り
        supportActionBar?.title = name
        graph_title.text = intent.getStringExtra(ResultActivity.EXTRA_TITLE)
        graph_author.text = intent.getStringExtra(ResultActivity.EXTRA_AUTHOR)

        //開いている作品のisFavoriteの値をboolに代入
        //ちなみに 0, 1, 2はそれぞれ null, like, dislikeに相当する
        var bool : Int? = realm.where<Novel>().equalTo("name", name).findFirst()?.isFavorite

        when(bool){
            0 -> {favorite_button.setBackgroundColor(Color.LTGRAY)
                  dislike_button.setBackgroundColor(Color.LTGRAY)}
            1 -> favorite_button.setBackgroundColor(Color.YELLOW)
            2 -> dislike_button.setBackgroundColor(Color.YELLOW)
        }

        //likeボタンが押されたら
        favorite_button.setOnClickListener {
            //null or dislikeならばlikeに登録。すでにlikeに登録されていたらnullにする
            if(bool != 1) {
                realm.executeTransaction { db: Realm ->
                    val novel = db.where<Novel>().equalTo("name", name).findFirst()
                    novel?.isFavorite = 1
                }
                Toast.makeText(this, "好きな本に登録されました", Toast.LENGTH_LONG).show()
                bool = 1
                favorite_button.setBackgroundColor(Color.YELLOW)
                dislike_button.setBackgroundColor(Color.LTGRAY)
            }else{
                realm.executeTransaction {db: Realm ->
                    val novel = db.where<Novel>().equalTo("name", name).findFirst()
                    novel?.isFavorite = 0
                }
                Toast.makeText(this, "好きな本の登録を解除しました", Toast.LENGTH_LONG).show()
                bool = 0
                favorite_button.setBackgroundColor(Color.LTGRAY)
            }
        }

        //dislikeボタンが押されたら
        dislike_button.setOnClickListener {
            //null or likeならばdislikeに登録。すでにdislikeに登録されていたらnullにする
            if(bool != 2) {
                realm.executeTransaction { db: Realm ->
                    val novel = db.where<Novel>().equalTo("name", name).findFirst()
                    novel?.isFavorite = 2
                }
                Toast.makeText(this, "嫌いな本に登録されました", Toast.LENGTH_LONG).show()
                bool = 2
                dislike_button.setBackgroundColor(Color.YELLOW)
                favorite_button.setBackgroundColor(Color.LTGRAY)
            }else{
                realm.executeTransaction{db: Realm ->
                    val novel = db.where<Novel>().equalTo("name", name).findFirst()
                    novel?.isFavorite = 0
                }
                Toast.makeText(this, "嫌いな本の登録を解除しました", Toast.LENGTH_LONG).show()
                bool = 0
                dislike_button.setBackgroundColor(Color.LTGRAY)
            }
        }

        setGraph()
        LineChart.data = setGraphData()
    }

    private fun setGraph() {
        LineChart.apply {
            description.isEnabled = false
            setTouchEnabled(true)
            isDragEnabled = true
            isScaleXEnabled = true
            setPinchZoom(false)
            setDrawGridBackground(false)

            legend.apply {
                form = Legend.LegendForm.LINE
                typeface = mTypeface
                textSize = 11f
                textColor = Color.BLACK
                verticalAlignment = Legend.LegendVerticalAlignment.BOTTOM
                horizontalAlignment = Legend.LegendHorizontalAlignment.LEFT
                orientation = Legend.LegendOrientation.HORIZONTAL
                setDrawInside(false)
            }

            axisRight.isEnabled = false /*apply {
                    typeface = mTypeface
                    setDrawLabels(false)
                    setDrawGridLines(true)
                }*/

            xAxis.apply {
                typeface = mTypeface
                setDrawLabels(true)
                setDrawGridLines(true)
                //axisMaximum = 100f
                //axisMinimum = 0f
            }

            axisLeft.apply {
                typeface = mTypeface
                textColor = Color.BLACK
                setDrawGridLines(true)
                axisMaximum = 1f
                axisMinimum = 0f
            }
        }
    }

    private fun setGraphData(): LineData {
        val bookY = mutableListOf<Entry>()
        val bookI = mutableListOf<Entry>()
        val bookK = mutableListOf<Entry>()
        val bookT = mutableListOf<Entry>()

        val title = intent.getStringExtra(ResultActivity.EXTRA_TITLE)
        val novel = realm.where<Novel>().equalTo("name", title).findFirst()!!

        for ((i, yorokobi)in novel.yorokobi.withIndex()) bookY.add(Entry(i.toFloat(), yorokobi.toFloat()))
        for ((i, ikari)in novel.ikari.withIndex()) bookI.add(Entry(i.toFloat(), ikari.toFloat()))
        for ((i, kanashimi)in novel.kanashimi.withIndex()) bookK.add(Entry(i.toFloat(), kanashimi.toFloat()))
        for ((i, tanoshimi)in novel.tanoshimi.withIndex()) bookT.add(Entry(i.toFloat(), tanoshimi.toFloat()))



        val yDataSet = LineDataSet(bookY, "喜び").apply {
            axisDependency =  YAxis.AxisDependency.LEFT
            color = Color.YELLOW
            highLightColor = Color.YELLOW
            setDrawCircles(false)
            setDrawCircleHole(false)
            setDrawValues(false)
            lineWidth = 2f
        }
        val iDataSet = LineDataSet(bookI, "怒り").apply {
            axisDependency =  YAxis.AxisDependency.LEFT
            color = Color.RED
            highLightColor = Color.RED
            setDrawCircles(false)
            setDrawCircleHole(false)
            setDrawValues(false)
            lineWidth = 2f
        }
        val kDataSet = LineDataSet(bookK, "悲しみ").apply {
            axisDependency =  YAxis.AxisDependency.LEFT
            color = Color.BLUE
            highLightColor = Color.BLUE
            setDrawCircles(false)
            setDrawCircleHole(false)
            setDrawValues(false)
            lineWidth = 2f
        }
        val tDataSet = LineDataSet(bookT, "楽しみ").apply {
            axisDependency =  YAxis.AxisDependency.LEFT
            color = Color.GREEN
            highLightColor = Color.GREEN
            setDrawCircles(false)
            setDrawCircleHole(false)
            setDrawValues(false)
            lineWidth = 2f
        }
        val datasets = ArrayList<ILineDataSet>()
        datasets.add(yDataSet)
        datasets.add(iDataSet)
        datasets.add(kDataSet)
        datasets.add(tDataSet)
        val lineData = LineData(datasets)
        lineData.setValueTextColor(Color.BLACK)
        lineData.setValueTextSize(9f)
        return lineData
    }

    override fun onDestroy() {
        super.onDestroy()
        realm.close()
    }
}


