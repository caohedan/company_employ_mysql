"# company_employee_mysql" 
### 1.Delete时出现异常：
#### No serializer found for class org.hibernate.proxy.pojo.javassist.JavassistLazyInitializer...org
#### 原因：
```html
hibernate会给每个被管理的对象加上hibernateLazyInitializer属性，同时struts-jsonplugin或者其他的jsonplugin
都是因为jsonplugin用的是java的内审机制.hibernate会给被管理的pojo加入一个hibernateLazyInitializer属性,
jsonplugin通过java的反射机制将pojo转换成json，会把hibernateLazyInitializer也拿出来操作,
但是hibernateLazyInitializer无法由反射得到，所以就抛异常了。 
```
#### 解决办法：
```html
 @JsonIgnoreProperties(value={"hibernateLazyInitializer"})   （此时只是忽略hibernateLazyInitializer属性）要加载被lazy的，也就是many-to-one的one端的pojo上

这行代码的作用在于告诉你的jsonplug组件，在将你的代理对象转换为json对象时，忽略value对应的数组中的属性，即：

通过java的反射机制将pojo转换成json的，属性，(通过java的反射机制将pojo转换成json的，)

"hibernateLazyInitializer","handler","fieldHandler",（如果你想在转换的时候继续忽略其他属性，可以在数组中继续加入）
```