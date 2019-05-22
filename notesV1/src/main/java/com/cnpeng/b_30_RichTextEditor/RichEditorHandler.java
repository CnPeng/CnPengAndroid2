package com.cnpeng.b_30_RichTextEditor;

import android.content.Context;
import android.graphics.Color;
import android.view.View;

import com.cnpeng.cnpeng_demos2017_01.databinding.ActivityRichEditorBinding;
import com.cnpeng.utils.LogUtils;

import jp.wasabeef.richeditor.RichEditor;

/**
 * 作者：CnPeng
 * <p>
 * 时间：2018/3/7:下午8:35
 * <p>
 * 说明：富文本编辑器界面的帮助类
 * TODO 调研音频和视频怎么加入
 */
public class RichEditorHandler {
    private final Context                   mContext;
    private final ActivityRichEditorBinding mBinding;
    private final RichEditor                mRichEditor;
    private       String                    mText;

    public RichEditorHandler(Context context, ActivityRichEditorBinding binding) {
        this.mContext = context;
        mBinding = binding;
        mRichEditor = mBinding.richEditor;
        initTextChangeListener();
        initEditor();

    }

    private void initEditor() {
        mRichEditor.setEditorHeight(200);
        mRichEditor.setEditorFontSize(22);
        mRichEditor.setEditorFontColor(Color.RED);
        mRichEditor.setPadding(10, 10, 10, 10);
        mRichEditor.setPlaceholder("Insert text here...");
        mRichEditor.setAlignLeft();
        //mEditor.setEditorBackgroundColor(Color.BLUE);
        //mEditor.setBackgroundColor(Color.BLUE);
        //mEditor.setBackgroundResource(R.drawable.bg);
        //mEditor.setBackground("https://raw.githubusercontent.com/wasabeef/art/master/chip.jpg");
        //mEditor.setInputEnabled(false);

    }

    /**
     * 初始化监听器，实时取出文本
     */
    private void initTextChangeListener() {
        mBinding.richEditor.setOnTextChangeListener(new RichEditor.OnTextChangeListener() {
            @Override
            public void onTextChange(String text) {
                // 实际是一个 html格式的文本               
                mText = text;
            }
        });
    }

    public void save(View view) {
        //TODO 保存数据到本地
    }

    public void preview(View view) {
        //TODO 携带数据跳转到预览界面，在 预览界面使用 setHtml（text）展示
        LogUtils.e("富文本", mText);
    }

    public void link(View view) {
        //TODO 加入超链接,需要自己输入链接标题（参考简书）
        mRichEditor.insertLink("www.baidu.com", "百度");
    }

    public void insertImage(View view) {
        //TODO 加入图片，后面的alt是啥？需要调出相册
        mRichEditor.insertImage("http://www.1honeywan.com/dachshund/image/7.21/7.21_3_thumb.JPG", "这个不知道是啥啊，");
    }

    /**
     * 引用
     */
    public void blockquote(View view) {
        //TODO 背景需要特殊处理. setBackgroundColor(Color.BLUE); 会将整个编辑区置为蓝色，不可用
        //        mRichEditor.setEditorBackgroundColor(Color.BLUE);
        mRichEditor.setBlockquote();
    }

    public void unDo(View view) {
        mRichEditor.undo();
    }

    public void reDo(View view) {
        mRichEditor.redo();
    }

    public void bold(View view) {
        mRichEditor.setBold();
    }

    public void italic(View view) {
        mRichEditor.setItalic();
    }

    /**
     * 删除线
     */
    public void strikeThrough(View view) {
        mRichEditor.setStrikeThrough();
    }

    public void underLine(View view) {
        mRichEditor.setUnderline();
    }

    public void heading1(View view) {
        mRichEditor.setHeading(1);
    }

    public void heading2(View view) {
        mRichEditor.setHeading(2);
    }

    public void heading3(View view) {
        mRichEditor.setHeading(3);
    }

    public void heading4(View view) {
        mRichEditor.setHeading(4);
    }

    public void heading5(View view) {
        mRichEditor.setHeading(5);
    }

    public void heading6(View view) {
        mRichEditor.setHeading(6);

    }

    /**
     * 设置文本颜色，颜色格式为16进制
     */
    public void textColor(View view) {
        mRichEditor.setTextColor(0x4466AA);
    }

    public void bgColor(View view) {
        mRichEditor.setBackgroundColor(0x3e3e3e);
    }

    public void alignLeft(View view) {
        mRichEditor.setAlignLeft();
    }


    /**
     * 无序列表。 bullet 子弹。无序列表前面的圆点类似于子弹
     */
    public void bullets(View view) {
        mRichEditor.setBullets();
    }

    /**
     * 有序列表
     */
    public void numbers(View view) {
        mRichEditor.setNumbers();
    }
}
