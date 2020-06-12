package semester.cn.services;

import semester.cn.domain.FaceInfo;
import semester.cn.persistence.FaceDao;
import semester.cn.persistence.impl.FaceDaoImpl;

public class FaceService {
    public FaceDao faceDao;
    public boolean insertFaceInfo(FaceInfo faceInfo){
        faceDao = new FaceDaoImpl();
        return faceDao.insertFaceInfoToDB(faceInfo);
    }
}
