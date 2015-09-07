package com.orm.commons.utils;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

import java.io.IOException;

@SuppressWarnings("serial")
public class TabInclude extends TagSupport{

    private String extendsFeild;
    public String getExtendsFeild() {
        return extendsFeild;
    }

    public void setExtendsFeild(String extendsFeild) {
        this.extendsFeild = extendsFeild;
    }

    @Override
    public int doStartTag() throws JspException {
        StringBuffer sb = new StringBuffer();
        if (extendsFeild.equals("body")){
            sb.append("<body>aaaaaaaaaaaaaaaaaaaa</body>");
        }
        try {
            pageContext.getOut().println(sb.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return EVAL_PAGE;
    }
}
