package com.joycity.joyclub.commons.utils;

// TODO: 2017/6/6 异常处理

/**
 * 批量插入大量数据工具
 * Created by CallMeXYZ on 2017/6/6.
 */
public abstract class AbstractBatchInsertlUtils {

    /**
     * 默认的单次插入最大数据量
     */
    private final int DEFAULT_MAX_SIZE_PER_INSERT = 10000;
    private final String SQL_INSERT = "insert ";
    private final String SQL_INTO = "into ";
    private final String SQL_IGNORE = "ignore ";
    private final String SQL_VALUES = " values ";
    private final String SQL_ON_DUBPLICATE_KEY_UPDATE = " on duplicate key update ";
    /**
     * values的分隔符
     */
    private final String SQL_COMA = ",";

    /**
     * 执行插入语句
     *
     * @param longInsertSql 这已经是完整的插入语句了
     * @return 插入的数据数
     */
    public abstract int executeInsert(String longInsertSql);

    /**
     * 获取插入表名称
     */
    public abstract String getTableName();

    /**
     * @return 结构 (value1,value2,...)
     */
    public abstract String getValuesNames();

    /**
     * 是否插入时无视重复，体现在insert 和table_name之间是否有ignore
     * 如果ignore=false,那么应该实现正确的{@link #getUpdateSqlWhenDuplicate()}
     * 如果ignore = true,那么{@link #getUpdateSqlWhenDuplicate()}返回什么都可以，不会被调用
     */
    public abstract Boolean ifIgnoreDuplicate();

    /**
     * @return 格式为 value1={value1},value2=#{value2}
     */
    public abstract String getUpdateSqlWhenDuplicate();

    /**
     * 根据单个对象获取要每个要插入的sql值
     *
     * @param index 在list中的index
     * @return 结构为(value1, value2, ...)
     */
    public abstract String getValues(int index);

    /**
     * 如果插入语句较小可以使用这个，如果插入语句较大，建议使用{@link #batchInsert(int, int)}
     * 自己先测试下最大数据量多少时不会导致com.mysql.cj.jdbc.exceptions.PacketTooBigException( 4194304)
     */
    public int batchInsert(int listSize) {
        return batchInsert(listSize, DEFAULT_MAX_SIZE_PER_INSERT);
    }

    /**
     * @param listSize             要插入的数据的list大小
     * @param maxListSizePerInsert 单次插入的最大数据量
     */
    public int batchInsert(int listSize, int maxListSizePerInsert) {

        StringBuilder sb = new StringBuilder();
        int insertNum = 0;
        //i是每个批次插入里的游标
        initStringBuilder(sb);
        for (int nowIndex = 0, i = 0; nowIndex < listSize; nowIndex++) {
            sb.append(getValues(nowIndex));
            //如果本次批量插入没到最后一个，并且当前的index也不是listData的最后一个，values应该追加逗号
            if (i != maxListSizePerInsert - 1 && nowIndex != listSize - 1) {
                sb.append(SQL_COMA);
                i++;
            }
            //否则就该执行插入语句
            else {
                //重复主键相关
                String updateSqlWhenDuplicate = getUpdateSqlWhenDuplicate();
                if (!ifIgnoreDuplicate() && updateSqlWhenDuplicate != null) {
                    sb.append(SQL_ON_DUBPLICATE_KEY_UPDATE);
                    sb.append(updateSqlWhenDuplicate);
                }
                insertNum += executeInsert(sb.toString());
                //单次插入成功清空i
                i = 0;
                initStringBuilder(sb);
            }

        }
        return insertNum;
    }


    private void initStringBuilder(StringBuilder sb) {
        //如果不为null，先清空，释放空间
        if (sb != null) {
            sb.setLength(0);
        } else {
            sb = new StringBuilder();
        }
        sb.append(SQL_INSERT);
        if (ifIgnoreDuplicate()) {
            sb.append(SQL_IGNORE);
        }
        sb.append(SQL_INTO);
        sb.append(getTableName());
        sb.append(getValuesNames());
        sb.append(SQL_VALUES);
    }

}
