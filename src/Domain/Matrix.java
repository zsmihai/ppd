package Domain;

/**
 * Created by Utilizator on 08-Oct-17.
 */
public class Matrix{
    private int[][] matrix;
    private int rowCount;
    private int columnCount;

    private void validateRowIndex(int row) throws Exception {
        if(row < 0 || row >= this.rowCount)
        {
            throw new Exception("Row index out of bounds! Row: " + row + " Matrix.rowCount: " + this.rowCount);
        }
    }


    private void validateColumnIndex(int column) throws Exception {
        if(column < 0 || column >= this.columnCount)
        {
            throw new Exception("Column index out of bounds! Column: " + column + " Matrix.columnCount: " + this.columnCount);
        }
    }

    /*
    * !!potential error if _matrix is not a proper matrix (doesn't have rows of equal length)
    *  !!try to avoid this
     */
    public Matrix(int [][] _matrix)
    {
        matrix = _matrix;
        rowCount = matrix.length;
        if(rowCount >= 1)
        {
            columnCount = matrix[0].length;
        }
        else
        {
            columnCount = 0;
        }
    }

    public Matrix(int rowsCount, int columnsCount)
    {
        this.matrix = new int[rowsCount][columnsCount];
        this.rowCount = rowsCount;
        this.columnCount = columnsCount;
    }

    public void setElement(int row, int column, int element) throws Exception {
        validateRowIndex(row);
        validateColumnIndex(column);
        this.matrix[row][column] = element;
    }


    public int getElement(int row, int column) throws Exception {
        validateRowIndex(row);
        validateColumnIndex(row);
        return matrix[row][column];
    }

    public int getRowCount() {
        return rowCount;
    }

    public int getColumnCount() {
        return columnCount;
    }
}
