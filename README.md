"# company_employee_mysql" 
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
