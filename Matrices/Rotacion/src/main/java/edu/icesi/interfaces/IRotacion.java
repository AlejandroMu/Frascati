package edu.icesi.interfaces;

import java.rmi.*;

public interface IRotacion extends Remote {
    public double[][] rotar(double[][] img,double gr)throws RemoteException;
}