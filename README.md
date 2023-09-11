# UserAdministration

Usar develop como base

O UserAdministration é um aplicativo Android que permite o cadastro e gerenciamento de usuários de forma simples e eficaz. Com este aplicativo, você pode realizar as seguintes ações:

* Registrar novos usuários.
* Visualizar a lista de usuários cadastrados.
* Editar informações de usuário existentes.
* Excluir usuários da base de dados.

Este projeto foi desenvolvido com o objetivo de proporcionar uma solução flexível e de fácil utilização para gerenciar informações de usuário em um aplicativo Android.

instruções

* Para cadastrar use o Floating Action Button
* Para excluir arraste da direita pra esquerda
* pra editar so clicar no elemento da lista

## 🚀 Tecnologias e Arquitetura Utilizadas

### 🏛️ Arquitetura
- **MVVM (Model-View-ViewModel)**: A aplicação adota a arquitetura MVVM para manter uma separação limpa entre a View, ViewModel e Model.
- **Clean Code**: Nosso código segue os princípios de Clean Code, tornando-o fácil de ler e manter.
- **Camadas**: Organizamos nosso código em camadas, incluindo Presenter, Domain e Data, para uma gestão eficiente das responsabilidades.

### 📚 Bibliotecas e Frameworks
- **Retrofit**: Usamos Retrofit para realizar chamadas de API de maneira eficiente e segura.
- **Koin**: Gerenciamos a injeção de dependência com Koin para simplificar a configuração e manutenção das dependências.
- **LiveData**: LiveData é empregado para notificar mudanças de dados na ViewModel, garantindo uma UI suave e reativa.
- **Room**: Utilizamos o Room para armazenar dados localmente, simplificando o acesso a um banco de dados SQLite.
- **Navigation**: O Navigation Component facilita a navegação entre telas, tornando o fluxo de navegação mais gerenciável, incluindo a navegação entre Fragments.
- **Módulos**: Modularizamos nosso aplicativo para torná-lo altamente escalável e permitir a adição de novos recursos de forma mais simples.
- **Coroutines**: Fazemos chamadas assíncronas de maneira concisa e eficiente com o uso de Coroutines.
- **Lifecycle**: O Android Lifecycle é integrado para gerenciar ciclos de vida de componentes, garantindo um comportamento correto em relação às atividades e fragmentos.

🤝 Contribuindo
Sinta-se à vontade para contribuir com nosso projeto. Confira nossa guia de contribuição para saber como começar.

📄 Licença
Este projeto está sob a licença MIT.
