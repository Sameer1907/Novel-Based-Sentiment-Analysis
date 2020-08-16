package com;
public class Sentiment{
	String sentiment;
	Aspects aspect;
	int rate;
public void setRate(int rate){
	this.rate = rate;
}
public int getRate(){
	return rate;
}
public void setSentiment(String sentiment){
	this.sentiment = sentiment;
}
public String getSentiment(){
	return sentiment;
}
public void setAspect(Aspects aspect){
	this.aspect = aspect;
}
public Aspects getAspect(){
	return aspect;
}
}