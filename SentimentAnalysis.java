package com;
import edu.stanford.nlp.ling.CoreAnnotations;
import edu.stanford.nlp.neural.rnn.RNNCoreAnnotations;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import edu.stanford.nlp.sentiment.SentimentCoreAnnotations;
import edu.stanford.nlp.trees.Tree;
import edu.stanford.nlp.util.CoreMap;
import edu.stanford.nlp.trees.TreeCoreAnnotations;
import java.util.Properties;
import java.util.ArrayList;
public class SentimentAnalysis {
	static StanfordCoreNLP pipeline;
	static int rate = 5; 
public static void init(){
	if(pipeline == null){
		Properties props = new Properties();
		props.setProperty("annotators", "tokenize, ssplit, pos, lemma, ner, parse, dcoref, sentiment");
		pipeline = new StanfordCoreNLP(props);
	}
}
public static String findSentiment(String grievance) {
	int mainSentiment = 0;
	String result = "none";
	rate = 5;
	if(grievance != null && grievance.length() > 0){
		int longest = 0;
		Annotation annotation = pipeline.process(grievance);
		for(CoreMap sentence : annotation.get(CoreAnnotations.SentencesAnnotation.class)){
			Tree tree = sentence.get(SentimentCoreAnnotations.SentimentAnnotatedTree.class);
			int sentiment = RNNCoreAnnotations.getPredictedClass(tree);
            String partText = sentence.toString();
			if(partText.length() > longest){
				mainSentiment = sentiment;
                longest = partText.length();
				if(mainSentiment == 0 || mainSentiment == 1)
					rate = rate - 1;
			}
		}
	}
	if(mainSentiment == 3 || mainSentiment == 4){
		result = "Positive. User is happy";
	}
	if(mainSentiment == 0 || mainSentiment == 1){
		result = "Negative. User is unhappy";
	}
	return result;
}
}