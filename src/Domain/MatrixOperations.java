package Domain;

/**
 * Created by Utilizator on 08-Oct-17.
 */
public class MatrixOperations {

    private static class AdditionThread implements Runnable
    {
        private int startRow;
        private int endRow;
        private int startColumn;
        private int endColumn;
        private Matrix firstMatrix;
        private Matrix secondMatrix;
        private Matrix result;

        public AdditionThread(int startRow, int endRow, int startColumn, int endColumn, Matrix firstMatrix, Matrix secondMatrix, Matrix result) {
            this.startRow = startRow;
            this.endRow = endRow;
            this.startColumn = startColumn;
            this.endColumn = endColumn;
            this.firstMatrix = firstMatrix;
            this.secondMatrix = secondMatrix;
            this.result = result;
        }

        @Override
        public void run() {

            int currentRow = startRow;
            int currentColumn = startColumn;
            int sum;

            while(currentRow != endRow || currentColumn != endColumn)
            {
                try
                {
                    sum = firstMatrix.getElement(currentRow, currentColumn) + secondMatrix.getElement(currentRow, currentColumn);
                    result.setElement(currentRow, currentColumn, sum);
                } catch (Exception e)
                {
                    e.printStackTrace();
                }

                if(currentColumn == firstMatrix.getColumnCount())
                {
                    currentColumn = 0;
                    currentRow++;
                }
                else
                {
                    currentColumn++;
                }
            }
        }
    }



    private static void validateMatricesForAddition(Matrix firstMatrix, Matrix secondMatrix) throws Exception {
        int firstMatrixColumnCount = firstMatrix.getColumnCount();
        int secondMatrixColumnCount = secondMatrix.getColumnCount();
        int firstMatrixRowCount = firstMatrix.getRowCount();
        int secondMatrixRowCount = secondMatrix.getRowCount();


        if(firstMatrixColumnCount != secondMatrixColumnCount)
        {
            throw new Exception("Invalid parameters for matrix addition! Column count: firstMatrix - " + firstMatrixColumnCount +
                    "; secondMatrix - " + secondMatrixColumnCount);
        }

        if(firstMatrixRowCount != secondMatrixRowCount)
        {
            throw new Exception("Invalid parameters for matrix addition! Row count: firstMatrix - " + firstMatrixRowCount +
                    "; secondMatrix - " + secondMatrixRowCount);
        }
    }

    private static void validateThreadCount(Matrix firstMatrix, Matrix secondMatrix, int threadCount) throws Exception {
        if(threadCount < 1)
        {
            throw new Exception("ThreadCount too small! ThreadCount should be at least 1!");
        }
        if( threadCount > firstMatrix.getRowCount() * firstMatrix.getColumnCount())
        {
            throw new Exception("ThreadCount too large! ThreadCount should not exceed number of elements in a matrix");
        }
    }

    public static Matrix addMatricesWithThreads(Matrix firstMatrix, Matrix secondMatrix, int threadCount) throws Exception {

        Matrix resultMatrix;
        AdditionThread[] threads;
        int rowCount;
        int columnCount;
        int currentIndex;
        int previousIndex;
        int remainder;
        int quotient;

        validateMatricesForAddition(firstMatrix, secondMatrix);
        validateThreadCount(firstMatrix, secondMatrix, threadCount);

        rowCount = firstMatrix.getRowCount();
        columnCount = firstMatrix.getColumnCount();

        resultMatrix = new Matrix(rowCount, columnCount);
        threads = new AdditionThread[threadCount];

        remainder = (rowCount * columnCount) % threadCount;
        quotient = (rowCount * columnCount) / threadCount;

        currentIndex = quotient - 1;
        previousIndex = 0;

        for(int threadIndex = 0; threadIndex<threadCount; threadIndex++)
        {
            if(threadIndex < remainder)
            {
                currentIndex++;
            }
            threads[threadIndex] = new AdditionThread(
                                     previousIndex / columnCount,
                                    previousIndex % columnCount,
                                 currentIndex / columnCount,
                                currentIndex % columnCount,
                                            firstMatrix,
                                            secondMatrix,
                                            resultMatrix);

            previousIndex = currentIndex;
            currentIndex += quotient;
        }

        for(int threadIndex = 0; threadIndex<threadCount; threadIndex++)
        {
            threads[threadIndex].run();
        }
        for(int threadIndex = 0; threadIndex<threadCount; threadIndex++)
        {
            threads[threadIndex].wait();
        }

        return resultMatrix;
    }


}
