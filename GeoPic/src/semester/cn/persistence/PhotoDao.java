package semester.cn.persistence;

import semester.cn.domain.PhotoInfo;

public interface PhotoDao {
    public PhotoInfo getPhotoByPath(PhotoInfo photoInfo);
    public boolean insertPhotoInfoToDB(PhotoInfo photoInfo);
}
