package org.extvos.common.geo;

/**
 * 四边形
 *
 * @author Mingcai SHEN
 */
public class Quadrangle {
    public final Point p1;
    public final Point p2;
    public final Point p3;
    public final Point p4;

    public Quadrangle(Point p1, Point p2, Point p3, Point p4) {
        this.p1 = p1;
        this.p2 = p2;
        this.p3 = p3;
        this.p4 = p4;
    }

    /**
     * 计算四边形周长
     *
     * @return 周长 单位：公里/千米
     */
    public double perimeter() {
        return new Segment(p1, p2).getDistance() + new Segment(p2, p3).getDistance() + new Segment(p3, p4).getDistance() + new Segment(p4, p1).getDistance();
    }

    /**
     * 计算四边形面积
     *
     * @return 面积 单位：平方公里/平方千米
     */
    public double area() {
        Triangle t1 = new Triangle(p1, p2, p3);
        Triangle t2 = new Triangle(p1, p3, p4);
        return t1.area() + t2.area();
    }
}
