package com.cnpeng.b_24_showH5TextInTextView;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Html;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.cnpeng.cnpeng_demos2017_01.R;
import com.cnpeng.cnpeng_demos2017_01.databinding.ActivityShowh5textBinding;
import com.cnpeng.utils.LogUtils;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.InputStream;
import java.net.URL;
import java.util.HashSet;

/**
 * 作者：CnPeng
 * <p>
 * 时间：2017/10/20:下午3:47
 * <p>
 * 说明：在TextView中展示H5文本，在H5中关键字标红，其他文本不设置字体。在TextView中需要给关键字增加点击事件，同时TextView中还需要展示出H5中指定的图片
 * <p>
 * ——使用了数据绑定
 * ——在解析这个 H5 文本串时使用的是 jsoup 库。
 * ——虽然TextView本身具有滚动属性，但是在不同手机上表现不一样：华为Che1-L20上滑动不流畅，魅族m3-note上滑动后会自动回到顶部。所以用ScrollView 包裹
 * ——jsoup中没有找到关于根据TAG和节点文本获取属性值的方法，所以无法通过代码去获取font节点中的属性值。（确实有必要的话可以考虑自己解析h5文本）
 * ——使用线程是为了保证图片能加载处理，加载图片是耗时操作，不用子线程的话图片可能会加载不出来
 */

public class ShowH5TextActivity extends AppCompatActivity {
    String H5String = "<html>\n" + " <head></head>\n" + " <body>\n" + "  <p style=\"text-indent: 2em;\"><span " + 
            "style=\"font-family: 宋体, SimSun; font-size: 16px;\">周三下午公布的<font " + 
            "color=\"#FF0000\">英国</font>5月失业率、英国5月失业金申请人数、英国4月三个月ILO失业率显示，英国4月三个月剔除红利的平均工资年率刷新2015年1月以来新低。英国2-4月连续3" 
            + "个月失业率为1975年以来最低，英国就业市场连续3" + 
            "个月保持稳健，但薪资增速进一步放缓，料将对内需产生负面影响，为英国经济增长预期增添担忧情绪。英国国家统计局表示薪资数据将改善小型企业的薪资策略，对薪资水平产生下行影响。</span></p>\n" + "  " +
            "" + "" + "" + "" + "" + "" + "" + "" + "" + "" + "<p><br " + "/></p>\n" + "  " + "<p" + "" + " " + 
            "style=\"text-indent: " + "2em;" + "\"><span " + "" + "" + "" + "style=\"font-family:" + " " + "宋体, " + 
            "" + "SimSun; " + "font-size: " + "16px;" + "\">英国前首相<font " + 
            "color=\"#FF000\">卡梅伦</font>表示现任首相特雷莎&middot;" + 
            "梅应采取“软脱欧”，并表示她应该与工党等反对派进行进一步交涉，与各党派进行更广泛的磋商以达成更多共识。认为“软脱欧”或许会面临更大压力，并表示议会现在应尽快面对这个问题。同时<font " + 
            "color=\"#FF000\">卡梅伦</font>还对特蕾莎&middot;梅表示了支持。</span></p>\n" + "  <p><br /></p>\n" + "  <p " + 
            "style=\"text-indent: 2em;\"><span style=\"font-family: 宋体, SimSun; font-size: 16px;" + 
            "\">据华尔街日报，MacroPolicy" + " Perspectives " + "LLC调查显示约50%的受访者表示，股市没有对美联储的计划做出反应，42" + 
            "%的受访者认为信用债市场也没有做出反应。几乎没有受访者认为美联储的计划在任何市场得到了充分的消化。这表明如果美联储在启动这项计划前没有与市场有效沟通，将可能引发不利的市场变动。</span></p>\n" +
            "  <p><br /></p>\n" + "  <p style=\"text-indent: 2em;\"><span style=\"font-family: 宋体, SimSun; " + 
            "font-size:" + " 16px;\">北京时间本周四凌晨2点美联储将公布最新利率决议及<font " + 
            "color=\"#FF0000\">政策声明</font>，预计美元在经历会议后将面临走软风险；市场广泛预期本次会议将加息25个基点至1.00%-1.25%；然而，FOMC有可能在此次声明中降低核心PCE" 
            + "通胀预期，长期联邦利率中值预期也有降低的可能性，这将对加息造成压力；此外，预计本次会议将对缩减资产负债表计划有所置评。</span></p>\n" + "  <p><br /></p>\n" + "  "
            + "<p " + "style=\"text-indent: 2em;\"><img src=\"http://www.gfxa" + "" + "" + "" + "" + "" + "" + "" + 
            "" + ".com/upload/image/20170614/6363305821523119573565195.png\" title=\"\" /></p>\n" + "  <p " + 
            "style=\"text-indent: 2em;\"><span style=\"font-family: 宋体, SimSun; font-size: 16px;" + 
            "\">支撑：1260——1255——1247 阻力：1273——1281</span></p>\n" + "  <p style=\"text-indent: 2em;\"><span " + 
            "style=\"font-family: 宋体, SimSun; font-size: 16px;\">交易策略：现货黄金现价1268.30，日内交易建议如下：</span></p>\n" + "  <p "
            + "style=\"text-indent: 2em;\"><span style=\"font-family: 宋体, SimSun; font-size: 16px;\">A：北京时间22:00之前," 
            + "现货黄金上行至1274附近时四十分之一仓位做空，止损设1279，目标下看至1268/1265区间止盈。持仓阶段，现货黄金下破1271后，建议将止损位下移至1274附近。持仓阶段，浮盈大于6" + 
            "美金时建议随机止盈。鉴于美联储利率决议影响的不确定性，此交易如触发，北京时间6月15日01:00之前建议择机离场。</span></p>\n" + "  <p style=\"text-indent: " +
            "2em;\"><span style=\"font-family: 宋体, SimSun; font-size: 16px;\">B：北京时间6月15日07:00之前," + 
            "现货黄金下行至1260和1253附近时分别以五十分之一仓位做多，止损统一设1245，目标依次上看1268—1273—1281—1288附近。持仓阶段，浮盈大于5" + 
            "美金时，建议将止损位上移至成本位。持仓阶段，浮盈大于25美金时建议随机止盈。</span></p>\n" + "  <p><br /></p>\n" + " <p style=\"text-indent: "
            + "2em;\"><span style=\"font-family: 宋体, SimSun; font-size: 16px;\">美元兑日元</span></p>\n" + "  <p " + 
            "style=\"text-indent: 2em;\"><span style=\"font-family: 宋体, SimSun; font-size: 16px;" + 
            "\">美元兑日元</span></p>\n" + "  <p style=\"text-indent: 2em;\"><img src=\"http://www.gfxa" + "" + "" + "" + 
            ".com/upload/image/20170614/6363305822435631122386267.png\" title=\"\" /></p>\n" + "  <p " + 
            "style=\"text-indent: 2em;\"><span style=\"font-family: 宋体, SimSun; font-size: 16px;\">支撑109.90 " + 
            "阻力110.40-110.80</span></p>\n" + "  <p style=\"text-indent: 2em;\"><span style=\"font-family: 宋体, SimSun;" +
            "" + "" + "" + "" + "" + "" + "" + "" + "" + "" + "" + "" + "" + "" + "" + "" + "" + "" + "" + "" + "" + 
            "" + "" + "" + "" + "" + "" + "" + "" + "" + "" + "" + "" + "" + "" + "" + "" + "" + "" + "" + "" + "" + 
            "" + "" + "" + "" + "" + "" + "" + "" + "" + "" + " " + "font-size: " + "16px;" + 
            "\">交易策略：美元兑日元，现价报111.20。明日凌晨有美联储议息会议，注意风险。加息概率极高，但是美元走势依旧疲软，不排除出现美元空头回补的现象。日内交易建议如下：</span" + "></p" + 
            "" + ">\n" + "" + "  " + "" + "" + "" + "" + "" + "" + "" + "" + "" + "" + "" + "" + "" + "" + "" + "" + 
            "<p " + "style=\"text-indent: " + "2em;" + "\"><span " + "style=\"font-family: " + "宋体, " + "SimSun;" + 
            "" + " " + "font-size: " + "16px;" + "\">A： " + "突破110.40做多，止损110.30，止盈110.77</span></p>\n" + "  " + "<p " +
            "" + "" + "" + "" + "" + "" + "" + "" + "" + "" + "" + "" + "" + "" + "style=\"text-indent: 2em;" + 
            "\"><span " + "" + "style=\"font-family:" + " 宋体, " + "" + "SimSun; " + "font-size: " + "16px;" + "\">B： " +
            "" + "" + "" + "" + "" + "" + "" + "" + "" + "" + "" + "" + "" + "" + "" + "" + "" + "" + "" + "" + "" + 
            "" + "应对加息:限价卖出挂单于110.80，止损111.20，止盈110.40</span></p>\n" + "" + "  " + "<p><br " + "/></p>\n" + " " + " "
            + "<p " + "style=\"text-indent: " + "2em;\"><span " + "style=\"font-family: 宋体, " + "SimSun; " + 
            "font-size: 16px;" + "\">英镑兑美元</span></p>\n" + "  <p " + "style=\"text-indent: 2em;" + "\"><img" + " " + 
            "src=\"http://www.gfxa" + "" + "" + "" + "" + "" + "" + "" + "" + "" + "" + "" + "" + "" + "" + "" + "" +
            "" + "" + "" + "" + "" + "" + "" + "" + ".com/upload/image/20170614/6363305823223140971492124.png\" " + 
            "title=\"\" " + "/><span" + " " + "style=\"font-family: 宋体, " + "SimSun; font-size: 16px;\">&nbsp; &nbsp;" +
            "" + "" + "" + "" + "" + "" + "" + "" + " &nbsp; &nbsp;" + " " + "&nbsp; &nbsp;" + " " + "" + "" + "" + 
            "" + "" + "" + "" + "" + "" + "" + "" + "" + "" + "" + "" + "&nbsp; &nbsp; &nbsp; &nbsp; " + "&nbsp;" + "" +
            " " + "&nbsp; " + "&nbsp; " + "" + "" + "&nbsp; " + "&nbsp; " + "&nbsp;" + " " + "&nbsp; " + "&nbsp; " + 
            "" + "&nbsp;" + "</span></p>\n" + "" + "" + "  <p " + "" + "style=\"text-indent: 2em;" + "\"><span" + " "
            + "style=\"font-family: " + "宋体, " + "" + "SimSun; " + "font-size: " + "16px;" + "\">支撑1.2673-1.2600 " + 
            "阻力1.1287-1.2828</span></p>\n" + "  " + "<p" + " " + "style=\"text-indent:" + " " + "" + "" + "" + "" + 
            "" + "" + "" + "" + "" + "" + "" + "" + "" + "2em;" + "\"><span " + "" + "" + "style=\"font-family: " + 
            "宋体, " + "SimSun;" + " " + "font-size: " + "" + "16px;" + 
            "\">交易策略：如图欧元兑美元四小时图所示，现价报1.2753，欧元轴心点为1.2714，中枢区间为1.2698—1.2730" + "，日内交易建议如下：</span></p>\n" + "  " + 
            "<p " + "style=\"text-indent: 2em;\"><span style=\"font-family: 宋体, " + "SimSun; font-size: " + "16px;" +
            "\">A：建议1.2730卖出英镑对美元，止损1.2787，止盈1.2673.</span></p>\n" + "  <p " + "style=\"text-indent: 2em;" + 
            "\"><span " + "style=\"font-family: 宋体, SimSun; font-size: 16px;" + 
            "\">（该建议以10000美金下0.5手为基准，参照可自行换算。请投资者控制好仓位，严格止损。）</span></p>\n" + "  <p><br /></p>\n" + " </body>\n" + 
            "</html>";

    private ActivityShowh5textBinding binding;
    private String                    tempSplitedStr;       //临时切割得到的字符串

    private final int FLAG_CONVERT_H5TEXT_OVER = 1;  //将H5转换成spanableString 完毕
    private final int MODE_INTRINSIC           = 0x001; //根据图片的原始大小进行展示
    private final int MODE_BASE_WINDOW_WITH    = 0x002; //与屏幕等宽（保持宽高比）

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (null != msg && msg.what == FLAG_CONVERT_H5TEXT_OVER) {
                binding.tvShowComplexH5Text.setText((SpannableString) msg.obj);
                //设置该句使文本的超连接起作用,不设置该句代码，点击事件不生效！！！
                binding.tvShowComplexH5Text.setMovementMethod(LinkMovementMethod.getInstance());
            }
        }
    };


    @Override
    public void onCreate(
            @Nullable
                    Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_showh5text);
        getAndSetStrToTextView();
    }

    /**
     * 获取并设置字符串到TextView
     */
    private void getAndSetStrToTextView() {
        final HashSet<String> keyWordsSet = getAllKeyWords();

        new Thread(new Runnable() {     //之所以放在线程中完成H5转 SpannableStirng ，是为了加载H5的图片
            @Override
            public void run() {
                Spanned normalStr = convertH5TextToSpanned();
                SpannableString spannableStr = new SpannableString(normalStr);  //最终要展示的字符串

                tempSplitedStr = spannableStr.toString();      //全局变量，赋初值

                for (String keyStr : keyWordsSet) { //为所有关键字增加点击事件
                    findKeyAndSetEvent(spannableStr, tempSplitedStr, keyStr, 0);
                }

                Message msg = handler.obtainMessage();
                msg.what = FLAG_CONVERT_H5TEXT_OVER;
                msg.obj = spannableStr;
                handler.sendMessage(msg);
            }
        }).start();
    }


    /**
     * 将H5字符串转换成Spanned字符串保证图片的显示。
     */
    private Spanned convertH5TextToSpanned() {
        return Html.fromHtml(H5String, new Html.ImageGetter() {
            @Override
            public Drawable getDrawable(String url) {
                InputStream is;
                try {
                    is = (InputStream) new URL(url).getContent();
                    Drawable d = Drawable.createFromStream(is, "src");

                    setDrawableBounds(d, MODE_BASE_WINDOW_WITH);  //设置图片区域

                    is.close();
                    return d;
                } catch (Exception e) {
                    return null;
                }
            }
        }, null);
    }

    /**
     * 设置图片的区域，必须设置，否则图片不展示
     *
     * @param d                图片对象
     * @param withOrHeightMode 宽高模式
     */
    private void setDrawableBounds(Drawable d, int withOrHeightMode) {
        switch (withOrHeightMode) {
            case MODE_INTRINSIC:    //根据原图大小进行展示
                d.setBounds(0, 0, d.getIntrinsicWidth(), d.getIntrinsicHeight());
                break;
            case MODE_BASE_WINDOW_WITH: //与屏幕等宽
                WindowManager wm = getWindowManager();
                int wmWidth = wm.getDefaultDisplay().getWidth();
                int picWidth = d.getIntrinsicWidth();
                int picHeight = d.getIntrinsicHeight();

                picHeight = (int) (picHeight * (wmWidth / picWidth * 1.0));

                d.setBounds(0, 0, wmWidth, picHeight);
        }
    }

    /**
     * 找出单个关键字每一次出现的位置并为其增加点击事件
     *
     * @param tempSplitedStr 被切割后的新字符串
     * @param keyStr         关键字
     * @param preEndIndex    关键词上一次出现时的结束索引/关键字本次在原始字符串中的结束索引
     */
    private void findKeyAndSetEvent(SpannableString spannableString, String tempSplitedStr, final String keyStr,
                                    int preEndIndex) {
        final int startIndex = tempSplitedStr.indexOf(keyStr);     //起始索引
        if (startIndex != -1) {
            final int endIndex = startIndex + keyStr.length() - 1;    //终止索引,
            int startIndexInOgirinal = 0;

            if (preEndIndex == 0) {    //关键字第一次出现
                startIndexInOgirinal = startIndex;
                preEndIndex = endIndex;
            } else {      //关键字不是第一次出现
                startIndexInOgirinal = startIndex + preEndIndex + 1;    //加1 是因为截取的字符串索引又是从0开始
                preEndIndex = startIndexInOgirinal + keyStr.length() - 1;   //减1 是因为起始索引已经占了一个索引
            }

            LogUtils.e("在临时字符串中的位置：", startIndex + "/" + endIndex);
            LogUtils.e("原始字符串中的位置：", startIndexInOgirinal + "/" + preEndIndex);

            spannableString.setSpan(new ClickableSpan() {
                @Override
                public void onClick(View widget) {
                    //点击事件弹窗+请求服务器数据
                    Toast.makeText(ShowH5TextActivity.this, "点我干嘛？关键字：" + keyStr, Toast.LENGTH_SHORT).show();
                }

                @Override
                public void updateDrawState(TextPaint ds) {
                    //super.updateDrawState(ds);
                    ds.setColor(Color.RED);      //更改超链接颜色(此颜色要与H5中关键字的 font 颜色一致)
                    ds.setUnderlineText(false);     //不展示下划线
                }
            }, startIndexInOgirinal, preEndIndex + 1, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
            //}, startIndexInOgirinal, preEndIndex, Spanned.SPAN_INCLUSIVE_INCLUSIVE);  //这样的话，非第一次出现的只会将第一个字符加上超链接

            tempSplitedStr = tempSplitedStr.substring(endIndex + 1);  //截取字符串，+1 表示从关键词后面截取，不含关键字；不加1 的话从关键词最后一个字开始截取
            findKeyAndSetEvent(spannableString, tempSplitedStr, keyStr, preEndIndex);     //递归调用
        }
    }

    /**
     * 获取关键字，并使用Set存储，实现去重
     */
    private HashSet<String> getAllKeyWords() {
        HashSet<String> keysSet = new HashSet<>();
        Document document = Jsoup.parse(H5String);
        Elements elementsList = document.getElementsByTag("font"); //在JSOUP中，Elements类继承自ArrayList

        if (null != elementsList) {
            for (Element element : elementsList) {
                keysSet.add(element.text());
            }
        }

        return keysSet;
    }
}
