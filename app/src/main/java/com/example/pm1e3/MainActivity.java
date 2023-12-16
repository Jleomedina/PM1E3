package com.example.pm1e3;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private EditText editTextIdOrden, editTextDescripcion, editTextPeriodista, editTextFecha;
    private Button buttonGuardar, buttonEliminar;
    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Inicializar Firebase Firestore
        db = FirebaseFirestore.getInstance();

        editTextIdOrden = findViewById(R.id.editTextIdOrden);
        editTextDescripcion = findViewById(R.id.editTextDescripcion);
        editTextPeriodista = findViewById(R.id.editTextPeriodista);
        editTextFecha = findViewById(R.id.editTextFecha);
        buttonGuardar = findViewById(R.id.buttonGuardar);
        buttonEliminar = findViewById(R.id.buttonEliminar);

        buttonGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Lógica para guardar la información en Cloud Firestore
                guardarEntrevista();
            }
        });

        buttonEliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Lógica para eliminar la información en Cloud Firestore
                eliminarEntrevista();
            }
        });
    }

    private void guardarEntrevista() {
        // Obtener los valores de los EditText
        String idOrden = editTextIdOrden.getText().toString();
        String descripcion = editTextDescripcion.getText().toString();
        String periodista = editTextPeriodista.getText().toString();
        String fecha = editTextFecha.getText().toString();

        // Crear un nuevo mapa con los datos a guardar
        Map<String, Object> entrevista = new HashMap<>();
        entrevista.put("idOrden", idOrden);
        entrevista.put("descripcion", descripcion);
        entrevista.put("periodista", periodista);
        entrevista.put("fecha", fecha);

        // Guardar los datos en Cloud Firestore
        db.collection("entrevistas")
                .document(idOrden)
                .set(entrevista)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {

                        } else {

                        }
                    }
                });
    }

    private void eliminarEntrevista() {
        // Obtener el ID de la entrevista a eliminar
        String idOrden = editTextIdOrden.getText().toString();

        // Eliminar la entrevista de Cloud Firestore
        db.collection("entrevistas")
                .document(idOrden)
                .delete()
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {

                        } else {
                            
                        }
                    }
                });
    }
}
