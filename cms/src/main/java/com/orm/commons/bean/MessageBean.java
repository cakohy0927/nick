package com.orm.commons.bean;

import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

/**
 * 在spring中配置参数
 * Created by huangyuan on 2015/2/10.
 */
public class MessageBean {

    private List<String> citys;
    private Set<String> friends;
    private Map<Integer, String> books;
    private Properties props;

    public List<String> getCitys() {
        return citys;
    }

    public void setCitys(List<String> citys) {
        this.citys = citys;
    }

    public Set<String> getFriends() {
        return friends;
    }

    public void setFriends(Set<String> friends) {
        this.friends = friends;
    }

    public Map<Integer, String> getBooks() {
        return books;
    }

    public void setBooks(Map<Integer, String> books) {
        this.books = books;
    }

    public Properties getProps() {
        return props;
    }

    public void setProps(Properties props) {
        this.props = props;
    }

    public void show() {
        System.out.println("----------");
        for (String str : citys) {
            System.out.println(str);
        }
        System.out.println("----------");
        for (String str : friends) {
            System.out.println(str);
        }
        System.out.println("---------");
        Set<Integer> keys = books.keySet();
        for (Integer key : keys) {
            System.out.println(key + " " + books.get(key));
        }
        System.out.println("---------");
        Set<Object> params = props.keySet();
        for (Object obj : params) {
            System.out.println(obj + " : "
                    + props.getProperty(obj.toString()));
        }
    }
}
