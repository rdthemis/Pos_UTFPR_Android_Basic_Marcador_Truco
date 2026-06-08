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
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private var tentosJogador1 = 0
    private var tentosJogador2 = 0
    private var partidasGanhasJogador1 = 0
    private var partidasGanhasJogador2 = 0
    private var numeroPartidas = 1
    private var jogador1 = "Jogador 1"
    private var jogador2 = "Jogador 2"
    private var dataHora = ""

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

        val calendar = Calendar.getInstance()
        val formato = SimpleDateFormat("dd/MM/yyyy HH:mm:ss", Locale.getDefault())
        dataHora = formato.format(calendar.time)

        jogador1 = intent.getStringExtra("jogador1") ?: "Jogador 1"
        jogador2 = intent.getStringExtra("jogador2") ?: "Jogador 2"
        binding.tvJogador1.text = jogador1
        binding.tvJogador2.text = jogador2
        binding.tvPlacarJogador1.text = tentosJogador1.toString()
        binding.tvPlacarJogador2.text = tentosJogador2.toString()
        binding.tvNumeroPartidas.text = numeroPartidas.toString()

        binding.btNomeJogadores.setOnClickListener { telaInformarJogadores() }
        binding.btHistorico.setOnClickListener { telaHistorico() }
        binding.btResetJogo.setOnClickListener { resetJogo() }

        binding.btTentoMaisUmJog1.setOnClickListener   { adicionarTento(1, 1)  }
        binding.btTentoMaisTresJog1.setOnClickListener { adicionarTento(1, 3)  }
        binding.btTentoMaisSeisJog1.setOnClickListener { adicionarTento(1, 6)  }
        binding.btTentoMaisNoveJog1.setOnClickListener { adicionarTento(1, 9)  }
        binding.btTentoMaisDozeJog1.setOnClickListener { adicionarTento(1, 12) }
        binding.btTentoMenosUmJog1.setOnClickListener  { adicionarTento(1, -1) }

        binding.btTentoMaisUmJog2.setOnClickListener   { adicionarTento(2, 1)  }
        binding.btTentoMaisTresJog2.setOnClickListener { adicionarTento(2, 3)  }
        binding.btTentoMaisSeisJog2.setOnClickListener { adicionarTento(2, 6)  }
        binding.btTentoMaisNoveJog2.setOnClickListener { adicionarTento(2, 9)  }
        binding.btTentoMaisDozeJog2.setOnClickListener { adicionarTento(2, 12) }
        binding.btTentoMenosUmJog2.setOnClickListener  { adicionarTento(2, -1) }
    }

    override fun onDestroy() {
        super.onDestroy()
        val arquivo = File(filesDir, "historico.txt")
        if (arquivo.exists()) {
            arquivo.writeText("")
        }
    }

    private fun adicionarTento(jogador: Int, tento: Int) {
        if (jogador == 1) {
            tentosJogador1 += tento
            if (tentosJogador1 < 0) tentosJogador1 = 0
            binding.tvPlacarJogador1.text = tentosJogador1.toString()
            registraJogada(jogador1, tento)
        } else {
            tentosJogador2 += tento
            if (tentosJogador2 < 0) tentosJogador2 = 0
            binding.tvPlacarJogador2.text = tentosJogador2.toString()
            registraJogada(jogador2, tento)
        }

        val vencedor = verificaVencedor()
        if (vencedor.isNotEmpty()) {
            registraHistorico("$dataHora - $vencedor venceu a partida $numeroPartidas! $jogador1: $tentosJogador1 x $tentosJogador2: $jogador2")
            Toast.makeText(this, "$vencedor venceu a partida $numeroPartidas!", Toast.LENGTH_LONG).show()

            if (vencedor == jogador1) {
                partidasGanhasJogador1++
                atualizarPlacar(1)
            } else {
                partidasGanhasJogador2++
                atualizarPlacar(2)
            }
            numeroPartidas++
            binding.tvNumeroPartidas.text = numeroPartidas.toString()
            tentosJogador1 = 0
            tentosJogador2 = 0
            binding.tvPlacarJogador1.text = "0"
            binding.tvPlacarJogador2.text = "0"
        }
    }

    private fun registraJogada(jogador: String, tento: Int) {
        if(tento < 0) registraHistorico("$dataHora - $jogador ajusta placar tento registrado $tento - Placar $jogador1 $tentosJogador1 X $tentosJogador2 $jogador2")
        if(tento > 0) registraHistorico("$dataHora - $jogador vence a mão $tento tento(s), Placar: $jogador1 $tentosJogador1 X $tentosJogador2 $jogador2")
    }

    private fun verificaVencedor(): String {
        if (tentosJogador1 >= 12) return jogador1
        if (tentosJogador2 >= 12) return jogador2
        return ""
    }
    private fun atualizarPlacar(jogador: Int) {
        when (jogador) {
            1 -> {
                when (partidasGanhasJogador1) {
                    1 -> binding.rbPartidaGanhaJogador11.isChecked = true
                    2 -> binding.rbPartidaGanhaJogador12.isChecked = true
                    3 -> binding.rbPartidaGanhaJogador13.isChecked = true
                }
            }
            2 -> {
                when (partidasGanhasJogador2) {
                    1 -> binding.rbPartidaGanhaJogador21.isChecked = true
                    2 -> binding.rbPartidaGanhaJogador22.isChecked = true
                    3 -> binding.rbPartidaGanhaJogador23.isChecked = true
                }
            }
            else -> {
                binding.rbPartidaGanhaJogador11.isChecked = false
                binding.rbPartidaGanhaJogador12.isChecked = false
                binding.rbPartidaGanhaJogador13.isChecked = false
                binding.rbPartidaGanhaJogador21.isChecked = false
                binding.rbPartidaGanhaJogador22.isChecked = false
                binding.rbPartidaGanhaJogador23.isChecked = false
            }
        }
    }

    private fun resetJogo() {
        tentosJogador1 = 0
        tentosJogador2 = 0
        partidasGanhasJogador1 = 0
        partidasGanhasJogador2 = 0
        numeroPartidas = 1
        binding.tvNumeroPartidas.text = numeroPartidas.toString()
        binding.tvPlacarJogador1.text = "0"
        binding.tvPlacarJogador2.text = "0"
        atualizarPlacar(0)
        registraHistorico("$dataHora --- Jogo resetado ---")
        Toast.makeText(this, "Jogo reiniciado!", Toast.LENGTH_SHORT).show()
    }

    private fun registraHistorico(historico: String) {
        val linha = "$historico\n"
        openFileOutput("historico.txt", MODE_APPEND).use { output ->
            output.write(linha.toByteArray())
        }
    }

    private fun telaHistorico() {
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
        ActivityResultContracts.StartActivityForResult()
    ) { retorno ->
        if (retorno.resultCode == RESULT_OK) {
            jogador1 = retorno.data?.getStringExtra("jogador1") ?: "Jogador 1"
            jogador2 = retorno.data?.getStringExtra("jogador2") ?: "Jogador 2"
            binding.tvJogador1.text = jogador1
            binding.tvJogador2.text = jogador2
            registraHistorico("Jogadores: $jogador1 x $jogador2")
        }
    }
}