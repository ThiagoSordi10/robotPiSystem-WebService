package br.com.thiago.robotPi.firebase;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.fasterxml.jackson.annotation.JsonInclude.Include;

import br.com.thiago.robotPi.dto.MensagemSync;
import br.com.thiago.robotPi.dto.RaspberrySync;
import br.com.thiago.robotPi.dto.UserSync;
import br.com.thiago.robotPi.model.Dispositivo;
import br.com.thiago.robotPi.model.Mensagem;
import br.com.thiago.robotPi.service.DispositivoService;
import okhttp3.Request;
import okhttp3.Response;

public class FirebaseSender extends FirebaseClient {

	private DispositivoService dispositivoService;
	private final static Logger LOGGER = Logger.getLogger(FirebaseSender.class);

	public FirebaseSender(FirebaseConfig config, DispositivoService dispositivoService) throws IOException {
		super(config);
		this.dispositivoService = dispositivoService;
	}
		
	public void enviaRaspberry(List<Dispositivo> dispositivos, RaspberrySync raspberrySync, String chave) throws IOException {
		List<Dispositivo> invalidos = new ArrayList<>();
		Dispositivo invalido = new Dispositivo();
		CloudMensaging mensagem = new CloudMensaging();
		mensagem.insereChaveEObjeto(chave, raspberrySync);
		mapper.setSerializationInclusion(Include.NON_NULL);
		
		for (Dispositivo dispositivo : dispositivos) {
			mensagem.setTo(dispositivo.getToken());
			String json = mapper.writeValueAsString(mensagem);
			System.out.println(json);
			Request request = newRequisicaoParaPost(json);
			Response response = client.newCall(request).execute();//realiza requisição com mensagem
			if (invalidator(response)) {
				LOGGER.error("falha no firebase " + response.body().string());
				invalidos.add(dispositivo);
				try {
					invalido = dispositivo.clone();
				} catch (CloneNotSupportedException e) {
					e.printStackTrace();
				}
			} else
				LOGGER.info("resposta do firebase " + response.body().string()); //ÊXITO
			response.close();
		}
		if(!invalidos.isEmpty()) {
			dispositivoService.deleta(invalidos);
		}
	}
	
	public void enviaMensagem(Dispositivo dispositivo, Mensagem mensagemToUser) throws IOException {
		CloudMensaging mensagem = new CloudMensaging();
		mensagem.insereChaveEObjeto("mensagem", mensagemToUser);
		mapper.setSerializationInclusion(Include.NON_NULL);
		
		mensagem.setTo(dispositivo.getToken());
		String json = mapper.writeValueAsString(mensagem);
		System.out.println(json);
		Request request = newRequisicaoParaPost(json);
		Response response = client.newCall(request).execute();//realiza requisição com mensagem
		if (invalidator(response)) {
			LOGGER.error("falha no firebase " + response.body().string());
			dispositivoService.deleta(dispositivo);
		} else
			LOGGER.info("resposta do firebase " + response.body().string()); //ÊXITO
		response.close();
	}

	@SuppressWarnings("unchecked")
	private boolean invalidator(Response response) throws IOException {
		System.out.println(response.code());
		if (response.code() != 401) {
			String resposta = response.body().string();
			List<LinkedHashMap<?, ?>> lista = (List<LinkedHashMap<?, ?>>) mapper.readValue(resposta, Map.class)
					.get("results");
			Map<?, ?> results = lista.get(0);
			if (results.containsKey(ERROR)) {
				String erro = (String) results.get(ERROR);
				if (erro.equals(NOT_REGISTERED) || erro.equals(INVALID_REGISTRATION)) {
					return true;
				}
			}
		}
		return false;
	}

}
