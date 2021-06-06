import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;

/**
 * This class provides all code necessary to take a query box and produce
 * a query result. The getMapRaster method must return a Map containing all
 * seven of the required fields, otherwise the front end code will probably
 * not draw the output correctly.
 */
public class Rasterer {
    private final Map<Integer, Double> DEPTH_LON_DPP_MAP = new HashMap<Integer, Double>();
    private final int MAX_DEPTH = 7;

    private int depth;
    private int minXIndex;
    private int maxXIndex;
    private int minYIndex;
    private int maxYIndex;

    private String[][] render_grid;
    private double raster_ul_lon;
    private double raster_ul_lat;
    private double raster_lr_lon;
    private double raster_lr_lat;
    private boolean query_success;

    public Rasterer() {
        // YOUR CODE HERE
        for (int i = 0; i < MAX_DEPTH + 1; i += 1) {
            DEPTH_LON_DPP_MAP.put(i, depthLonDPP(i));
        }
        depth = 0;
    }

    /**
     * Takes a user query and finds the grid of images that best matches the query. These
     * images will be combined into one big image (rastered) by the front end. <br>
     *
     *     The grid of images must obey the following properties, where image in the
     *     grid is referred to as a "tile".
     *     <ul>
     *         <li>The tiles collected must cover the most longitudinal distance per pixel
     *         (LonDPP) possible, while still covering less than or equal to the amount of
     *         longitudinal distance per pixel in the query box for the user viewport size. </li>
     *         <li>Contains all tiles that intersect the query bounding box that fulfill the
     *         above condition.</li>
     *         <li>The tiles must be arranged in-order to reconstruct the full image.</li>
     *     </ul>
     *
     * @param params Map of the HTTP GET request's query parameters - the query box and
     *               the user viewport width and height.
     *
     * @return A map of results for the front end as specified: <br>
     * "render_grid"   : String[][], the files to display. <br>
     * "raster_ul_lon" : Number, the bounding upper left longitude of the rastered image. <br>
     * "raster_ul_lat" : Number, the bounding upper left latitude of the rastered image. <br>
     * "raster_lr_lon" : Number, the bounding lower right longitude of the rastered image. <br>
     * "raster_lr_lat" : Number, the bounding lower right latitude of the rastered image. <br>
     * "depth"         : Number, the depth of the nodes of the rastered image <br>
     * "query_success" : Boolean, whether the query was able to successfully complete; don't
     *                    forget to set this to true on success! <br>
     */
    public Map<String, Object> getMapRaster(Map<String, Double> params) {
        System.out.println(params);
        System.out.println(optimalDepth(params));
        Map<String, Object> results = new HashMap<>();
        manipulate(results, params);
        System.out.println("Since you haven't implemented getMapRaster, nothing is displayed in "
                           + "your browser.");
        return results;
    }

    /**
     * Manipulate the images output.
     * @param results
     */
    public void manipulate(Map<String, Object> results, Map<String, Double> params) {
        setUpDepthAndIndex(params);
        query_success = true;
        render_grid = renderGrid();
        raster_ul_lon = rasterUlLon();
        raster_ul_lat = rasterUlLat();
        raster_lr_lon = rasterLrLon();
        raster_lr_lat = rasterLrLat();
        results.put("render_grid", render_grid);
        results.put("raster_ul_lon", raster_ul_lon);
        results.put("raster_ul_lat", raster_ul_lat);
        results.put("raster_lr_lon", raster_lr_lon);
        results.put("raster_lr_lat", raster_lr_lat);
        results.put("depth", depth);
        results.put("query_success", query_success);
        System.out.println("Hold on!");
    }

    /**
     * Return the render_grid for the output.
     */
    private String[][] renderGrid() {
        int nX = maxXIndex - minXIndex + 1;
        int nY = maxYIndex - minYIndex + 1;
        String[][] render_grid = new String[nY][nX];
        for (int i = minXIndex; i <= maxXIndex; i += 1) {
            for (int j = minYIndex; j <= maxYIndex; j += 1) {
                render_grid[j - minYIndex][i - minXIndex] = toImageFile(depth, i, j);
            }
        }
        return render_grid;
    }

    /**
     * Return the raster_ul_lon for the output.
     */
    private double rasterUlLon() {
        return MapServer.ROOT_ULLON + minXIndex * lonDistancePerImage(depth);
    }

    /**
     * Return the raster_ul_lat for the output.
     */
    private double rasterUlLat() {
        return MapServer.ROOT_ULLAT - minYIndex * latDistancePerImage(depth);
    }

    /**
     * Return the raster_lr_lon for the output.
     */
    private double rasterLrLon() {
        return MapServer.ROOT_ULLON + (maxXIndex + 1) * lonDistancePerImage(depth);
    }

    /**
     * Return the raster_lr_lat for the output.
     */
    private double rasterLrLat() {
        return MapServer.ROOT_ULLAT - (maxYIndex + 1) * latDistancePerImage(depth);
    }

    /**
     * Compute the LonDPP provided by the client.
     */
    private double givenLonDPP(Map<String, Double> params) {
        double lrlon = params.get("lrlon");
        double ullon = params.get("ullon");
        double width = params.get("w");
        double lonDPP = (lrlon - ullon) / width;
        return lonDPP;
    }

    /**
     * Compute the LonDPP with width depth d.
     */
    private double depthLonDPP(int d) {
        checkDepthInBound(d);
        double initialLonDPP = (MapServer.ROOT_LRLON - MapServer.ROOT_ULLON)
                / MapServer.TILE_SIZE;
        return initialLonDPP / Math.pow(2, d);
    }
    /**
     * Compute the optimal depth for the client.
     */
    private int optimalDepth(Map<String, Double> params) {
        double givenLonDPP = givenLonDPP(params);
        for (int d = 0; d < MAX_DEPTH; d += 1) {
            if (depthLonDPP(d) <= givenLonDPP) {
                return d;
            }
        }
        return MAX_DEPTH;
    }

    /**
     * Set up the depth given the parameters.
     */
    private void setUpDepthAndIndex(Map<String, Double> params) {
        depth = optimalDepth(params);
        minXIndex = minXIndex(params);
        maxXIndex = maxXIndex(params);
        minYIndex = minYIndex(params);
        maxYIndex = maxYIndex(params);
    }

    /**
     * Get the file name of the image with the correspondent depth and xi, and yi.
     */
    private String toImageFile(int depth, int xIndex, int yIndex) {
        checkDepthInBound(depth);
        if (xIndex >= Math.pow(2, depth) || yIndex > Math.pow(2, depth)
                || xIndex < 0 || yIndex < 0) {
            throw new IndexOutOfBoundsException("The indexes of images are out of bound.");
        }
        String fileName = "";
        fileName = fileName + "d" + depth + "_x" + xIndex + "_y" + yIndex + ".png";
        return fileName;
    }

    /**
     * Check if depth is out of bound.
     */
    private void checkDepthInBound(int d) {
        if (d < 0 || d > MAX_DEPTH) {
            throw new IndexOutOfBoundsException("The depth is out of bound.");
        }
    }
    /**
     * Compute the per width in longitudinal of a image given the depth.
     */
    public double lonDistancePerImage(int depth) {
        checkDepthInBound(depth);
        double totalDistance = MapServer.ROOT_LRLON - MapServer.ROOT_ULLON;
        return totalDistance / Math.pow(2, depth);
    }

    /**
     * Compute the per width in latitude of a image given the depth.
     */
    public double latDistancePerImage(int depth) {
        checkDepthInBound(depth);
        double totalDistance = MapServer.ROOT_ULLAT - MapServer.ROOT_LRLAT;
        return totalDistance / Math.pow(2, depth);
    }

    /**
     * The minimum xIndex of needed images.
     * @param params
     * @return
     */
    public int minXIndex(Map<String, Double> params) {
        double ullon = params.get("ullon");
        if (ullon <= MapServer.ROOT_ULLON) {
            return 0;
        }
        if (ullon > MapServer.ROOT_LRLON) {
            // throw new IndexOutOfBoundsException("The ullon is out of right.");
            query_success = false;
            return 0;
        }
        double distanceToLeft = ullon - MapServer.ROOT_ULLON;
        return (int) Math.floor(distanceToLeft / lonDistancePerImage(depth));
    }

    public int maxXIndex(Map<String, Double> params) {
        double lrlon = params.get("lrlon");
        if (lrlon >= MapServer.ROOT_LRLON) {
            return (int) Math.pow(2, depth) - 1;
        }
        if (lrlon < MapServer.ROOT_ULLON) {
            // throw new IndexOutOfBoundsException("The lrlon is out of left.");
            query_success = false;
            return (int) Math.pow(2, depth) - 1;
        }
        double distanceToRight = MapServer.ROOT_LRLON -lrlon;
        return (int) (Math.pow(2, depth) - 1
                - Math.floor(distanceToRight / lonDistancePerImage(depth)));
    }

    public int minYIndex(Map<String, Double> params) {
        double ullat = params.get("ullat");
        if (ullat >= MapServer.ROOT_ULLAT) {
            return 0;
        }
        if (ullat < MapServer.ROOT_LRLAT) {
            // throw new IndexOutOfBoundsException("The ullat is out of down.");
            query_success = false;
            return 0;
        }
        double distanceToUp = MapServer.ROOT_ULLAT - ullat;
        return (int) Math.floor(distanceToUp / latDistancePerImage(depth));
    }

    public int maxYIndex(Map<String, Double> params) {
        double lrlat = params.get("lrlat");
        if (lrlat <= MapServer.ROOT_LRLAT) {
            return (int) Math.pow(2, depth) - 1;
        }
        if (lrlat > MapServer.ROOT_ULLAT) {
            // throw new IndexOutOfBoundsException("The ullat is out of up.");
            query_success = false;
            return (int) Math.pow(2, depth) - 1;
        }
        double distanceToDown = lrlat - MapServer.ROOT_LRLAT;
        return (int) (Math.pow(2, depth) - 1
                - Math.floor(distanceToDown / latDistancePerImage(depth)));
    }
}
