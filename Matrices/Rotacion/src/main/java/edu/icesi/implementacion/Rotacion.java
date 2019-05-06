package edu.icesi.implementacion;

import java.awt.Point;
import java.rmi.*;
import java.rmi.server.UnicastRemoteObject;
// import java.awt.image.BufferedImage;
// import java.io.*;
// import javax.imageio.ImageIO;
import java.util.*;
import org.osoa.sca.annotations.Reference;
import edu.icesi.interfaces.IMatrixOperations;
import edu.icesi.interfaces.IRotacion;

public class Rotacion extends UnicastRemoteObject implements IRotacion {

    private static final long serialVersionUID = 1L;
    @Reference(name = "mulMatrix")
    private IMatrixOperations mulMatrix;

    public Rotacion(IMatrixOperations m) throws RemoteException {
        mulMatrix=m;
    }

    @Override
    public double[][] rotar(double[][] img, double gra) throws RemoteException {
        double gr=Math.toRadians(gra);
        double[][] matRotacion = new double[2][2];
        double grR = gr;
        matRotacion[0][0] = Math.cos(grR);
        matRotacion[0][1] = Math.sin(grR);
        matRotacion[1][0] = -1 * matRotacion[0][1];
        matRotacion[1][1] = matRotacion[0][0];
        HashMap<Point, Double> points = new HashMap<>();
        int m = img.length;
        int n = img[0].length;
        Point[] corners = new Point[] { new Point(0, 0), new Point(0, 0) };
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                double[][] vP = new double[][] { { i }, { j } };
                // double[][] rs = mulMatrix.matrixMultiplication(matRotacion, vP);
                double[][] rs=mul(matRotacion, vP);
                int iN= (int)Math.floor(rs[0][0]);
                int jn= (int)Math.floor(rs[1][0]);
                Point newPoint = new Point(iN, jn);
                points.put(newPoint, img[i][j]);
                claculateCorners(corners, newPoint);
            }
        }

        return transfomMatrix(points, corners);
    }

    private void claculateCorners(Point[] corners, Point newPoint) {
        if (newPoint.x < corners[0].x) {
            corners[0].setLocation(newPoint.x, corners[0].y);
        }
        if (newPoint.x > corners[1].x) {
            corners[1].setLocation(newPoint.x, corners[1].y);
        }
        if (newPoint.y < corners[0].y) {
            corners[0].setLocation(corners[0].x, newPoint.y);
        }
        if (newPoint.y > corners[1].y) {
            corners[1].setLocation(corners[1].x, newPoint.y);
        }
    }

    private double[][] transfomMatrix(HashMap<Point, Double> points, Point[] cornes) {
        int m = cornes[1].y - cornes[0].y;
        int n = cornes[1].x - cornes[0].x;
        int dx = -1 * cornes[0].x;
        int dy = -1 * cornes[0].y;
        double[][] res = new double[n+1][m+1];
        Iterator<Point> keys = points.keySet().iterator();
        while (keys.hasNext()) {
            Point tmp = keys.next();
            res[tmp.x + dx][tmp.y + dy] = points.get(tmp);
        }
        return res;
    }

    // public BufferedImage image() throws IOException {
    //     BufferedImage image=ImageIO.read(new File("pathname"));
    // File outputfile = new File("../desktop/nuevoNombre.png");
    // ImageIO.write(imagen, "png", outputfile);
    //     return null;
    // }

    public double[][] mul(double[][] m,double[][] n){
        double[][] t=new double[m.length][n[0].length];
        for (int i = 0; i < t.length; i++) {
            for (int j = 0; j < t[0].length; j++) {
                for (int k = 0; k < n.length; k++) {
                    t[i][j]+=m[i][k]*n[k][j];
                }
            }
        }
        return t;
    }
    public static void main(String[] args) throws RemoteException {
        double[][] p={{1,2,3},{4,5,6},{7,8,9}};
        Rotacion r=new Rotacion(null);
        double[][] res=r.rotar(p, 90);
        for (double[] var : res) {
            for (double v : var) {
                System.out.print((int)v+" ");
                
            }
            System.out.println();
        }
        res=r.rotar(res, 90);
        System.out.println();
        for (double[] var : res) {
            for (double v : var) {
                System.out.print((int)v+" ");
                
            }
            System.out.println();
        }
        System.exit(0);
    }
}