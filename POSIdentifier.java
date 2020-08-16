package com;
import opennlp.tools.postag.POSModel;
import opennlp.tools.postag.POSTaggerME;
import opennlp.tools.util.ObjectStream;
import opennlp.tools.util.PlainTextByLineStream;
import java.io.StringReader;
import java.util.ArrayList; 
import opennlp.tools.cmdline.postag.POSModelLoader;
import java.io.File;
import opennlp.tools.tokenize.WhitespaceTokenizer;
public class POSIdentifier{

	static POSModel model;

public static void loadDicitionary(){
	try{
		if(model == null){
			model = new POSModelLoader().load(new File("en-pos-maxent.bin"));
			SentimentAnalysis.init();
		}
	}catch(Exception e){
		e.printStackTrace();
	}
}

public static ArrayList<String> getPos(String input) throws Exception {
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