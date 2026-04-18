from typing import Dict, Any
import uuid
from datetime import datetime


def generate_uuid() -> str:
    """生成UUID"""
    return str(uuid.uuid4())


class UserFactory:
    """用户测试数据工厂"""

    @staticmethod
    def build_create_request(username: str = None, password: str = None, role: str = "USER") -> Dict[str, Any]:
        return {
            "username": username or f"test_user_{generate_uuid()[:8]}",
            "password": password or "test123456",
            "role": role
        }


class DataCenterFactory:
    """数据中心测试数据工厂"""

    @staticmethod
    def build_create_request(name: str = None) -> Dict[str, Any]:
        code = f"DC_{generate_uuid()[:8]}"
        return {
            "name": name or f"测试数据中心_{datetime.now().strftime('%Y%m%d')}",
            "code": code,
            "tier": "T3",
            "totalRacks": 100,
            "totalPowerKw": 1000.00
        }


class ServerFactory:
    """服务器测试数据工厂"""

    @staticmethod
    def build_create_request(asset_code: str = None) -> Dict[str, Any]:
        return {
            "assetCode": asset_code or f"SRV_{generate_uuid()[:8]}",
            "serialNumber": f"SN_{generate_uuid()[:12]}",
            "brand": "Dell",
            "model": "PowerEdge R740",
            "originalValue": 50000.00
        }