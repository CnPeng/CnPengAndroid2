package com.cnpeng.c_00_CommonCustomView;


import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.cnpeng.cnpeng_demos2017_01.R;

import static android.util.TypedValue.COMPLEX_UNIT_PX;

/**
 * 作者：CnPeng
 * <p>
 * 时间：2017/6/20:下午4:49
 * <p>
 * 说明：
 * 内容为空时不显示删除图标 √
 * 焦点改变时不显示删除图标 √
 * 删除图标的点击事件      √
 * 内容变化时控制删除图标的展示 √
 * EditText 的字号，颜色，hint 的字号，颜色， maxLines, inputType ,maxLength √
 * 暴露替换删除按钮图片的属性、图标和文字之间的padding设置属性 √
 */
public class EditTextWithDel extends LinearLayout {


    private Context      mContext;
    private LinearLayout ll_root;
    private EditText     editText;
    private ImageView    iv_del;

    public EditTextWithDel(Context paramContext) {
        this(paramContext, null);
    }

    public EditTextWithDel(Context paramContext, AttributeSet paramAttributeSet) {
        this(paramContext, paramAttributeSet, 0);
    }

    public EditTextWithDel(Context paramContext, AttributeSet paramAttributeSet, int defStyleAttr) {
        super(paramContext, paramAttributeSet, defStyleAttr);
        mContext = paramContext;
        initView();

        initAttr(paramAttributeSet, defStyleAttr);
    }

    private void initAttr(AttributeSet paramAttributeSet, int defStyleAttr) {
        TypedArray a = mContext.obtainStyledAttributes(paramAttributeSet, R.styleable.EditTextWithDel, defStyleAttr, 0);

        int n = a.getIndexCount();
        for (int i = 0; i < n; i++) {
            int attr = a.getIndex(i);

            switch (attr) {
                case R.styleable.EditTextWithDel_android_autoLink:
                    int mAutoLinkMask = a.getInt(attr, 0);
                    editText.setAutoLinkMask(mAutoLinkMask);
                    break;

                case R.styleable.EditTextWithDel_android_drawableRight:
                    Drawable drawableRight = a.getDrawable(attr);
                    iv_del.setImageDrawable(drawableRight);
                    break;

                case R.styleable.EditTextWithDel_android_drawablePadding:
                    int drawablePadding = a.getDimensionPixelSize(attr, 0);
                    iv_del.setPadding(drawablePadding, 0, 0, 0);
                    break;

                case R.styleable.EditTextWithDel_android_maxLines:
                    editText.setMaxLines(a.getInt(attr, -1));
                    break;

                case R.styleable.EditTextWithDel_android_hint:
                    CharSequence hint = a.getText(attr);
                    editText.setHint(hint);
                    break;

                case R.styleable.EditTextWithDel_android_text:
                    CharSequence text = a.getText(attr);
                    editText.setText(text);
                    break;


                case R.styleable.EditTextWithDel_android_ellipsize:
                    int ellipsize = a.getInt(attr, -1);
                    if (editText.getMaxLines() == 1 && editText.getKeyListener() == null && ellipsize < 0) {
                        ellipsize = 3; // END
                    }

                    switch (ellipsize) {
                        case 1:
                            editText.setEllipsize(TextUtils.TruncateAt.START);
                            break;
                        case 2:
                            editText.setEllipsize(TextUtils.TruncateAt.MIDDLE);
                            break;
                        case 3:
                            editText.setEllipsize(TextUtils.TruncateAt.END);
                            break;
                        case 4:
                            editText.setEllipsize(TextUtils.TruncateAt.MARQUEE);
                            break;
                    }
                    break;

                case R.styleable.EditTextWithDel_android_cursorVisible:
                    if (!a.getBoolean(attr, true)) {
                        editText.setCursorVisible(false);
                    }
                    break;

                case R.styleable.EditTextWithDel_android_maxLength:
                    int maxlength = a.getInt(attr, -1);
                    if (maxlength >= 0) {
                        editText.setFilters(new InputFilter[]{new InputFilter.LengthFilter(maxlength)});
                    } else {
                        editText.setFilters(new InputFilter[0]);
                    }
                    break;


                case R.styleable.EditTextWithDel_android_textColor:
                    ColorStateList textColor = a.getColorStateList(attr);
                    editText.setTextColor(textColor);
                    break;

                case R.styleable.EditTextWithDel_android_textColorHint:
                    ColorStateList textColorHint = a.getColorStateList(attr);
                    editText.setHintTextColor(textColorHint);
                    break;

                case R.styleable.EditTextWithDel_android_textColorLink:
                    ColorStateList textColorLink = a.getColorStateList(attr);
                    editText.setLinkTextColor(textColorLink);
                    break;

                case R.styleable.EditTextWithDel_android_textSize:
                    int textSize = a.getDimensionPixelSize(attr, 16);

                    // //170621 使用反射的方式获取EditText 的父类 TextView 中的私有方法-- setRawTextSize(float)
                    // Class clazz = editText.getClass().getSuperclass();  //获取TextView的class
                    // try {
                    //     Method method = clazz.getDeclaredMethod("setRawTextSize", float.class); //暴力反射获取全部方法，含私有和公有
                    //     method.setAccessible(true);     //访问权限设置为true
                    //     method.invoke(editText, textSize); //调用反射方法，第一个参数表示是哪个对象调用反射获取的方法，第二个参数表示方法的参数
                    // } catch (Exception e) {
                    //     e.printStackTrace();
                    // }

                    editText.setTextSize(COMPLEX_UNIT_PX, textSize);    //170703 不使用反射方式时用这种方法
                    break;

                case R.styleable.EditTextWithDel_android_inputType:
                    int inputType = a.getInt(attr, EditorInfo.TYPE_NULL);
                    editText.setInputType(inputType);
                    break;
                case R.styleable.EditTextWithDel_android_gravity:
                    editText.setGravity(a.getInt(attr, -1));
                    break;
            }
        }
        a.recycle();
    }

    private void initView() {
        LayoutInflater.from(mContext).inflate(R.layout.custom_edittext_withdel, this);
        ll_root = (LinearLayout) findViewById(R.id.ll_root_ETwithDel);
        editText = (EditText) findViewById(R.id.et_input_ETwithDel);
        iv_del = (ImageView) findViewById(R.id.iv_del_ETwithDel);
        initEvent();
    }

    /**
     * 初始化事件，点击，删除，文字变化
     */
    private void initEvent() {
        iv_del.setOnClickListener(new View.OnClickListener() {   //清空内容
            @Override
            public void onClick(View v) {
                editText.setText(null);
            }
        });

        editText.addTextChangedListener(new TextWatcher() { //文字变化
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() > 0) {
                    iv_del.setVisibility(View.VISIBLE);
                } else {
                    iv_del.setVisibility(View.GONE);
                }

                if (onTextChangedListener != null) {
                    onTextChangedListener.onTextChanged(s, start, before, count);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        editText.setOnFocusChangeListener(new OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    if (editText.getText().length() > 0) {
                        iv_del.setVisibility(View.VISIBLE);
                    }
                } else {
                    iv_del.setVisibility(View.GONE);
                }
            }
        });
    }


    /**
     * 返回EditText中的文本数据
     */
    public Editable getText() {
        return editText.getText();
    }

    /**
     * 设置文本到EditText中
     */
    public void setText(CharSequence charSequence) {
        editText.setText(charSequence);
    }

    /**
     * 设置EditText的选中
     */
    public void setSelection(int index) {
        editText.setSelection(index);
    }


    /**
     * 自定义文本变化监听，
     */
    public interface OnTextChangedListener {
        void onTextChanged(CharSequence s, int start, int before, int count);
    }

    /**
     * 对外暴露设置自定义的文本变化监听，
     */
    public void setOnTextChangedListener(OnTextChangedListener onTextChangedListener) {
        this.onTextChangedListener = onTextChangedListener;
    }

    OnTextChangedListener onTextChangedListener;
}