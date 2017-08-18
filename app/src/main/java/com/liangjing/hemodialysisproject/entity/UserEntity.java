package com.liangjing.hemodialysisproject.entity;


import org.greenrobot.greendao.DaoException;
import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Property;
import org.greenrobot.greendao.annotation.ToMany;

import java.util.List;
import com.liangjing.hemodialysisproject.DaoSession;
import com.liangjing.hemodialysisproject.PatientSchemeEntityDao;
import com.liangjing.hemodialysisproject.UserEntityDao;

/**
 * Created by liangjing on 2017/8/17.
 *
 * function:用户个人资料实体类
 */

@Entity
public class UserEntity {

    @Id(autoincrement = true)  //注意：从1开始递增
    private Long id;

    @Property(nameInDb = "userName")
    private String userName;

    @Property(nameInDb = "realName")
    private String realName;

    @Property(nameInDb = "userGender")
    private String userGender;

    @Property(nameInDb = "userLocation")
    private String userLocation;

    @Property(nameInDb = "cellPhone")
    private String cellPhone;        //手机号码

    @Property(nameInDb = "diagnosisNumber")
    private String diagnosisNumber;   //诊断号

    @Property(nameInDb = "idNumber")
    private String idNumber;

    @Property(nameInDb = "userBirthday")
    private String userBirthday;

    @ToMany(referencedJoinProperty = "schemeId")
    private List<PatientSchemeEntity> schemeLiist;

    /** Used to resolve relations */
    @Generated(hash = 2040040024)
    private transient DaoSession daoSession;

    /** Used for active entity operations. */
    @Generated(hash = 1814575071)
    private transient UserEntityDao myDao;

    @Generated(hash = 1630229926)
    public UserEntity(Long id, String userName, String realName, String userGender,
            String userLocation, String cellPhone, String diagnosisNumber,
            String idNumber, String userBirthday) {
        this.id = id;
        this.userName = userName;
        this.realName = realName;
        this.userGender = userGender;
        this.userLocation = userLocation;
        this.cellPhone = cellPhone;
        this.diagnosisNumber = diagnosisNumber;
        this.idNumber = idNumber;
        this.userBirthday = userBirthday;
    }

    @Generated(hash = 1433178141)
    public UserEntity() {
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserName() {
        return this.userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getRealName() {
        return this.realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getUserGender() {
        return this.userGender;
    }

    public void setUserGender(String userGender) {
        this.userGender = userGender;
    }

    public String getUserLocation() {
        return this.userLocation;
    }

    public void setUserLocation(String userLocation) {
        this.userLocation = userLocation;
    }

    public String getCellPhone() {
        return this.cellPhone;
    }

    public void setCellPhone(String cellPhone) {
        this.cellPhone = cellPhone;
    }

    public String getDiagnosisNumber() {
        return this.diagnosisNumber;
    }

    public void setDiagnosisNumber(String diagnosisNumber) {
        this.diagnosisNumber = diagnosisNumber;
    }

    public String getIdNumber() {
        return this.idNumber;
    }

    public void setIdNumber(String idNumber) {
        this.idNumber = idNumber;
    }

    public String getUserBirthday() {
        return this.userBirthday;
    }

    public void setUserBirthday(String userBirthday) {
        this.userBirthday = userBirthday;
    }

    /**
     * To-many relationship, resolved on first access (and after reset).
     * Changes to to-many relations are not persisted, make changes to the target entity.
     */
    @Generated(hash = 1966845657)
    public List<PatientSchemeEntity> getSchemeLiist() {
        if (schemeLiist == null) {
            final DaoSession daoSession = this.daoSession;
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            PatientSchemeEntityDao targetDao = daoSession
                    .getPatientSchemeEntityDao();
            List<PatientSchemeEntity> schemeLiistNew = targetDao
                    ._queryUserEntity_SchemeLiist(id);
            synchronized (this) {
                if (schemeLiist == null) {
                    schemeLiist = schemeLiistNew;
                }
            }
        }
        return schemeLiist;
    }

    /** Resets a to-many relationship, making the next get call to query for a fresh result. */
    @Generated(hash = 1799350435)
    public synchronized void resetSchemeLiist() {
        schemeLiist = null;
    }

    /**
     * Convenient call for {@link org.greenrobot.greendao.AbstractDao#delete(Object)}.
     * Entity must attached to an entity context.
     */
    @Generated(hash = 128553479)
    public void delete() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        myDao.delete(this);
    }

    /**
     * Convenient call for {@link org.greenrobot.greendao.AbstractDao#refresh(Object)}.
     * Entity must attached to an entity context.
     */
    @Generated(hash = 1942392019)
    public void refresh() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        myDao.refresh(this);
    }

    /**
     * Convenient call for {@link org.greenrobot.greendao.AbstractDao#update(Object)}.
     * Entity must attached to an entity context.
     */
    @Generated(hash = 713229351)
    public void update() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        myDao.update(this);
    }

    /** called by internal mechanisms, do not call yourself. */
    @Generated(hash = 287999134)
    public void __setDaoSession(DaoSession daoSession) {
        this.daoSession = daoSession;
        myDao = daoSession != null ? daoSession.getUserEntityDao() : null;
    }
}
