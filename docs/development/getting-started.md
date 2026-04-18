# 开发环境搭建指南

## 1. 环境要求

| 软件 | 版本 | 说明 |
|-----|------|------|
| JDK | 21 LTS | 必须使用 JDK 21 |
| Maven | 3.9.x | 多模块构建 |
| Python | 3.12+ | E2E 测试 |

## 2. 项目构建

```bash
# 克隆项目
git clone <repository-url>
cd erp

# 构建项目（跳过测试）
mvn clean install -DskipTests

# 构建并运行测试
mvn clean verify
```

## 3. 启动服务

```bash
# 启动全局聚合服务
mvn spring-boot:run -pl platform-app

# 或使用 jar 文件
java -jar platform-app/target/platform-app-1.0.0-SNAPSHOT.jar
```

## 4. E2E 测试

```bash
cd scripts/e2e_test
pip install -r requirements.txt
BASE_URL=http://localhost:8080 pytest tests/ -v
```

## 5. IDE 配置

### IntelliJ IDEA
- 启用 Lombok 注解处理
- 安装 MapStruct 插件
- 设置 Java 21 SDK

### VS Code
- 安装 Extension Pack for Java
- 安装 Lombok Annotations Support