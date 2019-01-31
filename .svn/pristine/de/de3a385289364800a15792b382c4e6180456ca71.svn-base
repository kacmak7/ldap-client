package org.standalone.db.export;

import java.io.FileOutputStream;
import java.sql.Connection;
import java.sql.DriverManager;

import org.dbunit.database.DatabaseConnection;
import org.dbunit.database.IDatabaseConnection;
import org.dbunit.database.QueryDataSet;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSet;

public class ExporterDb
{
	public static void main(String[] args) throws Exception
	{
		// database connection
		Class<?> driverClass = Class.forName("org.postgresql.Driver");
		Connection jdbcConnection = DriverManager.getConnection("jdbc:postgresql://localhost/parafia", "postgres", "postgres");
		jdbcConnection.setSchema("public");
		IDatabaseConnection connection = new DatabaseConnection(jdbcConnection);
		connection.getConfig().setProperty("http://www.dbunit.org/features/qualifiedTableNames", true);
		
		// full database export
        IDataSet fullDataSet = connection.createDataSet();
        FlatXmlDataSet.write(fullDataSet, new FileOutputStream("C:/DATA/projects_data/database.xml"));
	}
}
