package br.com.rafaelbiasi.blog.infrastructure.initial;

import br.com.rafaelbiasi.blog.core.domain.model.Post;
import br.com.rafaelbiasi.blog.core.domain.model.Role;
import br.com.rafaelbiasi.blog.core.domain.model.User;
import br.com.rafaelbiasi.blog.core.domain.service.FileService;
import br.com.rafaelbiasi.blog.core.domain.service.PostService;
import br.com.rafaelbiasi.blog.core.domain.service.RoleService;
import br.com.rafaelbiasi.blog.core.domain.service.UserService;
import br.com.rafaelbiasi.blog.infrastructure.util.LogId;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.HashSet;

@Slf4j
@Component
@RequiredArgsConstructor
public class InitialData implements CommandLineRunner {

	private final FileService fileService;
	private final PostService postService;
	private final UserService userService;
	private final RoleService roleService;

	@Override
	public void run(final String... args) {
		try {
			LogId.startLogId();
			if (postService.findAll().isEmpty()) {
				log.info("Starting application data initialization");
				fileService.init();
				log.info("File service initialization completed");
				val roles = createRoles();
				val users = createUsers(roles);
				createPosts(users);
			}
		} finally {
			LogId.endLogId();
		}
	}

	private UsersResult createUsers(final RolesResult roles) {
		log.info("Creating default users");
		val user = createUser(
				"User",
				"Resu",
				"user@domain.com",
				"user",
				"resu",
				roles.user()
		);
		val admin = createUser(
				"Admin",
				"Nimda",
				"admin@domain.com",
				"admin",
				"nimda",
				roles.admin()
		);
		val guest = createUser(
				"Guest",
				"Tseug",
				"guest@domain.com",
				"guest",
				"tseug",
				roles.guest()
		);
		return UsersResult.builder().user(user).admin(admin).guest(guest).build();
	}

	private void createPosts(UsersResult result) {
		log.info("Creating default posts");
		createPost(
				result.user(), "A Dança dos Relógios Flexíveis",
				"No crepúsculo das horas elásticas, " +
						"os relógios dançam ao ritmo da chuva de penas. " +
						"Giram, flexionam e distorcem, " +
						"marcando um tempo que flui como um rio de gelatina. " +
						"A cada badalar, uma nova dimensão se abre, " +
						"onde os gatos recitam poesias e as sombras cantam.",
				"324d5363-e26f-432b-a101-0263ca60532d.webp"
		);
		createPost(
				result.admin(), "O Sussurro das Bibliotecas Submersas",
				"Sob a luz difusa de uma lua subaquática, " +
						"livros nadam livremente entre corais de palavras. " +
						"Ecos de histórias nunca contadas ressoam nas ondas, " +
						"enquanto peixes de tinta desenham versos no mar de silêncio. " +
						"Cada página é uma vela, navegando no oceano do desconhecido.",
				"293df626-ac4d-4ebc-851c-17b2cb0456a5.webp"
		);
		createPost(
				result.user(),
				"A Colheita de Estrelas Cadentes",
				"Em campos celestiais, " +
						"agricultores de sonhos colhem estrelas cadentes com redes tecidas de esperança." +
						" O orvalho da noite brilha como diamantes em suas mãos, " +
						"enquanto sussurram segredos ao vento, " +
						"que os leva para dançar entre as constelações.",
				"c0bd6525-70c3-483b-aabd-518293795421.webp"
		);
		createPost(
				result.admin(),
				"O Chá das Quimeras",
				"À mesa, " +
						"quimeras de todas as formas e " +
						"tamanhos degustam um chá feito de nuvens e raios de sol. " +
						"Conversam sobre o peso das asas de borboleta e o sabor da luz da lua, " +
						"enquanto o céu se dobra para ouvir melhor suas histórias.",
				"3cf73ae4-a7ce-4f4f-888a-012a7c70cb66.webp"
		);
		createPost(
				result.user(),
				"Os Guardiões das Sombras Falantes",
				"Na floresta onde as sombras falam, " +
						"guardiões feitos de murmúrios e " +
						"brisa protegem os segredos sussurrados pelas árvores. " +
						"Eles caminham sem tocar o chão, " +
						"deixando rastros de poemas que só podem ser lidos ao anoitecer.",
				"085a1b50-6817-4970-b253-4454d02c7b95.webp"
		);
		createPost(
				result.admin(),
				"O Desfile dos Relógios Sonâmbulos",
				"Relógios sonâmbulos desfilam pelas ruas de uma cidade adormecida, " +
						"seus ponteiros girando ao contrário. " +
						"Eles contam histórias de tempos que nunca existiram, " +
						"guiados pela lua que canta canções de ninar para o sol.",
				"dec167ba-dcf2-465e-a8a9-034ab10b44eb.webp"
		);
		createPost(
				result.user(),
				"O Jardim das Chaves Perdidas",
				"Em um jardim secreto, " +
						"chaves de todas as formas e tamanhos brotam como flores. " +
						"Elas abrem portas para dimensões esquecidas, " +
						"onde o tempo brinca de esconde-esconde e as cores têm aroma.",
				"10241c93-a224-4993-9242-e3d2bbcbfbff.webp"
		);
		createPost(
				result.admin(),
				"O Baile dos Espelhos Invisíveis",
				"Espelhos invisíveis dançam um baile silencioso, " +
						"refletindo sonhos que ninguém sonhou. " +
						"Eles giram e sussurram segredos de outras vidas, " +
						"em um salão onde a realidade se dobra e o impossível é apenas um convite.",
				"c35b7aa8-2d52-4415-ab90-720bbde8fd11.webp"
		);
		createPost(
				result.user(),
				"A Ópera das Nuvens Rebeldes",
				"Nuvens rebeldes encenam uma ópera no céu, " +
						"com trovões como tambores e relâmpagos como holofotes. " +
						"Cantam a liberdade do vento e a arte de desenhar no azul infinito, " +
						"enquanto a chuva aplaude em pé.",
				"dcf06a23-9b4c-4b07-910e-9ec652af12a7.webp"
		);
		createPost(
				result.admin(),
				"O Circo dos Sonhos Despertos",
				"No circo dos sonhos despertos, " +
						"artistas saltam de pensamentos em voo, " +
						"equilibrando-se em fios de memórias. " +
						"Risos pintados no ar, " +
						"acrobatas desafiam a gravidade com a leveza de um desejo, " +
						"em um espetáculo onde cada ato é um milagre.",
				"c500b85f-e39c-4659-a1b1-63fff2684ece.webp"
		);
		createPost(
				result.user(),
				"A Fábrica de Arco-Íris Monocromáticos",
				"Em uma fábrica esquecida, " +
						"arco-íris monocromáticos são tecidos com fios de sombra e luz. " +
						"Eles colorem o mundo em tons de silêncio, " +
						"pintando esperanças em uma paleta de sonhos incolor.",
				"9f7035d6-158b-4142-835e-2a32406e207c.webp"
		);
		createPost(
				result.admin(),
				"O Café dos Poetas Invisíveis",
				"Em um café escondido entre as páginas do tempo, " +
						"poetas invisíveis recitam versos ao vento. " +
						"Suas palavras são como folhas ao outono, " +
						"dançando em um balé de metáforas e rimas que só o coração pode ouvir.",
				"43be43d2-0002-49af-a991-02c23b40a4f6.webp"
		);
		createPost(
				result.user(),
				"A Biblioteca dos Livros Nunca Escritos",
				"Em uma biblioteca de silêncios, " +
						"estantes guardam livros nunca escritos. " +
						"Suas páginas em branco ecoam histórias que aguardam ser sonhadas, " +
						"em uma sinfonia de possibilidades que a imaginação ainda não desenhou.",
				"d64b1b91-b51a-4e4d-b906-b6f4d53c9db3.webp"
		);
		createPost(
				result.admin(),
				"Os Carteiros do Tempo Impossível",
				"Carteiros de um tempo impossível entregam cartas que nunca foram enviadas. " +
						"Eles viajam por entre segundos esquecidos, " +
						"deixando mensagens de futuros que poderiam ter sido, " +
						"nas caixas de correio de ontem.",
				"9fd8d0e4-c969-4e7f-ab5e-8f244a6d2ed4.webp"
		);
		createPost(
				result.user(),
				"O Relojoeiro das Horas Invertidas",
				"Um relojoeiro conserta horas invertidas, " +
						"em um mundo onde os minutos correm para trás. " +
						"Ele ajusta os ponteiros de destinos entrelaçados, " +
						"tecendo o tecido do tempo com agulhas de momentos perdidos.",
				"f98dccc6-9fc9-49e3-829a-82d91faed2cc.webp"
		);
		createPost(
				result.admin(),
				"O Mercado das Palavras que Fugiram",
				"No mercado das palavras que fugiram, " +
						"vendedores oferecem diálogos que nunca foram ditos. " +
						"Frases voam como pássaros, procurando ouvidos onde possam pousar, " +
						"em um labirinto de conversas que o vento levou.",
				"7f906be8-7a2e-4ef2-b080-b4f113c325c3.webp"
		);
		createPost(
				result.user(),
				"Os Pintores dos Sussurros Coloridos",
				"Pintores de sussurros coloridos desenham no ar com pincéis feitos de brisa. " +
						"Eles capturam os tons do silêncio, " +
						"em uma galeria invisível onde cada obra é uma canção para os olhos.",
				"ff1b1deb-af2f-4aba-ad65-cbe3f77dd060.webp"
		);
		createPost(
				result.admin(),
				"O Encontro das Estações Esquecidas",
				"As estações esquecidas se encontram em um cruzamento de ventos. " +
						"Primaveras sem flores e " +
						"invernos sem frio compartilham segredos de um tempo que se perdeu, " +
						"em um baile de folhas que nunca caíram.",
				"d8ebb6c9-8250-419e-8714-e1580a0c12ee.webp"
		);
		createPost(
				result.user(),
				"Os Cartógrafos dos Mapas Imaginários",
				"Cartógrafos desenham mapas de lugares que existem apenas em sonhos. " +
						"Eles navegam por mares de fantasia e escalam montanhas de pura imaginação, " +
						"em uma jornada onde o destino é sempre uma surpresa.",
				"6d959b07-ce29-4a7f-93fe-a17b167032d8.webp"
		);
		createPost(
				result.admin(), "A Orquestra dos Instrumentos Silenciosos",
				"Uma orquestra de instrumentos silenciosos toca uma sinfonia de ecos. " +
						"Notas invisíveis preenchem o ar, " +
						"em uma melodia que se sente com a alma, " +
						"em um concerto onde o silêncio é o som mais profundo.",
				"118c414c-0548-408f-9f7a-ee2a30bb29cc.webp"
		);
	}

	private Role createRole(final String name) {
		val role = new Role(name);
		roleService.save(role);
		log.debug("Role created: {}", name);
		return role;
	}

	private User createUser(
			final String userFirst,
			final String userLast,
			final String mail,
			final String username,
			final String password,
			final Role role
	) {
		val user = new User(mail, username, password, userFirst, userLast);
		val roles = new HashSet<Role>();
		roles.add(role);
		user.setRoles(roles);
		userService.save(user);
		log.debug("User created: {}", username);
		return user;
	}

	private void createPost(
			final User user,
			final String title,
			final String body,
			final String imageFilePath
	) {
		val post = new Post(title, body, imageFilePath, user);
		postService.save(post);
		log.debug("Post created: {}", title);
	}

	RolesResult createRoles() {
		log.info("Creating default roles");
		val user = createRole("ROLE_USER");
		val admin = createRole("ROLE_ADMIN");
		val guest = createRole("ROLE_GUEST");
		return RolesResult.builder().user(user).admin(admin).guest(guest).build();
	}

	@Builder
	record UsersResult(User user, User admin, User guest) {
	}

	@Builder
	record RolesResult(Role user, Role admin, Role guest) {
	}
}
