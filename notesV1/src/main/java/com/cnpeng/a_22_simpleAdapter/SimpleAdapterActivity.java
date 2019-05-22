package com.cnpeng.a_22_simpleAdapter;

import android.os.Bundle;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import androidx.appcompat.app.AppCompatActivity;

import com.cnpeng.cnpeng_demos2017_01.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by CnPeng on 2017/1/19.
 * <p>
 * 使用SimpleAdapter展示内容
 */

public class SimpleAdapterActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_simpleadapter);

        String[] names = new String[]{"张三", "李四", "王五", "赵六", "谁"};
        String[] descs = new String[]{"祖上名人张飞", "祖上名人比较多", "钻石王老五💎", "赵四他弟？", "你到底是谁？"};
        int[] icons = new int[]{R.drawable.milaoshu, R.drawable.pikaqiu, R.drawable.xiaopohai, R.drawable
                .daomeixiong, R.drawable.gongfuxiongmao};

        List<Map<String, Object>> students = new ArrayList<>();   //全部人员

        for (int i = 0; i < names.length; i++) {
            //封装每一个对象的信息到map
            Map<String, Object> student = new HashMap<String, Object>();
            student.put("name", names[i]);
            student.put("desc", descs[i]);
            student.put("icon", icons[i]);

            //将封装了每一个个体信息的map存进list集合中
            students.add(student);
        }

        //创建适配器
        SimpleAdapter simpleAdapter = new SimpleAdapter(this, students, R.layout.item_simpleadapter, new 
                String[]{"name", "desc", "icon"}, new int[]{R.id.tv_name, R.id.tv_desc, R.id.iv_icon});

        ListView listView = (ListView) findViewById(R.id.lv_simpleAdapter);
        listView.setAdapter(simpleAdapter);
    }
}
