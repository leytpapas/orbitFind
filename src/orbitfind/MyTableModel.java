package orbitfind;


 
import java.awt.Component;
import java.util.Vector;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
 
/** 
 * TableDemo is just like SimpleTableDemo, except that it
 * uses a custom TableModel.
 */
public class MyTableModel extends DefaultTableModel {
        private Boolean DEBUG = false;
        private String[] columnNames = {"Enable",
                                        "Satellite's Name",
                                        "File",
                                        "Dec"};
        private Object[][] data= {
        {new Boolean(false),"", "", new Double(0)}
        /*,
        { new Boolean(false),"Sue", "Black", new Double(2)},
        { new Boolean(false),"Jane", "White", new Double(20)},
        { new Boolean(false),"Joe", "Brown", new Double(10)}*/
        };

        
        
        @Override
        public int getColumnCount() {
            return columnNames.length;
        }
        
        @Override
       public void addRow(Vector rowData) {
           insertRow(getRowCount(), rowData);
       }
       
       
/*
        @Override
        public int getRowCount() {
            return data.length;
        }
 */
        @Override
        public String getColumnName(int col) {
            return columnNames[col];
        }
 /*
        @Override
        public Object getValueAt(int row, int col) {
            return data[row][col];
        }
   */     
        
        /*
         * JTable uses this method to determine the default renderer/
         * editor for each cell.  If we didn't implement this method,
         * then the last column would contain text ("true"/"false"),
         * rather than a check box.
         */
        @Override
        public Class getColumnClass(int c) {
            return getValueAt(0, c).getClass();
        }
 
        /*
         * Don't need to implement this method unless your table's
         * editable.
         */
        @Override
        public boolean isCellEditable(int row, int col) {
            //Note that the data/cell address is constant,
            //no matter where the cell appears onscreen.
            if (col == 2) {
                return false;
            } else {
                return true;
            }
        }
        
        /*
         * Don't need to implement this method unless your table's
         * data can change.
         */
        /*
        @Override
        public void setValueAt(Object value, int row, int col) {
            if (DEBUG) {
                System.out.println("Setting value at " + row + "," + col
                                   + " to " + value
                                   + " (an instance of "
                                   + value.getClass() + ")");
            }
            System.out.println("OK");
            if(col>2 ){
                value =(Object) Math.abs((double) value);  
            }
                        System.out.println("OK");

            data[row][col] = value;
            fireTableCellUpdated(row, col);
             System.out.println("OK");

            if (DEBUG) {
                System.out.println("New value of data:");
                printDebugData();
            }
        }*/
 
        private void printDebugData() {
            int numRows = getRowCount();
            int numCols = getColumnCount();
 
            for (int i=0; i < numRows; i++) {
                System.out.print("    row " + i + ":");
                for (int j=0; j < numCols; j++) {
                    System.out.print("  " + data[i][j]);
                }
                System.out.println();
            }
            System.out.println("--------------------------");
        }
    }