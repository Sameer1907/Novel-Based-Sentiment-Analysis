package com;
import java.io.BufferedReader;
import java.io.FileReader;
import opennlp.tools.postag.POSModel;
import opennlp.tools.postag.POSTaggerME;
import opennlp.tools.util.ObjectStream;
import opennlp.tools.util.PlainTextByLineStream;
import java.io.StringReader;
import java.util.ArrayList; 
import opennlp.tools.cmdline.postag.POSModelLoader;
import java.io.File;
import opennlp.tools.tokenize.WhitespaceTokenizer;
public class test{
	static POSModel model;
public static void loadDicitionary(){
	try{
		model = new POSModelLoader().load(new File("en-pos-maxent.bin"));
		SentimentAnalysis.init();
	}catch(Exception e){
		e.printStackTrace();
	}
}
public static void main(String args[]){
	/*try{
		StringBuilder sb = new StringBuilder();
		BufferedReader br = new BufferedReader(new FileReader("amazon_reviews_dataset.txt"));
		String line = null;
		while((line = br.readLine())!=null){
			line = line.trim();
			if(line.length() > 0){
				line = line.substring(10,line.length());
				line = line.trim();
				sb.append(line+System.getProperty("line.separator"));
			}
		}
		br.close();
		java.io.FileWriter fw = new java.io.FileWriter("test.txt");
		fw.write(sb.toString().trim());
		fw.close();
	}catch(Exception e){
		e.printStackTrace();
	}*/

	try{
		loadDicitionary();
		String input = "Stuning even for the non-gamer: This sound track was beautiful! It paints the senery in your mind so well I would recomend it even to people who hate vid. game music! I have played the game Chrono Cross but out of all of the games I have ever played it has the best music! It backs away from crude keyboarding and takes a fresher step with grate guitars and soulful orchestras. It would impress anyone who cares to listen! ^_^";
		ArrayList<String> list = getPos(input,model);
		System.out.println(list);
		System.out.println(SentimentAnalysis.findSentiment(input));
	}catch(Exception e){
		e.printStackTrace();
	}
}
public static ArrayList<String> getPos(String input,POSModel model) throws Exception {
    POSTaggerME tagger = new POSTaggerME(model);
    ObjectStream<String> lineStream = new PlainTextByLineStream(new StringReader(input));
    String line;
	ArrayList<String> list = new ArrayList<String>();
    while((line = lineStream.read()) != null) {
		String whitespaceTokenizerLine[] = WhitespaceTokenizer.INSTANCE.tokenize(line);
        String[] tags = tagger.tag(whitespaceTokenizerLine);
		for(int i=0;i<tags.length;i++){
			list.add(whitespaceTokenizerLine[i]+"#"+tags[i]);
		}
	}
	return list;
}
}