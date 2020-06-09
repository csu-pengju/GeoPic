# GeoPic
A  Java Web Project for WebGIS course .We call it as "GeoPic". In fact, it is Just a simple photoManagement System.Welcome to Everyone!

# Update Daily

## 2020.5.30 23：17
### modified by Daisy
1. new project and add some supports


## 2020.06.01 13：00
### modified by Daisy
1. Database table structure design

## 2020.06.01 23：00
### modified by Daisy
1. complish the registration and login.Now it can well work.
2. Next work is PhotoWall.

## 2020.06.02 12：35
### modified by Daisy
1. photoWall ui design. 
2. complete image-scroll and some component layout

## 2020.06.07 15：18
### modified by Daisy
1. 完成照片信息的提取
2. 完成照片基本信息（时间、地点、路径、GPS坐标）的入库
3. 未解决：照片数据在前后端的传递，故暂时无法将前端打开的数据复制到我们的项目文件夹下。
4. 数据存放位置：data/photos为原始照片，data/thumbs为缩略图。


1. Complete the extraction of photo information
2. Complete the storage of basic photo information (time, place, path and GPS coordinates)
3. Unresolved: The photo data is transferred in the front end and the back end, so the data opened in the front end cannot be copied to our project folder temporarily.
4. Data storage location: Data /photos is the original photo, and data/ Thumbs is the thumbnail.

5. 下一步我先写照片地图了，先搭框架，写一下简单的哈哈哈哈，然后再去细化。

## 2020.06.08 0：17
### modified by Daisy
1. load amap
2. getPhotoGSPAndPath from GeoPic database

## 2020.06.09 10:24
## modified by Daisy
1. 完成照片的地图可视化。但是样式目前较为简单,后面会修改滴
2. 下一步写照片检索，先搭框架，把基本内容写出来


1. Complete the map visualization of photos.However, the style is relatively simple and will be modified later
2. The next step is to write the photo retrieval, first frame, write out the basic content

## 2020.06.09 14：53
### modified by Daisy
1. 完成照片查询的前端界面设计
2. 下一步完成根据输入条件从后台取得相应的照片
3. 加了一个bootstrap库，本来想用它的下拉菜单的，但是由于涉及到其他样式的原因放弃了。

1. Complete the front-end interface design of the photoQuery.
2. The next step is to get the corresponding photos from the background according to the input conditions

## 2020.06.09 22：00
### modified by Daisy
1. 完成照片的空间查询、时间查询
2. 下一步完成查询结果展示

1. Complete the spatial and temporal query of photos
2. Complete the query result display next

## modified by Daisy
### 2020.06.10 0:26
1. 完成了照片数据从前端传递到后端，并且后端实现将base64数据转换为图片保存在特定的文件夹下。
2. 明天先写初始化照片墙，显示目录下保存的图片。

1. Completed the transfer of the photo data from the front end to the back end, and the back end implementation converted the base64 data into images and saved them in a specific folder.
2. Write the initialization photo wall tomorrow to display the images saved in the directory.
