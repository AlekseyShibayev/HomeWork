package homeWorkShortestPathProblem;

public class Test {

    public static void main(String[] args) {
        char map[][] = PathUtils.makeMap(10000, 0);
        Navigator navi = new BFS();
//        Navigator navi = new AStarSearch();
        long t0 = System.currentTimeMillis();
        map = navi.searchRoute(map);
        long t1 = System.currentTimeMillis();
        if (map == null) {
            System.out.println(navi.getClass() + " : " + (t1 - t0) + " mc. You shall not path.");
        }
        else {
            System.out.println(navi.getClass() + " : " + (t1 - t0) + " mc. The path has been created.");
        }
//        PathUtils.printMap(map);
    }
}













