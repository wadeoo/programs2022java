package cn.edu.fzu.sm.wuweida.tableModel;

import cn.edu.fzu.sm.wuweida.bean.NamePriceQuantity;

import javax.swing.table.AbstractTableModel;
import java.util.List;

public class ChosenDishTableModel<T> extends AbstractTableModel {
    protected List<String> cols;
    protected List<T> rows;

    public ChosenDishTableModel(List<String> cols, List<T> rows) {
        this.cols = cols;
        this.rows = rows;
    }
    @Override
    public int getRowCount() {
        return rows.size();
    }

    @Override
    public int getColumnCount() {
        return cols.size();
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        NamePriceQuantity namePriceQuantity= (NamePriceQuantity) rows.get(rowIndex);
        if (columnIndex==0){
            return namePriceQuantity.getFoodName();
        }else if (columnIndex==1){
            return namePriceQuantity.getPrice();
        }else if(columnIndex==2){
            return namePriceQuantity.getQuantity();
        }
        return null;
    }

    @Override
    public String getColumnName(int column){
        return cols.get(column);
    }

    public void setRows(List<T> rows) {
        this.rows=rows;
    }

}
