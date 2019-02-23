package ecnu.cs.tibang.bean;

import cn.bmob.v3.BmobObject;

public class Question extends BmobObject {
    private String title;//标题
    private String describe;//描述
    private String phone;//联系手机
    private String url;
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public String getDescribe() {
        return describe;
    }
    public void setDescribe(String describe) {
        this.describe = describe;
    }
    public String getPhone() {
        return phone;
    }
    public void setPhone(String phone) {
        this.phone = phone;
    }
    public String getUrl() {
        return url;
    }
    public void setUrl(String url) {
        this.url = url;
    }
}
