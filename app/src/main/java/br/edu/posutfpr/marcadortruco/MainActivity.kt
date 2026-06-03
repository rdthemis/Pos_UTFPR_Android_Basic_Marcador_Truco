package br.edu.posutfpr.marcadortruco

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import br.edu.posutfpr.marcadortruco.databinding.ActivityMainBinding
import java.io.File

class MainActivity : AppCompatActivity(){

    private lateinit var binding: ActivityMainBinding

    private var tentosJogador1 = 0
    private var tentosJogador2 = 0
    private var partidasGanhasJogador1 = 0
    private var partidasGanhasJogador2 = 0
    private var numeroPartidas = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)

        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        binding.tvNumeroPartidas.text = numeroPartidas.toString()
        binding.btNomeJogadores.setOnClickListener { telaInformarJogadores() }
        binding.btHistorico.setOnClickListener { telaHistorico() }
        binding.btResetJogo.setOnClickListener { resetJogo() }
        binding.btTentoMaisUmJog1.setOnClickListener { addTentoJogador1(1, 1) }
        binding.btTentoMaisTresJog1.setOnClickListener { addTentoJogador1(1, 3) }
        binding.btTentoMaisSeisJog1.setOnClickListener { addTentoJogador1(1, 6) }
        binding.btTentoMaisNoveJog1.setOnClickListener { addTentoJogador1(1, 9) }
        binding.btTentoMaisDozeJog1.setOnClickListener { addTentoJogador1(1, 12) }
        binding.btTentoMenosUmJog1.setOnClickListener { addTentoJogador1(1, -1) }

        binding.btTentoMaisUmJog2.setOnClickListener { addTentoJogador1(2, 1) }
        binding.btTentoMaisTresJog2.setOnClickListener { addTentoJogador1(2, 3) }
        binding.btTentoMaisSeisJog2.setOnClickListener { addTentoJogador1(2, 6) }
        binding.btTentoMaisNoveJog2.setOnClickListener { addTentoJogador1(2, 9) }
        binding.btTentoMaisDozeJog2.setOnClickListener { addTentoJogador1(2, 12) }
        binding.btTentoMenosUmJog2.setOnClickListener { addTentoJogador1(2, -1) }

    }

    private fun resetJogo() {
        tentosJogador1 = 0
        tentosJogador2 = 0
        numeroPartidas = 0
        atualizarPlacar(0)
        registraHistorico("--- Jogo resetado ---")
        Toast.makeText(this, "Jogo reiniciado!", Toast.LENGTH_SHORT).show()
    }

    private fun addTentoJogador1(jogador: Int, tento: Int) {

        val nomeJogador: String

        if(jogador == 1){
            tentosJogador1 += tento
            if(verificaPontuacaoNegativa(tentosJogador1)) {
                binding.tvPlacarJogador1.text = "0"
                tentosJogador1 = 0
                return
            }
            binding.tvPlacarJogador1.text = tentosJogador1.toString()
            nomeJogador = binding.tvJogador1.text.toString()
        }else{
            tentosJogador2 += tento
            if(verificaPontuacaoNegativa(tentosJogador2)) {
                binding.tvPlacarJogador2.text = "0"
                tentosJogador2 = 0
                return
            }
            binding.tvPlacarJogador2.text = tentosJogador2.toString()
            nomeJogador = binding.tvJogador2.text.toString()
        }

        verificaVencedor()

        registraHistorico("$nomeJogador: $tento → Placar:$tentosJogador1  x $tentosJogador2")
    }

    private fun verificaVencedor() {
        if(tentosJogador1 >= 12){
            val nomeJogador = binding.tvJogador1.text.toString()
            reiniciarJogo(nomeJogador)
            partidasGanhasJogador1++
            atualizarPlacar(1)
            return
        }
        if(tentosJogador2 >= 12){
            val nomeJogador = binding.tvJogador2.text.toString()
            reiniciarJogo(nomeJogador)
            partidasGanhasJogador2++
            atualizarPlacar(2)
            return
        }
    }

    private fun reiniciarJogo(nomeJogador: String) {
        registraHistorico("$nomeJogador venceu a partida $numeroPartidas! de $tentosJogador1 x $tentosJogador2")
        Toast.makeText(this, "$nomeJogador  venceu!", Toast.LENGTH_LONG).show()
        numeroPartidas++
        binding.tvNumeroPartidas.text = numeroPartidas.toString()
        tentosJogador1 = 0
        binding.tvPlacarJogador1.text = tentosJogador1.toString()
        tentosJogador2 = 0
        binding.tvPlacarJogador2.text = tentosJogador2.toString()
    }

    private fun atualizarPlacar(jogador: Int) {
        if(jogador == 1){
            when(partidasGanhasJogador1) {
                1 -> binding.rbPartidaGanhaJogador11.isChecked = true
                2 -> binding.rbPartidaGanhaJogador12.isChecked = true
                3 -> binding.rbPartidaGanhaJogador13.isChecked = true
                else -> {
                    binding.rbPartidaGanhaJogador11.isChecked = false
                    binding.rbPartidaGanhaJogador12.isChecked = false
                    binding.rbPartidaGanhaJogador13.isChecked = false
                }
            }
            return
        }
        if(jogador == 2){
            when(partidasGanhasJogador2) {
                1 -> binding.rbPartidaGanhaJogador21.isChecked = true
                2 -> binding.rbPartidaGanhaJogador22.isChecked = true
                3 -> binding.rbPartidaGanhaJogador23.isChecked = true
                else -> {
                    binding.rbPartidaGanhaJogador11.isChecked = false
                    binding.rbPartidaGanhaJogador12.isChecked = false
                    binding.rbPartidaGanhaJogador13.isChecked = false
                }
            }
            return
        }
    }

    private fun verificaPontuacaoNegativa(tentos: Int): Boolean {
        return tentos < 0
    }

    private fun registraHistorico(historico: String){
        val linha = "$historico\n"
        openFileOutput("historico.txt", MODE_APPEND).use{
            output -> output.write(linha.toByteArray())
        }
    }

    private fun telaHistorico(){
        val arquivo = File(filesDir, "historico.txt")
        if (!arquivo.exists()) {
            Toast.makeText(this, "Sem registro", Toast.LENGTH_LONG).show()
            return
        }

        val intent = Intent(this, Historico::class.java)
        intent.putExtra("historico", arquivo.absolutePath)
        startActivity(intent)

    }

    private fun telaInformarJogadores() {
        val intent = Intent(this, InformarJogadores::class.java)
        getResult.launch(intent)
    }

    private val getResult = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult() ) { retorno ->

        if ( retorno.resultCode == RESULT_OK ) {
            val jogador1 = retorno.data?.getStringExtra( "jogador1")
            binding.tvJogador1.text = jogador1.toString()
            registraHistorico("Informado nome Jogador 1: $jogador1")

            val jogador2 = retorno.data?.getStringExtra( "jogador2")
            binding.tvJogador2.text = jogador2.toString()
            registraHistorico("Informado nome Jogador 2: $jogador2")
        }
    }
}