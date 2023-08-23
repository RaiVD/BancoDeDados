package com.example.boncodedados;
import androidx.appcompat.app.AppCompatActivity;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private UserDbHelper dbHelper;
    private TextView resultTextView;
    private EditText emailEditText;
    private EditText senhaEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dbHelper = new UserDbHelper(this);
        resultTextView = findViewById(R.id.resultTextView);
        emailEditText = findViewById(R.id.editTextTextEmailAddress);
        senhaEditText = findViewById(R.id.editTextNumberPassword);

        Button insertButton = findViewById(R.id.insertButton);
        insertButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = emailEditText.getText().toString();
                String senha = senhaEditText.getText().toString();
                inserirUsuario(email, senha);
            }
        });

        Button readButton = findViewById(R.id.readButton);
        readButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                List<String> usuarios = lerUsuarios();
                StringBuilder resultado = new StringBuilder();
                for (String usuario : usuarios) {
                    resultado.append(usuario).append("\n");
                }
                resultTextView.setText(resultado.toString());
            }
        });

        Button clearButton = findViewById(R.id.clearButton);
        clearButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                limparBancoDados();
                resultTextView.setText("Banco de dados limpo.");
            }
        });
    }

    private void inserirUsuario(String email, String senha) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues valores = new ContentValues();
        valores.put(UserContract.UserEntry.COLUMN_NAME_EMAIL, email);
        valores.put(UserContract.UserEntry.COLUMN_NAME_PASSWORD, senha);

        long newRowId = db.insert(UserContract.UserEntry.TABLE_NAME, null, valores);
    }

    private List<String> lerUsuarios() {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        List<String> usuarios = new ArrayList<>();

        String[] projecao = {
                UserContract.UserEntry.COLUMN_NAME_EMAIL,
                UserContract.UserEntry.COLUMN_NAME_PASSWORD
        };

        Cursor cursor = db.query(
                UserContract.UserEntry.TABLE_NAME,
                projecao,
                null,
                null,
                null,
                null,
                null
        );

        while (cursor.moveToNext()) {
            String email = cursor.getString(cursor.getColumnIndexOrThrow(UserContract.UserEntry.COLUMN_NAME_EMAIL));
            String senha = cursor.getString(cursor.getColumnIndexOrThrow(UserContract.UserEntry.COLUMN_NAME_PASSWORD));
            usuarios.add("Email: " + email + ", Senha: " + senha);
        }

        cursor.close();

        return usuarios;
    }
    private void limparBancoDados() {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.delete(UserContract.UserEntry.TABLE_NAME, null, null);
    }
}

