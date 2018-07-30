"# company_employee_mysql" 
### 作业要求
```html
1.将程序转为mysql程序
2.测试controller
3.测试repository
ps:repository全是调用已有方法，有一些就没有编写测试用例了。

```
### 1.Delete时出现异常：
#### No serializer found for class org.hibernate.proxy.pojo.javassist.JavassistLazyInitializer...org
#### 原因：
```html
使用Spring restful返回持久化的json对象，由于使用了hibernate，所以报错.
```
#### 解决办法：
```html
解决方法是将hibernate相关属性忽略,配置如下：
 @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
```
### 2.Junit mockito测试Pageable异常
#### 原因
```html
在使用MockMVC+Mockito模拟Service层返回的时候，当我们在Controller层中参数方法调用有Pageable对象的时候，
我们会发现，我们没办法生成一个Pageable的对象，会报一个Pageable是一个接口的错误。当我们把所有的参数从
Pageable接口变成Pageable的实现类PageRequest的时候，所有的方法参数都换成PageRequest，又会出现一个新的错误，
且不说PageRequest不能作为参数用于hibernate的分页查询，
另一方面，它没有构造方法，也无法初始化。
```
#### 解决办法
```html
解决方法：
报错的原因：没有Pageable的解析器。
解决的方法：给他设置一个解析器
1）使用匿名内部类
@Before()
public void setup() {
MockitoAnnotations.initMocks(this);
mockMvc = MockMvcBuilders.standaloneSetup(bannerController).setCustomArgumentResolvers(new PageableHandlerMethodArgumentResolver())
.setViewResolvers(new ViewResolver() {
@Override
public View resolveViewName(String viewName, Locale locale) throws Exception {
return new MappingJackson2JsonView();
}
} )
.build();
}
2）通过注解的方式注入一个PageableHandlerMethodArgumentResolver解析器
@InjectMocks
private PageableHandlerMethodArgumentResolver pageableArgumentResolver;
@Before()
public void setup() {
MockitoAnnotations.initMocks(this);
mockMvc = MockMvcBuilders.standaloneSetup(bannerController).setCustomArgumentResolvers(pageableArgumentResolver)
.build();
}

```