class Solution {
    public boolean solution(int[][] key, int[][] originLock) {
        
        int[][] lock = expandLock(originLock, key.length);
        
        int rotationCnt = 0;
        while (rotationCnt < 4)
        {
            if (openLockUsingKey(lock, key))
                return true;
            
            key = rotateKey(key);
            rotationCnt++;
        }
        
        return false;
    }
    
    boolean openLockUsingKey(int[][] lock, int[][] key)
    {
        int maxRowCol = lock.length - key.length + 1;
        int lockSize = lock.length - (key.length - 1) * 2;
        for (int row = 0; row < maxRowCol; row++)
        {
            for (int col = 0; col < maxRowCol; col++)
            {
               int[][] tempLock = copyLock(lock);
                sumLockAndKey(tempLock, key, row, col);
                if (isOpened(tempLock, lockSize, key.length - 1))
                    return true;
            }
        }
        
        return false;
    }
    
    void sumLockAndKey(int[][] lock, int[][] key, int startRow, int startCol)
    {
        for (int row = 0; row < key.length; row++)
            for (int col = 0; col < key.length; col++)
                lock[row + startRow][col + startCol] += key[row][col];
    }
    
    boolean isOpened(int[][] lock, int lockSize, int startIdx)
    {
        for (int row = startIdx; row < startIdx + lockSize; row++)
            for (int col = startIdx; col < startIdx + lockSize; col++)
                if (lock[row][col] != 1)
                    return false;
        
        return true;
    }
    
    int[][] copyLock(int[][] lock)
    {
        int[][] newLock = new int[lock.length][lock.length];
        
        for (int row = 0; row < lock.length; row++)
            for (int col = 0; col < lock.length; col++)
                newLock[row][col] = lock[row][col];
        
        return newLock;
    }
    
    int[][] expandLock(int[][] originLock, int keyLength)
    {
        int lockSize = originLock.length + (keyLength - 1) * 2;
        int[][] lock = new int[lockSize][lockSize];
        
        for (int row = 0; row < originLock.length; row++)
            for (int col = 0; col < originLock.length; col++)
                lock[row + keyLength - 1][col + keyLength - 1] = originLock[row][col];
        
        return lock;
    }
    
    int[][] rotateKey(int[][] key)
    {
        int[][] rotatedKey = new int[key.length][key.length];
        
        for (int row = 0; row < key.length; row++)
            for (int col = 0; col < key.length; col++)
                rotatedKey[row][col] = key[key.length - col - 1][row];
        
        return rotatedKey;
    }
} 
