package com.example.pm1e3;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private EditText editTextIdOrden, editTextDescripcion, editTextPeriodista, editTextFecha;
    private Button buttonGuardar, buttonEliminar, buttonModificar;
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
        buttonModificar = findViewById(R.id.buttonModificar);

        buttonGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                guardarEntrevista();
            }
        });

        buttonEliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                eliminarEntrevista();
            }
        });

        buttonModificar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                modificarEntrevista();
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
                            // Éxito al guardar

                        } else {
                            // Error al guardar
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
                            // Éxito al eliminar

                        } else {
                            // Error al eliminar
                        }
                    }
                });
    }

    private void modificarEntrevista() {
        // Obtener el ID de la entrevista a modificar
        final String idOrden = editTextIdOrden.getText().toString();

        // Obtener los nuevos valores de los EditText
        final String nuevaDescripcion = editTextDescripcion.getText().toString();
        final String nuevoPeriodista = editTextPeriodista.getText().toString();
        final String nuevaFecha = editTextFecha.getText().toString();

        // Obtener la referencia al documento en Cloud Firestore
        DocumentReference entrevistaRef = db.collection("entrevistas").document(idOrden);

        // Consultar el documento actual para asegurarnos de que exista
        entrevistaRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        // El documento existe, realizar la actualización
                        Map<String, Object> nuevosDatos = new HashMap<>();
                        nuevosDatos.put("descripcion", nuevaDescripcion);
                        nuevosDatos.put("periodista", nuevoPeriodista);
                        nuevosDatos.put("fecha", nuevaFecha);

                        // Actualizar el documento en Cloud Firestore
                        entrevistaRef.update(nuevosDatos)
                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if (task.isSuccessful()) {
                                            // Éxito al modificar
                                        } else {
                                            // Error al modificar
                                        }
                                    }
                                });
                    } else {

                    }
                } else {

                }
            }
        });
    }
}

