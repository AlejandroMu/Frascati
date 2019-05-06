package edu.icesi.MultiplicacionMatrices;

import java.rmi.Naming;
import java.rmi.registry.LocateRegistry;

// import org.osoa.sca.annotations.Property;
// import org.osoa.sca.annotations.Reference;
import org.osoa.sca.annotations.Service;

import edu.icesi.MultiplicacionVectores.*;
import edu.icesi.interfaces.*;


/**
 * Main
 * 
 */
@Service(Runnable.class)
public class Main implements Runnable {
    private String hostS="localhost";
    // @Property(name = "hostR")
    private int portS=1234;
   // @Reference(required = true)
	private IMultiplicationVectors mVectors;
    
    @Override
    public void run() {
        try {
            LocateRegistry.createRegistry(portS);
            IMatrixOperations imp = new MultiplicateMatrix(mVectors);
            String rout="rmi://"+hostS+":"+portS;
            Naming.rebind(rout, imp);
            System.out.println("server");
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            e.printStackTrace();
        }
    }
    public static void main(String[] args) {
        Main c=new Main();
        c.mVectors=new MultiplicationVectors();
        c.run();
        
    }
}