package com.cnpeng.b_31_picAndVideoSelector;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.bumptech.glide.Glide;
import com.cnpeng.cnpeng_demos2017_01.R;
import com.cnpeng.cnpeng_demos2017_01.databinding.ActivityPicSelectorBinding;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;

import java.util.List;

/**
 * 作者：CnPeng
 * <p>
 * 时间：2018/3/29:下午9:00
 * <p>
 * 说明：图片和视频选择的界面
 * 借助于Github上的 PictureSelector 库来实现该功能:https://github.com/LuckSiege/PictureSelector
 */

public class PicSelectorActivity extends AppCompatActivity {

    private ActivityPicSelectorBinding mBinding;

    @Override
    protected void onCreate(
            @Nullable
                    Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_pic_selector);

        initView();
    }

    private void initView() {
        mBinding.btPicSelector.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PictureSelector.create(PicSelectorActivity.this)
                        .openGallery(PictureMimeType.ofImage())
                        .isGif(true)
                        .forResult(PictureConfig.CHOOSE_REQUEST);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case PictureConfig.CHOOSE_REQUEST:
                    // 图片选择结果回调
                    List<LocalMedia> selectList = PictureSelector.obtainMultipleResult(data);
                    // 例如 LocalMedia 里面返回三种path
                    // 1.media.getPath(); 为原图path
                    // 2.media.getCutPath();为裁剪后path，需判断media.isCut();是否为true
                    // 3.media.getCompressPath();为压缩后path，需判断media.isCompressed();是否为true
                    // 如果裁剪并压缩了，以取压缩路径为准，因为是先裁剪后压缩的
                    Glide.with(PicSelectorActivity.this).load(selectList.get(0).getPath()).into(mBinding.ivSelectedPic);
                    break;
                default:
                    break;
            }
        }
    }
}
