package utils.files;

class Constants {
	 static final String NEW_LINE;
	 static final String QUOTE;
	 static final String DOUBLE_QUOTE;
	 
	 static final String CSV_SEPARATOR;
	 
	 static{
		 NEW_LINE = System.lineSeparator();
		 QUOTE = "\"";
		 DOUBLE_QUOTE = QUOTE + QUOTE;
		 
		 CSV_SEPARATOR = ",";
	 }
}
