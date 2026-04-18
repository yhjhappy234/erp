# 模块设计文档

## 1. 用户模块 (user-service)

### 功能
- 用户管理（创建、查询、更新、删除）
- 用户认证（登录、登出）
- 权限控制（ADMIN/USER 角色）

### API 路径
- `/api/v1/users` - 用户管理
- `/api/v1/auth` - 认证接口

---

## 2. 审计模块 (audit-service)

### 功能
- 操作日志记录
- 日志查询（按用户、实体、时间）
- 异步日志写入

### API 路径
- `/api/v1/audit` - 审计日志

---

## 3. IDC 模块 (idc-service)

### 功能
- 数据中心管理
- 机房管理
- 机柜管理

### API 路径
- `/api/v1/idc/datacenters` - 数据中心
- `/api/v1/idc/rooms` - 机房
- `/api/v1/idc/cabinets` - 机柜

---

## 4. 服务器模块 (server-service)

### 功能
- 服务器资产管理
- 服务器位置跟踪（机柜、U位）
- 保修信息管理

### API 路径
- `/api/v1/servers` - 服务器

---

## 5. 网络模块 (network-service)

### 功能
- 网络设备管理
- IP地址池管理
- VLAN管理

### API 路径
- `/api/v1/network/devices` - 网络设备
- `/api/v1/network/ip-pools` - IP地址池
- `/api/v1/network/vlans` - VLAN

---

## 6. 电源模块 (power-service)

### 功能
- 电源设备管理
- PUE数据记录
- 能耗数据分析

### API 路径
- `/api/v1/power/devices` - 电源设备
- `/api/v1/power/pue` - PUE数据

---

## 7. 库存模块 (inventory-service)

### 功能
- 物品库存管理
- 供应商管理
- 采购订单管理
- 合同管理

### API 路径
- `/api/v1/inventory/items` - 库存物品
- `/api/v1/inventory/suppliers` - 供应商
- `/api/v1/inventory/purchase-orders` - 采购订单
- `/api/v1/inventory/contracts` - 合同