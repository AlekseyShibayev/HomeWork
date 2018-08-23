package homeWorkShortestPathProblem;

import java.util.LinkedList;
import java.util.List;

public class BFS implements Navigator {

    @Override
    public char[][] searchRoute (char[][] map) {
        if (map == null) {return null;}
        int[] mapSize = getMapSize(map);
        int[] start = getStartCoordinates(map);
        int[] finish = getFinishCoordinates(map);
        int[][] ways = generateWaysArray(map, mapSize);
        ways = doBFS(ways, start, finish, mapSize);
        map = createShortestPath(map, ways, finish);
        return map;
    }

    private int[] getMapSize (char[][] map) {
        int[] mapSize = {map.length, map[0].length};
        return mapSize;
    }

    private int[] getStartCoordinates (char[][] map) {
        int[] startCoordinates = new int[2];
        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[i].length; j++) {
                if (map[i][j] == '@') {
                    startCoordinates[0] = i;
                    startCoordinates[1] = j;
                    return startCoordinates;
                }
            }
        }
        return startCoordinates;
    }

    private int[] getFinishCoordinates (char[][] map) {
        int[] finishCoordinates = new int[2];
        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[i].length; j++) {
                if (map[i][j] == 'X') {
                    finishCoordinates[0] = i;
                    finishCoordinates[1] = j;
                    return finishCoordinates;
                }
            }
        }
        return finishCoordinates;
    }

    private int[][] generateWaysArray (char[][] map, int[] mapSize) {
        int rows = mapSize[0];
        int columns = mapSize[1];
        int[][] ways = new int[rows][columns];
        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[i].length; j++) {
                if (map[i][j] == '#') {
                    ways[i][j] = 1;
                }
            }
        }
        return ways;
    }

    private int[][] doBFS (int[][] ways, int[] start, int[] finish, int[] mapSize) {
        if (ways == null || start == null || finish == null || mapSize == null) {return null;}
        int rows = mapSize[0];
        int columns = mapSize[1];
        ways[start[0]][start[1]] = 2;
        int i;
        int j;
        int[][] que = new int[2][rows*columns];
        que[0][0] = start[0];
        que[1][0] = start[1];
        int countSet = 1;
        int countGet = 0;
        int temp;

        while (true) {
            i = que[0][countGet];
            j = que[1][countGet];
            temp = ways[i][j]+1;
            if ((j < columns - 1) && (ways[i][j+1] == 0)) {
                ways[i][j+1] = temp;
                if ((i == finish[0]) && (j+1 == finish[1])) {
                    break;
                }
                que[0][countSet] = i;
                que[1][countSet] = j+1;
                countSet++;
            }
            if ((i < rows - 1) && (ways[i+1][j] == 0)) {
                ways[i+1][j] = temp;
                if ((i+1 == finish[0]) && (j == finish[1])) {
                    break;
                }
                que[0][countSet] = i+1;
                que[1][countSet] = j;
                countSet++;
            }
            if ((j > 0) && (ways[i][j-1] == 0)) {
                ways[i][j-1] = temp;
                if ((i == finish[0]) && (j-1 == finish[1])) {
                    break;
                }
                que[0][countSet] = i;
                que[1][countSet] = j-1;
                countSet++;
            }
            if ((i >0) && (ways[i-1][j] == 0)) {
                ways[i-1][j] = temp;
                if ((i-1 == finish[0]) && (j == finish[1])) {
                    break;
                }
                que[0][countSet] = i-1;
                que[1][countSet] = j;
                countSet++;
            }
            if (countGet == countSet) {
                return null;
            }
            countGet++;
        }
        return ways;
    }

    private char[][] createShortestPath(char[][] map, int[][] ways, int[] finish) {
        if (ways == null || map == null || finish == null) {
            return null;
        }
        int rows = map.length;
        int columns = map[0].length;
        List<int[]> listQueueForBFS = new LinkedList<>();
        listQueueForBFS.add(finish);
        int i;
        int j;
        int count = ways[finish[0]][finish[1]] - 1;

        while (true) {
            i = listQueueForBFS.get(0)[0];
            j = listQueueForBFS.get(0)[1];
            if (count == 2) {
                break;
            }
            if ((j < columns - 1) && (ways[i][j + 1] == count)) {
                map[i][j+1] = '+';
                int [] temp = {i, j+1};
                listQueueForBFS.add(temp);
            } else if ((i < rows - 1) && (ways[i+1][j] == count)) {
                map[i+1][j] = '+';
                int [] temp = {i+1, j};
                listQueueForBFS.add(temp);
            } else if ((j > 0) && (ways[i][j-1] == count)) {
                map[i][j-1] = '+';
                int [] temp = {i, j-1};
                listQueueForBFS.add(temp);
            } else if ((i > 0) && (ways[i-1][j] == count)) {
                map[i-1][j] = '+';
                int [] temp = {i-1, j};
                listQueueForBFS.add(temp);
            }
            count--;
            listQueueForBFS.remove(0);
        }
        return map;
    }

}

