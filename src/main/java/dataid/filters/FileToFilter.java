package dataid.filters;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import dataid.DataID;
import dataid.DataIDGeneralProperties;
import dataid.ontology.Linkset;

public class FileToFilter {

	// list of linksets
	List<String> links = new ArrayList<String>();
	public List<String> linksUniq = new ArrayList<String>();

	public void loadFileToFilter(GoogleBloomFilter filter, String fileName) {

		BufferedReader br = null;
		
		DataID.bean.addDisplayMessage(DataIDGeneralProperties.MESSAGE_LOG,"Loading file to bloom filter: "+ 
						DataIDGeneralProperties.SUBJECT_FILE_DISTRIBUTION_PATH+fileName);
		
		try {
			String sCurrentLine;
			br = new BufferedReader(new FileReader(DataIDGeneralProperties.SUBJECT_FILE_DISTRIBUTION_PATH+
							fileName));
			int count = 0;
			while ((sCurrentLine = br.readLine()) != null) {
				filter.add(sCurrentLine);
				count++;
			}
			DataID.bean.addDisplayMessage(DataIDGeneralProperties.MESSAGE_LOG,"Bloom filter loaded "+count + " lines.");
			
		} catch (Exception e) {
			DataID.bean.addDisplayMessage(DataIDGeneralProperties.MESSAGE_ERROR,e.getMessage());
			e.printStackTrace();
		} finally {
			try {
				if (br != null)
					br.close();
			} catch (IOException ex) {
				DataID.bean.addDisplayMessage(DataIDGeneralProperties.MESSAGE_ERROR,ex.getMessage());
			}
		}

	}

	public int searchFileOnFilter(GoogleBloomFilter filter, String path) throws Exception {
		BufferedReader br = null;
		try {

			String sCurrentLine;
			
			br = new BufferedReader(new FileReader(path));
			DataID.bean.addDisplayMessage(DataIDGeneralProperties.MESSAGE_LOG,"Searching "+path+" on filter.");
			
			while ((sCurrentLine = br.readLine()) != null) {
				if (filter.compare(sCurrentLine)) { 
					links.add(sCurrentLine);
				}
			}
			
			// remove duplicates from the linkset list
			Iterator<String> i = links.iterator();
			
			while(i.hasNext()){
				String link = i.next();
				if(!linksUniq.contains(link)){
					linksUniq.add(link);
				}
			}
			
			
		} catch (Exception e) {
			DataID.bean.addDisplayMessage(DataIDGeneralProperties.MESSAGE_ERROR,e.getMessage());
			e.printStackTrace();
		} finally {
			try {
				if (br != null)
					br.close();
			} catch (IOException ex) {
				DataID.bean.addDisplayMessage(DataIDGeneralProperties.MESSAGE_ERROR,ex.getMessage());
			}
		}

		return linksUniq.size();
	}

}
