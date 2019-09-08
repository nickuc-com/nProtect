/**
 * Copyright 2019 NickUltracraft
 *
 * A class UpdaterAPI.java é uma api pública usada para atualizações de plugins, criada originalmente por NickUltracraft
 * Discord: NickUltracraft#4550
 * Mais informações: https://nickuc.tk 
 *
 * Caso for utilizar esta api em seu plugin, não altere este aviso em qualquer distribuição.
*/

package nickultracraft.protect.api;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.logging.Logger;

import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;

import nickultracraft.updater.api.UpdaterManager;

public class UpdaterAPI {

	/*
	 * Não altere os campos abaixo
	 */
	private static final String API_VERSION = "1.7";
	
	private boolean DEBUG = false;
	private Plugin plugin;
	private File pluginFile;
	private boolean updateAvailable;
	private String pluginName;
	private String pluginLastVersion;
	private String pluginDownloadURL;
	private Logger logger;
	private String prefix;
	private String updaterLink;
	private String pluginCheck;
		
	public boolean setupURL() {
		/*
		 * A página de checagem de versão deve seguir este padrão:
		 * VERSÃO-LINK_DOWNLOAD
		 * 
		 * Exemplo:
		 * 1.0-https://www.nickuc.tk/downloads/jars/examplePlugin/examplePlugin.jar
		 */
		
		/*
		 * Link para verificar a versão do seu plugin. Siga o padrão acima.
		 */
		setPluginCheckLink("https://www.nickuc.tk/plugin/info?nProtect");
		
		/*
		 * Link para fazer o download do seu plugin. O link de download deve ser direto, sem apresentar nenhum sistema de pular anúncio, recaptcha, etc...
		 */
		setDownloadLink("https://www.nickuc.tk/plugin/download?id=2");
		
		/*
		 * Não altere o campo abaixo. Ele é utilizado para verificar se você
		 * trocou os campos do url para atualizar seu plugin.
		 */
		return !getPluginCheckLink().equals("{URL_CHECK_VERSION}") && !getDownloadLink().equals("{URL_DOWNLOAD_DIRETO}");
	}
	
	
	public UpdaterAPI(Plugin plugin, String pluginName) throws UpdaterException {
		if(plugin == null) { 
			new UpdaterException("Você não pode usar o auto updater para valores de plugin nulos."); 
			return; 
		}
		/*
		 * Dados dos plugins que serão atualizados.
		 * Não altere se você não sabe o que está fazendo.
		 */
		this.plugin = plugin;
		this.pluginName = pluginName;
		
		/*
		 * Logger que irá mandar mensagens das operações.
		 * Não altere se você não sabe o que está fazendo.
		 */
		this.logger = plugin.getLogger();
		
		/*
		 * Prefix que será enviado para operações feitas pela api do plugin.
		 */
		this.prefix = "[" + pluginName.toUpperCase() + " UPDATER] ";
		
		/*
		 * Link para fazer o download do plugin atualizador.
		 * Não altere se você não sabe o que está fazendo.
		 */
		this.updaterLink = "https://www.nickuc.tk/plugin/download?id=7";
			
		/*
		 * Verificação para ver se foi possível encontrar o arquivo do plugin.
		 */
		if(!searchPluginFile()) {
			new UpdaterException("Ops, não foi possível encontrar o nome do arquivo do atualizador do plugin " + pluginName + ". Certifique que você não alterou o nome do jar.");
		}
		
		/*
		 * Verificação para ver se as urls para verificação do plugin foram alteradas.
		 */
		if(!setupURL()) {
			new UpdaterException("Ops, parece que você ainda não configurou os links para o auto updater. Por favor, altere os campos {URL_DOWNLOAD_DIRETO} & {URL_CHECK_VERSION} da classe UpdaterAPI.java");
		}
	}
	
	/* 
	 * Easy config: Você pode apenas carregar este objeto no onEnable() para verificar updates do seu plugin.
	 */
	public void defaultEnableExecute() throws UpdaterException {
		if(plugin == null) { 
			new UpdaterException("Você não pode usar o auto updater para valores de plugin nulos."); 
			return; 
		}
		Bukkit.getScheduler().runTaskAsynchronously(plugin, new Runnable() {
			@Override
			public void run() {
				try {
					checkUpdate("Mozilla/5.0 (Windows NT 6.1; WOW64; rv:25.0) Gecko/20100101 Firefox/25.0");
				} catch (Throwable e) {
					e.printStackTrace();
					logger.warning("Nao foi possivel verificar por novas atualizacoes.");
				}
				try {
					downloadUpdate();
				} catch (Throwable e) {
					e.printStackTrace();
					logger.warning("Nao foi possivel realizar o download da atualizacao.");
				}
			}
		});
	}
	public void defaultDisableExecute() throws UpdaterException {
		if(plugin == null) { 
			new UpdaterException("Você não pode usar o auto updater para valores de plugin nulos."); 
			return; 
		}
		try {
			installUpdate();
		} catch (Throwable e) {
			e.printStackTrace();
			logger.warning("Nao foi possivel realizar a instalacao da atualizacao.");
		}
	}
	
	public boolean searchPluginFile() {
		Arrays.asList(plugin.getDataFolder().getParentFile().listFiles()).stream().filter(file -> file.getName().toLowerCase().contains(plugin.getName().toLowerCase()) && file.getName().toLowerCase().endsWith(".jar")).forEach(file -> pluginFile = file);
		if(pluginFile == null) {
			this.pluginFile = new File(plugin.getDataFolder().getParentFile(), pluginName + ".jar");
		}
		return pluginFile != null;
	}
	public Plugin getPlugin() {
		return plugin;
	}
	public void setPlugin(Plugin plugin) {
		this.plugin = plugin;
	}
	public String getPluginName() {
		return pluginName;
	}
	public void setPluginName(String pluginName) {
		this.pluginName = pluginName;
	}
	public String getLastVersion() {
		return pluginLastVersion;
	}
	public void setLastVersion(String lastVersion) {
		this.pluginLastVersion = lastVersion;
	}
	public String getDownloadLink() {
		return pluginDownloadURL;
	}
	public void setDownloadLink(String downloadUrl) {
		this.pluginDownloadURL = downloadUrl;
	}
	public String getPluginCheckLink() {
		return pluginCheck;
	}
	public void setPluginCheckLink(String pluginCheck) {
		this.pluginCheck = pluginCheck;
	}
	public String getPrefix() {
		return prefix;
	}
	public void setPrefix(String prefix) {
		this.prefix = prefix;
	}
	public boolean getDebug() {
		return DEBUG;
	}
	public void setDebug(boolean debug) {
		this.DEBUG = debug;
	}
	public boolean isUpdateAvailable() {
		return updateAvailable;
	}
	public void setUpdateAvailable(boolean updateAvailable) {
		this.updateAvailable = updateAvailable;
	}
	public String getApiVersion() {
		return API_VERSION;
	}
	public File getPluginFile() {
		return pluginFile;
	}
	public void installUpdate() throws Throwable {
		if(!isUpdateAvailable()) {
			return;
		}
		
		/*
		 * Instala o auto updater se não estiver instalado.
		 */
		installAutoUpdater();
		
		/*
		 * Envia para o usuário que o processo de atualização está sendo iniciado.
		 */
		logger.info(prefix + "Iniciando instalacao da atualizacao do plugin " + pluginName + " v" + plugin.getDescription().getVersion() + " -> " + getLastVersion() + "...");
		
		/*
		 * Realiza a substituição do arquivo do antigo update para o novo.
		 */
		new UpdaterManager(pluginName).update(getPluginFile().getName().substring(getPluginFile().getName().length(), getPluginFile().getName().length()-4));
	}
	public void downloadUpdate() throws Throwable {
		if(!isUpdateAvailable()) {
			return;
		}
		
		/*
		 * Instala o auto updater se não estiver instalado.
		 */
		installAutoUpdater();
		
		/*
		 * Envia para o usuário que o processo de download da atualização está sendo iniciado.
		 */
		logger.info(prefix + "Iniciando download da atualizacao do plugin " + pluginName + " v" + plugin.getDescription().getVersion() + " -> " + getLastVersion() + "...");
		
		/*
		 * Realiza o download arquivo do novo update e salva em um cache.
		 */
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				new UpdaterManager(pluginName).baixar(getDownloadLink());
			}
		}).start();
	}
	public void checkUpdate(String userAgent) throws Throwable {
		/*
		 * Verifica se um novo update está disponível
		 */
		InputStream is;
		URLConnection openConnection = new URL(pluginCheck).openConnection();
		openConnection.addRequestProperty("User-Agent", userAgent);
		is = openConnection.getInputStream();
		BufferedReader rd = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
        StringBuilder sb = new StringBuilder();
        int cp;
        while ((cp = rd.read()) != -1) {
        	sb.append((char) cp);
        }
        
        /*
         * Pega os dados atualizados para 
         */
		this.pluginLastVersion = sb.toString().split("-")[0];
		this.pluginDownloadURL = sb.toString().split("-")[1];
		
		/*
		 * Se um novo update estiver disponível, o valor será salvado.
		 */
		this.updateAvailable = !pluginLastVersion.equals(plugin.getDescription().getVersion());
	}
 	public void installAutoUpdater() throws Throwable {
 		/*
 		 * Instala o auto updater automaticamente para fazer o gerenciamento de updates.
 		 */
		if(plugin.getServer().getPluginManager().getPlugin("NickUC-Updater") == null) {
			logger.info(prefix + "Plugin de atualizacao nao foi encontrado. Realizando download automaticamente...");
			URL url = new URL(updaterLink);
	        HttpURLConnection httpConnection = (HttpURLConnection) url.openConnection();
	        httpConnection.setRequestProperty("User-Agent", pluginName + "ResourceUpdater");
	        long completeFileSize = httpConnection.getContentLength();
	        if(completeFileSize <= -1) {
	        	logger.warning(prefix + "Ops, parece que o arquivo de download do updater esta corrompido. Download cancelado."); 
	        	return;
	        }
	        if(DEBUG) logger.info("Conexao estabelecida com sucesso. Tamanho do arquivo é de " + completeFileSize);
	        BufferedInputStream in = new BufferedInputStream(httpConnection.getInputStream());
	        if(DEBUG) logger.info("Iniciando download do arquivo jar do updater...");
	        File updaterJar = new File(plugin.getDataFolder().getParentFile(), "nUpdater.jar");
	        FileOutputStream fos = new FileOutputStream(updaterJar);
	        BufferedOutputStream bout = new BufferedOutputStream(fos, 1024);
	        byte[] data = new byte[1024];
	        int x;
	        while ((x = in.read(data, 0, 1024)) >= 0) {
	            bout.write(data, 0, x);
	        }
	        if(DEBUG) logger.info(prefix + "Fechando conexoes com o site...");
	        bout.close();
			in.close();
				
			plugin.getServer().getPluginManager().loadPlugin(updaterJar);
			plugin.getServer().getPluginManager().enablePlugin(plugin.getServer().getPluginManager().getPlugin("NickUC-Updater"));
		}
	}
 	
 	public class UpdaterException extends Exception {

		private static final long serialVersionUID = -3001805382277989376L;
		
		public UpdaterException(String message) {
			super(message);
		}
	}
}