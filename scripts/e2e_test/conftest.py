import pytest
import os
from utils.http_client import ApiClient


@pytest.fixture(scope="session")
def base_url():
    """获取测试服务基础URL"""
    return os.getenv("BASE_URL", "http://localhost:8080")


@pytest.fixture(scope="session")
def api_client(base_url):
    """创建API客户端"""
    return ApiClient(base_url=base_url, timeout=30)


@pytest.fixture(scope="session", autouse=True)
def check_service_health(api_client):
    """测试前检查服务是否正常"""
    try:
        response = api_client.get("/actuator/health")
        assert response.status_code == 200, "服务未启动或不健康，请先启动服务"
    except Exception as e:
        pytest.fail(f"无法连接服务: {e}")


@pytest.fixture(scope="session")
def admin_token(api_client):
    """获取管理员登录Token"""
    # 登录获取session/token
    response = api_client.post("/api/v1/auth/login", json={
        "username": "admin",
        "password": "123456"
    })
    if response.status_code == 200:
        # 根据实际认证方式返回token或session
        return response.json().get("data", {}).get("token", "")
    return ""