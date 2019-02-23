package ecnu.cs.tibang.bean;

import cn.bmob.v3.BmobUser;

public class User extends BmobUser {

    private Integer money;

    public Integer getMoney() {
        return money;
    }

    public void setMoney(Integer money) {
        this.money = money;
    }

}
