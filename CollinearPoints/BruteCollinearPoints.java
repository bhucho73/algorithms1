import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;

import java.util.Arrays;
import java.util.ArrayList;

public class BruteCollinearPoints {
    private final LineSegment[] lineSeg;

    public BruteCollinearPoints(Point[] points) {   // finds all line segments containing 4 points

        // is null?
        this.isNull(points);

        // sorted array of points
        Point[] sorted = points.clone();
        Arrays.sort(sorted);
        // points = points.clone();
        // StdOut.println("sorted:" + Arrays.toString(points));
        // dups?
        this.hasDups(sorted);

        ArrayList<LineSegment> arrayList = new ArrayList<>();
        int n = sorted.length;
        Double refSlope = null;
        LineSegment seg;
        for (int i = 0; i < n - 3; i++) {
            Point ptI = sorted[i];
            for (int j = i + 1; j < n - 2; j++) {
                double slopeJ = ptI.slopeTo(sorted[j]);
                //  if (refSlope != null && refSlope.doubleValue() == slopeJ) continue;
                for (int k = j + 1; k < n - 1; k++) {
                    double slopeK = ptI.slopeTo(sorted[k]);
                    //    if (refSlope != null && refSlope.doubleValue() == slopeK) continue;
                    if (slopeJ == slopeK) {  // next loop only if the three points are collinear

                        Point collinear = null; // get the fartheest collinear point
                        for (int l = k + 1; l < n; l++) {
                            double slopeL = ptI.slopeTo(sorted[l]);
                            if (slopeJ == slopeL) {
                                // add to the list
                                // arrayList.add(new LineSegment(ptI, points[l]));
                                // seg = new LineSegment(ptI, points[l]);
                                collinear = sorted[l];
                            }
                        }
                        if (collinear != null) {
                            arrayList.add(new LineSegment(ptI, collinear));
                            // StdOut.println("collinear:" + arrayList.toString());
                            // refSlope = ptI.slopeTo(collinear);
                        }
                    }
                }
            }
        }
        // this.lineSeg = arrayList.toArray();
        // this.lineSeg = new LineSegment[arrayList.size()];
        //  this.lineSeg = arrayList.toArray(this.lineSeg);
        this.lineSeg = arrayList.toArray(new LineSegment[0]); //just using dummy array to get it compile
    }

    public int numberOfSegments() {       // the number of line segments
        return this.lineSeg.length;
    }

    public LineSegment[] segments() {     // the line segments
        return this.lineSeg.clone();
    }

    // check if points array or any point in the input is null
    private void isNull(Point[] ps) {
        if (ps == null) throw new IllegalArgumentException("Points array is null");
        for (Point p : ps) {
            if (p == null) throw new IllegalArgumentException("Null Point(s) found");
        }
    }

    // check if the input has duplicates
    private void hasDups(Point[] ps) {
        for (int x = 0; x < ps.length - 1; x++) {
            if (ps[x].compareTo(ps[x + 1]) == 0) throw new IllegalArgumentException("There are dups");
        }
    }

    // code as provided
    public static void main(String[] args) {

        // read the n points from a file
        In in = new In(args[0]);
        int n = in.readInt();
        Point[] points = new Point[n];
        for (int i = 0; i < n; i++) {
            int x = in.readInt();
            int y = in.readInt();
            points[i] = new Point(x, y);
        }

        // draw the points
        StdDraw.enableDoubleBuffering();
        StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768);
        for (Point p : points) {
            p.draw();
        }
        StdDraw.show();

        // print and draw the line segments
        BruteCollinearPoints bruteColl = new BruteCollinearPoints(points);

        for (LineSegment segment : bruteColl.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();
        // StdOut.println("noOfSegs:" + bruteColl.numberOfSegments());
        // StdOut.println("Segs:" + Arrays.toString(bruteColl.segments()));
        // LineSegment[] segs = bruteColl.segments();
        // segs[0] = segs[1];
        // StdOut.println("Segs:" + Arrays.toString(bruteColl.segments()));
    }
}
