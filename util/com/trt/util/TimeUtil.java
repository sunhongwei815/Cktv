/*jadclipse*/// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) radix(10) lradix(10) 

package com.trt.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.apache.log4j.Logger;

import com.trt.util.jz.TextUtil;

// Referenced classes of package com.zfsys.util.common:
//            TextUtil

public abstract class TimeUtil
{

    private TimeUtil()
    {
    }

    public static String getCurrentTime()
    {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        return (new StringBuilder()).append(calendar.get(1)).append("-").append(calendar.get(2) + 1).append("-").append(calendar.get(5)).append(" ").append(calendar.get(11)).append(":").append(calendar.get(12)).append(":").append(calendar.get(13)).toString();
    }

    public static String getDjbhTime()
    {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        return (new StringBuilder()).append(calendar.get(1)).append("").append(TextUtil.format(2, calendar.get(2) + 1)).append(TextUtil.format(2, calendar.get(5))).toString();
    }

    public static String getDateTime(Date date)
    {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return (new StringBuilder()).append(calendar.get(1)).append("-").append(calendar.get(2) + 1).append("-").append(calendar.get(5)).append(" ").append(calendar.get(11)).append(":").append(calendar.get(12)).append(":").append(calendar.get(13)).toString();
    }

    public static double getHourInMillis(long l)
    {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(l);
        return calendar.get(11) + calendar.get(12) / 60D;
    }

    public static long getMillisFromDate(Date date)
    {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.getTimeInMillis();
    }

    public static long getMillisFromDate(String s)
    {
        Date date = null;
        try
        {
            date = b.parse(s);
        }
        catch(ParseException parseexception)
        {
            parseexception.printStackTrace();
        }
        return getMillisFromDate(date);
    }

    public static boolean isPromotionTime(Date date, Date date1, Date date2, Date date3, String s)
    {
        Calendar calendar = Calendar.getInstance();
        boolean flag = false;
        long l = System.currentTimeMillis();
        calendar.setTimeInMillis(l);
        int i = calendar.get(1);
        int j = calendar.get(2);
        int k = calendar.get(5);
        int i1 = calendar.get(7) - 1;
        if(i1 == 0)
            i1 = 7;
        calendar.setTime(date);
        calendar.set(11, 0);
        calendar.set(12, 0);
        calendar.set(13, 0);
        long l1 = calendar.getTimeInMillis();
        calendar.setTime(date1);
        calendar.set(11, 23);
        calendar.set(12, 59);
        calendar.set(13, 59);
        long l2 = calendar.getTimeInMillis();
        if(l > l1 && l < l2)
        {
            calendar.setTime(date2);
            calendar.set(i, j, k, calendar.get(11), calendar.get(12));
            long l3 = calendar.getTimeInMillis();
            calendar.setTime(date3);
            calendar.set(i, j, k, calendar.get(11), calendar.get(12));
            long l4 = calendar.getTimeInMillis();
            if(l > l3 && l < l4 && (null == s || "".equals(s) || s.indexOf(String.valueOf(i1)) > -1))
                flag = true;
        }
        return flag;
    }

    public static boolean refreshPromotionData(Date date, int i)
    {
        Calendar calendar = Calendar.getInstance();
        boolean flag = false;
        if(i <= 0)
            return false;
        calendar.set(11, 0);
        calendar.set(12, 0);
        calendar.set(13, 0);
        calendar.set(14, 0);
        long l = calendar.getTimeInMillis();
        calendar.setTime(date);
        calendar.set(11, 0);
        calendar.set(12, 0);
        calendar.set(13, 0);
        calendar.set(14, 0);
        do
        {
            if(l <= calendar.getTimeInMillis())
                break;
            calendar.add(5, i);
            if(l != calendar.getTimeInMillis())
                continue;
            flag = true;
            break;
        } while(true);
        return flag;
    }

    public static boolean overTime(Date date, int i)
    {
        Calendar calendar = Calendar.getInstance();
        boolean flag = false;
        long l = calendar.getTimeInMillis();
        calendar.setTime(date);
        calendar.set(11, 23);
        calendar.set(12, 59);
        calendar.set(13, 59);
        calendar.add(5, i);
        long l1 = calendar.getTimeInMillis();
        if(l > l1)
            flag = true;
        return flag;
    }

    public static String getWeekday(String s)
    {
        Calendar calendar = Calendar.getInstance();
        int i = 0;
        String s1 = null;
        try
        {
            calendar.setTime(c.parse(s));
            i = calendar.get(7);
        }
        catch(ParseException parseexception)
        {
            parseexception.printStackTrace();
        }
        switch(i)
        {
        case 0: // '\0'
            s1 = "\u661F\u671F\u65E5";
            break;

        case 1: // '\001'
            s1 = "\u661F\u671F\u4E00";
            break;

        case 2: // '\002'
            s1 = "\u661F\u671F\u4E8C";
            break;

        case 3: // '\003'
            s1 = "\u661F\u671F\u4E09";
            break;

        case 4: // '\004'
            s1 = "\u661F\u671F\u56DB";
            break;

        case 5: // '\005'
            s1 = "\u661F\u671F\u4E94";
            break;

        case 6: // '\006'
            s1 = "\u661F\u671F\u516D";
            break;

        default:
            s1 = "\u661F\u671F\u65E5";
            break;
        }
        return s1;
    }

    public static String getStrdate(String defaultValue, String format)
    {
        if(defaultValue == null)
            return null;
        defaultValue = defaultValue.replaceAll(" ", "");
        if((defaultValue.indexOf("today") > -1 || defaultValue.indexOf("month") > -1) && defaultValue.indexOf("-") > -1)
        {
            int i = defaultValue.indexOf("-");
            return a(defaultValue.substring(0, i), format, "-", defaultValue.substring(i + 1));
        }
        if(defaultValue.indexOf("+") > -1)
        {
            int j = defaultValue.indexOf("+");
            return a(defaultValue.substring(0, j), format, "+", defaultValue.substring(j + 1));
        } else
        {
            return a(defaultValue, format, null, null);
        }
    }

    public static Date getDate(String defaultValue, String format)
    {
    	String dateStr = getStrdate(defaultValue, format);
    	try {
			return DateFormatUtil.getDate(dateStr, format);
		} catch (ParseException e) {
			e.printStackTrace();
		}
    	return null;
    }

    private static String a(String s, String s1, String s2, String s3)
    {
        SimpleDateFormat simpledateformat = new SimpleDateFormat(s1);
        Calendar calendar = Calendar.getInstance();
        if("today".equalsIgnoreCase(s))
        {
            calendar.set(11, 0);
            calendar.set(12, 0);
        } else
        if(!"todaynow".equalsIgnoreCase(s))
            if("monthstart".equalsIgnoreCase(s))
                calendar.set(5, 1);
            else
            if("monthend".equalsIgnoreCase(s))
            {
                calendar.set(5, 1);
                calendar.add(2, 1);
                calendar.add(5, -1);
            } else
            {
                Date date = null;
                try
                {
                    date = simpledateformat.parse(s);
                }
                catch(ParseException parseexception)
                {
                    parseexception.printStackTrace();
                }
                if(date != null)
                    return simpledateformat.format(date);
            }
        if(s2 != null && s3 != null)
            if("+".equals(s2))
            {
                int i = Integer.parseInt(s3);
                calendar.add(5, i);
            } else
            if("-".equals(s2))
            {
                int j = Integer.parseInt(s3);
                calendar.add(5, -j);
            }
        return simpledateformat.format(calendar.getTime());
    }

    public static boolean isUsetime(int i, int j)
    {
        Calendar calendar = Calendar.getInstance();
        int k = calendar.get(11);
        if(i == j)
            return false;
        if(i < j)
            return k < i || k >= j;
        return k < i && k >= j;
    }

    static Logger a = Logger.getLogger(TimeUtil.class.getName());
    private static DateFormat b = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private static DateFormat c = new SimpleDateFormat("yyyy-MM-dd");

}
