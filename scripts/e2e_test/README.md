# E2E 测试框架

基于 Python + pytest 的端到端测试框架，用于验证 ERP 系统的 API 功能。

## 环境要求

- Python 3.12+
- pytest 8.x
- requests 2.x

## 安装依赖

```bash
pip install -r requirements.txt
```

## 运行测试

```bash
# 运行全部测试
BASE_URL=http://localhost:8080 pytest tests/ -v

# 运行指定模块测试
BASE_URL=http://localhost:8080 pytest tests/test_user_api.py -v

# 生成 HTML 报告
BASE_URL=http://localhost:8080 pytest tests/ -v --html=reports/e2e_report.html
```

## 目录结构

```
scripts/e2e_test/
├── README.md           <- 本文档
├── requirements.txt    <- Python 依赖
├── conftest.py         <- pytest 全局配置
├── config/
│   ├── base_config.py  <- 基础配置
│   └── test_data.py    <- 测试数据常量
├── utils/
│   ├── http_client.py  <- HTTP 客户端封装
│   ├── assertions.py   <- 自定义断言
│   └── data_factory.py <- 测试数据工厂
└── tests/
    ├── test_user_api.py      <- 用户模块测试
    ├── test_idc_api.py       <- IDC 模块测试
    ├── test_server_api.py    <- 服务器模块测试
    ├── test_network_api.py   <- 网络模块测试
    ├── test_power_api.py     <- 电源模块测试
    └── test_inventory_api.py <- 库存模块测试
```