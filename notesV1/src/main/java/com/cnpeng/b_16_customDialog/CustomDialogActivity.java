package com.cnpeng.b_16_customDialog;

import android.os.Bundle;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.cnpeng.cnpeng_demos2017_01.R;
import com.cnpeng.cnpeng_demos2017_01.databinding.CustomDialogBinding;
import com.cnpeng.utils.LogUtils;

import static android.view.ViewGroup.LayoutParams.MATCH_PARENT;
import static android.view.ViewGroup.LayoutParams.WRAP_CONTENT;

/**
 * 作者：CnPeng
 * <p>
 * 时间：2017/7/24:下午5:33
 * <p>
 * 说明：自定义dialog的使用，自定义宽度，自定义高度，以及完全的自定义dialog
 * <p>
 * window.setLayout()                                   在show() 之后调用。一次性操作，无法动态控制宽高，
 * window.setAttributes()                               在show() 之后调用。一次性操作，无法动态控制宽高
 * viewTreeObserver.addOnPreDrawListener()              在show() 之前/之后均可。可以动态控制宽高
 * viewTreeObserver.addOnGlobalLayoutListener()         在show() 之前/之后均可。可以动态控制宽高
 * view.addOnLayoutChangeListener()                     在show() 之前/之后均可。可以动态控制宽高
 * <p>
 * 在获取原生Dialog的viewTreeObserver 时，先获取window ,然后 window.getDecorView().getViewTreeObserver();
 */

public class CustomDialogActivity extends AppCompatActivity implements View.OnClickListener {

    private CustomDialogBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_customdialog);

        init();
    }

    private void init() {
        binding.btCustomHeight1.setOnClickListener(CustomDialogActivity.this);
        binding.btCustomHeight2.setOnClickListener(CustomDialogActivity.this);
        binding.btCustomHeight3.setOnClickListener(CustomDialogActivity.this);
        binding.btCustomHeight4.setOnClickListener(CustomDialogActivity.this);
        binding.btCustomHeight5.setOnClickListener(CustomDialogActivity.this);
        binding.btCustomWidth1.setOnClickListener(CustomDialogActivity.this);
        binding.btCustomWidth2.setOnClickListener(CustomDialogActivity.this);
        binding.btCustomWidth3.setOnClickListener(CustomDialogActivity.this);
        binding.btCustomWidth4.setOnClickListener(CustomDialogActivity.this);
        binding.btCustomWidth5.setOnClickListener(CustomDialogActivity.this);
        binding.btMaxHeight1.setOnClickListener(CustomDialogActivity.this);
        binding.btMaxHeight2.setOnClickListener(CustomDialogActivity.this);
        binding.btMaxHeight3.setOnClickListener(CustomDialogActivity.this);
        binding.btCustomDialog.setOnClickListener(CustomDialogActivity.this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_customHeight1:
                AlertDialog.Builder builder_h1 = new AlertDialog.Builder(this);
                builder_h1.setMessage("高度--window.setLayout()");
                AlertDialog dialog_h1 = builder_h1.create();

                dialog_h1.show();

                Window window1 = dialog_h1.getWindow();
                if (null != window1) {
                    Display display = window1.getWindowManager().getDefaultDisplay();
                    int height = display.getHeight();
                    //在 dialog.show() 之后调用 setLayout() 才能生效。
                    window1.setLayout(WRAP_CONTENT, (int) (height * 0.6));  //指定高度
                    //window1.setLayout(WRAP_CONTENT, WRAP_CONTENT);          //包裹内容
                    //window1.setLayout(WRAP_CONTENT, MATCH_PARENT);          //填充屏幕
                }
                break;
            case R.id.bt_customHeight2:
                AlertDialog.Builder builder_h2 = new AlertDialog.Builder(this);
                builder_h2.setMessage("高度--window2.setAttributes()");
                AlertDialog dialog_h2 = builder_h2.create();
                dialog_h2.show();

                Window window2 = dialog_h2.getWindow();
                if (null != window2) {
                    Display display = window2.getWindowManager().getDefaultDisplay();
                    int height = display.getHeight();
                    WindowManager.LayoutParams params = window2.getAttributes();
                    params.height = (int) (height * 0.3);                         //指定高度
                    //params.height = WindowManager.LayoutParams.WRAP_CONTENT;       //包裹内容
                    //params.height = WindowManager.LayoutParams.MATCH_PARENT;     //填充屏幕
                    //在 dialog.show() 之后调用 setLayout() 才能生效。
                    window2.setAttributes(params);
                }

                break;
            case R.id.bt_customHeight3:
                AlertDialog.Builder builder_h3 = new AlertDialog.Builder(this);
                builder_h3.setMessage("高度--viewTreeObserver.addOnPreDrawListener()");
                AlertDialog dialog_h3 = builder_h3.create();

                dialog_h3.show();   //先show 后 show 均可

                final Window window3 = dialog_h3.getWindow();
                if (null != window3) {
                    Display display = window3.getWindowManager().getDefaultDisplay();
                    final int height = display.getHeight();
                    //在 dialog.show() 之前调用 setLayout() 才能生效。
                    ViewTreeObserver viewTreeObserver = window3.getDecorView().getViewTreeObserver();
                    viewTreeObserver.addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
                        @Override
                        public boolean onPreDraw() {
                            window3.setLayout(MATCH_PARENT, (int) (height * 0.3)); //指定高度
                            //window3.setLayout(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams
                            //.WRAP_CONTENT); //包裹内容
                            //window3.setLayout(MATCH_PARENT, MATCH_PARENT); //填充屏幕
                            return true;
                        }
                    });
                }
                //dialog_h3.show();
                break;
            case R.id.bt_customHeight4:
                AlertDialog.Builder builder_h4 = new AlertDialog.Builder(this);
                builder_h4.setMessage("高度--viewTreeObserver.addOnGlobalLayoutListener()");
                AlertDialog dialog_h4 = builder_h4.create();

                final Window window_h4 = dialog_h4.getWindow();
                if (null != window_h4) {
                    Display display = window_h4.getWindowManager().getDefaultDisplay();
                    final int height = display.getHeight();
                    //dialog_h4.show();     //先调show 和 后调 show 均可

                    ViewTreeObserver viewTreeObserver = window_h4.getDecorView().getViewTreeObserver();
                    viewTreeObserver.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                        @Override
                        public void onGlobalLayout() {
                            window_h4.setLayout(MATCH_PARENT, (int) (height * 0.6)); //指定高度
                            // window_h4.setLayout(FrameLayout.LayoutParams.MATCH_PARENT,FrameLayout.LayoutParams
                            // .WRAP_CONTENT); //包裹内容
                            // window_h4.setLayout(MATCH_PARENT, MATCH_PARENT); //填充屏幕
                        }
                    });
                    dialog_h4.show();
                }

                break;

            case R.id.bt_customHeight5:
                AlertDialog.Builder builder_h5 = new AlertDialog.Builder(this);
                builder_h5.setMessage("高度--viewTreeObserver.addOnGlobalLayoutListener()");
                AlertDialog dialog_h5 = builder_h5.create();

                final Window window_h5 = dialog_h5.getWindow();
                if (null != window_h5) {
                    Display display = window_h5.getWindowManager().getDefaultDisplay();
                    final int height = display.getHeight();
                    //dialog_h4.show();     //先调show 和 后调 show 均可

                    View view = window_h5.getDecorView();
                    view.addOnLayoutChangeListener(new View.OnLayoutChangeListener() {
                        @Override
                        public void onLayoutChange(View v, int left, int top, int right, int bottom, int oldLeft,
                                                   int oldTop, int oldRight, int oldBottom) {
                            window_h5.setLayout(MATCH_PARENT, (int) (height * 0.6)); //指定高度
                            // window_h4.setLayout(FrameLayout.LayoutParams.MATCH_PARENT,FrameLayout.LayoutParams
                            // .WRAP_CONTENT); //包裹内容
                            // window_h4.setLayout(MATCH_PARENT, MATCH_PARENT); //填充屏幕
                        }
                    });
                    dialog_h5.show();
                }

                break;
            case R.id.bt_customWidth1:
                AlertDialog.Builder builder_w1 = new AlertDialog.Builder(this);
                builder_w1.setMessage("宽度--window.setLayout()");
                AlertDialog dialog_w1 = builder_w1.create();
                dialog_w1.show();

                Window window4 = dialog_w1.getWindow();
                if (null != window4) {
                    Display display = window4.getWindowManager().getDefaultDisplay();
                    int width = display.getWidth();
                    //在 dialog.show() 之后调用 setLayout() 才能生效。
                    window4.setLayout((int) (width * 0.3), WRAP_CONTENT);  //指定宽度
                    //window4.setLayout(WRAP_CONTENT, WRAP_CONTENT);          //包裹内容
                    //window4.setLayout(MATCH_PARENT, WRAP_CONTENT);          //填充屏幕
                    // window4.setLayout((int) (width * 0.3), MATCH_PARENT);  //指定宽
                    //window4.setLayout(WRAP_CONTENT, MATCH_PARENT);          //包裹内容
                    //window4.setLayout(MATCH_PARENT, MATCH_PARENT);          //填充屏幕
                }
                break;
            case R.id.bt_customWidth2:
                AlertDialog.Builder builder_w2 = new AlertDialog.Builder(this);
                builder_w2.setMessage("宽度--window.setAttributes()");
                AlertDialog dialog_w2 = builder_w2.create();
                dialog_w2.show();

                Window window5 = dialog_w2.getWindow();
                if (null != window5) {
                    Display display = window5.getWindowManager().getDefaultDisplay();
                    WindowManager.LayoutParams params = window5.getAttributes();
                    int height = display.getHeight();
                    params.width = (int) (height * 0.3);                        //指定宽度
                    //params.width = WindowManager.LayoutParams.WRAP_CONTENT;       //包裹内容
                    //params.width = WindowManager.LayoutParams.MATCH_PARENT;       //包裹内容

                    //params.height = (int) (display.getWidth() * 0.3);             //指定高度
                    //params.height = WindowManager.LayoutParams.MATCH_PARENT;    //高度填充屏幕
                    params.height = WindowManager.LayoutParams.WRAP_CONTENT;     //高度包裹
                    //在 dialog.show() 之后调用 setLayout() 才能生效。
                    window5.setAttributes(params);
                }
                break;
            case R.id.bt_customWidth3:
                AlertDialog.Builder builder_w3 = new AlertDialog.Builder(this);
                builder_w3.setMessage("高度--viewTreeObserver.addOnPreDrawListener()");
                AlertDialog dialog_w3 = builder_w3.create();

                final Window window6 = dialog_w3.getWindow();
                if (null != window6) {
                    Display display = window6.getWindowManager().getDefaultDisplay();
                    final int height = display.getHeight();
                    //在 dialog.show() 之前调用 setLayout() 才能生效。
                    ViewTreeObserver viewTreeObserver = window6.getDecorView().getViewTreeObserver();
                    viewTreeObserver.addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
                        @Override
                        public boolean onPreDraw() {
                            //window6.setLayout(MATCH_PARENT, (int) (height * 0.3)); //指定宽度  高度用FrameLayout.LayoutParams
                            //window6.setLayout(MATCH_PARENT, WRAP_CONTENT); //包裹内容 用FrameLayout.LayoutParams
                            //window6.setLayout(MATCH_PARENT, MATCH_PARENT); //填充屏幕 

                            // window6.setLayout(WRAP_CONTENT, (int) (height * 0.3)); //指定宽度 高度用FrameLayout.LayoutParams
                            //window6.setLayout(WRAP_CONTENT, WRAP_CONTENT); //包裹内容 用FrameLayout.LayoutParams
                            window6.setLayout(WRAP_CONTENT, MATCH_PARENT); //填充屏幕
                            return true;
                        }
                    });

                }
                dialog_w3.show();

                break;
            case R.id.bt_maxHeight1:     //控制最大高度
                AlertDialog.Builder builder_mh1 = new AlertDialog.Builder(this);
                final AlertDialog dialog_mh1 = builder_mh1.create();
                final View view_mh1 = LayoutInflater.from(this).inflate(R.layout.custom_dialog_maxheight, null);
                dialog_mh1.setView(view_mh1);
                dialog_mh1.show();  //先show 后show 均可

                ViewTreeObserver viewTreeObserver_mh1 = view_mh1.getViewTreeObserver();
                viewTreeObserver_mh1.addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
                    @Override
                    public boolean onPreDraw() {
                        int viewHeight = view_mh1.getMeasuredHeight();
                        if (viewHeight > 500) {
                            Window window_mh1 = dialog_mh1.getWindow();
                            if (null != window_mh1) {
                                window_mh1.setLayout(FrameLayout.LayoutParams.MATCH_PARENT, 500);    //最大高度控制为500
                            }
                        }
                        return true;    //不再分发，自己处理
                    }
                });
                //dialog_mh1.show();

                break;
            case R.id.bt_maxHeight2:     //控制最大高度
                AlertDialog.Builder builder_mh2 = new AlertDialog.Builder(this);
                final AlertDialog dialog_mh2 = builder_mh2.create();
                final View view_mh2 = LayoutInflater.from(this).inflate(R.layout.custom_dialog_maxheight, null);
                dialog_mh2.setView(view_mh2);
                //                dialog_mh2.show();  //先show 后show 均可

                final ViewTreeObserver viewTreeObserver_mh2 = view_mh2.getViewTreeObserver();
                viewTreeObserver_mh2.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                    @Override
                    public void onGlobalLayout() {
                        int viewHeight = view_mh2.getMeasuredHeight();
                        if (viewHeight > 500) {
                            Window window_mh1 = dialog_mh2.getWindow();
                            if (null != window_mh1) {
                                window_mh1.setLayout(FrameLayout.LayoutParams.MATCH_PARENT, 500);    //最大高度控制为500
                            }
                        }
                        viewTreeObserver_mh2.removeOnGlobalLayoutListener(this);
                    }
                });
                dialog_mh2.show();

                break;
            case R.id.bt_maxHeight3:     //控制最大高度
                AlertDialog.Builder builder_mh3 = new AlertDialog.Builder(this);
                final AlertDialog dialog_mh3 = builder_mh3.create();
                final View view_mh3 = LayoutInflater.from(this).inflate(R.layout.custom_dialog_maxheight, null);
                dialog_mh3.setView(view_mh3);
                dialog_mh3.show();  //先show 后show 均可

                view_mh3.addOnLayoutChangeListener(new View.OnLayoutChangeListener() {
                    @Override
                    public void onLayoutChange(View v, int left, int top, int right, int bottom, int oldLeft,
                                               int oldTop, int oldRight, int oldBottom) {
                        int viewHeight = view_mh3.getMeasuredHeight();  //view的高度
                        int viewHeight1 = v.getMeasuredHeight();        //view的高度，值与viewHeight相同
                        int viewHeifht2 = bottom - top;                 //view的高度，值与viewHeight相同
                        LogUtils.e("高度值", viewHeight + "/" + viewHeight1 + "/" + viewHeifht2);
                        if (viewHeight > 500) {
                            Window window_mh1 = dialog_mh3.getWindow();
                            if (null != window_mh1) {
                                window_mh1.setLayout(FrameLayout.LayoutParams.MATCH_PARENT, 500);    //最大高度控制为500
                            }
                        }
                    }
                });

                //                dialog_mh3.show();
                break;
            case R.id.bt_customDialog:  //完全自定义dialog
                CustomAlertDialog dialog = new CustomAlertDialog(this);
                dialog.setTitle("随表写一个标题看看随表写一个标题看看随表写一个标题看看随表写一个标题看看随表写一个标题看看随表写一个标题看看");
                dialog.setMessage("消息内容消息内容消息内容消息内容消息内容消息内容消息内容消息内容消息内容消息内容消息内容消息内容消息内容消息内容消息内容消息内容消息内容");
                dialog.setPositiveButton("确定", null);
                dialog.setNegativeButton("取消", new CustomAlertDialog.AntDialogClickListener() {
                    @Override
                    public void onClick(View view) {
                        Toast.makeText(CustomDialogActivity.this, "点击了取消按钮", Toast.LENGTH_SHORT).show();
                    }
                });
                dialog.show();
                break;
        }
    }
}
