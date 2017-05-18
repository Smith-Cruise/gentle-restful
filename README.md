# Gentle Restful Framework
> Gentle是一个简易的restful的框架，方便大家更加容易开发单页应用后台。
现在框架还在逐渐完善中，大家可以先观望下

相关介绍
* 支持GET POST DELETE PUT四种请求
* 支持200 400 401 404四种状态返回
未完成
* 自带简易的数据库连接池
* 请求参数的获取  

## 简易教程
### 代码
```java
// 测试代码位于src/main/com/app中
@Controller
public class Test {
    @Action(method = RequestMethod.GET, path = "/success")
    public Data success() {
        return new Data(ResponseStatus.SUCCESS, "this is success request");
    }

    @Action(method = RequestMethod.GET, path = "/error")
    public Data error() {
        return new Data(ResponseStatus.ERROR, "this is error request");
    }

    @Action(method = RequestMethod.GET, path = "/unauthorized")
    public Data unauthorized() {
        return new Data(ResponseStatus.UNAUTHORIZED, "this is unauthorized request");
    }

    @Action(method = RequestMethod.GET, path = "/not_found")
    public Data notfound() {
        return new Data(ResponseStatus.NOTFOUND, "this is not found request");
    }
}
```
### 效果
![](https://raw.githubusercontent.com/Smith-Curise/gentle-restful/master/asset/introduce.gif)

