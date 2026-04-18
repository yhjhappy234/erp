# ERP 系统变更日志

## [1.0.0-SNAPSHOT] - 2026-04-18

### 规范合规修复
- Spring Boot 版本升级到 4.0.5
- JaCoCo 覆盖率门禁提升到 90%
- 启用 Flyway 数据库迁移管理
- 修复 ddl-auto 配置为 validate
- BaseEntity 时间字段改为 Instant (UTC存储)
- 创建 E2E 测试框架目录
- 创建 docs 文档目录

### 已有功能模块
- 用户管理模块 (user-service)
- 审计日志模块 (audit-service)
- IDC数据中心管理 (idc-service)
- 服务器资产管理 (server-service)
- 网络资源管理 (network-service)
- 电源能耗管理 (power-service)
- 库存采购管理 (inventory-service)