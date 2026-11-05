package org.example.twothousandfortyeight;

import java.util.Random;

public class Backend {
    private final Block[][] blocks = new Block[4][4];
    private final Random random = new Random();

    public Backend() {
        initializeBlocks();
    }

    private void initializeBlocks() {
        if (isFull()) {
            return;
        }
        int randomColumn = random.nextInt(blocks.length);
        int randomRow = random.nextInt(blocks.length);

        while (blocks[randomColumn][randomRow] != null) {
            randomColumn = random.nextInt(blocks.length);
            randomRow = random.nextInt(blocks.length);
        }
        blocks[randomColumn][randomRow] = new Block();
    }

    public void shift(boolean horizontal, boolean inverted) {

        if (horizontal) {
            for (int i = 0; i < blocks.length; i++) {
                if (inverted){
                    sortTop(i, inverted);
                } else {
                    sortBottom(i, inverted);
                }
            }
        } else {
            for (int i = 0; i < blocks.length; i++) {
                if (inverted){
                    sortLeft(i, inverted);
                } else {
                    sortRight(i, inverted);
                }
            }
        }
    }

    private void sortTop (int columnIndex, boolean inverted) {
        int index =  0;
        int merged = -1;
        Block[] sorted = new Block[blocks.length];

        for (int i = 0; i < 4; i++) {
            if (blocks[columnIndex][i] != null) {

                Block block = blocks[columnIndex][i];
                blocks[columnIndex][i] = null;
                blocks[columnIndex][index] = block;
                if (index > 0  && blocks[columnIndex][index] != null  && blocks[columnIndex][index - 1] != null  && index - 1 != merged){
                    if (blocks[columnIndex][index].getValue() == blocks[columnIndex][index - 1].getValue()) {
                        blocks[columnIndex][index - 1].add(blocks[columnIndex][index].getValue());
                        blocks[columnIndex][index] = null;
                        merged = index - 1;
                        index--;
                    }
                }

                index++;
            }

        }

    }

    private void sortBottom (int columnIndex, boolean inverted) {
        int index = 3;
        int merged = -1;

        for (int i = blocks[columnIndex].length - 1; i >= 0; i--) {
            if (blocks[columnIndex][i] != null) {
                Block block = blocks[columnIndex][i];
                blocks[columnIndex][i] = null;
                blocks[columnIndex][index] = block;
                if (index < 3  && blocks[columnIndex][index] != null && blocks[columnIndex][index + 1] != null && index + 1 != merged){
                    if (blocks[columnIndex][index].getValue() == blocks[columnIndex][index + 1].getValue()) {
                        blocks[columnIndex][index + 1].add(blocks[columnIndex][index].getValue());
                        blocks[columnIndex][index] = null;
                        merged = index + 1;
                        index++;
                    }
                }
                index--;
            }

        }

    }

    private void sortLeft (int rowIndex, boolean inverted) {
        int index = 3;
        int merged = -1;

        for (int i = blocks[rowIndex].length - 1; i >= 0; i--) {
            if (blocks[i][rowIndex] != null) {
                Block block = blocks[i][rowIndex];
                blocks[i][rowIndex] = null;
                blocks[index][rowIndex] = block;
                if (index < 3  && blocks[index][rowIndex] != null && blocks[index + 1][rowIndex] != null && index + 1 != merged){
                    if (blocks[index][rowIndex].getValue() == blocks[index + 1][rowIndex].getValue()) {
                        blocks[index + 1][rowIndex].add(blocks[index + 1][rowIndex].getValue());
                        blocks[index][rowIndex] = null;
                        merged = index + 1;
                        index++;
                    }
                }
                index--;
            }
        }
    }

    private void sortRight (int rowIndex, boolean inverted) {
        int index =  0;
        int merged = -1;

        for (int i = 0; i < 4; i++) {
            if (blocks[i][rowIndex] != null) {
                Block block = blocks[i][rowIndex];
                blocks[i][rowIndex] = null;
                blocks[index][rowIndex] = block;
                if (index > 0 && blocks[index][rowIndex] != null && blocks[index - 1][rowIndex] != null && index - 1 != merged){
                    if (blocks[index][rowIndex].getValue() == blocks[index - 1][rowIndex].getValue()) {
                        blocks[index - 1][rowIndex].add(blocks[index][rowIndex].getValue());
                        blocks[index][rowIndex] = null;
                        merged = index - 1;
                        index--;
                    }
                }
                index++;
            }
        }
    }




    private boolean isFull() {
        for (Block[] column : blocks) {
            for (Block block : column) {
                if (block == null) {
                    return false;
                }
            }
        }
        return true;
    }

    public void insert(){
        initializeBlocks();
    }

    public Block[][] getBlocks(){
        return this.blocks;
    }


}
