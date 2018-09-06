package com.example.administrator.maps.main.model;

import com.amap.api.services.core.LatLonPoint;
import com.amap.api.services.core.PoiItem;
import com.amap.api.services.poisearch.Photo;
import com.amap.api.services.poisearch.PoiItemExtension;
import com.example.administrator.maps.common.db.LovePoiDao;
import com.example.administrator.maps.common.db.SortBeanDao;
import com.example.administrator.maps.init.base.MyApplication;
import com.example.administrator.maps.love.model.SortBean;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ldq on 2018/6/1.
 */

@Entity
public class LovePoi {
    //返回POI 的id，即其唯一标识。
    @Id
    private String poiId;
    //返回POI 的行政区划代码。
    private String adCode;
    //返回POI 的行政区划名称。
    private String adName;
    //返回POI的所在商圈。
    private String businessArea;
    //返回POI的城市编码
    private String cityCode;
    //返回POI的城市名称。
    private String cityName;
    //返回逆地理编码查询时POI坐标点相对于地理坐标点的方向
    private String direction;
    //POI 距离中心点的距离。
    private int distance;
    //返回POI的电子邮件地址。
    private String email;
    //返回POI的经纬度坐标()，如果有建议使用它们作为导航的起终点
    private double lat;
    private double lng;
    //返回POI的停车场类型
    private String parkingType;
    //返回POI的图片信息。只取第一张图片的url
    private String photos;
    //返回POI的扩展信息。返回POI的评分与返回POI的营业时间, 这里只取营业时间
    private String poiExtension;
    //返回POI的邮编。
    private String postcode;
    //直辖市/特别行政区编码。
    private String provinceCode;
    //特别行政区名称 。
    private String provinceName;
    //返回POI的地址。
    private String snippet;
    //返回POI的电话号码。
    private String tel;
    //返回POI的名称。
    private String title;
    //返回兴趣点类型编码。
    private String typeCode;
    //返回POI 的类型描述。
    private String typeDes;
    //返回POI的网址。
    private String website;

    /**
     * 以下字段为自定义字段
     */
    // poi 所属类型
    private Long sortId;

    /**
     * 以下字段暂时弃之不用
     * private String subPois;//子POI信息获取。
     * private LatLonPoint enter;返回POI入口经纬度。
     * private String exit;返回POI出口经纬度。
     * private String indoorData;返回POI的室内信息，如楼层、建筑物。
     **/

    public LovePoi(PoiItem poiItem) {
        this.adCode = poiItem.getAdCode();
        this.adName = poiItem.getAdName();
        this.businessArea = poiItem.getBusinessArea();
        this.cityCode = poiItem.getCityCode();
        this.cityName = poiItem.getCityName();
        this.direction = poiItem.getDirection();
        this.distance = poiItem.getDistance();
        this.email = poiItem.getEmail();
        this.lat = poiItem.getLatLonPoint().getLatitude();
        this.lng = poiItem.getLatLonPoint().getLongitude();
        this.parkingType = poiItem.getParkingType();
        if (null != poiItem.getPhotos() && poiItem.getPhotos().size() >= 1) {
            this.photos = poiItem.getPhotos().get(0).getUrl();
        }else {
            this.photos = "";
        }
        this.poiExtension = poiItem.getPoiExtension().getOpentime();
        this.poiId = poiItem.getPoiId();
        this.postcode = poiItem.getPostcode();
        this.provinceCode = poiItem.getProvinceCode();
        this.provinceName = poiItem.getProvinceName();
        this.snippet = poiItem.getSnippet();
        this.tel = poiItem.getTel();
        this.title = poiItem.getTitle();
        this.typeCode = poiItem.getTypeCode();
        this.typeDes = poiItem.getTypeDes();

        //this.subPois = poiItem.getSubPois();
        //this.enter = poiItem.getEnter();
        //this.exit = poiItem.getExit();
        //this.indoorData = poiItem.getIndoorData();
    }


    @Generated(hash = 1146418021)
    public LovePoi(String poiId, String adCode, String adName, String businessArea,
            String cityCode, String cityName, String direction, int distance,
            String email, double lat, double lng, String parkingType, String photos,
            String poiExtension, String postcode, String provinceCode,
            String provinceName, String snippet, String tel, String title,
            String typeCode, String typeDes, String website, Long sortId) {
        this.poiId = poiId;
        this.adCode = adCode;
        this.adName = adName;
        this.businessArea = businessArea;
        this.cityCode = cityCode;
        this.cityName = cityName;
        this.direction = direction;
        this.distance = distance;
        this.email = email;
        this.lat = lat;
        this.lng = lng;
        this.parkingType = parkingType;
        this.photos = photos;
        this.poiExtension = poiExtension;
        this.postcode = postcode;
        this.provinceCode = provinceCode;
        this.provinceName = provinceName;
        this.snippet = snippet;
        this.tel = tel;
        this.title = title;
        this.typeCode = typeCode;
        this.typeDes = typeDes;
        this.website = website;
        this.sortId = sortId;
    }


    @Generated(hash = 9223789)
    public LovePoi() {
    }
    

    public String getAdCode() {
        return adCode;
    }

    public void setAdCode(String adCode) {
        this.adCode = adCode;
    }

    public String getAdName() {
        return adName;
    }

    public void setAdName(String adName) {
        this.adName = adName;
    }

    public String getBusinessArea() {
        return businessArea;
    }

    public void setBusinessArea(String businessArea) {
        this.businessArea = businessArea;
    }

    public String getCityCode() {
        return cityCode;
    }

    public void setCityCode(String cityCode) {
        this.cityCode = cityCode;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getDirection() {
        return direction;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }

    public int getDistance() {
        return distance;
    }

    public void setDistance(int distance) {
        this.distance = distance;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLng() {
        return lng;
    }

    public void setLng(double lng) {
        this.lng = lng;
    }

    public String getParkingType() {
        return parkingType;
    }

    public void setParkingType(String parkingType) {
        this.parkingType = parkingType;
    }

    public String getPhotos() {
        return photos;
    }

    public void setPhotos(String photos) {
        this.photos = photos;
    }

    public String getPoiExtension() {
        return poiExtension;
    }

    public void setPoiExtension(String poiExtension) {
        this.poiExtension = poiExtension;
    }

    public String getPoiId() {
        return poiId;
    }

    public void setPoiId(String poiId) {
        this.poiId = poiId;
    }

    public String getPostcode() {
        return postcode;
    }

    public void setPostcode(String postcode) {
        this.postcode = postcode;
    }

    public String getProvinceCode() {
        return provinceCode;
    }

    public void setProvinceCode(String provinceCode) {
        this.provinceCode = provinceCode;
    }

    public String getProvinceName() {
        return provinceName;
    }

    public void setProvinceName(String provinceName) {
        this.provinceName = provinceName;
    }

    public String getSnippet() {
        return snippet;
    }

    public void setSnippet(String snippet) {
        this.snippet = snippet;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTypeCode() {
        return typeCode;
    }

    public void setTypeCode(String typeCode) {
        this.typeCode = typeCode;
    }

    public String getTypeDes() {
        return typeDes;
    }

    public void setTypeDes(String typeDes) {
        this.typeDes = typeDes;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public boolean equals(Object var1) {
        if(this == var1) {
            return true;
        } else if(var1 == null) {
            return false;
        } else if(this.getClass() != var1.getClass()) {
            return false;
        } else {
            PoiItem var2 = (PoiItem)var1;
            if(this.poiId == null) {
                if(var2.getPoiId() != null) {
                    return false;
                }
            } else if(!this.poiId.equals(var2.getPoiId())) {
                return false;
            }

            return true;
        }
    }

    public int hashCode() {
        boolean var1 = true;
        byte var2 = 1;
        int var3 = 31 * var2 + ( this.poiId == null ? 0 : this.poiId.hashCode() );
        return var3;
    }

    @Override
    public String toString() {
        return "LovePoi{" +
                "poiId='" + poiId + '\'' +
                ", adCode='" + adCode + '\'' +
                ", adName='" + adName + '\'' +
                ", businessArea='" + businessArea + '\'' +
                ", cityCode='" + cityCode + '\'' +
                ", cityName='" + cityName + '\'' +
                ", direction='" + direction + '\'' +
                ", distance=" + distance +
                ", email='" + email + '\'' +
                ", lat=" + lat +
                ", lng=" + lng +
                ", parkingType='" + parkingType + '\'' +
                ", photos='" + photos + '\'' +
                ", poiExtension='" + poiExtension + '\'' +
                ", postcode='" + postcode + '\'' +
                ", provinceCode='" + provinceCode + '\'' +
                ", provinceName='" + provinceName + '\'' +
                ", snippet='" + snippet + '\'' +
                ", tel='" + tel + '\'' +
                ", title='" + title + '\'' +
                ", typeCode='" + typeCode + '\'' +
                ", typeDes='" + typeDes + '\'' +
                ", website='" + website + '\'' +
                ", sortId=" + sortId +
                '}';
    }

    public Long getSortId() {
        return this.sortId;
    }


    public void setSortId(Long sortId) {
        this.sortId = sortId;
    }

    public PoiItem tranPoiItem() {
        LatLonPoint point = new LatLonPoint(this.lat, this.lng);

        List<Photo> photos = new ArrayList<>();
        Photo photo = new Photo();
        photo.setUrl(this.photos);
        photos.add(photo);

        PoiItemExtension poiItemExtension = new PoiItemExtension("", this.poiExtension);

        PoiItem item = new PoiItem(this.poiId, point, this.title, this.snippet);
        item.setAdCode(this.adCode);
        item.setAdName(this.adName);
        item.setBusinessArea(this.businessArea);
        item.setCityCode(this.cityCode);
        item.setCityName(this.cityName);
        item.setDirection(this.direction);
        item.setDistance(this.distance);
        item.setEmail(this.email);
        item.setParkingType(this.parkingType);
        item.setPhotos(photos);
        item.setPoiExtension(poiItemExtension);
        item.setPostcode(this.postcode);
        item.setProvinceCode(this.provinceCode);
        item.setProvinceName(this.provinceName);
        item.setTel(this.tel);
        item.setTypeCode(this.typeCode);
        item.setTypeDes(this.typeDes);
        return item;
    }

    /**
     * 根据外键sortId 查询Love数据
     * @param application application
     * @param s sortId
     * @return
     */
    public static List<LovePoi> SelectLoveFromDB(MyApplication application, String where, String s) {
        LovePoiDao lovePoiDao = application.getDaoSession().getLovePoiDao();
        return lovePoiDao.queryRaw(where, s);
    }

    /**
     * 将love点写入数据库
     * @param application application
     * @param lovePoi 待写入数据
     * @param string 数据分类名称, 根据名称查询外键
     */
    public static void InsertLoveFromDB(MyApplication application, LovePoi lovePoi, String string) {
        SortBeanDao sortBeanDao = application.getDaoSession().getSortBeanDao();
        SortBean sortBean = sortBeanDao.queryRaw("where TITLE = ?", string).get(0);
        if (!sortBean.getTitle().isEmpty()){
            lovePoi.setSortId(sortBean.getId());
            LovePoiDao lovePoiDao = application.getDaoSession().getLovePoiDao();
            lovePoiDao.insert(lovePoi);
        }
    }

    public static void DeleteLoveFromDB(MyApplication application, String where, String poiId) {
        LovePoiDao lovePoiDao = application.getDaoSession().getLovePoiDao();
        lovePoiDao.deleteByKey(poiId);
    }
}
