package org.hyperskill.blockchain;

import java.util.Date;
import java.util.Random;

public class Miner extends Thread {

    int name;
    public Miner(int name) {
        this.name = name;
    }

    Random random = new Random();
    Block block;
    private static final Object lock = new Object();

    void generateBlock() {

        synchronized (lock) {
            int id = BlockChain.getBlockChain().blockChainList.size() + 1;
            long timeStamp = new Date().getTime();
            int magicNumber;
            String previousHash = id == 1 ? "0" : BlockChain.getBlockChain().blockChainList.get(id - 2).getHash();

            String hash;
            do {
                magicNumber = random.nextInt(Integer.MAX_VALUE);
                hash = Sha256.applySha256(previousHash + id + timeStamp + magicNumber);
            } while (!hash.startsWith(BlockChain.getBlockChain().zeros()));

            long proofOfWork = (new Date().getTime() - timeStamp) ;

            String N = BlockChain.getBlockChain().updateZeros(proofOfWork);

            block = new Block(name, id, timeStamp, magicNumber, previousHash, hash, proofOfWork, N);

            if (BlockChain.getBlockChain().isValid(block)) {
                System.out.println(block);
            } else if (BlockChain.getBlockChain().blockChainList.size() == 1) {
                System.out.println(block);
            } else {
                System.out.println("Block not accepted!");
            }
        }
    }

    @Override
    public void run() {
        generateBlock();
    }
}
