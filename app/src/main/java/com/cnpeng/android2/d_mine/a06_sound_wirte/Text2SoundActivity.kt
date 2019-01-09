package com.cnpeng.android2.d_mine.a06_sound_wirte

import android.graphics.Color
import android.os.Bundle
import android.os.Environment
import android.text.Spannable
import android.text.SpannableStringBuilder
import android.text.style.BackgroundColorSpan
import android.view.View
import com.cnpeng.android2.R
import com.cnpeng.android2.utils.BaseActivity
import com.iflytek.cloud.*
import kotlinx.android.synthetic.main.activity_text2_sound.*
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.error
import org.jetbrains.anko.toast

/**
 * CnPeng 2019/1/9 11:47 AM
 * 功用：讯飞语音——文字转语音
 * 说明：
 */
class Text2SoundActivity : BaseActivity(), View.OnClickListener {
    var mText2Convert = "这是要转换的文字哈，哈哈哈哈哈哈哈哈哈哈哈"
    var mSpeechSynthesizer: SpeechSynthesizer? = null
    var mEngineType = SpeechConstant.TYPE_CLOUD
    var mVoicer = "xiaoyan"
    var mBufferPercent = 0
    var mSpeakPercent = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_text2_sound)

        initSpeechSynthesize()
        initClickEvent()
    }

    private fun initSpeechSynthesize() {
        mSpeechSynthesizer = SpeechSynthesizer.createSynthesizer(this) { code ->
            if (ErrorCode.SUCCESS != code) {
                toast("SpeechSynthesizer初始化失败，错误码为$code")
            } else {
                //CnPeng 2019/1/9 2:35 PM 必须在确认初始化成功之后才执行startSpeaking
            }
        }
    }

    private fun initClickEvent() {
        tv_start.setOnClickListener(this)
        tv_pause.setOnClickListener(this)
        tv_restart.setOnClickListener(this)
        tv_cancel.setOnClickListener(this)

    }

    override fun onClick(view: View?) {
        if (null == view || null == mSpeechSynthesizer) {
            return
        }

        val viewID = view.id
        when (viewID) {
            R.id.tv_start -> startSynthesize()
            R.id.tv_pause -> mSpeechSynthesizer!!.pauseSpeaking()
            R.id.tv_restart -> mSpeechSynthesizer!!.resumeSpeaking()
            R.id.tv_cancel -> mSpeechSynthesizer!!.stopSpeaking()

            else -> toast("暂未实现")
        }
    }

    /**
     * CnPeng 2019/1/9 2:28 PM
     * 功用：开始转换
     * 说明：
     */
    private fun startSynthesize() {
        mText2Convert = if (!et_input.text.toString().isEmpty()) {
            et_input.text.toString()
        } else {
            mText2Convert
        }

        setSynthesizeParam()

        val startResponseCode = mSpeechSynthesizer!!.startSpeaking(mText2Convert, mSynthesizerListener)

        if (ErrorCode.SUCCESS != startResponseCode) {
            toast("转换失败，错误码为：$startResponseCode")
        }
    }

    private fun setSynthesizeParam() {
        if (null == mSpeechSynthesizer)
            return

        // 清空参数
        mSpeechSynthesizer!!.setParameter(SpeechConstant.PARAMS, null)
        // 根据合成引擎设置相应参数
        //        if (mEngineType == SpeechConstant.TYPE_CLOUD) {
        mSpeechSynthesizer!!.setParameter(SpeechConstant.ENGINE_TYPE, SpeechConstant.TYPE_CLOUD)
        mSpeechSynthesizer!!.setParameter(SpeechConstant.TTS_DATA_NOTIFY, "1")
        // 设置在线合成发音人
        mSpeechSynthesizer!!.setParameter(SpeechConstant.VOICE_NAME, mVoicer)
        //设置合成语速
        mSpeechSynthesizer!!.setParameter(SpeechConstant.SPEED, "50")
        //设置合成音调
        mSpeechSynthesizer!!.setParameter(SpeechConstant.PITCH, "50")
        //设置合成音量
        mSpeechSynthesizer!!.setParameter(SpeechConstant.VOLUME, "50")
        //        } else {
        //            mSpeechSynthesizer!!.setParameter(SpeechConstant.ENGINE_TYPE, SpeechConstant.TYPE_LOCAL)
        //            mSpeechSynthesizer!!.setParameter(SpeechConstant.VOICE_NAME, "")
        //        }

        //设置播放器音频流类型
        mSpeechSynthesizer!!.setParameter(SpeechConstant.STREAM_TYPE, "3")
        // 设置播放合成音频打断音乐播放，默认为true
        mSpeechSynthesizer!!.setParameter(SpeechConstant.KEY_REQUEST_FOCUS, "true")

        // 设置音频保存路径，保存音频格式支持pcm、wav，设置路径为sd卡请注意WRITE_EXTERNAL_STORAGE权限
        mSpeechSynthesizer!!.setParameter(SpeechConstant.AUDIO_FORMAT, "pcm")
        mSpeechSynthesizer!!.setParameter(SpeechConstant.TTS_AUDIO_PATH, Environment.getExternalStorageDirectory().toString() + "/msc/tts.pcm")
    }

    private val mSynthesizerListener = object : SynthesizerListener {
        override fun onBufferProgress(percent: Int, begainPos: Int, endPos: Int, strInfo: String?) {
            mBufferPercent = percent
            tv_percent.text = "当前合成进度：$mBufferPercent,当前播放进度:$mSpeakPercent"
        }

        override fun onSpeakBegin() {
            tv_speechStatus.text = "当前播放状态：开始播放"
        }

        override fun onSpeakProgress(percent: Int, begainPos: Int, endPos: Int) {
            mSpeakPercent = percent
            tv_percent.text = "当前合成进度：$mBufferPercent,当前播放进度:$mSpeakPercent"

            //CnPeng 2019/1/9 3:18 PM 改变当前正在播放内容的背景色
            val spannableStringBuilder = SpannableStringBuilder(mText2Convert)
            spannableStringBuilder.setSpan(BackgroundColorSpan(Color.LTGRAY), begainPos, endPos, Spannable.SPAN_EXCLUSIVE_INCLUSIVE)
            et_input.text = spannableStringBuilder
        }

        override fun onEvent(eventType: Int, p1: Int, p2: Int, obj: Bundle?) {
            if (SpeechEvent.EVENT_TTS_BUFFER == eventType) {
                val buf = obj?.getByteArray(SpeechEvent.KEY_EVENT_TTS_BUFFER)
                AnkoLogger("当前转换的内容").error { "buf is =" + buf }
            }

            /*
            * 以下代码用于获取与云端的会话id，当业务出错时将会话id提供给技术支持人员，可用于查询会话日志，定位出错原因
            * 若使用本地能力，会话id为null
            */
            if (SpeechEvent.EVENT_SESSION_ID == eventType) {
                val sid = obj?.getString(SpeechEvent.KEY_EVENT_SESSION_ID);
                AnkoLogger("使用在线引擎:").error { "会话id——session id 为：$sid" }
            }
        }

        override fun onSpeakPaused() {
            tv_speechStatus.text = "当前播放状态：暂停播放"
        }

        override fun onSpeakResumed() {
            tv_speechStatus.text = "当前播放状态：继续播放"
        }

        override fun onCompleted(error: SpeechError?) {
            if (null == error) {
                tv_speechStatus.text = "播放完成"
                tv_percent.text = "当前合成进度：100, 当前播放进度：100"
                et_input.text = SpannableStringBuilder(mText2Convert)
            } else {
                tv_speechStatus.text = error.getPlainDescription(true)
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        if (null != mSpeechSynthesizer) {
            mSpeechSynthesizer!!.stopSpeaking()
            mSpeechSynthesizer!!.destroy()
        }
    }
}
