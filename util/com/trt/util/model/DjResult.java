/*jadclipse*/// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.

package com.trt.util.model;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class DjResult
{


	private String djdymc;
	private String dy_hzxml;
	private String dy_mxxml;
	private String dy_mx2xml;
	private int state;
	private long reid;
	private String bh;
	private String urlget;
	private String urllist;
	private String urlnew;
	private String urlprt;
	private String restr1;
	private String restr2;
	private int reint1;
	private int reint2;
	private double redouble1;
	private double redouble2;
	private Timestamp retime1;
	private List<?> rels;
	private List<String> infolist;
	private boolean newable;
	private boolean printable;
	private boolean allowedit;
	public DjResult(){
		state = 0;
		reid = 0L;
		bh = "";
		urlget = "";
		urllist = "";
		urlnew = "";
		urlprt = "";
		restr1 = "";
		restr2 = "";
		reint1 = 0;
		reint2 = 0;
		redouble1 = 0.0D;
		redouble2 = 0.0D;
		rels = new ArrayList<Object>();
		infolist = new ArrayList<String>();
		newable = true;
	}

	public final boolean isAllowedit()
	{
		return allowedit;
	}

	public final void setAllowedit(boolean flag)
	{
		allowedit = flag;
	}

	public final boolean isPrintable()
	{
		return printable;
	}

	public final void setPrintable(boolean flag)
	{
		printable = flag;
	}

	public String getDjdymc()
	{
		return djdymc != null ? djdymc : "";
	}

	public void setDjdymc(String s1)
	{
		djdymc = s1;
	}

	public String getDy_hzxml()
	{
		return dy_hzxml != null ? dy_hzxml : "";
	}

	public void setDy_hzxml(String s1)
	{
		dy_hzxml = s1;
	}

	public String getDy_mxxml()
	{
		return dy_mxxml != null ? dy_mxxml : "";
	}

	public void setDy_mxxml(String s1)
	{
		dy_mxxml = s1;
	}

	public boolean isNewable()
	{
		return newable;
	}

	public void setNewable(boolean flag)
	{
		newable = flag;
	}

	public int getState()
	{
		return state;
	}

	public void setState(int i1)
	{
		state = i1;
	}

	public long getReid()
	{
		return reid;
	}

	public void setReid(long l1)
	{
		reid = l1;
	}

	public String getBh()
	{
		return bh;
	}

	public void setBh(String s1)
	{
		bh = s1;
	}

	public String getUrlget()
	{
		return urlget;
	}

	public void setUrlget(String s1)
	{
		urlget = s1;
	}

	public String getUrllist()
	{
		return urllist;
	}

	public void setUrllist(String s1)
	{
		urllist = s1;
	}

	public String getUrlnew()
	{
		return urlnew;
	}

	public void setUrlnew(String s1)
	{
		urlnew = s1;
	}

	public String getUrlprt()
	{
		return urlprt;
	}

	public void setUrlprt(String s1)
	{
		if(!"".equals(s1) && !"null".equals(s1))
			urlprt = s1;
	}

	public String getRestr1()
	{
		return restr1;
	}

	public void setRestr1(String s1)
	{
		restr1 = s1;
	}

	public String getRestr2()
	{
		return restr2;
	}

	public void setRestr2(String s1)
	{
		restr2 = s1;
	}

	public int getReint1()
	{
		return reint1;
	}

	public void setReint1(int i1)
	{
		reint1 = i1;
	}

	public int getReint2()
	{
		return reint2;
	}

	public void setReint2(int i1)
	{
		reint2 = i1;
	}

	public double getRedouble1()
	{
		return redouble1;
	}

	public void setRedouble1(double d1)
	{
		redouble1 = d1;
	}

	public double getRedouble2()
	{
		return redouble2;
	}

	public void setRedouble2(double d1)
	{
		redouble2 = d1;
	}

	public Timestamp getRetime1()
	{
		return retime1;
	}

	public void setRetime1(Timestamp timestamp)
	{
		retime1 = timestamp;
	}

	public List<?> getRels()
	{
		return rels;
	}

	public void setRels(List<?> list)
	{
		rels = list;
	}

	public List<String> getInfolist()
	{
		return infolist;
	}

	public void setInfolist(List<String> list)
	{
		infolist = list;
	}

	public final String getDy_mx2xml()
	{
		return dy_mx2xml != null ? dy_mx2xml : "";
	}

	public final void setDy_mx2xml(String s1)
	{
		dy_mx2xml = s1;
	}
}
