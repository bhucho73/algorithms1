import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;

import java.util.Arrays;
import java.util.ArrayList;
import java.util.Collections;

public class FastCollinearPoints {
    private final LineSegment[] lineSeg;

    public FastCollinearPoints(Point[] points) {   // finds all line segments containing 4 points

        //is null?
        this.isNull(points);

        // sorted array of points
        Point[] sorted = points.clone();
        Arrays.sort(sorted);
        // points = points.clone();
        // dups?
        this.hasDups(sorted);
        // Point[] sortedBySlope = Arrays.copyOf(points);
        ArrayList<LineSegment> segs = new ArrayList<>();
        // StdOut.println("INPUT SORTED:" + Arrays.toString(sorted));
        for (int i = 0; i < sorted.length; i++) {
            // Point[] sortedBySlope = Arrays.copyOf(points);
            Point[] sortedBySlope = sorted.clone();

            Point pt = sorted[i];
            // StdOut.println("pt:" + pt.toString());
            Arrays.sort(sortedBySlope, pt.slopeOrder()); // sort points based on slope
            // StdOut.println("INPUT SORTED By SLOPE:" + Arrays.toString(sortedBySlope));
            int a = 1; // when sorted by slope, the point pt is the zeroth element
            while (a < sorted.length) {
                ArrayList<Point> arrayList = new ArrayList<>();
                final double refSlope = pt.slopeTo(sortedBySlope[a]);
                // arrayList.add(sortedBySlope[a]);
                // a++;
                // StdOut.println("Coll:" + arrayList.toString());
                do {
                    //  StdOut.println("ref slope:" + refSlope + "slope:" + pt.slopeTo(sortedBySlope[a]));
                    arrayList.add(sortedBySlope[a++]);
                } while (a < sorted.length && pt.slopeTo(sortedBySlope[a]) == refSlope);
                //   {
                //      arrayList.add(sortedBySlope[a++]);
                //  StdOut.println("Coll:" + arrayList.toString());
                // a++;
                // }
                if (arrayList.size() >= 3) {
                    // StdOut.println("final Coll:" + arrayList.toString());
                    // Collections.sort(arrayList);
                    // Point pLo = null;
                    // Point pHi = null;
                    if (pt.compareTo(arrayList.get(0)) < 0) {
                        // pLo = pt;
                        // pHi = arrayList.get(arrayList.size() - 1);
                        // StdOut.println("sorted Coll:" + arrayList.toString());
                        // StdOut.println("Lo:" + pt + "Hi:" + arrayList.get(arrayList.size() - 1));
                        segs.add(new LineSegment(pt, arrayList.get(arrayList.size() - 1)));
                    }
                }
            }
        }
        // this.lineSeg = arrayList.toArray();
        // this.lineSeg = new LineSegment[segs.size()];
        // Collections.sort(segs);
        // ArrayList<LineSegment> finalList = new ArrayList<>();
        // finalList.add(segs.get(0));
        // for (int x = 0; x < segs.size(); x++ {
        //   if ()
        // }
        this.lineSeg = segs.toArray(new LineSegment[0]); // using dummy array just to prevent compile error
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
        try {
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
            FastCollinearPoints fastColl = new FastCollinearPoints(points);
            for (LineSegment segment : fastColl.segments()) {
                StdOut.println(segment);
                segment.draw();
            }
            StdDraw.show();

            points[0].slopeOrder().compare(points[1], points[2]);
            points[1].slopeOrder().compare(points[2], points[0]);
            // StdOut.println("pt1:" + points[0] + "pt2:" + points[1] + "pt3:" + points[2]);
            // points[0].slopeOrder().compare(points[1], null);
            
        } catch (NullPointerException iae) {
            StdOut.println(iae.getMessage());
        }

    }
}
