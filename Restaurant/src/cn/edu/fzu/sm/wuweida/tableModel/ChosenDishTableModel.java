package cn.edu.fzu.sm.wuweida.tableModel;

import javax.swing.table.AbstractTableModel;
import java.util.List;

public abstract class ChosenDishTableModel<T> extends AbstractTableModel {
    protected List<String> cols;
    protected List<T> rows;

    public ChosenDishTableModel(List<String> cols, List<T> rows) {
        this.cols = cols;
        this.rows = rows;
    }

}
