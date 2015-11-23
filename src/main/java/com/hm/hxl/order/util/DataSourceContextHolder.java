package com.hm.hxl.order.util;

/**
 * 类名: DataSourceContextHolder 
 * 作用: TODO(这里用一句话描述这个类的作用) 
 * 作者: yanpengjie 
 * 日期: 2015-7-17 下午12:48:53 
 * 版本: V 1.0
 *
 */
@SuppressWarnings({"rawtypes","unchecked"})
public class DataSourceContextHolder {
    /** 线程本地环境 **/
    private static ThreadLocal contextHolder = new ThreadLocal();

    /**
     * 设置数据源类型
     */
    public static void setDataSourceType(String dataSourceType) {
        contextHolder.set(dataSourceType);
    }
    
    /**
     * 设置惠成长数据源类型
     */
    public static void setHCZDataSource() {
        contextHolder.set("HCZ");
    }
    /**
     * 设置惠健康数据源类型
     */
    public static void setHJKNEWDataSource() {
        contextHolder.set("HJKNEW");
    }
    /**
     * 设置惠美丽数据源类型
     */
    public static void setHMLDataSource() {
        contextHolder.set("HML");
    }
    /**
     * 设置惠收藏数据源类型
     */
    public static void setHSCDataSource() {
        contextHolder.set("HSC");
    }
    /**
     *  获取数据源类型 
     */
    public static String getDataSourceType() {
        return (String) contextHolder.get();
    }

    /**
     * 清除数据源类型
     */
    public static void clearDataSourceType() {
        contextHolder.remove();
    }
}
