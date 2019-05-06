package edu.icesi.interfaces;


import org.osoa.sca.annotations.Service;


@Service
public interface IMatrixOperations {
	public double[][] matrixMultiplication(double[][] m1,double[][] m2);//throws RemoteException;
	public double[][] matrixMultiplication(String m1,String m2);
}
