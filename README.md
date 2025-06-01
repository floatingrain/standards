# standards
一些软件开发和配置的规范模板，用于在编写新项目时套用或参考。

## 项目概述
该项目是一个综合性的软件开发规范模板集合，包含Java相关的开发规范和示例、日志管理配置、局域网文件共享服务配置以及其他系统级别的配置文件。这些规范和配置旨在为开发者提供一个标准的开发环境和实践指南。

### Java 规范
- **Controller**: 包含全局控制器建议（`GlobalControllerAdvice.java`），用于处理全局异常和响应。它实现了`RequestBodyAdvice`和`ResponseBodyAdvice`接口，确保所有API调用的一致性。
- **Entity**: 包含响应实体类（`Response.java`）和工具类（`ResponseUtil.java`），用于封装API返回的数据结构。
- **日志**: 提供了日志脱敏转换器（`SensitiveLogDataConverter.java`）和日志配置文件（`logback-spring.xml`），支持敏感信息的脱敏处理。

### 局域网文件共享服务（dufs）
- **配置文件**: `dufs.xml` 和 `dufs.yaml` 配置了局域网文件共享服务，允许用户通过HTTP协议进行文件传输和访问。

### 系统配置文件
- **filebeat.yml**: 配置了Filebeat输入源和输出目标，用于收集和发送日志数据到Elasticsearch。
- **nginx.conf**: 配置了Nginx服务器的基本设置，包括SSL证书管理和反向代理规则。
- **fonts.conf**: 配置了字体渲染策略，指定了不同字体族的默认字体。
- **devcontainer.json**: 配置了开发容器的基本设置，便于快速搭建一致的开发环境。
- **chfs.ini**: 配置了CHFS（简易HTTP文件服务器）的相关参数，支持多种权限控制和文件操作模式。

## 使用说明
### Java 规范
- **GlobalControllerAdvice**: 在Spring Boot应用中使用此全局控制器建议来统一处理异常和响应格式。
- **ResponseUtil**: 使用此类来构建标准化的API响应。
- **SensitiveLogDataConverter**: 在日志记录中自动脱敏敏感信息如手机号码、银行卡号等。

### 日志管理
- **logback-spring.xml**: 将日志分为info及以下级别和error级别，并分别写入不同的文件，方便日志分析。

### 局域网文件共享服务（dufs）
- **启动服务**: 确保`dufs.exe`可执行文件存在并正确配置路径，然后启动服务。
- **访问服务**: 通过指定的端口和路径访问共享文件。

### 系统配置
- **filebeat.yml**: 根据实际需求调整输入路径和输出目标。
- **nginx.conf**: 修改域名和路径以匹配您的实际部署情况。
- **fonts.conf**: 调整字体配置以适应不同的操作系统和显示设备。
- **devcontainer.json**: 自定义开发容器的镜像和运行参数。
- **chfs.ini**: 设置共享目录、访问权限和安全策略。

## 注意事项
- 所有配置文件应根据具体的应用场景进行适当的修改。
- 在生产环境中启用任何服务之前，请确保进行了充分的安全性和性能测试。
- 对于涉及敏感信息的日志和文件操作，务必启用相应的保护措施。