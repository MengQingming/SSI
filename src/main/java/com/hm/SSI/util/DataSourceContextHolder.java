package com.hm.SSI.util;

/**多数据源配置
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
