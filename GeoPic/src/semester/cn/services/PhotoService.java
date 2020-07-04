package semester.cn.services;

import org.opencv.photo.Photo;
import semester.cn.domain.PhotoInfo;
import semester.cn.persistence.PhotoDao;
import semester.cn.persistence.impl.photoDaoIml;

import java.util.ArrayList;
import java.util.HashMap;

public class PhotoService {
    private PhotoDao photoDao;

    /**
     *
     * @param photoInfo  上传照片的信息
     * @return 返回插入结果，true or false
     */
    public boolean insertPhotoInfo(PhotoInfo photoInfo){
        photoDao = new photoDaoIml();
        return  photoDao.insertPhotoInfoToDB(photoInfo);
    }

    /**
     * 照片地图，用于获取有地理坐标的所有照片的GPS坐标
     * @return  数据库中所有有地理坐标的照片的GPS坐标
     */
    public HashMap<String,String> getPhotoGPSAndPath(){
        photoDao = new photoDaoIml();
        return photoDao.getPhotoGPSAndPath();
    }

    /**
     * 用于获取给定时间段的符合条件的照片
     * @param startTime  查询的起始日期 2020-01-02
     * @param endTime   查询的截至日期 2002-06-09
     * @return 所有符合条件的照片的相对路径
     */
    public ArrayList<String> getTimeQueryPhotoPath(String startTime,String endTime){
        photoDao = new photoDaoIml();
        return photoDao.getTimeQueryPhotoPath(startTime,endTime);
    }

    /**
     * 用于获取给定地点的符合条件的照片
     * @param geo 查询照片的GPS坐标 'POINT(112 28)'
     * @param address  输入的查询地点字符串
     * @return  所有符合条件的照片的相对路径
     */
    public ArrayList<String> getPlaceQueryPhotoPath(String geo,String address){
        photoDao = new photoDaoIml();
        return photoDao.getPlaceQueryPhotoPath(geo,address);
    }

    /**
     * 用于获取初始化界面的照片墙显示的照片路径
     * @return 返回当前数据库中存储的所有照片的相对路径
     *
     */
    public ArrayList<String> getAllPhotoPath(){
        photoDao  = new photoDaoIml();
        return photoDao.getAllPhotoPath();
    }

    public  boolean getThumbs(){
        photoDao = new photoDaoIml();
        return photoDao.getThumbs();
    }

    public  int getPhotoIdAccordingPhotoPath(PhotoInfo photoInfo){
        photoDao = new photoDaoIml();
        return photoDao.getPhotoIdAcoordintPhotoPath(photoInfo);
    }
    public boolean insertPhotoLabel(PhotoInfo photoInfo){
        photoDao = new photoDaoIml();
        return photoDao.insertPhotoLabel(photoInfo);
    }

    public boolean insertPhotoFacesId(PhotoInfo photoInfo){
        photoDao = new photoDaoIml();
        return photoDao.insertPhotoFaceId(photoInfo);
    }

    public HashMap<String,String> getPhotoDetail(String photoPath){
        photoDao = new photoDaoIml();
        return photoDao.getPhotoDetail(photoPath);
    }

    public ArrayList<String> getSemanticQueryPhotoPath(PhotoInfo photoInfo){
        photoDao = new photoDaoIml();
        return photoDao.getSemanticQueryPhotoPath(photoInfo);
    }

    public ArrayList<String> getIntegratedQueryPhotoPath(PhotoInfo photoInfo,String startTime,String endTime,String geo,String address){
        photoDao = new photoDaoIml();
        return photoDao.getIntegratedQueryPhotoPath(photoInfo,startTime,endTime,geo,address);
    }

    public HashMap<String ,String >getAllTimeLinePhotoPath(){
        photoDao = new photoDaoIml();
        return  photoDao.getAllTimeLinePhotoPath();
    }
}
