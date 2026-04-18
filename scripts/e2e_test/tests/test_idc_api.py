import pytest
from utils.http_client import ApiClient
from utils.assertions import assert_api_success, assert_pagination
from utils.data_factory import DataCenterFactory


class TestIdcApi:
    """IDC 模块 E2E 测试"""

    @pytest.fixture(autouse=True)
    def setup(self, api_client: ApiClient, admin_token: str):
        self.client = api_client
        if admin_token:
            self.client.set_auth_token(admin_token)

    def test_list_datacenters(self):
        """数据中心列表"""
        response = self.client.get("/api/v1/idc/datacenters", params={"page": 1, "size": 10})

        assert_api_success(response)

    def test_create_datacenter(self):
        """创建数据中心"""
        payload = DataCenterFactory.build_create_request()

        response = self.client.post("/api/v1/idc/datacenters", json=payload)

        assert_api_success(response)


class TestRoomApi:
    """机房模块 E2E 测试"""

    @pytest.fixture(autouse=True)
    def setup(self, api_client: ApiClient, admin_token: str):
        self.client = api_client
        if admin_token:
            self.client.set_auth_token(admin_token)

    def test_list_rooms(self):
        """机房列表"""
        response = self.client.get("/api/v1/idc/rooms", params={"page": 1, "size": 10})

        assert_api_success(response)