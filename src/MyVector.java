public class MyVector {
    public double x;
    public double y;
    public double magnitude;
    public double unitX;
    public double unitY;

    public MyVector(double x1, double x2, double y1, double y2){
        x = x2 - x1;
        y = y2 - y1;
        magnitude = Math.sqrt(Math.pow(x,2) + Math.pow(y,2));
        calcUnitVector();
    }

    public MyVector(double x, double y){
        this.x = x;
        this.y = y;
        magnitude = Math.sqrt(Math.pow(x,2) + Math.pow(y,2));
        calcUnitVector();
    }
    public MyVector(Body node1, Body node2){
        x = node1.xMeters - node2.xMeters;
        y = node1.yMeters - node2.yMeters;
        magnitude = Math.sqrt(Math.pow(x,2) + Math.pow(y,2));
        calcUnitVector();
    }

    private void calcUnitVector(){
        unitX = x / magnitude;
        unitY = y / magnitude;
    }
    private double calcAverage(double one, double two){
        return (one + two) / 2;
    }

}

