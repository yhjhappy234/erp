import pytest
from utils.http_client import ApiClient
from utils.assertions import assert_api_success, assert_api_error, assert_pagination
from utils.data_factory import UserFactory


class TestUserApi:
    """用户模块 E2E 测试"""

    @pytest.fixture(autouse=True)
    def setup(self, api_client: ApiClient, admin_token: str):
        self.client = api_client
        if admin_token:
            self.client.set_auth_token(admin_token)

    def test_create_user_success(self):
        """创建用户 - 成功场景"""
        payload = UserFactory.build_create_request()

        response = self.client.post("/api/v1/users", json=payload)

        assert_api_success(response, expected_status=200)
        data = response.json()["data"]
        assert data["username"] == payload["username"]

    def test_get_user_not_found(self):
        """获取不存在的用户 - 返回错误"""
        response = self.client.get("/api/v1/users/999999")

        assert response.status_code in [404, 400]

    def test_list_users_pagination(self):
        """用户列表分页"""
        response = self.client.get("/api/v1/users", params={"page": 1, "size": 10})

        assert_api_success(response)
        data = response.json()
        if "data" in data:
            assert_pagination(data["data"])


class TestAuthApi:
    """认证模块 E2E 测试"""

    @pytest.fixture(autouse=True)
    def setup(self, api_client: ApiClient):
        self.client = api_client

    def test_login_success(self):
        """登录 - 成功场景"""
        response = self.client.post("/api/v1/auth/login", json={
            "username": "admin",
            "password": "123456"
        })

        assert_api_success(response)

    def test_login_wrong_password(self):
        """登录 - 密码错误"""
        response = self.client.post("/api/v1/auth/login", json={
            "username": "admin",
            "password": "wrong_password"
        })

        assert response.status_code in [401, 400]