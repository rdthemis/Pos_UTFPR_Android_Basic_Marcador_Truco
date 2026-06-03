package br.edu.posutfpr.marcadortruco

import android.os.Bundle
import android.text.method.ScrollingMovementMethod
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import br.edu.posutfpr.marcadortruco.databinding.ActivityHistoricoBinding
import java.io.File

class Historico : AppCompatActivity() {

    private lateinit var binding: ActivityHistoricoBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHistoricoBinding.inflate(layoutInflater)

        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        binding.textView.movementMethod = ScrollingMovementMethod()

        binding.button.setOnClickListener { fecharVoltar() }

        val caminho = intent.getStringExtra("historico")

        if (caminho == null) {
            Toast.makeText(this, "Sem Registro no jogo", Toast.LENGTH_SHORT).show()
            finish()
            return
        }

        val arquivo = File(caminho)
        if (!arquivo.exists()) {
            finish()
            return
        }

        carregarHistorico(arquivo)
    }

    private fun fecharVoltar() {
        finish()
    }

    private fun carregarHistorico(arquivo: File) {
        Thread {
            val linhas = arquivo.readLines()
            runOnUiThread {
                binding.textView.text = linhas.joinToString("\n")
            }
        }.start()
    }
}