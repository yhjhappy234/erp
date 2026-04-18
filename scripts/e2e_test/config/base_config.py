import os

# 基础配置
BASE_URL = os.getenv("BASE_URL", "http://localhost:8080")
TIMEOUT = 30

# API 版本
API_VERSION = "v1"

# 默认管理员账户
DEFAULT_ADMIN_USERNAME = "admin"
DEFAULT_ADMIN_PASSWORD = "123456"