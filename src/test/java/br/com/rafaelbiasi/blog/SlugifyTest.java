package br.com.rafaelbiasi.blog;

import com.github.slugify.Slugify;

class SlugifyTest {

	private static final Slugify slugify = Slugify.builder().build();

	public static void main(String[] args) {
		slugify("A Dança dos Relógios Flexíveis");
		slugify("O Sussurro das Bibliotecas Submersas");
		slugify("A Colheita de Estrelas Cadentes");
		slugify("O Chá das Quimeras");
		slugify("Os Guardiões das Sombras Falantes");
		slugify("O Desfile dos Relógios Sonâmbulos");
		slugify("O Jardim das Chaves Perdidas");
		slugify("O Baile dos Espelhos Invisíveis");
		slugify("A Ópera das Nuvens Rebeldes");
		slugify("O Circo dos Sonhos Despertos");
		slugify("A Fábrica de Arco-Íris Monocromáticos");
		slugify("O Café dos Poetas Invisíveis");
		slugify("A Biblioteca dos Livros Nunca Escritos");
		slugify("Os Carteiros do Tempo Impossível");
		slugify("O Relojoeiro das Horas Invertidas");
		slugify("O Mercado das Palavras que Fugiram");
		slugify("Os Pintores dos Sussurros Coloridos");
		slugify("O Encontro das Estações Esquecidas");
		slugify("Os Cartógrafos dos Mapas Imaginários");
		slugify("A Orquestra dos Instrumentos Silenciosos");

	}

	private static void slugify(String title) {
		String slugifiedTitle = slugify.slugify(title);
		System.out.println("Slugified Title: " + slugifiedTitle);
	}
}