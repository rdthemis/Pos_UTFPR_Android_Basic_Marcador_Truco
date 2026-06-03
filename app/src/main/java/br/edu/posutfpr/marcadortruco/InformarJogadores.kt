package br.edu.posutfpr.marcadortruco

import android.content.Intent
import android.os.Bundle
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import br.edu.posutfpr.marcadortruco.databinding.ActivityInformarJogadoresBinding

class InformarJogadores : AppCompatActivity() {
    private lateinit var binding: ActivityInformarJogadoresBinding

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        binding = ActivityInformarJogadoresBinding.inflate(layoutInflater)

        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val jogador1 = binding.etNomeJogador1
        val jogador2 = binding.etNomeJogador2

        binding.btConfirmar.setOnClickListener {
            if(verificarDigitacao(jogador1, jogador2)){
                val intent = Intent()
                intent.putExtra("jogador1", jogador1.text.toString())
                intent.putExtra("jogador2", jogador2.text.toString())
                setResult(RESULT_OK, intent)
                finish()
            }
        }
    }

    private fun verificarDigitacao(jogador1: EditText, jogador2: EditText): Boolean {
        if (jogador1.text.toString().isEmpty()){
            Toast.makeText( this, "Nome do jogador 1 deve ser informado", Toast.LENGTH_LONG ).show()
            binding.etNomeJogador1.requestFocus()
            return false
        }

        if (jogador2.text.toString().isEmpty()){
            Toast.makeText( this, "Nome do jogador 2 deve ser informado", Toast.LENGTH_LONG ).show()
            binding.etNomeJogador2.requestFocus()
            return false
        }
        return true
    }
}