package br.edu.posutfpr.marcadortruco

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import br.edu.posutfpr.marcadortruco.databinding.ActivitySplashBinding

class SplashActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySplashBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Botão: jogadores querem inserir os nomes
        binding.btInformarNomes.setOnClickListener {
            val intent = Intent(this, InformarJogadores::class.java)
            getResult.launch(intent)
        }

        // Botão: continuar com Jogador 1 e Jogador 2
        binding.btContinuar.setOnClickListener {
            irParaMain("Jogador 1", "Jogador 2")
        }
    }

    // Recebe os nomes da tela InformarJogadores
    private val getResult = registerForActivityResult(
        androidx.activity.result.contract.ActivityResultContracts.StartActivityForResult()
    ) { retorno ->
        if (retorno.resultCode == RESULT_OK) {
            val jogador1 = retorno.data?.getStringExtra("jogador1") ?: "Jogador 1"
            val jogador2 = retorno.data?.getStringExtra("jogador2") ?: "Jogador 2"
            irParaMain(jogador1, jogador2)
        }
        // Se voltou sem confirmar, fica na splash
    }

    // Inicia o jogo passando os nomes para a MainActivity
    private fun irParaMain(jogador1: String, jogador2: String) {
        val intent = Intent(this, MainActivity::class.java)
        intent.putExtra("jogador1", jogador1)
        intent.putExtra("jogador2", jogador2)
        startActivity(intent)
        finish() // fecha a splash para não voltar pra ela
    }
}