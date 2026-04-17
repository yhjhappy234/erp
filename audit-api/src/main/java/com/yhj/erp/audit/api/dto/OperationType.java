package com.yhj.erp.audit.api.dto;

/**
 * 操作类型枚举
 */
public enum OperationType {

    /**
     * 创建
     */
    CREATE,

    /**
     * 更新
     */
    UPDATE,

    /**
     * 删除
     */
    DELETE,

    /**
     * 登录
     */
    LOGIN,

    /**
     * 登出
     */
    LOGOUT,

    /**
     * 查询
     */
    QUERY,

    /**
     * 其他
     */
    OTHER
}