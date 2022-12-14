package com.mygdx.game.game_elements;

public class Map {

    public static int[][] generate(int width, int height, float countRock, float countGold) {
        // 0 = grass
        // 1 = road gorozontal
        // 2 = road vetical
        // 3 = road right-up
        // 4 = road down-right
        // 5 = road right-down
        // 6 = road up-left
        int[][] map = new int[height][width];

        boolean isStop = false;
        int pointY, pointX = 0;

        pointY = randomNumber(map.length - 1);
        map[pointY][0] = 1;

        while (!isStop) {
            for (int i = 0; i < randomNumber(4) + 1; i++) {
                pointX += 1;
                if (pointX == map[0].length) {
                    isStop = true;
                    break;
                }
                map[pointY][pointX] = 1;
            }

            if (isStop) break;

            if (pointY == map.length - 1) {
                map[pointY][pointX] = 4;
                int randomNumber = randomNumber(3) + 1;

                for (int i = 0; i < randomNumber; i++) {
                    pointY -= 1;
                    map[pointY][pointX] = 2;
                    if (i == randomNumber - 1) {
                        map[pointY][pointX] = 5;
                        break;
                    }
                }
                continue;
            }
            if (pointY == 0) {
                map[pointY][pointX] = 6;
                int randomNumber = randomNumber(3) + 1;

                for (int i = 0; i < randomNumber; i++) {
                    pointY += 1;
                    map[pointY][pointX] = 2;
                    if (i == randomNumber - 1) {
                        map[pointY][pointX] = 3;
                        break;
                    }
                }
                continue;
            }

            int upOrDown = randomNumber(1);
            // up = 0
            // down = 1
            if (upOrDown == 0) {
                int randomNumber = randomNumber(4);
                map[pointY][pointX] = 4;

                for (int i = 0; i <= randomNumber; i++) {
                    pointY--;

                    map[pointY][pointX] = 2;
                    if (i == randomNumber) {
                        map[pointY][pointX] = 5;
                        break;
                    }

                    if (pointY == 0) {
                        map[pointY][pointX] = 5;
                        break;
                    }
                }
                map[pointY][pointX] = 5;
            }
            if (upOrDown == 1) {
                map[pointY][pointX] = 6;
                int randomNumber = randomNumber(3) + 2;

                for (int i = 0; i <= randomNumber; i++) {
                    pointY++;

                    map[pointY][pointX] = 2;
                    if (i == randomNumber) {
                        map[pointY][pointX] = 3;
                        break;
                    }

                    if (pointY == map.length - 1) {
                        map[pointY][pointX] = 3;
                        break;
                    }
                }
                map[pointY][pointX] = 3;
            }

        }

        map[pointY][pointX - 1] = 9;

        setObjectOnMap(map, 8, countRock);
        setObjectOnMap(map, 7, countGold);

        return map;
    }


    private static void setObjectOnMap(int[][] map, int id, float count) {
        for (int i = 0; i < map.length * map[0].length / (map[0].length / count); i++) {
            int[] rockPoint;
            do {
                rockPoint = new int[]{randomNumber(map.length - 1), randomNumber(map[0].length - 1)};
            } while (map[rockPoint[0]][rockPoint[1]] != 0);
            map[rockPoint[0]][rockPoint[1]] = id;
        }
    }

    private static int randomNumber(int value) {
        return (int) (Math.random() * (value + 1));
    }
}
