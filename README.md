# designer-server

## 文档地址

http://localhost:8080/api/swagger-ui/index.html

### checkstyle 代码规范检查

```bash
gradle clean build # build时会自动进行风格检测
```

## 接口响应规范

```java
// 响应规范
public class ResponseDTO<T> {
  private Boolean success;	// 是否成功标记
  @Nullable
  private T data;	// 返回的结果对象，为方便前端解析，统一为对象
  private StatusCode code;	// 业务状态码
  private StatusMessage message;	// 业务状态提示信息
}
```

```java
// 分页响应规范
public class PaginationData<T> {
  private List<T> list; // 分页列表
  private int current;	// 当前页数
  private int pageSize;	// 分页大小
  private int total;	// 总条数
}
```

## 接口请求规范

```
为了避免传参接参混乱，譬如 post 请求参数一会儿是查询参数（RequestParam），一会儿是请求体参数（RequestBody），因此统一规定

参数类型：1、查询参数（RequestParam） 2. 路径参数（PathVariable） 3. 请求体参数（RequestBody） 4. 请求头参数

1. Get 请求参数，只能使用 查询参数 和 路径参数;
2. Post 请求参数，只能使用 请求体参数 和 路径参数，请求体参数格式均为 json 格式（application/json）;
3. 后端参数字段需加上@Schema(description = "描述")swagger 注解, 方便理解;
4. 文件相关接口譬如上传、下载等另做特殊规定;
```

## 单元测试

```
gradle test --tests 测试类或方法完整路径

示例：
gradle test --tests designer.server.ServerApplicationTests
```
