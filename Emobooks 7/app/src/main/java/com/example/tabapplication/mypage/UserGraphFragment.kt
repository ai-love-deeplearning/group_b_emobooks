package com.example.tabapplication.mypage

import android.graphics.Color
import android.graphics.Typeface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.tabapplication.R
import com.example.tabapplication.userGraphMean
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.components.YAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet
import kotlinx.android.synthetic.main.fragment_my_page_graph.*


class UserGraphFragment : Fragment(){

    private var mTypeface = Typeface.create(Typeface.SANS_SERIF, Typeface.NORMAL)

    private val chartDataCount = 10

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment

        return inflater.inflate(R.layout.fragment_my_page_graph, container, false)

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        setuserGraph()
        userChart.data = setuserGraphData(chartDataCount)
    }

    private fun setuserGraph() {
        userChart.apply {
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

            axisRight.isEnabled = false

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

    private fun setuserGraphData(count: Int): LineData {
        val userY = mutableListOf<Entry>()
        val userI = mutableListOf<Entry>()
        val userK = mutableListOf<Entry>()
        val userT = mutableListOf<Entry>()

        val userDatas = userGraphMean()
        val arrayY = userDatas.arrayY
        val arrayI = userDatas.arrayI
        val arrayK = userDatas.arrayK
        val arrayT = userDatas.arrayT

        for(i in 0 until count) {
            userY.add(Entry(i.toFloat(), arrayY[i].toFloat()))
            userI.add(Entry(i.toFloat(), arrayI[i].toFloat()))
            userK.add(Entry(i.toFloat(), arrayK[i].toFloat()))
            userT.add(Entry(i.toFloat(), arrayT[i].toFloat()))
        }

        val useryDataSet = LineDataSet(userY, "喜び").apply {
            axisDependency =  YAxis.AxisDependency.LEFT
            color = Color.YELLOW
            highLightColor = Color.YELLOW
            setDrawCircles(false)
            setDrawCircleHole(false)
            setDrawValues(false)
            lineWidth = 2f
        }
        val useriDataSet = LineDataSet(userI, "怒り").apply {
            axisDependency =  YAxis.AxisDependency.LEFT
            color = Color.RED
            highLightColor = Color.RED
            setDrawCircles(false)
            setDrawCircleHole(false)
            setDrawValues(false)
            lineWidth = 2f
        }
        val userkDataSet = LineDataSet(userK, "悲しみ").apply {
            axisDependency =  YAxis.AxisDependency.LEFT
            color = Color.BLUE
            highLightColor = Color.BLUE
            setDrawCircles(false)
            setDrawCircleHole(false)
            setDrawValues(false)
            lineWidth = 2f
        }
        val usertDataSet = LineDataSet(userT, "楽しみ").apply {
            axisDependency =  YAxis.AxisDependency.LEFT
            color = Color.GREEN
            highLightColor = Color.GREEN
            setDrawCircles(false)
            setDrawCircleHole(false)
            setDrawValues(false)
            lineWidth = 2f
        }
        val dataSets = ArrayList<ILineDataSet>()
        dataSets.add(useryDataSet)
        dataSets.add(useriDataSet)
        dataSets.add(userkDataSet)
        dataSets.add(usertDataSet)
        val lineData = LineData(dataSets)
        lineData.setValueTextColor(Color.BLACK)
        lineData.setValueTextSize(9f)
        return lineData
    }
}