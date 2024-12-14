
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class QuickFitMemory {
    private static final int MEMORY_POOL_SIZE = 1024; // Total memory pool size (1024 KB)
    private static final int[] BLOCK_SIZES = {64, 128, 256}; // Fixed block sizes in KB
    private Map<Integer, List<Integer>> freeBlocks;

    public QuickFitMemory() {
        freeBlocks = new HashMap<>();
        // Initialize free blocks for each block size
        for (int size : BLOCK_SIZES) {
            freeBlocks.put(size, new ArrayList<>());
            for (int i = 0; i < MEMORY_POOL_SIZE / size; i++) {
                freeBlocks.get(size).add(i); // Simulate memory blocks
            }
        }
    }

    // Allocate memory of a given size (in KB)
    public Integer allocate(int size) {
        if (!freeBlocks.containsKey(size) || freeBlocks.get(size).isEmpty()) {
            System.out.println("No available blocks of size " + size + " KB.");
            return null; // No available memory of the requested size
        }

        List<Integer> availableBlocks = freeBlocks.get(size);
        Integer allocatedBlock = availableBlocks.remove(availableBlocks.size() - 1); // Allocate the last block
        System.out.println("Allocated " + size + " KB from block index " + allocatedBlock);
        return allocatedBlock;
    }

    // Deallocate memory by returning the block to the free list
    public void deallocate(int size, Integer blockIndex) {
        if (blockIndex == null) {
            System.out.println("Invalid block index.");
            return;
        }

        if (!freeBlocks.containsKey(size)) {
            System.out.println("Invalid block size.");
            return;
        }

        List<Integer> availableBlocks = freeBlocks.get(size);
        availableBlocks.add(blockIndex); // Return the block to the free list
        System.out.println("Deallocated " + size + " KB from block index " + blockIndex);
    }

    public static void main(String[] args) {
        QuickFitMemory memoryManager = new QuickFitMemory();

        // Allocation of different sizes
        Integer block1 = memoryManager.allocate(64); // Allocate 64 KB
        Integer block2 = memoryManager.allocate(128); // Allocate 128 KB
        Integer block3 = memoryManager.allocate(64); // Allocate 64 KB
        Integer block4 = memoryManager.allocate(256); // Allocate 256 KB

        // Deallocation of memory blocks
        memoryManager.deallocate(64, block1); // Deallocate the first 64 KB block
        memoryManager.deallocate(128, block2); // Deallocate the 128 KB block
        memoryManager.deallocate(64, block3); // Deallocate the second 64 KB block
        memoryManager.deallocate(256, block4); // Deallocate the 256 KB block

        // Trying to allocate memory after deallocation
        memoryManager.allocate(64); // Allocate 64 KB again after deallocation
    }
}
