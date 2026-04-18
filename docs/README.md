# ERP 系统文档导航

本目录包含 ERP 系统的所有设计文档、接口文档和开发指南。

## 目录结构

```
docs/
├── README.md           <- 本文档（导航）
├── architecture/       <- 架构设计文档
│   ├── system-overview.md     <- 系统整体架构
│   ├── module-design.md       <- 模块详细设计
│   ├── database-design.md     <- 数据库设计
│   └── deployment.md          <- 部署架构
├── api/                <- API 接口文档
│   ├── user-api.md            <- 用户模块接口
│   ├── idc-api.md             <- IDC模块接口
│   ├── server-api.md          <- 服务器模块接口
│   ├── network-api.md         <- 网络模块接口
│   ├── power-api.md           <- 电源模块接口
│   └── inventory-api.md       <- 库存模块接口
├── development/        <- 开发指南
│   ├── getting-started.md     <- 开发环境搭建
│   ├── coding-standards.md    <- 编码规范
│   └── testing-guide.md       <- 测试指南
└── changelog/          <- 版本变更记录
    └── CHANGELOG.md           <- 变更日志
```

## 快速导航

| 文档类型 | 说明 | 路径 |
|---------|------|------|
| 架构设计 | 系统架构、模块设计、数据库设计 | [architecture/](architecture/) |
| API 文档 | 各模块 REST API 详细说明 | [api/](api/) |
| 开发指南 | 环境搭建、编码规范、测试指南 | [development/](development/) |
| 变更日志 | 版本历史、变更记录 | [changelog/](changelog/) |

## 技术栈

- **Java**: 21 (LTS)
- **Spring Boot**: 4.0.5+
- **数据库**: SQLite 3.x (每模块独立)
- **ORM**: Spring Data JPA + Hibernate 6.x
- **DTO转换**: MapStruct 1.6.x
- **测试**: JUnit 5 + Mockito + JaCoCo (90%覆盖率)
- **E2E测试**: Python 3.12+ + pytest 8.x