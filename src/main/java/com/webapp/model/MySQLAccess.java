/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.webapp.model;

/**
 *
 * @author hugo
 */

import java.sql.*;
import java.util.*;

public class MySQLAccess {
        private Connection connect = null;
        private Statement statement = null;
        private PreparedStatement preparedStatement = null;
        private ResultSet resultSet = null;
        private ResultSetMetaData resultSetMetaData = null;

        public final static int TAILLE = 4;

        private String user, passwd, table, base;
        private ArrayList<String[]> listReponses = new ArrayList<String[]>();
        private ArrayList<String> metas = new ArrayList<String>();

         public MySQLAccess(ArrayList<String> _param){

            this.user = _param.get(0);
            this.passwd = _param.get(1);
            this.base = _param.get(2);
            this.table = _param.get(3);
            

            try {
                 // This will load the MySQL driver, each DB has its own driver
                Class.forName("com.mysql.jdbc.Driver");


                // Setup the connection with the DB
                connect = DriverManager.getConnection("jdbc:mysql://10.172.1.109/"+base, user, passwd);

            } catch (SQLException ex) {
                ex.printStackTrace();
            } catch (ClassNotFoundException ex) {
                ex.printStackTrace();
            }


        }

    public void read(String order) {
		try {
			// Statements allow to issue SQL queries to the datatable
			statement = connect.createStatement();
			// Result set get the result of the SQL query
			resultSet = statement.executeQuery("SELECT * FROM "+table+" ORDER BY "+order);
            resultSetMetaData = resultSet.getMetaData();
			this.setResultSet(resultSet, resultSetMetaData);

		} catch (Exception e) {
                    System.err.println(e.toString());
		}

	}

    public boolean insert(ArrayList<String> array){
		try {
			// Statements allow to issue SQL queries to the datatable
			statement = connect.createStatement();

			// PreparedStatements can use variables and are more efficient
			preparedStatement = connect.prepareStatement("insert into "+table+" values (default, ?, ?, ?)");

			// Parameters start with 1
                        for (int i = 1; i <= array.size(); i++) {
                        preparedStatement.setString(i, array.get(i-1).toString());

                        }
			preparedStatement.executeUpdate();

		} catch (Exception e) {
                e.printStackTrace();
                    return false;
		}
        return true;
	}

    public boolean insertDesc(ArrayList<String> values) {
       try {
			// Statements allow to issue SQL queries to the datatable
			statement = connect.createStatement();

			// PreparedStatements can use variables and are more efficient
			preparedStatement = connect.prepareStatement("INSERT INTO descriptions values (default, ?)");

			// Parameters start with 1
                        for (int i = 1; i <= values.size(); i++) {
                        preparedStatement.setString(i, values.get(i-1).toString());

                        }
			preparedStatement.executeUpdate();

		} catch (Exception e) {
                e.printStackTrace();
                    return false;
		}
        return true;
    }

    public boolean modify(ArrayList<String> array, String id){
		try {
			// Statements allow to issue SQL queries to the datatable
			statement = connect.createStatement();

			// PreparedStatements can use variables and are more efficient
			preparedStatement = connect.prepareStatement("UPDATE "+table+" SET description = ?, montant = ?, date = ? WHERE id="+id);

			// Parameters start with 1
                        for (int i = 1; i <= array.size(); i++) {
                        preparedStatement.setString(i, array.get(i-1).toString());

                        }
			preparedStatement.executeUpdate();

		} catch (Exception e) {
                e.printStackTrace();
                    return false;
		}
        return true;
	}

    public boolean delete(String id){
            try {
                // Remove again the insert comment
                preparedStatement = connect.prepareStatement("delete from " + table + " where id= " + id);
                preparedStatement.executeUpdate();
            } catch (SQLException ex) {
                ex.printStackTrace();
                return false;
            }
            return true;
        }

	private void setResultSet(ResultSet resultSet, ResultSetMetaData resultSetMetaData) {
            try {
                while (resultSet.next()) {
                    String[] reponse = new String[TAILLE];
                    for(int i = 1; i <= resultSetMetaData.getColumnCount(); i++){

                        reponse[i-1] = resultSet.getObject(i).toString();
                    }
                    listReponses.add(reponse);
                }
                for (int j = 1; j <= resultSetMetaData.getColumnCount(); j++) {
                    metas.add(resultSetMetaData.getColumnName(j).toString());
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
	}

    public ArrayList<String[]> getListReponses() {
        this.read("date");
        return listReponses;
    }


    public ArrayList<String[]> getListReponsesOrder(String order) {
        this.read(order);
        return listReponses;
    }

    public ArrayList<String> getMetaDatas(){
        return metas;
    }

    public ArrayList<String[]> getAList(String _id){
        ArrayList<String[]> one = new ArrayList<String[]>();
        try {

            statement = connect.createStatement();

            resultSet = statement.executeQuery("SELECT * FROM "+table+" WHERE id="+_id);
            resultSetMetaData = resultSet.getMetaData();

            while (resultSet.next()) {
                String[] reponse = new String[TAILLE];
                for(int i = 1; i <= resultSetMetaData.getColumnCount(); i++){

                    reponse[i-1] = resultSet.getObject(i).toString();
                }
                one.add(reponse);
            }
        } catch (Exception e) {
                    e.printStackTrace();
        }

        return one;

    }

    public ArrayList<String> getDescs(){
       ArrayList<String> descs = new ArrayList<String>();
       try {
            int i = 1;
			resultSet = (ResultSet) statement.executeQuery("SELECT description FROM descriptions");
            while (resultSet.next()) {
                String tmp = resultSet.getObject("description").toString();
                if(!descs.contains(tmp)){
                    descs.add(tmp);
                }
                i++;
            }
        } catch (Exception e) {
                    e.printStackTrace();
		}
       return descs;
    }

    public String convertD(String tmp){

        return tmp.substring(0, tmp.length()-3);
    }


    public ArrayList<String> getMpMounth(){
        ArrayList<String> montForD = new ArrayList<String>();
        ArrayList<String> dates = new ArrayList<String>();
        try {
            int i = 1;
			resultSet = (ResultSet) statement.executeQuery("SELECT date FROM "+table);
            while (resultSet.next()) {
                String tmp = convertD(resultSet.getObject("date").toString());
                if(!dates.contains(tmp)){
                    dates.add(tmp);
                }
                i++;
            }

            for (int j = 0; j < dates.size(); j++) {
                String date = dates.get(j);
                double totalForM = 0;
                resultSet = (ResultSet) statement.executeQuery("SELECT montant FROM "+table+" WHERE date LIKE \'"+date+"___\'");
                resultSetMetaData = resultSet.getMetaData();

                while (resultSet.next()) {
                    for(int k = 1; k <= resultSetMetaData.getColumnCount(); k++){
                        String tmp = resultSet.getString(k);
                        totalForM += Double.valueOf(tmp);
                    }
                }
                montForD.add(date);
                montForD.add(String.valueOf(totalForM));
            }
		} catch (Exception e) {
                    e.printStackTrace();
		}

        return montForD;
    }

    public ArrayList<String> getMpDesc(){
        ArrayList<String> montForD = new ArrayList<String>();
        ArrayList<String> descs = new ArrayList<String>();
        try {
            int i = 1;
			resultSet = (ResultSet) statement.executeQuery("SELECT description FROM "+table);
            while (resultSet.next()) {
                String tmp = resultSet.getObject("description").toString();
                if(!descs.contains(tmp)){
                    descs.add(tmp);
                }
                i++;
            }

            for (int j = 0; j < descs.size(); j++) {
                String desc = descs.get(j);
                double totalForD = 0;
                resultSet = (ResultSet) statement.executeQuery("SELECT montant FROM "+table+" WHERE description LIKE \'%"+desc+"%\'");
                resultSetMetaData = resultSet.getMetaData();

                while (resultSet.next()) {
                    for(int k = 1; k <= resultSetMetaData.getColumnCount(); k++){
                        String tmp = resultSet.getString(k);
                        totalForD += Double.valueOf(tmp);
                    }
                }
                montForD.add(desc);
                montForD.add(String.valueOf(totalForD));
            }
		} catch (Exception e) {
                    e.printStackTrace();
		}

        return montForD;
    }

    public ArrayList<String[]> getMpDescPMounth(){
        String[] montForD = new String[3];
        ArrayList<String[]> array = new ArrayList<String[]>();
        ArrayList<String> descs = new ArrayList<String>();
        ArrayList<String> dates = new ArrayList<String>();

        try {
            int l = 1;
            resultSet = (ResultSet) statement.executeQuery("SELECT date FROM "+table);
            while (resultSet.next()) {
                String Odate = convertD(resultSet.getObject("date").toString());
                if(!dates.contains(Odate)){
                    dates.add(Odate);
                }
                l++;
            }
            int i = 1;
            resultSet = (ResultSet) statement.executeQuery("SELECT description FROM "+table);
            while (resultSet.next()) {
                String Odesc = resultSet.getObject("description").toString();
                if(!descs.contains(Odesc)){
                    descs.add(Odesc);
                }
                i++;
            }

            for (int m = 0; m < dates.size(); m++) {
                for (int j = 0; j < descs.size(); j++) {
                    String date = dates.get(m);
                    String desc = descs.get(j);
                    double totalForD = 0;
                    resultSet = (ResultSet) statement.executeQuery("SELECT montant FROM "+table+" WHERE date LIKE \'"+date+"___\' AND description LIKE \'%"+desc+"%\'");
                    resultSetMetaData = resultSet.getMetaData();

                    while (resultSet.next()) {
                        for(int k = 1; k <= resultSetMetaData.getColumnCount(); k++){
                            String tmp = resultSet.getString(k);
                            totalForD += Double.valueOf(tmp);
                        }
                    }
                    montForD[0] = date;
                    montForD[1] = desc;
                    montForD[2] = String.valueOf(totalForD);
                    if(!montForD[2].equals("0.0")){
                        array.add(montForD.clone());
                    }
                }

            }
        } catch (Exception e) {
                    e.printStackTrace();
        }

        return array;
    }

    public String getAvgMontant(){
        String avg = "";
        try {

			resultSet = (ResultSet) statement.executeQuery("SELECT AVG(montant) AS prix_moyen FROM "+table);
            resultSetMetaData = (ResultSetMetaData) resultSet.getMetaData();

            while (resultSet.next()) {
                avg = resultSet.getObject("prix_moyen").toString();
            }

		} catch (Exception e) {
                    System.err.println(e.toString());
		}

        return avg;
    }

	public void close() {
		try {
			if (resultSet != null) {
				resultSet.close();
			}

			if (statement != null) {
				statement.close();
			}

			if (connect != null) {
				connect.close();
			}
		} catch (Exception e) {
            e.printStackTrace();
		}
	}
}

