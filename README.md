# Gentle Restful Framework
***公告：因为最近在使用此框架制作网站，进行实践，所以框架一直在更新，因此下面的文档不一定准确***
> Gentle是一个简易的restful的框架，方便大家更加容易开发单页应用后台。
现在框架还在逐渐完善中，大家可以先观望下

相关介绍
* 支持GET POST DELETE PUT四种请求
* 支持200 400 401 404四种状态返回

未完成
* action 上面添加参数匹配
* 学习jfinal 看看是不是要把FastJSON api提供出来
* druid
* 添加cookie session操作
* 完善拦截器
* Util名字更换，不然容易和用户的冲突
* ResultSet 自动装载Entity，使用注解方式判断字段

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

