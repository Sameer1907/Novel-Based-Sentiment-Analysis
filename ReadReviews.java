package com;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
public class ReadReviews{

	static ArrayList<String> reviews = new ArrayList<String>();

public static void readReviews(String path){
	try{
		reviews.clear();
		//reader = reader.toLowerCase().replaceAll("[^a-zA-Z\\s+]", " ");
		BufferedReader br = new BufferedReader(new FileReader(path));
		String line = null;
		while((line = br.readLine())!=null){
			line = line.trim();
			if(line.length() > 0){
				reviews.add(line);
			}
		}
		br.close();
	}catch(Exception e){
		e.printStackTrace();
	}
}
}