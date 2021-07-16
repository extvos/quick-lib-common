package plus.extvos.common.geo;

/**
 * @author Mingcai SHEN
 */
public class Point {
    public Double x;
    public Double y;

    public Point(){
        x = 0.0;
        y = 0.0;
    }

    public Point(Double x, Double y) {
        this.x = x;
        this.y = y;
    }

    public Double getX() {
        return x;
    }

    public void setX(Double x) {
        this.x = x;
    }

    public Double getY() {
        return y;
    }

    public void setY(Double y) {
        this.y = y;
    }
}
