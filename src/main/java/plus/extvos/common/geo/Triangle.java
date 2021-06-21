package plus.extvos.common.geo;

/**
 * 三角形
 *
 * @author Mingcai SHEN
 */
public class Triangle {
    public final Point p1;
    public final Point p2;
    public final Point p3;

    public Triangle(Point p1, Point p2, Point p3) {
        this.p1 = p1;
        this.p2 = p2;
        this.p3 = p3;
    }

    /**
     * 计算三角形周长
     *
     * @return 周长 单位：公里/千米
     */
    public double perimeter() {
        return new Segment(p1, p2).getDistance() + new Segment(p2, p3).getDistance() + new Segment(p3, p1).getDistance();
    }

    /**
     * 计算三角形面积
     *
     * @return 面积 单位：平方公里/平方千米
     */
    public double area() {
        double a = 0.0;
        Segment s1 = new Segment(p1, p2);
        Segment s2 = new Segment(p2, p3);
        Segment s3 = new Segment(p3, p1);
        double l1 = s1.getDistance();
        double l2 = s2.getDistance();
        double l3 = s3.getDistance();
        double p = (l1 + l2 + l3) / 2.0;
        return Math.sqrt(p * (p - l1) * (p - l2) * (p - l3));
    }
}
