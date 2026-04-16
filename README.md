# 云计算数字供应链ERP系统

基于 Spring Boot 3.4.4 和 JDK 21 的云计算服务商数字供应链ERP系统。

## 项目概述

本系统为云计算服务商提供完整的基础设施资源管理解决方案，包括：
- 用户认证与管理
- 机房IDC管理
- 服务器资产管理
- 网络资源管理
- 电力能源管理
- 库存采购管理

## 技术栈

| 组件 | 版本 |
|------|------|
| JDK | 21 |
| Spring Boot | 3.4.4 |
| 数据库 | SQLite 3.47.x |
| ORM | Spring Data JPA + Hibernate 6.x |
| 构建工具 | Maven 3.9.x |
| 密码加密 | BCrypt |
| 认证方式 | Session |
| 测试覆盖率 | JaCoCo (90%) |

## 默认账户

系统启动后自动创建默认管理员账户：
- 用户名: `admin`
- 密码: `123456`

可通过环境变量配置：
- `APP_DEFAULT_USERNAME`
- `APP_DEFAULT_PASSWORD`

## 项目结构

```
erp/
├── pom.xml                           # 父POM
├── platform-common/                  # 公共模块
│   └── src/main/java/com/yhj/erp/common/
│       ├── constant/                 # 常量定义
│       ├── dto/                      # 通用DTO
│       └── exception/                # 异常体系
├── platform-common-db/               # 数据库公共模块
│   └── src/main/java/com/yhj/erp/common/db/
│       ├── entity/                   # BaseEntity
│       ├── repository/               # BaseRepository
│       └── config/                   # 数据库配置
├── user-api/                         # 用户管理API
├── user-service/                     # 用户管理实现
├── idc-api/                          # IDC管理API
├── idc-service/                      # IDC管理实现
├── server-api/                       # 服务器管理API
├── server-service/                   # 服务器管理实现
├── network-api/                      # 网络资源API
├── network-service/                  # 网络资源实现
├── power-api/                        # 电力能源API
├── power-service/                    # 电力能源实现
├── inventory-api/                    # 库存采购API
├── inventory-service/                # 库存采购实现
└── platform-app/                     # 聚合启动器
```

## 模块说明

### 1. platform-common
公共工具模块，提供：
- `ErrorCode` - 错误码枚举
- `ApiResponse` - 统一API响应体
- `PageRequest` / `PageResponse` - 分页请求/响应
- `BusinessException` - 业务异常
- `GlobalExceptionHandler` - 全局异常处理

### 2. platform-common-db
数据库公共模块，提供：
- `BaseEntity` - 实体基类（包含id、创建时间、更新时间、软删除标志）
- `BaseRepository` - Repository基接口（支持软删除）

### 3. user-service (用户认证与管理)
功能：
- 用户登录/登出
- Session认证
- 用户CRUD管理（仅管理员）
- 密码修改
- 角色管理（ADMIN/USER）
- 用户状态管理（ACTIVE/DISABLED）

API端点：
- `POST /api/v1/auth/login` - 登录
- `POST /api/v1/auth/logout` - 登出
- `GET /api/v1/auth/current` - 获取当前用户
- `GET /api/v1/auth/status` - 检查登录状态
- `GET /api/v1/users` - 用户列表（需管理员）
- `POST /api/v1/users` - 创建用户（需管理员）
- `PUT /api/v1/users/{id}` - 更新用户（需管理员）
- `DELETE /api/v1/users/{id}` - 删除用户（需管理员）
- `POST /api/v1/users/me/password` - 修改密码

### 4. idc-service (机房IDC管理)
功能：
- 数据中心管理
- 机房房间管理
- 机柜管理
- U位管理

### 5. server-service (服务器资产管理)
功能：
- 服务器资产全生命周期管理
- 硬件配置管理
- 部署管理
- 报废管理

### 6. network-service (网络资源管理)
功能：
- 网络设备管理
- IP地址池管理
- VLAN管理
- 带宽资源管理

### 7. power-service (电力能源管理)
功能：
- 电力设备管理
- 能耗数据采集
- PUE监控
- 成本核算

### 8. inventory-service (库存采购管理)
功能：
- 供应商管理
- 采购申请/订单管理
- 库存管理
- 出入库管理

## 快速开始

### 前置条件
- JDK 21+
- Maven 3.9+

### 构建项目
```bash
cd /home/admin/workspace/erp
mvn clean install
```

### 启动应用
```bash
java -jar platform-app/target/platform-app-1.0.0-SNAPSHOT.jar
```

应用将在 http://localhost:8080 启动

### API文档
启动后访问：
- Swagger UI: http://localhost:8080/swagger-ui.html
- API Docs: http://localhost:8080/api-docs

## API示例

### 用户登录
```bash
curl -X POST http://localhost:8080/api/v1/auth/login \
  -H "Content-Type: application/json" \
  -d '{"username":"admin","password":"123456"}'
```

### 创建数据中心（需登录）
```bash
curl -X POST http://localhost:8080/api/v1/datacenters \
  -H "Content-Type: application/json" \
  -d '{
    "name": "北京数据中心",
    "code": "BJ-DC-01",
    "location": "北京市朝阳区xxx",
    "tier": "T3",
    "totalRacks": 200,
    "totalPowerKw": 2000
  }'
```

### 查询数据中心列表
```bash
curl "http://localhost:8080/api/v1/datacenters?page=1&size=10"
```

## 包命名规范

所有代码的包根路径为：`com.yhj.erp`

各模块子包结构：
```
com.yhj.erp.{模块名}
├── api/                    # API层
│   ├── dto/               # 数据传输对象
│   └── service/           # 服务接口
└── impl/                   # 实现层
    ├── controller/        # REST控制器
    ├── service/           # 服务实现
    ├── repository/        # 数据访问
    ├── entity/            # 实体类
    ├── mapper/            # MapStruct映射
    └── config/            # 配置类
```

## 测试

```bash
# 运行测试
mvn test

# 运行测试并生成覆盖率报告
mvn verify
```

覆盖率要求：行覆盖率 ≥ 90%，分支覆盖率 ≥ 90%

## 许可证

Copyright © 2026

## 相关文档

- [PRD文档](./prd/云计算数字供应链ERP系统PRD文档.md)
- [技术规范](../ai-spec.md)