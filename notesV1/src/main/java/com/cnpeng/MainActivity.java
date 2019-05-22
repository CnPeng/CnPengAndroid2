package com.cnpeng;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.cnpeng.A_16_ImageBrowserV2.ImageBrowserV2Activtiy;
import com.cnpeng.a_01_WebView01.WebViewTestActivity01;
import com.cnpeng.a_02_ViewStubTest.ViewStubTestActivitiy;
import com.cnpeng.a_03_CreateViewWithoutXML.CreateViewWithoutXMLActivity;
import com.cnpeng.a_04_ImageBrowser_V1.ImageBrowserActivityV1;
import com.cnpeng.a_05_scrollBallWithFinger.ScrollBallActivity;
import com.cnpeng.a_06_tablelayoutTest.TableLayoutTestActivity;
import com.cnpeng.a_07_frameLayout_NoenLamp.NeonLampActivity;
import com.cnpeng.a_08_relativeLayout_plumBlossom.PlumBlossomActivity;
import com.cnpeng.a_09_gridLayout_caculater.CaculaterActivity;
import com.cnpeng.a_10_textView_01.TextView01Activtiy;
import com.cnpeng.a_11_9PatchTest.NinePatchActivity;
import com.cnpeng.a_12_RadioButtonAndCheckBox.RadioButtonAndCheckBoxActivity;
import com.cnpeng.a_13_ToogleButtonAndSwitch.TBAndSwitchActivity;
import com.cnpeng.a_14_TextClockAndAnalogClock.TcAndAcActivity;
import com.cnpeng.a_15_chronometer.ChronometerActivity;
import com.cnpeng.a_17_imageButtonAndZoomButton.ImageButtonAndZoomButtonActivity;
import com.cnpeng.a_18_QuickContactBadge.QuickContactBadgeActivity;
import com.cnpeng.a_19_ListView01.ListView01Activity;
import com.cnpeng.a_20_showLvWithArrayAdapter.ArrayAdapterActivity;
import com.cnpeng.a_21_ListActivity.MyListActivity;
import com.cnpeng.a_22_simpleAdapter.SimpleAdapterActivity;
import com.cnpeng.a_23_autoCompleteTextView.AutoCompleteTextViewActivity;
import com.cnpeng.a_24_gridView.GridViewActivity;
import com.cnpeng.a_25_expandableListView.ExpandableListViewActivity;
import com.cnpeng.a_26_spinner.SpinnerActivity;
import com.cnpeng.a_27_AdapterViewFlipper.AdapterViewFlipperActivity;
import com.cnpeng.a_28_stackView.StackViewActivity;
import com.cnpeng.a_29_progressBar.ProgressBarActivity;
import com.cnpeng.a_30_SeekBar.SeekBarActivtiy;
import com.cnpeng.a_31_RatingBar.RatingBarActivity;
import com.cnpeng.a_32_ViewSwitcher.ViewSwitcherActivity;
import com.cnpeng.a_32_ViewSwitcher.ViewSwitcherActivity2;
import com.cnpeng.a_33_ImageSwitcher.ImageSwitcherActivity;
import com.cnpeng.a_34_textSwitcher.TextSwitcherActvity;
import com.cnpeng.a_35_viewFlipper.ViewFlipperActviity;
import com.cnpeng.a_36_Toast.ImageToastActivity;
import com.cnpeng.a_37_calendarView.CalendarViewActivity;
import com.cnpeng.a_38_DatePiackerAndTimePicker.DatePickerAndTimerPickerActivity;
import com.cnpeng.a_39_DatePickerDialogAndTimerPickerDialg.DatePickerDialogAndTimerPickerDialogActvitiy;
import com.cnpeng.a_40_NumberPicker.NumberPickerActivity;
import com.cnpeng.a_41_SearchView.SearchViewActivtiy;
import com.cnpeng.a_42_tabHost.TabHostActivity;
import com.cnpeng.a_43_scrollView.ScrollViewActivity;
import com.cnpeng.a_44_notification.NotificationActivity;
import com.cnpeng.a_45_alertDialog.AlertDialogActivity;
import com.cnpeng.b_01_spannableString_ImageSpan.SpannableStringAndImageSpanActivity;
import com.cnpeng.b_02_DrawerLayoutAndImmersion.DrawerLayoutActivity;
import com.cnpeng.b_04_suspendAndListView.SuspendAndListViewActivity;
import com.cnpeng.b_04_suspendAndListView.SuspendAndListViewActivity2;
import com.cnpeng.b_05_alertDialog_dismiss.DissmissAlertDialog;
import com.cnpeng.b_06_ObjectAnimator.ObjectAnimatorAndListViewActivity;
import com.cnpeng.b_07_bottomNavigationView.BottomNavigationViewActivity;
import com.cnpeng.b_08_suspendRv1.SuspendRvActivity1;
import com.cnpeng.b_09_TextureView.TextureViewActivity;
import com.cnpeng.b_10_AndroidDrawables.CustomDrawableActivity;
import com.cnpeng.b_10_AndroidDrawables.TransitionDrawableActivity;
import com.cnpeng.b_11_AddAppToSysShare.AddAppToSysShareActivity;
import com.cnpeng.b_12_customCalendar.CustomCalendarActivity;
import com.cnpeng.b_13_customUserAgent.CustomUserAgentActivity;
import com.cnpeng.b_14_screenShotInApp.ScreenShotActivity;
import com.cnpeng.b_15_cornerAlertDialog.CornerAlertDialogActivity;
import com.cnpeng.b_16_customDialog.CustomDialogActivity;
import com.cnpeng.b_17_shake.ShakeActivity;
import com.cnpeng.b_18_customSwitchButton.CustomSwitchButtonActivity;
import com.cnpeng.b_19_vectorTest.VectorDrawableActivity;
import com.cnpeng.b_20_shortCut.ShortCutActivity;
import com.cnpeng.b_21_listViewLocalRefresh.ListViewLocalRefreshActivity;
import com.cnpeng.b_23_RefreshFragmentInViewPager.RefreshFmInVpActivity;
import com.cnpeng.b_24_showH5TextInTextView.ShowH5TextActivity;
import com.cnpeng.b_25_clickSpanAndItemClick.ClickSpanAndItemClickActivity;
import com.cnpeng.b_26_TakePhotoWithSysMethod.TakePhotoActivity;
import com.cnpeng.b_27_suspendRv.SuspendRvActivity2;
import com.cnpeng.b_28_collspaningToolBar.CollapsingToolBarActivity;
import com.cnpeng.b_30_RichTextEditor.RichTextEditorActivity;
import com.cnpeng.b_31_picAndVideoSelector.PicSelectorActivity;
import com.cnpeng.b_32_largeImage.LargeImageViewActivity;
import com.cnpeng.b_33_BaseRvAdapter.BaseRvAdapterActivity;
import com.cnpeng.b_34_checkNotify.CheckNotifyActivity;
import com.cnpeng.b_35_rippleDrawable.RippleDrawableActivity;
import com.cnpeng.c_01_customView.CustomViewActivity;
import com.cnpeng.cnpeng_demos2017_01.R;
import com.cnpeng.cnpeng_demos2017_01.databinding.ActivityMainBinding;
import com.cnpeng.utils.CommonUtils;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityMainBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        binding.btTempActivity.setOnClickListener(this);
        binding.btWebviewTest01.setOnClickListener(this);
        binding.btViewStub02.setOnClickListener(this);
        binding.btWithoutXML.setOnClickListener(this);
        binding.btImageBrowserV1.setOnClickListener(this);
        binding.btScrollBall.setOnClickListener(this);
        binding.btTableLayout.setOnClickListener(this);
        binding.btNeonLamp.setOnClickListener(this);
        binding.btPlumBlossom.setOnClickListener(this);
        binding.btCaculater.setOnClickListener(this);
        binding.btTextView01.setOnClickListener(this);
        binding.bt9Patch.setOnClickListener(this);
        binding.btRBAndCB.setOnClickListener(this);
        binding.btTbAndSwitch.setOnClickListener(this);
        binding.btTcAndAc.setOnClickListener(this);
        binding.btChronometer.setOnClickListener(this);
        binding.btImageBrowserV2.setOnClickListener(this);
//        binding.btImageButtonAndZoomButton.setOnClickListener(this);
        binding.btQucikContactBadge.setOnClickListener(this);
        binding.btListView01.setOnClickListener(this);
        binding.btArrayAdapter01.setOnClickListener(this);
        binding.btListActivity.setOnClickListener(this);
        binding.btSimpleAdapterActivity.setOnClickListener(this);
        binding.btAutoCompleteTextView.setOnClickListener(this);
        binding.btGridView.setOnClickListener(this);
        binding.btExpandableListView.setOnClickListener(this);
        binding.btSpinner.setOnClickListener(this);
        binding.btAdapterViewFlipperActivity.setOnClickListener(this);
        binding.btStackView.setOnClickListener(this);
        binding.btProgressBar.setOnClickListener(this);
        binding.btProgressBar2.setOnClickListener(this);
        binding.btRatingBar.setOnClickListener(this);
        binding.btViewSwitcher.setOnClickListener(this);
        binding.btViewSwitcher2.setOnClickListener(this);
        binding.btImageSwitcher.setOnClickListener(this);
        binding.btImageSpanAndSpannableString.setOnClickListener(this);
        binding.btTextSwitcher.setOnClickListener(this);
        binding.btViewFlipper.setOnClickListener(this);
        binding.btImageToast.setOnClickListener(this);
        binding.btCalendarView.setOnClickListener(this);
        binding.btCalendarPicker.setOnClickListener(this);
        binding.btDatePickerDialog.setOnClickListener(this);
        binding.btNumberPicker.setOnClickListener(this);
        binding.btSearchView.setOnClickListener(this);
        binding.btDrawerLayout.setOnClickListener(this);
        binding.btTabhost.setOnClickListener(this);
        binding.btScrollview.setOnClickListener(this);
        binding.btNotification.setOnClickListener(this);
        binding.btSuspendAndLv.setOnClickListener(this);
        binding.btAlertDialog.setOnClickListener(this);
        binding.btSuspendAndLv2.setOnClickListener(this);
        binding.btDismissAlertDialog.setOnClickListener(this);
        binding.btObjectAnimator.setOnClickListener(this);
        binding.btBottomNavigationView.setOnClickListener(this);
        binding.btClTbVpRV.setOnClickListener(this);
        binding.btTextureView.setOnClickListener(this);
        binding.btTransitionDrawable.setOnClickListener(this);
        binding.btTransitionDrawable2.setOnClickListener(this);
        binding.btAddAppToSysShare.setOnClickListener(this);
        binding.btCustomCalendar.setOnClickListener(this);
        binding.btCustomUserAgent.setOnClickListener(this);
        binding.btScreenShotInAPP.setOnClickListener(this);
        binding.btCornerAlertDialog.setOnClickListener(this);
        binding.btCustomDialog.setOnClickListener(this);
        binding.btShake.setOnClickListener(this);
//        binding.btCustomSwitchButton.setOnClickListener(this);
        binding.btVector.setOnClickListener(this);
        binding.btShortCut.setOnClickListener(this);
        binding.btLvLoacalRefresh.setOnClickListener(this);
        binding.btCustomView.setOnClickListener(this);
        binding.btRefreshFmInVp.setOnClickListener(this);
        binding.btShowComplexH5Text.setOnClickListener(this);
        binding.btClickSpanAndItemClick.setOnClickListener(this);
        binding.btTakePhoto.setOnClickListener(this);
        binding.btSuspendRv.setOnClickListener(this);
        binding.btCollapsing.setOnClickListener(this);
        binding.btRichText.setOnClickListener(this);
        binding.btPicSelector.setOnClickListener(this);
        binding.btLargeImg.setOnClickListener(this);
        binding.btRvAdapter.setOnClickListener(this);
        binding.btCheckNotify.setOnClickListener(this);
        binding.btRipple.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_checkNotify:
                CommonUtils.mStartActivity(this, CheckNotifyActivity.class);
                break;
            case R.id.bt_rvAdapter:
                CommonUtils.mStartActivity(this, BaseRvAdapterActivity.class);
                break;
            case R.id.bt_tempActivity:
                CommonUtils.mStartActivity(this, TempActivity.class);
                break;
            case R.id.bt_webview_Test01:
                CommonUtils.mStartActivity(this, WebViewTestActivity01.class);
                break;
            case R.id.bt_viewStub_02:
                CommonUtils.mStartActivity(this, ViewStubTestActivitiy.class);
                break;
            case R.id.bt_withoutXML:
                CommonUtils.mStartActivity(this, CreateViewWithoutXMLActivity.class);
                break;
            case R.id.bt_ImageBrowserV1:
                CommonUtils.mStartActivity(this, ImageBrowserActivityV1.class);
                break;
            case R.id.bt_ScrollBall:
                CommonUtils.mStartActivity(this, ScrollBallActivity.class);
                break;
            case R.id.bt_TableLayout:
                CommonUtils.mStartActivity(this, TableLayoutTestActivity.class);
                break;
            case R.id.bt_neonLamp:
                CommonUtils.mStartActivity(this, NeonLampActivity.class);
                break;
            case R.id.bt_plumBlossom:
                CommonUtils.mStartActivity(this, PlumBlossomActivity.class);
                break;
            case R.id.bt_caculater:
                CommonUtils.mStartActivity(this, CaculaterActivity.class);
                break;
            case R.id.bt_textView01:
                CommonUtils.mStartActivity(this, TextView01Activtiy.class);
                break;
            case R.id.bt_9Patch:
                CommonUtils.mStartActivity(this, NinePatchActivity.class);
                break;
            case R.id.bt_RBAndCB:
                CommonUtils.mStartActivity(this, RadioButtonAndCheckBoxActivity.class);
                break;
            case R.id.bt_TbAndSwitch:
                CommonUtils.mStartActivity(this, TBAndSwitchActivity.class);
                break;
            case R.id.bt_TcAndAc:
                CommonUtils.mStartActivity(this, TcAndAcActivity.class);
                break;
            case R.id.bt_chronometer:
                CommonUtils.mStartActivity(this, ChronometerActivity.class);
                break;
            case R.id.bt_ImageBrowserV2:
                CommonUtils.mStartActivity(this, ImageBrowserV2Activtiy.class);
                break;
//            case R.id.bt_ImageButtonAndZoomButton:
//                CommonUtils.mStartActivity(this, ImageButtonAndZoomButtonActivity.class);
//                break;
            case R.id.bt_QucikContactBadge:
                CommonUtils.mStartActivity(this, QuickContactBadgeActivity.class);
                break;
            case R.id.bt_ListView01:
                CommonUtils.mStartActivity(this, ListView01Activity.class);
                break;
            case R.id.bt_ArrayAdapter01:
                CommonUtils.mStartActivity(this, ArrayAdapterActivity.class);
                break;
            case R.id.bt_ListActivity:
                CommonUtils.mStartActivity(this, MyListActivity.class);
                break;
            case R.id.bt_simpleAdapterActivity:
                CommonUtils.mStartActivity(this, SimpleAdapterActivity.class);
                break;
            case R.id.bt_autoCompleteTextView:
                CommonUtils.mStartActivity(this, AutoCompleteTextViewActivity.class);
                break;
            case R.id.bt_GridView:
                CommonUtils.mStartActivity(this, GridViewActivity.class);
                break;
            case R.id.bt_ExpandableListView:
                CommonUtils.mStartActivity(this, ExpandableListViewActivity.class);
                break;
            case R.id.bt_Spinner:
                CommonUtils.mStartActivity(this, SpinnerActivity.class);
                break;
            case R.id.bt_AdapterViewFlipperActivity:
                CommonUtils.mStartActivity(this, AdapterViewFlipperActivity.class);
                break;
            case R.id.bt_StackView:
                CommonUtils.mStartActivity(this, StackViewActivity.class);
                break;
            case R.id.bt_progressBar:
                CommonUtils.mStartActivity(this, ProgressBarActivity.class);
                break;
            case R.id.bt_progressBar2:
                CommonUtils.mStartActivity(this, SeekBarActivtiy.class);
                break;
            case R.id.bt_RatingBar:
                CommonUtils.mStartActivity(this, RatingBarActivity.class);
                break;
            case R.id.bt_ViewSwitcher:
                CommonUtils.mStartActivity(this, ViewSwitcherActivity.class);
                break;
            case R.id.bt_ViewSwitcher2:
                CommonUtils.mStartActivity(this, ViewSwitcherActivity2.class);
                break;
            case R.id.bt_ImageSwitcher:
                CommonUtils.mStartActivity(this, ImageSwitcherActivity.class);
                break;
            case R.id.bt_ImageSpanAndSpannableString:
                CommonUtils.mStartActivity(this, SpannableStringAndImageSpanActivity.class);
                break;
            case R.id.bt_TextSwitcher:
                CommonUtils.mStartActivity(this, TextSwitcherActvity.class);
                break;
            case R.id.bt_ViewFlipper:
                CommonUtils.mStartActivity(this, ViewFlipperActviity.class);
                break;
            case R.id.bt_imageToast:
                CommonUtils.mStartActivity(this, ImageToastActivity.class);
                break;
            case R.id.bt_calendarView:
                CommonUtils.mStartActivity(this, CalendarViewActivity.class);
                break;
            case R.id.bt_calendarPicker:
                CommonUtils.mStartActivity(this, DatePickerAndTimerPickerActivity.class);
                break;
            case R.id.bt_datePickerDialog:
                CommonUtils.mStartActivity(this, DatePickerDialogAndTimerPickerDialogActvitiy.class);
                break;
            case R.id.bt_NumberPicker:
                CommonUtils.mStartActivity(this, NumberPickerActivity.class);
                break;
            case R.id.bt_SearchView:
                CommonUtils.mStartActivity(this, SearchViewActivtiy.class);
                break;
            case R.id.bt_drawerLayout:
                CommonUtils.mStartActivity(this, DrawerLayoutActivity.class);
                break;
            case R.id.bt_tabhost:
                CommonUtils.mStartActivity(this, TabHostActivity.class);
                break;
            case R.id.bt_scrollview:
                CommonUtils.mStartActivity(this, ScrollViewActivity.class);
                break;
            case R.id.bt_notification:
                CommonUtils.mStartActivity(this, NotificationActivity.class);
                break;
            case R.id.bt_suspendAndLv:
                CommonUtils.mStartActivity(this, SuspendAndListViewActivity.class);
                break;
            case R.id.bt_alertDialog:
                CommonUtils.mStartActivity(this, AlertDialogActivity.class);
                break;
            case R.id.bt_suspendAndLv2:
                CommonUtils.mStartActivity(this, SuspendAndListViewActivity2.class);
                break;
            case R.id.bt_dismissAlertDialog:
                CommonUtils.mStartActivity(this, DissmissAlertDialog.class);
                break;
            case R.id.bt_objectAnimator:
                CommonUtils.mStartActivity(this, ObjectAnimatorAndListViewActivity.class);
                break;
            case R.id.bt_bottomNavigationView:
                CommonUtils.mStartActivity(this, BottomNavigationViewActivity.class);
                break;
            case R.id.bt_ClTbVpRV:
                CommonUtils.mStartActivity(this, SuspendRvActivity1.class);
                break;
            case R.id.bt_textureView:
                CommonUtils.mStartActivity(this, TextureViewActivity.class);
                break;
            case R.id.bt_TransitionDrawable:
                CommonUtils.mStartActivity(this, TransitionDrawableActivity.class);
                break;
            case R.id.bt_TransitionDrawable2:
                CommonUtils.mStartActivity(this, CustomDrawableActivity.class);
                break;
            case R.id.bt_AddAppToSysShare:
                CommonUtils.mStartActivity(this, AddAppToSysShareActivity.class);
                break;
            case R.id.bt_CustomCalendar:
                CommonUtils.mStartActivity(this, CustomCalendarActivity.class);
                break;
            case R.id.bt_CustomUserAgent:
                CommonUtils.mStartActivity(this, CustomUserAgentActivity.class);
                break;
            case R.id.bt_ScreenShotInAPP:
                CommonUtils.mStartActivity(this, ScreenShotActivity.class);
                break;
            case R.id.bt_cornerAlertDialog:
                CommonUtils.mStartActivity(this, CornerAlertDialogActivity.class);
                break;
            case R.id.bt_customDialog:
                CommonUtils.mStartActivity(this, CustomDialogActivity.class);
                break;
            case R.id.bt_CustomView:
                CommonUtils.mStartActivity(this, CustomViewActivity.class);
                break;
//            case R.id.bt_customSwitchButton:
//                CommonUtils.mStartActivity(this, CustomSwitchButtonActivity.class);
//                break;

            case R.id.bt_shake:
                CommonUtils.mStartActivity(this, ShakeActivity.class);
                break;
            case R.id.bt_vector:
                CommonUtils.mStartActivity(this, VectorDrawableActivity.class);
                break;
            case R.id.bt_shortCut:
                CommonUtils.mStartActivity(this, ShortCutActivity.class);
                break;
            case R.id.bt_lvLoacalRefresh:
                CommonUtils.mStartActivity(this, ListViewLocalRefreshActivity.class);
                break;
            case R.id.bt_RefreshFmInVp:
                CommonUtils.mStartActivity(this, RefreshFmInVpActivity.class);
                break;
            case R.id.bt_takePhoto:
                CommonUtils.mStartActivity(this, TakePhotoActivity.class);
                break;
            case R.id.bt_showComplexH5Text:
                CommonUtils.mStartActivity(this, ShowH5TextActivity.class);
                break;
            case R.id.bt_clickSpanAndItemClick:
                CommonUtils.mStartActivity(this, ClickSpanAndItemClickActivity.class);
                break;
            case R.id.bt_suspendRv:
                CommonUtils.mStartActivity(this, SuspendRvActivity2.class);
                break;
            case R.id.bt_collapsing:
                CommonUtils.mStartActivity(this, CollapsingToolBarActivity.class);
                break;
            case R.id.bt_richText:
                CommonUtils.mStartActivity(this, RichTextEditorActivity.class);
                break;
            case R.id.bt_picSelector:
                CommonUtils.mStartActivity(this, PicSelectorActivity.class);
                break;

            case R.id.bt_largeImg:
                CommonUtils.mStartActivity(this, LargeImageViewActivity.class);
                break;
            case R.id.bt_ripple:
                CommonUtils.mStartActivity(this, RippleDrawableActivity.class);
                break;
            default:
                break;
        }
    }
}
