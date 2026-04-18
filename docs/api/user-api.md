# API 文档通用规范

## 基本信息

- **Base URL**: `/api/v1/{resource}`
- **认证方式**: Session / JWT Token
- **数据格式**: `application/json`

## 统一响应格式

```json
{
  "code": 200,
  "message": "success",
  "data": { ... },
  "traceId": "uuid",
  "timestamp": 1712500000000
}
```

## HTTP 状态码

| 状态码 | 说明 |
|-------|------|
| 200 | 成功 |
| 201 | 创建成功 |
| 400 | 参数错误 |
| 401 | 未认证 |
| 403 | 无权限 |
| 404 | 资源不存在 |
| 500 | 服务器错误 |

## 分页参数

```
GET /api/v1/{resource}?page=1&size=20&sortBy=createdAt&sortDir=DESC
```

## 业务错误码

| 模块 | 前缀 | 范围 |
|-----|------|------|
| 公共 | CMN | CMN0001-CMN0999 |
| 用户 | USER | USER1000-USER1999 |
| IDC | IDC | IDC2000-IDC2999 |
| 服务器 | SERVER | SERVER3000-SERVER3999 |
| 网络 | NETWORK | NETWORK4000-NETWORK4999 |
| 电源 | POWER | POWER5000-POWER5999 |
| 库存 | INVENTORY | INVENTORY6000-INVENTORY6999 |