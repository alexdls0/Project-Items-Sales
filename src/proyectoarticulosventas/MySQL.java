package proyectoarticulosventas;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class MySQL {

    static private ResultSetMetaData meta;
    static private ResultSet rs;
    static private Connection conn = null;
    static private Statement stmt;

    public void conecta(String DB_URL, String USER, String PASS) {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection(DB_URL, USER, PASS);
        } catch (SQLException se) {
            se.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void ejecutaConsulta(String cons) {
        try {
            Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
            String sql = cons;
            rs = stmt.executeQuery(sql);
            meta = rs.getMetaData();

        } catch (SQLException se) {
            se.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void desconecta() {
        try {
            conn.close();
        } catch (SQLException se) {
            se.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException se) {
                se.printStackTrace();
            }
        }

        System.out.println("Desconectando");
    }

    public ResultSetMetaData getMeta() {
        return meta;
    }

    public ResultSet getRs() {
        return rs;
    }

    public String[] daCabeceras(ResultSet rs) throws SQLException {
        String[] cabeceras = null;

        try {
            String resultado = "";

            int nCol = meta.getColumnCount();
            String[] nombreColumnas = new String[nCol];
            String[] tipoColumnas = new String[nCol];
            Object[] valColumnas = new Object[nCol];
            for (int i = 0; i < nCol; i++) {
                nombreColumnas[i] = meta.getColumnName(i + 1);
                tipoColumnas[i] = meta.getColumnTypeName(i + 1);
                resultado += nombreColumnas[i] + "[" + tipoColumnas[i] + "]\n";
            }
            resultado += "\n";
            String[] v = resultado.split("\n");
            cabeceras = v;

            resultado = "";
        } catch (Exception e) {
            System.err.println("Error");
        }

        return cabeceras;
    }

    public Object[][] daContenido(ResultSet rs) throws SQLException {

        Object[][] contenido = null;

        try {
            String resultado = "";

            int nCol = meta.getColumnCount();
            String[] nombreColumnas = new String[nCol];
            String[] tipoColumnas = new String[nCol];
            Object[] valColumnas = new Object[nCol];

            ArrayList<String[]> listado = new ArrayList<>();
            rs.beforeFirst();
            while (rs.next()) {
                for (int i = 0; i < nCol; i++) {
                    valColumnas[i] = rs.getObject(i + 1);
                    resultado += valColumnas[i] + " ";

                }
                String[] v2 = resultado.split(" ");
                listado.add(v2);

                resultado = "";
            }

            contenido = new Object[listado.size()][listado.get(0).length];
            for (int i = 0; i < listado.size(); i++) {
                for (int j = 0; j < listado.get(i).length; j++) {
                    contenido[i][j] = listado.get(i)[j].toString();
                }
            }

        } catch (Exception e) {
            System.err.println("Error");
        }

        return contenido;
    }

    public void update(String consulta) {
        try {
            stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
            stmt.executeUpdate(consulta);
        } catch (SQLException se) {
            se.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void inserta(String consulta) {
        try {
            stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
            stmt.executeUpdate(consulta);
        } catch (SQLException se) {
            System.err.println("Error en la sentencia");;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void insertaConEstado(String consulta, StringBuilder estado) {
        try {
            stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
            stmt.executeUpdate(consulta);
        } catch (SQLException se) {
            System.err.println("Error en la sentencia");
            estado.replace(0, estado.length(), "Error");
        } catch (Exception e) {
            System.err.println("Error");
            estado.replace(0, estado.length(), "Error");
        }
    }
}
