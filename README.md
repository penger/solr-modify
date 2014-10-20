问题描述:<br/>
1.solrCloud在做增量导入的时候会出现错误详见<a href="https://issues.apache.org/jira/browse/SOLR-6165">issues 6165</a><br/>
	bugfix版本为4.10,jdk版本为1.7用到了map<br/>
	 private Map<String, Integer> fieldNameVsType = new HashMap<>();<br/>
	 
2.solr http://wiki.apache.org/solr/DataImportHandler#Scheduling<br/>
	定时增量导入的时候会出现任务挤压的问题<br/>
	对于定义的handler操作不灵活<br/>
	