/*jadclipse*/// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.

package com.trt.util.dj;

import java.io.Serializable;

public class NameValueBean
    implements Serializable
{

	private static final long serialVersionUID = 1L;
	public String getName()
    {
        return a;
    }

    public void setName(String s)
    {
        a = s;
    }

    public Object getValue()
    {
        return b;
    }

    public void setValue(Object obj)
    {
        b = obj;
    }

    public NameValueBean(String s, Object obj)
    {
        a = s;
        b = obj;
    }

    @Override
	public String toString()
    {
        return (new StringBuilder()).append("name=").append(a).append(" value=").append(b).toString();
    }

    private String a;
    private Object b;
}


/*
	DECOMPILATION REPORT

	Decompiled from: C:\Users\linpeng123l\Desktop\项目\卓凡项目\代码\卓凡1.2（阿里云服务器）\bjzrnjyy\WebRoot\WEB-INF\lib\aclwf.jar
	Total time: 47 ms
	Jad reported messages/errors:
	Exit status: 0
	Caught exceptions:
*/