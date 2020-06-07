package semester.cn.services;

import semester.cn.domain.PhotoInfo;
import semester.cn.persistence.PhotoDao;
import semester.cn.persistence.impl.photoDaoIml;

import java.util.HashMap;
import java.util.Map;

public class PhotoService {
    private PhotoDao photoDao;
    public boolean insertPhotoInfo(PhotoInfo photoInfo){
        System.out.println("photoservie"+photoInfo);
        photoDao = new photoDaoIml();
        return  photoDao.insertPhotoInfoToDB(photoInfo);
    }
    public HashMap<String,String> getPhotoGPSAndPath(){
        photoDao = new photoDaoIml();
        return photoDao.getPhotoGPSAndPath();
    }
}
