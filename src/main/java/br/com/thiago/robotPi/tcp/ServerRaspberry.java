package br.com.thiago.robotPi.tcp;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.thiago.robotPi.model.Estacao;
import br.com.thiago.robotPi.model.Raspberry;
import br.com.thiago.robotPi.service.ComandoService;
import br.com.thiago.robotPi.service.EstacaoService;
import br.com.thiago.robotPi.service.RaspberryService;

@Service
public class ServerRaspberry {
	private List<Socket> clientes = new ArrayList<Socket>();
	private RaspberryService raspberryService;
	private EstacaoService estacaoService;
	private ComandoService comandoService;
	
	@Autowired
	public ServerRaspberry(RaspberryService raspberryService, EstacaoService estacaoService, ComandoService comandoService) {
		this.raspberryService = raspberryService;
		this.estacaoService = estacaoService;
		this.comandoService = comandoService;
	}
	
	@PostConstruct
    public void init() {
        comandoService.setServer(this);
    }
	
	@Async
	public void startServer() {
		try {
			for(Socket cliente : clientes){ 
				cliente.close(); //Fecha conexão com qualquer cliente na lista
			}
			clientes.clear(); //Limpa lista
			
			ServerSocket serverSocket = new ServerSocket(9000, 30);//Porta 9000
			System.out.println("Escutando na porta "+serverSocket.getLocalPort());
			atualizaListaClientes();
			
			while(true) {		
				Socket cliente = serverSocket.accept();
				System.out.println("Conexão estabelecida com "+cliente.getInetAddress());
				 
				clientes.add(cliente);
				escrita(cliente, "1");//Comando que autoriza o envio de mensagem por parte cliente
				leitura(cliente);
			}	
		} catch (IOException e) {
			e.printStackTrace();
		} 		
	}
	
	@Async
	public void conexaoCliente(String endereco, String jsonComando) {
		for(Socket cliente : clientes){
			if(cliente.getInetAddress().getHostAddress() == endereco) {
				escrita(cliente, jsonComando);
			}
		}
		//escolher um client		
	}
	
	@Async
	public void atualizaListaClientes() {
		for(Socket cliente : clientes) {
			escrita(cliente, "testeConexao");
		}
	}
	
	
	@Async
	private void leitura(Socket cliente) {
		do {
			try {
				InputStream inStream = cliente.getInputStream();
				BufferedReader entrada = new BufferedReader(new InputStreamReader(inStream));
				
				executaRequisicaoRaspberry(entrada.readLine(), cliente);
			} catch (IOException e) {
				Raspberry raspberry = raspberryService.findByAddress(cliente.getInetAddress());
				raspberry.setEndereco(null);
				raspberryService.atualizaEnvia(raspberry);
				clientes.remove(cliente);
				e.printStackTrace();
			}		
		} while(clientes.contains(cliente));
	}
	
	@Async
	private void escrita(Socket cliente, String mensagem) {
		try {
			OutputStream outStream = cliente.getOutputStream();
			DataOutputStream saida = new DataOutputStream(outStream);
			
			saida.writeBytes(mensagem);
		} catch (IOException e) {
			Raspberry raspberry = raspberryService.findByAddress(cliente.getInetAddress());
			raspberry.setEndereco(null);
			raspberryService.atualizaEnvia(raspberry);
			clientes.remove(cliente);
			e.printStackTrace();
		}		
	}
	
	private void executaRequisicaoRaspberry(String mensagem, Socket cliente) {
		//Ele recebe uma lista de estações assim que sincronizadas - então ele recebe o 
		// o comando de ir para uma estação (este comando deverá ser confirmado) (verifica o menor caminho)
		//- assim que chega espera o usuário autorizar sua saída - se direciona para a ultima estação
		JSONObject json = new JSONObject(mensagem);
		if(json.has("raspberrySync")) { //Solicita novas infos do Raspberry
			
			String jsonRasp = getRaspberry(json, cliente.getInetAddress());
			jsonRasp = "{'raspberrySync': "+jsonRasp+"}";
			escrita(cliente, jsonRasp);					
			//update raspberry (ip de conexão / estação atual / horario_chegada
			//enviar mensagem para usuario nos dois ultimos casos
		}else if(json.has("estacaoId")){ //Raspberry novo se localiza por isso
			
			String jsonRasp = criaRaspberry(json, cliente.getInetAddress());
			jsonRasp = "{'raspberrySync': "+jsonRasp+"}";
			escrita(cliente, jsonRasp);			
			
		} else if(json.has("getEstacoes")) { //Ele solicita lista de estacoes
			
			String jsonEstacoes = listaEstacoes(cliente.getInetAddress());
			jsonEstacoes = "{'estacoesSync': "+jsonEstacoes+"}";
			escrita(cliente, jsonEstacoes);
			
		} else if(mensagem.equals("close_conn")){
			try {
				cliente.close();
				clientes.remove(cliente);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
	}
	
	private String listaEstacoes(InetAddress endereco) {
		Raspberry raspberry = raspberryService.findByAddress(endereco);
		List<Estacao> estacoes = estacaoService.getLista(raspberry.getEmpresa());
		ObjectMapper objectMapper = new ObjectMapper();
		String jsonEstacoes = "";
		try {
			jsonEstacoes = objectMapper.writeValueAsString(estacoes);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		System.out.println(jsonEstacoes);
		return jsonEstacoes;
	}
	
	private String getRaspberry(JSONObject json, InetAddress endereco) {
		JSONObject raspberryJson = json.getJSONObject("raspberrySync");
		String id = raspberryJson.getString("id");
		String estacao_id = json.getString("estacaoId");
		Raspberry raspberry = raspberryService.busca(id);
		raspberry.setEstacao(estacaoService.busca(estacao_id));
		raspberry.setEndereco(endereco.getHostAddress());
		raspberryService.atualizaEnvia(raspberry);
		
		comandoService.findComandoExecutando(raspberry);

		ObjectMapper objectMapper = new ObjectMapper();
		String jsonRasp = "";
		try {
			jsonRasp = objectMapper.writeValueAsString(raspberry);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		System.out.println(jsonRasp);
		return jsonRasp;
	}
	
	private String criaRaspberry(JSONObject json, InetAddress endereco) {
		String estacao_id = json.getString("estacaoId");
		Raspberry raspberry = new Raspberry();
		raspberry.setNome("Insira meu novo nome");
		raspberry.setEstacao(estacaoService.busca(estacao_id));
		raspberry.setEmpresa(raspberry.getEstacao().getEmpresa());
		raspberry.setEndereco(endereco.getHostAddress());
		
		raspberryService.salvaRaspberry(raspberry);
		ObjectMapper objectMapper = new ObjectMapper();
		String jsonRasp = "";
		try {
			jsonRasp = objectMapper.writeValueAsString(raspberry);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		System.out.println(jsonRasp);
		return jsonRasp;
	}

}
