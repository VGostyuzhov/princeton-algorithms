import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

import java.util.ArrayList;
import java.util.Arrays;

public class FastCollinearPoints {
    private final LineSegment[] segments;

    public FastCollinearPoints(Point[] points)     // finds all line segments containing 4 or more points
    {
        if (points == null) {
            throw new IllegalArgumentException();
        }
        for (int i = 0; i < points.length; i++) {
            if (points[i] == null) {
                throw new IllegalArgumentException();
            }
        }

        ArrayList<LineSegment> segmentsList = new ArrayList<LineSegment>();

        Point[] pointsTemp = points.clone();
        Arrays.sort(pointsTemp);
        for (int i = 0; i < pointsTemp.length - 1; i++) {
            if (pointsTemp[i].compareTo(pointsTemp[i + 1]) == 0) {
                throw new IllegalArgumentException();
            }
        }
        for (Point p : points) {
            Arrays.sort(pointsTemp, p.slopeOrder());
            int firstPoint = 0;
            int lastPoint = 0;
            int max = 0;
            boolean head = true;
            for (int j = 1; j < pointsTemp.length - 1; j++) {

                double slopeFirst = p.slopeTo(pointsTemp[j]);
                double slopeSecond = p.slopeTo(pointsTemp[j + 1]);
                if (Double.compare(slopeFirst, slopeSecond) == 0) {
                    if (head) {
                        firstPoint = j;
                        lastPoint = j + 1;
                        head = false;
                        max = firstPoint;
                    } else if (j - max == 1) { // Check if we still on the current segment
                        lastPoint++;
                        max++;
                    }
                } else if ((lastPoint - firstPoint) >= 2) {
                    Point[] pointInSegment = new Point[lastPoint - firstPoint + 1];
                    for (int v = 0; v < pointInSegment.length; v++) {
                        pointInSegment[v] = pointsTemp[firstPoint];
                        firstPoint++;
                    }
                    Arrays.sort(pointInSegment);
                    if (p.compareTo(pointInSegment[0]) < 0) {
                        LineSegment newSegment = new LineSegment(p, pointInSegment[pointInSegment.length - 1]);
                        segmentsList.add(newSegment);
                    }
                    head = true;
                } else {
                    head = true;
                }
                if ((j == pointsTemp.length - 2) && (lastPoint - firstPoint) >= 2) {
                    Point[] pointInSegment = new Point[lastPoint - firstPoint + 1];
                    for (int v = 0; v < pointInSegment.length; v++) {
                        pointInSegment[v] = pointsTemp[firstPoint];
                        firstPoint++;
                    }
                    Arrays.sort(pointInSegment);
                    if (p.compareTo(pointInSegment[0]) < 0) {
                        //StdOut.println("Segment");
                        LineSegment newSegment = new LineSegment(p, pointInSegment[pointInSegment.length - 1]);
                        segmentsList.add(newSegment);
                        //StdOut.println(newSegment);
                    }
                }
            }
        }
        segments = segmentsList.toArray(new LineSegment[segmentsList.size()]);
    }

    public int numberOfSegments()        // the number of line segments
    {
        return segments.length;

    }

    public LineSegment[] segments()                // the line segments
    {
        return segments.clone();

    }

    public static void main(String[] args) {

        Point[] points = new Point[20];
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 4; j++) {
                points[i * 4 + j] = new Point(i, j);
            }
        }

        // draw the points
        StdDraw.enableDoubleBuffering();
        StdDraw.setXscale(0, 10);
        StdDraw.setYscale(0, 10);
        for (Point p : points) {
            p.draw();
        }
        StdDraw.show();

        // print and draw the line segments
        FastCollinearPoints collinear = new FastCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();
    }
}
