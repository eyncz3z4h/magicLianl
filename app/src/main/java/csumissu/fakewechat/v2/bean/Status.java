package csumissu.fakewechat.v2.bean;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import org.greenrobot.greendao.DaoException;
import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Keep;
import org.greenrobot.greendao.annotation.NotNull;
import org.greenrobot.greendao.annotation.ToMany;
import org.greenrobot.greendao.annotation.ToOne;

import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import csumissu.fakewechat.v2.dao.DaoSession;
import csumissu.fakewechat.v2.dao.StatusDao;
import csumissu.fakewechat.v2.dao.PictureDao;
import csumissu.fakewechat.v2.dao.UserDao;

/**
 * @author sunyaxi
 * @date 2016/11/17.
 */
@Entity
public class Status implements Parcelable {

    @Id
    private long id;
    private String text;
    private String source;
    @ToOne
    @NotNull
    @SerializedName("user")
    private User sender;
    @ToMany(referencedJoinProperty = "statusId")
    @SerializedName("pic_urls")
    private List<Picture> picUrls;
    @SerializedName("created_at")
    private Date createTime;

    @Inject
    public Status() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    @Keep
    public User getSender() {
        return sender;
    }

    @Keep
    public void setSender(User sender) {
        this.sender = sender;
    }

    @Keep
    public List<Picture> getPicUrls() {
        return picUrls;
    }

    public void setPicUrls(List<Picture> picUrls) {
        this.picUrls = picUrls;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    @Override
    public String toString() {
        return "Status{" +
                "id=" + id +
                ", text='" + text + '\'' +
                ", source='" + source + '\'' +
                ", sender=" + sender +
                ", picUrls=" + picUrls +
                ", createTime=" + createTime +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(this.id);
        dest.writeString(this.text);
        dest.writeString(this.source);
        dest.writeParcelable(this.sender, flags);
        dest.writeTypedList(this.picUrls);
        dest.writeLong(this.createTime != null ? this.createTime.getTime() : -1);
    }

    /** To-one relationship, returned entity is not refreshed and may carry only the PK property. */
    @Generated(hash = 306704630)
    public User peakSender() {
        return sender;
    }

    /** Resets a to-many relationship, making the next get call to query for a fresh result. */
    @Generated(hash = 1344839235)
    public synchronized void resetPicUrls() {
        picUrls = null;
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
    @Generated(hash = 1377533788)
    public void __setDaoSession(DaoSession daoSession) {
        this.daoSession = daoSession;
        myDao = daoSession != null ? daoSession.getStatusDao() : null;
    }

    protected Status(Parcel in) {
        this.id = in.readLong();
        this.text = in.readString();
        this.source = in.readString();
        this.sender = in.readParcelable(User.class.getClassLoader());
        this.picUrls = in.createTypedArrayList(Picture.CREATOR);
        long tmpCreateTime = in.readLong();
        this.createTime = tmpCreateTime == -1 ? null : new Date(tmpCreateTime);
    }

    @Generated(hash = 515785798)
    public Status(long id, String text, String source, Date createTime) {
        this.id = id;
        this.text = text;
        this.source = source;
        this.createTime = createTime;
    }

    public static final Creator<Status> CREATOR = new Creator<Status>() {
        @Override
        public Status createFromParcel(Parcel source) {
            return new Status(source);
        }

        @Override
        public Status[] newArray(int size) {
            return new Status[size];
        }
    };
    /** Used to resolve relations */
    @Generated(hash = 2040040024)
    private transient DaoSession daoSession;
    /** Used for active entity operations. */
    @Generated(hash = 1443046080)
    private transient StatusDao myDao;
    @Generated(hash = 2105518247)
    private transient boolean sender__refreshed;

}
