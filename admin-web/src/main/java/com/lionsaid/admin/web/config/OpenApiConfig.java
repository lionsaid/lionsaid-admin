package com.lionsaid.admin.web.config;


import io.swagger.v3.oas.annotations.ExternalDocumentation;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.License;
import io.swagger.v3.oas.annotations.servers.Server;
import org.springframework.boot.SpringBootConfiguration;

@SpringBootConfiguration
@OpenAPIDefinition(
        // ## API的基本信息，包括标题、版本号、描述、联系人等
        info = @io.swagger.v3.oas.annotations.info.Info(title = "狮语 API 文档",       // Api接口文档标题（必填）
                description = "狮语 API 文档",      // Api接口文档描述
                version = "0.0.1",                                   // Api接口版本
                termsOfService = "https://example.com/",             // Api接口的服务条款地址
                contact = @Contact(name = "sunwei",                            // 作者名称
                        email = "lionsaid@aliyun.com"                 // 作者邮箱
                ), license = @License(                                                // 设置联系人信息
                name = "MIT License",                                       // 授权名称
                url = "https://choosealicense.com/licenses/mit/"   // 授权信息
        )),
        // ## 表示服务器地址或者URL模板列表，多个服务地址随时切换（只不过是有多台IP有当前的服务API）
        servers = {@Server(url = "http://localhost:8080", description = "本地服务器"),

        }, externalDocs = @ExternalDocumentation(description = "更多内容请查看该链接", url = "https://github.com/springdoc/springdoc-openapi?tab=readme-ov-file"))

public class OpenApiConfig {

}