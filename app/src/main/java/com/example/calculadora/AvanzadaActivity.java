package com.example.calculadora;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class AvanzadaActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView tvResultado;
    private Calculadora calculadora;

    private Double operando1;
    private String operador;
    private boolean empezandoNumero = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_avanzada);

        calculadora = new Calculadora();
        tvResultado = findViewById(R.id.tvResultado);

        int[] ids = {R.id.btn0, R.id.btn1, R.id.btn2, R.id.btn3, R.id.btn4,
                R.id.btn5, R.id.btn6, R.id.btn7, R.id.btn8, R.id.btn9,
                R.id.btnPunto, R.id.btnBorrar,
                R.id.btnSuma, R.id.btnResta, R.id.btnMult, R.id.btnDiv, R.id.btnIgual};

        for (int id : ids) {
            findViewById(id).setOnClickListener(this);
        }
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();

        // Borrar: reinicia todo
        if (id == R.id.btnBorrar) {
            tvResultado.setText("0");
            operando1 = null;
            operador = null;
            empezandoNumero = true;
            return;
        }

        // Punto decimal: solo si el número actual no tiene ya uno
        if (id == R.id.btnPunto) {
            String actual = tvResultado.getText().toString();
            if (!actual.contains(".")) {
                tvResultado.setText(actual + ".");
            }
            empezandoNumero = false;
            return;
        }

        // Operadores: solo si todavia no se ha pulsado ninguno
        if (id == R.id.btnSuma || id == R.id.btnResta || id == R.id.btnMult || id == R.id.btnDiv) {
            if (operador == null) {
                operando1 = Double.parseDouble(tvResultado.getText().toString());

                if (id == R.id.btnSuma) {
                    operador = "+";
                } else if (id == R.id.btnResta) {
                    operador = "-";
                } else if (id == R.id.btnMult) {
                    operador = "*";
                } else {
                    operador = "/";
                }

                empezandoNumero = true;
            }
            return;
        }

        // Igual: solo si hay un primer numero y un operador guardados
        if (id == R.id.btnIgual) {
            if (operando1 != null && operador != null) {
                double operando2 = Double.parseDouble(tvResultado.getText().toString());

                calculadora.setOper1(operando1);
                calculadora.setOper2(operando2);
                calculadora.setOperacion(Calculadora.OPERACION.fromSymbol(operador));
                double resultado = calculadora.opera();

                tvResultado.setText(String.valueOf(resultado));

                operando1 = null;
                operador = null;
                empezandoNumero = true;
            }
            return;
        }

        String digito;

        if (id == R.id.btn0) {
            digito = "0";
        } else if (id == R.id.btn1) {
            digito = "1";
        } else if (id == R.id.btn2) {
            digito = "2";
        } else if (id == R.id.btn3) {
            digito = "3";
        } else if (id == R.id.btn4) {
            digito = "4";
        } else if (id == R.id.btn5) {
            digito = "5";
        } else if (id == R.id.btn6) {
            digito = "6";
        } else if (id == R.id.btn7) {
            digito = "7";
        } else if (id == R.id.btn8) {
            digito = "8";
        } else {
            digito = "9";
        }

        if (empezandoNumero) {
            tvResultado.setText(digito);
            empezandoNumero = false;
        } else {
            String actual = tvResultado.getText().toString();
            tvResultado.setText(actual + digito);
        }
    }
}