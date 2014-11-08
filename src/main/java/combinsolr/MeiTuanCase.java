package combinsolr;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrQuery.ORDER;

public class MeiTuanCase {

	private SolrQuery getSuggestQuery(String prefix,Integer limit){
		SolrQuery solrQuery = new SolrQuery();
		StringBuilder sb = new StringBuilder();
		sb.append("suggest").append(prefix).append("*");
		solrQuery.setQuery(sb.toString());
		solrQuery.addField("kw");
		solrQuery.addField("kwfreq");
		solrQuery.addSort("kwfreq", ORDER.desc);
		solrQuery.setStart(0);
		solrQuery.setRows(limit);
		return solrQuery;
	}
}
