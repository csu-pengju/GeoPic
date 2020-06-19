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

## modified by Daisy
### 2020.06.12 15:16
1. 完成了初始化照片墙
2. 完成了人脸检测并将检测到的人脸保存到项目目录下和上传到数据库
3. 我放弃使用opencv进行人脸检测了
4. 下一步写将保存的人脸返回客户端显示，让用户输入人脸标签；
5. 再写创建faceAPI中的faceSet，用于存储检测到的face_token,为人脸搜索做准备。

1. Completed the initialization of photo wall
2. Completed face detection and saved the detected faces to the project directory and uploaded them to the database
3. I gave up using Opencv for face detection
4. Next I'll write the saved face back to the client display, and let the user input face label;
5. Write again to create faceSet in faceAPI to store the detected FACE_Token in preparation for face search.

## modified by Daisy
### 2020.06.19 13：13
1. 创建人脸数据集FaceSet，用于存储所有检测到的人脸的face_token
2. 完成人脸检测、人脸搜索、人脸添加的过程。并且可以从后台返回添加的人脸的路径
3. 待完成：将新添加的人脸显示在客户端，由用户输入人物标签，完成人物的标注

1. Create a face data set FaceSet to store the FACE_token for all detected faces
2. Complete the process of face detection, face search and face addition.And the path of the added face can be returned from the background.
3. To be completed: The newly added face will be displayed on the client, and the user will input the character label to complete the character labeling

## modified by Daisy
### 2020.06.19 20:00
1. 完成在客户端的人脸标签输入，当检测到人脸，并且数据库中没有这个人时，弹出模态框，用户输入人物标签
2. 完成在服务端的人脸标签入库
3. 下一步：将人脸id关联到相应的照片

1. Complete the face label input in the client. When the face is detected and there is no such person in the database, a modal box pops up and the user enters the character label
2. Complete the warehousing of face tags on the server
3. Next: Associate the face ID with the corresponding photo