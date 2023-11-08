package com.example.apptest1;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;
import android.os.AsyncTask;
import android.widget.Toast;
import org.firebirdsql.jdbc.FBDriver;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button MyBtn = findViewById(R.id.StartButton);
        MyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatabaseTask().execute();
            }
        });

    }
    private class DatabaseTask extends AsyncTask<Void, Void, String> {
        protected String doInBackground(Void... params) {
            String dbUrl = "jdbc:firebirdsql://192.168.0.11:3050/D:/Microsip datos/PAPELERIA CORIBA CORNEJO.fdb";
            String username = "SYSDBA";
            String password = "C0r1b423";
            Properties props = new Properties();

            props.setProperty("user", username);
            props.setProperty("password", password);
            props.setProperty("encoding", "UTF8");

            try {
                // Cargar el controlador JDBC
                Class.forName("org.firebirdsql.jdbc.FBDriver");
            } catch (ClassNotFoundException e) {
                return "Error al cargar el controlador JDBC: " + e.getMessage();
            }

            Connection connection = null;
            try {
                // Intentar establecer la conexión
                connection = DriverManager.getConnection(dbUrl, props);
                if (connection != null) {
                    // La conexión se estableció con éxito
                    // Realizar operaciones en la base de datos aquí

                    // Realiza aquí tu operación de red, por ejemplo, una solicitud HTTP
                    String result = performNetworkRequest();
                    return "Conexión exitosa";
                } else {
                    return "No se pudo establecer la conexión";
                }
            } catch (SQLException e) {
                return "Error en la conexión: " + e.getMessage();
            } catch (Exception e) {
                Log.e("Excepción", "Error inesperado", e);
                StringWriter sw = new StringWriter();
                e.printStackTrace(new PrintWriter(sw));
                String exceptionAsString = sw.toString();
                return "Error inesperado: " + exceptionAsString;
            } finally {
                if (connection != null) {
                    try {
                        connection.close();
                    } catch (SQLException e) {
                        return "Error al cerrar la conexión: " + e.getMessage();
                    }
                }
        }
            }

        protected void onPostExecute(String result) {
            // Actualiza la interfaz de usuario con el resultado
            runOnUiThread(new Runnable() {
                public void run() {
                    Toast.makeText(MainActivity.this, result, Toast.LENGTH_SHORT).show();
                }
            });

            // Actualiza la interfaz de usuario con el resultado si es necesario
            TextView txt = findViewById(R.id.Texto);
            txt.setText(result);
        }
    }

    // Método para realizar la operación de red (solicitud HTTP, etc.)
    private String performNetworkRequest() {
        // Realiza la operación de red aquí y devuelve el resultado
        return "Resultado de la operación de red";
    }

}