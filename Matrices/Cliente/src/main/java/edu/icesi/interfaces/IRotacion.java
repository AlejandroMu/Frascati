package edu.icesi.interfaces;

import java.rmi.Remote;

public interface IRotacion extends Remote {
    public double[][] rotar(double[][] img,double gr);
}