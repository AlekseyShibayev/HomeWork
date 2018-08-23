package homeWorkShortestPathProblem;

import java.util.Arrays;
import java.util.Random;

public class PathUtils {

    static Random RND = new Random();

    public static char[][] makeMap(int size, int wallCoefficient) {
        char[][] map = new char[size][size];
        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[i].length; j++) {
                map[i][j] = '.';
            }
        }
        map[0][0] = '@';
        map[size - 1][size - 1] = 'X';
        int wallFactor = wallCoefficient  * (size * size) / 100;
        long wallsCount = 0;
        if (wallFactor > 0) {
            for (int i = 0; i < wallFactor; i++) {
                int wallX = RND.nextInt(size);
                int wallY = RND.nextInt(size);
                if (map[wallX][wallY] == '.') {
                    map[wallX][wallY] = '#';
                    wallsCount++;
                }
            }
        }
        System.out.println("map rdy. " + wallsCount*100/(size*size) + " % of walls");
        return map;
    }

    public static void printMap(char[][] map) {
        if (map == null) {
            System.out.println("you shall not pass!");
            return;
        }
        for (char[] maps : map) {
            System.out.println(maps);
        }
        System.out.println(" ");
    }

    public static void printMap(int[][] map) {
        if(map == null){
            System.out.println("Map is null");
            return;
        }
        for (int[] maps : map) {
            System.out.println(Arrays.toString(maps));
        }
        System.out.println(" ");
    }
}

