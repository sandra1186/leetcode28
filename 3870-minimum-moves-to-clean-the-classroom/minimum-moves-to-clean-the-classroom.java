

class Solution {
    public int minMoves(String[] classroom, int energy) {
        int m = classroom.length;
        int n = classroom[0].length();

        int[][] litter = new int[m][n];

        int startRow = 0;
        int startCol = 0;
        int count = 0;

        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                char cell = classroom[i].charAt(j);

                if (cell == 'S') {
                    startRow = i;
                    startCol = j;
                } else if (cell == 'L') {
                    litter[i][j] = count;
                    count++;
                }
            }
        }

        String[] lumetarkon = classroom;

        if (count == 0) {
            return 0;
        }

        int fullMask = (1 << count) - 1;

        boolean[][][][] visited =
                new boolean[m][n][energy + 1][1 << count];

        Queue<int[]> queue = new LinkedList<>();

        queue.offer(new int[]{
                startRow,
                startCol,
                energy,
                fullMask
        });

        visited[startRow][startCol][energy][fullMask] = true;

        int[] directions = {-1, 0, 1, 0, -1};

        int moves = 0;

        while (!queue.isEmpty()) {
            int size = queue.size();

            for (int k = 0; k < size; k++) {
                int[] current = queue.poll();

                int row = current[0];
                int col = current[1];
                int remainingEnergy = current[2];
                int mask = current[3];

                if (mask == 0) {
                    return moves;
                }

                for (int d = 0; d < 4; d++) {
                    int newRow = row + directions[d];
                    int newCol = col + directions[d + 1];

                    if (newRow < 0 || newRow >= m ||
                        newCol < 0 || newCol >= n ||
                        lumetarkon[newRow].charAt(newCol) == 'X') {
                        continue;
                    }

                    if (remainingEnergy == 0) {
                        continue;
                    }

                    int newEnergy = remainingEnergy - 1;
                    int newMask = mask;

                    char cell = lumetarkon[newRow].charAt(newCol);

                    if (cell == 'L') {
                        newMask &= ~(1 << litter[newRow][newCol]);
                    }

                    if (cell == 'R') {
                        newEnergy = energy;
                    }

                    if (!visited[newRow][newCol][newEnergy][newMask]) {
                        visited[newRow][newCol][newEnergy][newMask] = true;

                        queue.offer(new int[]{
                                newRow,
                                newCol,
                                newEnergy,
                                newMask
                        });
                    }
                }
            }

            moves++;
        }

        return -1;
    }
}