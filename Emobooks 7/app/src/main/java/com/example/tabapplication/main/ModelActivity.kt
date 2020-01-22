package com.example.tabapplication.main


import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.tabapplication.createInputData
import com.google.android.gms.tasks.OnFailureListener
import com.google.firebase.ml.common.FirebaseMLException
import com.google.firebase.ml.common.modeldownload.FirebaseLocalModel
import com.google.firebase.ml.common.modeldownload.FirebaseModelManager
import com.google.firebase.ml.custom.*
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader
import java.nio.ByteBuffer
import java.nio.ByteOrder


class ModelActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        modelInterpreter = createLocalModelInterpreter()
    }

    /** assetsからlocal modelに使用されるFirebase model interpreter*/
    private lateinit var modelInterpreter: FirebaseModelInterpreter

    /** ローカルモデルの指定、モデルの登録、推論モデルの指定
     * assetsファイルからlocal model interpreterを初期化する*/
    private fun createLocalModelInterpreter(): FirebaseModelInterpreter {
        // ローカルモデルとして使用可能な最初の.tfliteファイルを選択
        /*val localModelName = resources.assets.list("")?.firstOrNull { it.endsWith(".tflite") }
            ?: throw(RuntimeException("Don't forget to add the tflite file to your assets folder"))*/
        val localModelName = "model#2.tflite"
        Log.d(TAG, "Local model found: $localModelName")

        // local model asset でインタープリターを作成する
        val localModel = FirebaseCustomLocalModel.Builder().setAssetFilePath(localModelName).build()
        val localInterpreter =
            FirebaseModelInterpreter.getInstance(FirebaseModelInterpreterOptions.Builder(localModel).build())!!
        Log.d(TAG, "Local model interpreter initialized")

        return localInterpreter
    }

    /** 入出力のデータを指定する */
    private val modelInputOutputOptions: FirebaseModelInputOutputOptions by lazy {
        FirebaseModelInputOutputOptions.Builder()
            .setInputFormat(0, FirebaseModelDataType.FLOAT32, intArrayOf(1, 4, 10, 2))
            .setOutputFormat(0, FirebaseModelDataType.FLOAT32, intArrayOf(1, 2))
            .build()
    }

    /** ByteBufferに書き込む*/
    @Synchronized
    fun convertImputDataToByteBuffer(array: Array<Array<Array<Array<Double?>>>>): ByteBuffer {
        val imputData = ByteBuffer.allocateDirect(1 * 4 * 10 * 2 * 4)
            .apply {
            order(ByteOrder.nativeOrder())
            rewind()
        }

        for (j in 0 until 4) {
            for (k in 0 until 10) {
                for (l in 0 until 2) {
                    val amount = array[0][j][k][l]!!
                    imputData.put(amount.toByte())
                }
            }
        }
        return imputData
    }

    /** modelへの出力 */
    fun runModelInference2(byteInput: ByteBuffer): FloatArray{
        //val imputDatas = createInputData()
        val firebaseInterpreter = createLocalModelInterpreter()!!
        val inputOutputOptions = modelInputOutputOptions
        //val input = convertImputDataToByteBuffer(imputDatas[0])

        val inputs = FirebaseModelInputs.Builder().add(byteInput)
            .build() // add() as many input arrays as your model requires

        var probabilities = FloatArray(2)
        firebaseInterpreter.run(inputs, inputOutputOptions)
            .addOnSuccessListener { result ->
                val output = result.getOutput<Array<FloatArray>>(0)
                probabilities = output[0]
                //Log.d("モデルからの出力" , "${output[0].size}")
            }
            .addOnFailureListener(
                object : OnFailureListener {
                    override fun onFailure(e: Exception) {
                        Log.d("例外発生", "$e")
                    }
                }
            )
        return probabilities
        // [END mlkit_run_inference]
        // }
    }
    companion object {
        private val TAG = MainActivity::class.java.simpleName
    }
}

//    /** 6.モデル推論出力を解釈する
//     * モデルを使用して予測を行い出力を適切なラベルに解釈する */
//    fun runModelInference() = selectedImage?.let { image ->
//
//        // 入力データの作成
//        val imputData = convertImputDataToByteBuffer(image)
//
//        try {
//            // 画像データからモデルの入力を作成する
//            val modelInputs = FirebaseModelInputs.Builder().add(imputData).build()
//
//            // モデルインタープリターを使用して推論を実行します。
//            modelInterpreter.run(modelInputs, modelInputOutputOptions).continueWith {
//                val inferenceOutput = it.result?.getOutput<Array<ByteArray>>(0)!!
//
//                // オーバーレイを使用して画面にラベルを表示する
//                val topLabels = getTopLabels(inferenceOutput)
//                graphic_overlay.clear()
//                graphic_overlay.add(LabelGraphic(graphic_overlay, topLabels))
//                topLabels
//            }
//
//        } catch (exc: FirebaseMLException) {
//            val msg = "Error running model inference"
//            Toast.makeText(baseContext, msg, Toast.LENGTH_SHORT).show()
//            Log.e(TAG, msg, exc)
//        }
//    }

//    fun runModelInference() {
//
//        // 入力データの作成
//        val inputData = convertImputDataToByteBuffer(createInputData())
//
//        try {
//            // 画像データからモデルの入力を作成する
//            val modelInputs = FirebaseModelInputs.Builder().add(inputData).build()
//
//            // モデルインタープリターを使用して推論を実行します。
//            modelInterpreter.run(modelInputs, modelInputOutputOptions).continueWith {
//                val inferenceOutput = it.result?.getOutput<Array<ByteArray>>(0)!!
//
//                // オーバーレイを使用して画面にラベルを表示する
//                val topLabels = getTopLabels(inferenceOutput)
//                graphic_overlay.clear()
//                graphic_overlay.add(LabelGraphic(graphic_overlay, topLabels))
//                topLabels
//            }
//
//        } catch (exc: FirebaseMLException) {
//            val msg = "Error running model inference"
//            Toast.makeText(baseContext, msg, Toast.LENGTH_SHORT).show()
//            Log.e(TAG, msg, exc)
//        }
//    }
// override fun onNothingSelected(parent: AdapterView<*>) = Unit



