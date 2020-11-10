package org.hyperskill.blockchain;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;


public class Main {
    public static void main(String[] args) {

        String fileName = "blockChainFile.data";
        File file = new File(fileName);

        if (file.length() != 0) {
            try {
                BlockChain.getBlockChain().blockChainList = (ArrayList<Block>) SerializationUtils.deserialize(fileName);
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }

        // mining simulation

        Miner miner1 = new Miner(1);
        Miner miner2 = new Miner(2);
        Miner miner3 = new Miner(3);

        miner1.start();
        miner2.start();
        miner3.start();


        try {
            miner1.join();
            miner2.join();
            miner3.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


        try {
            SerializationUtils.serialize(BlockChain.getBlockChain().blockChainList, fileName);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
