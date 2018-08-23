package homeWorkShortestPathProblem;

import java.util.Comparator;
import java.util.SortedSet;
import java.util.TreeSet;

public class AStarSearch implements Navigator {

    @Override
    public char[][] searchRoute(char[][] map) {
        int[] mapSize = getMapSize(map);
        int[] start = getStartCoordinates(map);
        int[] finish = getFinishCoordinates(map);
        int[][] ways = doAstarSearch(map, start, finish, mapSize);
        map = createShortestPathAs(map, ways, finish, start);
        return map;
    }

    private class AStarSearchComparator implements Comparator<int[]> {
        public int compare(int[] a, int[] b) {
            if (a[0] == b[0]) {
                if (a[1] == b[1]) {
                    if (a[2] == b[2]) {
                        return a[3] - b[3];
                    }
                    return a[2] - b[2];
                }
                return a[1] - b[1];
            }
            return a[0] - b[0];
        }
    }

    private class AStarBuildPathComparator implements Comparator<int[]> {
        public int compare(int[] a, int[] b) {
            if (a[0] == b[0]) {
                if (a[1] == b[1]) {
                    if (a[2] == b[2]) {
                        return a[3] - b[3];
                    }
                    return a[2] - b[2];
                }
                return b[1] - a[1];
            }
            return a[0] - b[0];
        }
    }

    private int[] getMapSize(char[][] map) {
        int[] mapSize = {map.length, map[0].length};
        return mapSize;
    }

    private int[] getStartCoordinates(char[][] map) {
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

    private int[] getFinishCoordinates(char[][] map) {
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

    private int[][] doAstarSearch(char[][] map, int[] start, int[] finish, int[] mapSize) {
        int rows = mapSize[0];
        int columns = mapSize[1];
        Comparator<int[]> comp = new AStarSearchComparator();
        SortedSet<int[]> priorities = new TreeSet<>(comp);
        int x = 0;
        int y = 0;
        int optimist;
        int distanceToTheEnd;
        int i = start[0];
        int j = start[1];
        int[][] ways = new int[rows][columns];
        ways[i][j] = Math.abs(finish[0] - i) + Math.abs(finish[1] - j);

        while (true) {
            for (int k = 0; k < 4; k++) {
                if (k == 0) {
                    if ((j < columns - 1) && (map[i][j + 1] != '#')) {
                        x = i;
                        y = j + 1;
                    } else {
                        continue;
                    }
                } else if (k == 1) {
                    if ((i < rows - 1) && (map[i + 1][j] != '#')) {
                        x = i + 1;
                        y = j;
                    } else {
                        continue;
                    }
                } else if (k == 2) {
                    if ((j > 0) && (map[i][j - 1] != '#')) {
                        x = i;
                        y = j - 1;
                    } else {
                        continue;
                    }
                } else if (k == 3) {
                    if ((i > 0) && (map[i - 1][j] != '#')) {
                        x = i - 1;
                        y = j;
                    } else {
                        continue;
                    }
                }
                if ((x == finish[0]) && (y == finish[1])) {
                    ways[x][y] = ways[i][j];
                    return ways;
                }
                optimist = ways[i][j] - (Math.abs(finish[0] - i) + Math.abs(finish[1] - j)) + 1 + ((Math.abs(finish[0] - x) + Math.abs(finish[1] - y)));
                distanceToTheEnd = (Math.abs(finish[0] - x) + Math.abs(finish[1] - y));
                if (ways[x][y] == 0) {
                    ways[x][y] = optimist;
                    priorities.add(new int[]{optimist, distanceToTheEnd, x, y});
                } else if (ways[x][y] > optimist) {
                    priorities.remove(new int[]{ways[x][y], distanceToTheEnd, x, y});
                    ways[x][y] = optimist;
                    priorities.add(new int[]{optimist, distanceToTheEnd, x, y});
                }
            }
            try {
                i = priorities.first()[2];
                j = priorities.first()[3];
                priorities.remove(priorities.first());
            } catch (Exception e) {
                return null;
            }
        }
    }

    private char[][] createShortestPathAs(char[][] map, int[][] ways, int[] finish, int[] start) {
        if (ways == null) {
            return null;
        }
        int rows = map.length;
        int columns = map[0].length;
        Comparator<int[]> comp = new AStarBuildPathComparator();
        SortedSet<int[]> priorities = new TreeSet<>(comp);
        int i = finish[0];
        int j = finish[1];
        ways[start[0]][start[1]] = 1;
        ways[finish[0]][finish[1]] = 0;
        int x = 0;
        int y = 0;

        while (true) {
            for (int k = 0; k < 4; k++) {
                if (k == 0) {
                    if ((j < columns - 1) && (ways[i][j + 1] > 0)) {
                        x = i;
                        y = j + 1;
                    } else {
                        continue;
                    }
                } else if (k == 1) {
                    if ((i < rows - 1) && (ways[i + 1][j] > 0)) {
                        x = i + 1;
                        y = j;
                    } else {
                        continue;
                    }
                } else if (k == 2) {
                    if ((j > 0) && (ways[i][j - 1] > 0)) {
                        x = i;
                        y = j - 1;
                    } else {
                        continue;
                    }
                } else if (k == 3) {
                    if ((i > 0) && (ways[i - 1][j] > 0)) {
                        x = i - 1;
                        y = j;
                    } else {
                        continue;
                    }
                }
                if ((x == start[0]) && (y == start[1])) {
                   return map;
                }
                priorities.add(new int[]{ways[x][y], (Math.abs(finish[0] - x) + Math.abs(finish[1] - y)), x, y});
                ways[x][y] = 0;
            }
            try {
                i = priorities.first()[2];
                j = priorities.first()[3];
                map[i][j] = '+';
                priorities.remove(priorities.first());
            } catch (Exception e) {
                return null;
            }
        }
    }

}











