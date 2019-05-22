package com.cnpeng.b_21_listViewLocalRefresh;

/**
 * 作者：CnPeng
 * <p>
 * 时间：2017/8/24:下午8:24
 * <p>
 * 说明：局部刷新使用的条目bean类
 */

class User {
    private String name;
    private int    id;

    public User(String name, int id) {
        this.name = name;
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}

