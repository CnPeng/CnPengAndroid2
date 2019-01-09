package com.cnpeng.android2.d_mine.a06_sound_wirte

import android.Manifest
import android.annotation.SuppressLint
import android.os.Bundle
import android.os.Environment
import com.cnpeng.android2.R
import com.cnpeng.android2.utils.BaseActivity
import com.iflytek.cloud.*
import com.iflytek.cloud.util.ResourceUtil
import com.tbruyelle.rxpermissions2.RxPermissions
import kotlinx.android.synthetic.main.activity_sound_write.*
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.error
import org.jetbrains.anko.toast
import org.json.JSONException
import org.json.JSONObject

/**
 * CnPeng 2019/1/8 2:16 PM
 * 功用：讯飞语音听写和识别
 * 说明：
 * - 相关API在该网址中：http://mscdoc.xfyun.cn/android/api/
 * - SDK集成文档：https://doc.xfyun.cn/msc_android/%E8%AF%AD%E9%9F%B3%E5%90%AC%E5%86%99.html
 */
class SoundWriteActivity : BaseActivity() {

    var mRecognizer: SpeechRecognizer? = null

    var mRecognizeResults: MutableMap<String, String> = mutableMapOf()
    var mEngineType = SpeechConstant.TYPE_CLOUD
    //    private val mEngineType = SpeechConstant.TYPE_LOCAL
    var mIsRecognizerSuccess = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sound_write)

        initRecognizer()
        initClickListener()
        tv_netInfo.text = "当前网络信息：\n网络是否可用——${mIsNetAvailable}\n当前网络类型名称${mNetTypeName}"
    }

    private fun initRecognizer() {
        mRecognizer = SpeechRecognizer.createRecognizer(this) { errorCode ->
            AnkoLogger("听写初始化").error { "SpeechRecognizer init() code = $errorCode" }

            if (errorCode != ErrorCode.SUCCESS) {
                toast("初始化失败，错误码：$errorCode");
            }
        }
    }

    private fun initClickListener() {
        tv_recordStart.setOnClickListener {
            checkPermission()
        }
    }

    @SuppressLint("CheckResult")
    private fun checkPermission() {
        val rxPermissions = RxPermissions(this)
        rxPermissions.request(Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.RECORD_AUDIO)
                .subscribe { granted ->
                    if (granted) {
                        startRecognizer()
                    } else {
                        toast("权限不足，无法享用相关功能")
                    }
                }
    }

    private fun startRecognizer() {
        if (null == mRecognizer) {
            return
        }


        tv_netInfo.text = "当前网络信息：\n网络是否可用——${mIsNetAvailable}\n当前网络类型名称${mNetTypeName}"

        mIsRecognizerSuccess = false

        mRecognizer!!.setParameter(SpeechConstant.PARAMS, null)

        if (!mIsNetAvailable) {
            // 设置本地识别资源——离线语音识别
            mRecognizer!!.setParameter(ResourceUtil.ASR_RES_PATH, getResourcePath());
            mEngineType = SpeechConstant.TYPE_LOCAL
        }

        //设置返回结果格式，目前支持json,xml以及plain 三种格式，其中plain为纯听写文本内容
        mRecognizer!!.setParameter(SpeechConstant.RESULT_TYPE, "json");
        //此处engineType为“cloud”——引擎类型，在线或离线
        mRecognizer!!.setParameter(SpeechConstant.ENGINE_TYPE, mEngineType);
        //设置语音输入语言，zh_cn为简体中文
        mRecognizer!!.setParameter(SpeechConstant.LANGUAGE, "zh_cn");
        //设置结果返回语言
        mRecognizer!!.setParameter(SpeechConstant.ACCENT, "mandarin");
        // 设置语音前端点:静音超时时间，单位ms，即用户多长时间不说话则当做超时处理
        //取值范围{1000～10000}
        mRecognizer!!.setParameter(SpeechConstant.VAD_BOS, "4000");
        //设置语音后端点:后端点静音检测时间，单位ms，即用户停止说话多长时间内即认为不再输入，
        //自动停止录音，范围{0~10000}
        mRecognizer!!.setParameter(SpeechConstant.VAD_EOS, "1000");
        //设置标点符号,设置为"0"返回结果无标点,设置为"1"返回结果有标点
        mRecognizer!!.setParameter(SpeechConstant.ASR_PTT, "1");

        // 设置音频保存路径，保存音频格式支持pcm、wav，设置路径为sd卡请注意WRITE_EXTERNAL_STORAGE权限
        mRecognizer!!.setParameter(SpeechConstant.AUDIO_FORMAT, "wav")
        mRecognizer!!.setParameter(SpeechConstant.ASR_AUDIO_PATH, Environment.getExternalStorageDirectory().toString() + "/msc/讯飞语音听写.wav")

        //开始识别，并设置监听器——这样其实就是开始录音了.并且获取是否识别成功的响应
        mIsRecognizerSuccess = ErrorCode.SUCCESS == mRecognizer!!.startListening(mRecogListener);

        if (!mIsRecognizerSuccess) {
            toast("语音识别失败，请重新打开页面再次尝试")
        }
    }

    /**
     * CnPeng 2019/1/8 7:44 PM
     * 功用：获取离线语音识别所使用的工具在本地的存储路径
     * 说明：如果路径不对或者没有这两个文件，会报错23002
     */
    private fun getResourcePath(): String? {
        val tempBuffer = StringBuffer()
        //识别通用资源：链接到放置 common.jet / sms_16k.jet 在assets中的路径
        tempBuffer.append(ResourceUtil.generateResourcePath(this, ResourceUtil.RESOURCE_TYPE.assets, "iat/common.jet"))
        tempBuffer.append(";")
        tempBuffer.append(ResourceUtil.generateResourcePath(this, ResourceUtil.RESOURCE_TYPE.assets, "iat/sms_16k.jet"))
        //识别8k资源-使用8k的时候请解开注释
        return tempBuffer.toString()
    }

    private val mRecogListener = object : RecognizerListener {
        /**
         * @param volume - 当前音量值，范围[0-30]
         * @param data - 录音数据，
         */
        override fun onVolumeChanged(p0: Int, p1: ByteArray?) {
            AnkoLogger("mRecogListener").error { "音量改变了" }
        }

        override fun onResult(result: RecognizerResult?, isLast: Boolean) {
            AnkoLogger("mRecogListener").error { "返回内容了——JSON 串：${result?.resultString}" }

            if (null != result) {
                //CnPeng 2019/1/8 5:06 PM 识别成功，就解析内容
                val text = JsonParser.parseIatResult(result.getResultString())

                var sn: String = ""
                // 读取json结果中的sn字段
                try {
                    val resultJson = JSONObject(result.getResultString())
                    sn = resultJson.optString("sn")
                } catch (e: JSONException) {
                    e.printStackTrace()
                }

                //CnPeng 2019/1/8 5:17 PM 对应的简写方式为：mRecognizeResults[sn] = text
                mRecognizeResults[sn] = text

                if (isLast) {
                    //CnPeng 2019/1/8 5:25 PM 输入完成之后，显示在界面
                    val resultBuffer = StringBuffer()
                    for (key in mRecognizeResults.keys) {
                        resultBuffer.append(mRecognizeResults[key])
                    }

                    tv_xfResult.setText(resultBuffer.toString())
                }
            }
        }

        override fun onBeginOfSpeech() {
            AnkoLogger("mRecogListener").error { "开始输入语音" }
            tv_xfResult.text = "正在输入..."

        }

        override fun onEvent(p0: Int, p1: Int, p2: Int, p3: Bundle?) {
            AnkoLogger("mRecogListener").error { "扩展事件" }
        }

        override fun onEndOfSpeech() {
            AnkoLogger("mRecogListener").error { "语音输入结束" }
            tv_xfResult.text = "输入完成，正在识别..."
        }

        override fun onError(error: SpeechError?) {
            AnkoLogger("mRecogListener").error { "发生错误——错误码：${error?.errorCode}, 错误MESSAGE：${error?.message}，错误描述：${error?.getPlainDescription(true)}" }
            tv_xfResult.text = "发生错误——错误码：${error?.errorCode},\n错误MESSAGE：${error?.message}，\n错误描述：${error?.getPlainDescription(true)}"

            if (null != error) {
                toast(error.getPlainDescription(true))
            }
        }
    }
}