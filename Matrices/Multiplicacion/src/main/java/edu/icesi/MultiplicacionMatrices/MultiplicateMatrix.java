package edu.icesi.MultiplicacionMatrices;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

// import org.osoa.sca.annotations.Reference;

import edu.icesi.interfaces.*;

public class MultiplicateMatrix extends UnicastRemoteObject implements IMatrixOperations {

	private static final long serialVersionUID = 1L;
	// @Reference(required = true)
	private IMultiplicationVectors mVectors;
	
	public MultiplicateMatrix(IMultiplicationVectors m)throws Exception {
		super();
		mVectors=m;
		System.out.println("empezó server");
	}

	public MultiplicateMatrix()throws Exception {
		System.out.println("empezó server");
	}

	public double[][] matrixMultiplication(double[][] m1, double[][] m2)throws RemoteException {// throws RemoteException {
		double[][] m2T = trasnponse(m2);
		double[][] ret = new double[m1.length][m2[0].length];
		for (int i = 0; i < ret.length; i++) {
			for (int j = 0; j < ret[0].length; j++) {
				ret[i][j] = mVectors.multipication(m1[i], m2T[j]);
			}
		}
		return ret;
	}

	private double[][] trasnponse(double[][] m2) {
		double[][] ret = new double[m2[0].length][m2.length];
		for (int i = 0; i < ret.length; i++) {
			for (int j = 0; j < ret[0].length; j++) {
				ret[i][j] = m2[j][i];

			}
		}
		return ret;
	}

	public double[][] matrixMultiplication(String m1, String m2)throws RemoteException{
		try {
			double[][] matix1 = parseMatr(m1);
			double[][] matix2 = parseMatr(m2);
			if (matix1[0].length == matix2.length) {
				double[][] result = matrixMultiplication(matix1, matix2);
//				System.out.println("hola");
				return result;
			} else {
				
//				System.out.println("Las columnas de A deben coincidir con las filas de B");
				return null;
			}
		} catch (IllegalArgumentException s) {
//			System.out.println(s.getMessage());
			return null;
		}

	}
		
	public String paint(double[][] result) {
		String r = "{\n";
		int m = result.length;
		int n = result[0].length;
		for (int i = 0; i < m; i++) {
			r += "[";
			for (int j = 0; j < n; j++) {
				r += result[i][j];
				r += j < n - 1 ? "  " : "";
			}
			r += "]\n";
		}
		r += "}";
//		System.out.println(r);
		return r;
	}

	private double[][] parseMatr(String m1) throws IllegalArgumentException {
		try {
			String[] rows = m1.split("-");
			int f = rows.length;
			int c = rows[0].substring(1, rows[0].length() - 1).split(",").length;
			double[][] mat = new double[f][c];

			for (int i = 0; i < f; i++) {
				String row[] = rows[i].substring(1, rows[i].length() - 1).split(",");
				for (int j = 0; j < c; j++) {
					String string = row[j];
					mat[i][j] = Double.parseDouble(string);

				}
			}
			return mat;
		} catch (Exception e) {
			throw new IllegalArgumentException(
					"El formato es erroneo,\n deberia ser de la forma [v1,v2,--Vn] para cada fila,\n y se separan por - \n todas las filas deben tener la misma cantidad de elementos");
		}

	}

}
