# RetrofitMaster


项目使用**MVP**架构并结合**rxjava**，使用**retrofit+okhttp**处理网络，应用**dragger2+butterKnife**简化代码，使用**rxbus**处理进度响应。



****功能介绍****：

1. get、post请求以及json解析测试；
2. 带有进度的上传、下载图片测试；
3. 服务器ip配置；
4. 图片预览；


## 配置服务器ip

app内修改服务器ip地址

![image](https://github.com/gu0-kim/RetrofitMaster/blob/master/screen/config.gif)

## Get和Post

测试get和post方法，服务端返回json

![image](https://github.com/gu0-kim/RetrofitMaster/blob/master/screen/json.gif)

## 带进度上传图片
列出assets/uploadfiles目录下图片文件，选中后支持带进度上传。传输结果可在服务端/upload_saved目录下查看。

![image](https://github.com/gu0-kim/RetrofitMaster/blob/master/screen/upload.gif)

## 带进度下载图片
先获取服务端图片列表，解析json，在列表中展示缩略图，选中后支持带进度下载。下载结果保存在设备的cache/images目录下。

![image](https://github.com/gu0-kim/RetrofitMaster/blob/master/screen/download.gif)

## 服务端代码说明
服务端代码保存在/server/server.war（如果使用tomcat，复制到/webapps目录下，运行服务端），进入app后修改服务主机ip地址。

相关链接：
- 上传图片url
```
http://[服务器ip]:8080/server/UploadServlet
```
- 下载图片url
```
http://[服务器ip]:8080/server/FileListServlet
```
- 返回json url
```
http://[服务器ip]:8080/server/WebApiServlet
```
> - get方式
> - post方式：参数name,code