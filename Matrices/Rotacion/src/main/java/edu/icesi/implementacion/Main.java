package edu.icesi.implementacion;

import java.rmi.*;
import java.rmi.registry.LocateRegistry;

import org.osoa.sca.annotations.Property;
import org.osoa.sca.annotations.Service;

import edu.icesi.interfaces.IMatrixOperations;

/**
 * Main
 */
@Service(Runnable.class)
public class Main implements Runnable{
    private String hostS="localhost";
    @Property(name = "hostR")
    private String hostR="localhost";
    @Property(name = "portS")
    private int portS=1235;
    @Property(name = "portR")
    private int portR=1234;
    
    public void run() {
        try {
            LocateRegistry.createRegistry(portS);
            IMatrixOperations mulM = (IMatrixOperations) Naming.lookup("rmi://"+hostR+":"+portR);
            Rotacion imp = new Rotacion(mulM);
            Naming.rebind("rmi://"+hostS+":"+portS, imp);
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            e.printStackTrace();
        }
    }
    public static void main(String[] args) {
        Main m=new Main();
        m.run();
    }

}