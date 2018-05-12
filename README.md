# RetrofitMaster


**项目使用MVP架构并结合rxjava，使用retrofit+okhttp处理网络，应用dragger2+butterKnife简化代码，使用rxbus处理进度响应**



****功能介绍****：

get、post请求以及json解析测试；
带有进度的上传、下载图片测试；
服务器ip配置；
图片预览；

## Get和Post

测试get和post方法，服务端返回json，使用retrofit

![image](json.gif)

## 带进度上传图片
列出assets/uploadfiles目录下图片文件，选中后支持带进度上传。传输结果可在服务端/upload_saved目录下查看。

![image](upoad.gif)

## 带进度下载图片
先获取服务端图片列表，解析json，在列表中展示缩略图，选中后支持带进度下载。下载结果保存在设备的cache/images目录下

![image](download.gif)

## 服务端代码说明
服务端代码保存在/server/server.war（如果使用tomcat，复制到/webapps目录下，然后重启本地服务器），进入app后修改服务主机ip地址。

相关链接：
```
上传图片url
http://[服务器ip]:8080/server/UploadServlet

```
```
下载图片url
http://[服务器ip]:8080/server/FileListServlet

```
```
返回json url
http://[服务器ip]:8080/server/WebApiServlet

> - get方式
> - post方式：参数name,code

参数name、code
```****