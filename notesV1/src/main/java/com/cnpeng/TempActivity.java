package com.cnpeng;

import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.cnpeng.cnpeng_demos2017_01.R;
import com.cnpeng.cnpeng_demos2017_01.databinding.ActivityTempBinding;
import com.cnpeng.utils.LogUtils;
import com.google.gson.Gson;

import org.apache.http.NameValuePair;
import org.apache.http.client.utils.URLEncodedUtils;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import static com.cnpeng.utils.KotlinUtlisKt.isNetAvailable;


public class TempActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityTempBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_temp);

        if (isNetAvailable(this)){
            Toast.makeText(this, "调用 Kotlin 中扩展的函数判断网络是否可用", Toast.LENGTH_SHORT).show();
        }

        String str = getJsonStr();
        binding.tv.setText(str);


        try {
            URI query = new URI("ant://question/detail/open?detailID=7334&PUSHID=5b8f363a1d41c87e638a7c55");
            List<NameValuePair> list = URLEncodedUtils.parse(query, "UTF-8");
            if (null != list && !list.isEmpty()) {
                String value = list.get(list.size() - 1).getValue();
                LogUtils.d("解析出来的数据是", value);
            }
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }

    public String getJsonStr() {
        String str = "";
        List<Product> productList = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            productList.add(new Product("ID_00" + i, i));
        }

        Gson gson = new Gson();
        str = gson.toJson(productList);
        LogUtils.i("Json字符串", str);
        return str;
    }

    class Product {
        String id;
        int    num;

        public Product(String id, int num) {
            this.id = id;
            this.num = num;
        }
    }

}