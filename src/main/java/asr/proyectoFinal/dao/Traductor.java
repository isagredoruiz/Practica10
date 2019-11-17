package asr.proyectoFinal.dao;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.ibm.cloud.sdk.core.security.Authenticator;
import com.ibm.cloud.sdk.core.security.IamAuthenticator;
import com.ibm.watson.language_translator.v3.LanguageTranslator;
import com.ibm.watson.language_translator.v3.model.TranslateOptions;
import com.ibm.watson.language_translator.v3.model.TranslationResult;
public class Traductor {
	public static String translate(String palabra, String sourceModel, String destModel,
	boolean conversational){
	String model;
	if(sourceModel.equals("en") || sourceModel.equals("es") ||
	destModel.equals("en") || destModel.equals("es")){
		model=sourceModel+"-"+destModel;
		if(conversational)
		model+="-conversational";
		}
	else
		model="en-es";
	Authenticator authenticator = new IamAuthenticator("Au2ojES2tEiSjRe-tW3jn23C94d13df2SGai3y8DytGE");
	LanguageTranslator languageTranslator = new LanguageTranslator("2018-05-01",
	authenticator);
	languageTranslator.setServiceUrl("https://gateway-lon.watsonplatform.net/language-translator/api");
	TranslateOptions translateOptions = new TranslateOptions.Builder()
	 .addText(palabra)
	 .modelId(model)
	 .build();
	TranslationResult translationResult =
	languageTranslator.translate(translateOptions).execute().getResult();
	System.out.println(translationResult);
	String traduccionJSON = translationResult.toString();
	JsonParser aux = new JsonParser();
	JsonObject rootObj = aux.parse(traduccionJSON).getAsJsonObject();
	JsonArray traducciones = rootObj.getAsJsonArray("translations");
	String traduccionPrimera = palabra;
	if(traducciones.size()>0)
	traduccionPrimera =
	traducciones.get(0).getAsJsonObject().get("translation").getAsString();
	return traduccionPrimera;
	}
}
