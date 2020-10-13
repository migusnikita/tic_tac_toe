package ru.mail.migus_nikita;

public class Field {

    private static final int FIELD_SIZE = 3;
    private static final int[][] PLAYING_FIELD = new int[FIELD_SIZE][FIELD_SIZE];
    private static int[] weights = new int[] {3, 2, 3, 2, 4, 2, 3, 2, 3};

    private int[] determineCoordinates(int turn) {
        int[] coordinates = new int[2];
        coordinates[0] = (turn % FIELD_SIZE == 0) ? turn / FIELD_SIZE - 1 : turn / FIELD_SIZE;
        coordinates[1] = (turn % FIELD_SIZE == 0) ? FIELD_SIZE - 1 : turn % FIELD_SIZE - 1;
        return coordinates;
    }

    public void makeMove(int turn, int coordinateN) {
        int[] coordinates = determineCoordinates(turn);
        if (PLAYING_FIELD[coordinates[0]][coordinates[1]] == 0) {
            PLAYING_FIELD[coordinates[0]][coordinates[1]] = coordinateN;
        }
    }

    public void printBoard() {
        System.out.println();
        for (int x = 0; x < FIELD_SIZE; x++) {
            for (int y = 0; y < FIELD_SIZE; y++) {
                if (PLAYING_FIELD[x][y] == 0) {
                    System.out.print(" _");
                } else if (PLAYING_FIELD[x][y] == 1) {
                    System.out.print(" X");
                } else {
                    System.out.print(" O");
                }
            }
            System.out.println();
        }
        System.out.println();
    }

    public int declareWinner() {
        int winner = 0;
        if (diagonalWinner() != 0) {
            winner = diagonalWinner();
        } else if (rowWinner() != 0) {
            winner = rowWinner();
        } else if (columnWinner() != 0) {
            winner = columnWinner();
        }
        for (int x = 0; x < FIELD_SIZE; x++) {
            for (int y = 0; y < FIELD_SIZE; y++) {
                if (PLAYING_FIELD[x][y] == 0) {
                    return winner;
                }
            }
        }
        winner = 3;
        return winner;
    }

    private int diagonalWinner() {
        for (int x = 1; x < FIELD_SIZE; x++) {
            if (PLAYING_FIELD[x][x] != PLAYING_FIELD[0][0]) {
                x = FIELD_SIZE;
            }
            if (x == FIELD_SIZE - 1) {
                return PLAYING_FIELD[0][0];
            }
        }
        for (int x = 1; x < FIELD_SIZE; x++) {
            if (PLAYING_FIELD[x][FIELD_SIZE - x - 1] != PLAYING_FIELD[0][FIELD_SIZE - 1]) {
                x = FIELD_SIZE;
            }
            if (x == FIELD_SIZE - 1) {
                return PLAYING_FIELD[0][FIELD_SIZE - 1];
            }
        }
        return 0;
    }

    private int rowWinner() {
        for (int x = 0; x < FIELD_SIZE; x++) {
            for (int y = 1; y < FIELD_SIZE; y++) {
                if (PLAYING_FIELD[x][y] != PLAYING_FIELD[x][0]) {
                    break;
                }
                if (y == FIELD_SIZE - 1) {
                    return PLAYING_FIELD[x][0];
                }
            }
        }
        return 0;
    }

    private int columnWinner() {
        for (int y = 0; y < FIELD_SIZE; y++) {
            for (int x = 1; x < FIELD_SIZE; x++) {
                if (PLAYING_FIELD[x][y] != PLAYING_FIELD[0][y]) {
                    break;
                }
                if (x == FIELD_SIZE - 1) {
                    return PLAYING_FIELD[0][y];
                }
            }
        }
        return 0;
    }

    public boolean canMove(int turn) {
        int[] coordinates = determineCoordinates(turn);
        return turn <= 0 || turn > FIELD_SIZE * FIELD_SIZE || PLAYING_FIELD[coordinates[0]][coordinates[1]] != 0;
    }

    private int pcMove() {
        int cell = 0;
        int max = Integer.MIN_VALUE;
        for (int i = 0; i < weights.length; i++) {
            if (weights[i] > max) {
                max = weights[i];
                cell = i + 1 + 1;
            }
        }

        int firstRandom = 3 * ((int) (Math.random() * 2) + 1);
        firstRandom++;
        int[] coordinates1 = determineCoordinates(firstRandom);
        int secondRandom = 3 + 2 * (int) (Math.pow(-1, (int) (Math.random() * 2)));
        secondRandom++;
        int[] coordinates2 = determineCoordinates(secondRandom);

        if (PLAYING_FIELD[1][1] == 0) {
            cell = 5;
        } else if ((PLAYING_FIELD[0][0] == 1 || PLAYING_FIELD[1][2] == 1) && PLAYING_FIELD[0][2] == 0) {
            cell = 3;
        } else if ((PLAYING_FIELD[0][2] == 1 || PLAYING_FIELD[2][1] == 1) && PLAYING_FIELD[2][2] == 0) {
            cell = 9;
        } else if ((PLAYING_FIELD[2][0] == 1 || PLAYING_FIELD[0][1] == 1) && PLAYING_FIELD[0][0] == 0) {
            cell = 1;
        } else if ((PLAYING_FIELD[0][0] == 1 || PLAYING_FIELD[2][2] == 1) && PLAYING_FIELD[coordinates1[0]][coordinates1[1]] == 0) {
            cell = firstRandom;
        } else if ((PLAYING_FIELD[2][1] == 1 || PLAYING_FIELD[0][2] == 1) && PLAYING_FIELD[coordinates2[0]][coordinates2[1]] == 0) {
            cell = secondRandom;
        }

        return cell;
    }

    public void pcTurn() {

        //		 Blocking 1 for win on sides on the middle
        //		 Blocking 1 for win on Middle
        if (PLAYING_FIELD[0][0] == 1 && PLAYING_FIELD[0][2] == 1 && PLAYING_FIELD[0][1] == 0) {
            PLAYING_FIELD[0][1] = 2;
            return;
        } else if (PLAYING_FIELD[0][0] == 1 && PLAYING_FIELD[2][0] == 1 && PLAYING_FIELD[1][0] == 0) {
            PLAYING_FIELD[1][0] = 2;
            return;
        } else if (PLAYING_FIELD[2][0] == 1 && PLAYING_FIELD[2][2] == 1 && PLAYING_FIELD[2][1] == 0) {
            PLAYING_FIELD[2][1] = 2;
            return;
        } else if (PLAYING_FIELD[0][2] == 1 && PLAYING_FIELD[2][2] == 1 && PLAYING_FIELD[1][2] == 0) {
            PLAYING_FIELD[1][2] = 2;
            return;
        } else

            // Blocking 1 for win on Middle
            if (PLAYING_FIELD[1][0] == 1 && PLAYING_FIELD[1][1] == 1 && PLAYING_FIELD[1][2] == 0) {
                PLAYING_FIELD[1][2] = 2;
                return;
            } else if (PLAYING_FIELD[1][2] == 1 && PLAYING_FIELD[1][1] == 1 && PLAYING_FIELD[1][0] == 0) {
                PLAYING_FIELD[1][0] = 2;
                return;
            } else if (PLAYING_FIELD[2][1] == 1 && PLAYING_FIELD[1][1] == 1 && PLAYING_FIELD[0][1] == 0) {
                PLAYING_FIELD[0][1] = 2;
                return;
            } else if (PLAYING_FIELD[0][1] == 1 && PLAYING_FIELD[1][1] == 1 && PLAYING_FIELD[2][1] == 0) {
                PLAYING_FIELD[2][1] = 2;
                return;
            } else

                // Blocking 1 Win on Sides
                if (PLAYING_FIELD[0][0] == 1 && PLAYING_FIELD[0][1] == 1 && PLAYING_FIELD[0][2] == 0) {
                    PLAYING_FIELD[0][2] = 2;
                    return;
                } else if (PLAYING_FIELD[0][0] == 1 && PLAYING_FIELD[1][0] == 1 && PLAYING_FIELD[2][0] == 0) {
                    PLAYING_FIELD[2][0] = 2;
                    return;
                } else
                    // Win for PC Sides
                    if (PLAYING_FIELD[0][0] == 2 && PLAYING_FIELD[0][1] == 2 && PLAYING_FIELD[0][2] == 0) {
                        PLAYING_FIELD[0][2] = 2;
                        return;
                    } else if (PLAYING_FIELD[0][0] == 2 && PLAYING_FIELD[1][0] == 2) {
                        PLAYING_FIELD[2][0] = 2;
                        return;
                    } else if (PLAYING_FIELD[2][0] == 2 && PLAYING_FIELD[2][1] == 2 && PLAYING_FIELD[2][2] == 0) {
                        PLAYING_FIELD[2][2] = 2;
                        return;
                    } else if (PLAYING_FIELD[2][2] == 2 && PLAYING_FIELD[1][2] == 2 && PLAYING_FIELD[0][2] == 0) {
                        PLAYING_FIELD[0][2] = 2;
                        return;
                    }

                    // Win for PC Middle
                    else if (PLAYING_FIELD[1][0] == 2 && PLAYING_FIELD[1][1] == 2 && PLAYING_FIELD[1][2] == 0) {
                        PLAYING_FIELD[1][2] = 2;
                        return;
                    } else if (PLAYING_FIELD[1][2] == 2 && PLAYING_FIELD[1][1] == 2 && PLAYING_FIELD[1][0] == 0) {
                        PLAYING_FIELD[1][0] = 2;
                        return;
                    } else if (PLAYING_FIELD[2][1] == 2 && PLAYING_FIELD[1][1] == 2 && PLAYING_FIELD[0][1] == 0) {
                        PLAYING_FIELD[0][1] = 2;
                        return;
                    } else if (PLAYING_FIELD[0][1] == 2 && PLAYING_FIELD[1][1] == 2 && PLAYING_FIELD[2][1] == 0) {
                        PLAYING_FIELD[2][1] = 2;
                        return;
                    } else

                        // Middle
                        if (PLAYING_FIELD[1][1] == 0) {
                            PLAYING_FIELD[1][1] = 2;
                            return;
                        } else if (PLAYING_FIELD[2][0] == 1 && PLAYING_FIELD[2][1] == 1 && PLAYING_FIELD[2][2] == 0) {
                            PLAYING_FIELD[2][2] = 2;
                            return;
                        } else if (PLAYING_FIELD[2][2] == 1 && PLAYING_FIELD[1][2] == 1 && PLAYING_FIELD[0][2] == 0) {
                            PLAYING_FIELD[0][2] = 2;
                            return;
                        } else if (PLAYING_FIELD[1][1] == 0) {
                            PLAYING_FIELD[1][1] = 2;
                            return;
                        } else
                            // Corners.
                            if (PLAYING_FIELD[0][0] == 0) {
                                PLAYING_FIELD[0][0] = 2;
                                return;
                            } else if (PLAYING_FIELD[0][2] == 0) {
                                PLAYING_FIELD[0][2] = 2;
                                return;
                            } else if (PLAYING_FIELD[2][0] == 0) {
                                PLAYING_FIELD[2][0] = 2;
                                return;
                            } else if (PLAYING_FIELD[2][2] == 0) {
                                PLAYING_FIELD[2][2] = 2;
                                return;
                            } else if

                                // The Last empty fields
                            (PLAYING_FIELD[0][0] == (0)) {
                                PLAYING_FIELD[0][0] = 2;
                                return;
                            } else if (PLAYING_FIELD[0][1] == (0)) {
                                PLAYING_FIELD[0][1] = 2;
                                return;
                            } else if (PLAYING_FIELD[0][2] == (0)) {
                                PLAYING_FIELD[0][2] = 2;
                                return;
                            } else if (PLAYING_FIELD[1][0] == (0)) {
                                PLAYING_FIELD[1][0] = 2;
                                return;
                            } else if (PLAYING_FIELD[1][1] == (0)) {
                                PLAYING_FIELD[1][1] = 2;
                                return;
                            } else if (PLAYING_FIELD[1][2] == (0)) {
                                PLAYING_FIELD[1][2] = 2;
                                return;
                            } else if (PLAYING_FIELD[2][0] == (0)) {
                                PLAYING_FIELD[2][0] = 2;
                                return;
                            } else if (PLAYING_FIELD[2][1] == (0)) {
                                PLAYING_FIELD[2][1] = 2;
                                return;
                            } else if (PLAYING_FIELD[2][2] == (0)) {
                                PLAYING_FIELD[2][2] = 2;
                                return;
                            }
        int pcMoveField = pcMove();
        int[] coordinates = determineCoordinates(pcMoveField);
        if (PLAYING_FIELD[coordinates[0]][coordinates[1]] == 0) {
            PLAYING_FIELD[coordinates[0]][coordinates[1]] = 2;
        }
    }

}
