from typing import Dict, Any


def assert_api_success(response, expected_status: int = 200):
    """验证API响应成功"""
    assert response.status_code == expected_status, f"期望状态码 {expected_status}, 实际 {response.status_code}"
    data = response.json()
    assert data.get("code") == 200 or data.get("success") == True, f"API返回错误: {data.get('message', '未知错误')}"
    return data


def assert_api_error(response, expected_status: int, expected_message: str = None):
    """验证API响应错误"""
    assert response.status_code == expected_status, f"期望状态码 {expected_status}, 实际 {response.status_code}"
    if expected_message:
        data = response.json()
        assert expected_message in data.get("message", ""), f"期望错误消息包含 '{expected_message}'"


def assert_pagination(data: Dict[str, Any]):
    """验证分页响应结构"""
    assert "content" in data or "items" in data, "分页响应缺少 content/items 字段"
    assert "totalElements" in data or "total" in data, "分页响应缺少总数字段"
    assert "currentPage" in data or "page" in data, "分页响应缺少当前页字段"