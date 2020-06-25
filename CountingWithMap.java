// json 데이터 input 예 : 
// [{"seq":"2826146","issueDate":"2020-05-19 09:00:00", "searchType":"01","searchAmountR":50000,"plazaOptions":"101,201,301","cateNo":"1267","isEditTheDay":true},{"seq":"2826145","issueDate":"2020-05-18 09:00:00","searchType":"01","searchAmountR":50000,"plazaOptions":"101,201,301","cateNo":"1049","isEditTheDay":true},{"seq":"2826144","issueDate":"2020-05-20 09:00:00","searchType":"01","searchAmountR":50000,"plazaOptions":"101,201,301","cateNo":"1106","isEditTheDay":true},{"seq":"2826143","issueDate":"2020-05-20 09:00:00","searchType":"01","searchAmountR":50000,"plazaOptions":"101,201,301","joinNumber":"2","cateNo":"1106","isEditTheDay":true},{"seq":"2826142","issueDate":"2020-05-20 09:00:00","searchType":"01","searchAmountR":50000,"plazaOptions":"101,201,301","cateNo":"1106","isEditTheDay":true}]


Gson gson = new Gson();
JsonParser jsonParser = new JsonParser();
JsonArray jsonArray = (JsonArray) jsonParser.parse(dataList);

Map<String, Integer> forEachMap = null;
Map<String, Map<String,Integer>> forDateMap = null;
Map<String, Map<String, Map<String,Integer>>> pastMap = new HashMap<String, Map<String, Map<String,Integer>>>();

for(int i = 0 ; i < jsonArray.size() ; i++){
	JsonElement str = jsonArray.get(i);
	JoinEasyEditModel model = gson.fromJson(str, JoinEasyEditModel.class);
	
	String _issueDate = model.getIssueDate().substring(0,10); // yyyy-MM-dd

	if(pastMap.containsKey(model.getCateNo())) {
		for( String key1 : pastMap.keySet() ){
			if(key1.equals(model.getCateNo())) {
				forDateMap = pastMap.get(key1);
				if(forDateMap.containsKey(_issueDate)) {
					for( String key2 : forDateMap.keySet() ){
						if(key2.equals(_issueDate)) {
							forEachMap = forDateMap.get(key2);
							
							if(forEachMap.containsKey(model.getSearchType())) {
								forEachMap.put(model.getSearchType(), forEachMap.get(model.getSearchType()) + 1);
							} else {
								forEachMap.put(model.getSearchType(), 1);
							}											
							forDateMap.put(_issueDate, forEachMap);
							pastMap.put(model.getCateNo(), forDateMap);
						}														
					}									
				} else {
					forEachMap = new HashMap<String, Integer>();
					forEachMap.put(model.getSearchType(), 1);
					forDateMap.put(_issueDate, forEachMap);
					pastMap.put(model.getCateNo(), forDateMap);
				}
			}
		}					
	} else {
		forEachMap = new HashMap<String, Integer>();
		forEachMap.put(model.getSearchType(), 1);
		forDateMap = new HashMap<String, Map<String, Integer>>();					
		forDateMap.put(_issueDate, forEachMap);					
		pastMap.put(model.getCateNo(), forDateMap);
	}
}
