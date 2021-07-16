package plus.extvos.common.geo;

/**
 * @author shenmc
 */
public class Location {
    private Double lat;
    private Double lng;
    private String remark;

    public Location() {
        lat = 0.0;
        lng = 0.0;
    }

    public Location(Double x, Double y) {
        this.lat = x;
        this.lng = y;
    }

    public Location(Double x, Double y, String remark) {
        this.lat = x;
        this.lng = y;
        this.remark = remark;
    }

    public Double getLat() {
        return lat;
    }

    public void setLat(Double lat) {
        this.lat = lat;
    }

    public Double getLng() {
        return lng;
    }

    public void setLng(Double lng) {
        this.lng = lng;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
