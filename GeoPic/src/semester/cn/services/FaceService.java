package semester.cn.services;

import semester.cn.domain.FaceInfo;
import semester.cn.persistence.FaceDao;
import semester.cn.persistence.impl.FaceDaoImpl;

public class FaceService {
    public FaceDao faceDao;

    //用来插入人脸的路径、face_token信息
    public boolean insertFaceInfo(FaceInfo faceInfo){
        faceDao = new FaceDaoImpl();
        return faceDao.insertFaceInfoToDB(faceInfo);
    }

    //更新人脸的标签
    public boolean updateFaceLabelInfo(FaceInfo faceInfo){
        faceDao = new FaceDaoImpl();
        return faceDao.updateFaceLabelToDB(faceInfo);
    }

    //根据人物token获取人物的id
    public int getFaceIdAccordingFaceToken(FaceInfo faceInfo){
        faceDao = new FaceDaoImpl();
        return faceDao.getFaceIdAccordingFaceToken(faceInfo);
    }

    //根据人物path获取人物的id
    public  int getFaceIdAccordingFacePath(FaceInfo faceInfo){
        faceDao = new FaceDaoImpl();
        return faceDao.getFaceIdAccordingFacePath(faceInfo);
    }

}
