package edu.icesi.interfaces;
import org.osoa.sca.annotations.Service;

@Service
public interface IMultiplicationVectors {//extends Remote{
	
	public double multipication(double[] v1,double[] v2);//throws RemoteException;

}
