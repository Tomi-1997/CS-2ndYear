import edu.princeton.cs.introcs.StdDraw;
import edu.princeton.cs.introcs.StdStats;

import java.awt.*;
import java.util.HashSet;
import java.util.Set;

public class Cluster
{
    private Set<Point> clusters;
    private Set<Point> centers;
    private final int k;
    public static void main(String[] args)
    {
        StdDraw.setPenRadius(0.005);
        Cluster c = new Cluster(20);

        c.draw();
    }

    public Cluster(Set<Point> ps, int k)
    {
        this.clusters = ps;
        this.k = k;
        calcCenters();
    }

    public Cluster(int k)
    {
        this.k = k;
        generateCluster();
        calcCenters();
    }

    private void generateCluster()
    {
        this.clusters = new HashSet<>();
        int clusterNum = k * k;
        for (int i = 0; i < clusterNum; i++)
        {
            int clusterPoints = 100;
            int clusterRadius = 20;
            double centerX = Math.random() * 450 + 50;
            double centerY = Math.random() * 450 + 50;
            for (int j = 0; j < clusterPoints; j++)
            {
                double x = centerX - clusterRadius + Math.random()*2*clusterRadius;
                double y = centerY - clusterRadius + Math.random()*2*clusterRadius;
                this.clusters.add(new Point(x,y));
            }
        }
    }

    private void calcCenters()
    {
        this.centers = new HashSet<>();
        Point ptemp = null;
        for (Point p : this.clusters)
        {
            ptemp = p;
            this.centers.add(p);
            break;
        }
        this.clusters.remove(ptemp);
        while(this.centers.size() < k)
        {
            Point farthest = getMinFarthest();
            if (farthest == null)
                break;
            this.centers.add(farthest);
            this.clusters.remove(farthest);
        }
    }

    private Point getMinFarthest()
    {
        double ans = 0;
        Point p_ans = null;
        for (Point p : this.clusters)
        {
            double cand = getFarthest(p, this.centers).val;
            Point p_cand = getFarthest(p, this.centers).point;

            if (cand > ans)
            {
                ans = cand;
                p_ans = p_cand;
            }

        }
        return p_ans;
    }

    private Candidate getFarthest(Point p, Set<Point> pointSet)
    {
        Candidate c = new Candidate();
        for (Point pc : pointSet)
        {
            double d =  dist(p, pc);
            if (d < c.val)
            {
                c.val = d;
                c.point = p;
            }
        }
        return c;
    }

    static class Candidate
    {
        double val;
        Point point;
        public Candidate()
        {
            val = Double.MAX_VALUE;
            point = null;
        }
    }

    private double dist(Point pc, Point pcen)
    {
        double dx = (pc.x - pcen.x) * (pc.x - pcen.x);
        double dy = (pc.y - pcen.y) * (pc.y - pcen.y);
        return Math.pow(dx + dy,0.5);
    }

    public void draw()
    {
        StdDraw.clear(Color.black);
        StdDraw.setPenColor(Color.lightGray);
        StdDraw.setScale(findMin(), findMax());
        for (Point p : this.clusters)
        {
            StdDraw.setPenRadius(Math.random() * 0.004);
            p.draw();
        }
        for (Point p : this.centers)
        {
            StdDraw.setPenColor(Color.red);
            StdDraw.setPenRadius(0.01);
            p.draw();
        }
    }

    private double findMin()
    {
        double md = Double.MAX_VALUE;
        for (Point p : this.centers)
        {
            if (p.x < md)
                md = p.x;
            if (p.y < md)
                md = p.y;
        }
        for (Point p : this.clusters)
        {
            if (p.x < md)
                md = p.x;
            if (p.y < md)
                md = p.y;
        }
        return md;
    }

    private double findMax()
    {
        double md = 0;
        for (Point p : this.centers)
        {
            if (p.x > md)
                md = p.x;
            if (p.y > md)
                md = p.y;
        }
        for (Point p : this.clusters)
        {
            if (p.x > md)
                md = p.x;
            if (p.y > md)
                md = p.y;
        }
        return md;
    }


}

class Point
{
    int x, y;
    Point (int xx, int yy)
    {
        this.x = xx;
        this.y = yy;
    }
    Point (double xx, double yy)
    {
        this.x = (int) xx;
        this.y = (int) yy;
    }
    public void draw()
    {
        StdDraw.point(this.x, this.y);
    }

}
