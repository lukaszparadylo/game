package pl.lukaszparadylo.scanner;

public class Scanner {
    public Integer[] getDataFromBackSlash(Character[][] data){
        Integer dataFromBackSlash[] = {0,0,0};
        for (int i = 0; i<data.length;i++){
            if(data[i][i]=='_')dataFromBackSlash[0]++;
            if(data[i][i]=='X')dataFromBackSlash[1]++;
            if(data[i][i]=='O')dataFromBackSlash[2]++;
        }
        return dataFromBackSlash;
    }
    public Integer[] getDataFromColumn(Character[][] columns, Integer columnNumber){
        Integer dataFromColumn[] = {0,0,0};
        for (int i = 0; i<columns.length;i++){
            if(columns[i][columnNumber]=='_')dataFromColumn[0]++;
            if(columns[i][columnNumber]=='X')dataFromColumn[1]++;
            if(columns[i][columnNumber]=='O')dataFromColumn[2]++;
        }
        return dataFromColumn;
    }
    public Integer[] getDataFromRows(Character[][] rows, Integer rowNumber){
        Integer dataFromRows[] = {0,0,0};
        for (int i = 0; i<rows.length;i++){
            if(rows[rowNumber][i]=='_')dataFromRows[0]++;
            if(rows[rowNumber][i]=='X')dataFromRows[1]++;
            if(rows[rowNumber][i]=='O')dataFromRows[2]++;
        }
        return dataFromRows;
    }
    public Integer[] getDataFromForwardSlash(Character[][] data){
        Integer dataFromForwardSlash[] = {0,0,0};
        for (int i = 0; i<data.length;i++){
            if(data[data.length-i-1][i]=='_')dataFromForwardSlash[0]++;
            if(data[data.length-i-1][i]=='X')dataFromForwardSlash[1]++;
            if(data[data.length-i-1][i]=='O')dataFromForwardSlash[2]++;
        }
        return dataFromForwardSlash;
    }
}
