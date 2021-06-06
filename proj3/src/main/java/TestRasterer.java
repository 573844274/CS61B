import java.util.HashMap;
import java.util.Map;

public class TestRasterer {


    public static void main(String[] args) {
        // double lonLDD = Rasterer.depthLonDPP(1);
        Rasterer rasterer = new Rasterer();
        //System.out.println(rasterer.toImageFile(7,5,7));
        System.out.println("Hold on!");
        Map<String, Object> results = new HashMap<>();
        Map<String, Double> params = new HashMap<String, Double>();
        params.put("lrlon", -122.2104604264636);
        params.put("ullon", -122.30410170759153);
        params.put("w", 1091.0);
        params.put("h", 566.0);
        params.put("ullat", 37.870213571328854);
        params.put("lrlat", 37.8318576119893);
        rasterer.manipulate(results, params);
    }

}
