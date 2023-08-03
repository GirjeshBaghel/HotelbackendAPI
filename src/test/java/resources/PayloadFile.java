package resources;

import org.json.simple.JSONObject;

@SuppressWarnings("unchecked")
public class PayloadFile {

	public String jsonObject() {
		JSONObject js = new JSONObject();
		
		js.put("adName", "Girjesh Baghel9");
		js.put("adEmail", "girjeshbaghel9@gmail.com");
		js.put("adPassword", "girjesh@123");
		js.put("cusAddress", "New Delhi");
		js.put("phoneno", "74859632145");
		js.put("role", 2);
		
		return js.toJSONString();
	}
}
