package com.cnpeng.android2.d_mine.a06_sound_wirte

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.cnpeng.android2.R
import com.cnpeng.android2.utils.TripleLibInitUtils
import com.iflytek.cloud.*
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.error
import org.jetbrains.anko.toast

/**
 * CnPeng 2019/1/8 2:16 PM
 * 功用：讯飞语音听写和识别
 * 说明：
 * - 相关API在该网址中：http://mscdoc.xfyun.cn/android/api/
 * - SDK集成文档：https://doc.xfyun.cn/msc_android/%E8%AF%AD%E9%9F%B3%E5%90%AC%E5%86%99.html
 */
class SoundWriteActivity : AppCompatActivity() {


    private val mEngineType = SpeechConstant.TYPE_CLOUD

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sound_write)

        initRecognizer()
    }

    private fun initRecognizer() {

        var mRecognizer = SpeechRecognizer.createRecognizer(this, mInitListener);

        toast(if (null == mRecognizer) {
            "null"
        } else {
            mRecognizer.toString()
        })

        if (null == mRecognizer) {
            return
        }

        //设置返回结果格式，目前支持json,xml以及plain 三种格式，其中plain为纯听写文本内容
        mRecognizer.setParameter(SpeechConstant.RESULT_TYPE, "json");
        //此处engineType为“cloud”——引擎类型，在线或离线
        mRecognizer.setParameter(SpeechConstant.ENGINE_TYPE, mEngineType);
        //设置语音输入语言，zh_cn为简体中文
        mRecognizer.setParameter(SpeechConstant.LANGUAGE, "zh_cn");
        //设置结果返回语言
        mRecognizer.setParameter(SpeechConstant.ACCENT, "mandarin");
        // 设置语音前端点:静音超时时间，单位ms，即用户多长时间不说话则当做超时处理
        //取值范围{1000～10000}
        mRecognizer.setParameter(SpeechConstant.VAD_BOS, "4000");
        //设置语音后端点:后端点静音检测时间，单位ms，即用户停止说话多长时间内即认为不再输入，
        //自动停止录音，范围{0~10000}
        mRecognizer.setParameter(SpeechConstant.VAD_EOS, "1000");
        //设置标点符号,设置为"0"返回结果无标点,设置为"1"返回结果有标点
        mRecognizer.setParameter(SpeechConstant.ASR_PTT, "1");

        //开始识别，并设置监听器
        mRecognizer.startListening(mRecogListener);
    }

    /**
     * 初始化监听器。
     */
    private val mInitListener = InitListener {
        AnkoLogger("听写初始化").error { "SpeechRecognizer init() code = " + it };
        if (it != ErrorCode.SUCCESS) {
            toast("初始化失败，错误码：" + it);
        }
    };

    private val mRecogListener = object : RecognizerListener {
        /**
         * @param volume - 当前音量值，范围[0-30]
         * @param data - 录音数据，
         */
        override fun onVolumeChanged(p0: Int, p1: ByteArray?) {
            toast("音量改变了")
            AnkoLogger("mRecogListener").error { "音量改变了" }
        }

        override fun onResult(result: RecognizerResult?, p1: Boolean) {
            toast("返回内容了：${result?.resultString}")
            AnkoLogger("mRecogListener").error { "返回内容了：${result?.resultString}" }

        }

        override fun onBeginOfSpeech() {
            toast("开始输入语音")
            AnkoLogger("mRecogListener").error { "开始输入语音" }

        }

        override fun onEvent(p0: Int, p1: Int, p2: Int, p3: Bundle?) {
            toast("扩展事件？")
            AnkoLogger("mRecogListener").error { "扩展事件" }
        }

        override fun onEndOfSpeech() {
            toast("语音输入结束")
            AnkoLogger("mRecogListener").error { "语音输入结束" }
        }

        override fun onError(p0: SpeechError?) {
            toast("发生错误了")
            AnkoLogger("mRecogListener").error { "发生错误了" }
        }
    }
}