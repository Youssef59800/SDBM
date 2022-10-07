module fr.fs.sdbm {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires com.microsoft.sqlserver.jdbc;
    requires java.naming;
    requires org.controlsfx.controls;
    requires kernel;
    requires io;
    requires layout;



    opens fr.fs.sdbm to javafx.fxml;
    exports fr.fs.sdbm;
}