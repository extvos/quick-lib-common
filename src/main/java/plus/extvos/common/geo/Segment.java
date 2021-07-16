package plus.extvos.common.geo;

/**
 * Segment 线段（分段），两个点之间的直线
 *
 * @author Mingcai SHEN
 * // 水平 1  竖直 2  上升  3 下降 4
 */
public class Segment {

    /**
     * 地球半径
     */
    private static final double EARTH_RADIUS = 6378.137;

    public final Point a;
    public final Point b;

    public Segment(Point p1, Point p2) {
        int lat = p1.x.compareTo(p2.x);
        if (lat > 0) {
            a = p2;
            b = p1;
        } else if (lat < 0) {
            a = p1;
            b = p2;
        } else {
            if (p1.y.compareTo(p2.y) > 0) {
                a = p2;
                b = p1;
            } else {
                a = p1;
                b = p2;
            }
        }
    }

    public Double tan() {
        if (a.x.compareTo(b.x) == 0) {
            return 0.0;
        }
        if (a.y.compareTo(b.y) == 0) {
            return 1.0;
        }
        return (b.x - a.x) / (b.y - a.y);
    }

    /**
     * 判断是否预 l2 线段平行
     *
     * @param l2 另一条线段
     * @return true 平行| false 不平行
     */
    public boolean isParallel(Segment l2) {
        return tan().compareTo(l2.tan()) == 0;
    }

    /**
     * 返回两条直线的相交点
     *
     * @param l2 另外一条线段
     * @return 相交点坐标
     */
    public Point crossPoint(Segment l2) {
        if (isParallel(l2)) {
            return new Point(0.0, 0.0);
        } else {
            Double x, y;
            x = (l2.b.y - this.b.y + this.b.x * ((this.b.y - this.a.y) / (this.b.x - this.a.x)) - l2.b.x * ((l2.b.y - l2.a.y) / (l2.b.x - l2.a.x))) / ((this.b.y - this.a.y) / (this.b.x - this.a.x) - (l2.b.y - l2.a.y) / (l2.b.x - l2.a.x));
            y = (l2.b.x - this.b.x + this.b.y * ((this.b.x - this.a.x) / (this.b.y - this.a.y)) - l2.b.y * ((l2.b.x - l2.a.x) / (l2.b.y - l2.a.y))) / ((this.b.x - this.a.x) / (this.b.y - this.a.y) - (l2.b.x - l2.a.x) / (l2.b.y - l2.a.y));
            return new Point(x, y);
        }
    }

    /**
     * 是否与l2相交
     *
     * @param l2 另一线段
     * @return true 相交|false 不相交
     */
    public boolean isCrossed(Segment l2) {
        if (outside(l2)) {
            // This make a little bit faster.
            return false;
        }
        if (isParallel(l2)) {
            return false;
        }
        Point pt = crossPoint(l2);
        return insideBox(pt) && l2.insideBox(pt);
    }

    public boolean outsideBox(Point p) {
        return (p.x < Polygon.min(a.x, b.x) || p.x > Polygon.max(a.x, b.x)) || (p.y < Polygon.min(a.y, b.y) || p.y > Polygon.max(a.y, b.y));
    }

    public boolean outsideBox(Segment l2) {
        return outsideBox(l2.a) && outsideBox(l2.b);
    }

    public boolean insideBox(Point p) {
        return ((p.x >= Polygon.min(a.x, b.x) && p.x <= Polygon.max(a.x, b.x)) && (p.y >= Polygon.min(a.y, b.y) && p.y <= Polygon.max(a.y, b.y)));
    }

    public boolean insideBox(Segment l2) {
        return insideBox(l2.a) && insideBox(l2.b);
    }

    public boolean outside(Segment l2) {
        return Polygon.min(l2.a.x, l2.b.x) > Polygon.max(a.x, b.x)
            || Polygon.max(l2.a.x, l2.b.x) < Polygon.min(a.x, b.x)
            || Polygon.min(l2.a.y, l2.b.y) > Polygon.max(a.y, b.y)
            || Polygon.max(l2.a.y, l2.b.y) < Polygon.min(a.y, b.y);
    }

    /**
     * 经纬度转化成弧度
     *
     * @param d 经度/纬度
     * @return
     */
    private static double rad(double d) {
        return d * Math.PI / 180.0;
    }

    /**
     * 计算两个坐标点之间的距离
     *
     * @param firstLatitude   第一个坐标的纬度
     * @param firstLongitude  第一个坐标的经度
     * @param secondLatitude  第二个坐标的纬度
     * @param secondLongitude 第二个坐标的经度
     * @return 返回两点之间的距离，单位：公里/千米
     */
    public static double getDistance(double firstLatitude, double firstLongitude,
                                     double secondLatitude, double secondLongitude) {
        double firstRadLat = rad(firstLatitude);
        double firstRadLng = rad(firstLongitude);
        double secondRadLat = rad(secondLatitude);
        double secondRadLng = rad(secondLongitude);

        double a = firstRadLat - secondRadLat;
        double b = firstRadLng - secondRadLng;
        double cal = 2 * Math.asin(Math.sqrt(Math.pow(Math.sin(a / 2), 2) + Math.cos(firstRadLat)
            * Math.cos(secondRadLat) * Math.pow(Math.sin(b / 2), 2))) * EARTH_RADIUS;
        return Math.round(cal * 10000d) / 10000d;
    }

    public static double getDistance(Point p1, Point p2) {
        return getDistance(p1.x, p1.y, p2.x, p2.y);
    }

    public static double getDistance(Point... points) {
        if (points.length <= 2) {
            return 0.0;
        }
        double distance = 0.0;
        for (int i = 0; i < points.length - 1; i++) {
            distance += getDistance(points[i], points[i + 1]);
        }
        return distance;
    }

    public double getDistance() {
        return getDistance(a, b);
    }
}
