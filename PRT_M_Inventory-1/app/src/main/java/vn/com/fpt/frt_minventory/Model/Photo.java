package vn.com.fpt.frt_minventory.Model;

import java.io.Serializable;

import io.realm.annotations.PrimaryKey;

/**
 * Created by ADMIN on 12/21/2017.
 */

public class Photo implements Serializable {
    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    String uri;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    private String id;
    public String getBitmap() {
        return bitmap;
    }

    public void setBitmap(String bitmap) {
        this.bitmap = bitmap;
    }

    String bitmap;

    public String getDocEntry() {
        return DocEntry;
    }

    public void setDocEntry(String docEntry) {
        DocEntry = docEntry;
    }

    public String getItemCode() {
        return ItemCode;
    }

    public void setItemCode(String itemCode) {
        ItemCode = itemCode;
    }

    public String getImageName() {
        return ImageName;
    }

    public void setImageName(String imageName) {
        ImageName = imageName;
    }

    public String getSerial() {
        return Serial;
    }

    public void setSerial(String serial) {
        Serial = serial;
    }

    public String getFileByte() {
        return FileByte;
    }

    public void setFileByte(String fileByte) {
        FileByte = fileByte;
    }

    public String getDeviceType() {
        return DeviceType;
    }

    public void setDeviceType(String deviceType) {
        DeviceType = deviceType;
    }

    String DocEntry;
    String ItemCode;
    String ImageName;
    String Serial;
    String FileByte;
    String DeviceType;
}