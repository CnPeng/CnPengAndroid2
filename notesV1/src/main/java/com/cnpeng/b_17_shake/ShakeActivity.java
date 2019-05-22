package com.cnpeng.b_17_shake;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Vibrator;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.cnpeng.cnpeng_demos2017_01.R;
import com.cnpeng.b_16_customDialog.CustomAlertDialog;
import com.cnpeng.utils.LogUtils;

import org.apache.commons.lang3.concurrent.BasicThreadFactory;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import static android.hardware.Sensor.TYPE_ACCELEROMETER;


/**
 * 作者：CnPeng
 * <p>
 * 时间：2017/8/2:上午10:50
 * <p>
 * 说明：摇一摇的实现:播放音效，振动，弹出dialog
 * <p>
 * 注意：
 * 1、传感器监听要在 onResume  中注册，在 onPause 中取消注册
 * 2、振动要在清单文件中声明权限（4.4 以上不需要声明）
 * 3、sound 用完之后要在onDestroy 中释放并置空
 */

public class ShakeActivity extends AppCompatActivity implements SensorEventListener {
    private final int PlayAudioOver = 0;   //播放完毕
    private final int VibratorOver  = 1;   //振动结束

    private SensorManager     sensorManager;
    private Sensor            sensor;
    private SoundPool         soundPool;
    private boolean           isPlayAudio;  //是否正在播放音频
    private Vibrator          vibrator;     //振动器对象
    private boolean           isVibrator;   //是否正在振动
    private int               musicStreamId;  //通过SoundPool加载得到的音频id
    private float             playVolume;   //音量比率值
    private CustomAlertDialog customAlertDialog;

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (null != msg) {
                switch (msg.what) {
                    case PlayAudioOver:
                        isPlayAudio = false;
                        break;
                    case VibratorOver:
                        isVibrator = false;
                        break;
                    default:
                        break;
                }
            }
        }
    };

    @Override
    protected void onCreate(
            @Nullable
                    Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        DataBindingUtil.setContentView(this, R.layout.activity_shake);

        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);   //传感器管理器
        sensor = sensorManager.getDefaultSensor(TYPE_ACCELEROMETER);     //此处传入1 也可以，Sensor中加速度传感器对应的int值为1

        soundPool = new SoundPool(1, AudioManager.STREAM_MUSIC, 0);        //音效池
        musicStreamId = soundPool.load(ShakeActivity.this, R.raw.audio_shake, 1);//根据id加载音频

        AudioManager audioManager = (AudioManager) getSystemService(AUDIO_SERVICE); //用来获取音量
        int curVolume = audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);    //当前音量值
        int maxVolume = audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);   //最大音量
        playVolume = (float) (curVolume * 1.0 / maxVolume);   //音量比率值

        vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);          //振动器

        customAlertDialog = new CustomAlertDialog(ShakeActivity.this);      //振动的时候需要展示的dialog
    }

    @Override
    protected void onResume() {
        super.onResume();
        sensorManager.registerListener(ShakeActivity.this, sensor, SensorManager.SENSOR_DELAY_GAME);

    }

    @Override
    protected void onPause() {
        super.onPause();
        sensorManager.unregisterListener(ShakeActivity.this, sensor);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        soundPool.release();    //销毁
        soundPool = null;
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        int type = event.sensor.getType();  //获取传感器类型
        if (type == 1) {   //等价于  type==TYPE_ACCELEROMETER
            float x = event.values[0];
            float y = event.values[1];
            float z = event.values[2];

            if (Math.abs(x) > 15 && Math.abs(y) > 15 && Math.abs(z) > 15) {     //摇动灵敏度取决于后面的常量值，这里定义了15
                playShakeAudio();
                vibratorPhone();
                showCusDialog();
            }
        }
    }

    /**
     * 展示dialog
     */
    private void showCusDialog() {
        customAlertDialog.setTitle("摇一摇");
        customAlertDialog.setMessage("摇一摇，摇到了外婆桥");
        customAlertDialog.setPositiveButton("关闭", null);
        customAlertDialog.show();
    }

    /**
     * 开启手机震动
     */
    private void vibratorPhone() {
        if (!isVibrator) {
            isVibrator = true;
            vibrator.vibrate(300);  //振动时长300ms

            new Timer().schedule(new TimerTask() {
                @Override
                public void run() {
                    handler.sendEmptyMessage(VibratorOver);
                }
            }, 400);        //延时时间根据振动时长决定
        }
    }

    /**
     * 播放摇一摇的音频
     */
    private void playShakeAudio() {
        if (!isPlayAudio) {

            isPlayAudio = true;
            soundPool.play(musicStreamId, playVolume, playVolume, 1, 0, 1.0f);
            LogUtils.e("摇一摇", "摇啊摇，要到了外婆桥");


            //            Timer timer = new Timer();
            //            timer.schedule(new TimerTask() {
            //                @Override
            //                public void run() {
            //                    handler.sendEmptyMessage(PlayAudioOver);
            //                }
            //            }, 1000);    //此处延时时间根据音频时间确定

            //org.apache.commons.lang3.concurrent.BasicThreadFactory
            ScheduledExecutorService executorService = new ScheduledThreadPoolExecutor(1, new BasicThreadFactory
                    .Builder().daemon(true).build());
            executorService.schedule(new Runnable() {
                @Override
                public void run() {
                    //do something
                    handler.sendEmptyMessage(PlayAudioOver);
                }
            }, 1000, TimeUnit.MILLISECONDS);

        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        //灵敏度变化时调用。灵敏度级别参考：SensorManager.SENSOR_DELAY_GAME
    }
}
