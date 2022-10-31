package cn.edu.fzu.sm.zhangsan.stu.entity;

import javax.swing.table.AbstractTableModel;
import java.util.List;

public class StudentTableModel<T> extends AbstractTableModel {
    protected List<String> cols;
    protected List<T> rows;

    public StudentTableModel(List<String> cols, List<T> rows) {
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
        Student student= (Student) rows.get(rowIndex);
        if (columnIndex==0){
            return student.getSid();
        }else if (columnIndex==1){
            return student.getSname();
        }else if (columnIndex==2){
            return student.getSage();
        }else if (columnIndex==3){
            return student.getSaddress();
        }else if (columnIndex==4){
            return student.getSnumber();
        }else if (columnIndex==5){
            return student.getSphone();
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
