package homeWorkShortestPathProblem;

public class Test {
    public static void main(String[] args) throws Exception {
        char map[][] = PathUtils.makeMap(10, 23);
        Navigator navigator = new AStarSearch();
        long t0 = System.currentTimeMillis();
        map = navigator.searchRoute(map);
        long t1 = System.currentTimeMillis();
        System.out.println("mc for this search try: " + (t1 - t0));
        PathUtils.printMap(map);
        System.out.println("path has been built");
    }
}















