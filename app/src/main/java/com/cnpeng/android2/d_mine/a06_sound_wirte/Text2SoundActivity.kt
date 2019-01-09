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
import com.iflytek.cloud.SpeechConstant.TYPE_CLOUD
import com.iflytek.cloud.util.ResourceUtil
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
    var mVolume = 50
    var mSpeed = 50
    var mPitch = 50
    var mAudioStream = 3

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_text2_sound)

        initSpeechSynthesize()
        initClickEvent()
        initEngineSelectEvent()
        initSettingEvent()
        initAudioStreamSelectEvent()
    }

    /**
     * CnPeng 2019/1/9 5:05 PM
     * 功用：初始化音频流的选择事件
     * 说明：
     * 具体取值对照 AudioSystem 中的 STRAM_xxx, 如 STREAM_VOICE_CALL、STREAM_MUSIC
     */
    private fun initAudioStreamSelectEvent() {
        rg_audioStream.setOnCheckedChangeListener { group, checkedId ->
            when (checkedId) {
                R.id.rb_call -> mAudioStream = 0
                R.id.rb_sys -> mAudioStream = 1
                R.id.rb_ring -> mAudioStream = 2
                R.id.rb_music -> mAudioStream = 3
                R.id.rb_alarm -> mAudioStream = 4
                R.id.rb_notify -> mAudioStream = 5
                else -> toast("没实现")
            }
        }
    }

    private fun initSettingEvent() {
        tv_pitchConfirm.setOnClickListener(this)
        tv_volumeConfirm.setOnClickListener(this)
        tv_speedConfirm.setOnClickListener(this)

    }

    private fun initEngineSelectEvent() {
        rg_engineType.setOnCheckedChangeListener { group, checkedId ->
            when (checkedId) {
                R.id.rb_onLine -> {
                    mEngineType = SpeechConstant.TYPE_CLOUD
                    if (mSpeed in 101..200) {
                        toast("在线模式语速只能在0--100之间")
                    }
                }
                R.id.rb_offLine -> mEngineType = SpeechConstant.TYPE_LOCAL
                else -> toast("啥情况？怎么会有其他id？")
            }
        }
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
            R.id.tv_cancel -> {
                mSpeechSynthesizer!!.stopSpeaking()
                et_input.text = SpannableStringBuilder(mText2Convert)
            }

            R.id.tv_volumeConfirm -> changeVolume()
            R.id.tv_speedConfirm -> changeSpeed()
            R.id.tv_pitchConfirm -> changePitch()
            else -> toast("暂未实现")
        }
    }

    /**
     * CnPeng 2019/1/9 4:25 PM
     * 功用：改变音调
     * 说明：
     */
    private fun changePitch() {
        val tempPitch = et_pitch.text.toString().toInt()
        if (tempPitch in 0..100) {
            mPitch = tempPitch
            toast("新设置的音调为$mPitch")
        } else {
            toast("音调取值0--100")
            et_pitch.text = SpannableStringBuilder(mPitch.toString())
        }
    }

    /**
     * CnPeng 2019/1/9 4:20 PM
     * 功用：改变语速
     * 说明：
     */
    private fun changeSpeed() {
        val tempSpeed = et_speed.text.toString().toInt()
        if (TYPE_CLOUD == mEngineType) {
            if (tempSpeed in 0..100) {
                mSpeed = tempSpeed
            } else {
                toast("在线引擎语速只能在0--100之间")
                et_speed.text = SpannableStringBuilder(mSpeed.toString())
            }
        } else {
            if (tempSpeed in 0..200) {
                mSpeed = tempSpeed
                toast("新设置的语速为$mSpeed")
            } else {
                toast("离线引擎语速只能在0--200之间")
                et_speed.text = SpannableStringBuilder(mSpeed.toString())
            }
        }
    }

    private fun changeVolume() {
        val tempVolume = et_volume.text.toString().toInt()
        if (tempVolume in 0..100) {
            mVolume = tempVolume
            toast("新设置的音量为$mVolume")
        } else {
            et_volume.text = SpannableStringBuilder(mVolume.toString())
            toast("音量取值只能为1--100之间的值")
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

        //	/**
        //	 * 只保存音频不进行播放接口,调用此接口请注释startSpeaking接口
        //	 * text:要合成的文本，uri:需要保存的音频全路径，listener:回调接口
        //	*/
        /*var path = Environment.getExternalStorageDirectory()+"/tts.pcm";
        var code = mSpeechSynthesizer.synthesizeToUri(text, path, mTtsListener);*/
    }

    private fun setSynthesizeParam() {
        if (null == mSpeechSynthesizer)
            return


        // 清空参数
        mSpeechSynthesizer!!.setParameter(SpeechConstant.PARAMS, null)
        // 根据合成引擎设置相应参数
        if (!mIsNetAvailable) {
            toast("当前网络不可用，已经自动切换为离线模式")
            mEngineType = SpeechConstant.TYPE_LOCAL

            mSpeechSynthesizer!!.setParameter(ResourceUtil.TTS_RES_PATH, getResourcePath());
        }
        mSpeechSynthesizer!!.setParameter(SpeechConstant.ENGINE_TYPE, SpeechConstant.TYPE_CLOUD)
        mSpeechSynthesizer!!.setParameter(SpeechConstant.TTS_DATA_NOTIFY, "1")
        // 设置在线合成发音人
        mSpeechSynthesizer!!.setParameter(SpeechConstant.VOICE_NAME, mVoicer)
        //设置合成语速
        mSpeechSynthesizer!!.setParameter(SpeechConstant.SPEED, mSpeed.toString())
        //设置合成音调
        mSpeechSynthesizer!!.setParameter(SpeechConstant.PITCH, mPitch.toString())
        //设置合成音量
        mSpeechSynthesizer!!.setParameter(SpeechConstant.VOLUME, mVolume.toString())

        //设置播放器音频流类型
        mSpeechSynthesizer!!.setParameter(SpeechConstant.STREAM_TYPE, mAudioStream.toString())
        // 设置播放合成音频打断音乐播放，默认为true
        mSpeechSynthesizer!!.setParameter(SpeechConstant.KEY_REQUEST_FOCUS, "true")

        // 设置音频保存路径，保存音频格式支持pcm、wav，设置路径为sd卡请注意WRITE_EXTERNAL_STORAGE权限
        mSpeechSynthesizer!!.setParameter(SpeechConstant.AUDIO_FORMAT, "pcm")
        mSpeechSynthesizer!!.setParameter(SpeechConstant.TTS_AUDIO_PATH, Environment.getExternalStorageDirectory().toString() + "/msc/tts.pcm")
    }

    /**
     * CnPeng 2019/1/9 4:00 PM
     * 功用：获取离线包在本地的路径
     * 说明：
     */
    private fun getResourcePath(): String {
        val tempBuffer = StringBuffer()
        //识别通用资源：链接到放置 common.jet / xiaofeng.jet / xiaoyan.jet 在assets中的路径
        tempBuffer.append(ResourceUtil.generateResourcePath(this, ResourceUtil.RESOURCE_TYPE.assets, "tts/common.jet"))
        tempBuffer.append(";")
        tempBuffer.append(ResourceUtil.generateResourcePath(this, ResourceUtil.RESOURCE_TYPE.assets, "tts/xiaofeng.jet"))
        tempBuffer.append(";")
        tempBuffer.append(ResourceUtil.generateResourcePath(this, ResourceUtil.RESOURCE_TYPE.assets, "tts/xiaoyan.jet"))
        //识别8k资源-使用8k的时候请解开注释
        return tempBuffer.toString()
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

            //CnPeng 2019/1/9 3:18 PM 改变当前正在播放内容的背景色——这地方有一个问题，就是最后一个文字不变背景色。因为100%时endPos、percent不返回值
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
