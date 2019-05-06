package edu.icesi.implementacion;

import java.io.*;

import java.util.*;

import org.osoa.sca.annotations.Property;
import org.osoa.sca.annotations.Reference;
import org.osoa.sca.annotations.Service;

import edu.icesi.interfaces.*;

/**
 * Hello world!
 *
 */
@Service(Runnable.class)
public class ClientMultiplicar implements Runnable
{
	private IMatrixOperations servicio;
	private String pathOrig;
	private String keyM1;
	private String keyRes;
	private String keyM2;
	
	@Property(name="keyResult")
	public void setKeyRes(String keyRes) {
		this.keyRes = keyRes;
	}

	@Property(name="keyM1")
	public void setKeyM1(String keyM1) {
		this.keyM1 = keyM1;
	}

	@Property(name="keyM2")
	public void setKeyM2(String keyM2) {
		this.keyM2 = keyM2;
	}

	
	@Property(name="path")
	public void setPathOrig(String pathOrig) {
		this.pathOrig = pathOrig;
	}


	@Reference(required=true,name="multiplicar")
	public void setServicio(IMatrixOperations service){
		this.servicio=service;
	}

 	
	public void run() {
		Properties properties=new Properties();
		try {
			properties.load(new FileReader(pathOrig));
			String matrix1=properties.getProperty(keyM1);
			String matrix2=properties.getProperty(keyM2);
			String result=properties.getProperty(keyRes);
			double[][] mat1=readMatrix(matrix1);
			double[][] mat2=readMatrix(matrix2);
			double[][] resul=servicio.matrixMultiplication(mat1, mat2);
			writte(resul,result);
			System.out.println("Termino con éxito");
		} catch (Exception e) {
			e.printStackTrace();
		} 
		
		
	}


	private void writte(double[][] resul, String result) throws IOException {
		FileWriter a=new FileWriter(new File(result));
		BufferedWriter bw=new BufferedWriter(a);
		bw.write(resul.length+" "+resul[0].length+"\n");
		for (double[] ds : resul) {
			
			for (double d : ds) {
				bw.write(d+" ");
			}
			bw.newLine();
		}
		bw.close();
		
	}

	private double[][] readMatrix(String matrix1) throws IOException {
		FileReader a=new FileReader(new File(matrix1));
		BufferedReader br=new BufferedReader(a);
		String tam[]=br.readLine().split(" ");
		int r=Integer.parseInt(tam[0]);
		int c=Integer.parseInt(tam[1]);
		double[][] ret=new double[r][c];
		for (int i = 0; i < ret.length; i++) {
			String row[]=br.readLine().split(" ");
			for (int j = 0; j < ret[0].length; j++) {
				ret[i][j]=Double.parseDouble(row[j]);
			}
		}
		br.close();
		return ret;
	}

   
}
