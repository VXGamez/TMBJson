import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import java.io.IOException;
import java.util.List;
import java.util.Objects;

public class API {

	public static Parades getParades(String tipus) throws IOException {
		Parades stops = null;
		OkHttpClient client = new OkHttpClient();
		Request request = new Request.Builder().url("https://api.tmb.cat/v1/transit/linies/" + tipus +"?app_id=dde70f9a&app_key=5e1254a79b83ad188c034f46ef273566").build();
		Call call = client.newCall(request);
		Response response  = (call).execute();

		if (response.isSuccessful()){
			Gson stop = new GsonBuilder().setPrettyPrinting().create();
			stops = stop.fromJson(Objects.requireNonNull(response.body()).string(), Parades.class);
		}
		return stops;
	}


	public static DataBus getInfoParada(String codi) throws IOException {
		DataBus parada = null;
		OkHttpClient client = new OkHttpClient();
		Request request = new Request.Builder().url("https://api.tmb.cat/v1/ibus/stops/" + codi + "?app_id=dde70f9a&app_key=5e1254a79b83ad188c034f46ef273566").build();
		Call call = client.newCall(request);
		Response response  = (call).execute();

		Gson stop = new GsonBuilder().setPrettyPrinting().create();
		parada = stop.fromJson(Objects.requireNonNull(response.body()).string(), DataBus.class);

		return parada;
	}

	public static Ruta getRuta(List<Double> from, List<Double> to, String date, String time, String arriveBy, int maxWalkDistance, Boolean showIntermediateStops) throws IOException {
		Ruta route = null;
		OkHttpClient client = new OkHttpClient();
		String f = "";
		if(arriveBy.equalsIgnoreCase("s")){
			f = "false";
		}else if(arriveBy.equalsIgnoreCase("a")){
			f = "true";
		}
		Request request = new Request.Builder().url("https://api.tmb.cat/v1/planner/plan?app_id=dde70f9a&app_key=5e1254a79b83ad188c034f46ef273566&fromPlace="+ from.get(0)+","+from.get(1) +"&toPlace="+ to.get(0)+","+to.get(1) +"&date=" + date +"&time="+ time +"&arriveBy="+ f +"&mode=TRANSIT,WALK&maxWalkDistance="+ maxWalkDistance +"&showIntermediateStops="+showIntermediateStops).build();

		Call call = client.newCall(request);
		Response response  = (call).execute();

		Gson rutes = new GsonBuilder().setPrettyPrinting().create();
		route = rutes.fromJson(Objects.requireNonNull(response.body()).string(), Ruta.class);

		return route;
	}

}
