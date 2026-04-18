import requests
from typing import Optional, Dict, Any


class ApiClient:
    """HTTP API 客户端封装"""

    def __init__(self, base_url: str, timeout: int = 30):
        self.base_url = base_url.rstrip("/")
        self.timeout = timeout
        self.session = requests.Session()
        self.session.headers.update({
            "Content-Type": "application/json",
            "Accept": "application/json"
        })

    def set_auth_token(self, token: str):
        """设置认证Token"""
        self.session.headers["Authorization"] = f"Bearer {token}"

    def get(self, path: str, params: Optional[Dict] = None) -> requests.Response:
        """GET 请求"""
        url = f"{self.base_url}{path}"
        return self.session.get(url, params=params, timeout=self.timeout)

    def post(self, path: str, json: Optional[Dict] = None, data: Optional[Dict] = None) -> requests.Response:
        """POST 请求"""
        url = f"{self.base_url}{path}"
        return self.session.post(url, json=json, data=data, timeout=self.timeout)

    def put(self, path: str, json: Optional[Dict] = None) -> requests.Response:
        """PUT 请求"""
        url = f"{self.base_url}{path}"
        return self.session.put(url, json=json, timeout=self.timeout)

    def delete(self, path: str) -> requests.Response:
        """DELETE 请求"""
        url = f"{self.base_url}{path}"
        return self.session.delete(url, timeout=self.timeout)

    def patch(self, path: str, json: Optional[Dict] = None) -> requests.Response:
        """PATCH 请求"""
        url = f"{self.base_url}{path}"
        return self.session.patch(url, json=json, timeout=self.timeout)