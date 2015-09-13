package CONTROL;

import kr.ac.kaist.swrc.jhannanum.hannanum.Workflow;
import kr.ac.kaist.swrc.jhannanum.hannanum.WorkflowFactory;


import java.io.FileNotFoundException;
import java.io.IOException;

import kr.ac.kaist.swrc.jhannanum.hannanum.Workflow;
import kr.ac.kaist.swrc.jhannanum.plugin.MajorPlugin.MorphAnalyzer.ChartMorphAnalyzer.ChartMorphAnalyzer;
import kr.ac.kaist.swrc.jhannanum.plugin.MajorPlugin.PosTagger.HmmPosTagger.HMMTagger;
import kr.ac.kaist.swrc.jhannanum.plugin.SupplementPlugin.MorphemeProcessor.SimpleMAResult09.SimpleMAResult09;
import kr.ac.kaist.swrc.jhannanum.plugin.SupplementPlugin.MorphemeProcessor.SimpleMAResult22.SimpleMAResult22;
import kr.ac.kaist.swrc.jhannanum.plugin.SupplementPlugin.MorphemeProcessor.UnknownMorphProcessor.UnknownProcessor;
import kr.ac.kaist.swrc.jhannanum.plugin.SupplementPlugin.PlainTextProcessor.InformalSentenceFilter.InformalSentenceFilter;
import kr.ac.kaist.swrc.jhannanum.plugin.SupplementPlugin.PlainTextProcessor.SentenceSegmentor.SentenceSegmentor;
import kr.ac.kaist.swrc.jhannanum.plugin.SupplementPlugin.PosProcessor.SimplePOSResult09.SimplePOSResult09;
import kr.ac.kaist.swrc.jhannanum.plugin.SupplementPlugin.PosProcessor.SimplePOSResult22.SimplePOSResult22;


public class Hannanum {

	Workflow hannanumWordflow;
	
	public Hannanum()
	{
		hannanumWordflow = new Workflow();
		
		try {

			hannanumWordflow.appendPlainTextProcessor(new SentenceSegmentor(), null);
			hannanumWordflow.appendPlainTextProcessor(new InformalSentenceFilter(), null);
			hannanumWordflow.setMorphAnalyzer(new ChartMorphAnalyzer(), "conf/plugin/MajorPlugin/MorphAnalyzer/ChartMorphAnalyzer.json");
			hannanumWordflow.appendMorphemeProcessor(new UnknownProcessor(), null);
			hannanumWordflow.setPosTagger(new HMMTagger(), "conf/plugin/MajorPlugin/PosTagger/HmmPosTagger.json");
			hannanumWordflow.activateWorkflow(true);
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			System.exit(0);
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(0);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	public void closeWordFlow()
	{
		hannanumWordflow.close();
	}
	
	public String getResult(String sentence)
	{
		String[] array = sentence.split("\n");
		String result = "";
		int length = array.length;
		
		for(int i=0; i<length; i++)
		{
			String tmp = array[i];
			if(tmp.contains("\t"))
			{
				String[] list = tmp.split("\t");
				tmp = list[1];
			}
			if(tmp.contains("/"))
			{
				result = result + tmp + "\n";
			}
		}
		
		return result;
	}
	
	public String extractVerb(String sentence)
	{
		
		if(sentence.contains("/P"))
		{
			String[] temp = sentence.split("/P");
			return 	temp[0];
		}
		return "";
	}
	
	public String extractNoun(String sentence)
	{
		
		if(sentence.contains("/N"))
		{
			String[] temp = sentence.split("/P");
			return 	temp[0];
		}
		return "";
	}

	
	public void setSetting()
	{
		
	}
	
	public void ManualWorkflowSetUp (String sentence) {
	
		hannanumWordflow.analyze(sentence);
		System.out.println(hannanumWordflow.getResultOfDocument());
			
	}
	

}
